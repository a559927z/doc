package net.chinahrd.eis.search.service.impl;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.global.Global;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStats;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import net.chinahrd.eis.search.EsUtil;
import net.chinahrd.eis.search.aggs.AggsItem;
import net.chinahrd.eis.search.aggs.AggsOption;
import net.chinahrd.eis.search.aggs.AggsResult;
import net.chinahrd.eis.search.aggs.AggsType;
import net.chinahrd.eis.search.aggs.DateHistogramPack;
import net.chinahrd.eis.search.condition.HighlightOption;
import net.chinahrd.eis.search.condition.SearchContent;
import net.chinahrd.eis.search.condition.SearchKind;
import net.chinahrd.eis.search.condition.SearchLogic;
import net.chinahrd.eis.search.service.EsQueryService;
import net.chinahrd.utils.AssertKit;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.JsonKit;


public class EsQueryServiceImpl implements EsQueryService {
	private final static Logger logger = LoggerFactory.getLogger(EsQueryServiceImpl.class);
	/**
	 * double 数据格式化
	 */
	private final static DecimalFormat df = new DecimalFormat("#.00");

	private static final int FromIndex = 0;
	private static final int MinSize = 100;
	private static final int MaxSize = 100000;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private SearchResultMapper highlightMapper;

	@Override
	public BoolQueryBuilder buildBoolQueryBuilder(List<SearchContent> searchContents, String minimumShouldMatch) {
		BoolQueryBuilder root = QueryBuilders.boolQuery();

		for (SearchContent searchContent : searchContents) {
			String field = searchContent.getField();
			Object[] values = searchContent.getValues();
			if (!this.checkValue(values)) {
				continue;
			}

			if (searchContent.getSearchKind() == SearchKind.range) {
				/* 区间搜索 */
				buildBoolQueryByLogic(root, createRangeQueryBuilder(field, values), searchContent.getSearchLogic());
				continue;
			}

			if (searchContent.getSearchKind() == SearchKind.terms) {
				buildBoolQueryByLogic(root, QueryBuilders.termsQuery(field, values).boost(searchContent.getBoost()),
						searchContent.getSearchLogic());
				continue;
			}

			for (Object valueItem : values) {
				QueryBuilder item = null;
				String formatValue = valueItem.toString().trim().replace("*", "");// 格式化搜索数据
				if (searchContent.getSearchKind() == SearchKind.term) {
					item = QueryBuilders.termQuery(field, formatValue).boost(searchContent.getBoost());
				} else if (searchContent.getSearchKind() == SearchKind.querystring) {
					if (formatValue.length() == 1) {
						if (!Pattern.matches("[0-9]", formatValue)) {
							formatValue = "*" + formatValue + "*";
						}
					}
					QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(formatValue)
							.minimumShouldMatch(searchContent.getQueryStringPrecision());
					item = queryStringQueryBuilder.field(field).boost(searchContent.getBoost());
				}
				buildBoolQueryByLogic(root, item, searchContent.getSearchLogic());
			}
		}

		if (minimumShouldMatch != null) {
			root.minimumShouldMatch(minimumShouldMatch);
		}

		return root;
	}

	@Override
	public <T> Page<T> templateSearch(String indexName, String typeName, QueryBuilder queryBuilder, Pageable pageable,
			Map<String, String> sortOption, HighlightOption highlightOption, Class<T> entityClass) {
		AssertKit.notNull(indexName);
		AssertKit.notNull(typeName);
		AssertKit.notNull(queryBuilder);

		NativeSearchQueryBuilder nativeBuilder = new NativeSearchQueryBuilder().withIndices(indexName)
				.withTypes(typeName).withQuery(queryBuilder).withSearchType(SearchType.QUERY_THEN_FETCH);
		
		if (pageable != null) {
			nativeBuilder.withPageable(pageable);
		}

		// 添加多级排序
		if (sortOption != null) {
			for (Map.Entry<String, String> entry : sortOption.entrySet()) {
				SortOrder sortOrder = entry.getValue().equals("desc") ? SortOrder.DESC : SortOrder.ASC;
				nativeBuilder.withSort(new FieldSortBuilder(entry.getKey()).order(sortOrder));
			}
		}

		// 搜索结果高亮设置
		if (highlightOption != null) {
			HighlightBuilder.Field[] highlightFields = new HighlightBuilder.Field[highlightOption.getFields().size()];
			int i = 0;
			for (String field : highlightOption.getFields()) {
				highlightFields[i] = new HighlightBuilder.Field(field)
						.preTags(highlightOption.getPreTags())
						.postTags(highlightOption.getPostTags());
				i++;
			}
			nativeBuilder.withHighlightFields(highlightFields);
			return elasticsearchTemplate.queryForPage(nativeBuilder.build(), entityClass, highlightMapper);
		}
		
		Page<T> rets = elasticsearchTemplate.queryForPage(nativeBuilder.build(), entityClass);
		return rets;
	}
	
	@Override
	public <T> List<T> complexSearch(String indexName, String typeName, QueryBuilder queryBuilder, int from, int size,
			Map<String, String> sortOption, HighlightOption highlightOption, Class<T> entityClass) {

		AssertKit.notNull(indexName);
		AssertKit.notNull(typeName);
		AssertKit.notNull(queryBuilder);

		try {
			SearchRequestBuilder searchRequestBuilder = null;
			searchRequestBuilder = this.elasticsearchTemplate.getClient().prepareSearch(indexName).setTypes(typeName)
					.setSearchType(SearchType.QUERY_THEN_FETCH).setFrom(from > 0 ? from : FromIndex)
					.setSize(0 < size && size <= MaxSize ? size : MinSize).setExplain(true);

			// 添加查询条件
			if (queryBuilder != null) {
				searchRequestBuilder.setQuery(queryBuilder);
			}
			// 添加多级排序
			if (sortOption != null) {
				for (Map.Entry<String, String> entry : sortOption.entrySet()) {
					SortOrder sortOrder = entry.getValue().equals("desc") ? SortOrder.DESC : SortOrder.ASC;
					searchRequestBuilder.addSort(entry.getKey(), sortOrder);
				}
			}

			// 搜索结果高亮设置
			if (highlightOption != null) {
				for (String field : highlightOption.getFields()) {
					searchRequestBuilder.addHighlightedField(field);
				}
				searchRequestBuilder.setHighlighterPreTags(highlightOption.getPreTags())
						.setHighlighterPostTags(highlightOption.getPostTags());

			}

			if (logger.isDebugEnabled()) {
				logger.debug(searchRequestBuilder.toString());
			}

			SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
			return this.getSearchResultExt(searchResponse, entityClass);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public long complexCount(String indexName, String typeName, QueryBuilder queryBuilder) {
		if (queryBuilder == null) {
			return 0;
		}

		try {
			SearchResponse searchResponse = this.searchCountRequest(indexName, typeName, queryBuilder);
			return searchResponse.getHits().totalHits();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public AggsResult aggs(String indexName, String typeName, QueryBuilder queryBuilder, AggsOption aggsOption) {
		if (queryBuilder == null) {
			return null;
		}

		SearchRequestBuilder searchRequestBuilder = null;
		try {
			searchRequestBuilder = this.elasticsearchTemplate.getClient().prepareSearch(indexName).setTypes(typeName)
					.setSearchType(SearchType.QUERY_THEN_FETCH).setQuery(queryBuilder).setFrom(0).setSize(1)
					.setExplain(true);

			AbstractAggregationBuilder aggregation = buildAggregationBuilder(aggsOption);
			if (aggregation != null) {
				searchRequestBuilder.addAggregation(aggregation);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(searchRequestBuilder.toString());
			}

			SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
			return getAggsResult(searchResponse.getAggregations(), aggsOption);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<AggsResult> aggs(String indexName, String typeName, QueryBuilder queryBuilder,
			AggsOption[] aggsOptions) {
		if (queryBuilder == null) {
			return null;
		}

		SearchRequestBuilder searchRequestBuilder = null;
		try {
			searchRequestBuilder = this.elasticsearchTemplate.getClient().prepareSearch(indexName).setTypes(typeName)
					.setSearchType(SearchType.QUERY_THEN_FETCH).setQuery(queryBuilder).setFrom(0).setSize(1)
					.setExplain(true);

			for (AggsOption aggsOption : aggsOptions) {
				AbstractAggregationBuilder aggregation = buildAggregationBuilder(aggsOption);
				if (aggregation != null) {
					searchRequestBuilder.addAggregation(aggregation);
				}
			}

			if (logger.isDebugEnabled()) {
				logger.debug(searchRequestBuilder.toString());
			}

			SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

			List<AggsResult> result = CollectionKit.newList();
			for (AggsOption aggsOption : aggsOptions) {
				result.add(this.getAggsResult(searchResponse.getAggregations(), aggsOption));
			}

			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private AbstractAggregationBuilder buildAggregationBuilder(AggsOption aggsOption) {
		if (aggsOption == null) {
			return null;
		}
		AbstractAggregationBuilder aggregation = null;
		String name = aggsOption.getName();
		String field = aggsOption.getField();
		List<AggsOption> subAggsOptions = aggsOption.getSubAggsOptions();
		switch (aggsOption.getType()) {
		case global:
			aggregation = AggregationBuilders.global(name);
			for (AggsOption subAggsOption : subAggsOptions) {
				((AggregationBuilder) aggregation).subAggregation(buildAggregationBuilder(subAggsOption));
			}
			break;
		case terms:
			aggregation = AggregationBuilders.terms(name).field(field).size(9999).order(Terms.Order.term(true));
			for (AggsOption subAggsOption : subAggsOptions) {
				((AggregationBuilder) aggregation).subAggregation(buildAggregationBuilder(subAggsOption));
			}
			break;
		case histogram:
			aggregation = AggregationBuilders.histogram(name).field(field).minDocCount(0)
					.interval((Long) aggsOption.getOption());
			for (AggsOption subAggsOption : subAggsOptions) {
				((AggregationBuilder) aggregation).subAggregation(buildAggregationBuilder(subAggsOption));
			}
			break;
		case date_histogram:
			aggregation = AggregationBuilders.dateHistogram(name).field(field).minDocCount(0);
			if (aggsOption.getOption() != null && aggsOption.getOption() instanceof DateHistogramPack) {
				DateHistogramPack pack = (DateHistogramPack) aggsOption.getOption();
				((DateHistogramBuilder) aggregation).interval(pack.getInterval()).format(pack.getFormat())
						.extendedBounds(pack.getMin(), pack.getMax());
			}
			for (AggsOption subAggsOption : subAggsOptions) {
				((AggregationBuilder) aggregation).subAggregation(buildAggregationBuilder(subAggsOption));
			}
			break;
		case filter:
			aggregation = AggregationBuilders.filter(name).filter((QueryBuilder) aggsOption.getOption());
			for (AggsOption subAggsOption : subAggsOptions) {
				((AggregationBuilder) aggregation).subAggregation(buildAggregationBuilder(subAggsOption));
			}
			break;
		case nested:
			aggregation = AggregationBuilders.nested(name).path((String) aggsOption.getOption());
			for (AggsOption subAggsOption : subAggsOptions) {
				((AggregationBuilder) aggregation).subAggregation(buildAggregationBuilder(subAggsOption));
			}
			break;
		case value_count:
			aggregation = AggregationBuilders.count(name).field(field);
			break;
		case avg:
			aggregation = AggregationBuilders.avg(name).field(field);
			break;
		case max:
			aggregation = AggregationBuilders.max(name).field(field);
			break;
		case min:
			aggregation = AggregationBuilders.min(name).field(field);
			break;
		case sum:
			aggregation = AggregationBuilders.sum(name).field(field);
			break;
		case stats:
			aggregation = AggregationBuilders.stats(name).field(field);
			break;
		case extended_stats:
			aggregation = AggregationBuilders.extendedStats(name).field(field);
			break;
		}
		return aggregation;
	}

	private AggsResult getAggsResult(Aggregations aggregations, AggsOption aggsOption) {
		AggsResult aggsResult = new AggsResult();

		aggsResult.setAggName(aggsOption.getName());
		AggsType aggsType = aggsOption.getType();
		List<AggsOption> subAggsOptions = aggsOption.getSubAggsOptions();
		switch (aggsType) {
		case global:
			Global global = aggregations.get(aggsOption.getName());
			AggsItem itemGlobal = AggsItem.of("总数", String.valueOf(global.getDocCount()), aggsType);

			for (AggsOption subAggsOption : subAggsOptions) {
				itemGlobal.addAggsResult(this.getAggsResult(global.getAggregations(), subAggsOption));
			}
			aggsResult.addItem(itemGlobal);
			break;
		case terms:
			Terms terms = aggregations.get(aggsOption.getName());
			Collection<org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket> buckets = terms.getBuckets();
			for (org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket bucket : buckets) {
				AggsItem item = AggsItem.of(bucket.getKeyAsString(), String.valueOf(bucket.getDocCount()), aggsType);

				for (AggsOption subAggsOption : subAggsOptions) {
					item.addAggsResult(this.getAggsResult(bucket.getAggregations(), subAggsOption));
				}
				aggsResult.addItem(item);
			}
			break;
		case histogram:
			Histogram histogram = aggregations.get(aggsOption.getName());
			Collection<? extends Histogram.Bucket> hisbuckets = histogram.getBuckets();
			for (Histogram.Bucket hisbucket : hisbuckets) {
				AggsItem item = AggsItem.of(hisbucket.getKeyAsString(), String.valueOf(hisbucket.getDocCount()),
						aggsType);

				for (AggsOption subAggsOption : subAggsOptions) {
					item.addAggsResult(this.getAggsResult(hisbucket.getAggregations(), subAggsOption));
				}
				aggsResult.addItem(item);
			}
			break;
		case date_histogram:
			DateHistogramPack pack = (DateHistogramPack) aggsOption.getOption();
			Histogram dateHistogram = aggregations.get(aggsOption.getName());
			Collection<? extends Histogram.Bucket> dateHisbuckets = dateHistogram.getBuckets();
			for (Histogram.Bucket dateHisbucket : dateHisbuckets) {
				AggsItem item = AggsItem.of(dateHisbucket.getKeyAsString(), String.valueOf(dateHisbucket.getDocCount()),
						aggsType, pack);

				for (AggsOption subAggsOption : subAggsOptions) {
					item.addAggsResult(this.getAggsResult(dateHisbucket.getAggregations(), subAggsOption));
				}
				aggsResult.addItem(item);
			}
			break;
		case filter:
			Filter filter = aggregations.get(aggsOption.getName());
			AggsItem itemFilter = AggsItem.of("总数", String.valueOf(filter.getDocCount()), aggsType);

			for (AggsOption subAggsOption : subAggsOptions) {
				itemFilter.addAggsResult(this.getAggsResult(filter.getAggregations(), subAggsOption));
			}
			aggsResult.addItem(itemFilter);
			break;
		case nested:
			Nested nested = aggregations.get(aggsOption.getName());
			AggsItem itemNested = AggsItem.of("总数", String.valueOf(nested.getDocCount()), aggsType);

			for (AggsOption subAggsOption : subAggsOptions) {
				itemNested.addAggsResult(this.getAggsResult(nested.getAggregations(), subAggsOption));
			}
			aggsResult.addItem(itemNested);
			break;
		case value_count:
			ValueCount valueCount = aggregations.get(aggsOption.getName());
			AggsItem itemValueCount = AggsItem.of("数量", String.valueOf(valueCount.getValue()), aggsType);
			aggsResult.addItem(itemValueCount);
			break;
		case avg:
			Avg avg = aggregations.get(aggsOption.getName());
			AggsItem itemAvg = AggsItem.of("平均值", String.valueOf(df.format(avg.getValue())), aggsType);
			aggsResult.addItem(itemAvg);
			break;
		case max:
			Max max = aggregations.get(aggsOption.getName());
			AggsItem itemMax = AggsItem.of("最大值", String.valueOf(df.format(max.getValue())), aggsType);
			aggsResult.addItem(itemMax);
			break;
		case min:
			Min min = aggregations.get(aggsOption.getName());
			AggsItem itemMin = AggsItem.of("最小值", String.valueOf(df.format(min.getValue())), aggsType);
			aggsResult.addItem(itemMin);
			break;
		case sum:
			Sum sum = aggregations.get(aggsOption.getName());
			AggsItem itemSum = AggsItem.of("求和", String.valueOf(df.format(sum.getValue())), aggsType);
			aggsResult.addItem(itemSum);
			break;
		case stats:
			Stats stats = aggregations.get(aggsOption.getName());
			AggsItem itemStats = AggsItem.of("数量/最小值/最大值/平均值/求和",
					stats.getCount() + "/" + df.format(stats.getMin()) + "/" + df.format(stats.getMax()) + "/"
							+ df.format(stats.getAvg()) + "/" + df.format(stats.getSum()),
					aggsType);
			aggsResult.addItem(itemStats);
			break;
		case extended_stats:
			ExtendedStats extStats = aggregations.get(aggsOption.getName());
			AggsItem itemExtStats = AggsItem.of("数量/最小值/最大值/平均值/求和/平方和/方差/标准偏差值",
					extStats.getCount() + "/" + df.format(extStats.getMin()) + "/" + df.format(extStats.getMax()) + "/"
							+ df.format(extStats.getAvg()) + "/" + df.format(extStats.getSum()) + "/"
							+ df.format(extStats.getSumOfSquares()) + "/" + df.format(extStats.getVariance()) + "/"
							+ df.format(extStats.getStdDeviation()),
					aggsType);
			aggsResult.addItem(itemExtStats);
			break;
		}

		return aggsResult;
	}

	/* 简单的值校验 */
	private boolean checkValue(Object[] values) {
		if (values == null) {
			return false;
		} else if (values.length == 0) {
			return false;
		} else if (values[0] == null) {
			return false;
		} else if (values[0].toString().trim().isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 值范围查询
	 * 
	 * @param field
	 * @param values
	 * @return
	 */
	private RangeQueryBuilder createRangeQueryBuilder(String field, Object[] values) {
		if (values.length == 1 || values[1] == null || values[1].toString().trim().isEmpty()) {
			logger.warn("[区间搜索]必须传递两个值，但是只传递了一个值，所以返回null");
			return null;
		}
		boolean timeType = false;
		if (EsUtil.isDate(values[0]) && EsUtil.isDate(values[1])) {
			timeType = true;
		}
		String begin = "", end = "";
		if (timeType) {
			begin = EsUtil.formatDate(values[0]);
			end = EsUtil.formatDate(values[1]);
		} else {
			begin = values[0].toString();
			end = values[1].toString();
		}
		return QueryBuilders.rangeQuery(field).from(begin).to(end);
	}

	/**
	 * 根据逻辑组装查询
	 * 
	 * @param root
	 * @param item
	 * @param searchLogic
	 */
	private void buildBoolQueryByLogic(BoolQueryBuilder root, QueryBuilder item, SearchLogic searchLogic) {
		if (item != null) {
			switch (searchLogic) {
			case must:
				root = root.must(item);
				break;
			case mustNot:
				root = root.mustNot(item);
				break;
			case should:
				root = root.should(item);
				break;
			}
		}
	}

	/* 获得搜索结果 */
	private <T> List<T> getSearchResultExt(SearchResponse searchResponse, Class<T> entityClass) {
		try {
			List<T> resultList = CollectionKit.newList();
			for (SearchHit searchHit : searchResponse.getHits()) {
				T result = JsonKit.fromJson(searchHit.getSourceAsString(), entityClass);
				for (Entry<String, HighlightField> entry : searchHit.highlightFields().entrySet()) {
					StringBuilder highlightStr = new StringBuilder();
					for (Text text : entry.getValue().fragments()) {
						highlightStr.append(text.string());
					}
					PropertyUtils.setProperty(result, entry.getKey(), highlightStr.toString());
				}
				resultList.add(result);
			}
			return resultList;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	private SearchResponse searchCountRequest(String indexName, String typeName, QueryBuilder queryBuilder) {
		try {
			SearchRequestBuilder searchRequestBuilder = this.elasticsearchTemplate.getClient().prepareSearch(indexName)
					.setTypes(typeName).setSearchType(SearchType.QUERY_THEN_FETCH).setSize(0);
			searchRequestBuilder = searchRequestBuilder.setQuery(queryBuilder);
			return searchRequestBuilder.execute().actionGet();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

}

package net.chinahrd.eis.search.service;

import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.chinahrd.eis.search.aggs.AggsOption;
import net.chinahrd.eis.search.aggs.AggsResult;
import net.chinahrd.eis.search.condition.HighlightOption;
import net.chinahrd.eis.search.condition.SearchContent;

/**
 * @Description ElasticSearch api 封装
 * @author bright
 * 
 */
public interface EsQueryService {

	/**
	 * 构建查询
	 * 
	 * @param searchContents
	 *            查询条件
	 * @param minimumShouldMatch
	 *            匹配度
	 * @return
	 */
	BoolQueryBuilder buildBoolQueryBuilder(List<SearchContent> searchContents, String minimumShouldMatch);
	
	/**
	 * 利用elasticsearchTemplate实现查询
	 * @param indexNames
	 * @param searchContents
	 *            查询条件
	 * @param minimumShouldMatch
	 *            匹配度
	 * @param pageable
	 * @param sortOption
	 *            排序设置 <field,asc|desc>
	 * @param highlightOption
	 *            搜索结果高亮设置
	 * @param entityClass
	 * @return
	 */
	<T> Page<T> templateSearch(String indexName, String typeName, QueryBuilder queryBuilder, Pageable pageable,
			Map<String, String> sortOption, HighlightOption highlightOption, Class<T> entityClass);

	/**
	 * 
	 * @param indexNames
	 * @param searchContents
	 *            查询条件
	 * @param minimumShouldMatch
	 *            匹配度
	 * @param from
	 * @param size
	 * @param sortOption
	 *            排序设置 <field,asc|desc>
	 * @param highlightOption
	 *            搜索结果高亮设置
	 * @param entityClass
	 * @return
	 */
	<T> List<T> complexSearch(String indexName, String typeName, QueryBuilder queryBuilder, int from, int size,
			Map<String, String> sortOption, HighlightOption highlightOption, Class<T> entityClass);

	/**
	 * 求记录总数
	 * 
	 * @param indexName
	 * @param typeName
	 * @param queryBuilder
	 * @return
	 */
	long complexCount(String indexName, String typeName, QueryBuilder queryBuilder);

	/**
	 * 聚合查询
	 * 
	 * @param indexName
	 * @param typeName
	 * @param queryBuilder
	 * @param aggsOption
	 * @return
	 */
	AggsResult aggs(String indexName, String typeName, QueryBuilder queryBuilder, AggsOption aggsOption);

	/**
	 * 多重聚合查询
	 * 
	 * @param indexName
	 * @param typeName
	 * @param queryBuilder
	 * @param aggsOptions
	 * @return
	 */
	List<AggsResult> aggs(String indexName, String typeName, QueryBuilder queryBuilder, AggsOption[] aggsOptions);

}

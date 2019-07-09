package net.chinahrd.eis.search.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import net.chinahrd.utils.JsonKit;
/**
 * 结果高亮映射器
 * 要求结果实体有highlightedContent属性
 * 如果设置了多个字段，则是多个字段内容组合而成。
 * 
 * @author bright
 *
 */
public class HighlightMapper implements SearchResultMapper {
	private final static Logger logger = LoggerFactory.getLogger(HighlightMapper.class);

	@Override
	public <T> Page<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
		long totalHits = response.getHits().totalHits();
		List<T> results = new ArrayList<T>();
		for (SearchHit hit : response.getHits()) {
			if (hit != null) {
				T result = null;
				if (StringUtils.isNotBlank(hit.sourceAsString())) {
					result = JsonKit.fromJson(hit.sourceAsString(), clazz);
				} else {
					result = JsonKit.fromJson(buildJSONFromFields(hit.getFields().values()), clazz);
				}

				StringBuilder highlightStr = new StringBuilder();
				for (Entry<String, HighlightField> entry : hit.highlightFields().entrySet()) {
					for (Text text : entry.getValue().fragments()) {
						highlightStr.append(text.string());
					}
					highlightStr.append("<br />");
				}

				try {
					PropertyUtils.setProperty(result, "highlightedContent", highlightStr.toString());
				} catch (Exception e) {
					logger.error(e.getMessage());
				}

				results.add(result);
			}
		}
		return new PageImpl<T>(results, pageable, totalHits);
	}

	private String buildJSONFromFields(Collection<SearchHitField> values) {
		JsonFactory nodeFactory = new JsonFactory();
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			JsonGenerator generator = nodeFactory.createGenerator(stream, JsonEncoding.UTF8);
			generator.writeStartObject();
			for (SearchHitField value : values) {
				if (value.getValues().size() > 1) {
					generator.writeArrayFieldStart(value.getName());
					for (Object val : value.getValues()) {
						generator.writeObject(val);
					}
					generator.writeEndArray();
				} else {
					generator.writeObjectField(value.getName(), value.getValue());
				}
			}
			generator.writeEndObject();
			generator.flush();
			return new String(stream.toByteArray(), Charset.forName("UTF-8"));
		} catch (IOException e) {
			return null;
		}
	}

}

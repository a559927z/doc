package net.chinahrd.eis.search.aggs;

import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;

/**
 * @Description DateHistogram 聚集类型附加参数包装类
 * @author bright   
 * @date 2014-5-29 下午3:58:17 
 * @version V2.0   
 *
 */
public class DateHistogramPack {
	private String min;
	private String max;
	private String format = "yyyy-MM-dd";
	private DateHistogramInterval interval;
	
	private DateHistogramPack(DateHistogramInterval interval, String min, String max) {
		this.interval = interval;
		this.min = min;
		this.max = max;
	}
	
	public static DateHistogramPack of(DateHistogramInterval interval, String min, String max) {
		return new DateHistogramPack(interval, min, max);
	}

	public String getMin() {
		return min;
	}

	public String getMax() {
		return max;
	}

	public DateHistogramInterval getInterval() {
		return interval;
	}

	public String getFormat() {
		return format;
	}

	public DateHistogramPack format(String format) {
		this.format = format;
		return this;
	}
	
	
	
}

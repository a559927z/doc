package net.chinahrd.eis.search.aggs;

/**
 * @Description 聚合类型
 * @author bright   
 * @date 2014-5-28 下午12:38:11 
 * @version V2.0   
 *
 */
public enum AggsType {
	global("全局"),
	value_count("值计数"),
	avg("平均值"),
	max("最大值"),
	min("最小值"),
	sum("求和"),
	stats("统计"),
	extended_stats("扩展统计"),
	filter("过滤"),
	date_histogram("日期直方图"),
	histogram("直方图"),
	nested("嵌套"),
	terms("分组统计");
	
	private final String label;
	
	private AggsType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
	
}

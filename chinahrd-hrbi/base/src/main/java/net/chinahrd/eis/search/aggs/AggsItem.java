package net.chinahrd.eis.search.aggs;

import java.util.List;

import net.chinahrd.utils.CollectionKit;

/**
 * @Description 聚合结果项
 * @author bright   
 *
 */
public class AggsItem {
	private String key;
	private String value;
	private AggsType aggsType;
	private Object option;
	private List<AggsResult> aggsResults = CollectionKit.newList();
	
	private AggsItem(String key, String value, AggsType aggsType, Object option) {
		this.key = key;
		this.value = value;	
		this.aggsType = aggsType;
		this.option = option;
	}
	
	public static AggsItem of(String key, String value, AggsType aggsType) {
		return new AggsItem(key, value, aggsType, null);
	}
	
	public static AggsItem of(String key, String value, AggsType aggsType, Object option) {
		return new AggsItem(key, value, aggsType, option);
	}
	
	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public List<AggsResult> getAggsResult() {
		return aggsResults;
	}

	public AggsItem addAggsResult(AggsResult aggsResult) {
		this.aggsResults.add(aggsResult);
		return this;
	}
	
	public AggsType getAggsType() {
		return aggsType;
	}

	public Object getOption() {
		return option;
	}

	@Override
	public String toString() {
		return aggsType + "{" + key + ":" + value + ", [" + aggsResults + "]}";
	}
}

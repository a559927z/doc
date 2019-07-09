package net.chinahrd.eis.search.aggs;

import java.util.Collections;
import java.util.List;

import net.chinahrd.eis.search.EsUtil;
import net.chinahrd.utils.AssertKit;
import net.chinahrd.utils.CollectionKit;

/**
 * @Description 聚合选项
 * @author bright   
 *
 */
public class AggsOption {
	private String name;
	private AggsType type;
	private String field;
	private Object option;
	private List<AggsOption> subAggsOptions = CollectionKit.newList();
	
	private AggsOption(String field, AggsType type) {
		AssertKit.notNull(field);
		AssertKit.notNull(type);
		
		this.field = field;
		this.type = type;
		this.name = field + "_" + type.name();
	}
	
	public static AggsOption of(String field, AggsType type) {
		return new AggsOption(field, type);
	}
		
	public String getName() {
		return name;
	}
	
	public AggsType getType() {
		return type;
	}
	
	public String getField() {
		return field;
	}

	public Object getOption() {
		return option;
	}

	public AggsOption option(Object option) {
		this.option = option;
		return this;
	}

	public List<AggsOption> getSubAggsOptions() {
		return Collections.unmodifiableList(subAggsOptions);
	}

	public AggsOption addSubAggsOption(AggsOption subAggsOption) {
		if (subAggsOption.getType() == AggsType.global) {
			throw new RuntimeException("Global aggregators can only be placed as top level aggregators");
		}
		
		if (EsUtil.isMetricsType(type)) {
			throw new RuntimeException("Metrics aggregators can only be placed as bottom level aggregators");
		}
		
		this.subAggsOptions.add(subAggsOption);
		return this;
	}
}

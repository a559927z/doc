package net.chinahrd.eis.search.aggs;

import java.util.List;

import net.chinahrd.utils.CollectionKit;

/**
 * @Description 聚合结果
 * @author bright   
 *
 */
public class AggsResult {
	private String aggName;
	private List<AggsItem> items = CollectionKit.newList();
	
	public String getAggName() {
		return aggName;
	}

	public void setAggName(String aggName) {
		this.aggName = aggName;
	}

	public List<AggsItem> getItems() {
		return items;
	}

	public void addItem(AggsItem item) {
		items.add(item);
	}

	@Override
	public String toString() {
		return aggName + "[" + items + "]";
	}
	
	
}

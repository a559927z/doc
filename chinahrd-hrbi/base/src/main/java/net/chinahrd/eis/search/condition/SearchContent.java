package net.chinahrd.eis.search.condition;

/**
 * @Description 搜索内容封装
 * @author bright   
 *
 */
public class SearchContent {
	private String field;
	private Object[] values;
	
	private SearchLogic searchLogic = SearchLogic.must;
	private SearchKind searchKind = SearchKind.term;
	
	private String queryStringPrecision = "100%";
	/* 排名权重 */
	private float boost = 1.0f;
	
	public SearchContent(String field, Object[] values) {
		this.field = field;
		this.values = values;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Object[] getValues() {
		return values;
	}
	public void setValues(Object[] values) {
		this.values = values;
	}
	public float getBoost() {
		return this.boost;
	}

	public SearchContent setBoost(float boost) {
		this.boost = boost;
		return this;
	}

	public SearchLogic getSearchLogic() {
		return this.searchLogic;
	}

	public SearchContent setSearchLogic(SearchLogic searchLogic) {
		this.searchLogic = searchLogic;
		return this;
	}

	public SearchKind getSearchKind() {
		return this.searchKind;
	}

	public SearchContent setSearchKind(SearchKind searchKind) {
		this.searchKind = searchKind;
		return this;
	}

	public String getQueryStringPrecision() {
		return this.queryStringPrecision;
	}

	public SearchContent setQueryStringPrecision(String queryStringPrecision) {
		this.queryStringPrecision = queryStringPrecision;
		return this;
	}

}

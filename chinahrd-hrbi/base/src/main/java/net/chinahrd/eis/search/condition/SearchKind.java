package net.chinahrd.eis.search.condition;

public enum SearchKind {
	/* 按照quert_string搜索，搜索非词组时候使用 */
	querystring
	/* 按照区间搜索 */
	, range
	/* 按照词组搜索，搜索一个词时候使用 */
	, term
	
	/* 按照词组搜索，任何一个词匹配时候使用 */
	, terms
	/* nested搜索 */
	, nested
}
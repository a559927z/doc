package net.chinahrd.eis.pagination;

public class SqlServer2005Dialect extends Dialect {
	public boolean supportLimit() {
		return true;
	}

	public boolean supportLimitOffset() {
		return true;
	}

	@Override
	protected String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		
		StringBuffer pagingBuilder = new StringBuffer();
		String orderby = getOrderByPart(sql);
		String distinctStr = "";

		String loweredString = sql.toLowerCase();
		String sqlPartString = sql;
		if (loweredString.trim().startsWith("select")) {
			int index = 6;
			if (loweredString.startsWith("select distinct")) {
				distinctStr = "DISTINCT ";
				index = 15;
			}
			sqlPartString = sqlPartString.substring(index);
		}
		pagingBuilder.append(sqlPartString);

		// if no ORDER BY is specified use fake ORDER BY field to avoid errors
		if (orderby == null || orderby.length() == 0) {
			orderby = "ORDER BY CURRENT_TIMESTAMP";
		}

		StringBuffer result = new StringBuffer();
		String endString = Integer.toString(offset + limit);
		result.append("WITH query AS (SELECT ")
				.append(distinctStr)
				.append("TOP 100 PERCENT ")
				.append(" ROW_NUMBER() OVER (")
				.append(orderby)
				.append(") as __row_number__, ")
				.append(pagingBuilder)
				.append(") SELECT * FROM query WHERE __row_number__ > " + offsetPlaceholder + " AND __row_number__ <= " + endString)
				.append(" ORDER BY __row_number__");
		return result.toString();
	}
	
	static String getOrderByPart(String sql) {
		String loweredString = sql.toLowerCase();
		int orderByIndex = loweredString.indexOf("order by");
		if (orderByIndex != -1) {
			// if we find a new "order by" then we need to ignore
			// the previous one since it was probably used for a subquery
			return sql.substring(orderByIndex);
		} else {
			return "";
		}
	}
}

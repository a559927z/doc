package net.chinahrd.eis.pagination;

public class MySQLDialect extends Dialect {
	public boolean supportLimit() {
		return true;
	}
	
	public boolean supportLimitOffset() {
		return true;
	}
	
	@Override
	protected String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		if (offset > 0) {
			return sql + " limit " + offsetPlaceholder + "," + limitPlaceholder;
		} else {
			return sql + " limit " + limitPlaceholder;
		}
	}
}

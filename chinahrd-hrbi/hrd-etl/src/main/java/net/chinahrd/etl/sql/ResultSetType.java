/**
*net.chinahrd.etl.sql
*/
package net.chinahrd.etl.sql;

/**
 * @author htpeng
 *2017年4月25日下午3:51:00
 */
public enum ResultSetType {
	STRING("getString",String.class),
	INT("getInt",Integer.class);
	private String value;
	private Class<?> clazz;
	/**
	 * 
	 */
	ResultSetType(String value,Class<?> clazz) {
		this.value=value;
		this.clazz=clazz;
	}
	
//	public static ResultSetType getResultSetTypeByValue(String value){
//		if(null==value)return null;
//		for(ResultSetType resultSetType:ResultSetType.values()){
//			if(resultSetType.value.equals(value)){
//				return resultSetType;
//			}
//		}
//		return null;
//	}

	public static ResultSetType getTypeByClass(Class<?> clazz) {
		if (null == clazz)
			return null;
		for (ResultSetType resultSetType : ResultSetType.values()) {
			if (resultSetType.clazz.equals(clazz)) {
				return resultSetType;
			}
		}
	return null;
}
	
	/**
	 * @return
	 */
	public String getValue() {
		return this.value;
	}
}

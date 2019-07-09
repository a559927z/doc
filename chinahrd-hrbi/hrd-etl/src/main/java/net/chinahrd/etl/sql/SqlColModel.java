package net.chinahrd.etl.sql;

/**
 *查询sql 的列 对应的模型
 * @author htpeng
 *2017年4月24日下午6:25:28
 */
public class SqlColModel{
	private String name = ""; // 列名
	private String methodName = "";
	
	private String type = "";
	
	private CustomColumn<?,?> customColumn=null;
	
	/**
	 * 该列是否查询
	 */
	private boolean isQuery;
	/**
	 * @param name2
	 * @param methodName2
	 */
	public SqlColModel(String name, String methodName,final String type,boolean isQuery,CustomColumn<?,?>...cols) {
		this.name=name;
		this.methodName=methodName;
		this.type=type;
		this.isQuery=isQuery;
		if(null!=cols&&cols.length>0){
			customColumn =cols[0];
		}
	}
	/**
	 * 获取列名
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取方法名
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		if(null==customColumn){
			return type;
		}
		
		return customColumn.getResultSetType().getValue();

	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	} 

	public Object formatData (Object obj){
		if(null==customColumn){
			return obj;
		}
		return customColumn.formatData(obj);
	}
	/**
	 * @return the isQuery
	 */
	public boolean isQuery() {
		return isQuery;
	}
	
	
}

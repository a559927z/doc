/**
*net.chinahrd.etl
*/
package net.chinahrd.etl.sql;

import java.util.List;

import net.chinahrd.utils.CollectionKit;

/**sql 辅助接口
 * @author htpeng
 *2017年4月24日下午5:52:28
 */
public  class AbstractSqlAuxiliary implements SqlAuxiliary {
	private List<SqlColModel> list =CollectionKit.newList();
	private StringBuffer sql=new StringBuffer("SELECT ");
	private int colNum=0;
	@Override
	public String getSql() {
		return sql.toString();
	}

	@Override
	public List<SqlColModel> getSqlCol() {
		return list;
	}
	
	/**
	 * 设置标准查询  列信息
	 * @param colname   原表中数据库字段名    (example : name)
	 * @param asName	查询sql中列别名          (example : `name`)
	 * @param name      传入的具体数据库字段名   (example : t.c_name)
	 * @param methodName   实体类中 set方法名   
	 * @param type         字段类型
	 * @param cols         自定义字段配置
	 */
	protected void setColName(String colname,String asName,String name,String methodName,String type,CustomColumn<?,?>...cols){
		if(null==name){
			list.add(new SqlColModel(colname,methodName,type,false,cols));
			return;
		}else{
			list.add(new SqlColModel(colname,methodName,type,true,cols));
		}
		
		if(colNum>0){
			sql.append(",");
		}
		sql.append(name);
		sql.append(" AS ");
		sql.append(asName);
		colNum++;
	}
	
	public SqlAuxiliary setSqlBody(String body){
		sql.append(" FROM ");
		sql.append(body);
		return this;
	}
}

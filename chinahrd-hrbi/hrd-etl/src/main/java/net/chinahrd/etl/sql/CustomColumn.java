package net.chinahrd.etl.sql;

/**
 * <p>自定义列的类型和格式化</p>
 *@param <K>  数据查询出来的格式
 *@param <T> 表示格式化完成后的类型
 * @author htpeng
 *2017年4月24日下午6:25:28
 */
public interface CustomColumn<K,T>{
	/**
	 * 设置ResultSet获取列的值的类型
	 * @return
	 */
	ResultSetType getResultSetType();
	
	/**
	 * 格式化数据
	 * @param obj
	 * @return
	 */
	T formatData(Object obj);
}

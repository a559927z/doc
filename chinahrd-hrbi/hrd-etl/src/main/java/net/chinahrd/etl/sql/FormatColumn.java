package net.chinahrd.etl.sql;

import java.lang.reflect.ParameterizedType;

/**
 * 自定义列的格式化方式
 *@param <K>  数据查询出来的格式
 *@param <T> 表示格式化完成后的类型
 * @author htpeng
 *2017年4月24日下午6:25:28
 */
public abstract class FormatColumn<K,T> implements CustomColumn<K,T>{

	private Class<?> clazz;
	
	public FormatColumn() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<?>) pt.getActualTypeArguments()[0];
	}
	@Override
	public ResultSetType getResultSetType() {
		return ResultSetType.getTypeByClass(clazz);
	}

	
	@Override
	public abstract T formatData(Object obj);

}

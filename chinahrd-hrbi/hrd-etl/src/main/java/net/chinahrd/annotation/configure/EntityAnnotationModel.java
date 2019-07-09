/**
*net.chinahrd.annotation
*/
package net.chinahrd.annotation.configure;

import java.util.ArrayList;
import java.util.List;

import net.chinahrd.annotation.Column;
import net.chinahrd.annotation.Table;

/**
 * 实体类模型
 * 
 * @author htpeng 2017年4月13日上午11:06:20
 */
public class EntityAnnotationModel {
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 表列集合
	 */
	private List<ColumnModel> colList = new ArrayList<ColumnModel>();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<ColumnModel> getColList() {
		return colList;
	}

	public void setColList(List<ColumnModel> colList) {
		this.colList = colList;
	}

	public void setColList(String methodName, Column column) {
		this.colList.add(new ColumnModel(methodName, column));
	}

	public void setTable(Table annotation) {
		this.tableName = annotation.name();
	}
}

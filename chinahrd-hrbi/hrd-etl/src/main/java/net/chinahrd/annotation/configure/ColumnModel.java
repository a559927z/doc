package net.chinahrd.annotation.configure;

import net.chinahrd.annotation.Column;
import net.chinahrd.utils.version.configure.ColumnType;


public class ColumnModel{
	private ColumnType type; // 数据类型 （实体类中）
	private String colname = ""; // 列名
	private long length = -1; // 长度
	private int prec = 0; // 整数位
	private int scale = 0; // 小数位
	private String defaultValue ; // 默认值
	private String isNULLABLE = "";
	private String methodName = "";

	// private ForeignKeyModel foreignKey=null;
	public ColumnModel(String methodName,Column column) {
		this.colname = column.name();
		this.type = column.type();
		this.methodName=methodName;
		this.defaultValue=column.defaultValue();
	}


	public String getMethodName() {
		return methodName;
	}



	public String isNULLABLE() {
		return isNULLABLE.equals("YES")?" NULL":" NOT NULL";
	}




	public ColumnModel() {

	}



	public void getUpdateVlalue(StringBuffer str) {
		str.append("," + colname + "=?");
	}

	public String getColname() {
		return colname;
	}

	public void setColname(String colname) {
		this.colname = colname;
		getMethodName();
	}

//	private void getMethodName(){
//		this.getMethodName = Util.getGMethodNameByName(colname);
//	}

	public long getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPrec() {
		return prec;
	}

	public void setPrec(int prec) {
		this.prec = prec;
	}

	public int getScale() {
		return scale;
	}


	/**
	 * @return the type
	 */
	public ColumnType getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(ColumnType type) {
		this.type = type;
	}

	
	public String getValue(Object value){
		if(null==value){
			return null;
		}
		if(type.getType().equals("String")){
			return "'"+value+"'";
		}
		return value.toString();
		
	}
}

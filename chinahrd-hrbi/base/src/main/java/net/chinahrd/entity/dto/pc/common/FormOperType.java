package net.chinahrd.entity.dto.pc.common;

/**
 * jqgrid的操作类型枚举（新增、修改、删除）
 * @author jayu
 *
 */
public enum FormOperType {
	//新增
	ADD("add"),
	//修改
	EDIT("edit"),
	//删除
	DEL("del");
	private String oper;
	private FormOperType(String oper) {
		this.oper = oper;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}
	
}

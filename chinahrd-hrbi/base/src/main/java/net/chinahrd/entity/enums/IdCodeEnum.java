package net.chinahrd.entity.enums;

/**
 * 特殊情况固定ID枚举类</br>
 * 公共使用
 * 
 * @author jxzhang on 2017年2月14日
 * @Verdion 1.0 版本
 */
public enum IdCodeEnum {

	/**
	 * 超级管理员用户ID
	 */
	SUPERADMIN_USERID_ZRW(1, "6264849c52fa11e6902390b11c32f63c", "userId"), 
	
	/**
	 * 考勤类型
	 */
	CHECKWORK_NORMAL_ZRW(1, "b90bb95e3c01413b80899b49ba13392e", "dim_checkwork_type-正常出勤"), 
	CHECKWORK_OT_ZRW(5, "7f533380bf574dd9916972a08808e121", "dim_checkwork_type-加班"), 
	CHECKWORK_ANNUAL_ZRW(6, "c5b620905b444ffa82dda81fbf99d389", "dim_checkwork_type-年假"), 
	CHECKWORK_CANLEAVE_ZRW(9, "8679d6eea0a911e6899290b11c32f63c", "dim_checkwork_type-调休"),
	;

	private Integer code; // 编码
	private String status; // 状态
	private String info; // 描述

	private IdCodeEnum(Integer code, String status, String info) {
		this.code = code;
		this.status = status;
		this.info = info;
	}

	public Integer getCode() {
		return code;
	}

	public String getStatus() {
		return status;
	}

	public String getInfo() {
		return info;
	}


}

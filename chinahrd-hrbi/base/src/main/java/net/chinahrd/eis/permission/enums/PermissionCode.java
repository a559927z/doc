package net.chinahrd.eis.permission.enums;

/**
 * 权限类型统一匹配符 （角色、功能、功能动作）
 */
public enum PermissionCode {

	ALL_FUNCTION("*"), // "所有功能模块"
	ALL_ACTION("*"), // "所有功能动作"

	/**
	 * 角色
	 */
	SUPER_ADMIN("superAdmin"), // "超级管理员Key"
	
	ADMIN("admin"), // "管理员"
	USER("user"), // "用户"
	GUEST("guest"), // "来宾"
	
	/**
	 * 超级管理员UUID
	 */
	SUPER_ADMIN_ID(""),// "超级管理员id" bc10d0cf3b1c11e5b2cb08606e0aa89a
	SUPER_ADMIN_KEY("superAdmin"),
	
	/**
	 *  功能模块
	 */
	YUAN_DONG_LI("yuan_dong_li"), //元动力
	
	/**
	 *  功能动作
	 */
	NO_PERMISSION("noPremission"), // 没有权限
	NO_ACTION("noAction"); // 没有功能动作

	private final String value;

	private PermissionCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}

package net.chinahrd.eis.permission.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.chinahrd.entity.dto.app.OrganTreeDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.admin.ProjectRelationDto;
import net.chinahrd.utils.CollectionKit;

/**
 * 系统登录人，封装shiro.subject对象
 * 
 * @author jxzhang on 2016年12月8日
 * @Verdion 1.2 版本
 */
public class RbacUser implements Serializable {

	private static final long serialVersionUID = 215776572118582878L;

	private String userId;
	private String empId;
	private String username;
	private String userNameCh;
	private String customerId;
	private String userKey;
	private String password;
	private String note;
	private int sysDeploy;
	private String createUserId;
	private String modifyUserId;
	private Timestamp createTime;
	private Timestamp modifyTime;

	private Set<String> shiroRolesKey;
	private Set<String> shiroPermissions;

	public Set<String> getShiroRolesKey() {
		return shiroRolesKey;
	}

	public void setShiroRolesKey(Set<String> shiroRolesKey) {
		this.shiroRolesKey = shiroRolesKey;
	}

	public Set<String> getShiroPermissions() {
		return shiroPermissions;
	}

	public void setShiroPermissions(Set<String> shiroPermissions) {
		this.shiroPermissions = shiroPermissions;
	}

	/**
	 * @see net.chinahrd.eis.permission.support.ShiroAuthenticationFilter.
	 *      doInsertLoginLog
	 */
	private String ipAddress; // 登录IP地址
	/**
	 * @see net.chinahrd.eis.permission.support.ShiroAuthenticationFilter.
	 *      doInsertLoginLog
	 */
	private Timestamp loginTime; // 登录时间

	private List<OrganDto> organAllStatus; // 所有机构包括全勾和半勾（注：不能当角色拥有数据权限使用）

	/**
	 * <p>
	 * 	假如有A11和A22
	 * 你的顶级机构A1、A2</p>
	 *
	 * A 				</ br>
	 * 	| -A1			</ br>
	 * 	|	| -A11		</ br>
	 *  |	| -A12		</ br>
	 * 	| -A2			</ br>
	 * 	|	| -A21		</ br>
	 *  |	| -A22		</ br>
	 *  
	 */
	private List<OrganDto> organPermitTop; // 用户拥有顶级数据权限
	/**
	 * 所有数据权限
	 */
	private List<OrganDto> organPermit; // 用户拥有数据权限
	/**
	 * 所有角色
	 */
	private List<RbacRole> rbacRoles;
	/**
	 * 所有功能
	 */
	private List<RbacFunction> rbacFunctions;

	private Map<Object, Object> cache = CollectionKit.newMap(); // 用于移动端 缓存

	private List<OrganTreeDto> mobileTree; // 用于移动端 机构树结构
	
	private List<ProjectRelationDto> projectAllStatus; // 所有机构包括全勾和半勾（注：不能当角色拥有数据权限使用）
	
	private List<String> empInfoList; // 获取登录用户的项目下的所有员工
	public List<RbacRole> getRbacRoles() {
		return rbacRoles;
	}

	public void setRbacRoles(List<RbacRole> rbacRoles) {
		this.rbacRoles = rbacRoles;
	}

	public List<RbacFunction> getRbacFunctions() {
		return rbacFunctions;
	}

	public void setRbacFunctions(List<RbacFunction> rbacFunctions) {
		this.rbacFunctions = rbacFunctions;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Object getCache(Object key) {
		return cache.get(key);
	}

	public void setCache(Object key, Object value) {
		this.cache.put(key, value);
	}

	public List<OrganDto> getOrganPermitTop() {
		return organPermitTop;
	}

	public void setOrganPermitTop(List<OrganDto> organPermitTop) {
		this.organPermitTop = organPermitTop;
	}

	public List<OrganDto> getOrganAllStatus() {
		return organAllStatus;
	}

	public void setOrganAllStatus(List<OrganDto> organAllStatus) {
		this.organAllStatus = organAllStatus;
	}

	public List<OrganDto> getOrganPermit() {
		return organPermit;
	}

	public void setOrganPermit(List<OrganDto> organPermit) {
		this.organPermit = organPermit;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getUserNameCh() {
		return userNameCh;
	}

	public void setUserNameCh(String userNameCh) {
		this.userNameCh = userNameCh;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public int getSysDeploy() {
		return sysDeploy;
	}

	public void setSysDeploy(int sysDeploy) {
		this.sysDeploy = sysDeploy;
	}

	public List<OrganTreeDto> getMobileTree() {
		return mobileTree;
	}

	public void setMobileTree(List<OrganTreeDto> mobileTree) {
		this.mobileTree = mobileTree;
	}

	public List<ProjectRelationDto> getProjectAllStatus() {
		return projectAllStatus;
	}

	public void setProjectAllStatus(List<ProjectRelationDto> projectAllStatus) {
		this.projectAllStatus = projectAllStatus;
	}

	public List<String> getEmpInfoList() {
		return empInfoList;
	}

	public void setEmpInfoList(List<String> empInfoList) {
		this.empInfoList = empInfoList;
	}

}

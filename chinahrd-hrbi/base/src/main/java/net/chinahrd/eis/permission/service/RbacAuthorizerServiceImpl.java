package net.chinahrd.eis.permission.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.dao.RbacAuthorizerDao;
import net.chinahrd.eis.permission.enums.AuthenticationCode;
import net.chinahrd.eis.permission.enums.PermissionCode;
import net.chinahrd.eis.permission.model.AuthenticationResult;
import net.chinahrd.eis.permission.model.RbacFunction;
import net.chinahrd.eis.permission.model.RbacRole;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.admin.ProjectRelationDto;
import net.chinahrd.mvc.pc.service.admin.EmpProjectRelationService;
import net.chinahrd.mvc.pc.service.admin.OrganService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.PropertiesUtil;

/**
 * Shiro提供自定服务
 * 
 * @author jxzhang on 2016年12月9日
 * @Verdion 1.0 版本
 */
@Service("authorizerService")
public class RbacAuthorizerServiceImpl implements RbacAuthorizerService {

	private static Logger log = LoggerFactory.getLogger(RbacAuthorizerServiceImpl.class);

	@Autowired
	private RbacAuthorizerDao authorizerDao;

	@Autowired
	private OrganService organService;

	@Autowired
	private EmpProjectRelationService empProjectRelationService;

	@Override
	public AuthenticationResult authenticate(final String customerId, final String username, final String inputPwd) {

		String confPwd = PropertiesUtil.getProperty("user.password");

		String userkey = username; // 登录时用编码检查
		// String email = null;
		// if(StringUtils.contains(username, "@")){
		// email = username;
		// }

		// jxzhang by 2016-01-13
		Map<String, String> paramMap = CollectionKit.newMap();
		paramMap.put("customerId", customerId);
		paramMap.put("userKey", userkey);
		paramMap.put("inputPwd", inputPwd);
		if (StringUtils.equals(confPwd, inputPwd)) {
			List<RbacUser> userList = authorizerDao.checkUserExist(paramMap);
			if (userList.isEmpty()) {
				return new AuthenticationResult(AuthenticationCode.USER_NOT_FOUND);
			}
			RbacUser rbacUser = userList.get(0);
			return newAuthentInstall(rbacUser);
		} else {
			// 组装明密
			String keyPwd = inputPwd + StringUtils.substring(userkey, 1, 2);
			// 对输入时加密
			String enPwd = Base64.encodeToString(keyPwd.getBytes());
			List<RbacUser> userList = authorizerDao.checkUserExist(paramMap);
			if (userList.isEmpty()) {
				return new AuthenticationResult(AuthenticationCode.USER_NOT_FOUND);
			}
			// 这里通过userKey + custmerId,查出来的必须是只有一个
			RbacUser rbacUser = userList.get(0);
			String dbPwd = userList.get(0).getPassword();
			// 对数据解密
			String decPass = Base64.decodeToString(dbPwd);
			if (StringUtils.equals(decPass, keyPwd)) {
				return newAuthentInstall(rbacUser);
			} else {
				return new AuthenticationResult(AuthenticationCode.USER_NOT_FOUND);
			}
		}
	}

	/**
	 * 拼装新的登录对象</br>
	 * (加入数据权限、功能权限、角色权限,添加登录记录)
	 * 
	 * @param rbacUser
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private AuthenticationResult newAuthentInstall(RbacUser rbacUser) {
		Map<String, Object> rsMap = getUserInfo(rbacUser.getCustomerId(), rbacUser.getUserId(), rbacUser.getEmpId(),
				rbacUser.getSysDeploy() == 1);

		if (null != rsMap && !rsMap.isEmpty()) {
			// 提供shrio.hasRole()使用, 用户的角色集合
			Set<String> roleSet = (Set<String>) rsMap.get("roleSet");
			// 提供shrio.isPermissions()使用, 用户的角色对应的所有功能权限
			Set<String> funSet = (Set<String>) rsMap.get("funSet");

			List<RbacRole> rbacRoles = (List<RbacRole>) rsMap.get("rbacRoles");
			List<RbacFunction> rbacFunctions = (List<RbacFunction>) rsMap.get("rbacFunctions");
			List<OrganDto> organAllStatus = (List<OrganDto>) rsMap.get("organAllStatus");
			List<ProjectRelationDto> projectAllStatus = (List<ProjectRelationDto>) rsMap.get("projectAllStatus");
			List<String> empInfoList = (List<String>) rsMap.get("empInfoList");
			if (null != roleSet) {
				rbacUser.setShiroRolesKey(roleSet);
			}
			if (null != funSet) {
				rbacUser.setShiroPermissions(funSet);
			}
			if (null != rbacRoles) {
				rbacUser.setRbacRoles(rbacRoles);
			}
			if (null != rbacFunctions) {
				rbacUser.setRbacFunctions(rbacFunctions);
			}
			if (null != organAllStatus) {
				packOrgans(rbacUser, organAllStatus);
			}
			if (null != projectAllStatus) {
				rbacUser.setProjectAllStatus(projectAllStatus);
			}
			if (null != empInfoList) {
				rbacUser.setEmpInfoList(empInfoList);
			}
		}
		return new AuthenticationResult(rbacUser);
	}

	@Override
	public Map<String, Object> getUserInfo(String customerId, String userId, String empId, boolean isSysDeploy) {

		Map<String, Object> rsMap = CollectionKit.newMap();

		// Organs @see chkboxType: {"Y": "ps", "N": "ps"}
		List<OrganDto> organAllStatus = organService.queryOrganPermit2(userId);
		rsMap.put("organAllStatus", organAllStatus);

		// List<ProjectRelationDto> projectAllStatus =
		// empProjectRelationService.queryProjectPermit(userId);
		// rsMap.put("projectAllStatus", projectAllStatus);
		//
		// List<String> empInfoList =
		// empProjectRelationService.queryEmpInfosByEmpid(empId);
		// rsMap.put("empInfoList", empInfoList);
		// superAdmin
		if (isSysDeploy) {
			rsMap.put("funSet", new HashSet<String>(Arrays.asList(PermissionCode.ALL_FUNCTION.getValue())));
			rsMap.put("roleSet", new HashSet<String>(Arrays.asList(PermissionCode.ALL_ACTION.getValue())));
			return rsMap;
		}

		// Roles
		List<RbacRole> roleList = authorizerDao.findRoleList(customerId, userId);
		if (roleList.isEmpty() || null == roleList) {
			rsMap.put("roleSet", new HashSet<String>(Arrays.asList(PermissionCode.GUEST.getValue())));
			return rsMap;
		}
		rsMap.put("rbacRoles", roleList);
		Set<String> roleKeys = CollectionKit.newSet();
		List<String> roleIds = CollectionKit.newList();
		for (RbacRole role : roleList) {
			roleIds.add(role.getRoleId());
			roleKeys.add(role.getRoleKey());
		}
		rsMap.put("roleSet", roleKeys);

		// Function
		List<RbacFunction> funList = authorizerDao.findRbacPermissionByRole(customerId, roleIds);
		if (funList.isEmpty() || null == funList) {
			rsMap.put("funSet", new HashSet<String>(Arrays.asList(PermissionCode.NO_PERMISSION.getValue())));
			return rsMap;
		}
		rsMap.put("rbacFunctions", funList);
		Set<String> permissionSet = CollectionKit.newSet();
		// Permissions
		for (RbacFunction per : funList) {
			String itemCodes = per.getItemCodes();
			if (null == itemCodes) {
				permissionSet.add(PermissionCode.NO_ACTION.getValue());
				continue;
			}
			String[] split = itemCodes.split(",");
			for (int i = 0; i < split.length; i++) {
				String stringFun = "";
				stringFun = per.getFullPath() + ":" + split[i];
				permissionSet.add(stringFun);
			}
		}
		rsMap.put("funSet", permissionSet);

		return rsMap;
	}

	/**
	 * 用户拥有机构 </br>
	 * organAllStatus ：所有机构状态
	 * </p>
	 * organPermit ： 应有机构
	 * </p>
	 * organPermitTop ：应用顶级机构
	 * </p>
	 * 
	 * @param rbacUser
	 *            ：组装入对象
	 * @param organAllStatus
	 *            ：提交数据库里当前用户所有机构
	 */
	private void packOrgans(RbacUser rbacUser, List<OrganDto> organAllStatus) {
		MultiValueMap<Integer, OrganDto> mapOrganPermit = this.getOrganPermit(organAllStatus);
		List<OrganDto> organPermit = CollectionKit.newList();

		for (Entry<Integer, List<OrganDto>> empEntry : mapOrganPermit.entrySet()) {
			// 为0是代表：配置机构数据时全勾状态，应为数据权限。
			// 为1是代表：配置机构数据时半勾状态，应不为数据权限。
			if (empEntry.getKey() == 0) {
				organPermit = empEntry.getValue();
			}
		}
		rbacUser.setOrganPermit(organPermit);
		rbacUser.setOrganAllStatus(organAllStatus);

		// List<OrganDto> organPermitTop =
		// organService.queryOrganPermitTop(rbacUser.getUserId(),
		// EisWebContext.getCustomerId());
		List<OrganDto> organPermitTop = CollectionKit.newList();
		boolean isTop = true;
		for (OrganDto dto1 : organPermit) {

			String pId = dto1.getOrganizationParentId();

			if (null == pId) {
				log.debug("你的dim_organization，的pId为空了");
				break;
			}
			for (OrganDto dto2 : organPermit) {
				String id = dto2.getOrganizationId();
				if (pId.equals(id)) {
					isTop = false;
					break;
				}
			}
			if (isTop) {
				organPermitTop.add(dto1);
			}
		}
		rbacUser.setOrganPermitTop(organPermitTop);
	}

	private MultiValueMap<Integer, OrganDto> getOrganPermit(List<OrganDto> list) {
		MultiValueMap<Integer, OrganDto> m = new LinkedMultiValueMap<Integer, OrganDto>();
		for (OrganDto dto : list) {
			m.add(dto.getHalfCheck(), dto);
		}
		return m;
	}

	@Override
	public boolean checkUserExist(String userKey, String email) {
		Map<String, String> paramMap = CollectionKit.newMap();
		paramMap.put("customerId", EisWebContext.getCustomerId());
		paramMap.put("userKey", userKey);
		paramMap.put("email", email);
		List<RbacUser> userList = authorizerDao.checkUserExist(paramMap);
		return CollectionKit.isEmpty(userList) ? false : true;
	}

	@Override
	public boolean initPassword(String userKey) {
		int r = authorizerDao.initPassword(EisWebContext.getCustomerId(), userKey,
				PropertiesUtil.getProperty("user.password"));
		if (r > 0) {
			return true;
		}
		return false;
	}

}

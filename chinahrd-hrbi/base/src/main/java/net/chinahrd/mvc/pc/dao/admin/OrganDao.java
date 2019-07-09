package net.chinahrd.mvc.pc.dao.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.admin.RoleOrganizationDto;
import net.chinahrd.entity.dto.pc.admin.TreeDto;
import net.chinahrd.entity.dto.pc.admin.UserDto;
import net.chinahrd.entity.dto.pc.admin.UserOrganizationDto;
import net.chinahrd.entity.dto.pc.common.OrganManagerDto;

/**
 * Created by jxzhang on 15/6/19.
 */
@Repository("organDao")
public interface OrganDao {

	
	/*==============================================================*/
	/*  配置页面使用-配置数据权限							            */
	/*==============================================================*/
	/**
	 * 查出superAdmin的ID
	 * @param userKey
	 * @return
	 */
	String findSuperAdminIdByUserKey(String userKey);
	/**
	 * 查询当前登录人所有数据权限
	 * 
	 * @param userId
	 * @param customerId
	 *            TODO
	 * @return （带出全勾和半勾选的机构）
	 */
	List<OrganDto> queryOrganPermit(@Param("userId") String userId,
			@Param("customerId") String customerId);

	/**
	 * 根据机构ID查询相关机构信息
	 * @param organId
	 * @param customerId
	 * @return
	 */
	OrganDto findOrganById(@Param("organId") String organId,  @Param("customerId") String customerId);
	
	/**
	 * 查询superAdmin所有数据权 
	 * 
	 * @param userId
	 * @param customerId
	 *            TODO
	 * @return （带出全勾和半勾选的机构）
	 */
	List<OrganDto> queryOrganPermitBySuperAdmin(@Param("customerId") String customerId);

	/**
	 * 每个指标里的机构树
	 * @param userId
	 * @param customerId
	 * @param topOne
	 *            true:只查询第一条；false：查询所有
	 * @return
	 */
	List<TreeDto> queryOrganTree(@Param("userId") String userId,
			@Param("customerId") String customerId
			);

	/**
	 * 	isOrganPermit==true 角色下所有数据权限 </br>
	 *  isOrganPermit==false 角色下所有带状态数据,用作机构树配置半勾情况
	 * 
	 * @param roleId
	 * @param customerId
	 *            TODO
	 * @param isOrganPermit
	 *            TODO
	 * @return
	 */
	List<OrganDto> queryRoleOrgans(@Param("roleId") String roleId,
			@Param("customerId") String customerId,
			@Param("isOrganPermit") Boolean isOrganPermit);

	
	
	/**
	 * 根据用户ID查询用户组织架构权限
	 *
	 * 	isOrganPermit==true 用户下所有数据权限 </br>
	 *  isOrganPermit==false 用户下所有带状态数据,用作机构树配置半勾情况
	 *
	 * @param userId
	 * @param customerId
	 *            TODO
	 * @param isOrganPermit
	 *            TODO
	 * @return
	 */
	List<OrganDto> queryUserOrgans(@Param("userId")String userId, @Param("customerId")String customerId,
								   @Param("isOrganPermit")boolean isOrganPermit);
	/**
	 * 删除用户数据权限信息
	 *
	 * @param userId
	 * @param customerId
	 */
	void deleteUserOrganization(@Param("userId")String userId, @Param("customerId")String customerId);
	/**
	 * 添加用户数据权限信息
	 *
	 * @param dtos
	 */
	void addUserOrganization(@Param("dtos")List<UserOrganizationDto> dtos);

	
	
	
	

	/**
	 * 删除角色功能权限信息
	 * 
	 * @param roleId
	 */
	void deleteRoleOrganization(@Param("roleId") String roleId,
			@Param("customerId") String customerId);

	/**
	 * 添加角色数据权限信息
	 * 
	 * @param dtos
	 */
	void addRoleOrganization(@Param("dtos") List<RoleOrganizationDto> dtos);

	/**
	 * 查询机构所拥用的员工
	 * 
	 * @param organizationId
	 * @return
	 */
	List<UserDto> queryEmpsByOrganId(@Param("customerId") String customerId,
			@Param("organizationId") String organizationId);

	
	/**
	 * 查找所有机构
	 * 
	 * @param customerId
	 * @param organKeyList
	 *            TODO
	 * @return
	 */
	List<OrganDto> queryOrganAll(@Param("customerId") String customerId,
			@Param("organKeyList") List<String> organKeyList);
	
	/**
	 * 通过父id查询子机构
	 * 
	 * @param parentId
	 * @param customerId
	 *            TODO
	 * @param isSingleOrgan
	 *            TODO
	 * @return
	 */
	List<TreeDto> querySubOrganAsync(@Param("parentId") String parentId,
			@Param("customerId") String customerId,
			@Param("isSingleOrgan") Boolean isSingleOrgan);

	List<TreeDto> queryOrganTreeBySuperAdmin(@Param("customerId") String customerId);

	
	
	
	/*==============================================================*/
	/*  指标页面使用-机构树选择							                */
	/*==============================================================*/
	
//	/**
//	 * 查询当前部门在职员工数，不包括子部门  add by zgj 20151130
//	 * @param organizationId
//	 * @return
//	 */
	/**
	 * 查询当前和下属部门在职员工数，包括子部门  add by htpeng 20160105
	 * @param organizationId
	 * @return
	 */
	Integer getEmpCountByOrganId(@Param("customerId") String customerId,@Param("organizationId") String organizationId);
	
	/***
	 * 根据条件查询相关机构及机构负责人信息
	 * @param customerId
	 * @return
	 */
	List<OrganManagerDto> queryOrganManagerLists(@Param("customerId") String customerId);
	
	/**
	 * 根据用户查询用户顶级机构 
	 * @param userId
	 * @param customerId
	 * @return
	 */
//	List<OrganDto> queryOrganPermitTop(@Param("userId") String userId, @Param("customerId") String customerId);
	
	/**
	 * 查询机构的直接机构列表   add by zgj 20160318
	 * @param customerId
	 * @param orgPid 组织机构父ID
	 * @param isShowSelf 是否需要查询自己出来
	 * @return
	 */
	List<OrganDto> queryDirectOrgans(@Param("customerId") String customerId,
			@Param("orgPid") String orgPid,@Param("isShowSelf") boolean isShowSelf);

	/**
	 * 通过pid查出所有子孙机构
	 * @param customerId
	 * @param orgPid
	 * @param isHideSelf	不包含自己
	 * @return
	 */
	List<String> queryOrgansByPid(@Param("customerId") String customerId,
			@Param("orgPid") String orgPid,@Param("isHideSelf") boolean isHideSelf);
	/**
	 * 按照条件查询集合
	 * @param org
	 * @return
	 */
	List<OrganDto> findList(@Param("org") OrganDto org);

	
}

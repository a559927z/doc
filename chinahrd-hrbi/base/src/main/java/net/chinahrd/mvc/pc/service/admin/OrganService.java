package net.chinahrd.mvc.pc.service.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.util.MultiValueMap;

import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.admin.TreeDto;
import net.chinahrd.entity.dto.pc.admin.UserDto;
import net.chinahrd.entity.dto.pc.common.OrganManagerDto;

/**
 * 数据信息Service Created by jxzhang on 15/6/23.
 */
public interface OrganService {

	
	/**
	 * 查询当前登录人所有数据权限
	 * 
	 * @param userId
	 * @return
	 */
	MultiValueMap<Integer, OrganDto> queryOrganPermit(String userId);

	/**
	 * 每个指标里的机构树
	 * @param userId
	 * @param customerId
	 * @param topOne
	 *            true:只查询第一条；false：查询所有
	 * @return
	 */
	List<TreeDto> queryOrganTree(String userId, String customerId,
			Boolean isSingleOrgan);
	
	/**
	 * 查询角色下所有数据权限
	 * 
	 * @param roleId
	 * @param customerId
	 *            TODO
	 * @param isOrganPermit
	 *            isOrganPermit==true 
	 *            	角色下所有数据权限 </br> 
	 *            isOrganPermit==false
	 *            	角色下所有带状态数据,用作机构树配置半勾情况
	 * @return
	 */
	List<OrganDto> queryRoleOrgans(String roleId, String customerId,
			Boolean isOrganPermit);

	OrganDto findOrganById(String organId,String customerId);
	

	/**
	 * 添加角色数据权限信息
	 * 
	 * @param roleId
	 *            TODO
	 * @param createUserId
	 *            TODO
	 * @param customerId
	 *            TODO
	 * @param organDtos
	 *            TODO
	 * @return TODO
	 */
	boolean addRoleOrganization(String roleId, String createUserId,
			String customerId, List<OrganDto> organDtos);

	/**
	 * 删除
	 * 
	 * @param roleId
	 * @param customerId
	 * @return
	 */
	boolean deleteRoleOrganization(String roleId, String customerId);

	/**
	 * 查询机构所拥用的员工
	 * 
	 * @param organizationId
	 * @return
	 */
	List<UserDto> queryEmpsByOrganId(String customerId,
			@Param("organizationId") String organizationId);

	
	/**
	 * @see机构树组件调用 数据库结构 --> zTree结构
	 * @param existOrgans
	 * @return
	 */
	List<TreeDto> dbToZtree(List<OrganDto> existOrgans);

	
	/**
	 * 查询当前部门在职员工数，不包括子部门 add by zgj 20151130
	 * @param organizationId
	 * @return
	 */
	Integer getEmpCountByOrganId(String customerId,String organizationId);
	
	/**
	 * 根据条件查询相关机构及机构负责人信息
	 * @param customerId
	 * @return
	 */
	List<OrganManagerDto> queryOrganManagerLists(String customerId);

	List<OrganDto> queryOrganPermit2(String userId);

	/**
	 * 根据用户查询用户顶级机构
	 * @param userId
	 * @return
	 */
//	List<OrganDto> queryOrganPermitTop(String userId, String customerId);
	
	
	
	
	/**
	 * 配置数据权限时使用-用户对机构-查询
	 * @param userId
	 * @param customerId
	 * @param b
	 * @return
	 */
	List<OrganDto> queryUserOrgans(String userId, String customerId, boolean b);
	/**
	 * 配置数据权限时使用-用户对机构-删除
	 * @param userId
	 * @param customerId
	 * @return
	 */
    boolean deleteUserOrganization(String userId, String customerId);
	/**
	 * 配置数据权限时使用-用户对机构-添加
	 * @param userId
	 * @param customerId
	 * @return
	 */
    boolean addUserOrganization( String userId,String createUserId, String customerId,
                                 List<OrganDto> organDtos);

    /**
	 * 查询机构的直接子机构列表 add by zgj 20160318
	 * @param customerId
	 * @param orgPid 组织机构父ID
	 * @param isShowSelf 是否需要查询自己出来
	 * @return
	 */
	List<OrganDto> queryDirectOrgans(String customerId,String orgPid,boolean isShowSelf);
	
	/**
	 * 通过pid查出所有子孙机构
	 * @param customerId
	 * @param orgPid
	 * @param isHideSelf	不包含自己
	 * @return
	 */
	public List<String> queryOrgansByPid(String orgPid, boolean isHideSelf);

	/**
	 * 根据条件查询集合
	 * @param org
	 * @return
	 */
	List<OrganDto> findList(OrganDto org);
	/**
	 * 查找所有的分公司（单位）
	 * @param org
	 * @return
	 */
	List<OrganDto> findCompanyList(OrganDto org);
    
}

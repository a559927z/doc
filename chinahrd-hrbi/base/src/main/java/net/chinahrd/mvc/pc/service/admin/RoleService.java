package net.chinahrd.mvc.pc.service.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.chinahrd.entity.dto.pc.admin.RoleDto;
import net.chinahrd.entity.dto.pc.admin.UserRoleDto;
import net.chinahrd.entity.dto.pc.common.ItemDto;
import net.chinahrd.entity.dto.PaginationDto;

/**
 * 角色Service接口类
 * Created by wqcai on 15/6/10.
 */
public interface RoleService {
    /**
     * 查询所有角色
     * @return
     */
	PaginationDto<RoleDto> findAll(String customerId,PaginationDto<RoleDto> pageDto);

    /**
     * 根据用户查询关联的角色信息
     * @param userId
     * @return
     */
    List<UserRoleDto> findRoleOfUser(String customerId,String userId);
    
    /**
     * 添加角色
     * @param roleDto
     * @return
     */
    boolean addRole(RoleDto roleDto);
    
    /**
     * 修改角色
     * @param roleDto
     * @return
     */
    boolean editRole(RoleDto roleDto);
    
    /**
     * 删除角色
     * @param roleId
     * @return
     */
    boolean deleteRole(String customerId,List<String> roleId);
    

    /**
     * 根据角色ID查询角色信息
     * @return
     */
    RoleDto findRoleById(String customerId,String roleId);

    /**
     * 添加用户角色信息
     * @param customerId
     * @param userId
     * @param roleIds
     * @param createUserId
     */
    void addUserRole(String customerId,String userId,String[] roleIds,String createUserId);

    /**
     * 查询当前登录人的所有角色ID
     *
     * @param customerId
     * @param userId
     * @return
     */
    List<ItemDto> queryRoleIdByUserId(String customerId, String userId);
    
	/**
	 * 查询角色下有没有员工	by jxzhang
	 * @param customerId
	 * @param roleIds
	 * @return
	 */
	int hasEmpByRoleId(@Param("customerId") String customerId, @Param("roleIds")  List<String> roleIds);
}

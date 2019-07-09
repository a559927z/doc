package net.chinahrd.mvc.pc.dao.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.pc.admin.RoleDto;
import net.chinahrd.entity.dto.pc.admin.UserRoleDto;
import net.chinahrd.entity.dto.pc.common.ItemDto;

/**
 * Created by wqcai on 15/6/10.
 */
@Repository("roleDao")
public interface RoleDao {

    /**
     * 查询角色的数据量
     *
     * @param customerId 客户ID
     * @return
     */
    int findAllCount(@Param("customerId") String customerId);

    /**
     * 分页查询角色的数据
     *
     * @param customerId 客户ID
     * @param rb         ibatis分页查询对象
     * @return
     */
    List<RoleDto> findAll(@Param("customerId") String customerId, RowBounds rb);

    /**
     * 根据角色ID查询角色信息
     *
     * @param customerId 客户ID
     * @param roleId     角色ID
     * @return
     */
    RoleDto findRoleById(@Param("customerId") String customerId, @Param("roleId") String roleId);

    /**
     * 根据用户查询关联的角色信息
     *
     * @param customerId 客户ID
     * @param userId     用户ID
     * @return
     */
    List<UserRoleDto> findRoleOfUser(@Param("customerId") String customerId, @Param("userId") String userId);

    /**
     * 添加角色
     *
     * @param roleDto
     * @return
     */
    void insertRole(RoleDto roleDto);

    /**
     * 修改角色
     *
     * @param roleDto
     * @return
     */
    void updateRole(RoleDto roleDto);

    /**
     * 删除角色
     *
     * @param customerId 客户ID
     * @param roleIds    角色ID集合
     * @return
     */
    void deleteRole(@Param("customerId") String customerId,
    		@Param("roleIds")  List<String> roleIds);

    /**
     * 添加用户角色信息（可批量）
     *
     * @param list       用户角色集合
     */
    void addUserRole(@Param("list") List<UserRoleDto> list);

    /**
     * 删除用户角色信息
     *
     * @param customerId 客户ID
     * @param userId     用户ID
     */
    void deleteUserRole(@Param("customerId") String customerId, @Param("userId") String userId);
    
	/**
	 * 查询当前登录人的所有角色ID
	 * @param userId
	 * @param customerId
	 * @return
	 */
	List<ItemDto> queryRoleIdByUserId(@Param("customerId") String customerId, @Param("userId") String userId);
	
	/**
	 * 查询角色下有没有员工	by jxzhang
	 * @param customerId
	 * @param roleIds
	 * @return
	 */
	int hasEmpByRoleId(@Param("customerId") String customerId, @Param("roleIds")  List<String> roleIds);
}

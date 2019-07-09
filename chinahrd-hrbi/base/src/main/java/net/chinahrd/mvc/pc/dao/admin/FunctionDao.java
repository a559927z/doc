package net.chinahrd.mvc.pc.dao.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.pc.admin.FunctionDto;
import net.chinahrd.entity.dto.pc.admin.FunctionItemDto;
import net.chinahrd.entity.dto.pc.admin.FunctionTreeDto;
import net.chinahrd.entity.dto.pc.admin.RoleFunctionDto;
import net.chinahrd.entity.dto.pc.manage.HomeConfigDto;

/**
 * Created by wqcai on 15/6/12.
 */
@Repository("functionDao")
public interface FunctionDao {

    /**
     * 查询功能及操作信息
     *
     * @param customerIds 客户ID集合
     * @return
     */
    List<FunctionTreeDto> findFunctionItemTree(@Param("customerIds") List<String> customerIds);

    /**
     * 根据条件查询对应的功能信息
     *
     * @param customerId 客户ID
     * @param id         功能ID
     * @param url        路径
     * @return
     */
    FunctionTreeDto findFunctionObj(@Param("customerId") String customerId, @Param("id") String id, @Param("url") String url);

    /**
     * 根据父节点信息修改子节点full_path
     *
     * @param customerId 客户ID
     * @param pKey       父级key
     * @param pid        父级ID
     */
    void updateFullPathByParent(@Param("customerId") String customerId, @Param("pKey") String pKey, @Param("pid") String pid);

    /**
     * 删除功能信息
     *
     * @param customerId 客户ID
     * @param id         主键ID
     */
    void deleteFunction(@Param("customerId") String customerId, @Param("id") String id);

    /**
     * 删除功能操作信息
     *
     * @param customerId 客户ID
     * @param itemId     功能操作ID
     * @param functionId 功能ID
     */
    void deleteFunctionItem(@Param("customerId") String customerId, @Param("itemId") String itemId, @Param("functionId") String functionId);

    /**
     * 添加功能信息
     *
     * @param functionDto
     */
    void addFunction(@Param("functionDto") FunctionDto functionDto);

    /**
     * 添加功能操作信息
     *
     * @param itemDto
     */
    void addFunctionItem(@Param("itemDto") FunctionItemDto itemDto);

    /**
     * 修改功能信息
     *
     * @param functionDto
     */
    void updateFunction(@Param("functionDto") FunctionDto functionDto);

    /**
     * 修改功能操作信息
     *
     * @param itemDto
     */
    void updateFunctionItem(@Param("itemDto") FunctionItemDto itemDto);

    /**
     * 查询角色功能信息
     *
     * @return
     */
    List<RoleFunctionDto> findFunctionAll(@Param("customerId") String customerId,@Param("roleIds") List<String> roleIds);

    /**
     * 添加角色功能权限信息
     *
     * @param dtos
     */
    void addRoleFunction(@Param("dtos") List<RoleFunctionDto> dtos);

    /**
     * 删除角色功能权限信息
     *
     * @param customerId
     * @param roleId
     */
    void deleteRoleFunction(@Param("customerId") String customerId, @Param("roleId") String roleId);

    /**
     * 获取角色关联的功能及功能操作信息
     *
     * @param roleId
     * @return
     */
    List<RoleFunctionDto> findRoleFunction(@Param("customerId") String customerId,@Param("roleId") String roleId);
    
    //=============================================================================
    
    
    /***
     * 查询首页配置信息
     * @param functionId
     * @param empId
     * @param customerId
     * @return
     */
    List<HomeConfigDto> queryHomeConfig(@Param("functionId") String functionId,@Param("empId") String empId,@Param("customerId") String customerId);

    /***
     * 添加用户首页配置信息
     * @param configDtos
     */
    void insertUserHomeConfig(@Param("dtos") List<HomeConfigDto> configDtos);

    /***
     * 修改用户首页配置信息
     * @param configDtos
     */
    void updateUserHomeConfig(@Param("dtos") List<HomeConfigDto> configDtos);
}

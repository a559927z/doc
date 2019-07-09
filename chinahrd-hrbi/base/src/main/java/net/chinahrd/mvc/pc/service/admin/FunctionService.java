package net.chinahrd.mvc.pc.service.admin;

import java.util.List;

import net.chinahrd.entity.dto.pc.admin.FunctionDto;
import net.chinahrd.entity.dto.pc.admin.FunctionItemDto;
import net.chinahrd.entity.dto.pc.admin.FunctionTreeDto;
import net.chinahrd.entity.dto.pc.admin.RoleFunctionDto;
import net.chinahrd.entity.dto.pc.manage.HomeConfigDto;

/**
 * 功能Service接口类
 * Created by wqcai on 15/6/12.
 */
public interface FunctionService {

    /**
     * 查询功能及功能操作以树形数据展示
     *
     * @param customerIds 客户ID集合
     * @return
     */
    List<FunctionTreeDto> findFunctionItemTree(List<String> customerIds);

    /**
     * 根据条件查询对应的功能信息
     *
     * @param customerId 客户ID
     * @param id         功能ID
     * @param url        链接
     * @return
     */
    FunctionTreeDto findFunctionObj(String customerId, String id, String url);

    /**
     * 删除功能及功能操作
     *
     * @param id
     * @param type
     */
    void deleteFunctionAndItem(String customerId, String id, Integer type);

    /**
     * 添加功能信息
     *
     * @param functionDto
     */
    void addFunction(FunctionDto functionDto);

    /**
     * 添加功能操作信息
     *
     * @param itemDto
     */
    void addFunctionItem(FunctionItemDto itemDto);

    /**
     * 修改功能信息
     *
     * @param functionDto
     */
    void updateFunction(FunctionDto functionDto);

    /**
     * 修改功能操作信息
     *
     * @param itemDto
     */
    void updateFunctionItem(FunctionItemDto itemDto);

    /**
     * 查询角色关联的功能信息
     * @param customerId 客户ID
     * @param roleIds 角色ID集合
     * @return
     */
    List<RoleFunctionDto> findFunctionAll(String customerId,List<String> roleIds);

    /**
     * 添加角色功能权限信息
     *
     * @param customerId
     * @param roleId
     * @param functionItems
     * @param createUserId
     */
    void addRoleFunction(String customerId, String roleId, String[] functionItems, String createUserId);

    /**
     * 获取角色关联的功能及功能操作信息
     *
     * @param roleId
     * @return
     */
    List<RoleFunctionDto> findRoleFunction(String customerId, String roleId);
    
    
    
    
    //================================================================


    /**
     * 查询首页相关排序信息
     * @param empId
     * @param customerId
     * @return
     */
    List<HomeConfigDto> queryUserHomeConfig(String empId, String customerId);

    /**
     * 查询首页相关排序信息
     * @param empId
     * @param customerId
     * @return
     */
    List<HomeConfigDto> queryUserHomeConfig(String functionCode, String empId, String customerId);

    /**
     * 添加或更新用户首页拖拽排序配置信息
     * @param homeConfig
     * @param empId
     * @param customerId
     */
    void editUserHomeConfig(String homeConfig, String empId, String customerId);

    /**
     * 添加或更新用户首页拖拽排序配置信息
     * @param homeConfig
     * @param empId
     * @param customerId
     */
    void editUserHomeConfig(String homeConfig,String functionCode, String empId, String customerId);
    
    
    
    

}

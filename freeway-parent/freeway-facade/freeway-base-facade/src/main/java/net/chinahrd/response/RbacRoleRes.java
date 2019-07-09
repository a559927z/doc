package net.chinahrd.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wzmo
 * 角色信息
 *
 */
@Data
public class RbacRoleRes implements Serializable {
    /**
     * 角色名
     */
    private String roleId;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 权限列表
     */
    private List<RbacResourceRes> resources;

}

package net.chinahrd.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author wzmo
 * @since 2019-03-05
 */
@Data
public class RbacUserRes implements Serializable {

    /**
     * 用户Id
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户名称
     */
    private String password;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 角色列表
     */
    private List<RbacRoleRes> roles;

}

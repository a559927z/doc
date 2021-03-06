package net.chinahrd.platform.rbac.response;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wzmo
 * @since 2019-03-18
 */
@Data
public class UserInfoRes implements Serializable {

    /**
     * 用户Id
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户密码
     */
    private String password;

    /**
     * 状态
     */
    private Integer state;
}

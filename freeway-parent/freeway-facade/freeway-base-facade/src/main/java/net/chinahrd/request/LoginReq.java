package net.chinahrd.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录、初次使用接口
 * @author wzmo
 */
@Data
public class LoginReq implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;

}
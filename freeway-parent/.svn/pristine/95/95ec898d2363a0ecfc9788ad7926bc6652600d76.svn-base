package net.chinahrd.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  通用的登录实体信息抽象
 * </p>
 *
 * @author bright
 * @since 2019/3/12 10:34
 */
public class CurrentUser implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 租户d
     */
    private String tenantId;

    /**
     * 可能的附加信息
     */
    private Map<String, Object> extras = new HashMap<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void put(String key, Object value) {
        extras.put(key, value);
    }

    public Object get(String key){
         return extras.get(key);
    }
}

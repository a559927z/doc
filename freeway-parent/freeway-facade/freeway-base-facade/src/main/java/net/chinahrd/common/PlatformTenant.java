package net.chinahrd.common;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *  租户对应的租户信息及数据源信息
 * </p>
 *
 * @author bright
 * @since 2019/3/19 14:34
 */
@Data
public class PlatformTenant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户Id
     */
    private String tenantId;
    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 数据源Id
     */
    private String datasourceId;
    /**
     * 数据源名称
     */
    private String datasourceName;
    /**
     * 数据库类型
     */
    private String dbType;
    /**
     * 数据库账号
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * 数据库jdbcUr
     */
    private String url;
    /**
     * 数据库初始化连接数量
     */
    private Integer initialSize;
    /**
     * 数据库最大活动连接
     */
    private Integer maxActive;
    /**
     * 数据库最大空闲连接
     */
    private Integer maxIdle;

}

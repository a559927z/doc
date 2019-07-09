package net.chinahrd.bean;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author bright
 * @since 2019/3/15 13:42
 */

@Data
public class JdbcBean {
    private String driverClassName;

    private String url;

    private String username;

    private String password;
}

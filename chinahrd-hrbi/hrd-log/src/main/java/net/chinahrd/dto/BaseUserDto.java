package net.chinahrd.dto;

import lombok.Data;

/**
 * Title: ${type_name} <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年04月27日 14:19
 * @Verdion 1.0 版本
 * ${tags}
 */
@Data
public class BaseUserDto {

    private String userId; // 用户ID
    private String userKey; // 用户key
    private String userName; // 用户名
    private String userNameCh; // 用户姓名
    private String password;

}

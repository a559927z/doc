package net.chinahrd.platform.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.chinahrd.common.BusinessEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author wzmo
 * @since 2019-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("rbac_user")
public class UserInfo extends BusinessEntity {

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
     * 用户密码
     */
    private String password;

    /**
     * 状态
     */
    private Integer state;
}

package net.chinahrd.modules.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import net.chinahrd.common.BaseEntity;

import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bright
 * @since 2019-03-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("demo_user_info")
public class UserInfo extends BaseEntity {

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
     * 租户Id
     */
    private String tenantId;


}

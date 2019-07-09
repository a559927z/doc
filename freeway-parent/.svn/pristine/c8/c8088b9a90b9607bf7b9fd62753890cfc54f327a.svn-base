package net.chinahrd.modules.pub.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import net.chinahrd.common.BaseEntity;

import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统参数配置表
 * </p>
 *
 * @author cxhuo_gz
 * @since 2019-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pub_param_info")
public class ParamInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配置id
     */
    private String paramId;
    /**
     * 参数分类
     */
    private String paramType;
    /**
     * 参数key
     */
    private String paramKey;
    /**
     * 参数值
     */
    private String paramValue;
    /**
     * 参数名
     */
    private String paramName;

}

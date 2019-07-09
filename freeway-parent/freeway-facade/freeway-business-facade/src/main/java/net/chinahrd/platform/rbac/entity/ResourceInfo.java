package net.chinahrd.platform.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.chinahrd.common.BaseEntity;
import net.chinahrd.common.BusinessEntity;
/**
 * <p>
 *资源
 * </p>
 *
 * @author wzmo
 * @since 2019-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("rbac_resource")
public class ResourceInfo extends BaseEntity {
    /**
     * 资源id
     */
    private String resourceId;
    /**
     * 上级parentId
     */
    private String resourcePid;

    /**
     * 资源url
     */
    private String resourceUrl;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 类型：1资源 2菜单 3按钮
     */
    private String resourceType;

    /**
     * 排序
     */
    private Integer resourceSort;
}

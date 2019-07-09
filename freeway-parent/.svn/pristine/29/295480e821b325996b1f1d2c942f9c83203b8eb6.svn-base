package net.chinahrd.modules.pub.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.chinahrd.common.BaseEntity;

/**
 * <p>
 * 租户与数据源关系表
 * </p>
 *
 * @author zoujiaxi
 * @since 2019-03-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pub_tenant_datasource_rel")
public class TenantDatasourceRel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 租户与数据源关系id
     */
    private String relId;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 数据源id
     */
    private String datasourceId;


}

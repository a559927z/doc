package net.chinahrd.modules.pub.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.chinahrd.common.BaseEntity;

import java.util.Date;

/**
 * <p>
 * 数据源管理
 * </p>
 *
 * @author zoujiaxi
 * @since 2019-03-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pub_datasource_info")
public class DatasourceInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 数据源id
     */
    private String datasourceId;
    /**
     * 数据源名称
     */
    private String datasourceName;
    /**
     * 数据库类型 mysql,oracle,postgresql,sqlserver
     */
    private String dbType;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * jdbcUrl
     */
    private String url;
    /**
     * 初始化连接数量
     */
    private Integer initialSize;
    /**
     * 最大活动连接
     */
    private Integer maxActive;
    /**
     * 最大空闲连接
     */
    private Integer maxIdle;
    /**
     * 备注
     */
    private String note;

}

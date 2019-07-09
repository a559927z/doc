package net.chinahrd.modules.pub.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chinahrd.request.BasePageRequest;
import org.hibernate.validator.constraints.Length;

/**
 * 用户查询参数对象
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(value="用户查询参数对象",description="用户查询参数对象")
public class DatasourceQueryReq extends BasePageRequest {
    private static final long serialVersionUID = 1L;

    @Length(max=32)
    @ApiModelProperty(name="数据源id",example="bright")
    private String datasourceId;

    @Length(max=100)
    @ApiModelProperty(name="数据源名称",example="bright")
    private String datasourceName;

    @Length(max=20)
    @ApiModelProperty(name="数据库类型",example="mysql")
    private String dbType;

    @Length(max=20)
    @ApiModelProperty(name="账号",example="aaaa")
    private String username;

}

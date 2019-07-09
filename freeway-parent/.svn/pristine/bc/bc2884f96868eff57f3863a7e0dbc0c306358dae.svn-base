package net.chinahrd.modules.pub.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chinahrd.request.BasePageRequest;
import org.hibernate.validator.constraints.Length;

/**
 * 租户查询参数对象
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(value="租户新增修改参数对象",description="租户新增修改参数对象")
public class TenantInfoReq extends BasePageRequest {
    private static final long serialVersionUID = 1L;

    @Length(max=32)
    @ApiModelProperty(name="数据源id",example="bright")
    private String datasourceId;

    @Length(max=32)
    @ApiModelProperty(name="租户id",example="bright")
    private String tenantId;

    @Length(max=100)
    @ApiModelProperty(name="租户名称",example="bright")
    private String tenantName;

    @Length(max=20)
    @ApiModelProperty(name="租户状态",example="bright")
    private String tenantState;

    @Length(max=200)
    @ApiModelProperty(name="租户地址",example="bright")
    private String tenantAddress;

    @Length(max=32)
    @ApiModelProperty(name="联系人",example="bright")
    private String contact;

    @Length(max=32)
    @ApiModelProperty(name="联系人电话",example="bright")
    private String contactTel;

    @Length(max=64)
    @ApiModelProperty(name="联系人邮箱",example="bright")
    private String contactEmail;


}

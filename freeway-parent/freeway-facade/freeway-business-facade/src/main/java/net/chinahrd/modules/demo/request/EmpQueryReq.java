package net.chinahrd.modules.demo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chinahrd.request.BasePageRequest;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 员工查询参数对象
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(value="员工查询参数对象",description="员工查询参数对象")
public class EmpQueryReq extends BasePageRequest {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Length(max=50)
    @ApiModelProperty(name="员工名称",example="bright")
    private String empName;
    
}

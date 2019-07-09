package net.chinahrd.modules.pub.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chinahrd.request.BasePageRequest;
import net.chinahrd.request.BaseRequest;
import org.hibernate.validator.constraints.Length;

/**
 * Created by huocaoxi on 2019/3/21.
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(value="参数信息查询对象",description="参数信息查询对象")
public class ParamQueryReq extends BasePageRequest {

    /**
     * 参数分类
     */
    @Length(max=20)
    @ApiModelProperty(name="参数分类",example="huocaoxi")
    private String paramType;
    /**
     * 参数key
     */
    @Length(max=20)
    @ApiModelProperty(name="参数key",example="huocaoxi")
    private String paramKey;
    /**
     * 参数值
     */
    @Length(max=100)
    @ApiModelProperty(name="参数值",example="huocaoxi")
    private String paramValue;
    /**
     * 参数名
     */
    @Length(max=64)
    @ApiModelProperty(name="参数名",example="huocaoxi")
    private String paramName;
}

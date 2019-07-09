package net.chinahrd.modules.demo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author bright
 * @since 2019-03-06
 */
@Data
@ApiModel(value="用户返回信息",description="")
public class UserInfoRes implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name="用户Id",example="32")
	private String userId;

	@ApiModelProperty(name="用户名称",example="20")
	private String userName;
	
}

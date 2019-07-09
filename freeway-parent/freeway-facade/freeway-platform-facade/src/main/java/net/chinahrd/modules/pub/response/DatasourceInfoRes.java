package net.chinahrd.modules.pub.response;

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
public class DatasourceInfoRes implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name="数据源id",example="bright")
	private String datasourceId;

	@ApiModelProperty(name="数据源名称",example="bright")
	private String datasourceName;

	@ApiModelProperty(name="数据库类型",example="mysql")
	private String dbType;

	@ApiModelProperty(name="账号",example="mysql")
	private String username;

	@ApiModelProperty(name="密码",example="mysql")
	private String password;

	@ApiModelProperty(name="jdbcUrl",example="mysql")
	private String url;

	@ApiModelProperty(name="初始化连接数量",example="mysql")
	private Integer initialSize;

	@ApiModelProperty(name="最大活动连接",example="mysql")
	private Integer maxActive;

	@ApiModelProperty(name="最大空闲连接",example="mysql")
	private Integer maxIdle;

	@ApiModelProperty(name="备注",example="mysql")
	private String note;
	
}

package net.chinahrd.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * 分页请求
 *
 */
@Data
public class PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull
	@ApiModelProperty(name="页码",example="1")
    private Integer pageNum = 1;

    @NotNull
    @ApiModelProperty(name="每页记录数",example="20")
    private Integer pageSize = 20;
    
    /**
     * 排序字段
     */
    private String orderByField;
    /**
     * 排序字段
     */
    private Boolean isAsc;
    /**
     * 查询参数
     */
    private Map<String, Object> condition;
    
    /**
     *用在mybatis中根据isAsc设置排序方式，isAsc=true, 则orderByType = asc 否则就是 desc
     */
    @JsonIgnore
    private String orderByType;
    
    public String getOrderByType(){
        return isAsc!=null && isAsc?" asc":" desc";
    }
}

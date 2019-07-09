package net.chinahrd.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  分页查询返回结果封装
 * </p>
 *
 * @author bright
 * @since 2019/3/8 10:34
 */
@Data
public class PageData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name="总记录数",example="100")
    private long total;

    @ApiModelProperty(name="页数据列表")
    private List<T> list;

    @ApiModelProperty(name="页码",example="1")
    private int pageNum;

    @ApiModelProperty(name="每页记录数",example="20")
    private int pageSize;

    @ApiModelProperty(name="总页数",example="100")
    private int pages;

    @ApiModelProperty(name="前页",example="0")
    private int prePage;

    @ApiModelProperty(name="后页",example="2")
    private int nextPage;

}

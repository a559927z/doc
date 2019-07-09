package net.chinahrd.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.PageRequest;

/**
 * <p>
 *  分页请求基类
 * </p>
 *
 * @author bright
 * @since 2019/3/8 10:34
 */
@Data
public class BasePageRequest extends PageRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 当前操作用户
     * 调用service层时必须包含当前操作用户，从前端到api不可见不用设置
     */
    @JsonIgnore
    private CurrentUser currentUser;
    
}

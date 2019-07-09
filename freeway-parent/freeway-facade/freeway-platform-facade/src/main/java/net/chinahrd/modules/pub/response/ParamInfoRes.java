package net.chinahrd.modules.pub.response;

import lombok.Data;
import net.chinahrd.request.BaseRequest;

/**
 * Created by huocaoxi on 2019/3/21.
 */
@Data
public class ParamInfoRes extends BaseRequest{
    /**
     * 配置id
     */
    private String paramId;
    /**
     * 参数分类
     */
    private String paramType;
    /**
     * 参数key
     */
    private String paramKey;
    /**
     * 参数值
     */
    private String paramValue;
    /**
     * 参数名
     */
    private String paramName;

}

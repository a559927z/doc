package net.chinahrd.modules.pub.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.chinahrd.common.BaseEntity;

/**
 * <p>
 * 日志记录表 
 * </p>
 *
 * @author bright
 * @since 2019-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pub_log_info")
public class LogInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String logId;
    /**
     * 操作名称
     */
    private String opName;
    /**
     * 操作描述
     */
    private String opDesc;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 客户端请求ip
     */
    private String requestIp;
    /**
     * 请求方法类型
     */
    private String requestMethod;
    /**
     * 请求Uri
     */
    private String requestUri;
    /**
     * 远程主机
     */
    private String remoteHost;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 执行时间
     */
    private Long execTime;
    /**
     * 错误信息
     */
    private String errorInfo;


}

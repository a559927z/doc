package net.chinahrd.eis.aop;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author jxzhang on 2016年11月10日
 * @Verdion 1.0 版本
 */
@Data
public class AopInformation implements Serializable {
    private static final long serialVersionUID = 1412651275510147118L;

    private String userLoginId; // 登录日志ID
    private String userId; // 用户ID
    private String userKey; // 用户key
    private String userName; // 用户名
    private String userNameCh; // 用户姓名
    private String ipAddress; // 登录IP地址
    private Timestamp loginTime; // 登录时间

    private String description; // 描述
    private String packageName; // 包名
    private String clazz; // 类名
    private String method; // 方法名
    private String methodFull; // 类+方法名
    private int type; // 1，登录， 2 登出， 3，查询、修改、添加
    private int exceptionCode = 0; // 错误代号 0为没有
    private String exceptionDetail; // 错误描述
    private String params; // 参数
    private double useTime; // 用时单位秒
    private Timestamp createDate; // 创建时间
    private boolean writeDb; // 是否写入数据库

}

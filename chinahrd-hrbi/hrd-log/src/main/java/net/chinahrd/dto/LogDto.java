package net.chinahrd.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;

@EqualsAndHashCode
@Data
public class LogDto extends BaseUserDto implements Serializable {

    private static final long serialVersionUID = -2649114633409878192L;

    private String operateLogId; // 登录日志ID
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

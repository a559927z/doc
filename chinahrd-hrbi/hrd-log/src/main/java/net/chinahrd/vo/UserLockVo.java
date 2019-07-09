package net.chinahrd.vo;

import lombok.Data;

/**
 * Title: hrd-log <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年04月26日 23:41
 * @Verdion 1.0 版本
 * ${tags}
 */
@Data
public class UserLockVo {

    private String userId;

    private String userNameCh;

    private String userKey;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否有锁（离职员工）
     */
    private Integer isLock;

    /**
     * 电话
     */
    private String telPhone;

    /**
     * 岗位
     */
    private String positionName;

    /**
     * 入职时间
     */
    private String entryDate;

    /**
     * 地址
     */
    private String address;

    /**
     * 父级机构名称
     */
    private String orgParName;
    /**
     * 所在机构名称
     */
    private String orgName;
    /**
     * 角色字符串拼接
     */
    private String roles;

    /**
     * 总数据
     * select count(1) from dim_user
     */
    private Long total;

    /**
     * 总年假
     */
    private String annualTotal;

    /**
     * 已休年假
     */
    private String annual;

    /**
     * 总内部调休
     */
    private String canLeave;

    /**
     * 身份证号
     */
    private String nationalId;
}

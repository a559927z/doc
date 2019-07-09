package net.chinahrd.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * Title: 员工扩展信息 <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年04月27日 12:00
 * @Verdion 1.0 版本
 * ${tags}
 */
@Data
@EqualsAndHashCode
public class EmpExtendDto extends BaseEmpDto implements Serializable {

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

    private String nationalId;
}

package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;


/***
 * 机构负责人相关Dto
 *
 * @author wqcai
 */
public class OrganManagerDto implements Serializable {
    private static final long serialVersionUID = -4142746632667191479L;

    private String organId;        //机构ID
    private String organkey;        //机构key
    private String organName;        //机构名称
    private String organPId;        //机构父ID
    private String empId;            //机构负责人ID
    private String userNameCh;        //机构负责人名称
    private String email;            //机构负责人邮箱
    private String telPhone;        //机构负责人手机号码


    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrgankey() {
        return organkey;
    }

    public void setOrgankey(String organkey) {
        this.organkey = organkey;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getOrganPId() {
        return organPId;
    }

    public void setOrganPId(String organPId) {
        this.organPId = organPId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getUserNameCh() {
        return userNameCh;
    }

    public void setUserNameCh(String userNameCh) {
        this.userNameCh = userNameCh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }
}
    
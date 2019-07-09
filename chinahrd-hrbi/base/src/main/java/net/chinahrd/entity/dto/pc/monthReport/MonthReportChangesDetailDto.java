package net.chinahrd.entity.dto.pc.monthReport;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 月报异动员工详情dto
 * Created by wqcai on 16/08/30 030.
 */
public class MonthReportChangesDetailDto implements Serializable {
    private static final long serialVersionUID = 7093801657069880311L;

    private String empId;               //员工ID
    private String userNameCh;          //员工姓名
    private Timestamp changesDate;      //异动时间
    private String organId;             //所属机构ID
    private String organName;           //所属机构名称
    private String note;                //异动备注

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

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Timestamp getChangesDate() {
        return changesDate;
    }

    public void setChangesDate(Timestamp changesDate) {
        this.changesDate = changesDate;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

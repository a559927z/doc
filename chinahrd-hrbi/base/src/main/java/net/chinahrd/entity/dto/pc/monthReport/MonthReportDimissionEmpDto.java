package net.chinahrd.entity.dto.pc.monthReport;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 月报关健人才离职员工dto
 * Created by wqcai on 16/08/30 030.
 */
public class MonthReportDimissionEmpDto implements Serializable {

    private static final long serialVersionUID = -7001874933839452454L;
    private String empId;               //员工ID
    private String userNameCh;          //员工姓名
    private Timestamp changesDate;      //异动时间
    private String organId;             //所属机构ID
    private String organName;           //所属机构名称
    private String note;                //异动备注
    private Double yearRate;			//年流失率
    private Double monthRate;			//月流失率
    private Double sameRatio;			//同比
    private Double basicRatio;			//环比
    private Integer yearMonth;			//年月

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

	public Double getYearRate() {
		return yearRate;
	}

	public void setYearRate(Double yearRate) {
		this.yearRate = yearRate;
	}

	public Double getMonthRate() {
		return monthRate;
	}

	public void setMonthRate(Double monthRate) {
		this.monthRate = monthRate;
	}

	public Double getSameRatio() {
		return sameRatio;
	}

	public void setSameRatio(Double sameRatio) {
		this.sameRatio = sameRatio;
	}

	public Double getBasicRatio() {
		return basicRatio;
	}

	public void setBasicRatio(Double basicRatio) {
		this.basicRatio = basicRatio;
	}

	public Integer getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}
}

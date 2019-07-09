package net.chinahrd.entity.dto.pc.monthReport;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 月报分享dto
 * Created by wqcai on 16/09/09 009.
 */
public class MonthReportShareDto implements Serializable {
    private static final long serialVersionUID = -6013468200055736303L;

    private String shareId;                     //分享ID
    private String empId;                       //受益者ID
    private String shareEmpId;                  //分享者ID
    private String reportContent;               //内容
    private String path;                        //路径
    private String note;                        //备注
    private Boolean sendMail;                   //是否发送邮件
    private Integer yearMonth;                  //年月
    private Timestamp createTime;               //创建时间
    private String customerId;                  //客户ID
    private String userName;                    //用户名
    private String organId;                    //用户名

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getShareEmpId() {
        return shareEmpId;
    }

    public void setShareEmpId(String shareEmpId) {
        this.shareEmpId = shareEmpId;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getSendMail() {
        return sendMail;
    }

    public void setSendMail(Boolean sendMail) {
        this.sendMail = sendMail;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}
    
}

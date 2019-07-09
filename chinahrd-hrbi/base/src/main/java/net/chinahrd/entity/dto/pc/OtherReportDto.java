package net.chinahrd.entity.dto.pc;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * （人才剖像）其它测评报告Dto
 * Created by wqcai on 15/10/20 0020.
 */
public class OtherReportDto implements Serializable {
    private static final long serialVersionUID = -8858066469068777455L;

    private String otherReportId;        //测评报告主键ID
    private String empId;               //员工ID
    private String reportName;          //报告名称
    private Timestamp reportDate;       //测评时间
    private String path;                //附件路径

    public String getOtherReportId() {
        return otherReportId;
    }

    public void setOtherReportId(String otherReportId) {
        this.otherReportId = otherReportId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    public Timestamp getReportDate() {
        return reportDate;
    }

    public void setReportDate(Timestamp reportDate) {
        this.reportDate = reportDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}

package net.chinahrd.entity.dto.pc.monthReport;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 月报收藏dto
 * Created by wqcai on 16/09/09 009.
 */
public class MonthReportSavaDto implements Serializable {

    private static final long serialVersionUID = -2990925573550359127L;

    private String savaId;          //附件保存ID
    private String organId;         //所属机构ID
    private String fileName;        //附件名称/机构名称
    private String path;            //附件路径
    private String empId;           //存储用户
    private Integer yearMonth;      //年月
    private Timestamp createTime;   //创建时间
    private String customerId;      //客户ID

    public String getSavaId() {
        return savaId;
    }

    public void setSavaId(String savaId) {
        this.savaId = savaId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
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

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }
}

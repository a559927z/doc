package net.chinahrd.dto;

import java.io.Serializable;
import java.util.Date;

public class EmpVacation implements Serializable {
    private String empId;

    private String customerId;

    private String empKey;

    private String userNameCh;

    private String annualTotal;

    private String annual;

    private String canLeave;

    private String historyHour;

    private Date refresh;

    private static final long serialVersionUID = 1L;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getEmpKey() {
        return empKey;
    }

    public void setEmpKey(String empKey) {
        this.empKey = empKey == null ? null : empKey.trim();
    }

    public String getUserNameCh() {
        return userNameCh;
    }

    public void setUserNameCh(String userNameCh) {
        this.userNameCh = userNameCh == null ? null : userNameCh.trim();
    }

    public String getAnnualTotal() {
        return annualTotal;
    }

    public void setAnnualTotal(String annualTotal) {
        this.annualTotal = annualTotal == null ? null : annualTotal.trim();
    }

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual == null ? null : annual.trim();
    }

    public String getCanLeave() {
        return canLeave;
    }

    public void setCanLeave(String canLeave) {
        this.canLeave = canLeave == null ? null : canLeave.trim();
    }

    public String getHistoryHour() {
        return historyHour;
    }

    public void setHistoryHour(String historyHour) {
        this.historyHour = historyHour == null ? null : historyHour.trim();
    }

    public Date getRefresh() {
        return refresh;
    }

    public void setRefresh(Date refresh) {
        this.refresh = refresh;
    }
}
package net.chinahrd.entity.dto.pc.manage;

import java.sql.Timestamp;

import net.chinahrd.entity.dto.pc.manage.EmpBaseInfoDto;

/**
 * 团队提醒-员工信息
 * Created by wqcai on 15/11/11 0011.
 */
public class RemindEmpDto extends EmpBaseInfoDto {
    private static final long serialVersionUID = -5442821281238704284L;

    private Timestamp birthDate;                //生日时间
    private Timestamp entryDate;                //入职时间
    private Integer annualYear;                 //入司周年


    public Timestamp getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    public Timestamp getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getAnnualYear() {
        return annualYear;
    }

    public void setAnnualYear(Integer annualYear) {
        this.annualYear = annualYear;
    }
}

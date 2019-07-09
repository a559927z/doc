package net.chinahrd.entity.dto.pc.employeePerformance;

import java.io.Serializable;
import java.util.List;

/**
 * 员工绩效查询条件Dto
 * Created by wqcai on 16/3/9.
 */
public class PerfChangeQueryDto implements Serializable {

    private static final long serialVersionUID = 5716432325353001620L;

    /* 客户ID */
    private String customerId;
    /*　部门id　*/
    private String organizationId;
    /*　当前绩效　*/
    private Integer yearMonth;
    /*　上次绩效日期　*/
    private Integer prevYearMonth;
    /*　是否多次绩效符合　*/
    private Boolean manyPerf;
    /*　绩效星级集合　*/
    private List<Integer> ranks;
    /* 低绩效 */
    private List<Integer> lowPerfs;
    /* 高绩效 */
    private List<Integer> heightPerfs;
    /*　员工类型 0 全部  1 员工 2 管理者　*/
    private Integer empType;
    /*　绩效周期 1:一年 2:半年 3:季度　*/
    private Integer preWeek;

    private List<String> crowds;

    public List<Integer> getHeightPerfs() {
        return heightPerfs;
    }

    public void setHeightPerfs(List<Integer> heightPerfs) {
        this.heightPerfs = heightPerfs;
    }

    public List<Integer> getLowPerfs() {
        return lowPerfs;
    }

    public void setLowPerfs(List<Integer> lowPerfs) {
        this.lowPerfs = lowPerfs;
    }

    public List<String> getCrowds() {
        return crowds;
    }

    public void setCrowds(List<String> crowds) {
        this.crowds = crowds;
    }


    public Integer getPreWeek() {
        return preWeek;
    }

    public void setPreWeek(Integer preWeek) {
        this.preWeek = preWeek;
    }

    public List<Integer> getRanks() {
        return ranks;
    }

    public void setRanks(List<Integer> ranks) {
        this.ranks = ranks;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getPrevYearMonth() {
        return prevYearMonth;
    }

    public void setPrevYearMonth(Integer prevYearMonth) {
        this.prevYearMonth = prevYearMonth;
    }

    public Integer getEmpType() {
        return empType;
    }

    public void setEmpType(Integer empType) {
        this.empType = empType;
    }

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }


    public Boolean getManyPerf() {
        return manyPerf;
    }

    public void setManyPerf(Boolean manyPerf) {
        this.manyPerf = manyPerf;
    }
}

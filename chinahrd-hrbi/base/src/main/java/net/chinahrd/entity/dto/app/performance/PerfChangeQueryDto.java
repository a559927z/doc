package net.chinahrd.entity.dto.app.performance;

import java.io.Serializable;
import java.util.List;

/**
 * 员工绩效查询条件Dto
 * @author htpeng
 *2016年6月6日下午12:05:21
 */
public class PerfChangeQueryDto implements Serializable {

    private static final long serialVersionUID = -8982011075350343543L;
	/** 客户ID */
    private String customerId;
    /** 部门id */
    private String organizationId;
    /** 当前绩效 */
    private Integer yearMonth;
    /** 上次绩效日期 */
    private Integer prevYearMonth;
    /** 是否多次绩效符合 */
    private Boolean isManyPerf;
    /** 绩效星级集合 */
    private List<Integer> ranks;
    /** 员工类型 0 全部  1 员工 2 管理者 */
    private Integer empType;
    /** 绩效周期 1:一年 2:半年 3:季度 */
    private Integer preWeek;

    
    private Integer highH;   
    private Integer highL;
    private Integer lowH;
    private Integer lowL;

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
    
    

    public Boolean getIsManyPerf() {
		return isManyPerf;
	}

	public void setIsManyPerf(Boolean isManyPerf) {
		this.isManyPerf = isManyPerf;
	}

	public Integer getHighH() {
		return highH;
	}

	public void setHighH(Integer highH) {
		this.highH = highH;
	}

	public Integer getHighL() {
		return highL;
	}

	public void setHighL(Integer highL) {
		this.highL = highL;
	}

	public Integer getLowH() {
		return lowH;
	}

	public void setLowH(Integer lowH) {
		this.lowH = lowH;
	}

	public Integer getLowL() {
		return lowL;
	}

	public void setLowL(Integer lowL) {
		this.lowL = lowL;
	}

	public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Boolean getManyPerf() {
        return isManyPerf;
    }

    public void setManyPerf(Boolean manyPerf) {
        isManyPerf = manyPerf;
    }
}

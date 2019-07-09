package net.chinahrd.entity.dto.pc.accordDismiss;

import java.io.Serializable;

/**
 * 主动流失率趋势
 * Created by wqcai on 15/09/14 014.
 */
public class DismissTrendDto implements Serializable {

    private static final long serialVersionUID = 6756541314640288242L;

    private String organId;         //组织机构ID
    private Double monthBegin;     //月初人数
    private Double monthEnd;       //月末人数
    private Double monthCount;     //月总人数
    private Double accordCount;    //月主动流失人数
    private Double rate;			//流失率
    private String yearMonth;       //年月
    private String strQuarter;  //季度
    private int minQuarter;  //季初
    private int maxQuarter;  //季末
    private int quarterCount;  //

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public Double getMonthBegin() {
        return monthBegin;
    }

    public void setMonthBegin(Double monthBegin) {
        this.monthBegin = monthBegin;
    }

    public Double getMonthEnd() {
        return monthEnd;
    }

    public void setMonthEnd(Double monthEnd) {
        this.monthEnd = monthEnd;
    }

    public Double getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(Double monthCount) {
        this.monthCount = monthCount;
    }

    public Double getAccordCount() {
        return accordCount;
    }

    public void setAccordCount(Double accordCount) {
        this.accordCount = accordCount;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getStrQuarter() {
		return strQuarter;
	}

	public void setStrQuarter(String strQuarter) {
		this.strQuarter = strQuarter;
	}

	public int getMinQuarter() {
		return minQuarter;
	}

	public void setMinQuarter(int minQuarter) {
		this.minQuarter = minQuarter;
	}

	public int getQuarterCount() {
		return quarterCount;
	}

	public void setQuarterCount(int quarterCount) {
		this.quarterCount = quarterCount;
	}

	public int getMaxQuarter() {
		return maxQuarter;
	}

	public void setMaxQuarter(int maxQuarter) {
		this.maxQuarter = maxQuarter;
	}
	
}

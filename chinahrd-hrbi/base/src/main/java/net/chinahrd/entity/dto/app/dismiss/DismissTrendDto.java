package net.chinahrd.entity.dto.app.dismiss;

import java.io.Serializable;

/**
 * 主动流失率趋势
 * Created by wqcai on 15/09/14 014.
 */
public class DismissTrendDto implements Serializable ,Comparable<DismissTrendDto>{

    private static final long serialVersionUID = 6756541314640288242L;

    private String organId;         //组织机构ID
    private String organName;         //组织机构ID
    private Double monthBegin;     //月初人数
    private Double monthEnd;       //月末人数
    private Double monthCount;     //月总人数
    private Double accordCount;    //月主动流失人数
    private Double rate;			//流失率
    private String yearMonth;       //年月

    
    public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(DismissTrendDto o) {
		// TODO Auto-generated method stub
		return this.getRate()>o.getRate()?1:this.getRate()==o.getRate()?0:-1;
	}
}

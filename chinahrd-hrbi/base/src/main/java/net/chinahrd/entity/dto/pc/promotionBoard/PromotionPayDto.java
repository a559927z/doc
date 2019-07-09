package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/12.
 */
public class PromotionPayDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String empId;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public double getConditionProp() {
        return conditionProp;
    }

    public void setConditionProp(double conditionProp) {
        this.conditionProp = conditionProp;
    }

    public Integer getMatchCondition() {
        return matchCondition;
    }

    public void setMatchCondition(Integer matchCondition) {
        this.matchCondition = matchCondition;
    }

    public Integer getMismatchCondition() {
        return mismatchCondition;
    }

    public void setMismatchCondition(Integer mismatchCondition) {
        this.mismatchCondition = mismatchCondition;
    }

    public Integer getMatchCount() {
		return matchCount;
	}

	public void setMatchCount(Integer matchCount) {
		this.matchCount = matchCount;
	}

	public String getRankNameAf() {
		return rankNameAf;
	}

	public void setRankNameAf(String rankNameAf) {
		this.rankNameAf = rankNameAf;
	}

	private String userName;
    private double pay;
    private String rankName;
    private double conditionProp;
    private Integer matchCondition;
    private Integer mismatchCondition;
    private Integer matchCount;
    private String rankNameAf;  //晋级后的
     
}

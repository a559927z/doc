package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/8.
 */
public class PromotionTrackDto implements Serializable{
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

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPerformanceName() {
        return performanceName;
    }

    public void setPerformanceName(String performanceName) {
        this.performanceName = performanceName;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    

    public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}


	public double getCurrRank() {
		return currRank;
	}

	public void setCurrRank(double currRank) {
		this.currRank = currRank;
	}


	private Integer years;
    private double rank;
    private String userName;
    private String performanceName;
    private Integer total;
    private String rankName;
    private double currRank;
}

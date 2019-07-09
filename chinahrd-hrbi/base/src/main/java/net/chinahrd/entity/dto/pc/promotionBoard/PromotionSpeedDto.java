package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/7.
 */
public class PromotionSpeedDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    private String id;
    private String name;
    private double rank;
    private double total;
    private double avg;

    public String getIncrease() {
        return increase;
    }

    public void setIncrease(String increase) {
        this.increase = increase;
    }

    public String getParOrganId() {
		return parOrganId;
	}

	public void setParOrganId(String parOrganId) {
		this.parOrganId = parOrganId;
	}

	private String increase;
    private String parOrganId;
    
}

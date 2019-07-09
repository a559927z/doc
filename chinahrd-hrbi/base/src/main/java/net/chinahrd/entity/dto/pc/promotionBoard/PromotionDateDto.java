package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/5.
 */
public class PromotionDateDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public double getConditionProp() {
        return conditionProp;
    }

    public void setConditionProp(double conditionProp) {
        this.conditionProp = conditionProp;
    }

    private Date maxDate;
    private Date minDate;
    private double conditionProp;
}

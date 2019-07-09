package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/14.
 */
public class PromotionTotalDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getTotalDate() {
        return totalDate;
    }

    public void setTotalDate(Date totalDate) {
        this.totalDate = totalDate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    private String userName;
    private Date totalDate;
    private Integer total;
}

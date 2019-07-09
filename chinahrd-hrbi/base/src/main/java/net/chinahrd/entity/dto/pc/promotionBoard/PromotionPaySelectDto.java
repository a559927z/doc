package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/12.
 */
public class PromotionPaySelectDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rank;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getMatch() {
        return match;
    }

    public void setMatch(Integer match) {
        this.match = match;
    }

    public Integer getMismatch() {
        return mismatch;
    }

    public void setMismatch(Integer mismatch) {
        this.mismatch = mismatch;
    }

    private Integer match;
    private Integer mismatch;
}

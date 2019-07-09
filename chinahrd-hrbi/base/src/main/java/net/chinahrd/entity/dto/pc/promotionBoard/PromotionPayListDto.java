package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class PromotionPayListDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rankName;
    private double nextRankSalary;
    private List<PromotionPayPersonListDto> match;
    private List<PromotionPayPersonListDto> mismatch;

    public List<PromotionPayPersonListDto> getMismatch() {
        return mismatch;
    }

    public void setMismatch(List<PromotionPayPersonListDto> mismatch) {
        this.mismatch = mismatch;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public double getNextRankSalary() {
        return nextRankSalary;
    }

    public void setNextRankSalary(double nextRankSalary) {
        this.nextRankSalary = nextRankSalary;
    }

    public List<PromotionPayPersonListDto> getMatch() {
        return match;
    }

    public void setMatch(List<PromotionPayPersonListDto> match) {
        this.match = match;
    }
}

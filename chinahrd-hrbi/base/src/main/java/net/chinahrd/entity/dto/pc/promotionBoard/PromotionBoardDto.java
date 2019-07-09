package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/4.
 */
public class PromotionBoardDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getRankNameAfter() {
        return rankNameAfter;
    }

    public void setRankNameAfter(String rankNameAfter) {
        this.rankNameAfter = rankNameAfter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String name;
    private String rankName;
    private String rankNameAfter;
    private String status;
}

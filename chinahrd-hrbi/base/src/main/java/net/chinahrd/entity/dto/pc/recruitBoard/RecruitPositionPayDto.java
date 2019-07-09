package net.chinahrd.entity.dto.pc.recruitBoard;

import java.io.Serializable;

/**
 *  招聘看板-招聘岗位薪资
 * Created by Kevins on 16/5/12.
 */
public class RecruitPositionPayDto implements Serializable {
    private static final long serialVersionUID = 6171604479890145855L;

    private String positionId;          //岗位ID
    private String positionName;        //岗位名称
    private String rankName;            //职级
    private Double pay;                 //平均薪资
    private Integer empTotal;           //人员统计

    public Double getPay() {
        return pay == null ? 0d : pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Integer getEmpTotal() {
        return empTotal;
    }

    public void setEmpTotal(Integer empTotal) {
        this.empTotal = empTotal;
    }
}

package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
public class PromotionStatusDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getRankNameNext() {
        return rankNameNext;
    }

    public void setRankNameNext(String rankNameNext) {
        this.rankNameNext = rankNameNext;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PromotionReqDto> getListReq() {
        return listReq;
    }

    public void setListReq(List<PromotionReqDto> listReq) {
        this.listReq = listReq;
    }

    private String empId;
    private String userName;
    private String imgPath;
    private String organizationName;
    private String rankName;
    private String rankNameNext;
    private Integer status;
    private List<PromotionReqDto> listReq;
}

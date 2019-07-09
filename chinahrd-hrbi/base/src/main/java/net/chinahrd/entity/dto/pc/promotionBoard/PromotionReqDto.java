package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/11.
 */
public class PromotionReqDto implements Serializable{
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

    public Integer getNoteIndex() {
        return noteIndex;
    }

    public void setNoteIndex(Integer noteIndex) {
        this.noteIndex = noteIndex;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPromotionRequest() {
        return promotionRequest;
    }

    public void setPromotionRequest(String promotionRequest) {
        this.promotionRequest = promotionRequest;
    }

    public String getPersomaStatus() {
        return persomaStatus;
    }

    public void setPersomaStatus(String persomaStatus) {
        this.persomaStatus = persomaStatus;
    }

    public Integer getIsAccord() {
        return isAccord;
    }

    public void setIsAccord(Integer isAccord) {
        this.isAccord = isAccord;
    }

    private Integer noteIndex;
    private String note;
    private String promotionRequest;
    private String persomaStatus;
    private Integer isAccord;
}

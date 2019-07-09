package net.chinahrd.entity.dto.pc.emp;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.chinahrd.entity.enums.TipsEnum;


/**
 * 培训经历dto
 */
public class TrainExperienceDto implements Serializable {

    private static final long serialVersionUID = 1795874525797730795L;

    private String id;

    /**
     * 课程名称/项目
     */
    private String trainName;

    /**
     * 完成日期
     */
    private Timestamp finishDate;

    /**
     * 状态名称(进行中、已完成...)
     */
    private Integer status;

    /**
     * 状态名称(进行中、已完成...)
     */
    @SuppressWarnings("unused")
    private String statusName;

    /**
     * 成绩
     */
    private String result;

    /**
     * 培训机构
     */
    private String trainUnit;

    /**
     * 所获证书
     */
    private String gainCertificate;

    /**
     * 讲师
     */
    private String teacherName;

    /**
     * 备注
     */
    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Timestamp getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Timestamp finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
    	return TipsEnum.getDescByCode(status, 1);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTrainUnit() {
        return trainUnit;
    }

    public void setTrainUnit(String trainUnit) {
        this.trainUnit = trainUnit;
    }

    public String getGainCertificate() {
        return gainCertificate;
    }

    public void setGainCertificate(String gainCertificate) {
        this.gainCertificate = gainCertificate;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}

package net.chinahrd.entity.dto.pc.emp;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 所获职称dto
 */
public class ProfTitleDto implements Serializable {

    private static final long serialVersionUID = -9210752029268693324L;

    private String id;

    /**
     * 获得时间
     */
    private Timestamp gainDate;

    /**
     * 职称名称
     */
    private String profTitleName;

    /**
     * 级别
     */
    private String profLv;

    /**
     * 授予单位
     */
    private String awardUnit;

    /**
     * 有效期
     */
    private Timestamp effectDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getProfTitleName() {
        return profTitleName;
    }

    public void setProfTitleName(String profTitleName) {
        this.profTitleName = profTitleName;
    }

    public String getProfLv() {
        return profLv;
    }

    public void setProfLv(String profLv) {
        this.profLv = profLv;
    }

    public String getAwardUnit() {
        return awardUnit;
    }

    public void setAwardUnit(String awardUnit) {
        this.awardUnit = awardUnit;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Timestamp getGainDate() {
        return gainDate;
    }

    public void setGainDate(Timestamp gainDate) {
        this.gainDate = gainDate;
    }
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    public Timestamp getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Timestamp effectDate) {
        this.effectDate = effectDate;
    }


}

package net.chinahrd.entity.dto.pc.talentMaps;

import java.io.Serializable;

/**
 * 人才地图人员统计Dto
 * Created by wqcai on 16/7/25.
 */
public class TalentMapsEmpCountDto implements Serializable {
    private static final long serialVersionUID = 7410471967851527726L;
    /**
     * 机构ID
     */
    private String organId;
    /**
     * 机构名称
     */
    private String organName;
    /**
     * 人员统计信息
     */
    private Integer empCount;

    public TalentMapsEmpCountDto() {
    }

    public TalentMapsEmpCountDto(String organId, String organName) {
        this.organId = organId;
        this.organName = organName;
    }

    public Integer getEmpCount() {
        return null == empCount ? 0 : empCount;
    }

    public void setEmpCount(Integer empCount) {
        this.empCount = empCount;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }
}

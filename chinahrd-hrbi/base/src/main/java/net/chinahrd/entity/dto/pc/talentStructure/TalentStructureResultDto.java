package net.chinahrd.entity.dto.pc.talentStructure;

import java.io.Serializable;
import java.util.List;

/**
 * 人员结构查询结果Dto
 * Created by wqcai on 16/11/04 004.
 */
public class TalentStructureResultDto implements Serializable {
    private static final long serialVersionUID = 6050517154436342833L;

    private String organId;             //机构ID
    private String organName;           //机构名称
    private List<TalentStructureResultSubDto> subDtos;  //子级集合
    private String other;               //其它类型字段


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

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public List<TalentStructureResultSubDto> getSubDtos() {
        return subDtos;
    }

    public void setSubDtos(List<TalentStructureResultSubDto> subDtos) {
        this.subDtos = subDtos;
    }
}

package net.chinahrd.entity.dto.pc.talentStructure;

import java.io.Serializable;

/**
 * 人员结构查询结果子Dto
 * Created by wqcai on 16/11/04 004.
 */
public class TalentStructureResultSubDto implements Serializable {

    private static final long serialVersionUID = -8053661835335548643L;
    private String typeId;              //类型ID
    private String typeKey;               //类型key
    private String typeName;            //类型名称
    private Integer total;              //统计人数

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTotal() {
        return total == null ? 0:total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }
}

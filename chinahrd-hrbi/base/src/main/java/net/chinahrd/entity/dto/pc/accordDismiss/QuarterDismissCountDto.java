package net.chinahrd.entity.dto.pc.accordDismiss;

import java.io.Serializable;

/**
 * 季度流失人员统计Dto
 * Created by wqcai on 15/09/17 017.
 */
public class QuarterDismissCountDto implements Serializable {
    private static final long serialVersionUID = -7876283389417430733L;

    private String typeId;          //类型ID （绩效、层级、司龄）
    private String typeName;        //类型名称 （绩效、层级、司龄）
    private Integer workingCount;   //在职人数
    private Integer runOffCount;    //离职人数

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

    public Integer getWorkingCount() {
        return workingCount == null ? 0 : workingCount;
    }

    public void setWorkingCount(Integer workingCount) {
        this.workingCount = workingCount;
    }

    public Integer getRunOffCount() {
        return runOffCount == null ? 0 : runOffCount;
    }

    public void setRunOffCount(Integer runOffCount) {
        this.runOffCount = runOffCount;
    }
}

package net.chinahrd.entity.dto.app.dismiss;

import java.io.Serializable;

/**
 * 季度流失人员统计Dto
 * Created by wqcai on 15/09/17 017.
 */
public class DismissTypeDto implements Serializable {
    private static final long serialVersionUID = -7876283389417430733L;

    private String typeId;          //类型ID （绩效、层级、司龄）
    private String typeName;        //类型名称 （绩效、层级、司龄）
    private Double rate;   //流失率
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

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getRunOffCount() {
		return runOffCount;
	}

	public void setRunOffCount(Integer runOffCount) {
		this.runOffCount = runOffCount;
	}


}

package net.chinahrd.entity.dto.pc.manage;

import java.sql.Timestamp;

import net.chinahrd.entity.dto.pc.manage.EmpBaseInfoDto;
/**
 * 人才损益员工信息
 * Created by wqcai on 15/11/13 0013.
 */
public class LossesEmpDto extends EmpBaseInfoDto {
    private static final long serialVersionUID = -1722849273508911925L;

    private String performanceName;             //绩效信息
    private Integer changeType;                 //异动类型
    private Timestamp changeDate;               //异动日期
    
    // by jxzhang on 16/01/26 
    private String inOrganId;	// 调入部门
    private String inOrganName;
    private String inPosName;
    private String inSeqName;
    private String inSeqSubName;
    private String inAbName;
    private String inJTName;
    private String inRName;
    private Integer inCType;
    private Timestamp inCDate;
    
    private String outOrganId;	// 调出部门
    private String outOrganName;
    private String outPosName;
    private String outSeqName;
    private String outSeqSubName;
    private String outAbName;
    private String outJTName;
    private String outRName;
    private Integer outCType;
    private Timestamp outCDate;
    
    
    
    public String getInOrganName() {
		return inOrganName;
	}

	public void setInOrganName(String inOrganName) {
		this.inOrganName = inOrganName;
	}

	public String getInPosName() {
		return inPosName;
	}

	public void setInPosName(String inPosName) {
		this.inPosName = inPosName;
	}

	public String getInSeqName() {
		return inSeqName;
	}

	public void setInSeqName(String inSeqName) {
		this.inSeqName = inSeqName;
	}

	public String getInSeqSubName() {
		return inSeqSubName;
	}

	public void setInSeqSubName(String inSeqSubName) {
		this.inSeqSubName = inSeqSubName;
	}

	public String getInAbName() {
		return inAbName;
	}

	public void setInAbName(String inAbName) {
		this.inAbName = inAbName;
	}

	public String getInJTName() {
		return inJTName;
	}

	public void setInJTName(String inJTName) {
		this.inJTName = inJTName;
	}

	public String getInRName() {
		return inRName;
	}

	public void setInRName(String inRName) {
		this.inRName = inRName;
	}

	public Integer getInCType() {
		return inCType;
	}

	public void setInCType(Integer inCType) {
		this.inCType = inCType;
	}

	public Timestamp getInCDate() {
		return inCDate;
	}

	public void setInCDate(Timestamp inCDate) {
		this.inCDate = inCDate;
	}

	public String getOutOrganName() {
		return outOrganName;
	}

	public void setOutOrganName(String outOrganName) {
		this.outOrganName = outOrganName;
	}

	public String getOutPosName() {
		return outPosName;
	}

	public void setOutPosName(String outPosName) {
		this.outPosName = outPosName;
	}

	public String getOutSeqName() {
		return outSeqName;
	}

	public void setOutSeqName(String outSeqName) {
		this.outSeqName = outSeqName;
	}

	public String getOutSeqSubName() {
		return outSeqSubName;
	}

	public void setOutSeqSubName(String outSeqSubName) {
		this.outSeqSubName = outSeqSubName;
	}

	public String getOutAbName() {
		return outAbName;
	}

	public void setOutAbName(String outAbName) {
		this.outAbName = outAbName;
	}

	public String getOutJTName() {
		return outJTName;
	}

	public void setOutJTName(String outJTName) {
		this.outJTName = outJTName;
	}

	public String getOutRName() {
		return outRName;
	}

	public void setOutRName(String outRName) {
		this.outRName = outRName;
	}

	public Integer getOutCType() {
		return outCType;
	}

	public void setOutCType(Integer outCType) {
		this.outCType = outCType;
	}

	public Timestamp getOutCDate() {
		return outCDate;
	}

	public void setOutCDate(Timestamp outCDate) {
		this.outCDate = outCDate;
	}

	public String getInOrganId() {
		return inOrganId;
	}

	public void setInOrganId(String inOrganId) {
		this.inOrganId = inOrganId;
	}

	public String getOutOrganId() {
		return outOrganId;
	}

	public void setOutOrganId(String outOrganId) {
		this.outOrganId = outOrganId;
	}

	public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public Timestamp getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
    }

    public String getPerformanceName() {
        return performanceName;
    }

    public void setPerformanceName(String performanceName) {
        this.performanceName = performanceName;
    }
}

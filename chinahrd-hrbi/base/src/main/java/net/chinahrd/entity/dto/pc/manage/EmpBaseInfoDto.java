package net.chinahrd.entity.dto.pc.manage;

import java.io.Serializable;

/**
 * 员工基本信息Dto
 * Created by wqcai on 15/11/12 0012.
 */
public class EmpBaseInfoDto implements Serializable {
    private static final long serialVersionUID = 5021154907941311305L;

    private String empId;                           //员工ID
    private String userNameCh;                      //员工姓名
    private String imgPath;                         //头像路径
    private String organizationId;	
    private String organizationName;                //机构名称
    private String positionName;                    //岗位名称
    private String sequenceName;                    //职位序列名称
    private String sequenceSubName;                 //职位子序列名称
    private String abilityName;                     //职位层级名称
    private String jobTitleName;                    //职衔名称
    private String rankName;                        //职级名称（主序列+子序列+职位层级+'.'+职级）
    private String abilityKey;						//职位子序列编码
    
    public String getAbilityKey() {
		return abilityKey;
	}

	public void setAbilityKey(String abilityKey) {
		this.abilityKey = abilityKey;
	}

	public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getUserNameCh() {
        return userNameCh;
    }

    public void setUserNameCh(String userNameCh) {
        this.userNameCh = userNameCh;
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

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public String getSequenceSubName() {
        return sequenceSubName;
    }

    public void setSequenceSubName(String sequenceSubName) {
        this.sequenceSubName = sequenceSubName;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
    
    
}

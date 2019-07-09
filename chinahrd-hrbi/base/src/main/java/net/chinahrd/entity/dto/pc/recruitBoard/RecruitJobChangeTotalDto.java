package net.chinahrd.entity.dto.pc.recruitBoard;

import java.io.Serializable;

/**
 * 招聘看板-工作异动统计Dto
 * Created by wqcai on 16/5/16.
 */
public class RecruitJobChangeTotalDto implements Serializable {

    private static final long serialVersionUID = 5649785362125335059L;

    private String changeTypeId;            //异动类型ID
    private String changeTypeName;          //异动类型名称
    private String curtName;                //异动类型简称
    private Integer empNum;                  //异动人数
    private String empStr;                  //异动人员集合

    public String getChangeTypeId() {
        return changeTypeId;
    }

    public void setChangeTypeId(String changeTypeId) {
        this.changeTypeId = changeTypeId;
    }

    public String getChangeTypeName() {
        return changeTypeName;
    }

    public void setChangeTypeName(String changeTypeName) {
        this.changeTypeName = changeTypeName;
    }

    public String getCurtName() {
        return curtName;
    }

    public void setCurtName(String curtName) {
        this.curtName = curtName;
    }

    public Integer getEmpNum() {
        return empNum;
    }

    public void setEmpNum(Integer empNum) {
        this.empNum = empNum;
    }

    public String getEmpStr() {
        return empStr;
    }

    public void setEmpStr(String empStr) {
        this.empStr = empStr;
    }
}

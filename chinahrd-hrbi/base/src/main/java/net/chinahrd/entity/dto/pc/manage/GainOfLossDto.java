package net.chinahrd.entity.dto.pc.manage;

import java.io.Serializable;
import java.util.List;

import net.chinahrd.entity.dto.pc.manage.LossesEmpDto;

/**
 * 人才损益Dto
 * Created by wqcai on 15/11/13 0013.
 */
public class GainOfLossDto implements Serializable {
    private static final long serialVersionUID = -4701706048839933818L;

    private String organId;                     //机构ID
    private Integer publiceJobNum;              //发布职位数
    private Integer resumeNum;                  //简历数
    private Integer acceptNum;                  //应聘数
    private Integer offerNum;                   //offer数
    private Integer compileNum;                 //编制数
    private Integer workingNum;                 //在职人数
    private List<LossesEmpDto> empDtos;         //损益员工集合

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public Integer getPubliceJobNum() {
        return publiceJobNum;
    }

    public void setPubliceJobNum(Integer publiceJobNum) {
        this.publiceJobNum = publiceJobNum;
    }

    public Integer getResumeNum() {
        return resumeNum;
    }

    public void setResumeNum(Integer resumeNum) {
        this.resumeNum = resumeNum;
    }

    public Integer getAcceptNum() {
        return acceptNum;
    }

    public void setAcceptNum(Integer acceptNum) {
        this.acceptNum = acceptNum;
    }

    public Integer getOfferNum() {
        return offerNum;
    }

    public void setOfferNum(Integer offerNum) {
        this.offerNum = offerNum;
    }

    public Integer getCompileNum() {
        return compileNum;
    }

    public void setCompileNum(Integer compileNum) {
        this.compileNum = compileNum;
    }

    public Integer getWorkingNum() {
        return workingNum;
    }

    public void setWorkingNum(Integer workingNum) {
        this.workingNum = workingNum;
    }

    public List<LossesEmpDto> getEmpDtos() {
        return empDtos;
    }

    public void setEmpDtos(List<LossesEmpDto> empDtos) {
        this.empDtos = empDtos;
    }
}

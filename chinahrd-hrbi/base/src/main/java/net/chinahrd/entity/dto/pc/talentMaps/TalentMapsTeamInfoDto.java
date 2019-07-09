package net.chinahrd.entity.dto.pc.talentMaps;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.google.gson.Gson;

/**
 * 人才地图团队信息dto
 * Created by wqcai on 16/8/2.
 */
public class TalentMapsTeamInfoDto implements Serializable {

    private static final long serialVersionUID = -7912386441437427381L;
    private String teamId;          //团队ID
    private String empId;           //员工ID
    private String customerId;      //客户ID
    private String teamName;        //团队名称
    private boolean pkView;       //是否团队PK查看
    private String requirement;     //团队要求
    private TalentMapsTeamQueryDto queryDto;


    public String getRequirement() {
        if (StringUtils.isEmpty(requirement) && null != queryDto){
            Gson gson = new Gson();
            return gson.toJson(queryDto);
        }
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public TalentMapsTeamQueryDto getQueryDto() {
        return queryDto;
    }

    public void setQueryDto(TalentMapsTeamQueryDto queryDto) {
        this.queryDto = queryDto;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public boolean isPkView() {
        return pkView;
    }

    public void setPkView(boolean pkView) {
        this.pkView = pkView;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}

package net.chinahrd.entity.dto.pc.talentMaps;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.StringUtils;

import net.chinahrd.entity.enums.AgeIntervalEnum;
import net.chinahrd.utils.CollectionKit;

/**
 * 人才地图-团队人员查询dto
 * Created by wqcai on 16/8/1.
 */
public class TalentMapsTeamQueryDto implements Serializable {
    private static final long serialVersionUID = 5181501017786013684L;

    private String organId;
    private String name;
    private String customerId;
    private String yearMonths;   //人才地图时间周期

    /* str后缀的为传参用 */
    private String sequenceStr;
    private String sequenceSubStr;
    private String abilityStr;
    private String performanceStr;
    private String ageStr;
    private String sexStr;
    private String eduStr;
    /* 下面参数为转换查询用 */
    private String[] sequences;
    private String[] sequenceSubs;
    private String[] abilitys;
    private String[] performances;
    private List<AgeIntervalEnum> ageIntervals;
    private String[] sexs;
    private String[] edus;

    /* 团队PK用 */
    private String team;
    /** 选择地图相关区域用 */
    private String xLaberId;
    private String yLaberId;

    /*团队趋势图用*/
    private String date;
    private String minYearMonth;
    private String maxYearMonth;
    private String isFull;
    private String viewType;
    private String yVal;
    
    public String getAbilityStr() {
        return abilityStr;
    }

    public void setAbilityStr(String abilityStr) {
        this.abilityStr = abilityStr;
    }

    public String getAgeStr() {
        return ageStr;
    }

    public void setAgeStr(String ageStr) {
        this.ageStr = ageStr;
    }

    public String getEduStr() {
        return eduStr;
    }

    public void setEduStr(String eduStr) {
        this.eduStr = eduStr;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getPerformanceStr() {
        return performanceStr;
    }

    public void setPerformanceStr(String performanceStr) {
        this.performanceStr = performanceStr;
    }

    public String getSequenceStr() {
        return sequenceStr;
    }

    public void setSequenceStr(String sequenceStr) {
        this.sequenceStr = sequenceStr;
    }

    public String getSequenceSubStr() {
        return sequenceSubStr;
    }

    public void setSequenceSubStr(String sequenceSubStr) {
        this.sequenceSubStr = sequenceSubStr;
    }

    public String getSexStr() {
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }


    public String[] getAbilitys() {
        return null == abilitys && !StringUtils.isEmpty(abilityStr) ? abilityStr.split(",") : abilitys;
    }

    public void setAbilitys(String[] abilitys) {
        this.abilitys = abilitys;
    }

    public List<AgeIntervalEnum> getAgeIntervals() {
        if (null == ageIntervals && !StringUtils.isEmpty(ageStr)) {
            String[] age = ageStr.split(",");
            List<AgeIntervalEnum> enumList = CollectionKit.newList();
            for (String a : age) {
                AgeIntervalEnum ageEnum = AgeIntervalEnum.valueOf(a);
                enumList.add(ageEnum);
            }
            return enumList;
        }
        return ageIntervals;
    }

    public void setAgeIntervals(List<AgeIntervalEnum> ageIntervals) {
        this.ageIntervals = ageIntervals;
    }

    public String[] getEdus() {
        return null == edus && !StringUtils.isEmpty(eduStr) ? eduStr.split(",") : edus;
    }

    public void setEdus(String[] edus) {
        this.edus = edus;
    }

    public String[] getPerformances() {
        return null == performances && !StringUtils.isEmpty(performanceStr) ? performanceStr.split(",") : performances;
    }

    public void setPerformances(String[] performances) {
        this.performances = performances;
    }

    public String[] getSequences() {
        return null == sequences && !StringUtils.isEmpty(sequenceStr) ? sequenceStr.split(",") : sequences;
    }

    public void setSequences(String[] sequences) {
        this.sequences = sequences;
    }

    public String[] getSequenceSubs() {
        return null == sequenceSubs && !StringUtils.isEmpty(sequenceSubStr) ? sequenceSubStr.split(",") : sequenceSubs;
    }

    public void setSequenceSubs(String[] sequenceSubs) {
        this.sequenceSubs = sequenceSubs;
    }

    public String[] getSexs() {
        return null == sexs && !StringUtils.isEmpty(sexStr) ? sexStr.split(",") : sexs;
    }

    public void setSexs(String[] sexs) {
        this.sexs = sexs;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getYearMonths() {
        return yearMonths;
    }

    public void setYearMonths(String yearMonths) {
        this.yearMonths = yearMonths;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getxLaberId() {
        return xLaberId;
    }

    public void setxLaberId(String xLaberId) {
        this.xLaberId = xLaberId;
    }

    public String getyLaberId() {
        return yLaberId;
    }

    public void setyLaberId(String yLaberId) {
        this.yLaberId = yLaberId;
    }

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMinYearMonth() {
		return minYearMonth;
	}

	public void setMinYearMonth(String minYearMonth) {
		this.minYearMonth = minYearMonth;
	}

	public String getMaxYearMonth() {
		return maxYearMonth;
	}

	public void setMaxYearMonth(String maxYearMonth) {
		this.maxYearMonth = maxYearMonth;
	}

	public String getIsFull() {
		return isFull;
	}

	public void setIsFull(String isFull) {
		this.isFull = isFull;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getyVal() {
		return yVal;
	}

	public void setyVal(String yVal) {
		this.yVal = yVal;
	}
    
}

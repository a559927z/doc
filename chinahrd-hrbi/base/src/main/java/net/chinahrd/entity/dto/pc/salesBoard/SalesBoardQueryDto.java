package net.chinahrd.entity.dto.pc.salesBoard;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.StringUtils;

import net.chinahrd.entity.enums.AgeIntervalEnum;
import net.chinahrd.utils.CollectionKit;

/**
 * 销售看板
 * 
 * @author lma and xwli 2016-08-16
 */
public class SalesBoardQueryDto implements Serializable {

	private static final long serialVersionUID = 5528308014117095365L;
	private String organId;// 组织机构id
	private String customerId;// 客户id
	private String performanceStr;// 绩效
	private String ageStr;// 年龄
	private String sexStr;// 性别
	private String eduStr;// 学历
	private String yearMonth;// 日期
	private List<AgeIntervalEnum> ageIntervals;
	private Integer rows;// 行
	/* 团队PK用 */
	private String team;
	/* 团队总览/团队PK区分标识 */
	private String flag;
	private List<String> textList;
	private List<String> performanceList;
	private List<AgeIntervalEnum> ageList;
	private List<String> sexList;
	private List<String> eduList;

	private List<String> subOrganIdList;
	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPerformanceStr() {
		return performanceStr;
	}

	public void setPerformanceStr(String performanceStr) {
		this.performanceStr = performanceStr;
	}

	public String getAgeStr() {
		return ageStr;
	}

	public void setAgeStr(String ageStr) {
		this.ageStr = ageStr;
	}

	public String getSexStr() {
		return sexStr;
	}

	public void setSexStr(String sexStr) {
		this.sexStr = sexStr;
	}

	public String getEduStr() {
		return eduStr;
	}

	public void setEduStr(String eduStr) {
		this.eduStr = eduStr;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
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

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<String> getTextList() {
		return textList;
	}

	public void setTextList(List<String> textList) {
		this.textList = textList;
	}

	public List<String> getSubOrganIdList() {
		return subOrganIdList;
	}

	public void setSubOrganIdList(List<String> subOrganIdList) {
		this.subOrganIdList = subOrganIdList;
	}

	public List<String> getPerformanceList() {
		return performanceList;
	}

	public void setPerformanceList(List<String> performanceList) {
		this.performanceList = performanceList;
	}

	public List<AgeIntervalEnum> getAgeList() {
		return ageList;
	}

	public void setAgeList(List<AgeIntervalEnum> list) {
		this.ageList = list;
	}

	public List<String> getSexList() {
		return sexList;
	}

	public void setSexList(List<String> sexList) {
		this.sexList = sexList;
	}

	public List<String> getEduList() {
		return eduList;
	}

	public void setEduList(List<String> eduList) {
		this.eduList = eduList;
	}

}
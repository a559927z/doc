package net.chinahrd.entity.dto.pc.salesBoard;

import java.io.Serializable;

/**
 * 销售看板
 * 
 * @author lma and xwli 2016-08-16
 */
public class SalesBoardDto implements Serializable, Comparable<SalesBoardDto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3308565649689115664L;
	private String empId; // empid
	private String organId;// 组织机构Id
	private String organName;// 组织机构名称
	private String userName;// 姓名
	private Integer conNum;// 数量
	private String date;// 日期
	private Integer yearMonth; // 年月
	private Double target; // 目标值
	private Double sales; // 销售额
	private Double standardRate; // 达标率
	private Double compareVal; // 比较值
	private String ratio; // 环比
	private String basis; // 同比
	private String teamId; // 团队ID
	private String teamName; // 团队名称
	private Double payment; // 已回款
	private Double returnAmount; // 回款额
	private Double sumNum;// 数量
	private Double befSumNum;// 数量
	private Double percentNum;// 数量
	private Integer numberNum; // 销售量
	private Double moneyNum;// 销售额
	private Double profitNum;// 利润
	private Integer rank; // 排名
	private String changeDate; // 异动日期
	private String changeType; // 异动情况类型
	private String changeInfo; // 异动情况
	private String region;// 区域
	private String yellowRange;// 黄色预警
	private String redRange;// 红色预警
	private String abilityNumberId;// 能力id
	private String abilityNumberName;// 能力名称
	/** 键 */
	private String k;
	/** 值 */
	private String v;
	private String rankName;// 排序名称
	private String empIds;// 用户ids
	private String userNames;// 用户名
	private String configVal;// 配置表val
	private String id; // id
	private String name; // name
	private Integer showIndex;// 排序码
	/** 客户画像用 */
	private Double moneyPer; // 销售额比较值
	private Double profitPer; // 利润比较值

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getConNum() {
		return conNum == null ? 0 : conNum;
	}

	public void setConNum(Integer conNum) {
		this.conNum = conNum;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Double getTarget() {
		return target;
	}

	public void setTarget(Double target) {
		this.target = target;
	}

	public Double getSales() {
		return sales;
	}

	public void setSales(Double sales) {
		this.sales = sales;
	}

	public Double getStandardRate() {
		return standardRate;
	}

	public void setStandardRate(Double standardRate) {
		this.standardRate = standardRate;
	}

	public Double getCompareVal() {
		return compareVal;
	}

	public void setCompareVal(Double compareVal) {
		this.compareVal = compareVal;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public String getBasis() {
		return basis;
	}

	public void setBasis(String basis) {
		this.basis = basis;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Double getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(Double returnAmount) {
		this.returnAmount = returnAmount;
	}

	public Double getBefSumNum() {
		return befSumNum;
	}

	public void setBefSumNum(Double befSumNum) {
		this.befSumNum = befSumNum;
	}

	public Double getSumNum() {
		return sumNum == null ? 0D : sumNum;
	}

	public void setSumNum(Double sumNum) {
		this.sumNum = sumNum;
	}

	public Double getPercentNum() {
		return percentNum == null ? 0D : percentNum;
	}

	public void setPercentNum(Double percentNum) {
		this.percentNum = percentNum;
	}

	public Integer getNumberNum() {
		return numberNum == null ? 0 : numberNum;
	}

	public void setNumberNum(Integer numberNum) {
		this.numberNum = numberNum;
	}

	public Double getMoneyNum() {
		return moneyNum == null ? 0D : moneyNum;
	}

	public void setMoneyNum(Double moneyNum) {
		this.moneyNum = moneyNum;
	}

	public Double getProfitNum() {
		return profitNum == null ? 0D : profitNum;
	}

	public void setProfitNum(Double profitNum) {
		this.profitNum = profitNum;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getChangeInfo() {
		return changeInfo;
	}

	public void setChangeInfo(String changeInfo) {
		this.changeInfo = changeInfo;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getYellowRange() {
		return yellowRange;
	}

	public void setYellowRange(String yellowRange) {
		this.yellowRange = yellowRange;
	}

	public String getRedRange() {
		return redRange;
	}

	public void setRedRange(String redRange) {
		this.redRange = redRange;
	}

	public String getAbilityNumberId() {
		return abilityNumberId;
	}

	public void setAbilityNumberId(String abilityNumberId) {
		this.abilityNumberId = abilityNumberId;
	}

	public String getAbilityNumberName() {
		return abilityNumberName;
	}

	public void setAbilityNumberName(String abilityNumberName) {
		this.abilityNumberName = abilityNumberName;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public String getEmpIds() {
		return empIds;
	}

	public void setEmpIds(String empIds) {
		this.empIds = empIds;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getConfigVal() {
		return configVal;
	}

	public void setConfigVal(String configVal) {
		this.configVal = configVal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}

	public Double getMoneyPer() {
		return moneyPer;
	}

	public void setMoneyPer(Double moneyPer) {
		this.moneyPer = moneyPer;
	}

	public Double getProfitPer() {
		return profitPer;
	}

	public void setProfitPer(Double profitPer) {
		this.profitPer = profitPer;
	}

	@Override
	public int compareTo(SalesBoardDto o) {
		if (showIndex != o.getShowIndex()) {
			return o.getShowIndex() - showIndex;
		} else {
			return showIndex.compareTo(o.getShowIndex());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SalesBoardDto) {
			SalesBoardDto o = (SalesBoardDto) obj;
			if (showIndex == o.getShowIndex()) {
				return true;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

}

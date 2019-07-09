package net.chinahrd.entity.dto.pc.clientImg;

import java.io.Serializable;

/**
 * 客户基本信息
 * @author malong 2017-02-22
 * 
 * */
public class ClientBaseInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5262408242804994733L;

	private String clientId; // 客户id
	private String clientName; // 客户名称
	private String curtName; // 客户简称
	private String nature; // 公司性质
	private String industry; // 所属行业
	private String clientType; // 客户类型
	private Double turnover; // 年均营业额
	private Integer empNum; // 员工规模
	private Integer leaderNum; // 管理干部数量
	private String address; // 公司地址
	private String clientTel; // 公司电话
	private String clientEmail; // 公司email

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getCurtName() {
		return curtName;
	}

	public void setCurtName(String curtName) {
		this.curtName = curtName;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Double getTurnover() {
		return turnover;
	}

	public void setTurnover(Double turnover) {
		this.turnover = turnover;
	}

	public Integer getEmpNum() {
		return empNum;
	}

	public void setEmpNum(Integer empNum) {
		this.empNum = empNum;
	}

	public Integer getLeaderNum() {
		return leaderNum;
	}

	public void setLeaderNum(Integer leaderNum) {
		this.leaderNum = leaderNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClientTel() {
		return clientTel;
	}

	public void setClientTel(String clientTel) {
		this.clientTel = clientTel;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

}

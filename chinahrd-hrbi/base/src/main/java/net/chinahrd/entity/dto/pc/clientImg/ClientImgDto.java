package net.chinahrd.entity.dto.pc.clientImg;

import java.io.Serializable;

/**
 * 客户画像DTO
 * 
 * @author jxzhang on 2017年1月12日
 * @Verdion 1.0 版本
 */
public class ClientImgDto implements Serializable {

	private static final long serialVersionUID = -1591238865202787844L;
	/**
	 * 客户ID
	 */
	private String clientId;
	/**
	 * 存在基本信息
	 */
	private Integer existBaseInfo;
	/**
	 * 存在联系人信息
	 */
	private Integer existContacts;
	/**
	 * 存在纪要信息
	 */
	private Integer existSummary;
	/**
	 * 存在订单信息
	 */
	private Integer existOrder;

	/**
	 * 存在信用信息
	 */
	private Integer existCredit;


	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getExistBaseInfo() {
		return existBaseInfo;
	}

	public void setExistBaseInfo(Integer existBaseInfo) {
		this.existBaseInfo = existBaseInfo;
	}

	public Integer getExistContacts() {
		return existContacts;
	}

	public void setExistContacts(Integer existContacts) {
		this.existContacts = existContacts;
	}

	public Integer getExistSummary() {
		return existSummary;
	}

	public void setExistSummary(Integer existSummary) {
		this.existSummary = existSummary;
	}

	public Integer getExistOrder() {
		return existOrder;
	}

	public void setExistOrder(Integer existOrder) {
		this.existOrder = existOrder;
	}

	public Integer getExistCredit() {
		return existCredit;
	}

	public void setExistCredit(Integer existCredit) {
		this.existCredit = existCredit;
	}
}

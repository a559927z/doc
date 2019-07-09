package net.chinahrd.entity.dto.pc.clientImg;

import java.io.Serializable;

/**
 * 纪要信息
 * @author malong 2017-02-22
 */
public class ClientSummaryDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8868871717203915311L;
	private String summaryId;// 纪要id
	private String note;// 纪要内容
	private String keyNote;// 纪要要点
	private String product;// 感兴趣产品
	private String principal;// 沟通负责人

	public String getSummaryId() {
		return summaryId;
	}

	public void setSummaryId(String summaryId) {
		this.summaryId = summaryId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getKeyNote() {
		return keyNote;
	}

	public void setKeyNote(String keyNote) {
		this.keyNote = keyNote;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

}

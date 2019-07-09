package net.chinahrd.db.input.Entity;

import java.io.Serializable;
import java.util.Date;

public class CheckInputdataStatusEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1903405684162549723L;

	private String id;
	private String customer_id;
	private int code;
	private String note;
	private Date date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}

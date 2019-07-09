package net.chinahrd.db.input.Entity;

import java.io.Serializable;
import java.util.Date;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.utils.Identities;

public class LogEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5163607849331729826L;

	@SuppressWarnings("unused")
	private String log_id;
	@SuppressWarnings("unused")
	private String customer_id;
	@SuppressWarnings("unused")
	private String user_id;
	private String module;
	private String text;
	private String user_time;
	private Date start_time;
	private Date end_time;
	private String type;
	private int show_index;

	public LogEntity() {
		super();
	}

	public LogEntity(String module, String text, String user_time,
			Date start_time, Date end_time, String type, int show_index ) {
		super();
		this.module = module;
		this.text = text;
		this.user_time = user_time;
		this.start_time = start_time;
		this.end_time = end_time;
		this.type = type;
		this.show_index = show_index;
	}

	public LogEntity(String log_id, String customer_id, String user_id,
			String module, String text, String user_time, Date start_time,
			Date end_time, String type, int show_index) {
		super();
		this.log_id = log_id;
		this.customer_id = customer_id;
		this.user_id = user_id;
		this.module = module;
		this.text = text;
		this.user_time = user_time;
		this.start_time = start_time;
		this.end_time = end_time;
		this.type = type;
		this.show_index = show_index;
	}
	
	

	public int getShow_index() {
		return show_index;
	}

	public void setShow_index(int show_index) {
		this.show_index = show_index;
	}

	public String getLog_id() {
		return Identities.uuid2();
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getCustomer_id() {
		return EisWebContext.getCustomerId();
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getUser_id() {
		return "System-Event";
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUser_time() {
		return user_time;
	}
	public void setUser_time(String user_time) {
		this.user_time = user_time;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}

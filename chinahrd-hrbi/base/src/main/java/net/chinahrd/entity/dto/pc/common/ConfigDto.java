package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;

/**
 * 配置表Dto
 * 
 * @author jxzhang
 *
 */
public class ConfigDto  implements Serializable {

	private static final long serialVersionUID = 2540022986226166112L;
	
	private String customerId;
	private String configId;
	private String configKey;
	private String configVal;
	private String functionId;
	private String note;

	public ConfigDto() {
	}

	public ConfigDto(String configKey, String configVal) {
		this.configKey = configKey;
		this.configVal = configVal;
	}

	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getConfigKey() {
		return configKey;
	}
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	public String getConfigVal() {
		return configVal;
	}
	public void setConfigVal(String configVal) {
		this.configVal = configVal;
	}
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}

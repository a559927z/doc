package net.chinahrd.entity.dto.pc.laborEfficiency;

public class LaborEfficiencyImportDto extends LaborEfficiencyDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7451254562322831803L;

	private String attId;
	private String customerId;
	private String examineId;
	private int status;
	private String createTime;
	private String examineTime;
	private String dateName;
	
	public String getAttId() {
		return attId;
	}
	public void setAttId(String attId) {
		this.attId = attId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getExamineId() {
		return examineId;
	}
	public void setExamineId(String examineId) {
		this.examineId = examineId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getExamineTime() {
		return examineTime;
	}
	public void setExamineTime(String examineTime) {
		this.examineTime = examineTime;
	}
	public String getDateName() {
		return dateName;
	}
	public void setDateName(String dateName) {
		this.dateName = dateName;
	}
	
}

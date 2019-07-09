package net.chinahrd.entity.dto.pc;

import java.io.Serializable;

/**
 * @author guanjian
 * 组织机构(编制和空缺)
 */
public class OrgChartDto implements Serializable{
	
	private static final long serialVersionUID = -1554172987728526260L;
	/** 组织机构ID */
	private String organizationId;
	/** 组织机构名称 */
	private String organizationName;
	/** 组织机构父ID */
	private String organizationParentId;
	/** 负责人 */
	private String budgetEmpNumberId;
	/** 负责人姓名 */
	private String userName;
	/** 是否有子节点 */
	private boolean hasChildren;
	/** 编制 */
	private int number;
	/** 在职员工数 */
	private int empCount;
	/** 可用编制数 */
	private int usableEmpCount;
	/** 预警类型 0：正常 1：预警 2 超编 */
	private int warnType;
	
	/** 预警阀值 */
	private double warnValue;
	
	/** 是否也子节点 */
	private boolean isLeaf;
	
	/** 树 级别 仅用于树表格 */
	private int level;
	
	
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getOrganizationParentId() {
		return organizationParentId;
	}
	public void setOrganizationParentId(String organizationParentId) {
		this.organizationParentId = organizationParentId;
	}
	public String getBudgetEmpNumberId() {
		return budgetEmpNumberId;
	}
	public void setBudgetEmpNumberId(String budgetEmpNumberId) {
		this.budgetEmpNumberId = budgetEmpNumberId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean getHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getEmpCount() {
		return empCount;
	}
	public void setEmpCount(int empCount) {
		this.empCount = empCount;
	}
	/**
	 * 可用编制数=编制数-在职员工数
	 * 结果负数：就是超编
	 * @return
	 */
	public int getUsableEmpCount() {
		return usableEmpCount=getNumber()-getEmpCount();
	}
	public void setUsableEmpCount(int usableEmpCount) {
		this.usableEmpCount = usableEmpCount;
	}
	
	public int getWarnType() {
		if(getUsableEmpCount()<0){
			return warnType=2;
		}
		//在岗员工数/编制数
		double p=getEmpCount()*1.0/getNumber();
		if(p>=getWarnValue()){
			return warnType=1;
		}
		
		return warnType;
	}
	
	public void setWarnType(int warnType) {
		this.warnType = warnType;
	}
	public double getWarnValue() {
		return warnValue;
	}
	public void setWarnValue(double warnValue) {
		this.warnValue = warnValue;
	}
	public boolean getIsLeaf() {
		return getHasChildren()==true?false:true;
	}
	
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	
	
}

package net.chinahrd.eis.search.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import net.chinahrd.eis.search.SearchConsts;

@Document(indexName = SearchConsts.EMP_INDEX, type = SearchConsts.EMP_TYPE)
public class Employee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2083250484141335950L;
	/** 员工id */
	@Id
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String empId;
	/** 客户id */
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String customerId;
	/** 员工编码 */
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String empKey;
	/** 中文名 */
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String userNameCh;
	/** 员工性质 */
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String empType;
	/** 年龄 */
	@Field(type = FieldType.Double, index = FieldIndex.not_analyzed)
	private double age;
	/** 性别 */
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String sex;
	/** 学历 */
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String degree;
	/** 机构Id */
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String organizationId;
	/** 机构名称 */
	@Field(type = FieldType.String, index = FieldIndex.no)
	private String organizationName;
	/** 岗位id */
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String positionId;
	/** 岗位名称 */
	@Field(type = FieldType.String, index = FieldIndex.no)
	private String positionName;
	/** 序列id */
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String sequenceId;
	/** 序列名称 */
	@Field(type = FieldType.String, index = FieldIndex.no)
	private String sequenceName;
	/** 子序列id */
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String sequenceSubId;
	/** 子序列名称 */
	@Field(type = FieldType.String, index = FieldIndex.no)
	private String sequenceSubName;
	/** 能力层级id */
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String abilityId;
	/** 能力层级名称 */
	@Field(type = FieldType.String, index = FieldIndex.no)
	private String abilityName;
	/** 职衔id */
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String jobTitleId;
	/** 职衔名称 */
	@Field(type = FieldType.String, index = FieldIndex.no)
	private String jobTitleName;

	@Field(type = FieldType.Nested, includeInParent = false)
	private List<Performance> performances;

	@Field(type = FieldType.Nested, includeInParent = false)
	private List<PastResume> pastResumes;
	
	@Field(type = FieldType.Nested, includeInParent = false)
	private List<TrainExperience> trainExperiences;

	/**
	 * 用来保存高亮结果，如果设置了多个字段，则是多个字段内容组合而成。
	 */
	@Field(type = FieldType.String, index = FieldIndex.no)
	private transient String highlightedContent;
	@Field(type = FieldType.Integer, index = FieldIndex.no)
	private transient int mark;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getEmpKey() {
		return empKey;
	}

	public void setEmpKey(String empKey) {
		this.empKey = empKey;
	}

	public String getUserNameCh() {
		return userNameCh;
	}

	public void setUserNameCh(String userNameCh) {
		this.userNameCh = userNameCh;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

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

	public String getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public String getSequenceSubId() {
		return sequenceSubId;
	}

	public void setSequenceSubId(String sequenceSubId) {
		this.sequenceSubId = sequenceSubId;
	}

	public String getSequenceSubName() {
		return sequenceSubName;
	}

	public void setSequenceSubName(String sequenceSubName) {
		this.sequenceSubName = sequenceSubName;
	}

	public String getAbilityId() {
		return abilityId;
	}

	public void setAbilityId(String abilityId) {
		this.abilityId = abilityId;
	}

	public String getAbilityName() {
		return abilityName;
	}

	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getJobTitleId() {
		return jobTitleId;
	}

	public void setJobTitleId(String jobTitleId) {
		this.jobTitleId = jobTitleId;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public List<Performance> getPerformances() {
		return performances;
	}

	public void setPerformances(List<Performance> performances) {
		this.performances = performances;
	}

	public List<PastResume> getPastResumes() {
		return pastResumes;
	}

	public void setPastResumes(List<PastResume> pastResumes) {
		this.pastResumes = pastResumes;
	}
	
	public List<TrainExperience> getTrainExperiences() {
		return trainExperiences;
	}

	public void setTrainExperiences(List<TrainExperience> trainExperiences) {
		this.trainExperiences = trainExperiences;
	}

	public String getHighlightedContent() {
		return highlightedContent;
	}

	public void setHighlightedContent(String highlightedContent) {
		this.highlightedContent = highlightedContent;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

}
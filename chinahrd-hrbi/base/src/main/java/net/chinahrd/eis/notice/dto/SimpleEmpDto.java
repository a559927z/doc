package net.chinahrd.eis.notice.dto;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleEmpDto implements Serializable {
	private static final long serialVersionUID = -6574699449389649718L;

	private String headId; // 领导Id(上级 /机构负责人/BP)
	private String headName; // 领导姓名
	private String telPhone; // 手机
	private String email; // 邮箱
	private String empId; // 员工Id
	private String empName; // 员工姓名
	private String organId; // 员工所属机构Id
	private String organName; // 员工所属机构名称
	private String dateStr; // 时间

	public String getHeadId() {
		return headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public String getEmpName2() {
		if (empName != null) {
			String[] tmp = empName.split(",");
			if (tmp.length > 5) {
				int p = getCharacterPosition(empName, ",", 5);
				return empName.substring(0, p) + "等5人";
			}
		}
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
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

	public String getOrganName2() {
		if (organName != null) {
			String[] tmp = organName.split(",");
			if (tmp.length > 5) {
				int p = getCharacterPosition(organName, ",", 5);
				return organName.substring(0, p) + "等5个组织";
			}
		}
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public int getCharacterPosition(String string, String regex, int n) {
		// 这里是获取"regex"符号的位置
		Matcher slashMatcher = Pattern.compile(regex).matcher(string);
		int mIdx = 0;
		while (slashMatcher.find()) {
			mIdx++;
			// 当"regex"符号第n次出现的位置
			if (mIdx == n) {
				break;
			}
		}
		return slashMatcher.start();
	}

	public static void main(String[] args) {
		SimpleEmpDto dto = new SimpleEmpDto();
		dto.setOrganName("A,B,C,D");
		System.out.println(dto.getOrganName());
	}
}

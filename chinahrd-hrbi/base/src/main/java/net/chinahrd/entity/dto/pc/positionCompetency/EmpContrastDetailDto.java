/**
*net.chinahrd.biz.paper.dto.competency.positionCompetency
*/
package net.chinahrd.entity.dto.pc.positionCompetency;

import java.io.Serializable;
import java.util.List;

import net.chinahrd.utils.CollectionKit;

/**人员对比DTO
 * @author htpeng
 *2016年9月7日下午5:18:33
 */
public class EmpContrastDetailDto  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String empId;
	private String empName;
	private String imgPath;
	private String age;          //年龄
	private String sex;         //性别
	private String degree;       //学历
	private String entryDate;       //出生年月
	private String seniority;   //司龄
	private List<DimensionDto> list;   //所有维度信息

	public String getEmpId() {
		return empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}


	public List<DimensionDto> getList() {
		return list;
	}

	public void setList(List<DimensionDto> list) {
		this.list = list;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
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

	public String getSeniority() {
		return seniority;
	}

	public void setSeniority(String seniority) {
		this.seniority = seniority;
	}
	
	
}

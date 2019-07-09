package net.chinahrd.entity.dto.pc.positionCompetency;

import java.io.Serializable;

/**
 * 招聘看板-高绩效人员信息
 * Created by wqcai on 16/5/18.
 */
public class HighPerfEmpDto implements Serializable {
    private static final long serialVersionUID = -8455346978567257419L;

    private String empId;       //员工ID
    private String sex;         //性别
    private String degreeId;    //学历ID
    private String degree;      //学历
    private String birthDate;//生日


	private String positionalTitles;      //职称
    private int age;      //年龄
    
    private int seniority;      //司龄
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(String degreeId) {
        this.degreeId = degreeId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

	public String getPositionalTitles() {
		return positionalTitles;
	}

	public void setPositionalTitles(String positionalTitles) {
		this.positionalTitles = positionalTitles;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSeniority() {
		return seniority;
	}

	public void setSeniority(int seniority) {
		this.seniority = seniority;
	}

    public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate =birthDate;
	}
    
    
}

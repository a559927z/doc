package net.chinahrd.entity.dto.pc.recruitBoard;

import java.io.Serializable;

/**
 * 招聘看板-招聘岗位进度详情dto
 * Created by wqcai on 16/5/5.
 */
public class RecruitPositionResultDto implements Serializable {
    private static final long serialVersionUID = 828948278540482025L;

    private String id;              //主键ID
    private String username;        //姓名
    private String sex;             //性别
    private String age;             //年龄
    private String degree;          //学历
    private String major;           //专业
    private String school;          //毕业学校
    private String url;             //简历URL


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSex() {
        if(sex.equals("m")){
            return "男";
        }else if(sex.equals("w")){
            return "女";
        }else{
            return "-";
        }
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

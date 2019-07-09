package net.chinahrd.entity.dto.pc.common;


import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 备忘录返回Dto
 * Created by wqcai on 15/6/30.
 */
public class MemoResultDto implements Serializable {
    private static final long serialVersionUID = -2679591660502999860L;


    private String id;          //备忘录ID
    private String customerId;      //客户ID
    private String content;         //内容
    private String createEmpId;    //创建者员工ID
    private String userName;        //创建者用户名称
    private String userNameCh;      //创建者中文名
    private String imgPath;         //创建者头像
    private Timestamp createTime;   //创建时间
    private Integer isRead;         //是否已读  0:未读  1：已读
    private Integer hasDelete = 0;      //是否可删除 0:否  1：是

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNameCh() {
        return userNameCh;
    }

    public void setUserNameCh(String userNameCh) {
        this.userNameCh = userNameCh;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm",timezone = "GMT+8")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getHasDelete() {
        return hasDelete;
    }

    public void setHasDelete(Integer hasDelete) {
        this.hasDelete = hasDelete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateEmpId() {
        return createEmpId;
    }

    public void setCreateEmpId(String createEmpId) {
        this.createEmpId = createEmpId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}

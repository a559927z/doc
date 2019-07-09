package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 备忘录添加Dto
 * Created by wqcai on 15/7/3.
 */
public class MemoCreateDto implements Serializable {
    private static final long serialVersionUID = -8889747627998212750L;

    private String memoId;              //备忘录ID
    private String customerId;          //客户ID
    private String quataId;             //指标ID
    private String content;             //内容
    private String organizationId;      //组织机构ID
    private String createEmpId;        //创建人ID
    private Timestamp createTime;       //创建时间

    public String getMemoId() {
        return memoId;
    }

    public void setMemoId(String memoId) {
        this.memoId = memoId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getQuataId() {
        return quataId;
    }

    public void setQuataId(String quataId) {
        this.quataId = quataId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateEmpId() {
        return createEmpId;
    }

    public void setCreateEmpId(String createEmpId) {
        this.createEmpId = createEmpId;
    }
}

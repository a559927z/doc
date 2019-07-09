package net.chinahrd.common;

import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体，不含租户id
 */
public class BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected String createBy;

    protected Date createDate;

    protected String updateBy;

    protected Date updateDate;

    @Version
    protected Integer revision;

    public void setCreateInfo(String userId) {
        Date now = new Date();
        setCreateBy(userId);
        setCreateDate(now);
        setUpdateBy(userId);
        setUpdateDate(now);
        setRevision(1);
    }

    public void setUpdateInfo(String userId) {
        Date now = new Date();
        setUpdateBy(userId);
        setUpdateDate(now);
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }
}

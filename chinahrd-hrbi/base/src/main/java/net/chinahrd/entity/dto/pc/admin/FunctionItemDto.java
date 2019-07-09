package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 功能操作Dto
 * Created by wqcai on 15/6/15.
 */
public class FunctionItemDto implements Serializable {

    private static final long serialVersionUID = -7239597288563643687L;

    private String functionItemId;
    private String customerId;
    private String functionId;
    private String itemCode;
    private String note;
    private String createUserId;
    private Timestamp createTime;
    private String modifyUserId;
    private Timestamp modifyTime;

    public String getFunctionItemId() {
        return functionItemId;
    }

    public void setFunctionItemId(String functionItemId) {
        this.functionItemId = functionItemId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }
}

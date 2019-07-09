package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.springframework.util.StringUtils;

/**
 * 角色功能Dto
 * Created by wqcai on 15/6/17.
 */
public class RoleFunctionDto implements Serializable {
    private static final long serialVersionUID = 914052078430171869L;

    private String roleFunctionId;
    private String customerId;
    private String functionId;
    private String functionName;
    private String pathUrl;
    private Integer isMenu;
    private String roleId;
    private String functionItem;
    private String[] functionItems;
    private String itemName;
    private String[] itemNames;
    private String pid;
    private List<RoleFunctionDto> childs;
    private String createUserId;
    private Timestamp createTime;

    public String getRoleFunctionId() {
        return roleFunctionId;
    }

    public void setRoleFunctionId(String roleFunctionId) {
        this.roleFunctionId = roleFunctionId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getFunctionItem() {
        return functionItem;
    }

    public void setFunctionItem(String functionItem) {
        this.functionItem = functionItem;
    }

    public String[] getFunctionItems() {
        return null == functionItems && null != functionItem ? functionItem.split(",") : functionItems;
    }

    public void setFunctionItems(String[] functionItems) {
        this.functionItems = functionItems;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String[] getItemNames() {
        return null == itemNames && null != itemName ? itemName.split(",") : itemNames;
    }

    public void setItemNames(String[] itemNames) {
        this.itemNames = itemNames;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public List<RoleFunctionDto> getChilds() {
        return childs == null ? Collections.<RoleFunctionDto>emptyList() : childs;
    }

    public void setChilds(List<RoleFunctionDto> childs) {
        this.childs = childs;
    }

    public RoleFunctionDto findChildById(String id) {
        if (null != childs && !childs.isEmpty()) {
            for (RoleFunctionDto sub : childs) {
                if (StringUtils.hasText(id) && id.equals(sub.getFunctionId())) {
                    return sub;
                }
            }
        }
        return null;
    }

    public void addChild(RoleFunctionDto dto) {
        if (this.childs == null) {
            childs = new ArrayList<>();
        }
        childs.add(dto);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }


}

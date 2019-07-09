package net.chinahrd.entity.dto.pc.admin;

import java.io.Serializable;

/**
 * 功能维的树形Dto
 * Created by wqcai on 15/6/12.
 */
public class FunctionTreeDto implements Serializable {
    private static final long serialVersionUID = 1045384892270632565L;

    private String id;
    private String functionKey;
    private String name;
    private String pid;
    private String urlPath;    //url属性是ztree的关键字,添加的话则会添加超链接
    private Integer showIndex;
    private Integer isMenu;     //是否菜单栏 0：否 1：是
    private String fullPath;
    private String note;
    private Integer type;   //0：功能 1：功能操作
    private boolean open = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(Integer showIndex) {
        this.showIndex = showIndex;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getFunctionKey() {
        return functionKey;
    }

    public void setFunctionKey(String functionKey) {
        this.functionKey = functionKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }
}

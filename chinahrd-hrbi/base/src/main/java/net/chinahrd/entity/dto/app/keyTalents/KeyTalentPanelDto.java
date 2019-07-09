package net.chinahrd.entity.dto.app.keyTalents;


import java.io.Serializable;
import java.util.Date;

/**
 * 关键人才库各个面板以及对应数量dto
 * Created by htpeng on 16/01/21 0021.
 */
public class KeyTalentPanelDto implements Serializable {
 

	private static final long serialVersionUID = 6222681418068718957L;
	private String pid;                       //上级ID
	private String id;                       //ID
    private String name;                       //name
    private int count;                       //数量
    private int hasChildren;                //是否有子节点
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public int getHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(int hasChildren) {
		this.hasChildren = hasChildren;
	}

	
    
}

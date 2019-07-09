package net.chinahrd.entity.dto.pc.emp;


import java.io.Serializable;

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

	
    
}

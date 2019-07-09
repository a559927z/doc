package net.chinahrd.entity.dto.pc.competency;

import java.io.Serializable;


public class SatisfactionDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7783400632609568326L;
	
	private String date;
	private String curorgscore;
	private String id;
	private String name;
	private String parent;
	private String comscore;
	private String level;
	private String isLeaf;
	private String expanded;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCurorgscore() {
		return curorgscore;
	}
	public void setCurorgscore(String curorgscore) {
		this.curorgscore = String.format("%.1f",Double.parseDouble(curorgscore)*100)+"%";
	}
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
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public String getComscore() {
		return comscore;
	}
	public void setComscore(String comscore) {
		this.comscore = String.format("%.1f",Double.parseDouble(comscore)*100)+"%";
	}
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getExpanded() {
		return expanded;
	}
	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}
	
}

package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;


/**
 * 关系网图nodes数据dto
 * Created by wqcai on 16/12/20 020.
 */
public class GraphNodesDto implements Serializable {

    private static final long serialVersionUID = -8366707715465661279L;
    private String id;              //主键ID
    private String text;            //节点名称（简称）
    private String name;            //节点名称（简称）
    private String fullName;        //节点名称（全称）
    private int symbolSize;         //节点展示大小
    private int category;           //节点分类
    private String parent;          //父级节点（links用）
    private int type = 0;           //特殊类别，0：单个 1：多个
    private Double salesTarget;		//销售目标金额
    private Double perNum;			//百分比
    private Object object;				//节点信息
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? text : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(int symbolSize) {
        this.symbolSize = symbolSize;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

	public Double getSalesTarget() {
		return salesTarget == null ? 0d : salesTarget;
	}

	public void setSalesTarget(Double salesTarget) {
		this.salesTarget = salesTarget;
	}

	public Double getPerNum() {
		return perNum == null ? 0d : perNum;
	}

	public void setPerNum(Double perNum) {
		this.perNum = perNum;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}

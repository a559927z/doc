package net.chinahrd.entity.dto.pc.salesBoard;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.StringUtils;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.utils.CollectionKit;
/**
 * 销售地图简易展示dto
 * Created by malong on 2016-11-14
 */
public class SalesMapsSimpleCountDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4065537698021933324L;
	private String xLabelId; // x轴ID
	private String xLabel; // x轴名称
	private String yLabelId; // y轴ID
	private String yLabel; // y轴名称
	private Integer countNum; // 人员统计数量
	private String empIds; // 人员id集合
	private String empNames; // 人员名称集合
	private List<KVItemDto> data;
	private String team;
	private String organId;// 组织机构id

	public String getxLabelId() {
		return xLabelId;
	}

	public void setxLabelId(String xLabelId) {
		this.xLabelId = xLabelId;
	}

	public String getxLabel() {
		return xLabel;
	}

	public void setxLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	public String getyLabelId() {
		return yLabelId;
	}

	public void setyLabelId(String yLabelId) {
		this.yLabelId = yLabelId;
	}

	public String getyLabel() {
		return yLabel;
	}

	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	public Integer getCountNum() {
		return countNum;
	}

	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}

	public String getEmpIds() {
		return empIds;
	}

	public void setEmpIds(String empIds) {
		this.empIds = empIds;
	}

	public String getEmpNames() {
		return empNames;
	}

	public void setEmpNames(String empNames) {
		this.empNames = empNames;
	}

	public List<KVItemDto> getData() {
		return data == null ? getItems() : data;
	}

	public void setData(List<KVItemDto> data) {
		this.data = data;
	}
	
	private List<KVItemDto> getItems() {
        if (StringUtils.isEmpty(empIds) || StringUtils.isEmpty(empNames)) return null;
        String[] ids = empIds.split(","), names = empNames.split(",");
        List<KVItemDto> list = CollectionKit.newList();
        for (int i = 0; i < ids.length; i++) {
            list.add(new KVItemDto(ids[i], names[i]));
        }
        return list;
    }

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

}

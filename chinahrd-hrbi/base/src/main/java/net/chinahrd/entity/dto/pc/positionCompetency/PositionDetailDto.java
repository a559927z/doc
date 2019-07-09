/**
*net.chinahrd.biz.paper.dto.competency.positionCompetency
*/
package net.chinahrd.entity.dto.pc.positionCompetency;

import java.io.Serializable;
import java.util.List;

import net.chinahrd.utils.CollectionKit;

/**岗位胜任度  岗位面板DTO
 * @author htpeng
 *2016年9月7日下午5:18:33
 */
public class PositionDetailDto  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String positionId;
	private String positionName;
	private Double rate;               //平均胜任度
	private List<DimensionDto> list;   //所有维度信息
	
	private DimensionDto highDimension;         //匹配度最高维度
	private DimensionDto lowDimension;          //匹配度最低维度
	
	private int totalEmp;               //全部人员数量
	
	private List<EmpDetailDto> empList;         //匹配度最高人员  匹配度最低人员
	
	
	
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public List<DimensionDto> getList() {
		return list;
	}
	public void setList(List<DimensionDto> list) {
		this.list = list;
		if(list!=null){
			for(DimensionDto dto:list){
		
				if(highDimension==null||highDimension.getRate()<dto.getRate()){
					highDimension=dto;
				}
				if(lowDimension==null||lowDimension.getRate()>dto.getRate()){
					lowDimension=dto;
				}
			}
		}
	}
	public DimensionDto getHighDimension() {
		return highDimension;
	}
	public void setHighDimension(DimensionDto highDimension) {
		this.highDimension = highDimension;
	}
	public DimensionDto getLowDimension() {
		return lowDimension;
	}
	public void setLowDimension(DimensionDto lowDimension) {
		this.lowDimension = lowDimension;
	}
	public int getTotalEmp() {
		return totalEmp;
	}
	public void setTotalEmp(int totalEmp) {
		this.totalEmp = totalEmp;
	}
	public List<EmpDetailDto> getEmpList() {
		return empList;
	}
	public void setEmpList(List<EmpDetailDto> empList) {
		this.empList = empList;
	}
	
}

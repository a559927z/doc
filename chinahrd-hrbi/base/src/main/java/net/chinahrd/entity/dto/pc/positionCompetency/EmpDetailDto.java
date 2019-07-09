/**
*net.chinahrd.biz.paper.dto.competency.positionCompetency
*/
package net.chinahrd.entity.dto.pc.positionCompetency;

import java.io.Serializable;
import java.util.List;

import net.chinahrd.utils.CollectionKit;

/**岗位胜任度  人员面板DTO
 * @author htpeng
 *2016年9月7日下午5:18:33
 */
public class EmpDetailDto  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String positionId;
	/**
	 * @return the positionId
	 */
	public String getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	private String empId;
	private String empName;
	private String positionName;
	private Double rate;               //平均胜任度
	private List<DimensionDto> list;   //所有维度信息
	
	private DimensionDto high;         //最高维度
	private DimensionDto low;          //最低维度
	
	private List<DimensionDto> fail;  //未达标维度

	public String getEmpId() {
		return empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
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
		setList(list,false);
	}
	public void setList(List<DimensionDto> list,boolean cal) {
		this.list = list;
		if(cal){
			for(DimensionDto dto:list){
				double r=dto.getReallyScore()/dto.getExpectScore();
				if(r>1){
					r=1;
				}
				dto.setRate(r);
			}
		}else{
			if(list!=null){
				fail=CollectionKit.newList();
				for(DimensionDto dto:list){
					if(dto.getExpectScore()>dto.getReallyScore()){
						fail.add(dto);
					}
					if(high==null||high.getReallyScore()<dto.getReallyScore()){
						high=dto;
					}
					if(low==null||low.getReallyScore()>dto.getReallyScore()){
						low=dto;
					}
				}
			}
		}
		
	}
	public DimensionDto getHigh() {
		return high;
	}

	public void setHigh(DimensionDto high) {
		this.high = high;
	}

	public DimensionDto getLow() {
		return low;
	}

	public void setLow(DimensionDto low) {
		this.low = low;
	}

	public List<DimensionDto> getFail() {
		return fail;
	}

	public void setFail(List<DimensionDto> fail) {
		this.fail = fail;
	}
	
	
	
}

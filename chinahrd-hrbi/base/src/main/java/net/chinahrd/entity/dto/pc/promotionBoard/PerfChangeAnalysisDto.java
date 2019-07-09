package net.chinahrd.entity.dto.pc.promotionBoard;

import java.io.Serializable;

/**
 * 绩效等级分析dto
 * 
 * @author jxzhang on 2017年5月25日
 * @Verdion 1.0 版本
 */
public class PerfChangeAnalysisDto implements Serializable {

	private static final long serialVersionUID = -910000684765376837L;
	private String empId; // 员工ID
	private String perfChangeId; // 主键ID
	private String perfKey; // 绩效key
	private String perfName; // 绩效
	private Integer curtName; // 得分
	private Integer yearMonth; // 年月

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getPerfChangeId() {
		return perfChangeId;
	}

	public void setPerfChangeId(String perfChangeId) {
		this.perfChangeId = perfChangeId;
	}

	public String getPerfKey() {
		return perfKey;
	}

	public void setPerfKey(String perfKey) {
		this.perfKey = perfKey;
	}

	public String getPerfName() {
		return perfName;
	}

	public void setPerfName(String perfName) {
		this.perfName = perfName;
	}

	public Integer getCurtName() {
		return curtName;
	}

	public void setCurtName(Integer curtName) {
		this.curtName = curtName;
	}

	public Integer getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}

}

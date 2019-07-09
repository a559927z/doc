package net.chinahrd.entity.dto.pc.accordDismiss;

import java.io.Serializable;

/**
 * 流失记录dto
 */
public class DismissRecordDto  implements Serializable {
	
	private static final long serialVersionUID = -7822702031585607920L;
	/**员工id */
    private String empId;
    /**0：其他；1：建议重新录用；2：不建议重新录用 */
    private int reHire;
    /**流失去向 */
    private String whereAbouts;
    /**流失风险类型 */
    private int type;
	/** 流失风险名称 */
	private String runOffName;

    public DismissRecordDto() {
    }

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public int getReHire() {
		return reHire;
	}

	public void setReHire(int reHire) {
		this.reHire = reHire;
	}

	public String getWhereAbouts() {
		return whereAbouts;
	}

	public void setWhereAbouts(String whereAbouts) {
		this.whereAbouts = whereAbouts;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRunOffName() {
		return runOffName;
	}

	public void setRunOffName(String runOffName) {
		this.runOffName = runOffName;
	}

}

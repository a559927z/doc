package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 备忘录状态dto
 * Created by wqcai on 15/7/3.
 */
public class MemoStatusDto implements Serializable {
    private static final long serialVersionUID = -6719283809176500148L;

    private String memoStatusId;        //备忘录状态ID
    private String customerId;          //客户ID
    private String memoId;              //备忘录ID
    private String empId;               //员工ID
    private Timestamp readTime;         //读取时间

    public MemoStatusDto() {
    }

    public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getMemoId() {
        return memoId;
    }

    public void setMemoId(String memoId) {
        this.memoId = memoId;
    }

    public String getMemoStatusId() {
        return memoStatusId;
    }

    public void setMemoStatusId(String memoStatusId) {
        this.memoStatusId = memoStatusId;
    }


    public Timestamp getReadTime() {
        return readTime;
    }

    public void setReadTime(Timestamp readTime) {
        this.readTime = readTime;
    }
}

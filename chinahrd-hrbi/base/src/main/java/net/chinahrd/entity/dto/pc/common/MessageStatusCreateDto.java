package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 消息状态添加dto
 * Created by wqcai on 15/7/3.
 */
public class MessageStatusCreateDto implements Serializable {
    private static final long serialVersionUID = -6719283809176500148L;

    private String messageStatusId;        //备忘录状态ID
    private String customerId;          //客户ID
    private String messageId;              //备忘录ID
    private String empId;               //员工ID
    private Timestamp readTime;         //读取时间

    public MessageStatusCreateDto() {
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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageStatusId() {
        return messageStatusId;
    }

    public void setMessageStatusId(String messageStatusId) {
        this.messageStatusId = messageStatusId;
    }


    public Timestamp getReadTime() {
        return readTime;
    }

    public void setReadTime(Timestamp readTime) {
        this.readTime = readTime;
    }
}

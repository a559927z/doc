package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 消息添加Dto
 * Created by htpeng on 16/1/14.
 */
public class MessageCreateDto implements Serializable {
    private static final long serialVersionUID = -8889747627998212750L;

    private String messageId;              //备忘录ID
    private String customerId;          //客户ID
    private String quataId;             //指标ID
    private String content;             //内容
    private String organizationId;      //组织机构ID
    private String createEmpId;        //创建人ID
    private String url;        //点对点的路径
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private int type;        //类型  1--指标    2--人对人 
    private Timestamp createTime;       //创建时间

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getQuataId() {
        return quataId;
    }

    public void setQuataId(String quataId) {
        this.quataId = quataId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateEmpId() {
        return createEmpId;
    }

    public void setCreateEmpId(String createEmpId) {
        this.createEmpId = createEmpId;
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
    
    
}

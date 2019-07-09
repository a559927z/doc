package net.chinahrd.entity.dto.pc.common;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 消息返回Dto
 * Created by wqcai on 15/6/30.
 */
public class MessageResultDto implements Serializable {
    private static final long serialVersionUID = -2679591660502999860L;


    private String id;          //消息ID
    private String customerId;      //客户ID
    private String content;         //内容
    private String createEmpId;    //创建者员工ID
    private String userName;        //创建者用户名称
	private String userNameCh;      //创建者中文名
    private Timestamp createTime;   //创建时间
    private String organId;         //机构
    private String organName;       //机构名称
    private String target; //指标
    private String targetUrl; //指标路径
    private int type; //消息类型
    
    public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNameCh() {
        return userNameCh;
    }

    public void setUserNameCh(String userNameCh) {
        this.userNameCh = userNameCh;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateEmpId() {
        return createEmpId;
    }

    public void setCreateEmpId(String createEmpId) {
        this.createEmpId = createEmpId;
    }
    
    
}

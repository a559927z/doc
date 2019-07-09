package net.chinahrd.entity.dto.pc.emp;

import java.io.Serializable;

/**
 * @author zhiwei
 * @time 2016年12月5日下午3:47:05
 * @version 20162016年12月5日 招聘渠道
 */
public class Channel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String channelId; // 招聘渠道ID
	private String customerId; // 客户ID
	private String channelKey; // 渠道key
	private String channelName; // 渠道名
	private String showIndex; // 显示顺序

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getChannelKey() {
		return channelKey;
	}

	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(String showIndex) {
		this.showIndex = showIndex;
	}

}

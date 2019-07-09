package net.chinahrd.entity.dto.pc;

import java.io.Serializable;

/**
 * 子序列dto
 * Created by zhiwei on 16/12/05 0001.
 */
public class SubSequenceItemsDto implements Serializable {
    private static final long serialVersionUID = 5790752371586664646L;

    private String subId;                      //主键ID/子序列ID
    private String itemKey;                     //key
    private String itemName;                    //序列名称
    private Integer showIndex;                  //排序索引
    private String curtName;                      //前缀
    private String customerId;					//客户ID
    private String sequenceId;					//序列ID

    public SubSequenceItemsDto() {
    }

    public SubSequenceItemsDto(String subId, String itemName) {
        this.subId = subId;
        this.itemName = itemName;
    }

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getItemKey() {
		return itemKey;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}

	public String getCurtName() {
		return curtName;
	}

	public void setCurtName(String curtName) {
		this.curtName = curtName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

}

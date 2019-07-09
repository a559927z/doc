package net.chinahrd.entity.dto.pc;

import net.chinahrd.utils.CollectionKit;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 主序列及子序列dto
 * Created by wqcai on 15/10/30 0030.
 */
public class SequenceItemsDto implements Serializable {
    private static final long serialVersionUID = 5790752371586664646L;

    private String itemId;                      //主键ID
    private String itemKey;                     //key
    private String itemName;                    //序列名称
    private Integer showIndex;                  //排序索引
    private String prefix;                      //前缀
    private int empCount;                   //员工人数
    private List<SequenceItemsDto> subItems;    //子项集合

    public SequenceItemsDto() {
    }

    public SequenceItemsDto(String itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public SequenceItemsDto(String itemId, String itemName, Integer showIndex) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.showIndex = showIndex;
    }


    public SequenceItemsDto(String itemId, String itemName, Integer showIndex, int empCount) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.showIndex = showIndex;
        this.empCount = empCount;
    }

    public SequenceItemsDto(String itemId, String itemName, String prefix, Integer showIndex, int empCount) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.prefix = prefix;
        this.showIndex = showIndex;
        this.empCount = empCount;
    }

    public int getEmpCount() {
        return empCount;
    }

    public void setEmpCount(int empCount) {
        this.empCount = empCount;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public List<SequenceItemsDto> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<SequenceItemsDto> subItems) {
        this.subItems = subItems;
    }

    public void addSubItem(SequenceItemsDto item) {
        if (this.subItems == null) {
            subItems = CollectionKit.newList();
        }
        subItems.add(item);
    }

    public SequenceItemsDto findSubById(String id) {
        if (null != subItems && !subItems.isEmpty()) {
            for (SequenceItemsDto subItem : subItems) {
                if (StringUtils.hasText(id) && id.equals(subItem.getItemId())) {
                    return subItem;
                }
            }
        }
        return null;
    }

    public Integer getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(Integer showIndex) {
        this.showIndex = showIndex;
    }
}

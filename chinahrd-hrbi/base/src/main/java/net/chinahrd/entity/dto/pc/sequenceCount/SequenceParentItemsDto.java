package net.chinahrd.entity.dto.pc.sequenceCount;

import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import net.chinahrd.utils.CollectionKit;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 职位序列总集合dto
 * Created by wqcai on 15/11/03 0003.
 */
public class SequenceParentItemsDto implements Serializable {
    private static final long serialVersionUID = -3389217507779394431L;

    private String id;                          //主键ID
    private String name;                        //名称
    private boolean hasJobTitle;                //是否有职衔
    private List<SequenceItemsDto> legends;     //职位层级集合
    private List<SequenceItemsDto> subItems = CollectionKit.newList();    //子项数据集合

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasJobTitle() {
        return hasJobTitle;
    }

    public void setHasJobTitle(boolean hasJobTitle) {
        this.hasJobTitle = hasJobTitle;
    }

    public List<SequenceItemsDto> getLegends() {
        return legends;
    }

    public void setLegends(List<SequenceItemsDto> legends) {
        this.legends = legends;
    }

    public void addLegend(SequenceItemsDto node) {
        if (this.legends == null) {
            legends = CollectionKit.newList();
        }
        legends.add(node);
    }

    public SequenceItemsDto findLegendById(String id) {
        if (null != legends && !legends.isEmpty()) {
            for (SequenceItemsDto sub : legends) {
                if (StringUtils.hasText(id) && id.equals(sub.getItemId()))
                    return sub;
            }
        }
        return null;
    }

    public List<SequenceItemsDto> getSubItems() {
        return subItems == null ? CollectionKit.<SequenceItemsDto>newList() : subItems;
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
}

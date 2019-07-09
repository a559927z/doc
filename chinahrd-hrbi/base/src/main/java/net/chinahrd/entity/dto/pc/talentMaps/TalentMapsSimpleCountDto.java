package net.chinahrd.entity.dto.pc.talentMaps;

import java.io.Serializable;
import java.util.List;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.utils.CollectionKit;

import org.springframework.util.StringUtils;

/**
 * 人才地图简易展示dto
 * Created by wqcai on 16/08/03 003.
 */
public class TalentMapsSimpleCountDto implements Serializable {
    private static final long serialVersionUID = 432259933489915609L;
    private String xLabelId;        //x轴ID
    private String xLabel;          //x轴名称
    private String yLabelId;        //y轴ID
    private String yLabel;          //y轴名称
    private Integer countNum;       //人员统计数量
    private String empIds;          //人员id集合
    private String empNames;        //人员名称集合
    private List<KVItemDto> data;

    public String getxLabel() {
        return xLabel;
    }

    public void setxLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    public String getyLabel() {
        return yLabel;
    }

    public void setyLabel(String yLabel) {
        this.yLabel = yLabel;
    }

    public String getEmpIds() {
        return empIds;
    }

    public void setEmpIds(String empIds) {
        this.empIds = empIds;
    }

    public String getEmpNames() {
        return empNames;
    }

    public void setEmpNames(String empNames) {
        this.empNames = empNames;
    }

    public List<KVItemDto> getData() {
        return data == null ? getItems() : data;
    }

    public void setData(List<KVItemDto> data) {
        this.data = data;
    }

    private List<KVItemDto> getItems() {
        if (StringUtils.isEmpty(empIds) || StringUtils.isEmpty(empNames)) return null;
        String[] ids = empIds.split(","), names = empNames.split(",");
        List<KVItemDto> list = CollectionKit.newList();
        for (int i = 0; i < ids.length; i++) {
            list.add(new KVItemDto(ids[i], names[i]));
        }
        return list;
    }

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public String getxLabelId() {
        return xLabelId;
    }

    public void setxLabelId(String xLabelId) {
        this.xLabelId = xLabelId;
    }

    public String getyLabelId() {
        return yLabelId;
    }

    public void setyLabelId(String yLabelId) {
        this.yLabelId = yLabelId;
    }
}

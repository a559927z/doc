package net.chinahrd.entity.dto.pc.monthReport;

import net.chinahrd.utils.ArithUtil;

import java.io.Serializable;

/**
 * 月报异动相关dto
 * Created by wqcai on 16/08/29 029.
 */
public class MonthReportChangesDto implements Serializable {
    private static final long serialVersionUID = -4286722523206122623L;

    private String itemId;              //职级ID/机构ID
    private String itemName;            //职级名称/机构名称
    private Integer entry;               //入职（异动类型为3）
    private Integer transferIn;          //调入（异动类型为2）
    private Integer transferOut;         //调出（异动类型为4）
    private Integer dimission;           //离职（异动类型为5）
    private Integer pureFlow;           //净流动：（入职+调入）-（离职+调出）

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public Integer getEntry() {
        return entry;
    }

    public void setEntry(Integer entry) {
        this.entry = entry;
    }

    public Integer getTransferIn() {
        return transferIn;
    }

    public void setTransferIn(Integer transferIn) {
        this.transferIn = transferIn;
    }

    public Integer getTransferOut() {
        return transferOut;
    }

    public void setTransferOut(Integer transferOut) {
        this.transferOut = transferOut;
    }

    public Integer getDimission() {
        return dimission;
    }

    public void setDimission(Integer dimission) {
        this.dimission = dimission;
    }

    public Integer getPureFlow() {
        if (pureFlow != null) return pureFlow;
        Double value = ArithUtil.sub(ArithUtil.sum(entry, transferIn), ArithUtil.sum(dimission, transferOut));
        return Integer.valueOf(value.intValue());
    }

    public void setPureFlow(Integer pureFlow) {
        this.pureFlow = pureFlow;
    }
}

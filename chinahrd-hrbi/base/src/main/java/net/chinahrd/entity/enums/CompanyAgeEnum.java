package net.chinahrd.entity.enums;



/**
 * 司龄对应枚举
 * Created by wqcai on 15/10/08 0008.
 */
public enum CompanyAgeEnum {
    ONE(1, 0, 3, "3个月以内"),
    TWO(2, 3, 12, "3个月-1年"),
    THREE(3, 12, 36, "1年-3年"),
    FOUR(4, 36, 60, "3年-5年"),
    FIVE(5, 60, null, "5年以上");

    private int typeId;     //类型ID
    private Integer min;    //最小司龄
    private Integer max;    //最大司龄
    private String desc;    //司龄描述

    CompanyAgeEnum(int typeId, Integer min, Integer max, String desc) {
        this.typeId = typeId;
        this.min = min;
        this.max = max;
        this.desc = desc;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDesc(int typeId) {
        for (CompanyAgeEnum ageType : CompanyAgeEnum.values()) {
            if (ageType.getTypeId() == typeId) {
                return ageType.getDesc();
            }
        }
        return null;
    }
}

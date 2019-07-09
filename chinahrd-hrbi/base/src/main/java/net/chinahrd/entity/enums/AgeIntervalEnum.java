package net.chinahrd.entity.enums;

/**
 * 年龄区间枚举
 * Created by wqcai on 16/07/26 0003.
 */
public enum AgeIntervalEnum {
    ONE(1, 0, 23, "23岁以下"),
    TWO(2, 23, 28, "23-28岁"),
    THREE(3, 28, 35, "28-35岁"),
    FOUR(4, 35, 45, "35-45岁"),
    FIVE(5, 45, 55, "45-55岁"),
    SIX(6, 55, null, "55岁以上");

    private int typeId;     //类型ID
    private Integer min;    //最小年龄
    private Integer max;    //最大年龄
    private String desc;    //年龄描述

    AgeIntervalEnum(int typeId, Integer min, Integer max, String desc) {
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
        for (AgeIntervalEnum ageType : AgeIntervalEnum.values()) {
            if (ageType.getTypeId() == typeId) {
                return ageType.getDesc();
            }
        }
        return null;
    }
}

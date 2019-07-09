package net.chinahrd.entity.dto.pc.manage;


import java.io.Serializable;

/**
 * 用户首页配置Dto
 * Created by wqcai on 2016/1/21 021.
 */
public class HomeConfigDto implements Serializable {
    private static final long serialVersionUID = 3419058466368389245L;
    /** 配置Id(主键ID) */
    private String functionConfigId;
    /** 客户ID */
    private String customerId;
    /** 员工ID */
    private String empId;
    /** 页面ID/指标ID */
    private String functionId;
    /** 卡片编码 */
    private String cardCode;
    /** 功能是否可见 */
    private boolean isView;
    /** 显示索引 */
    private int showIndex;
    /***** 页面展示需要 ******/
    /** 页面模块 */
    private int module;
    /** 页面描述 */
    private String name;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public boolean isView() {
        return isView;
    }

    public void setView(boolean view) {
        isView = view;
    }

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }

    public String getFunctionConfigId() {
        return functionConfigId;
    }

    public void setFunctionConfigId(String functionConfigId) {
        this.functionConfigId = functionConfigId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }
}

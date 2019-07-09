package net.chinahrd.entity.enums;

/**
 * 首页配置相关枚举
 * Created by wqcai on 2016/1/21 021.
 */
public enum HomeConfigEnum {
    DIMISSION_RISK(1, "离职风险"),
    HIGH_PERFORMANCE(1, "高绩效未晋升"),
    Low_PERFORMANCE(1, "低绩效未调整"),
    LIFE_NO_BALANCE(1, "工作生活平衡欠佳"),
    TANLENT_GAIN_OR_LOSS(2, "当季人才损益"),
    PERFORMANCE_TARGET(2, "绩效目标"),
    TEAM_PORTRAYAL(2, "团队画像"),
    TEAM_REMIND(2, "团队提醒"),
    EMPLOYEE_MANAGE(2, "员工管理"),
    SUBORDINATE_ORGAN(2, "下属组织信息");

    /** 卡片编码 */
    private int moduleId;
    /** 描述/标题 */
    private String title;

    HomeConfigEnum(int moduleId,String title) {
        this.moduleId = moduleId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }
}

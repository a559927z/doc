package net.chinahrd.db.input.enums;

public enum LogCode {

	// 维度表
	DIM_ORGANIZATION(100, "【机构-维度表】", "dim_organization"),
	ALL_DIM_TABLE(200, "【所有维度表】", "dimTables"),
	DIM_SEQUENCE(201, "【主序列维】", "dim_sequence"),
	DIM_SEQUENCE_SUB(202, "【子序列维】", "dim_sequence_sub"),
	DIM_JOB_TITLE(203, "【职衔维】", "dim_job_title"),
	DIM_ABILITY(204, "【能力层级维】", "dim_ability"),
	DIM_ABILITY_LV(205, "【能力层级子级维】", "dim_ability_lv"),
	DIM_KEY_TALENT_TYPE(206, "【关键人才库维】", "dim_key_talent_type"),
	DIM_COURSE(207, "【课程维】", "dim_course"),
	DIM_COURSE_TYPE(208, "【课程类别维】", "dim_course_type"),
	DIM_RUN_OFF(209, "【流失维】", "dim_run_off"),
	DIM_CITY(210, "【城市】", "dim_city"),
	DIM_STRUCTURE(211, "【工资结构】", "dim_structure"),
	DIM_PROJECT_TYPE(212, "【项目类型】", "dim_project_type"),
	DIM_PROJECT_INPUT_TYPE(213, "【项目投入费用类型】", "dim_project_input_type"),
	DIM_POSITION(214, "【岗位维】", "dim_position"),
	DIM_CHANGE_TYPE(215, "【异动类型-维度表】", "dim_change_type"),
	DIM_CHANNEL(216, "【招聘渠道维】", "dim_channel"),
	DIM_DISMISSION_WEEK(217, "【离职周期维度】", "dim_dismission_week"),
	DIM_ENCOURAGES(218, "【激励要素-维度表】", "dim_encourages"),
	DIM_PERFORMANCE(219, "【绩效-维度表】", "dim_performance"),
	DIM_POPULATION(220, "【人群范围-维度表】", "dim_population"),
	DIM_QUALITY(221, "【岗位能力素质维】", "dim_quality"),
	DIM_CHECKWORK_TYPE(222, "【异动类型维】", "dim_checkwork_type"),
	
	
	// 基础表
	V_DIM_EMP(301, "【员工信息表】", "v_dim_emp"),
	UNDERLING(302, "【我的下属】", "underling"),
	EMP_OVER_TIMER(303, "【员工加班记录-天/月】", "emp_overtime_day/emp_overtime"),	
	// 历史表
	HISTORY_EMP_COUNT(304, "【总人数】", "history_emp_count"),
	HISTORY_EMP_COUNT_MONTH(305, "【月度人数】", "history_emp_count_month"),
	JOB_CHANGE(306, "【工作异动】", "job_change"),
	
	// 业务表
	TARGET_BENEFIT_VALUE(401, "【明年目标人均效益】", "target_benefit_value"),
	TRADE_PROFIT(402, "【营业利润】", "trade_profit"),
	FACT_FTE(403, "【等效员工】", "fact_fte"),
	MONTHLY_EMP_COUNT(502, "【流失率】", "monthly_emp_count")
	;

	public static final String SUCESS = "sucess"; 
	public static final String ERROR = "error"; 
	
	private final int showIndex;
	private final String value, module;
	
	
	private LogCode(int showIndex, String value, String module) {
		this.showIndex = showIndex;
		this.value = value;
		this.module = module;
	}
	
	

	public int getShowIndex() {
		return showIndex;
	}
	public String getValue() {
		return value;
	}

	public String getModule() {
		return module;
	}

}

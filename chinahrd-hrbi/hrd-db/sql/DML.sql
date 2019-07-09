/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/3/31 10:17:06                           */
/*==============================================================*/
drop table if exists 360_report;

drop table if exists 360_time;

drop index ind_Org_y on ability_change;

drop index ind_eId_y on ability_change;

drop index ind_abId on ability_change;

drop table if exists ability_change;

drop table if exists budget_emp_numbe;

drop index un_cid on budget_position_number;

drop table if exists budget_position_number;

drop table if exists client_credit;

drop table if exists code_group;

drop table if exists config;

drop table if exists course_record;

drop table if exists days;

drop table if exists dedicat_genre_soure;

drop table if exists dedicat_organ;

drop table if exists dept_kpi;

drop table if exists dept_per_exam_relation;

drop index index_abKey on dim_ability;

drop index index_abId on dim_ability;

drop table if exists dim_ability;

drop table if exists dim_ability_lv;

drop table if exists dim_ability_number;

drop table if exists dim_certificate_info;

drop table if exists dim_change_type;

drop index un_cid on dim_channel;

drop table if exists dim_channel;

drop table if exists dim_checkwork_type;

drop index ind_pId_dim_city on dim_city;

drop table if exists dim_city;

drop table if exists dim_course;

drop table if exists dim_course_type;

drop table if exists dim_customer;

drop table if exists dim_dedicat_genre;

drop index un_cid on dim_dismission_week;

drop table if exists dim_dismission_week;

drop index ind_ym_org on dim_emp_month;

drop index ind_ym_eId on dim_emp_month;

drop table if exists dim_emp_month;

drop index un_cId on dim_encourages;

drop table if exists dim_encourages;

drop index index_parentId on dim_function;

drop index index_key on dim_function;

drop index index_customerId on dim_function;

drop table if exists dim_function;

drop table if exists dim_function_item;

drop table if exists dim_job_title;

drop table if exists dim_key_talent_type;

drop table if exists dim_organization;

drop table if exists dim_organization_type;

drop table if exists dim_performance;

drop index un_cId on dim_population;

drop table if exists dim_population;

drop index index_posId on dim_position;

drop index un_cId on dim_position;

drop table if exists dim_position;

drop table if exists dim_product_category;

drop table if exists dim_product_modul;

drop table if exists dim_product_supplier;

drop table if exists dim_profession;

drop table if exists dim_project_input_type;

drop table if exists dim_project_type;

drop table if exists dim_province;

drop index un_cid on dim_quality;

drop index idx_QId on dim_quality;

drop table if exists dim_quality;

drop index index_key on dim_role;

drop index index_customerId on dim_role;

drop table if exists dim_role;

drop index FK_Reference_35 on dim_run_off;

drop table if exists dim_run_off;

drop table if exists dim_sales_client;

drop table if exists dim_sales_product;

drop table if exists dim_sales_team;

drop table if exists dim_satfac_genre;

drop table if exists dim_separation_risk;

drop index index_seqId on dim_sequence;

drop table if exists dim_sequence;

drop table if exists dim_sequence_sub;

drop table if exists dim_structure;

drop table if exists dim_teacher;

drop index index_username on dim_user;

drop index index_pwd on dim_user;

drop index index_key on dim_user;

drop index index_customerId on dim_user;

drop table if exists dim_user;

drop index ind_z on dim_z_info;

drop table if exists dim_z_info;

drop index index_eId on dimission_risk;

drop table if exists dimission_risk;

drop index FK_Reference_29 on dimission_risk_item;

drop index FK_Reference_28 on dimission_risk_item;

drop table if exists dimission_risk_item;

drop table if exists emp_att_info;

drop table if exists emp_att_item_info;

drop table if exists emp_attendance;

drop table if exists emp_attendance_day;

drop index ind_y_org on emp_attendance_month;

drop index ind_y_eId on emp_attendance_month;

drop index ind_eId_y on emp_attendance_month;

drop table if exists emp_attendance_month;

drop index un_cId on emp_bonus_penalty;

drop table if exists emp_bonus_penalty;

drop table if exists emp_certificate_info;

drop table if exists emp_contact_person;

drop index un_cId on emp_edu;

drop index ind_name on emp_edu;

drop index ind_eId on emp_edu;

drop table if exists emp_edu;

drop table if exists emp_family;

drop index un_cId on emp_other_day;

drop index ind_eId_ym on emp_other_day;

drop index ind_days_eId on emp_other_day;

drop index ind_days on emp_other_day;

drop table if exists emp_other_day;

drop index index_ym on emp_overtime_day;

drop index index_days_name on emp_overtime_day;

drop index index_days_eId on emp_overtime_day;

drop index index_orgId on emp_overtime_day;

drop table if exists emp_overtime_day;

drop table if exists emp_past_resume;

drop table if exists emp_per_exam_relation;

drop index ind_eId on emp_personality;

drop table if exists emp_personality;

drop table if exists emp_pq_eval_relation;

drop index un_cid on emp_pq_relation;

drop index ind_mId_ym on emp_pq_relation;

drop index ind_date_eId on emp_pq_relation;

drop table if exists emp_pq_relation;

drop table if exists emp_prof_title;

drop table if exists emp_report_relation;

drop table if exists emp_train_experience;

drop table if exists emp_vacation;

drop table if exists exp_function_mobile;

drop table if exists exp_function_pc;

drop table if exists extend_product_course;

drop table if exists fact_fte;

drop table if exists function_config;

drop table if exists function_config_mobile;

drop table if exists function_role_relation;




drop table if exists history_dim_organization_month;

drop table if exists history_emp_count;

drop table if exists history_emp_count_month;

drop table if exists history_sales_ability;

drop index ind_sh on history_sales_detail;

drop index ind_pId on history_sales_detail;

drop index ind_eId_history_sales_detail on history_sales_detail;

drop table if exists history_sales_detail;

drop table if exists history_sales_order;

drop index index_seqSubId on job_change;

drop index ind_Ch_Us_job_change on job_change;

drop index index_seqId on job_change;

drop index index_posId on job_change;

drop index index_orgId_ym_job_change on job_change;

drop index index_jtId on job_change;

drop index index_eKey on job_change;

drop index index_eId on job_change;

drop index index_abLvId on job_change;

drop index index_abId on job_change;

drop table if exists job_change;

drop index index_y on job_relation;

drop index index_seqSubId on job_relation;

drop index index_seqId on job_relation;

drop index index_rN on job_relation;

drop index index_abLvId on job_relation;

drop index index_abId on job_relation;

drop table if exists job_relation;

drop index ind_eId on key_talent;

drop table if exists key_talent;

drop table if exists key_talent_encourages;

drop table if exists key_talent_focuses;

drop table if exists key_talent_logs;

drop index ind_kId on key_talent_tags;

drop table if exists key_talent_tags;

drop table if exists key_talent_tags_auto;

drop table if exists key_talent_tags_ed;

drop table if exists lecturer;

drop table if exists lecturer_course_design;

drop table if exists lecturer_course_speak;

drop table if exists login_log;

drop table if exists manpower_cost;

drop table if exists manpower_cost_item;

drop table if exists manpower_cost_value;

drop table if exists map_adjustment;

drop table if exists map_confing;

drop table if exists map_management;

drop table if exists map_public;

drop index Index_1 on map_talent;

drop table if exists map_talent;

drop table if exists map_talent_info;

drop table if exists map_talent_result;

drop table if exists map_team;

drop table if exists matching_school;

drop index un_cid on matching_score;

drop table if exists matching_score;

drop table if exists messages;

drop table if exists messages_status;

drop table if exists monthly_report_save;

drop table if exists monthly_report_share;

drop table if exists nfb_proportion;

drop table if exists operate_log;

drop index ind_Ch_Date_Uch on organization_change;

drop index ind_Ch_Date on organization_change;

drop index index_orgId_Day on organization_change;

drop index index_eKey on organization_change;

drop index index_eId on organization_change;

drop table if exists organization_change;

drop table if exists organization_emp_relation;

drop table if exists out_talent;

drop index ind_org_pay on pay;

drop index index_ym_orgId on pay;

drop index index_ym on pay;

drop index index_fp on pay;

drop index "index_eId_ym_pay y" on pay;

drop index index_cusId on pay;

drop table if exists pay;

drop index index_ym on pay_collect;

drop table if exists pay_collect;

drop table if exists pay_collect_year;

drop table if exists pay_value;

drop index index_ym on performance_change;

drop index index_perId_ym_performance_change on performance_change;

drop index index_perId on performance_change;

drop index index_orgId on performance_change;

drop index index_eKey on performance_change;

drop index index_customerId on performance_change;

drop index index_eId on performance_change;

drop table if exists performance_change;

drop table if exists permiss_import;

drop index ind_eId_day on population_relation;

drop index ind_eId on population_relation;

drop index ind_day_pId on population_relation;

drop index ind_day_eId on population_relation;

drop index ind_days on population_relation;

drop table if exists population_relation;

drop index ind_ym_pop on population_relation_month;

drop index ind_eId_ym_pop on population_relation_month;

drop table if exists population_relation_month;

drop index un_cid on position_quality;

drop index ind_pId_qId on position_quality;

drop table if exists position_quality;

drop table if exists profession_quantile_relation;

drop table if exists profession_value;

drop table if exists project;

drop table if exists project_cost;

drop table if exists project_input_detail;

drop table if exists project_manpower;

drop table if exists project_maxvalue;

drop table if exists project_partake_relation;

drop index ind_eId on promotion_analysis;

drop table if exists promotion_analysis;

drop index idx_sId on promotion_element_scheme;

drop table if exists promotion_element_scheme;

drop table if exists promotion_plan;

drop index Index_4 on promotion_results;

drop index ind_org_pd_promotion_date on promotion_results;

drop index ind_Org_date on promotion_results;

drop index idx_eId on promotion_results;

drop table if exists promotion_results;

drop index ind_tod on promotion_total;

drop table if exists promotion_total;

drop table if exists quota_memo;

drop table if exists quota_memo_status;

drop table if exists quota_memo_tips;

drop table if exists quota_memo_tips_item;

drop index un_cid on recruit_channel;

drop table if exists recruit_channel;

drop table if exists recruit_public;

drop index un_cid on recruit_result;

drop table if exists recruit_result;

drop index ind_PosY on recruit_salary_statistics;

drop table if exists recruit_salary_statistics;

drop index un_cid on recruit_value;

drop table if exists recruit_value;

drop table if exists recruitment_process;

drop index index_halfCheck on role_organization_relation;

drop index index_customerId on role_organization_relation;

drop table if exists role_organization_relation;

drop index ind_eId_run_off_record on run_off_record;

drop table if exists run_off_record;

drop index index_ym on run_off_total;

drop index index_ofp on run_off_total;

drop table if exists run_off_total;

drop index index_ym on salary;

drop index index_eId_ym_salary on salary;

drop table if exists salary;

drop index ind_strId_y_salary_year on salary_year;

drop table if exists salary_year;

drop table if exists sales_ability;

drop table if exists sales_client_contacts;

drop table if exists sales_client_plan;

drop table if exists sales_client_summary;

drop table if exists sales_config;

drop table if exists sales_detail;

drop index ind_orgId_sales_emp on sales_emp;

drop table if exists sales_emp;

drop table if exists sales_emp_client_relation;

drop table if exists sales_emp_month;

drop index ind_ym on sales_emp_rank;

drop table if exists sales_emp_rank;

drop table if exists sales_emp_target;

drop table if exists sales_order;

drop table if exists sales_org_day;

drop table if exists sales_org_month;

drop table if exists sales_org_prod_month;

drop table if exists sales_org_target;

drop table if exists sales_pro_target;

drop table if exists sales_team;

drop table if exists sales_team_rank;

drop table if exists sales_team_target;

drop table if exists satfac_genre_soure;

drop table if exists satfac_organ;

drop table if exists share_holding;

drop table if exists `mup-source`.source_code_item;

drop table if exists sys_customize;

drop table if exists sys_module;

drop table if exists talent_development_exam;

drop table if exists talent_development_promote;

drop table if exists talent_development_train;

drop table if exists target_benefit_value;

drop index ind_ym on theory_attendance;

drop index ind_days on theory_attendance;

drop table if exists theory_attendance;

drop table if exists trade_profit;

drop table if exists train_outlay;

drop table if exists train_plan;

drop table if exists train_satfac;

drop table if exists train_value;

drop table if exists underling;

drop table if exists user_organization_relation;

drop table if exists user_role_relation;

drop index ind_Uch on v_dim_emp;

drop index ind_rn_v_dim_emp on v_dim_emp;

drop index ind_perId_v_dim_emp on v_dim_emp;

drop index ind_Org on v_dim_emp;

drop index ind_mark on v_dim_emp;

drop index ind_eId_unc_rk_VDE on v_dim_emp;

drop index indx_pos on v_dim_emp;

drop index indx_eORG on v_dim_emp;

drop index index_eKey on v_dim_emp;

drop index index_eId on v_dim_emp;

drop index index_ed on v_dim_emp;

drop table if exists v_dim_emp;

drop index ind_ym on welfare_cpm;

drop index ind_eId_ym_welfare_cpm on welfare_cpm;

drop index ind_cId on welfare_cpm;

drop index index_ym_eId on welfare_cpm;

drop index index_eId on welfare_cpm;

drop table if exists welfare_cpm;

drop table if exists welfare_cpm_total;

drop index ind_ym_unc_welfare_nfb on welfare_nfb;

drop index ind_ym_eId on welfare_nfb;

drop index ind_eId_ym on welfare_nfb;

drop index ind_ym on welfare_nfb;

drop index ind_eId on welfare_nfb;

drop table if exists welfare_nfb;

drop table if exists welfare_nfb_total;

drop index ind_ym_eId on welfare_uncpm;

drop index ind_Uid on welfare_uncpm;

drop index ind_ym on welfare_uncpm;

drop index ind_eId on welfare_uncpm;

drop table if exists welfare_uncpm;



-- ********************************`mup-source`库****************************************
-- 主动流失率
-- ==========================
drop table if exists `mup-source`.source_run_off_record;

-- 人均效益
-- ======================
drop table if exists `mup-source`.source_target_benefit_value;
drop table if exists `mup-source`.source_trade_profit;

-- 培训看板
-- ===================================================
drop table if exists `mup-source`.source_train_outlay;
drop table if exists `mup-source`.source_train_value;
drop table if exists `mup-source`.source_lecturer_course_speak;
drop table if exists `mup-source`.source_train_plan;
drop table if exists `mup-source`.source_train_satfac;
drop table if exists `mup-source`.source_lecturer;
drop table if exists `mup-source`.source_course_record;
drop table if exists `mup-source`.source_emp_train_experience;

-- 团队画像
-- ===========================================
drop table if exists `mup-source`.source_emp_personality;

-- 人才损益
-- ==================================================================
drop table if exists `mup-source`.source_job_change;

-- 员工表
-- ================================
drop table if exists `mup-source`.source_v_dim_emp;

-- 销售看板
-- ===============================================================
drop table if exists `mup-source`.source_sales_emp_target;
drop table if exists `mup-source`.source_sales_detail;
drop table if exists `mup-source`.source_sales_pro_target;
drop table if exists `mup-source`.source_sales_org_target;
drop table if exists `mup-source`.source_sales_ability;
drop table if exists `mup-source`.source_sales_team_rank;
drop table if exists `mup-source`.source_sales_team_target;

-- 招聘看板
-- ========================================================
DROP TABLE IF EXISTS `mup-source`.source_recruit_value
drop table if exists `mup-source`.source_out_talent;
drop table if exists `mup-source`.source_recruit_public;
drop table if exists `mup-source`.source_recruit_result;
drop table if exists `mup-source`.source_recruit_channel;

-- 晋级看板
-- ======================
drop table if exists `mup-source`.source_promotion_plan;
drop table if exists `mup-source`.source_promotion_results;
drop table if exists `mup-source`.source_promotion_element_scheme;

-- 薪酬看板
-- ===================
drop table if exists `mup-source`.source_profession_quantile_relation;
drop table if exists `mup-source`.source_pay;
drop table if exists `mup-source`.source_share_holding;
drop table if exists `mup-source`.source_salary_year;
drop table if exists `mup-source`.source_salary;
drop table if exists `mup-source`.source_welfare_uncpm;
drop table if exists `mup-source`.source_welfare_nfb;
drop table if exists `mup-source`.source_welfare_cpm;

-- 职位序列
-- ===============
drop table if exists `mup-source`.source_job_relation;

-- 岗位胜任度
-- ==================
drop table if exists `mup-source`.source_position_quality;
drop table if exists `mup-source`.source_emp_pq_relation;

-- 人力成本
-- ==============
drop table if exists `mup-source`.source_manpower_cost_value;

-- ********************************`mup-source`库END****************************************

/*==============================================================*/
/* 代码组（字典表）                                              */
/*==============================================================*/ 
CREATE TABLE `sys_code_item` (
	`sys_code_item_id` CHAR(32) NOT NULL DEFAULT '',
	`customer_id` CHAR(32) NULL DEFAULT NULL COMMENT '客户ID',
	`sys_code_item_key` CHAR(20) NULL DEFAULT NULL,
	`sys_code_item_name` VARCHAR(60) NULL DEFAULT NULL COMMENT '用户编码',
	`code_group_id` CHAR(32) NULL DEFAULT NULL COMMENT '用户名称',
	`show_index` TINYINT(3) NULL DEFAULT NULL,
	PRIMARY KEY (`sys_code_item_id`),
	INDEX `ind_code_GId` (`code_group_id`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=MyISAM
;


/*==============================================================*/
/* Table: 360_report                                            */
/*==============================================================*/
create table 360_report
(
   360_report_id        varchar(32) not null comment '360测评报告ID',
   emp_id               varchar(32) comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   360_ability_id       varchar(32) comment '能力维度ID（字典表同步）',
   360_ability_name     varchar(20) comment '能力维度名称（字典表同步）',
   360_ability_lv_id    varchar(32) comment '能力层级ID（字典表同步）',
   360_ability_lv_name  varchar(5) comment '能力层级名称（字典表同步）',
   standard_score       double(5,2) comment '标准分',
   gain_score           double(5,2) comment '取得分',
   score                double(5,4) comment '成绩（取得分/标准分）',
   module_type          int(1) comment '模块（1 想，2 做， 3 带）',
   360_time_id          varchar(32) comment '360测评时段ID',
   primary key (360_report_id)
);

alter table 360_report comment '历史表-360测评报告（360_report）';

/*==============================================================*/
/* Table: 360_time                                              */
/*==============================================================*/
create table 360_time
(
   360_time_id          varchar(32) not null comment '360测评时段ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   report_date          datetime comment '分配测评时间',
   report_name          varchar(20) comment '测试报告名称',
   path                 varchar(100) comment '路径',
   type                 int(1) comment '类别（1:360测评 0：其它测试）',
   year                 int(4) comment '公报时间（周期：年度、半年度）',
   primary key (360_time_id)
);

alter table 360_time comment '历史表-360测评时段（360_time）';

/*==============================================================*/
/* Table: ability_change                                        */
/*==============================================================*/
create table ability_change
(
   ability_change_id    varchar(32) not null comment '员工能力ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   user_name_ch         varchar(50) comment '员工名称',
   full_path            varchar(200) comment '员工机构全路径',
   organization_parent_id varchar(32) comment '父机构ID',
   organization_id      varchar(32) comment '部门ID',
   sequence_id          varchar(32) comment '主序列',
   sequence_sub_id      varchar(32) comment '子序列',
   ability_id           varchar(32) comment '职位层级ID',
   age                  int(3) comment '年龄',
   sex                  varchar(3) comment '性别',
   degree_id            varchar(32) comment '学历ID',
   ability_number_id    varchar(32) comment '能力编码',
   update_time          datetime comment '更新时间',
   year_months          int(6) comment '年月',
   primary key (ability_change_id)
);

alter table ability_change comment '员工能力记录(ability_change)';

/*==============================================================*/
/* Index: ind_abId                                              */
/*==============================================================*/
create index ind_abId on ability_change
(
   year_months,
   ability_number_id
);

/*==============================================================*/
/* Index: ind_eId_y                                             */
/*==============================================================*/
create index ind_eId_y on ability_change
(
   year_months,
   emp_id
);

/*==============================================================*/
/* Index: ind_Org_y                                             */
/*==============================================================*/
create index ind_Org_y on ability_change
(
   year_months,
   organization_id
);

/*==============================================================*/
/* Table: budget_emp_numbe                                      */
/*==============================================================*/
create table budget_emp_number
(
   budget_emp_number_id varchar(32) not null comment '编制员工人数ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   number               int(4) comment '编制数',
   year                 int(4) comment '年',
   primary key (budget_emp_number_id)
);
alter table budget_emp_number comment '业务表-机构编制数（budget_emp_number）';

/*==============================================================*/
/* Table: budget_position_number                                */
/*==============================================================*/
create table budget_position_number
(
   budget_position_number_id varchar(32) not null comment '岗位编制数ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   position_id          varchar(32) comment '岗位ID',
   number               int(4) comment '编制数',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (budget_position_number_id)
);
alter table budget_position_number comment '业务表-岗位编制数（budget_position_number）';


/*==============================================================*/
/* Table: client_credit                                         */
/*==============================================================*/
create table client_credit
(
   client_credit_id     varchar(32) comment '销售客户信用度ID',
   customer_id          varchar(32) comment '客户ID',
   client_id            varchar(32) comment '销售客户ID',
   arrears              double(8,4) comment '欠款数',
   return_amount        double(8,4) comment '待回款数',
   create_date          date comment '创建时间'
);

alter table client_credit comment '销售客户信用度（client_credit）';

/*==============================================================*/
/* Table: code_group                                            */
/*==============================================================*/
create table code_group
(
   code_group_id        varchar(32) not null comment '代码组ID',
   customer_id          varchar(32) comment '客户ID',
   code_group_key       varchar(20) comment '编码',
   code_group_name      varchar(60) comment '代码名称',
   primary key (code_group_id)
);

alter table code_group comment '字典表-代码组（code_group）';

/*==============================================================*/
/* Table: config                                                */
/*==============================================================*/
create table config
(
   config_id            varchar(32) not null comment '系统配置ID',
   customer_id          varchar(32) comment '客户ID',
   config_key           varchar(50) comment '编码',
   config_val           varchar(50) comment '值',
   function_id          varchar(32) comment '功能ID',
   note                 varchar(50) comment '备注',
   primary key (config_id)
);

alter table config comment '基础表-系统配置（config）';

/*==============================================================*/
/* Table: course_record                                         */
/*==============================================================*/
create table course_record
(
   course_record_id     varchar(32) not null comment '课程安排记录ID',
   customer_id          varchar(32) comment '客户ID',
   course_id            varchar(32) comment '课程ID',
   start_date           datetime comment '培训时间',
   end_date             datetime comment '结束时间',
   status               tinyint(1) comment '状态（0进行中，1已完成）',
   train_unit           varchar(20) comment '主办方名称',
   train_plan_id        varchar(32) comment '培训计划ID',
   year                 int(4) comment '年',
   primary key (course_record_id)
);

alter table course_record comment '业务表-课程安排记录(course_record)';

/*==============================================================*/
/* Table: days                                                  */
/*==============================================================*/
create table days
(
   days                 datetime not null comment '日期ID',
   customer_id          varchar(32) comment '客户ID',
   is_work_flag         tinyint(1) comment '工作日',
   is_holiday           tinyint(1) comment '休息日（周六日）',
   is_vacation          tinyint(1) comment '节假日（法定假期）',
   primary key (days)
);

alter table days comment '业务表-日期（days）';

/*==============================================================*/
/* Table: dedicat_genre_soure                                   */
/*==============================================================*/
create table dedicat_genre_soure
(
   dedicat_soure_id     varchar(32) not null comment '敬业度评分ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   dedicat_genre_id     varchar(32) comment '敬业度分类ID',
   soure                double(5,4) comment '得分',
   date                 date comment '报告日期',
   primary key (dedicat_soure_id)
);
alter table dedicat_genre_soure comment '历史表-敬业度评分（dedicat_genre_soure）';

/*==============================================================*/
/* Table: dedicat_organ                                         */
/*==============================================================*/
create table dedicat_organ
(
   dedicat_organ_id     varchar(32) not null comment '敬业机构评分ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   organization_name    varchar(20) comment '机构名称',
   soure                double(4,4) comment '得分',
   date                 date comment '报告日期',
   primary key (dedicat_organ_id)
);
alter table dedicat_organ comment '历史表-敬业度机构评分（dedicat_organ）';

/*==============================================================*/
/* Table: dept_kpi                                              */
/*==============================================================*/
create table dept_kpi
(
   dept_per_exam_relation_id varchar(32) not null comment '部门绩效目标ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   kpi_value            double(6,4) comment 'KPI达标率（范围：0-1）',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (dept_per_exam_relation_id)
);

alter table dept_kpi comment '业务表-部门绩效达标率（dept_kpi）';

/*==============================================================*/
/* Table: dept_per_exam_relation                                */
/*==============================================================*/
create table dept_per_exam_relation
(
   dept_per_exam_relation_id varchar(32) not null comment '部门绩效目标ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   content              varchar(100) comment '考核内容',
   weight               double(4,4) comment '权重',
   progress             double(5,4) comment '进度',
   refresh              datetime comment '更新时间',
   primary key (dept_per_exam_relation_id)
);

alter table dept_per_exam_relation comment '业务表-部门绩效目标（dept_per_exam_relation）';

/*==============================================================*/
/* Table: dim_ability                                           */
/*==============================================================*/
create table dim_ability
(
   ability_id           varchar(32) not null comment '能力层级ID',
   customer_id          varchar(32) comment '客户ID',
   ability_key          varchar(20) comment '能力编号',
   ability_name         varchar(60) comment '能力层级名称',
   curt_name            varchar(1) comment '短名',
   type                 tinyint(1) comment '序列类型',
   show_index           int(3) comment '排序',
   primary key (ability_id)
);

alter table dim_ability comment '维度表-职位层级维(dim_ability)';

/*==============================================================*/
/* Index: index_abId                                            */
/*==============================================================*/
create index index_abId on dim_ability
(
   ability_id
);

/*==============================================================*/
/* Index: index_abKey                                           */
/*==============================================================*/
create index index_abKey on dim_ability
(
   ability_key
);

/*==============================================================*/
/* Table: dim_ability_lv                                        */
/*==============================================================*/
create table dim_ability_lv
(
   ability_lv_id        varchar(32) not null comment '职级ID',
   customer_id          varchar(32) comment '客户ID',
   ability_lv_key       varchar(20) comment '职级编号',
   ability_lv_name      varchar(60) comment '职级名称',
   curt_name            varchar(1) comment '短名',
   show_index           int(3) comment '排序',
   primary key (ability_lv_id)
);

alter table dim_ability_lv comment '维度表-职级维（dim_ability_lv）';

/*==============================================================*/
/* Table: dim_ability_number                                    */
/*==============================================================*/
create table dim_ability_number
(
   ability_number_id    varchar(32) not null comment '能力ID',
   ability_number_key   varchar(20) comment '能力编码',
   ability_number_name  varchar(20) comment '能力名称',
   customer_id          varchar(32) comment '客户ID',
   show_index           int(1) comment '排序',
   create_date          date comment '创建时间',
   primary key (ability_number_id)
);

alter table dim_ability_number comment '员工能力记录(dim_ability_number)';

/*==============================================================*/
/* Table: dim_certificate_info                                  */
/*==============================================================*/
create table dim_certificate_info
(
   certificate_info_id  varchar(32) not null comment '证书信息ID',
   certificate_name     varchar(100) comment '证书名称',
   customer_id          varchar(32) comment '客户ID',
   certificate_type_id  varchar(32) comment '证书类型ID',
   curt_name            tinyint(2) comment '短名',
   show_index           int(1) comment '排序',
   refresh          date comment '创建时间',
   primary key (certificate_info_id)
);

alter table dim_certificate_info comment '证书信息（dim_certificate_info）';

/*==============================================================*/
/* Table: dim_change_type                                       */
/*==============================================================*/
create table dim_change_type
(
   change_type_id       varchar(32) not null comment '异动类型ID',
   customer_id          varchar(32) comment '客户ID',
   change_type_name     varchar(20) comment '名称',
   curt_name            char(1) comment '简称',
   show_index           tinyint(3) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (change_type_id)
);

alter table dim_change_type comment '维度表-异动类型维（dim_change_type）';

/*==============================================================*/
/* Table: dim_channel                                           */
/*==============================================================*/
create table dim_channel
(
   channel_id           varchar(32) not null comment '应聘渠道ID',
   customer_id          varchar(32) comment '客户ID',
   channel_key          varchar(20) comment '招聘渠道编号',
   channel_name         varchar(20) comment '招聘渠道名称',
   show_index           tinyint(3) comment '排序',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (channel_id)
);

alter table dim_channel comment '维度表-招聘渠道维（dim_channel）';

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create index un_cid on dim_channel
(
   c_id
);

/*==============================================================*/
/* Table: dim_checkwork_type                                    */
/*==============================================================*/
create table dim_checkwork_type
(
   checkwork_type_id    varchar(32) not null comment '考勤类型ID',
   customer_id          varchar(32) comment '客户ID',
   checkwork_type_name  varchar(10) comment '考勤类型名称',
   curt_name            tinyint(1) comment '短名称',
   show_index           tinyint(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (checkwork_type_id)
);

alter table dim_checkwork_type comment '维度表-考勤类型（dim_checkwork_type）';

/*==============================================================*/
/* Table: dim_city                                              */
/*==============================================================*/
create table dim_city
(
   city_id              varchar(32) not null comment '城市ID',
   customer_id          varchar(32) comment '客户ID',
   city_key             varchar(20) comment '城市编码',
   city_name            varchar(20) comment '城市名称',
   province_id          varchar(32) comment '省ID',
   show_index           int(3) comment '排序',
   primary key (city_id)
);

alter table dim_city comment '维度表-城市（dim_city）';

/*==============================================================*/
/* Index: ind_pId_dim_city                                      */
/*==============================================================*/
create index ind_pId_dim_city on dim_city
(
   province_id
);

/*==============================================================*/
/* Table: dim_course                                            */
/*==============================================================*/
create table dim_course
(
   course_id            varchar(32) not null comment '课程维ID',
   customer_id          varchar(32) comment '客户ID',
   course_key           varchar(20) comment '课程编号',
   course_name          varchar(20) comment '课程名称',
   course_type_id       varchar(32) comment '课程类别ID',
   show_index           int(1) comment '排序',
   primary key (course_id)
);

alter table dim_course comment '维度表-课程维(dim_course)';

/*==============================================================*/
/* Table: dim_course_type                                       */
/*==============================================================*/
create table dim_course_type
(
   course_type_id       varchar(32) not null comment '课程类别维ID',
   customer_id          varchar(32) comment '客户ID',
   course_type_key      varchar(20) comment '课程类别编号',
   course_type_name     varchar(20) comment '类别名称',
   show_index           tinyint(1) comment '排序',
   primary key (course_type_id)
);

alter table dim_course_type comment '维度表-课程类别维(dim_course_type)';

/*==============================================================*/
/* Table: dim_customer                                          */
/*==============================================================*/
create table dim_customer
(
   customer_id          varchar(32) not null comment '客户ID',
   customer_key         varchar(20) comment '客户编码',
   customer_name        varchar(20) comment '客户名称',
   note                 varchar(200) comment '描述',
   primary key (customer_id)
);

alter table dim_customer comment '维度表-客户维（dim_customer）';

/*==============================================================*/
/* Table: dim_dedicat_genre                                     */
/*==============================================================*/
create table dim_dedicat_genre
(
   dedicat_genre_id     varchar(32) not null comment '敬业度ID',
   customer_id          varchar(32) comment '客户ID',
   dedicat_name         varchar(100) comment '名称',
   dedicat_genre_parent_id varchar(32) comment '上级ID',
   is_children          tinyint(1) comment '是否有子节点',
   create_date          date comment '创建日期',
   show_index           int(1) comment '排序',
   primary key (dedicat_genre_id)
);

alter table dim_dedicat_genre comment '维度表-敬业度分类（dim_dedicat_genre）';

/*==============================================================*/
/* Table: dim_dismission_week                                   */
/*==============================================================*/
create table dim_dismission_week
(
   dismission_week_id   varchar(32) not null comment '离职周期范围维ID',
   customer_id          varchar(32) comment '客户ID',
   name                 varchar(10) comment '周期名称',
   days                 int(4) comment '天数',
   type                 tinyint(1) comment '类型',
   show_index           int(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (dismission_week_id)
);
alter table dim_dismission_week comment '维度表-离职周期范围维（dim_dismission_week）';


/*==============================================================*/
/* Table: dim_emp_month                                         */
/*==============================================================*/
create table dim_emp_month
(
   dim_emp_month_id     varchar(32) not null comment '员工维度ID',
   emp_id               varchar(32) comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   emp_key              varchar(20) comment '员工编码',
   user_name            varchar(20) comment '用户名称',
   user_name_ch         varchar(5) comment '中文名',
   emp_hf_id            varchar(32) comment '上级ID',
   emp_hf_key           varchar(20) comment '上级编码',
   passtime_or_fulltime varchar(1) comment '工作性质(p、f、s)',
   degree_id            varchar(32) comment '学历ID',
   degree               varchar(20) comment '学历',
   native_place         varchar(90) comment '籍贯',
   country              varchar(20) comment '国籍',
   national_id          varchar(60) comment '证件号码',
   national_type        varchar(60) comment '证件类型',
   marry_status         int(1) comment '婚姻状况',
   patty_name           varchar(20) comment '政治面貌',
   is_key_talent        int(1) comment '是否关键人才',
   organization_parent_id varchar(32) comment '父机构ID',
   organization_parent_name varchar(60) comment '父机构名称',
   organization_id      varchar(32) comment '部门ID',
   organization_name    varchar(60) comment '部门名称',
   position_id          varchar(32) comment '岗位ID',
   position_name		varchar(60) comment '岗位名称',
   sequence_id          varchar(32) comment '主序列ID',
   sequence_name        varchar(60) comment '主序列名称',
   sequence_sub_id      varchar(32) comment '子序列ID',
   sequence_sub_name    varchar(60) comment '子序列名称',
   ability_id           varchar(32) comment '职位层级ID',
   ability_name         varchar(60) comment '职位层级名称',
   job_title_id         varchar(32) comment '职衔ID',
   job_title_name       varchar(20) comment '职衔名称',
   ability_lv_id        varchar(32) comment '职级ID',
   ability_lv_name      varchar(60) comment '职级名称',
   rank_name            varchar(5) comment '主子序列层级职级（短名）',
   performance_id       varchar(32) comment '绩效ID',
   performance_name     varchar(20) comment '绩效名称',
   address              varchar(60) comment '住址',
   contract_unit        varchar(32) comment '合同单位',
   contract             varchar(20) comment '合同性质',
   work_place_id        varchar(32) comment '工作地点ID',
   work_place           varchar(60) comment '工作地点',
   is_regular           int(1) comment '是否正职（1 主岗位  0 负岗位）',
   entry_date           datetime comment '入职日期',
   run_off_date         datetime not null comment '流失日期',
   `year_month`         int(6) comment '年月',
   refresh	            datetime comment '更新日期',
	PRIMARY KEY (`dim_emp_month_id`),
	INDEX `ind_ym_eId` (`year_month`, `emp_id`) USING BTREE,
	INDEX `ind_ym_org` (`year_month`, `organization_id`) USING BTREE
);
alter table dim_emp_month comment '历史表-员工信息月切片(dim_emp_month)';

/*==============================================================*/
/* Table: dim_encourages                                        */
/*==============================================================*/
create table dim_encourages
(
   encourages_id        varchar(32) not null comment '激励要素ID',
   customer_id          varchar(32) comment '客户ID',
   encourages_key       varchar(20) comment '激励要素编码',
   content              varchar(20) comment '激励要素内容',
   show_index           int(1) comment '排序',
   primary key (encourages_id)
);

alter table dim_encourages comment '维度表-激励要素表(dim_encourages)';

/*==============================================================*/
/* Index: un_cId                                                */
/*==============================================================*/
create unique index un_cId on dim_encourages
(
   c_id
);

/*==============================================================*/
/* Table: dim_function                                          */
/*==============================================================*/
create table dim_function
(
   function_id          varchar(32) not null comment '功能ID',
   customer_id          varchar(32) comment '客户ID',
   function_key         varchar(20) comment '功能编码',
   function_name        varchar(20) comment '功能名称',
   function_parent_id   varchar(32) comment '上级功能',
   full_path            varchar(1000) comment '全路径',
   note                 varchar(200) comment '描述',
   show_index           int(4) comment '排序',
   quota_or_fun         tinyint(1) comment '指标或功能',
   create_user_id       varchar(32) not null comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime not null comment '创建时间',
   modify_time          datetime comment '修改时间',
   url                  varchar(200) comment '连接',
   is_menu              int(1) comment '是否菜单',
   primary key (function_id)
);

alter table dim_function comment '维度表-功能维（dim_function）';

/*==============================================================*/
/* Index: index_customerId                                      */
/*==============================================================*/
create index index_customerId on dim_function
(
   customer_id
);

/*==============================================================*/
/* Index: index_key                                             */
/*==============================================================*/
create index index_key on dim_function
(
   function_key
);

/*==============================================================*/
/* Index: index_parentId                                        */
/*==============================================================*/
create index index_parentId on dim_function
(
   function_parent_id
);

/*==============================================================*/
/* Table: dim_function_item                                     */
/*==============================================================*/
create table dim_function_item
(
   function_item_id     varchar(32) not null comment '功能操作ID',
   customer_id          varchar(32) comment '客户ID',
   function_id          varchar(32) comment '功能ID',
   item_code            varchar(20) comment '操作项',
   note                 varchar(200) comment '描述',
   create_user_id       varchar(32) not null comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime not null comment '创建时间',
   modify_time          datetime comment '修改时间',
   primary key (function_item_id)
);

alter table dim_function_item comment '维度表-功能操作项维（dim_function_item）';

/*==============================================================*/
/* Table: dim_job_title                                         */
/*==============================================================*/
create table dim_job_title
(
   job_title_id         varchar(32) not null comment '职衔ID',
   customer_id          varchar(32) comment '客户ID',
   job_title_key        varchar(20) not null comment '职衔编号',
   job_title_name       varchar(20) comment '职衔名称',
   curt_name            varchar(1) comment '短名',
   show_index           int(3) comment '排序',
   primary key (job_title_id)
);

alter table dim_job_title comment '维度表-职衔（dim_job_title）';

/*==============================================================*/
/* Table: dim_key_talent_type                                   */
/*==============================================================*/
create table dim_key_talent_type
(
   key_talent_type_id   varchar(32) not null comment '关键人才',
   customer_id          varchar(32) comment '客户ID',
   key_talent_type_key  varchar(20) comment '关键人才类型编号',
   key_talent_type_name varchar(60) comment '关键人才名称',
   show_idnex           int(1) comment '排序',
   primary key (key_talent_type_id)
);

alter table dim_key_talent_type comment '维度表-关键人才库维(dim_key_talent_type)';

/*==============================================================*/
/* Table: dim_organization                                      */
/*==============================================================*/
create table dim_organization
(
   organization_id      varchar(32) not null comment '机构ID',
   customer_id          varchar(32) comment '客户ID',
   organization_company_id varchar(32) comment '分公司',
   organization_type_id varchar(32) comment '机构级别ID',
   organization_key     varchar(20) comment '机构编码',
   organization_parent_key varchar(20) comment '上级机构编码',
   organization_parent_id varchar(32) comment '上级机构',
   organization_name    varchar(20) comment '机构名称',
   organization_name_full varchar(20) comment '全名称',
   note                 varchar(200) comment '描述',
   is_single            int(1) comment '是否独立核算',
   full_path            longtext comment '全路径',
   has_children         tinyint(1) comment '是否有子节点',
   depth                int(4) comment '深度',
   profession_id        varchar(32) comment '行业ID',
   refresh              datetime comment '更新时间',
   primary key (organization_id)
);

alter table dim_organization comment '维度表-机构维（dim_organization）';

/*==============================================================*/
/* Table: dim_organization_type                                 */
/*==============================================================*/
create table dim_organization_type
(
   organization_type_id varchar(32) not null comment '机构级别ID',
   customer_id          varchar(32) comment '客户ID',
   organization_type_key varchar(20) comment '机构级别编码',
   organization_type_level int(2) comment '机构级别层级',
   organization_type_name varchar(20) comment '机构级别名称',
   show_index           int(1) comment '排序',
   primary key (organization_type_id)
);

alter table dim_organization_type comment '维度表-机构级别维（dim_organization_type）';

/*==============================================================*/
/* Table: dim_performance                                       */
/*==============================================================*/
create table dim_performance
(
   performance_id       varchar(32) not null comment '绩效ID',
   customer_id          varchar(32) comment '客户ID',
   performance_key      varchar(20) comment '绩效编号',
   performance_name     varchar(60) comment '绩效名称',
   curt_name            tinyint(1) comment '短名',
   show_index           int(3) comment '排序',
   primary key (performance_id)
);

alter table dim_performance comment '维度表-绩效维(dim_performance)';

/*==============================================================*/
/* Table: dim_population                                        */
/*==============================================================*/
create table dim_population
(
   population_id        varchar(32) not null comment '人群范围ID',
   customer_id          varchar(32) comment '客户ID',
   population_key       varchar(20) comment '人群范围编码',
   population_name      varchar(20) comment '人群范围名称',
   show_index           int(1) comment '排序',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (population_id)
);

alter table dim_population comment '维度表-人群范围维（dim_population）';

/*==============================================================*/
/* Index: un_cId                                                */
/*==============================================================*/
create unique index un_cId on dim_population
(
   c_id
);

/*==============================================================*/
/* Table: dim_position                                          */
/*==============================================================*/
create table dim_position
(
   position_id          varchar(32) not null comment '岗位ID',
   customer_id          varchar(32) comment '客户ID',
   position_key         varchar(20) comment '岗位编码',
   position_name        varchar(20) comment '岗位名称',
   show_index           int(1) comment '排序',
   refresh              datetime comment '更新时间',
   c_id                 varchar(0) comment '标识ID',
   primary key (position_id)
);

alter table dim_position comment '维度表-岗位维(dim_position)';

/*==============================================================*/
/* Index: un_cId                                                */
/*==============================================================*/
create unique index un_cId on dim_position
(
   c_id
);

/*==============================================================*/
/* Index: index_posId                                           */
/*==============================================================*/
create index index_posId on dim_position
(
   position_key
);

/*==============================================================*/
/* Table: dim_product_category                                  */
/*==============================================================*/
create table dim_product_category
(
   product_category_id  varchar(32) not null comment '产品类别ID',
   customer_id          varchar(32) comment '客户ID',
   product_category_name varchar(50) comment '产品名称',
   show_index           int(1) comment '排序',
   primary key (product_category_id)
);

alter table dim_product_category comment '商品类别(dim_product_category)';

/*==============================================================*/
/* Table: dim_product_modul                                     */
/*==============================================================*/
create table dim_product_modul
(
   product_modul_id     varchar(32) not null comment '产品模块ID',
   customer_id          varchar(32) comment '客户ID',
   product_modul_name   varchar(50) comment '产品模块名称',
   show_index           int(1) comment '排序',
   primary key (product_modul_id)
);

alter table dim_product_modul comment '商品模块(dim_product_modul)';

/*==============================================================*/
/* Table: dim_product_supplier                                  */
/*==============================================================*/
create table dim_product_supplier
(
   product_supplier_id  varchar(32) not null comment '产品供应商ID',
   product_supplier_name varchar(50) comment '产品供应商ID',
   customer_id          varchar(32) comment '客户ID',
   show_index           int(1) comment '排序',
   primary key (product_supplier_id)
);

alter table dim_product_supplier comment '商品供应商(dim_product_supplier)';

/*==============================================================*/
/* Table: dim_profession                                        */
/*==============================================================*/
create table dim_profession
(
   profession_id        varchar(32) not null comment '行业ID',
   profession_name      varchar(80) comment '行业名称',
   profession_key       varchar(32) comment '行业编码',
   show_index           int(1) comment '排序',
   refresh              datetime comment '更新日期',
   primary key (profession_id)
);

alter table dim_profession comment '维度表-行业维（dim_profession）';

/*==============================================================*/
/* Table: dim_project_input_type                                */
/*==============================================================*/
create table dim_project_input_type
(
   project_input_type_id varchar(32) not null comment '项目投入类型ID',
   customer_id          varchar(32) comment '客户ID',
   project_input_type_name varchar(10) comment '项目投入类型名称',
   show_index           tinyint(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (project_input_type_id)
);

alter table dim_project_input_type comment '维度表-项目投入费用类型维（dim_project_input_type）';

/*==============================================================*/
/* Table: dim_project_type                                      */
/*==============================================================*/
create table dim_project_type
(
   project_type_id      varchar(32) not null comment '项目类型ID',
   customer_id          varchar(32) comment '客户ID',
   project_type_name    varchar(10) comment '项目类型名称',
   show_index           tinyint(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (project_type_id)
);

alter table dim_project_type comment '维度表-项目类型维（dim_project_type）';

/*==============================================================*/
/* Table: dim_province                                          */
/*==============================================================*/
create table dim_province
(
   province_id          varchar(32) not null comment '省ID',
   customer_id          varchar(32) comment '客户ID',
   province_key         varchar(20) comment '省编码',
   province_name        varchar(20) comment '省名称',
   show_index           int(3) comment '排序',
   curt_name            char(1) comment '短名',
   primary key (province_id)
);

alter table dim_province comment '维度表-省（dim_province）';

/*==============================================================*/
/* Table: dim_quality                                           */
/*==============================================================*/
create table dim_quality
(
   quality_id           varchar(32) not null comment '能力素质ID',
   customer_id          varchar(32) comment '客户ID',
   vocabulary           varchar(10) comment '能力素质词条',
   note                 varchar(80) comment '能力素质定义',
   show_index           int(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (quality_id)
);

alter table dim_quality comment '维度表-能力素质维（dim_quality）';

/*==============================================================*/
/* Index: idx_QId                                               */
/*==============================================================*/
create index idx_QId on dim_quality
(
   quality_id
);

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create unique index un_cid on dim_quality
(
   c_id
);

/*==============================================================*/
/* Table: dim_role                                              */
/*==============================================================*/
create table dim_role
(
   role_id              varchar(32) not null comment '角色ID',
   customer_id          varchar(32) comment '客户ID',
   role_key             varchar(20) comment '角色编码',
   role_name            varchar(20) comment '角色名称',
   note                 varchar(200) comment '描述',
   create_user_id       varchar(32) not null comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime not null comment '创建时间',
   modify_time          datetime comment '修改时间',
   show_index           int(1) comment '排序',
   primary key (role_id)
);

alter table dim_role comment '维度表-角色维（dim_role）';

/*==============================================================*/
/* Index: index_customerId                                      */
/*==============================================================*/
create index index_customerId on dim_role
(
   customer_id
);

/*==============================================================*/
/* Index: index_key                                             */
/*==============================================================*/
create index index_key on dim_role
(
   role_key
);

/*==============================================================*/
/* Table: dim_run_off                                           */
/*==============================================================*/
create table dim_run_off
(
   run_off_id           varchar(32) not null comment '流失项ID',
   customer_id          varchar(32) comment '客户ID',
   run_off_key          varchar(3) comment '流失编码',
   run_off_name         varchar(200) comment '流失名称',
   type                 int(1) comment '(1：主动 2：被动 3：其它)',
   show_index           int(1) comment '排序',
   primary key (run_off_id)
);

alter table dim_run_off comment '维度表-流失维(dim_run_off)';

/*==============================================================*/
/* Index: FK_Reference_35                                       */
/*==============================================================*/
create index FK_Reference_35 on dim_run_off
(
   run_off_key
);

/*==============================================================*/
/* Table: dim_sales_client                                      */
/*==============================================================*/
create table dim_sales_client
(
   client_id            varchar(32) not null comment '销售客户ID',
   customer_id          varchar(32) comment '客户ID',
   client_key           varchar(20) comment '销售客户编码',
   client_name          varchar(50) comment '销售客户名称',
   curt_name            varchar(10) comment '销售客户短名称',
   company_nature       varchar(32) comment '公司性质',
   main_business        varchar(200) comment '主营业务',
   emp_num              int(7) comment '员工数量',
   leader_num           int(7) comment '管理干部数量',
   client_parent_name   varchar(100) comment '销售客户母公司',
   client_owned_industry varchar(100) comment '销售客户所属行业',
   client_type          int(1) comment '销售客户类型',
   client_turnover      double(10,4) comment '销售客户年均营业额(万元)',
   client_email         varchar(100) comment '销售客户e-mail',
   client_telephone     varchar(100) comment '销售客户电话',
   client_credit_rating int(1) comment '销售客户信用等级',
   country              varchar(100) comment '国家',
   province             varchar(100) comment '省',
   city                 varchar(100) comment '市',
   area                 varchar(100) comment '区',
   street               varchar(100) comment '街道',
   show_index           int(1) comment '排序',
   primary key (client_id)
);

alter table dim_sales_client comment '销售客户表(dim_sales_client)';

/*==============================================================*/
/* Table: dim_sales_product                                     */
/*==============================================================*/
create table dim_sales_product
(
   product_id           varchar(32) not null comment '商品ID',
   customer_id          varchar(32) comment '客户ID',
   product_key          varchar(32) comment '商品编码',
   product_name         varchar(50) comment '商品名称',
   product_cost         double(10,2) comment '商品成本（单位：元）',
   product_price        double(10,2) comment '商品价格（单位：元）',
   product_supplier_id  varchar(32) comment '产品供应商ID',
   product_modul_id     varchar(32) comment '产品模块ID',
   product_category_id  varchar(32) comment '产品类型ID',
   primary key (product_id)
);

alter table dim_sales_product comment '商品（dim_sales_product）';

/*==============================================================*/
/* Table: dim_sales_team                                        */
/*==============================================================*/
create table dim_sales_team
(
   team_id              varchar(32) not null comment '团队ID',
   customer_id          varchar(32) comment '客户ID',
   team_name            varchar(40) comment '团队名称',
   emp_id               varchar(200) comment '负责人ID',
   emp_names            varchar(100) comment '负责人名称',
   organization_id      varchar(32) comment '机构ID',
   show_index           int(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (team_id)
);

alter table dim_sales_team comment '团队信息(dim_sales_team)';

/*==============================================================*/
/* Table: dim_satfac_genre                                      */
/*==============================================================*/
create table dim_satfac_genre
(
   satfac_genre_id      varchar(32) not null comment '满意度ID',
   customer_id          varchar(32) comment '客户ID',
   satfac_name          varchar(100) comment '名称',
   satfac_genre_parent_id varchar(32) comment '上级ID',
   is_children          tinyint(1) comment '是否有子节点',
   create_date          date comment '创建日期',
   show_index           int(1) comment '排序',
   primary key (satfac_genre_id)
);

alter table dim_satfac_genre comment '维度表-满意度分类（dim_satfac_genre）';

/*==============================================================*/
/* Table: dim_separation_risk                                   */
/*==============================================================*/
create table dim_separation_risk
(
   separation_risk_id   varchar(32) not null comment '离职风险ID',
   customer_id          varchar(32) comment '客户ID',
   separation_risk_key  varchar(100) comment '离职风险编号',
   separation_risk_parent_id varchar(32) comment '上级ID',
   separation_risk_parent_key varchar(100) comment '上级编号',
   separation_risk_name varchar(20) comment '离职风险名称',
   refer                longtext comment '参考',
   show_index           int(3) comment '排序',
   primary key (separation_risk_id)
);

alter table dim_separation_risk comment '维度表-离职风险维(dim_separation_risk)';

/*==============================================================*/
/* Table: dim_sequence                                          */
/*==============================================================*/
create table dim_sequence
(
   sequence_id          varchar(32) not null comment '序列',
   customer_id          varchar(32) comment '客户ID',
   sequence_key         varchar(20) comment '序列编号',
   sequence_name        varchar(60) comment '序列名称',
   curt_name            varchar(1) comment '短名',
   show_index           int(3) comment '排序',
   primary key (sequence_id)
);

alter table dim_sequence comment '维度表-序列维(dim_sequence)';

/*==============================================================*/
/* Index: index_seqId                                           */
/*==============================================================*/
create index index_seqId on dim_sequence
(
   sequence_key
);

/*==============================================================*/
/* Table: dim_sequence_sub                                      */
/*==============================================================*/
create table dim_sequence_sub
(
   sequence_sub_id      varchar(32) not null comment '子序列ID',
   customer_id          varchar(32) comment '客户ID',
   sequence_id          varchar(32) comment '序列',
   sequence_sub_key     varchar(20) comment '子序列编号',
   sequence_sub_name    varchar(60) comment '子序列名称',
   curt_name            varchar(1) comment '短名',
   show_index           int(3) comment '排序',
   primary key (sequence_sub_id)
);

alter table dim_sequence_sub comment '维度表-子序列维(dim_sequence_sub)';

/*==============================================================*/
/* Table: dim_structure                                         */
/*==============================================================*/
create table dim_structure
(
   structure_id         varchar(32) not null comment '工资结构ID',
   customer_id          varchar(32) comment '客户ID',
   structure_name       varchar(20) comment '结构名称',
   is_fixed             tinyint(1) comment '是否固定',
   show_index           int(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (structure_id)
);

alter table dim_structure comment '维度表-工资结构维（dim_structure）';

/*==============================================================*/
/* Table: dim_teacher                                           */
/*==============================================================*/
create table dim_teacher
(
   teacher_id           varchar(32) not null comment '产品供应商ID',
   customer_id          varchar(32) comment '客户ID',
   teacher_name         varchar(50) comment '老师名称',
   good_field           varchar(100) comment '擅长领域',
   show_index           int(1) comment '排序',
   primary key (teacher_id)
);

alter table dim_teacher comment '教师信息(dim_teacher)';

/*==============================================================*/
/* Table: dim_user                                              */
/*==============================================================*/
create table dim_user
(
   user_id              varchar(32) not null comment '用户ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   user_key             varchar(20) comment '用户编码',
   user_name            varchar(20) comment '用户名称',
   user_name_ch         varchar(5) comment '中文名',
   password             varchar(20) comment '密码',
   email                varchar(50) comment '邮箱',
   note                 varchar(200) comment '描述',
   sys_deploy           int(1) comment '系统部署标识',
   show_index           int(5) comment '排序',
   create_user_id       varchar(32) not null comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime not null comment '创建时间',
   modify_time          datetime comment '修改时间',
   is_lock              tinyint(1) comment '是否锁',
   primary key (user_id)
);

alter table dim_user comment '维度表-用户信息表(dim_user)';

/*==============================================================*/
/* Index: index_customerId                                      */
/*==============================================================*/
create index index_customerId on dim_user
(
   customer_id
);

/*==============================================================*/
/* Index: index_key                                             */
/*==============================================================*/
create index index_key on dim_user
(
   user_key
);

/*==============================================================*/
/* Index: index_pwd                                             */
/*==============================================================*/
create index index_pwd on dim_user
(
   password
);

/*==============================================================*/
/* Index: index_username                                        */
/*==============================================================*/
create index index_username on dim_user
(
   user_name
);

/*==============================================================*/
/* Table: dim_z_info                                            */
/*==============================================================*/
create table dim_z_info
(
   z_id                 varchar(32) not null comment 'z轴ID',
   z_name               varchar(50) comment 'z轴名称',
   customer_id          varchar(32) comment '客户名称',
   show_index           int(1) comment '排序',
   primary key (z_id)
);

alter table dim_z_info comment 'z轴信息(dim_z_info)';

/*==============================================================*/
/* Index: ind_z                                                 */
/*==============================================================*/
create index ind_z on dim_z_info
(
   z_name
);

/*==============================================================*/
/* Table: dimission_risk                                        */
/*==============================================================*/
create table dimission_risk
(
   dimission_risk_id    varchar(32) not null comment '离职风险评估ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   note                 longtext comment '备注',
   risk_date            datetime comment '评估日期',
   risk_flag            int(1) comment '风险标识',
   is_last              int(1) comment '是否最近一次的离职风险',
   primary key (dimission_risk_id)
);

alter table dimission_risk comment '业务表-离职风险评估(dimission_risk)';

/*==============================================================*/
/* Index: index_eId                                             */
/*==============================================================*/
create index index_eId on dimission_risk
(
   emp_id
);

/*==============================================================*/
/* Table: dimission_risk_item                                   */
/*==============================================================*/
create table dimission_risk_item
(
   dimission_risk_item_id varchar(32) not null comment '离职风险评估项ID',
   customer_id          varchar(32) comment '客户ID',
   dimission_risk_id    varchar(32) comment '离职风险评估ID',
   separation_risk_id   varchar(32) comment '离职风险ID',
   risk_flag            int(1) comment '风险标识',
   primary key (dimission_risk_item_id)
);

alter table dimission_risk_item comment '业务表-离职风险评估细项(dimission_risk_item)';

/*==============================================================*/
/* Index: FK_Reference_28                                       */
/*==============================================================*/
create index FK_Reference_28 on dimission_risk_item
(
   dimission_risk_id
);

/*==============================================================*/
/* Index: FK_Reference_29                                       */
/*==============================================================*/
create index FK_Reference_29 on dimission_risk_item
(
   separation_risk_id
);

/*==============================================================*/
/* Table: emp_att_info                                          */
/*==============================================================*/
create table emp_att_info
(
   emp_att_id           varchar(32) not null comment '劳动力效能数据导入信息',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '导入员工ID',
   examine_id           varchar(32) comment '审核人ID',
   status               int comment '状态',
   create_date          datetime comment '生成时间',
   examine_date         datetime comment '审核时间',
   date_name            varchar(100) comment '导入数据名称',
   primary key (emp_att_id)
);

alter table emp_att_info comment '劳动力效能数据导入信息(emp_att_import)';

/*==============================================================*/
/* Table: emp_att_item_info                                     */
/*==============================================================*/
create table emp_att_item_info
(
   emp_att_id           varchar(32) comment '劳动力效能数据导入信息',
   customer_id          varchar(32) comment '客户ID',
   emp_key              char(10) not null comment '员工编码',
   hour_count           double(4,2) comment '实际出勤小时',
   theory_hour_count    double(4,2) comment '应出勤小时',
   overtime_count       double(4,2) comment '加班小时',
   other_count          varchar(100) comment '其他考勤',
   attendance_date      date not null comment '日期',
   primary key (emp_key, attendance_date)
);

alter table emp_att_item_info comment '劳动力效能人员信息(emp_att_item_info)';

/*==============================================================*/
/* Table: emp_attendance                                        */
/*==============================================================*/
create table emp_attendance
(
   emp_attendance_id    varchar(32) not null comment '出勤记录ID',
   customer_id          varchar(32) comment '客户ID',
   emp_key              varchar(20) comment '员工编号',
   emp_id               varchar(32) comment '员工ID',
   user_name_ch         varchar(5) comment '员工名称',
   clock_in_am          datetime comment '上班AM',
   clock_out_am         datetime comment '下班AM',
   clock_in_pm          datetime comment '上班PM',
   clock_out_pm         datetime comment '下班PM',
   opt_in               datetime comment '调整上班',
   opt_out              datetime comment '调整下班',
   opt_reason           varchar(32) comment '调整原因',
   cal_hour             varchar(20) comment '调整时间',
   checkwork_type_id    varchar(160) comment '考勤类型ID',
   note                 varchar(100) comment '备注',
   days                 date comment '日期',
   `year_month`         int(6) comment '年月',
   primary key (emp_attendance_id)
);

alter table emp_attendance comment '业务表-打卡记录（emp_attendance）';

/*==============================================================*/
/* Table: emp_attendance_day                                    */
/*==============================================================*/
create table emp_attendance_day
(
   emp_attendance_day_id varchar(32) not null comment '出勤记录ID',
   customer_id          varchar(32) comment '客户ID',
   emp_key              varchar(20) comment '员工编号',
   emp_id               varchar(32) comment '员工ID',
   user_name_ch         varchar(5) comment '员工名称',
   hour_count           double(4,2) comment '实际出勤小时',
   theory_hour_count    double(4,2) comment '应出勤小时',
   organization_id      varchar(32) comment '机构ID',
   checkwork_type_id    varchar(32) comment '考勤类型ID',
   days                 date comment '日期',
   year_months          int(6) comment '年月',
   primary key (emp_attendance_day_id)
);

alter table emp_attendance_day comment '业务表-出勤记录（emp_attendance_day）';

/*==============================================================*/
/* Table: emp_attendance_month                                  */
/*==============================================================*/
create table emp_attendance_month
(
   emp_attendance_month_id varchar(32) not null comment '考勤记录ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   emp_id               varchar(32) comment '员工ID',
   user_name_ch         varchar(50) comment '员工中文名',
   check_type_id        varchar(32) comment '出勤ID',
   at_hour              double(10,2) comment '出勤小时',
   overtime_type_id     varchar(32) comment '加班ID',
   ot_hour              double(10,2) comment '加班小时',
   th_hour              double(10,2) comment '应出勤小时',
   `year_month`         int(6) comment '年月',
   refresh              datetime comment '更新时间',
   primary key (emp_attendance_month_id)
);

alter table emp_attendance_month comment '业务表-考勤记录（emp_attendance_month）';

/*==============================================================*/
/* Index: ind_eId_y                                             */
/*==============================================================*/
create index ind_eId_y on emp_attendance_month
(
   emp_id,
   `year_month`
);

/*==============================================================*/
/* Index: ind_y_eId                                             */
/*==============================================================*/
create index ind_y_eId on emp_attendance_month
(
   `year_month`,
   emp_id
);

/*==============================================================*/
/* Index: ind_y_org                                             */
/*==============================================================*/
create index ind_y_org on emp_attendance_month
(
   `year_month`,
   organization_id
);

/*==============================================================*/
/* Table: emp_bonus_penalty                                     */
/*==============================================================*/
create table emp_bonus_penalty
(
   emp_bonus_penalty_id varchar(32) not null comment '奖惩信息ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   bonus_penalty_name varchar(20) comment '奖惩信息名称',
   type                 int(1) comment '奖惩类型（1：奖，0：惩）',
   grant_unit           varchar(32) comment '授予单位',
   witness_name         varchar(32) comment '证明人',
   bonus_penalty_date   datetime comment '奖惩时间',
   primary key (emp_bonus_penalty_id)
);

alter table emp_bonus_penalty comment '业务表-奖惩信息（emp_bonus_penalty）';

/*==============================================================*/
/* Index: un_cId                                                */
/*==============================================================*/
create index un_cId on emp_bonus_penalty
(
   c_id
);

/*==============================================================*/
/* Table: emp_certificate_info                                  */
/*==============================================================*/
create table emp_certificate_info
(
   emp_certificate_info_id varchar(32) comment '员工证书信息ID',
   emp_id               varchar(32) comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   certificate_id       varchar(32) comment '证书ID',
   obtain_date          date comment '获取时间',
   primary key ()
);

alter table emp_certificate_info comment '业务表-员工证书信息（emp_certificate_info）';

/*==============================================================*/
/* Table: emp_contact_person                                    */
/*==============================================================*/
create table emp_contact_person
(
   emp_contact_person_id varchar(32) not null comment '联系人ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_contact_person_name varchar(20) comment '联系人名称',
   tel_phone            varchar(11) comment '联系电话',
   `call`               varchar(10) comment '称呼（配偶、子女、朋友、同事）',
   is_urgent            int(1) comment '是否紧急联系人',
   create_time          datetime comment '录入时间',
   primary key (emp_contact_person_id)
);

alter table emp_contact_person comment '业务表-联系人信息（emp_contact_person）';

/*==============================================================*/
/* Table: emp_edu                                               */
/*==============================================================*/
create table emp_edu
(
   emp_edu_id           varchar(32) not null comment '教育背景ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   school_name          varchar(60) comment '学校名校',
   degree_id            varchar(32) comment '学历ID',
   degree               varchar(12) comment '学历名称',
   major                varchar(12) comment '专业',
   start_date           date comment '开始时间',
   end_date             date comment '结束时间',
   is_last_major        tinyint(1) comment '是否最后专业',
   academic_degree      varchar(12) comment '学位',
   develop_mode         varchar(12) comment '培养方式',
   bonus                varchar(60) comment '奖励信息',
   note                 varchar(200) comment '备注',
   primary key (emp_edu_id)
);

alter table emp_edu comment '业务表-教育背景（emp_edu）';

/*==============================================================*/
/* Index: ind_eId                                               */
/*==============================================================*/
create index ind_eId on emp_edu
(
   emp_id
);

/*==============================================================*/
/* Index: ind_name                                              */
/*==============================================================*/
create index ind_name on emp_edu
(
   school_name
);

/*==============================================================*/
/* Index: un_cId                                                */
/*==============================================================*/
create unique index un_cId on emp_edu
(
   c_id
);

/*==============================================================*/
/* Table: emp_family                                            */
/*==============================================================*/
create table emp_family
(
   emp_family_id        varchar(32) not null comment '家族ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_family_name      varchar(20) comment '家属名称',
   `call`               varchar(6) comment '称呼(父、母、子、女)',
   is_child             tinyint(1) comment '是否是子女',
   work_unit            varchar(20) comment '工作单位',
   department_name      varchar(20) comment '部门名称',
   position_name        varchar(20) comment '岗位/职务',
   tel_phone            varchar(11) comment '手机号码',
   born_date            datetime comment '出生年月',
   note                 varchar(20) comment '备注',
   primary key (emp_family_id)
);

alter table emp_family comment '业务表-家庭关系（emp_family）';

/*==============================================================*/
/* Table: emp_other_day                                         */
/*==============================================================*/
create table emp_other_day
(
   emp_other_day_id     varchar(32) not null comment '记录ID',
   customer_id          varchar(32) comment '客户ID',
   emp_key              varchar(20) comment '员工编号',
   emp_id               varchar(32) comment '员工ID',
   user_name_ch         varchar(5) comment '员工名称',
   hour_count           double(4,2) comment '休假小时',
   organization_id      varchar(32) comment '机构ID',
   checkwork_type_id    varchar(32) comment '考勤类型ID',
   days                  date comment '日期',
   `year_months`         int(6) comment '年月',
   primary key (emp_other_day_id)
);

alter table emp_other_day comment '业务表-其他考勤记录（emp_other_day）';

/*==============================================================*/
/* Table: emp_overtime_day                                      */
/*==============================================================*/
create table emp_overtime_day
(
   emp_overtime_day_id  varchar(32) not null comment '员工加班记录ID',
   customer_id          varchar(32) comment '客户ID',
   emp_key              varchar(20) comment '员工编号',
   emp_id               varchar(32) comment '员工ID',
   user_name_ch         varchar(5) comment '员工名称',
   hour_count           double(4,2) comment '加班小时',
   organization_id      varchar(32) comment '机构ID',
   checkwork_type_id    varchar(32) comment '考勤类型ID',
   days                 date comment '日期',
   `year_month`         int(6) comment '年月',
   primary key (emp_overtime_day_id)
);

alter table emp_overtime_day comment '业务表-加班记录（emp_overtime_day）';

/*==============================================================*/
/* Index: index_orgId                                           */
/*==============================================================*/
create index index_orgId on emp_overtime_day
(
   organization_id
);

/*==============================================================*/
/* Index: index_days_eId                                        */
/*==============================================================*/
create index index_days_eId on emp_overtime_day
(
   days,
   emp_id
);

/*==============================================================*/
/* Index: index_days_name                                       */
/*==============================================================*/
create index index_days_name on emp_overtime_day
(
   days,
   user_name_ch
);

/*==============================================================*/
/* Index: index_ym                                              */
/*==============================================================*/
create index index_ym on emp_overtime_day
(
   `year_month`
);

/*==============================================================*/
/* Table: emp_past_resume                                       */
/*==============================================================*/
create table emp_past_resume
(
   emp_past_resume_id   varchar(32) not null comment '过往履历ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   work_unit            varchar(20) comment '就职单位',
   department_name      varchar(20) comment '部门名称',
   position_name        varchar(20) comment '岗位名称',
   bonus_penalty_name   varchar(20) comment '奖惩名称',
   witness_name         varchar(12) comment '证明人',
   witness_contact_info varchar(20) comment '证明人联系方式',
   change_reason        varchar(20) comment '变动原因',
   entry_date           datetime comment '入职日期',
   run_off_date         datetime comment '流失日期',
   mark                 tinyint(1) comment '标记',
   primary key (emp_past_resume_id)
);

alter table emp_past_resume comment '业务表-过往履历（不是简历）（emp_past_resume）';

/*==============================================================*/
/* Table: emp_per_exam_relation                                 */
/*==============================================================*/
create table emp_per_exam_relation
(
   emp_per_exam_relation_id varchar(32) not null comment '部门绩效目标ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   content              varchar(20) comment '考核内容归类',
   sub_content          varchar(100) comment '考核内容',
   weight               double(4,4) comment '权重',
   progress             double(4,4) comment '进度',
   emp_idp              varchar(500) comment '子项目IDP',
   idp                  varchar(100) comment 'IDP',
   refresh              datetime comment '更新时间',
   primary key (emp_per_exam_relation_id)
);

alter table emp_per_exam_relation comment '业务表-员工绩效目标（emp_per_exam_relation）';

/*==============================================================*/
/* Table: emp_personality                                       */
/*==============================================================*/
create table emp_personality
(
   emp_personality_id   varchar(32) not null comment '员工性格ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   personality_id       varchar(32) comment '性格ID',
   type                 int(1) comment '性格类型',
   refresh              datetime comment '更新时间',
   primary key (emp_personality_id)
);

alter table emp_personality comment '业务表-员工性格（emp_personality）';

/*==============================================================*/
/* Index: ind_eId                                               */
/*==============================================================*/
create index ind_eId on emp_personality
(
   emp_id
);

/*==============================================================*/
/* Table: emp_pq_eval_relation                                  */
/*==============================================================*/
create table emp_pq_eval_relation
(
   emp_id               varchar(32) NOT NULL comment '员工ID',
   customer_id          varchar(32)  NULL DEFAULT NULL comment '客户ID',
   examination_result_id varchar(32)  NULL DEFAULT NULL comment '考核结果ID',
   examination_result   varchar(4)  NULL DEFAULT NULL comment '考核结果',
   date                 date comment  NULL DEFAULT NULL '考核日期',
   refresh              datetime comment  NULL DEFAULT NULL '更新时间',
   curt_name            int(1) comment  NULL DEFAULT NULL '短名',
   	PRIMARY KEY (`emp_id`,`date`)
);
alter table emp_pq_eval_relation comment '关系表-员工岗位能力评价（emp_pq_eval_relation）';

/*==============================================================*/
/* Table: emp_pq_relation                                       */
/*==============================================================*/
create table emp_pq_relation
(
   emp_pq_relation_id   varchar(32) not null comment '员工岗位素质得分ID',
   quality_id           varchar(32) comment '能力素质ID',
   matching_score_id    varchar(32) comment '所得分数ID',
   demands_matching_score_id varchar(32) comment '要求分数ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   date                 date comment '考核日期',
   refresh              datetime comment '更新时间',
   `year_month`         int(6) comment '年月',
   primary key (emp_pq_relation_id)
);

alter table emp_pq_relation comment '关系表-员工岗位素质得分（emp_pq_relation）';

/*==============================================================*/
/* Index: ind_date_eId                                          */
/*==============================================================*/
create index ind_date_eId on emp_pq_relation
(
   emp_id,
   `year_month`
);

/*==============================================================*/
/* Index: ind_mId_ym                                            */
/*==============================================================*/
create index ind_mId_ym on emp_pq_relation
(
   matching_soure_id,
   `year_month`
);

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create unique index un_cid on emp_pq_relation
(
   c_id
);

/*==============================================================*/
/* Table: emp_prof_title                                        */
/*==============================================================*/
create table emp_prof_title
(
   emp_prof_title_id    varchar(32) not null comment '所获职称ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   gain_date            datetime comment '获得时间',
   emp_prof_title_name  varchar(20) comment '职称名称',
   prof_lv              varchar(8) comment '级别',
   award_unit           varchar(20) comment '授予单位',
   effect_date          datetime comment '有效期',
   primary key (emp_prof_title_id)
);

alter table emp_prof_title comment '业务表-所获职称（emp_prof_title）';

/*==============================================================*/
/* Table: emp_report_relation                                   */
/*==============================================================*/
create table emp_report_relation
(
   emp_report_id        varchar(32) comment '汇报链ID',
   emp_key              varbinary(20) comment '员工编码',
   report_relation      varchar(500) comment '汇报链'
);

alter table emp_report_relation comment '汇报链(emp_report_relation)';

/*==============================================================*/
/* Table: emp_train_experience                                  */
/*==============================================================*/
create table emp_train_experience
(
   train_experience_id	varchar(32) not null comment '培训经历ID',
   course_record_id     varchar(32) comment '课程进程记录ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   course_name          varchar(20) comment '课程名称/项目',
   start_date           datetime comment '开始日期',
   finish_date          datetime comment '完成日期',
   train_time           double(5,2) comment '学时',
   status               tinyint(1) comment '状态（1已完成，0进行中）',
   result               varchar(8) comment '成绩（A、B.../98.5、88.0）',
   train_unit           varchar(20) comment '培训机构',
   gain_certificate     varchar(20) comment '所获证书',
   lecturer_name        varchar(12) comment '讲师名称',
   note                 varchar(20) comment '备注',
   year                 int(4) comment '年',
   mark                 tinyint(1) comment '标记',
   primary key (train_experience_id)
);

alter table emp_train_experience comment '业务表-培训经历（emp_train_experience）';

/*==============================================================*/
/* Table: emp_vacation                                          */
/*==============================================================*/
create table emp_vacation
(
   emp_id               varchar(32) comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   emp_key              varchar(20) comment '员工编号',
   user_name_ch         varchar(5) comment '员工名称',
   annual               varchar(20) comment '剩下年假数',
   can_leave            varchar(20) comment '剩下总天数',
   refresh              datetime comment '更新日期',
   primary key ()
);

alter table emp_vacation comment '业务表-假期（emp_vacation）';

/*==============================================================*/
/* Table: exp_function_mobile                                   */
/*==============================================================*/
create table exp_function_mobile
(
   function_id          varchar(32) not null comment '功能ID',
   customer_id          varchar(32) comment '客户ID',
   url                  varchar(200) comment '请求路径',
   image_url            varchar(200) comment '图标路径',
   image_add_url        varchar(200) comment '图标增加路径',
   is_show_organ        tinyint(1) not null comment '是否显示机构组件',
   is_scroll            int(3) comment 'is_scroll',
   units                units comment 'units',
   image_quota          varchar(20) comment 'image_quota',
   primary key (function_id)
);

alter table exp_function_mobile comment '扩展表-MOBILE端功能维（exp_function_mobile）';

/*==============================================================*/
/* Table: exp_function_pc                                       */
/*==============================================================*/
create table exp_function_pc
(
   function_id          varchar(32) not null comment '功能ID',
   customer_id          varchar(32) comment '客户ID',
   function_key         varchar(20) comment '功能编码',
   function_name        varchar(20) comment '功能名称',
   url                  varchar(200) comment '连接',
   is_menu              tinyint(1) comment '是否菜单',
   primary key (function_id)
);

alter table exp_function_pc comment '扩展表-PC端功能维（exp_function_pc）';

/*==============================================================*/
/* Table: extend_product_course                                 */
/*==============================================================*/
create table extend_product_course
(
   product_id           varchar(32) not null comment '商品ID',
   customer_id          varchar(32) comment '客户ID',
   teaching_date_id     varchar(32) comment '授课日期ID',
   teacher_id           varchar(32) comment '教师ID',
   place                varchar(50) comment '地点',
   start_date           varchar(32) comment '课程开始时间',
   end_date             varchar(32) comment '课程结束时间',
   duration             double(4,1) comment '课程时长',
   primary key (product_id)
);

alter table extend_product_course comment '商品扩展表-课程（extend_product_course）';

/*==============================================================*/
/* Table: fact_fte                                              */
/*==============================================================*/
create table fact_fte
(
   fte_id               varchar(32) not null comment '等效全职员工数ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   organization_name    varchar(50) comment '机构名称',
   fulltime_sum         double(10,2) comment '全职员工数（过）',
   passtime_sum         double(10,2) comment '兼职员工数（过）',
   overtime_sum         double(10,2) comment '加班员工数（过）',
   sales_amount          decimal(10,4) comment '销售总额',
   target_value         double(10,4) comment '目标人均效益值（单位：万）',
   gain_amount          decimal(10,4) comment '营业利润（单位：万、分子）',
   fte_value            double(10,2) comment '等效全职员工数值（单位：人、分母）',
   benefit_value        double(10,4) comment '实际人均效益值（单位：万、结果）',
   range_per            double(10,4) comment '变化幅度((本期数－上期数)/上期数*100%)',
   `year_month`         int(6) comment '年月',
   primary key (fte_id)
);
alter table fact_fte comment '业务表-等效全职员工数（fact_fte）';

/*==============================================================*/
/* Table: function_config                                       */
/*==============================================================*/
create table function_config
(
   function_config_id   varchar(32) not null comment '页面功能配置ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   function_parent_id   varchar(32) comment '上级功能ID',
   function_id          varchar(32) not null comment '功能ID',
   card_code            varchar(255) comment '卡片编码',
   is_view              tinyint comment '是否显示',
   show_index           int comment '功能排序',
   primary key (function_config_id)
);

alter table function_config comment '配置表-页面功能排序（function_config）';

/*==============================================================*/
/* Table: function_config_mobile                                */
/*==============================================================*/
create table function_config_mobile
(
   function_config_mobile_id varchar(32) not null comment '移动页面功能配置ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   function_id          varchar(32) comment '功能ID',
   card_code            varchar(255) comment '卡片编码',
   is_view              tinyint(1) comment '是否显示',
   show_index           int comment '功能排序',
   create_time          datetime comment '创建时间',
   primary key (function_config_mobile_id)
);

alter table function_config_mobile comment '配置表-移动页面功能排序（function_config_mobile）';

/*==============================================================*/
/* Table: function_role_relation                                */
/*==============================================================*/
create table function_role_relation
(
   function_role_id     varchar(32) not null comment '功能角色ID',
   role_id              varchar(32) comment '角色ID',
   function_id          varchar(32) comment '功能ID',
   customer_id          varchar(32) comment '客户ID',
   item_codes           varchar(200) comment '操作项集合Codes',
   create_user_id       varchar(32) not null comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime not null comment '创建时间',
   modify_time          datetime comment '修改时间',
   primary key (function_role_id)
);

alter table function_role_relation comment '关系表-功能角色关系（function_role_relation）';


/*==============================================================*/
/* Table: history_dim_organization_month                        */
/*==============================================================*/
DROP table history_dim_organization_month;
CREATE TABLE `history_dim_organization_month` (
	`history_dim_organization_month_id` VARCHAR(50) NOT NULL,
	`organization_id` VARCHAR(32) NOT NULL,
	`customer_id` VARCHAR(32) NULL DEFAULT NULL,
	`organization_company_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '分公司',
	`organization_type_id` VARCHAR(32) NULL DEFAULT NULL,
	`organization_key` VARCHAR(20) NULL DEFAULT NULL COMMENT '机构编码',
	`organization_parent_key` VARCHAR(20) NULL DEFAULT NULL,
	`organization_parent_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '上级机构',
	`organization_name` VARCHAR(20) NULL DEFAULT NULL COMMENT '机构名称',
	`organization_name_full` VARCHAR(50) NULL DEFAULT NULL,
	`note` VARCHAR(100) NULL DEFAULT NULL COMMENT '描述',
	`is_single` TINYINT(1) NULL DEFAULT '0' COMMENT '是否独立核算',
	`full_path` VARCHAR(2500) NULL DEFAULT NULL COMMENT '业务单位ID',
	`has_children` TINYINT(1) NULL DEFAULT NULL,
	`depth` INT(3) NULL DEFAULT NULL,
	`refresh` DATETIME NULL DEFAULT NULL,
	`profession_id` VARCHAR(32) NULL DEFAULT NULL,
	`year_month` INT(6) NOT NULL,
	PRIMARY KEY (`history_dim_organization_month_id`),
	INDEX `Index 2` (`organization_id`, `year_month`)
)
COMMENT='历史表-机构维(history_dim_organization_month)'
COLLATE='utf8_general_ci'
ENGINE=Aria
;

/*==============================================================*/
/* Table: history_emp_count                                     */
/*==============================================================*/
create table history_emp_count
(
   history_emp_count_id varchar(32) not null comment '总人数ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   full_path            varchar(100) comment '机构全路径',
   type                 int(1) comment '类型',
   emp_count            int(6) comment '本部门总人数',
   emp_count_sum        int(6) comment '本部门、子孙总人数',
   day                  date comment '日期',
   note                 varchar(30) comment '备注',
   year                 int(4) comment '年',
   primary key (history_emp_count_id)
);

alter table history_emp_count comment '历史表-每天总人数（history_emp_count）';

/*==============================================================*/
/* Table: history_emp_count_month                               */
/*==============================================================*/
create table history_emp_count_month
(
   history_emp_count_id varchar(32) not null comment '总人数ID',
   organization_id      varchar(32) comment '机构ID',
   type                 int(1) comment '类型',
   month_begin          int(6) comment '总人数（月初）',
   month_begin_sum      int(6) comment '子孙总人数（月初）',
   month_end            int(6) comment '总人数（昨天）',
   month_end_sum        int(6) comment '子孙总人数（昨天）',
   month_count          double(6,2) comment '总人数（月初+昨天/2）',
   month_count_sum      double(6,2) comment '子孙总人数（月初+昨天/2）',
   month_avg            double(10,4) comment '平均人数（总人数/月天数）',
   month_avg_sum        double(10,4) comment '子孙平均人数（总人数/月天数）',
   `year_month`         int(6) comment '年月',
   refresh              date comment '更新时间',
   primary key (history_emp_count_id)
);

alter table history_emp_count_month comment '历史表-每月总人数（history_emp_count_month）';

/*==============================================================*/
/* Table: history_sales_ability                                 */
/*==============================================================*/
create table history_sales_ability
(
   history_sales_ability_id varchar(32) comment '业务能力考核历史',
   emp_id               varchar(32) comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   status               int(1) comment '考核状态',
   check_date           date comment '考核时间',
   `year_month`         int(6) comment '年月'
);

alter table history_sales_ability comment '业务能力考核历史(history_sales_ability)';

/*==============================================================*/
/* Table: history_sales_detail                                  */
/*==============================================================*/
create table history_sales_detail
(
   history_sales_detail_id varchar(32) not null comment '历史销售明细ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   organization_id      varchar(32) comment '机构ID',
   product_id           varchar(32) comment '商品ID',
   product_number       int(5) comment '商品数量',
   sales_money          double(10,2) comment '实际金额（单位：元）',
   sales_profit         double(10,2) comment '商品销售利润（单位：元）',
   sales_province_id    varchar(32) comment '商品销售地点(省)',
   sales_city_id        varchar(32) comment '商品销售地点(市)',
   sales_date           date comment '商品销售时间',
   `year_month`         int(6) comment '年月',
   primary key (history_sales_detail_id)
);

alter table history_sales_detail comment '历史销售明细(history_sales_detail)';

/*==============================================================*/
/* Index: ind_eId_history_sales_detail                          */
/*==============================================================*/
create index ind_eId_history_sales_detail on history_sales_detail
(
   emp_id
);

/*==============================================================*/
/* Index: ind_pId                                               */
/*==============================================================*/
create index ind_pId on history_sales_detail
(
   product_id
);

/*==============================================================*/
/* Index: ind_sh                                                */
/*==============================================================*/
create index ind_sh on history_sales_detail
(
   `year_month`
);

/*==============================================================*/
/* Table: history_sales_order                                   */
/*==============================================================*/
create table history_sales_order
(
   sales_order_id       int(10) not null auto_increment comment '历史订单ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '销售员工ID(卖方)',
   client_id            varchar(32) comment '客户ID(买方)',
   province_id          varchar(32) comment '销售地点(省)',
   city_id              varchar(32) comment '销售地点(市)',
   sales_date           date comment '销售时间',
   `year_month`         int(6) comment '年月',
   primary key (sales_order_id)
);

alter table history_sales_order comment '历史订单(history_sales_order)';

/*==============================================================*/
/* Table: job_change                                            */
/*==============================================================*/
create table job_change
(
   emp_job_change_id    varchar(32) not null comment '变更ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_key              varchar(20) comment '员工编码',
   user_name_ch         varchar(20) comment '员工中文名',
   change_date          date comment '异动日期',
   change_type_id       varchar(32) comment '异动类型ID',
   change_type          int(3) comment '异动类型',
   organization_id      varchar(32) comment '部门ID（机构类别为3）',
   organization_name    varchar(60) comment '部门名称',
   position_id          varchar(32) comment '岗位ID',
   position_name        varchar(20) comment '岗位名称',
   sequence_id          varchar(32) comment '序列ID',
   sequence_name        varchar(20) comment '序列名称',
   sequence_sub_id      varchar(32) comment '子序列ID',
   sequence_sub_name    varchar(20) comment '子序列名称',
   ability_id           varchar(32) comment '层级ID',
   ability_name         varchar(20) comment '层级名称',
   ability_lv_id        varchar(32) comment '职级ID',
   ability_lv_name      varchar(20) comment '职级名称',
   job_title_id         varchar(32) comment '职衔ID',
   job_title_name       varchar(60)  comment '职衔名称',
   rank_name            varchar(5) comment '简称',
   rank_name_ed         varchar(5) comment '前简称',
   position_id_ed       varchar(32) comment '前岗位ID',
   position_name_ed     varchar(20) comment '前岗位名称',
   note                 varchar(5) comment '备注',
   refresh              datetime comment '更新时间',
   `year_month`         int(6) comment '年月',
   primary key (emp_job_change_id)
);

alter table job_change comment '历史表-工作异动表（job_change）';

/*==============================================================*/
/* Index: index_abId                                            */
/*==============================================================*/
create index index_abId on job_change
(
   ability_id
);

/*==============================================================*/
/* Index: index_abLvId                                          */
/*==============================================================*/
create index index_abLvId on job_change
(
   ability_lv_id
);

/*==============================================================*/
/* Index: index_eId                                             */
/*==============================================================*/
create index index_eId on job_change
(
   emp_id
);

/*==============================================================*/
/* Index: index_eKey                                            */
/*==============================================================*/
create index index_eKey on job_change
(
   emp_key
);

/*==============================================================*/
/* Index: index_jtId                                            */
/*==============================================================*/
create index index_jtId on job_change
(
   job_title_id
);

/*==============================================================*/
/* Index: index_orgId_ym_job_change                             */
/*==============================================================*/
create index index_orgId_ym_job_change on job_change
(
   organization_id,
   `year_month`
);

/*==============================================================*/
/* Index: index_posId                                           */
/*==============================================================*/
create index index_posId on job_change
(
   position_id
);

/*==============================================================*/
/* Index: index_seqId                                           */
/*==============================================================*/
create index index_seqId on job_change
(
   sequence_id
);

/*==============================================================*/
/* Index: ind_Ch_Us_job_change                                  */
/*==============================================================*/
create index ind_Ch_Us_job_change on job_change
(
   change_date,
   user_name_ch
);

/*==============================================================*/
/* Index: index_seqSubId                                        */
/*==============================================================*/
create index index_seqSubId on job_change
(
   sequence_sub_id
);

/*==============================================================*/
/* Table: job_relation                                          */
/*==============================================================*/
create table job_relation
(
   job_relation_id      varchar(32) not null comment '职位序列ID',
   customer_id          varchar(32) comment '客户ID',
   sequence_sub_id      varchar(32) comment '子序列ID',
   sequence_id          varchar(32) comment '主序列ID',
   ability_id           varchar(32) comment '能力层级ID',
   job_title_id         varchar(32) comment '职衔ID',
   ability_lv_id        varchar(32) comment '职级ID',
   year                 int(4) comment '年',
   type                 int(1) comment '类型（0：默认名称。1：特定名称）',
   rank_name            varchar(5) comment '主子序列层级职级',
   show_index           int(4) comment '排序',
   refresh              datetime comment '更新日期',
   primary key (job_relation_id)
);

alter table job_relation comment '关系表-职位序列关系（job_relation）';

/*==============================================================*/
/* Index: index_abId                                            */
/*==============================================================*/
create index index_abId on job_relation
(
   ability_id
);

/*==============================================================*/
/* Index: index_abLvId                                          */
/*==============================================================*/
create index index_abLvId on job_relation
(
   ability_lv_id
);

/*==============================================================*/
/* Index: index_rN                                              */
/*==============================================================*/
create index index_rN on job_relation
(
   rank_name
);

/*==============================================================*/
/* Index: index_seqId                                           */
/*==============================================================*/
create index index_seqId on job_relation
(
   sequence_id
);

/*==============================================================*/
/* Index: index_seqSubId                                        */
/*==============================================================*/
create index index_seqSubId on job_relation
(
   sequence_sub_id
);

/*==============================================================*/
/* Index: index_y                                               */
/*==============================================================*/
create index index_y on job_relation
(
   year
);

/*==============================================================*/
/* Table: key_talent                                            */
/*==============================================================*/
create table key_talent
(
   key_talent_id        varchar(32) not null comment '关键人才ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   key_talent_type_id   varchar(32) comment '关键人才',
   is_sychron           int(1) comment '是否同步数据',
   is_delete            int(1) comment '是否删除',
   note                 varchar(500) comment '备注',
   create_emp_id        varchar(32) comment '创建人',
   create_time          datatime comment '创建时间',
   modity_encourage_emp_id varchar(32) comment '修改激励要素员工ID',
   refresh              datetime comment '更新时间',
   refresh_tag1         datetime comment '优势标签更新时间',
   refresh_tag2         datetime comment '短板标签更新时间',
   refresh_log          datetime comment '日志更新时间',
   refresh_encourage    datetime comment '激励要素更新时间',
   primary key (key_talent_id)
);

alter table key_talent comment '业务表-关键人才库(key_talent)';

/*==============================================================*/
/* Index: ind_eId                                               */
/*==============================================================*/
create index ind_eId on key_talent
(
   emp_id
);

/*==============================================================*/
/* Table: key_talent_encourages                                 */
/*==============================================================*/
create table key_talent_encourages
(
   key_talent_encourages_id varchar(32) not null comment '关键人才激励要素ID',
   customer_id          varchar(32) comment '客户ID',
   key_talent_id        varchar(32) comment '关键人才ID',
   encourages_id        varchar(32) comment '激励要素ID',
   create_emp_id        varchar(32) comment '操作员工ID',
   create_time          datetime comment '创建时间',
   primary key (key_talent_encourages_id)
);

alter table key_talent_encourages comment '业务表-关键人才激励要素表(key_talent_encourages)';

/*==============================================================*/
/* Table: key_talent_focuses                                    */
/*==============================================================*/
create table key_talent_focuses
(
   key_talent_focuses_id varchar(32) not null comment '关注ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   key_talent_id        varchar(32) comment '关键人才ID',
   create_time          datetime comment '创建时间',
   primary key (key_talent_focuses_id)
);

alter table key_talent_focuses comment '业务表-关键人才关注表(key_talent_focuses)';

/*==============================================================*/
/* Table: key_talent_logs                                       */
/*==============================================================*/
create table key_talent_logs
(
   key_talent_logs_id   varchar(32) not null comment '关键人才日志ID',
   customer_id          varchar(32) comment '客户ID',
   content              longtext comment '日志内容',
   key_talent_id        varchar(32) comment '关键人才ID',
   create_emp_id        varchar(32) comment '操作员工ID',
   create_time          datetime comment '创建时间',
   refresh              datetime comment '更新时间',
   primary key (key_talent_logs_id)
);

alter table key_talent_logs comment '业务表-关键人才日志表(key_talent_logs)';

/*==============================================================*/
/* Table: key_talent_tags                                       */
/*==============================================================*/
create table key_talent_tags
(
   key_talent_tags_id   varchar(32) not null comment '关键人才标签ID',
   customer_id          varchar(32) comment '客户ID',
   type                 int(1) comment '标签类型（1:优势,2:短板）',
   content              varchar(10) comment '标签内容',
   key_talent_id        varchar(32) comment '关键人才ID',
   create_emp_id        varchar(32) comment '操作员工ID',
   create_time          datetime comment '创建时间',
   primary key (key_talent_tags_id)
);

alter table key_talent_tags comment '业务表-关键人才标签表(key_talent_tags)';

/*==============================================================*/
/* Index: ind_kId                                               */
/*==============================================================*/
create index ind_kId on key_talent_tags
(
   key_talent_tags_id
);

/*==============================================================*/
/* Table: key_talent_tags_auto                                  */
/*==============================================================*/
create table key_talent_tags_auto
(
   key_talent_tags_auto_id varchar(32) not null comment '关键人才自动标签ID',
   customer_id          varchar(32) comment '客户ID',
   content              varchar(10) comment '标签内容',
   key_talent_id        varchar(32) comment '关键人才ID',
   create_time          datetime comment '创建时间',
   primary key (key_talent_tags_auto_id)
);

alter table key_talent_tags_auto comment '业务表-关键人才自动标签表(key_talent_tags_auto)';

/*==============================================================*/
/* Table: key_talent_tags_ed                                    */
/*==============================================================*/
create table key_talent_tags_ed
(
   key_talent_tags_ed_id varchar(32) not null comment '关键人才标签历史ID',
   customer_id          varchar(32) comment '客户ID',
   type                 int(1) comment '标签类型（1：优势，2：短板）',
   content              varchar(10) comment '标签内容',
   key_talent_id        varchar(32) comment '关键人才ID',
   opt_username         varchar(32) comment '操作员工名称',
   action_type          int(1) comment '动作类型（1：新增，2：删除）',
   create_time          datetime comment '创建时间',
   primary key (key_talent_tags_ed_id)
);

alter table key_talent_tags_ed comment '历史表-关键人才标签历史表(key_talent_tags_ed)';

/*==============================================================*/
/* Table: lecturer                                              */
/*==============================================================*/
create table lecturer
(
   lecturer_id          varchar(32) not null comment '讲师ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID（内部讲师）',
   lecturer_name        varchar(20) comment '讲师名称',
   level_id             varchar(32) comment '讲师级别ID',
   type                 tinyint(1) comment '讲师类别（1外部，2内部）',
   primary key (lecturer_id)
);

alter table lecturer comment '业务表-讲师（lecturer）';

/*==============================================================*/
/* Table: lecturer_course_design                                */
/*==============================================================*/
create table lecturer_course_design
(
   lecturer_course_design_id varchar(32) not null comment '讲师设计课ID',
   customer_id          varchar(32) comment '客户ID',
   lecturer_id          varchar(32) comment '讲师ID',
   course_id            varchar(32) comment '课程ID',
   primary key (lecturer_course_design_id)
);

alter table lecturer_course_design comment '关系表-讲师设计课（lecturer_course_design）';

/*==============================================================*/
/* Table: lecturer_course_speak                                 */
/*==============================================================*/
create table lecturer_course_speak
(
   lecturer_course_speak_id varchar(32) not null comment '讲师授课ID',
   customer_id          varchar(32) comment '客户ID',
   lecturer_id           varchar(32) comment '讲师ID',
   course_id            varchar(32) comment '课程ID',
   start_date           datetime comment '授课开始时间',
   end_date             datetime comment '授课结束时间',
   year                 int(4) comment '年',
   primary key (lecturer_course_speak_id)
);

alter table lecturer_course_speak comment '关系表-讲师授课（lecturer_course_speak）';

/*==============================================================*/
/* Table: login_log                                             */
/*==============================================================*/
create table login_log
(
   login_log_id         varchar(32) not null comment '登录日记ID',
   user_id              varchar(32) comment '登录人ID',
   user_key             varchar(20) comment '登录人编码',
   user_name            varchar(10) comment '登录人名称',
   user_name_ch         varchar(5) comment '登录人中文名称',
   ip_address           varchar(15) comment '登录IP',
   login_time           timestamp comment '登录时间',
   logout_time          timestamp comment '登出时间',
   primary key (login_log_id)
);

alter table login_log comment '历史表-登录日记（login_log）';

/*==============================================================*/
/* Table: manpower_cost                                         */
/*==============================================================*/
create table manpower_cost
(
   manpower_cost_id     varchar(32) not null comment '人力成本ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   cost                 double(10,2) comment '人力成本、已使用成本（单位：万元）',
   emp_num              double(10,2) comment '人数（包括子孙，月度人数抽取）',
   cost_avg             double(10,2) comment '人均成本（单位：万元）',
   cost_budget          double(10,2) comment '预算成本',
   cost_company         double(10,2) comment '企业总成本（单位：万元）',
   `year_month`         int(6) comment '年月',
   primary key (manpower_cost_id)
);

alter table manpower_cost comment '业务表-人力成本（manpower_cost）';

/*==============================================================*/
/* Table: manpower_cost_item                                    */
/*==============================================================*/
create table manpower_cost_item
(
   manpower_cost_item_id varchar(32) not null comment '人力成本结构ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   item_id              varchar(32) comment '结构ID',
   item_name            varchar(20) comment '结构名称',
   item_cost            double(15,2) comment '结构成本值（单位：万元）',
   `year_month`         int(6) comment '年月',
   show_index           int(1) comment '排序',
   primary key (manpower_cost_item_id)
);
alter table manpower_cost_item comment '业务表-人力成本结构（manpower_cost_item）';

/*==============================================================*/
/* Table: manpower_cost_value                                   */
/*==============================================================*/
create table manpower_cost_value
(
   manpower_cost_value_id varchar(32) not null comment '人力年度预算成本ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   budget_value         double(20,4) comment '预算度本',
   year                 int(4) comment '年',
   primary key (manpower_cost_value_id)
);

alter table manpower_cost_value comment '业务表-人力年度预算成本（年）（manpower_cost_value）';

/*==============================================================*/
/* Table: map_adjustment                                        */
/*==============================================================*/
create table map_adjustment
(
   organization_Id      char(32） not null comment '顶级ID',
   customer_id          varchar(32) comment '客户ID',
   full_path            varchar(200) comment '全路径',
   update_time          datetime comment '更新时间',
   `year_month`         int(6) not null comment '年月',
   primary key (organization_Id, `year_month`)
);

alter table map_adjustment comment '人才地图调整（map_adjustment）';

/*==============================================================*/
/* Table: map_confing                                           */
/*==============================================================*/
create table map_config
(
   map_config_id        varchar(32) not null comment '地图颜色配置ID',
   customer_id          varchar(32) comment '客户ID',
   x_number             int(2) comment 'x轴',
   y_number             int(2) comment 'y轴',
   color               varchar(50) comment '颜色',
   update_time          date comment '更新时间',
   z_name               varchar(100) comment 'z轴名称',
   primary key (map_config_id)
);

alter table map_config comment '人才地图配置表（map_config）';

/*==============================================================*/
/* Table: map_management                                        */
/*==============================================================*/
create table map_management
(
   map_management_id    varchar(32) not null comment '人才地图管理ID',
   customer_id          varchar(32) comment '客户ID',
   top_id               varchar(32) comment '顶级ID',
   status               int(1) comment '状态',
   adjustment_id        varchar(32) comment '调整人ID',
   emp_id               varchar(32) comment '审核人ID',
   starte_date          datetime comment '生成时间',
   motify_date          datetime comment '调整时间',
   release_date         datetime comment '发布时间',
   year_months          int(6) comment '年月',
   primary key (map_management_id)
);

alter table map_management comment '人才地图管理（map_management）';

/*==============================================================*/
/* Table: map_public                                            */
/*==============================================================*/
create table map_public
(
   organization_id      varchar(32) not null comment '顶级ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   full_path            varchar(200) comment '全路径',
   update_time          datetime comment '更新时间',
   `year_month`         int(10) not null comment '年月',
   primary key (organization_id, `year_month`)
);

alter table map_public comment '人才地图发布(map_public)';

/*==============================================================*/
/* Table: map_talent                                            */
/*==============================================================*/
create table map_talent
(
   map_talent_id        varchar(32) not null comment '员工人才地图ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   x_axis_id            varchar(32) comment 'x轴ID',
   x_axis_id_af         varchar(32) comment 'x轴ID后',
   x_axis_id_af_r       varchar(32) comment 'x轴ID后R',
   y_axis_id            varchar(32) comment 'y轴ID',
   y_axis_id_af         varchar(32) comment 'y轴ID后',
   y_axis_id_af_r       varchar(32) comment 'y轴ID后R',
   update_time          datetime comment '更新时间',
   is_update            int(1) comment '更新标识',
   modify_id            varchar(32) comment '调整人ID',
   release_id           varchar(32) comment '发布人ID',
   year_months          int(6) comment '年月',
   note                 varchar(500) comment '第一次调整说明',
   note1                varchar(500) comment '第二次调整说明',
   date_time            date comment '人才地图数据抽取时间',
   primary key (map_talent_id)
);

alter table map_talent comment '人才地图（map_talent）';

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on map_talent
(
   emp_id,
   year_months
);

/*==============================================================*/
/* Table: map_talent_info                                       */
/*==============================================================*/
create table map_talent_info
(
   emp_id               varchar(32) not null comment '员工ID',
   user_name_ch         varchar(50) comment '员工中文名',
   customer_id          varchar(32) comment '客户ID',
   full_path            varchar(200) comment '员工机构全路径',
   organization_parent_id varchar(32) comment '父机构ID',
   organization_id      varchar(32) comment '部门ID',
   sequence_id          varchar(32) comment '主序列',
   sequence_sub_id      varchar(32) comment '子序列',
   ability_id           varchar(32) comment '职位层级ID',
   degree_id            varchar(32) comment '学位ID',
   performance_id       varchar(32) comment '绩效ID',
   position_id          varchar(32) comment '职位ID',
   age                  int(3) comment '年龄',
   sex                  varchar(1) comment '性别',
   refresh              datetime comment '更新时间',
   primary key (emp_id)
);

alter table map_talent_info comment '人才地图员工信息(map_talent_info)';

/*==============================================================*/
/* Table: map_talent_result                                     */
/*==============================================================*/
create table map_talent_result
(
   map_talent_id        varchar(32) not null comment '员工人才地图ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   x_axis_id            varchar(32) comment 'x轴ID',
   y_axis_id            varchar(32) comment 'y轴ID',
   update_time          datetime comment '更新时间',
   year_months          int(6) comment '年月',
   note                 varchar(500) comment '调整说明',
   primary key (map_talent_id)
);

alter table map_talent_result comment '人才地图结果（map_talent_result）';

/*==============================================================*/
/* Table: map_team                                              */
/*==============================================================*/
create table map_team
(
   map_team_id          varchar(32) not null comment '自定义团队',
   emp_id               varchar(32) comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   team_name            varchar(50) comment '团队名称',
   requirement          varchar(4000) comment '要求',
   pk_view              int(1) comment '是否团队PK查看',
   create_time          date comment '创建时间',
   primary key (map_team_id)
);

alter table map_team comment '自定义团队（map_team）';

/*==============================================================*/
/* Table: matching_school                                       */
/*==============================================================*/
create table matching_school
(
   matching_school_id   varchar(32) not null comment '学校ID',
   customer_id          varchar(32) comment '客户ID',
   name                 varchar(50) comment '学校名称',
   school_type          varchar(10) comment '院校类型',
   level                int(1) comment '等级',
   primary key (matching_school_id)
);

alter table matching_school comment '匹配表-学校表(matching_school)';

/*==============================================================*/
/* Table: matching_score                                        */
/*==============================================================*/
create table matching_score
(
   matching_score_id    varchar(32) not null comment '分数映射ID',
   customer_id          varchar(32) comment '客户ID',
   v1                   varchar(10) comment '字符',
   show_index           tinyint(1) comment '排名',
   refresh              datetime comment '更新时间',
   primary key (matching_score_id)
);
alter table matching_score comment '匹配表-分数映射（matching_score）';

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create unique index un_cid on matching_soure
(
   c_id
);

/*==============================================================*/
/* Table: messages                                              */
/*==============================================================*/
create table messages
(
   messages_id          varchar(32) not null comment '消息ID',
   customer_id          varchar(32) comment '客户ID',
   quota_id             varchar(32) comment '指标ID',
   content              longtext comment '内容',
   send_emp_id          varchar(32) comment '发送人',
   organization_id      varchar(32) comment '机构ID',
   type                 int(1) comment '类型',
   create_time          datetime not null comment '创建时间',
   is_delete            int(1) not null comment '是否删除',
   delete_time          datetime comment '删除时间',
   url                  varchar(200) comment 'url',
   primary key (messages_id)
);

alter table messages comment '业务表-消息（messages）';

/*==============================================================*/
/* Table: messages_status                                       */
/*==============================================================*/
create table messages_status
(
   messages_status_id   varchar(32) not null comment '消息状态ID',
   customer_id          varchar(32) comment '客户ID',
   messages_id          varchar(32) comment '消息ID',
   emp_id               varchar(32) comment '接收员工ID',
   is_read              int(1) comment '是否已读',
   read_time            datetime comment '阅读时间',
   primary key (messages_status_id)
);

alter table messages_status comment '业务表-消息状态（messages_status）';

/*==============================================================*/
/* Table: monthly_report_save                                   */
/*==============================================================*/
create table monthly_report_save
(
   monthly_report_save_id varchar(32) not null comment '附件ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   organization_id      varchar(32) comment '选择机构ID',
   path                 varchar(200) comment '保存文件路径',
   file_name            varchar(50) comment '文件名称',
   show_index           int(2) comment '排序',
   `year_month`         int(6) comment '年月',
   create_time          datetime comment '创建时间',
   primary key (monthly_report_save_id)
);

alter table monthly_report_save comment '业务表-月报附件保存(monthly_report_save)';

/*==============================================================*/
/* Table: monthly_report_share                                  */
/*==============================================================*/
create table monthly_report_share
(
   monthly_report_share_id varchar(32) not null comment '受益附件ID',
   customer_id          varchar(32) comment '客户ID',
   share_eid            varchar(32) comment '分享员工ID',
   emp_id               varchar(32) comment '受益员工ID',
   organization_id      varchar(32) comment '选择机构ID',
   create_time          datetime comment '创建时间',
   report_content       varchar(50) comment '统计内容',
   send_mail            tinyint(1) comment '是否发邮件',
   path                 varchar(200) comment '保存文件路径',
   note                 varchar(200) comment '备注',
   `year_month`         int(6) comment '年月',
   primary key (monthly_report_share_id)
);

alter table monthly_report_share comment '业务表-月报受益附件保存(monthly_report_share)';

/*==============================================================*/
/* Table: nfb_proportion                                        */
/*==============================================================*/
create table nfb_proportion
(
   nfb_proportion_id    varchar(32) not null comment '社保缴费比率ID',
   customer_id          varchar(32) comment '客户ID',
   city_id              varchar(32) comment '城市ID',
   nfb_id               varchar(32) comment '固定福利ID',
   proportion_value     double(4,3) comment '比率值（范围：0-1）',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (nfb_proportion_id)
);

alter table nfb_proportion comment '业务表-社保缴费比率（nfb_proportion）';

/*==============================================================*/
/* Table: operate_log                                           */
/*==============================================================*/
create table operate_log
(
   operate_log_id       int(10) not null comment '操作日记ID',
   user_id              varchar(32) comment '操作人ID',
   user_key             varchar(20) comment '操作人编码',
   user_name            varchar(10) comment '操作人名称',
   user_name_ch         varchar(5) comment '操作人中文名称',
   ip_address           varchar(15) comment '操作人IP',
   create_date          datetime comment '操作时间',
   description          varchar(100) comment '描述',
   method               varchar(200) comment '方法名',
   params               varchar(100) comment '参数',
   type                 int(10) comment '操作类型',
   exception_code       int(20) comment '错误代号',
   exception_detail     varchar(200) comment '错误描述',
   primary key (operate_log_id)
);

alter table operate_log comment '历史表-登录日记（operate_log）';

/*==============================================================*/
/* Table: organization_change                                   */
/*==============================================================*/
create table organization_change
(
   organization_change_id varchar(32) not null comment '变更ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_key              varchar(20) comment '员工编码',
   user_name_ch         varchar(20) comment '员工姓名',
   change_date          datetime comment '异动日期',
   change_type_id       varchar(32) comment '异动类型ID',
   organization_id      varchar(32) comment '部门ID',
   organization_name    varchar(60) comment '部门名称',
   organization_id_ed   varchar(32) comment '前部门ID',
   organization_name_ed varchar(60) comment '前部门名称',
   position_id          varchar(32) comment '岗位ID',
   position_name        varchar(20) comment '岗位名称',
   sequence_id          varchar(32) comment '序列ID',
   sequence_name        varchar(20) comment '序列名称',
   ability_id           varchar(32) comment '层级ID',
   ability_name         varchar(20) comment '层级名称',
   note                 varchar(5) comment '备注',
   refresh              datetime comment '更新时间',
   `year_month`         int(6) comment '年月',
   primary key (organization_change_id)
);

alter table organization_change comment '历史表-机构异动表（organization_change）';

/*==============================================================*/
/* Index: index_eId                                             */
/*==============================================================*/
create index index_eId on organization_change
(
   emp_id
);

/*==============================================================*/
/* Index: index_eKey                                            */
/*==============================================================*/
create index index_eKey on organization_change
(
   emp_key
);

/*==============================================================*/
/* Index: index_orgId_Day                                       */
/*==============================================================*/
create index index_orgId_Day on organization_change
(
   change_date,
   organization_id
);

/*==============================================================*/
/* Index: ind_Ch_Date                                           */
/*==============================================================*/
create index ind_Ch_Date on organization_change
(
   change_date
);

/*==============================================================*/
/* Index: ind_Ch_Date_Uch                                       */
/*==============================================================*/
create index ind_Ch_Date_Uch on organization_change
(
   change_date,
   user_name_ch
);

/*==============================================================*/
/* Table: organization_emp_relation                             */
/*==============================================================*/
create table organization_emp_relation
(
   organization_emp_relation_id varchar(32) not null comment '机构负责人关系ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   emp_id               varchar(32) comment '负责人ID',
   sys_code_item_id		varchar(32) comment '负责人类型ID： 机构负责人，BP,',
   refresh              datetime comment '更新时间',
   primary key (organization_emp_relation_id)
);
alter table organization_emp_relation comment '关系表-机构负责人关系（organization_emp_relation）';

/*==============================================================*/
/* Table: out_talent                                            */
/*==============================================================*/
create table out_talent
(
   out_talent_id        varchar(32) not null comment '外部人才ID',
   customer_id          varchar(32) comment '客户ID',
   user_name_ch         varchar(5) comment '中文名',
   user_name            varchar(20) comment '英文名',
   email                text comment '邮箱',
   age                  int(3) comment '年龄',
   sex                  char(3) comment '姓别',
   degree_id            varchar(32) comment '学历ID',
   url                  text comment '简历url',
   school               varchar(30) comment '毕业院校',
   refresh              datetime comment '更新时间',
   img_path             varchar(500) comment '图片路径',
   tag                  varchar(100) comment '标签',
   primary key (out_talent_id)
);

alter table out_talent comment '关系表-外部人才库（out_talent）';

/*==============================================================*/
/* Table: pay                                                   */
/*==============================================================*/
create table pay
(
   pay_id               varchar(32) not null comment '薪酬费用ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   usre_name_ch         varchar(5) comment '中文名',
   organization_id      varchar(32) comment '机构ID',
   full_path            varchar(200) comment '机构全路径',
   postion_id           varchar(32) comment '岗位ID',
   pay_contract         blob comment '合同薪酬（单位：元）',
   pay_should           blob comment '应发薪酬（单位：元）',
   pay_actual           blob comment '实发工资（单位：元）',
   salary_actual        blob comment '应发工资（单位：元）',
   welfare_actual       varchar(50) comment '应发福利（单位：元）',
   cr_value             double(6,4) comment 'CR值（过）',
   `year_month`         int(6) comment '年月',
   refresh              datetime comment '更新时间',
   year                 int(4) comment '年',
   bonus                 varchar(10) comment 'bonus',
   primary key (pay_id)
);

alter table pay comment '业务表-薪酬费用（pay）';

/*==============================================================*/
/* Index: index_cusId                                           */
/*==============================================================*/
create index index_cusId on pay
(
   customer_id
);

/*==============================================================*/
/* Index: "index_eId_ym_pay y"                                  */
/*==============================================================*/
create index "index_eId_ym_pay y" on pay
(
   `year_month`,
   emp_id
);

/*==============================================================*/
/* Index: index_fp                                              */
/*==============================================================*/
create index index_fp on pay
(
   full_path
);

/*==============================================================*/
/* Index: index_ym     大量行具有重复值，无大效果，浪费空间                 */
/*==============================================================*/
/*create index index_ym on pay*/
/*(*/
 /*  `year_month`*/
/*);*/

/*==============================================================*/
/* Index: index_ym_orgId                                        */
/*==============================================================*/
create index index_ym_orgId on pay
(
   `year_month`,
   organization_id
);

/*==============================================================*/
/* Index: ind_org_pay                                           */
/*==============================================================*/
create index ind_org_pay on pay
(
   organization_id
);

/*==============================================================*/
/* Table: pay_collect                                           */
/*==============================================================*/
create table pay_collect
(
   pay_collect_id       varchar(32) not null comment '薪酬汇总ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   sum_pay              double(10,4) comment '薪酬总额（单位：万元）汇',
   avg_pay              double(10,4) comment '薪酬平均（单位：万元）',
   avg_emp_num          double(10,5) comment '平均人数',
   sum_salary           double(10,4) comment '工资总额-应发工资（单位：万元）汇',
   avg_salary           double(10,4) comment '工资平均-应发工资（单位：万元）',
   sum_welfare          double(10,4) comment '福利总额（单位：万元）汇',
   avg_welfare          double(10,4) comment '福利平均（单位：万元）',
   sum_bonus            double(10,5) comment '奖金总额',
   quantile_value       double(10,4) comment '50分位值-应发薪酬（过、单位：万元）',
   `year_month`         int(6) comment '年月',
   refresh              datetime comment '更新时间',
   primary key (pay_collect_id)
);

alter table pay_collect comment '业务表-薪酬汇总表（应发）（pay_collect）';

/*==============================================================*/
/* Index: index_ym                                              */
/*==============================================================*/
create index index_ym on pay_collect
(
   `year_month`
);

/*==============================================================*/
/* Table: pay_collect_year                                      */
/*==============================================================*/
create table pay_collect_year
(
   pay_collect_yeah_id  varchar(32) not null comment '薪酬汇总ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   sum_pay              double(10,4) comment '薪酬总额（单位：万元）',
   avg_pay              double(10,4) comment '薪酬平均（单位：万元）',
   sum_salary           double(10,4) comment '工资总额（单位：万元）',
   avg_salary           double(10,4) comment '工资平均（单位：万元）',
   sum_welfare          double(10,4) comment '福利总额（单位：万元）',
   avg_welfare          double(10,4) comment '福利平均（单位：万元）',
   sum_share            int(5) comment '持股数量（单位：股）',
   count_share          int(5) comment '持股员工总数（单位：股）',
   sum_bonus            double(10,4) comment '奖金（单位：万元）',
   year                 int(5) comment '年',
   refresh              datetime comment '更新时间',
   primary key (pay_collect_yeah_id)
);

alter table pay_collect_year comment '历史表-薪酬汇总过往（应发 年）（pay_collect_year）';

/*==============================================================*/
/* Table: pay_value                                             */
/*==============================================================*/
create table pay_value
(
   pay_value_id         varchar(32) not null comment '薪酬汇总ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   pay_value            double(10,4) comment '预算薪酬（单位：万元）',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (pay_value_id)
);

alter table pay_value comment '业务表-薪酬预算（年）（pay_value）';

/*==============================================================*/
/* Table: performance_change                                    */
/*==============================================================*/
create table performance_change
(
   performance_change_id varchar(32) not null comment '变更ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_key              varchar(20) comment '员工编码',
   organization_parent_id varchar(32) comment '单位ID（机构类别为2）',
   organzation_parent_name varchar(60) comment '单位名称',
   organization_id      varchar(32) comment '部门ID（机构类别为3）',
   organization_name    varchar(60) comment '部门名称',
   position_id          varchar(32) comment '岗位ID',
   position_name        varchar(20) comment '岗位名称',
   performance_id       varchar(32) comment '绩效ID',
   performance_name     varchar(60) comment '绩效名称',
   rank_name            varchar(5) comment '简称',
   rank_name_past       varchar(5) comment '前简称',
   `year_month`         int(6) comment '年月',
   type                 int(1) comment '类型',
   primary key (performance_change_id)
);

alter table performance_change comment '历史表-绩效信息（performance_change）';

/*==============================================================*/
/* Index: index_eId                                             */
/*==============================================================*/
create index index_eId on performance_change
(
   emp_id
);

/*==============================================================*/
/* Index: index_customerId                                      */
/*==============================================================*/
create index index_customerId on performance_change
(
   customer_id
);

/*==============================================================*/
/* Index: index_eKey                                            */
/*==============================================================*/
create index index_eKey on performance_change
(
   emp_key
);

/*==============================================================*/
/* Index: index_orgId                                           */
/*==============================================================*/
create index index_orgId on performance_change
(
   organization_id
);

/*==============================================================*/
/* Index: index_perId                                           */
/*==============================================================*/
create index index_perId on performance_change
(
   performance_id
);

/*==============================================================*/
/* Index: index_perId_ym_performance_change                     */
/*==============================================================*/
create index index_perId_ym_performance_change on performance_change
(
   performance_id,
   `year_month`
);

/*==============================================================*/
/* Index: index_ym                                              */
/*==============================================================*/
create index index_ym on performance_change
(
   `year_month`
);

/*==============================================================*/
/* Table: permiss_import                                        */
/*==============================================================*/
create table permiss_import
(
   emp_id               varchar(32) not null comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   note                 varchar(100) comment '备注',
   primary key (emp_id)
);

alter table permiss_import comment '数据导入权限(emp_att_permiss_import)';

/*==============================================================*/
/* Table: population_relation                                   */
/*==============================================================*/
CREATE TABLE `population_relation` (
	`population_relation_id` VARCHAR(32) NOT NULL DEFAULT '',
	`customer_id` VARCHAR(32) NULL DEFAULT NULL,
	`population_id` VARCHAR(32) NULL DEFAULT NULL,
	`emp_id` VARCHAR(32) NULL DEFAULT NULL COMMENT '员工ID',
	`organization_id` VARCHAR(32) NULL DEFAULT NULL,
	`days` DATE NULL DEFAULT NULL,
	INDEX `ind_days` (`days`),
	INDEX `ind_eId` (`emp_id`),
	INDEX `ind_day_eId` (`days`, `emp_id`),
	INDEX `ind_day_pId` (`days`, `population_id`),
	INDEX `ind_eId_day` (`emp_id`, `days`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=MyISAM
;

/*==============================================================*/
/* Table: population_relation_month                             */
/*==============================================================*/
CREATE TABLE population_relation_month
(
   population_relation_month_id varchar(32) not null comment '人员人群关系ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   organization_id      varchar(32) comment '机构ID',
   population_id        varchar(32) comment '人群范围ID',
   `year_month`         int(6) comment '年月',
   PRIMARY KEY (population_relation_month_id),
   INDEX `ind_ym_pop` (`year_month`, `population_id`) USING BTREE,
   INDEX `ind_eId_ym_pop` (`year_month`, `emp_id`) USING BTREE
)
COMMENT='关系表-人员人群关系表(population_relation_month)'
COLLATE='utf8_general_ci'
ENGINE=Aria
;


/*==============================================================*/
/* Table: position_quality                                      */
/*==============================================================*/
create table position_quality
(
   position_quality_id  varchar(32) not null comment '岗位能力素质ID',
   customer_id          varchar(32) comment '客户ID',
   position_id          varchar(32) comment '岗位ID',
   quality_id           varchar(32) comment '能力素质ID',
   matching_soure_id    varchar(32) comment '分数映射ID',
   show_index           tinyint(1) comment '排序',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (position_quality_id)
);

alter table position_quality comment '关系表-岗位能力素质（position_quality）';

/*==============================================================*/
/* Index: ind_pId_qId                                           */
/*==============================================================*/
create index ind_pId_qId on position_quality
(
   quality_id,
   position_id
);

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create unique index un_cid on position_quality
(
   c_id
);

/*==============================================================*/
/* Table: profession_quantile_relation                          */
/*==============================================================*/
create table profession_quantile_relation
(
   profession_quantile_id varchar(32) not null comment '行业分位ID',
   customer_id          varchar(32) comment '客户ID',
   profession_id        varchar(32) comment '行业ID',
   quantile_id          varchar(32) comment '分位ID',
   quantile_value       double(4,3) comment '分位值（单位：万元）',
   type                 tinyint(1) comment '类别（1人均；2成本；3薪酬）',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (profession_quantile_id)
);

alter table profession_quantile_relation comment '关系表-行业分位（profession_quantile_relation）';

/*==============================================================*/
/* Table: profession_value                                      */
/*==============================================================*/
create table profession_value
(
   profession_value     varchar(32) not null comment '行业指标值ID',
   profession_name      varchar(50) comment '行业指标值名称',
   profession_values_key varchar(20) comment '行业指标值编码',
   value                double(6,2) comment '行业指标值',
   profession_id        varchar(32) comment '行业ID',
   refresh              datetime comment '更新日期',
   primary key (profession_value)
);

alter table profession_value comment '业务表-行业指标值（profession_value）';

/*==============================================================*/
/* Table: project                                               */
/*==============================================================*/
create table project
(
   project_id           varchar(32) not null comment '项目ID',
   customer_id          varchar(32) comment '客户ID',
   project_key          varchar(32) comment '项目编码',
   project_name         varchar(30) comment '项目名称',
   emp_id               varchar(32) comment '负责人',
   organization_id      varchar(32) comment '主导机构',
   project_type_id      varchar(32) comment '项目类型ID',
   project_progress_id  varchar(32) comment '项目进度ID',
   project_parent_id    varchar(32) comment '父项目ID',
   full_path            text comment '全路径',
   has_chilren          tinyint(1) comment '是否有子节点',
   start_date           date comment '项目开始时间',
   end_date             date comment '项目结束时间',
   refresh              date comment '更新日期',
   primary key (project_id)
);
alter table project comment '业务表-项目（project）';

/*==============================================================*/
/* Table: project_cost                                          */
/*==============================================================*/
create table project_cost
(
   project_cost_id       varchar(32) not null comment '项目费用明细ID',
   customer_id          varchar(32) comment '客户ID',
   input                double(10,5) comment '投入（单位：万元）',
   output               double(10,5) comment '产出（单位：万元）',
   gain                 double(10,5) comment '利润（单位：万元）',
   project_id           varchar(32) comment '项目ID',
   date                 date comment '盘点日期',
   type                 tinyint(1) comment '日期类型(1季、2月、3双月)',
   primary key (project_cost_id)
);
alter table project_cost comment '业务表-项目费用明细（project_cost）';

/*==============================================================*/
/* Table: project_input_detail                                  */
/*==============================================================*/
create table project_input_detail
(
   project_input_detail_id varchar(32) not null comment '项目投入明细ID',
   customer_id          varchar(32) comment '客户ID',
   project_id           varchar(32) comment '主导项目ID',
   project_input_type_id varchar(32) comment '投入类型ID',
   outlay               double(12,7) comment '花费（单位：万元）',
   date                 date comment '盘点日期',
   type                 tinyint(1) comment '日期类型(1季、2月、3双月)',
   primary key (project_input_detail_id)
);
alter table project_input_detail comment '关系表-项目投入费用明细（project_input_detail）';

/*==============================================================*/
/* Table: project_manpower                                      */
/*==============================================================*/
create table project_manpower
(
   project_manpower_id  varchar(32) not null comment '项目人力明细ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '参与员工',
   input                double(10,2) comment '人力投入',
   note                 text comment '工作内容',
   project_id           varchar(32) comment '项目ID',
   project_sub_id       varchar(32) comment '所服务子项目',
   date                 date comment '盘点日期',
   type                 tinyint(1) comment '日期类型(1季、2月、3双月)',
   primary key (project_manpower_id)
);
alter table project_manpower comment '业务表-项目人力明细（project_manpower）';

/*==============================================================*/
/* Table: project_maxvalue                                      */
/*==============================================================*/
create table project_maxvalue
(
   project_maxvalue_id  varchar(32) not null comment '项目最大负荷数ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   maxval               int(4) comment '数值',
   total_work_hours     double(5,2) comment '部门工时总数',
   refresh              datetime comment '更新时间',
   primary key (project_maxvalue_id)
);
alter table project_maxvalue comment '业务表-项目最大负荷数（project_maxvalue）';

/*==============================================================*/
/* Table: project_partake_relation                              */
/*==============================================================*/
create table project_partake_relation
(
   project_partake_id   varchar(32) not null comment '主导项目参与项目ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '参与机构ID',
   project_id           varchar(32) comment '项目ID',
   refresh              date comment '更新日期',
   primary key (project_partake_id)
);
alter table project_partake_relation comment '关系表-主导项目参与项目关系（project_partake_relation）';

/*==============================================================*/
/* Table: promotion_analysis                                    */
/*==============================================================*/
create table promotion_analysis
(
   promotion_analysis_id varchar(32) not null comment '员工晋级分析ID',
   emp_id               varchar(32) comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   promotion_req        varbinary(50) comment '晋级要求',
   persoma_status       varbinary(50) comment '个人状态',
   is_accord            int(1) comment '是否符合',
   analysis_date        date comment '分析时间',
   note                 varchar(40) comment '备注',
   primary key (promotion_analysis_id)
);

alter table promotion_analysis comment '员工占比分析(promotion_analysis)';

/*==============================================================*/
/* Index: ind_eId                                               */
/*==============================================================*/
create index ind_eId on promotion_analysis
(
   emp_id
);

/*==============================================================*/
/* Table: promotion_element_scheme                              */
/*==============================================================*/
create table promotion_element_scheme
(
   scheme_id            varchar(32) not null comment '方案ID',
   customer_id          varchar(32) comment '客户ID',
   scheme_name          varchar(50) comment '方案名称',
   company_age          int(3) comment '司龄',
   curt_name_per        varchar(30) comment '绩效短名',
   curt_name_eval       int(4) comment '能力评价短名',
   certificate_id       varchar(32) comment '证书ID',
   certificate_type_id  varchar(32) comment '证书类型ID',
   create_user_id       varchar(32) comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   start_date           datetime comment '生效时间',
   invalid_date         datetime comment '失效时间',
   primary key (scheme_id),
   	INDEX `idx_sId` (`scheme_id`) USING BTREE
);
alter table promotion_element_scheme comment '晋级要素方案(promotion_element_scheme)';

/*==============================================================*/
/* Index: idx_sId                                               */
/*==============================================================*/
create index idx_sId on promotion_element_scheme
(
   scheme_id
);

/*==============================================================*/
/* Table: promotion_plan                                        */
/*==============================================================*/
create table promotion_plan
(
   promotion_plan_id    varbinary(32) not null comment '职级晋升方案ID',
   customer_id          varchar(32) comment '客户ID',
   rank_name_af         varbinary(5) comment '晋级后简称',
   scheme_id            varchar(32) comment '方案ID',
   create_user_id       varchar(32) comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   primary key (promotion_plan_id)
);

alter table promotion_plan comment '职级晋升方案(promotion_plan)';

/*==============================================================*/
/* Table: promotion_results                                     */
/*==============================================================*/
create table promotion_results
(
   promotion_results_id varchar(32) not null comment '员工晋级结果ID',
   emp_id               varchar(32) comment '员工ID',
   company_age          int(2) comment '司龄',
   oranization_parent_id varchar(32) comment '父部门ID',
   organization_id      varchar(32) comment '部门ID',
   full_path            varchar(20) comment '部门全路径',
   performance_id       varchar(32) comment '绩效ID',
   sequence_id          varchar(32) comment '主序列ID',
   is_key_talent        int(1) comment '是否关键人才',
   customer_id          varchar(32) comment '客户ID',
   entry_date           date comment '入职时间',
   show_index           int(1) comment '排序',
   rank_name            varchar(5) comment '当前简称',
   show_index_af        int(1) comment '晋级后排序',
   rank_name_af         varchar(5) comment '晋级后简称',
   status               int(1) comment '晋级状态',
   status_date          date comment '结果时间',
   promotion_date       date comment '晋级时间',
   primary key (promotion_results_id)
);

alter table promotion_results comment '员工晋级结果(promotion_results)';

/*==============================================================*/
/* Index: idx_eId                                               */
/*==============================================================*/
create index idx_eId on promotion_results
(
   emp_id
);

/*==============================================================*/
/* Index: ind_Org_date                                          */
/*==============================================================*/
create index ind_Org_date on promotion_results
(
   organization_id,
   status_date
);

/*==============================================================*/
/* Index: ind_org_pd_promotion_date                             */
/*==============================================================*/
create index ind_org_pd_promotion_date on promotion_results
(
   promotion_date,
   organization_id
);

/*==============================================================*/
/* Index: Index_4                                               */
/*==============================================================*/
create index Index_4 on promotion_results
(

);

/*==============================================================*/
/* Table: promotion_total                                       */
/*==============================================================*/
create table promotion_total
(
   promotion_total_id   varchar(32) not null comment '员工晋级统计ID',
   scheme_id            varbinary(32) comment '方案ID',
   emp_id               varchar(32) comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   rank_name            varchar(5) comment '当前简称',
   rank_name_af         varchar(5) comment '晋级后简称',
   organization_id      varchar(32) comment '部门ID',
   status               int(1) comment '申请状态',
   condition_prop       double(3,1) comment '条件符合占比',
   total_date           date comment '统计时间',
   primary key (promotion_total_id)
);

alter table promotion_total comment '员工占比统计(promotion_total)';

/*==============================================================*/
/* Index: ind_tod                                               */
/*==============================================================*/
create index ind_tod on promotion_total
(
   total_date,
   customer_id
);

/*==============================================================*/
/* Table: quota_memo                                            */
/*==============================================================*/
create table quota_memo
(
   memo_id              varchar(32) not null comment '备忘录ID',
   customer_id          varchar(32) comment '客户ID',
   quota_id             varchar(32) comment '指标ID',
   content              text comment '内容',
   organization_id      varchar(32) comment '机构ID',
   emp_id               varchar(32) not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   is_delete            int(10) not null comment '是否删除',
   delete_time          datetime comment '删除时间',
   primary key (memo_id)
);

alter table quota_memo comment '业务表-备忘（quota_memo）';

/*==============================================================*/
/* Table: quota_memo_status                                     */
/*==============================================================*/
create table quota_memo_status
(
   memo_status_id       varchar(32) not null comment '备忘状态ID',
   customer_id          varchar(32) comment '客户ID',
   memo_id              varchar(32) comment '备忘录ID',
   emp_id               varchar(32) comment '已读员工ID',
   read_time            datetime comment '阅读时间',
   primary key (memo_status_id)
);

alter table quota_memo_status comment '业务表-已读员工备忘状态（quota_memo_status）';

/*==============================================================*/
/* Table: quota_memo_tips                                       */
/*==============================================================*/
create table quota_memo_tips
(
   quota_memo_tips_id   varchar(32) not null comment '锦囊ID',
   customer_id          varchar(32) comment '客户ID',
   title                varchar(32) comment '标题',
   show_index           int(3) comment '排序',
   primary key (quota_memo_tips_id)
);

alter table quota_memo_tips comment '业务表-锦囊（quota_memo_tips）';

/*==============================================================*/
/* Table: quota_memo_tips_item                                  */
/*==============================================================*/
create table quota_memo_tips_item
(
   quota_memo_tips_item_id varchar(32) not null comment '锦囊项ID',
   customer_id          varchar(32) comment '客户ID',
   quota_memo_tips_id   varchar(32) not null comment '锦囊ID',
   content              longtext comment '内容',
   show_index           int(3) comment '排序',
   primary key (quota_memo_tips_item_id)
);

alter table quota_memo_tips_item comment '业务表-锦囊项（quota_memo_tips_item）';

/*==============================================================*/
/* Table: recruit_channel                                       */
/*==============================================================*/
create table recruit_channel
(
   recruit_channel_id   varchar(32) not null comment '应聘渠道ID',
   customer_id          varchar(32) comment '客户ID',
   channel_id           varchar(32) comment '渠道ID',
   position_id          varchar(32) comment '职位ID',
   employ_num           int(4) comment '已招人数',
   outlay               double(10,2) comment '花费（单位：元）',
   start_date           datetime comment '招聘开始时间',
   end_date             datetime comment '招聘结束时间',
   days                 int(4) comment '天数',
   recruit_public_id    varchar(32) comment '招聘发布ID',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (recruit_channel_id)
);

alter table recruit_channel comment '业务表-招聘渠道（recruit_channel）';

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create unique index un_cid on recruit_channel
(
   c_id
);

/*==============================================================*/
/* Table: recruit_public                                        */
/*==============================================================*/
create table recruit_public
(
   recruit_public_id    varchar(32) not null comment '招聘发布ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '发布机构ID',
   position_id          varchar(32) comment '招聘岗位ID',
   employ_num           int(4) comment '已招人数（过）',
   plan_num             int(4) comment '计划人数',
   start_date           datetime comment '招聘开始时间',
   end_date             datetime comment '招聘结束时间',
   days                 int(4) comment '天数',
   resume_num           int(4) comment '简历数（过）',
   interview_num        int(4) comment '面试数（过）',
   offer_num            int(4) comment '通过数（过）',
   entry_num            int(4) comment '入职数（过）',
   is_public            tinyint(1) comment '招聘岗位状态',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (recruit_public_id)
);

alter table recruit_public comment '业务表-招聘发布（recruit_public）';

/*==============================================================*/
/* Table: recruit_result                                        */
/*==============================================================*/
create table recruit_result
(
   recruit_result_id    varchar(32) not null comment '应聘结果ID',
   customer_id          varchar(32) comment '客户ID',
   recruit_result_key   varchar(20) comment '应聘者编号',
   username             varchar(6) comment '姓名',
   sex                  char(3) comment '性别',
   age                  double(5,2) comment '年龄',
   degree_id            varchar(32) comment '学历ID',
   major                varchar(30) comment '专业',
   school               varchar(30) comment '毕业院校',
   is_resume            tinyint(1) comment '是否投简',
   is_interview         tinyint(1) comment '是否面试',
   is_offer             tinyint(1) comment '是否通过',
   is_entry             tinyint(1) comment '是否入职',
   url                  text comment '简历url',
   recruit_public_id    varchar(32) comment '招聘发布ID',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (recruit_result_id)
);

alter table recruit_result comment '业务表-招聘结果（recruit_result）';

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create unique index un_cid on recruit_result
(
   c_id
);

/*==============================================================*/
/* Table: recruit_salary_statistics                             */
/*==============================================================*/
create table recruit_salary_statistics
(
   recruit_salary_statistics_id varchar(32) not null comment '招聘岗位薪酬ID',
   customer_id          varchar(32) comment '客户ID',
   positition_id        varchar(32) comment '岗位ID',
   positition_name      varbinary(60) comment '岗位名称',
   rank_name            varchar(5) comment '主子序列层级职级(短名)',
   avg_sal              double(10,2) comment '平均工资（单位：元）',
   emp_total            int(5) comment '员工总数',
   year_months          int(10) comment '年月',
   primary key (recruit_salary_statistics_id)
);

alter table recruit_salary_statistics comment '招聘岗位薪酬(recruit_salary_statistics
)';

/*==============================================================*/
/* Index: ind_PosY                                              */
/*==============================================================*/
create index ind_PosY on recruit_salary_statistics
(
   year_months,
   positition_id
);

/*==============================================================*/
/* Table: recruit_value                                         */
/*==============================================================*/
create table recruit_value
(
   recruit_value_id     varchar(32) not null comment '招聘年度费用',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   budget_value         double(12,6) comment '预算度本（单位：万）',
   outlay               double(12,6) comment '已花成本（单位：万、过）',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (recruit_value_id)
);

alter table recruit_value comment '业务表-招聘年度费用（年）（recruit_value）';

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create unique index un_cid on recruit_value
(
   c_id
);

/*==============================================================*/
/* Table: recruitment_process                                   */
/*==============================================================*/
create table recruitment_process
(
   recruitment_process_id varchar(32) not null comment '招聘进程ID',
   customer_id          varchar(32) comment '客户ID',
   publice_job_num      int(3) comment '发布职位数',
   resume_num           int(3) comment '简历数',
   accept_num           int(3) comment '应聘数',
   offer_num            int(3) comment 'offer数',
   organization_id      varchar(32) comment '机构ID',
   primary key (recruitment_process_id)
);

alter table recruitment_process comment '业务表-招聘进程（recruitment_process）';

/*==============================================================*/
/* Table: role_organization_relation                            */
/*==============================================================*/
create table role_organization_relation
(
   role_organization_id varchar(32) not null comment '用户机构',
   customer_id          varchar(32) comment '客户ID',
   role_id              varchar(32) comment '角色ID',
   organization_id      varchar(32) comment '机构ID',
   half_check           int(1) comment '半勾状态',
   create_user_id       varchar(32) not null comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime not null comment '创建时间',
   modify_time          datetime comment '修改时间',
   primary key (role_organization_id)
);

alter table role_organization_relation comment '关系表-角色机构关系（role_organization_relation）';

/*==============================================================*/
/* Index: index_customerId                                      */
/*==============================================================*/
create index index_customerId on role_organization_relation
(
   customer_id
);

/*==============================================================*/
/* Index: index_halfCheck                                       */
/*==============================================================*/
create index index_halfCheck on role_organization_relation
(
   half_check
);

/*==============================================================*/
/* Table: run_off_record                                        */
/*==============================================================*/
create table run_off_record
(
   run_off_record_id    varchar(32) not null comment '流失记录ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   run_off_id           varchar(32) comment '流失项ID',
   where_abouts_id      varchar(32) comment '流失去向ID',
   where_abouts         varchar(200) comment '流失去向',
   run_off_date         datetime not null comment '流失日期',
   is_warn              int(1) comment '是否有过预警',
   re_hire              int(1) comment '建议重新录用',
   primary key (run_off_record_id)
);

alter table run_off_record comment '业务表-流失记录(run_off_record)';

/*==============================================================*/
/* Index: ind_eId_run_off_record                                */
/*==============================================================*/
create index ind_eId_run_off_record on run_off_record
(
   emp_id
);

/*==============================================================*/
/* Table: run_off_total                                         */
/*==============================================================*/
create table run_off_total
(
   run_off_total_id     varchar(32) not null comment '月度总人数ID',
   organization_id      varchar(32) comment '机构ID',
   organization_full_path  varchar(200) comment '机构全路径',
   customer_id          varchar(32) comment '客户ID',
   act_count            int(6) comment '本部门主动流失总人数',
   un_act_count         int(6) comment '本部门被动流失总人数',
   act_count_sum        int(6) comment '本部门、子孙主动流失总人数',
   un_act_count_sum     int(6) comment '本部门、子孙被动流失总人数',
   `year_month`         int(6) comment '年月',
   refresh              datetime comment '更新时间',
   primary key (run_off_total_id)
);

alter table run_off_total comment '业务表-流失率月度总人数（run_off_total）';

/*==============================================================*/
/* Index: index_ofp                                             */
/*==============================================================*/
create index index_ofp on run_off_total
(
   organization_full_path
);

/*==============================================================*/
/* Index: index_ym                                              */
/*==============================================================*/
create index index_ym on run_off_total
(
   `year_month`
);

/*==============================================================*/
/* Table: salary                                                */
/*==============================================================*/
create table salary
(
   salary_id            varchar(32) not null comment '工资ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   structure_id         varchar(32) comment '工资结构ID',
   salary_value         double(10,5) comment '值（单位：万元）',
   `year_month`         int(10) not null comment '年月',
   refresh              datetime comment '更新时间',
   primary key (salary_id, `year_month`)
);

alter table salary comment '业务表-工资明细（salary）';

/*==============================================================*/
/* Index: index_eId_ym_salary                                   */
/*==============================================================*/
create index index_eId_ym_salary on salary
(
   emp_id,
   `year_month`
);

/*==============================================================*/
/* Index: index_ym                                              */
/*==============================================================*/
create index index_ym on salary
(
   `year_month`
);

/*==============================================================*/
/* Table: salary_year                                           */
/*==============================================================*/
create table salary_year
(
   salary_id            varchar(32) not null comment '工资ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   structure_id         varchar(32) comment '工资结构ID',
   salary_value_year    double(10,4) comment '值（单位：万元）',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (salary_id)
);

alter table salary_year comment '业务表-工资明细（salary_year）';

/*==============================================================*/
/* Index: ind_strId_y_salary_year                               */
/*==============================================================*/
create index ind_strId_y_salary_year on salary_year
(
   structure_id,
   year
);

/*==============================================================*/
/* Table: sales_ability                                         */
/*==============================================================*/
create table sales_ability
(
   emp_id               varchar(32) not null comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   status               int(1) comment '考核状态',
   check_date           date comment '考核时间',
   primary key (emp_id)
);

alter table sales_ability comment '业务能力考核(sales_ability)';

/*==============================================================*/
/* Table: sales_client_contacts                                 */
/*==============================================================*/
create table sales_client_contacts
(
   contacts_id          varchar(32) not null comment '客户联系人ID',
   customer_id          varchar(32) comment '客户ID',
   contacts_name        varchar(32) comment '客户联系人名称',
   client_id            varchar(32) comment '销售客户ID',
   contacts_dept        varchar(50) comment '客户联系人部门',
   sex                  varchar(1) comment '性别',
   age                  int(3) comment '年龄',
   native_place         varchar(90) comment '籍贯',
   contacts_parent_name varchar(32) comment '直属上司',
   post                 varchar(32) comment '职务',
   marriage             int(1) comment '婚姻',
   children             int(1) comment '子女数量',
   interest             varchar(100) comment '兴趣',
   disposition          varchar(100) comment '性格',
   company_age          int(3) comment '司龄',
   contacts_telephone   varchar(100) comment '联系手机',
   contacts_email       varchar(100) comment '联系e-mail',
   primary key (contacts_id)
);

alter table sales_client_contacts comment '销售客户联系人表（sales_client_contacts）';

/*==============================================================*/
/* Table: sales_client_plan                                     */
/*==============================================================*/
create table sales_client_plan
(
   sales_client_plan_id varchar(32) not null comment '沟通纪要ID',
   customer_id          varchar(32) comment '客户ID',
   client_id            varchar(32) comment '销售客户ID',
   plan_note            text comment '沟通纪要内容',
   plan_key_note        varchar(200) comment '沟通纪要要点',
   plan_time            datetime comment '创建时间',
   interested_product   varchar(100) comment '感兴趣产品',
   summary_principal    varchar(32) comment '沟通负责人',
   contacts_id          varchar(32) comment '沟通计划联系人',
   primary key (sales_client_plan_id)
);

alter table sales_client_plan comment '销售客户沟通计划表（sales_client_plan）';

/*==============================================================*/
/* Table: sales_client_summary                                  */
/*==============================================================*/
create table sales_client_summary
(
   summary_id           varchar(32) not null comment '沟通纪要ID',
   customer_id          varchar(32) comment '客户ID',
   client_id            varchar(32) comment '销售客户ID',
   note                 text comment '沟通纪要内容',
   key_note             varchar(200) comment '沟通纪要要点',
   interested_product   varchar(100) comment '感兴趣产品',
   summary_principal    varchar(20) comment '沟通负责人',
   contacts_id          varchar(32) comment '沟通联系人',
   create_time          datetime comment '创建时间',
   primary key (summary_id)
);

alter table sales_client_summary comment '销售客户沟通纪要表（sales_client_summary）';

/*==============================================================*/
/* Table: sales_config                                          */
/*==============================================================*/
create table sales_config
(
   emp_id               varchar(32) not null comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   region               varchar(20) comment '区域',
   yellow_range         varchar(20) comment '黄色预警范围',
   red_range            varchar(20) comment '红色预警范围',
   note                 varchar(20) comment '备注',
   primary key (emp_id)
);

alter table sales_config comment '销售预警设置(sales_config)';

/*==============================================================*/
/* Table: sales_detail                                          */
/*==============================================================*/
create table sales_detail
(
   sales_detail_id      int(10) not null auto_increment comment '明细ID',
   customer_id          varchar(32) comment '客户ID',
   product_id           varchar(32) comment '商品ID',
   sales_order_id 		varchar(32)	comment '订单id',
   product_number       int(5) comment '销售数量',
   sales_money          double(10,2) comment '实际金额（单位：元）',
   sales_profit         double(10,2) comment '商品利润（单位：元）',
   product_modul_id     varchar(32) comment '商品模块ID',
   product_category_id  varchar(32) comment '商品类型ID',
   primary key (sales_detail_id)
);

alter table sales_detail comment '订单明细(sales_detail)';

/*==============================================================*/
/* Table: sales_order                                           */
/*==============================================================*/
create table sales_order
(
   sales_order_id 		varchar(32)	comment '订单id',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '销售员工ID(卖方)',
   client_id            varchar(32) comment '客户ID(买方)',
   province_id          varchar(32) comment '销售地点(省)',
   city_id              varchar(32) comment '销售地点(市)',
   sales_date           date comment '销售时间',
   primary key (sales_order_id)
);

alter table sales_order comment '订单(sales_order)';
/*==============================================================*/
/* Table: sales_emp                                             */
/*==============================================================*/
create table sales_emp
(
   emp_id               varchar(32) not null comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   user_name_ch         varchar(20) comment '员工姓名',
   organization_id      varchar(32) comment '机构ID',
   age                  int(3) comment '年龄',
   sex                  varchar(1) comment '性别',
   performance_id       varchar(32) comment '绩效',
   degree_id            varchar(32) comment '学位ID',
   team_id              varchar(32) comment '团队ID',
   primary key (emp_id)
);

alter table sales_emp comment '销售人员信息表(sales_emp)';

/*==============================================================*/
/* Index: ind_orgId_sales_emp                                   */
/*==============================================================*/
create index ind_orgId_sales_emp on sales_emp
(
   organization_id
);

/*==============================================================*/
/* Table: sales_emp_client_relation                             */
/*==============================================================*/
create table sales_emp_client_relation
(
   sales_emp_client_id  varchar(32) not null comment '销售人员与客户关系表',
   emp_id               varchar(32) comment '销售人员ID',
   customer_id          varchar(32) comment '客户ID',
   client_id            varchar(32) comment '销售客户ID',
   client_type          varchar(1),
   primary key (sales_emp_client_id)
);

alter table sales_emp_client_relation comment '销售人员与客户关系表（sales_emp_client_relation）';

/*==============================================================*/
/* Table: sales_emp_month                                       */
/*==============================================================*/
create table sales_emp_month
(
   emp_id               varchar(32) not null comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   sales_target         double(10,4) comment '目标销售额(单位：元)',
   payment              int(10) comment '已回款',
   return_amount        int(10) comment '回款额',
   sales_money_month    double(10,4) comment '销售金额（单位：元）',
   sales_profit_month   double(10,4) comment '销售利润（单位：元）',
   sales_number_month   int(10) comment '销售数量',
   `year_month`         int(6) not null comment '年月',
   primary key (emp_id, `year_month`)
);

alter table sales_emp_month comment '员工销售月统计(sales_emp_month)';

/*==============================================================*/
/* Table: sales_emp_rank                                        */
/*==============================================================*/
create table sales_emp_rank
(
   emp_id               varchar(32) not null comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   emp_rank             int(6) comment '排名',
   organization_id      varchar(32) not null comment '所排名机构',
   rank_date            date not null comment '时间',
   `year_month`         int(6) comment '年月',
   proportion_id        varchar(32) comment '占比ID',
   primary key (emp_id, organization_id, rank_date)
);

alter table sales_emp_rank comment '员工销售排名(sales_emp_rank)';

/*==============================================================*/
/* Index: ind_ym                                                */
/*==============================================================*/
create index ind_ym on sales_emp_rank
(
   `year_month`
);

/*==============================================================*/
/* Table: sales_emp_target                                      */
/*==============================================================*/
create table sales_emp_target
(
   emp_id               varchar(32) not null comment '员工ID',
   cusomer_id           varchar(32) comment '客户ID',
   sales_target         double(10,4) comment '目标销售额(单位：万元',
   return_amount        double(10,4) comment '回款额(单位：万元)',
   payment              double(10,4) comment '已回款(单位：万元)',
   `year_month`         int(6) not null comment '年月',
   primary key (emp_id, `year_month`)
);

alter table sales_emp_target comment '员工考核(sales_emp_target)';


/*==============================================================*/
/* Table: sales_org_day                                         */
/*==============================================================*/
create table sales_org_day
(
   organization_id      varchar(32) not null comment '机构ID',
   customer_id          varchar(32) comment '客户ID',
   sales_money_day      double(10,4) comment '销售金额（单位：万元）',
   sales_profit_day     double(10,4) comment '销售利润（单位：万元）',
   sales_number_day     int(10) comment '销售数量',
   sales_date           date not null comment '销售时间',
   primary key (organization_id, sales_date)
);

alter table sales_org_day comment '机构销售日统计(sales_org_day)';

/*==============================================================*/
/* Table: sales_org_month                                       */
/*==============================================================*/
create table sales_org_month
(
   organization_id      varchar(32) not null comment '机构ID',
   customer_id          varchar(32) comment '客户ID',
   sales_money_month    double(10,4) comment '销售金额（单位：万元）',
   sales_profit_month   double(10,4) comment '销售利润（单位：万元）',
   sales_number_month   int(10) comment '销售数量',
   `year_month`         int(6) not null comment '年月',
   primary key (organization_id, `year_month`)
);

alter table sales_org_month comment '机构销售月统计(sales_org_month)';

/*==============================================================*/
/* Table: sales_org_prod_month                                  */
/*==============================================================*/
create table sales_org_prod_month
(
   organization_id      varchar(32) not null comment '机构ID',
   product_id           varchar(32) not null comment '产品ID',
   customer_id          varchar(32) comment '客户ID',
   sales_money_month    double(10,4) comment '销售金额（单位：万元）',
   sales_profit_month   double(10,4) comment '销售利润（单位：万元）',
   sales_number_month   int(10) comment '销售数量',
   sales_number         int(6) comment '销售人员数量',
   `year_month`         int(6) not null comment '年月',
   primary key (organization_id, product_id, `year_month`)
);

alter table sales_org_prod_month comment '机构产品月销售统计(sales_org_prod_month)';

/*==============================================================*/
/* Table: sales_org_target                                      */
/*==============================================================*/
create table sales_org_target
(
   organization_id      varchar(32) not null comment '机构ID',
   customer_id          varchar(32) comment '客户ID',
   sales_target         double(10,4) comment '目标销售额(单位：万元)',
   sales_number         int(10) comment '目标销售量',
   sales_profit         double(10,4) comment '目标销售利润',
   return_amount        double(10,4) comment '回款额(单位：万元)',
   payment              double(10,4) comment '已回款(单位：万元)',
   `year_month`         int(6) not null comment '年月',
   primary key (organization_id, `year_month`)
);

alter table sales_org_target comment '机构考核(sales_org_target)';

/*==============================================================*/
/* Table: sales_pro_target                                      */
/*==============================================================*/
create table sales_pro_target
(
   product_id           varchar(32) not null comment '产品ID',
   customer_id          varchar(32) comment '客户ID',
   sales_target         double(14,4) comment '目标销售额(单位：万元',
   return_amount        double(14,4) comment '回款额(单位：万元)',
   payment              double(10,4) comment '已回款(单位：万元)',
   `year_month`         int(6) not null comment '年月',
   primary key (product_id, `year_month`)
);

alter table sales_pro_target comment '产品考核(sales_pro_target)';

/*==============================================================*/
/* Table: sales_team                                            */
/*==============================================================*/
create table sales_team
(
   sales_team_id        varchar(32) not null comment '自定义团队',
   emp_id               varchar(32) comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   team_name            varchar(50) comment '团队名称',
   requirement          varchar(4000) comment '要求',
   pk_view              int(1) comment '是否团队PK查看',
   primary key (sales_team_id)
);

alter table sales_team comment '自定义团队（sales_team）';

/*==============================================================*/
/* Table: sales_team_rank                                       */
/*==============================================================*/
create table sales_team_rank
(
   team_id              varchar(32) not null comment '团队ID',
   organization_id      varchar(32) comment '机构ID',
   customer_id          varchar(32) comment '客户ID',
   team_rank            int(6) comment '排名',
   rank_date            date not null comment '时间',
   `year_month`         int(6) comment '年月',
   primary key (team_id, rank_date)
);

alter table sales_team_rank comment '团队销售排名(sales_team_rank)';

/*==============================================================*/
/* Table: sales_team_target                                     */
/*==============================================================*/
create table sales_team_target
(
   team_id              varchar(32) not null comment '团队ID',
   customer_id          varchar(32) comment '客户ID',
   sales_target         int(10) comment '目标销售额',
   payment              int(10) comment '已回款',
   return_amount        int(10) comment '回款额',
   `year_month`         int(6) not null comment '年月',
   primary key (team_id, `year_month`)
);

alter table sales_team_target comment '团队考核(sales_team_target)';

/*==============================================================*/
/* Table: satfac_genre_soure                                    */
/*==============================================================*/
create table satfac_genre_soure
(
   satfac_soure_id      varchar(32) not null comment '满意度评分ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   satfac_genre_id      varchar(32) comment '满意度分类ID',
   soure                double(5,4) comment '得分',
   date                 date comment '报告日期',
   primary key (satfac_soure_id)
);
alter table satfac_genre_soure comment '历史表-满意度评分（satfac_genre_soure）';

/*==============================================================*/
/* Table: satfac_organ                                          */
/*==============================================================*/
create table satfac_organ
(
   satfac_organ_id      varchar(32) not null comment '满意机构评分ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   organization_name    varchar(20) comment '机构名称',
   soure                double(4,4) comment '得分',
   date                 date comment '报告日期',
   primary key (satfac_organ_id)
);
alter table satfac_organ comment '历史表-满意度机构评分（satfac_organ）';

/*==============================================================*/
/* Table: share_holding                                         */
/*==============================================================*/
create table share_holding
(
   share_holding_id     varchar(32) not null comment '持股明细ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   usre_name_ch         varchar(5) comment '中文名',
   organization_id      varchar(32) comment '机构ID',
   full_path            varchar(200) comment '机构全路径',
   now_share            int(4) comment '当前数量（单位：股）',
   confer_share         int(4) comment '授予数量（单位：股）',
   price                double(10,4) comment '授予价（单位：元/股）',
   hold_period          varchar(5) comment '持有期',
   sub_num              int(4) comment '最近减持数量',
   sub_date             datetime comment '最近减持时间',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (share_holding_id)
);

alter table share_holding comment '业务表-持股明细（share_holding）';

/*==============================================================*/
/* Table: `mup-source`.source_code_item                                       */
/*==============================================================*/
create table `mup-source`.source_code_item
(
   code_item_id         varchar(32) not null comment '代码项',
   customer_id          varchar(32) comment '客户ID',
   code_item_key        varchar(20) comment '编码',
   code_item_name       varchar(60) comment '名称',
   code_group_id        varchar(32) comment '代码组ID',
   curt_name            tinyint(1) comment '简称',
   type                 tinyint(1) comment '类别：默认0',
   show_index           tinyint(1) comment '排序列',
   primary key (code_item_id)
);

alter table `mup-source`.source_code_item comment '字典表-代码项（soure_code_item）';

/*==============================================================*/
/* Table: sys_customize                                         */
/*==============================================================*/
create table sys_customize
(
   id                   varchar(32) not null comment '客户ID',
   customize_id         varchar(32) comment '客户编码',
   code                 varchar(255) comment '客户名称',
   plug_in              varchar(255) comment '描述',
   primary key (id)
);

alter table sys_customize comment '维度表-客户维（sys_customize）';

/*==============================================================*/
/* Table: sys_module                                            */
/*==============================================================*/
create table sys_module
(
   id                   varchar(32) not null comment '模块ID',
   customer_id          varchar(32) comment '客户ID',
   code                 varchar(32) comment '客户编码',
   name                 varchar(255) comment '客户名称',
   staus                varchar(255) comment '描述',
   version              varchar(13),
   primary key (id)
);

alter table sys_module comment '系统表-系统模块（sys_module）';

/*==============================================================*/
/* Table: talent_development_exam                               */
/*==============================================================*/
create table talent_development_exam
(
   talent_development_exam_id varchar(32) not null comment '人才发展ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_key              varchar(20) comment '员工编号',
   organization_id      varchar(32) comment '机构ID',
   position_name
   position_name varchar(60) comment '岗位名称',
   sequence_name        varchar(60) comment '主序列名称',
   sequence_sub_name    varchar(60) comment '子序列名称',
   ability_match        double(4,4) comment '能力匹配度',
   exam_date            datetime comment '测评时间',
   refresh              datetime comment '更新时间',
   year                 int(10) comment '年',
   primary key (talent_development_exam_id)
);

alter table talent_development_exam comment '业务表-人才发展（talent_development_exam）';

/*==============================================================*/
/* Table: talent_development_promote                            */
/*==============================================================*/
create table talent_development_promote
(
   talent_development_promte_id varchar(32) not null comment '人才发展ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_key              varchar(20) comment '员工编号',
   organization_id      varchar(32) comment '机构ID',
   position_name
   position_name varchar(60) comment '岗位名称',
   sequence_name        varchar(60) comment '主序列名称',
   sequence_sub_name    varchar(60) comment '子序列名称',
   ability_name         varchar(60) comment '职位层级名称',
   entry_rank_name      varchar(5) comment '入职职级（简称）',
   entry_date           datetime comment '入职日期',
   rank_name            varchar(5) comment '当前职级（简称）',
   promote_date         datetime comment '晋升日期',
   stay_time            double(5,2) comment '原职级所待时长（年）',
   pomote_num           int comment '晋升次数',
   refresh              datetime comment '更新时间',
   primary key (talent_development_promte_id)
);

alter table talent_development_promote comment '业务表-人才发展（talent_development_promote）';

/*==============================================================*/
/* Table: talent_development_train                              */
/*==============================================================*/
create table talent_development_train
(
   talent_development_train_id varchar(32) not null comment '人才发展ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_key              varchar(20) comment '员工编号',
   organization_id      varchar(32) comment '机构ID',
   position_name
   position_name varchar(60) comment '岗位名称',
   sequence_name        varchar(60) comment '主序列名称',
   sequence_sub_name    varchar(60) comment '子序列名称',
   train_time           double(5,2) comment '总培训学时',
   train_num            int(5) comment '总培训次数',
   refresh              datetime comment '更新时间',
   year_quarter         varchar(6) comment '季度',
   primary key (talent_development_train_id)
);

alter table talent_development_train comment '业务表-人才发展（talent_development_train）';

/*==============================================================*/
/* Table: target_benefit_value                                  */
/*==============================================================*/
create table target_benefit_value
(
   target_benefit_value_id varchar(32) not null comment '目标人均效益ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   target_value         double(6,2) comment '目标人均效益值',
   year                 int(4) comment '年',
   primary key (target_benefit_value_id)
);

alter table target_benefit_value comment '业务表-目标人均效益（年）（target_benefit_value）';

/*==============================================================*/
/* Table: theory_attendance                                     */
/*==============================================================*/
create table theory_attendance
(
   theory_attendance_id varchar(32) not null comment '应出勤记录ID',
   days                 date comment '日期',
   hour_count           double(4,2) comment '出勤小时',
   customer_id          varchar(32) comment '客户ID',
   year_months          int(6) comment '年月',
   year                 int(4) comment '年',
   primary key (theory_attendance_id)
);

alter table theory_attendance comment '应出勤记录（theory_attendance）';

/*==============================================================*/
/* Index: ind_days                                              */
/*==============================================================*/
create index ind_days on theory_attendance
(
   days
);

/*==============================================================*/
/* Index: ind_ym                                                */
/*==============================================================*/
create index ind_ym on theory_attendance
(
   year_months
);

/*==============================================================*/
/* Table: trade_profit                                          */
/*==============================================================*/
create table trade_profit
(
   trade_profit_id      varchar(32) not null comment '营业利润ID',
   customer_id          varchar(32) comment '客户ID',
   business_unit_id     varchar(32) comment '业务单位ID',
   organization_id      varchar(32) comment '机构ID',
   sales_amount         decimal(10,4) comment '营业收入、销售总额（单位：万元）',
   expend_amount        double(7,2) comment '营业支出、企业总成本（单位：万元）',
   gain_amount          decimal(10,4) comment '营业利润（单位：万元）',
   target_value         double(10,4) comment '目标人均效益值（单位：万元）',
   `year_month`         int(6) comment '年月',
   primary key (trade_profit_id)
);

alter table trade_profit comment '业务表-营业利润（trade_profit）';

/*==============================================================*/
/* Table: train_outlay                                          */
/*==============================================================*/
create table train_outlay
(
   train_outlay_id      varchar(32) not null comment '培训花费用ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   outlay               double(6,2) comment '花费（万元）汇',
   date                 datetime comment '时间',
   note                 varchar(200) comment '备注',
   year                 int(4) comment '年',
   primary key (train_outlay_id)
);

alter table train_outlay comment '业务表-培训实际花费用（train_outlay）';

/*==============================================================*/
/* Table: train_plan                                            */
/*==============================================================*/
create table train_plan
(
   train_plan_id        varchar(32) not null comment '培训计划ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   train_name           varchar(20) comment '计划名称',
   train_num            int(3) comment '计划培训人数',
   date                 datetime comment '制定发布时间',
   start_date           datetime comment '计划实施开始时间',
   end_date             datetime comment '计划实施结束时间',
   year                 int(4) comment '计划实施年',
   status               tinyint(1) comment '状态（0进行中，1已完成）',
   primary key (train_plan_id)
);

alter table train_plan comment '业务表-培训计划(train_plan)';

/*==============================================================*/
/* Table: train_satfac                                          */
/*==============================================================*/
create table train_satfac
(
   train_satfac_id      varchar(32) not null comment '培训满意度ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   soure                double(5,4) comment '得分',
   year                 int(4) comment '年',
   primary key (train_satfac_id)
);

alter table train_satfac comment '业务表-培训满意度（train_satfac）';

/*==============================================================*/
/* Table: train_value                                           */
/*==============================================================*/
create table train_value
(
   train_value_id       varchar(32) not null comment '培训年度预算费用ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   budget_value         double(6,2) comment '预算度本（万元）',
   year                 int(4) comment '年',
   primary key (train_value_id)
);

alter table train_value comment '业务表-培训年度预算费用（年）（train_value）';

/*==============================================================*/
/* Table: underling                                             */
/*==============================================================*/
create table underling
(
   emp_relation_id      varchar(32) not null comment '我的下属员工ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_key              varchar(20) comment '员工编码',
   underling_emp_id     varchar(32) comment '下属员工ID',
   underling_emp_key    varchar(20) comment '下属员工编码',
   primary key (emp_relation_id)
);
alter table underling comment '业务表-我的下属员工（underling）';

/*==============================================================*/
/* Table: user_organization_relation                            */
/*==============================================================*/
create table user_organization_relation
(
   user_organization_id varchar(32) not null comment '用户机构',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   user_id              varchar(32) comment '用户ID',
   half_check           int(1) comment '半勾状态',
   create_user_id       varchar(32) not null comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime not null comment '创建时间',
   modify_time          datetime comment '修改时间',
   primary key (user_organization_id)
);

alter table user_organization_relation comment '关系表-员工机构关系（user_organization_relation）';

/*==============================================================*/
/* Table: user_role_relation                                    */
/*==============================================================*/
create table user_role_relation
(
   user_role_id         varchar(32) not null comment '用户角色ID',
   role_id              varchar(32) comment '角色ID',
   user_id              varchar(32) comment '用户ID',
   customer_id          varchar(32) comment '客户ID',
   create_user_id       varchar(32) not null comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime not null comment '创建时间',
   modify_time          datetime comment '修改时间',
   primary key (user_role_id)
);

alter table user_role_relation comment '关系表-用户角色关系（user_role_relation）';

/*==============================================================*/
/* Table: v_dim_emp                                             */
/*==============================================================*/
create table v_dim_emp
(
   v_dim_emp_id         varchar(32) comment '员工维度ID',
   emp_id               varchar(32) not null comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   emp_key              varchar(20) comment '员工编码',
   user_name            varchar(20) comment '用户名称',
   user_name_ch         varchar(5) comment '中文名',
   emp_hf_id            varchar(32) comment '上级ID',
   emp_hf_key           varchar(20) comment '上级编码',
   email                varchar(100) comment '邮箱',
   img_path             varchar(32) comment '头像',
   passtime_or_fulltime varchar(1) comment '工作性质(p、f、s)',
   blood                varchar(2) comment '血型',
   age                  int(3) comment '年龄（生日日期计算）',
   sex                  varchar(1) comment '性别',
   nation               varchar(9) comment '民族',
   degree_id            varchar(32) comment '学历ID',
   degree               varchar(20) comment '学历',
   native_place         varchar(90) comment '籍贯',
   country              varchar(20) comment '国籍',
   birth_place          varchar(90) comment '出生地',
   birth_date           datetime comment '出生日期',
   national_id          varchar(60) comment '证件号码',
   national_type        varchar(60) comment '证件类型',
   marry_status         int(1) comment '婚姻状况',
   patty_name           varchar(20) comment '政治面貌',
   company_age          int(3) comment '司龄（入职日期计算）',
   is_key_talent        int(1) comment '是否关键人才',
   organization_parent_id varchar(32) comment '单位ID（dept为：2）',
   organization_parent_name varchar(60) comment '单位名称',
   organization_id      varchar(32) comment '部门ID',
   organization_name    varchar(60) comment '部门名称',
   position_id          varchar(32) comment '岗位ID',
   position_name        varchar(60) comment '岗位名称',
   sequence_id          varchar(32) comment '主序列ID',
   sequence_name        varchar(60) comment '主序列名称',
   sequence_sub_id      varchar(32) comment '子序列ID',
   sequence_sub_name    varchar(60) comment '子序列名称',
   ability_id           varchar(32) comment '职位层级ID',
   ability_name         varchar(60) comment '职位层级名称',
   job_title_id         varchar(32) comment '职衔ID',
   job_title_name       varchar(20) comment '职衔名称',
   ability_lv_id        varchar(32) comment '职级ID',
   ability_lv_name      varchar(60) comment '职级名称',
   rank_name            varchar(5) comment '主子序列层级职级（短名）',
   performance_id       varchar(32) comment '绩效ID',
   performance_name     varchar(20) comment '绩效名称',
   population_id        varchar(32) comment '人群ID',
   population_name      varchar(20) comment '人群名称',
   tel_phone            varchar(11) comment '手机',
   qq                   varchar(20) comment 'qq号码',
   wx_code              varchar(32) comment '微信号码',
   address              varchar(60) comment '住址',
   contract_unit        varchar(32) comment '合同单位',
   work_place_id        varchar(32) comment '工作地点ID',
   work_place           varchar(60) comment '工作地点',
   regular_date         datetime comment '转正日期',
   channel_id           varchar(32) comment '应聘渠道ID',
   speciality           varchar(32) comment '特长',
   is_regular           int(1) comment '是否正职（1 主岗位  0 负岗位）',
   area_id              varchar(32) comment '地区',
   entry_date           datetime comment '入职日期',
   run_off_date         datetime not null comment '流失日期',
   refresh              datetime comment '更新日期',
   mark                 tinyint(1) comment '标记',
   primary key (emp_id)
);

alter table v_dim_emp comment '维度表-员工信息维(v_dim_emp)';

/*==============================================================*/
/* Index: index_ed                                              */
/*==============================================================*/
create index index_ed on v_dim_emp
(
   entry_date
);

/*==============================================================*/
/* Index: index_eId                                             */
/*==============================================================*/
create index index_eId on v_dim_emp
(
   emp_id
);

/*==============================================================*/
/* Index: index_eKey                                            */
/*==============================================================*/
create index index_eKey on v_dim_emp
(
   emp_key
);

/*==============================================================*/
/* Index: indx_eORG                                             */
/*==============================================================*/
create index indx_eORG on v_dim_emp
(
   emp_id,
   organization_id
);

/*==============================================================*/
/* Index: indx_pos                                              */
/*==============================================================*/
create index indx_pos on v_dim_emp
(
   position_id
);

/*==============================================================*/
/* Index: ind_eId_unc_rk_VDE                                    */
/*==============================================================*/
create index ind_eId_unc_rk_VDE on v_dim_emp
(
   emp_id,
   rank_name,
   user_name
);

/*==============================================================*/
/* Index: ind_mark                                              */
/*==============================================================*/
create index ind_mark on v_dim_emp
(
   mark
);

/*==============================================================*/
/* Index: ind_Org                                               */
/*==============================================================*/
create index ind_Org on v_dim_emp
(
   organization_id
);

/*==============================================================*/
/* Index: ind_perId_v_dim_emp                                   */
/*==============================================================*/
create index ind_perId_v_dim_emp on v_dim_emp
(
   performance_id
);

/*==============================================================*/
/* Index: ind_rn_v_dim_emp                                      */
/*==============================================================*/
create index ind_rn_v_dim_emp on v_dim_emp
(
   rank_name
);

/*==============================================================*/
/* Index: ind_Uch                                               */
/*==============================================================*/
create index ind_Uch on v_dim_emp
(
   user_name
);

/*==============================================================*/
/* Table: welfare_cpm                                           */
/*==============================================================*/
create table welfare_cpm
(
   welfare_cpm_id       varchar(32) not null comment '企业福利明细ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   user_name_ch         varchar(50) comment '员工中文名',
   cpm_id               varchar(32) comment '企业福利ID',
   welfare_value        double(10,4) comment '发放金额（单位：元）',
   date                 tinyint(1) comment '发放时间',
   `year_month`         int(6) not null comment '年月',
   refresh              datetime comment '更新时间',
   primary key (welfare_cpm_id, `year_month`)
);

alter table welfare_cpm comment '业务表-企业福利明细（货币）（welfare_cpm）';

/*==============================================================*/
/* Index: index_eId                                             */
/*==============================================================*/
create index index_eId on welfare_cpm
(
   emp_id
);

/*==============================================================*/
/* Index: index_ym_eId                                          */
/*==============================================================*/
create index index_ym_eId on welfare_cpm
(
   emp_id,
   `year_month`
);

/*==============================================================*/
/* Index: ind_cId                                               */
/*==============================================================*/
create index ind_cId on welfare_cpm
(
   cpm_id,
   `year_month`
);

/*==============================================================*/
/* Index: ind_eId_ym_welfare_cpm                                */
/*==============================================================*/
create index ind_eId_ym_welfare_cpm on welfare_cpm
(
   emp_id,
   `year_month`
);

/*==============================================================*/
/* Index: ind_ym                                                */
/*==============================================================*/
create index ind_ym on welfare_cpm
(
   `year_month`
);

/*==============================================================*/
/* Table: welfare_cpm_total                                     */
/*==============================================================*/
create table welfare_cpm_total
(
   organization_id      varchar(32) not null comment '机构ID',
   customer_id          varchar(32) comment '客户ID',
   cpm_id               varchar(32) not null comment '企业福利ID',
   cpm_total            double(16,4) comment '发放总额（单位：元）',
   enjoin_cpm_total     int(6) comment '享受福利子孙人数（分子）',
   month_total          int(6) comment '本月子孙总人数（分母）',
   year                 int(4) comment '年',
   primary key (organization_id, cpm_id)
);

alter table welfare_cpm_total comment '企业福利月统计(welfare_cpm_total)末月清空';

/*==============================================================*/
/* Table: welfare_nfb                                           */
/*==============================================================*/
create table welfare_nfb
(
   welfare_id           varchar(32) not null comment '福利ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   user_name_ch          varchar(50) comment '员工中文名',
   nfb_id               varchar(32) comment '国家固定福利ID',
   welfare_value        double(10,4) comment '缴费金额（单位：元）',
   date                 tinyint(1) comment '缴费时间',
   `year_month`         int(6) comment '年月',
   refresh              datetime comment '更新时间',
   primary key (welfare_id)
);

alter table welfare_nfb comment '业务表-固定福利明细（welfare_nfb）';

/*==============================================================*/
/* Index: ind_eId                                               */
/*==============================================================*/
create index ind_eId on welfare_nfb
(
   emp_id
);

/*==============================================================*/
/* Index: ind_ym                                                */
/*==============================================================*/
create index ind_ym on welfare_nfb
(
   `year_month`
);

/*==============================================================*/
/* Index: ind_eId_ym                                            */
/*==============================================================*/
create index ind_eId_ym on welfare_nfb
(
   emp_id,
   `year_month`
);

/*==============================================================*/
/* Index: ind_ym_eId                                            */
/*==============================================================*/
create index ind_ym_eId on welfare_nfb
(
   `year_month`,
   emp_id
);

/*==============================================================*/
/* Index: ind_ym_unc_welfare_nfb                                */
/*==============================================================*/
create index ind_ym_unc_welfare_nfb on welfare_nfb
(
   `year_month`,
   user_name_ch
);

/*==============================================================*/
/* Table: welfare_nfb_total                                     */
/*==============================================================*/
create table welfare_nfb_total
(
   organization_id      varchar(32) not null comment '机构ID',
   customer_id          varchar(32) comment '客户ID',
   nfb_id               varchar(32) not null comment '固定福利ID',
   nfb_total            double(14,4) comment '福利总额（单位：元）',
   enjoin_nfb_total     int(6) comment '享受福利子孙人数（分子）',
   month_total          int(6) comment '本月子孙总人数（分母）',
   year                 int(4) comment '年',
   primary key (organization_id, nfb_id)
);

alter table welfare_nfb_total comment '固定福利月统计(welfare_nfb_total)末月清空';

/*==============================================================*/
/* Table: welfare_uncpm                                         */
/*==============================================================*/
create table welfare_uncpm
(
   welfare_uncpm_id     varchar(32) not null comment '企业福利明细ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   uncpm_id             varchar(32) comment '福利类别ID',
   note                 varchar(50) comment '备注',
   date                 tinyint(1) comment '发放时间',
   `year_month`         int(6) comment '年月',
   refresh              datetime comment '更新时间',
   primary key (welfare_uncpm_id)
);

alter table welfare_uncpm comment '业务表-企业福利明细（非货币）（welfare_uncpm）';

/*==============================================================*/
/* Index: ind_eId                                               */
/*==============================================================*/
create index ind_eId on welfare_uncpm
(
   emp_id
);

/*==============================================================*/
/* Index: ind_ym                                                */
/*==============================================================*/
create index ind_ym on welfare_uncpm
(
   `year_month`
);

/*==============================================================*/
/* Index: ind_Uid                                               */
/*==============================================================*/
create index ind_Uid on welfare_uncpm
(
   uncpm_id
);

/*==============================================================*/
/* Index: ind_ym_eId                                            */
/*==============================================================*/
create index ind_ym_eId on welfare_uncpm
(
   `year_month`,
   emp_id
);

CREATE TABLE `history_dim_organization` (
	`history_dim_organization_id` varchar(32) NOT NULL,
	`organization_id` varchar(32) NULL DEFAULT NULL COMMENT '机构ID',
	`customer_id` varchar(32) NULL DEFAULT NULL COMMENT '客户ID',
	`business_unit_id` varchar(32) NULL DEFAULT NULL COMMENT '业务单位ID',
	`organization_company_id` varchar(32) NULL DEFAULT NULL COMMENT '分公司',
	`organization_type_id` varchar(32) NULL DEFAULT NULL COMMENT '机构级别ID',
	`organization_key` VARCHAR(20) NULL DEFAULT NULL COMMENT '机构编码',
	`organization_parent_key` VARCHAR(20) NULL DEFAULT NULL,
	`organization_parent_id` CHAR(32) NULL DEFAULT NULL COMMENT '上级机构',
	`organization_name` VARCHAR(20) NULL DEFAULT NULL COMMENT '机构名称',
	`organization_name_full` VARCHAR(50) NULL DEFAULT NULL,
	`note` VARCHAR(100) NULL DEFAULT NULL COMMENT '描述',
	`is_single` TINYINT(1) NULL DEFAULT '0' COMMENT '是否独立核算',
	`full_path` TEXT NULL DEFAULT NULL COMMENT '业务单位ID',
	`has_children` TINYINT(1) NULL DEFAULT NULL,
	`depth` INT(3) NULL DEFAULT NULL,
	`refresh_date` DATETIME NULL DEFAULT NULL,
	`profession_id` CHAR(32) NULL DEFAULT NULL,
	`days` DATE NULL DEFAULT NULL,
	PRIMARY KEY (`history_dim_organization_id`),
	INDEX `index_fp` (`full_path`(255)) USING BTREE,
	INDEX `index_day_orgId` (`days`, `organization_id`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=MyISAM
;


drop table if exists dept_kpi;

/*==============================================================*/
/* Table: dept_kpi                                              */
/*==============================================================*/
create table dept_kpi
(
   dept_per_exam_relation_id varchar(32) not null comment '部门绩效目标ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   kpi_value            double(6,4) comment 'KPI达标率（范围：0-1）',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (dept_per_exam_relation_id)
);
alter table dept_kpi comment '业务表-部门绩效达标率（dept_kpi）';


drop table if exists profession_value;
/*==============================================================*/
/* Table: profession_value                                      */
/*==============================================================*/
create table profession_value
(
   profession_value     varchar(32) not null comment '行业指标值ID',
   profession_name      varchar(50) comment '行业指标值名称',
   profession_value_key varchar(20) comment '行业指标值编码',
   value                double(6,2) comment '行业指标值',
   profession_id        varchar(32) comment '行业ID',
   refresh              datetime comment '更新日期',
   primary key (profession_value)
);
alter table profession_value comment '业务表-行业指标值（profession_value）';


-- ********************************`mup-source`库****************************************
-- 招聘看板
-- ========================================================
create table `mup-source`.source_recruit_value
(
   recruit_value_id     varchar(32) not null comment '招聘年度费用',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   budget_value         double(12,6) comment '预算度本（单位：万）',
   outlay               double(12,6) comment '已花成本（单位：万、过）',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (recruit_value_id)
);

alter table `mup-source`.source_recruit_value comment '业务表-招聘年度费用（年）（recruit_value）';

create table `mup-source`.source_out_talent
(
   out_talent_id        varchar(32) not null comment '外部人才ID',
   customer_id          varchar(32) comment '客户ID',
   user_name_ch         varchar(5) comment '中文名',
   user_name            varchar(20) comment '英文名',
   email                text comment '邮箱',
   age                  int(3) comment '年龄',
   sex                  char(3) comment '姓别',
   degree_id            varchar(32) comment '学历ID',
   url                  text comment '简历url',
   school               varchar(30) comment '毕业院校',
   refresh              datetime comment '更新时间',
   img_path             varchar(500) comment '图片路径',
   tag                  varchar(100) comment '标签',
   c_id                 varchar(32) comment '标识ID',
   primary key (out_talent_id)
);

alter table `mup-source`.source_out_talent comment '关系表-外部人才库（out_talent）';

create table `mup-source`.source_recruit_public
(
   recruit_public_id    varchar(32) not null comment '招聘发布ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '发布机构ID',
   position_id          varchar(32) comment '招聘岗位ID',
   employ_num           int(4) comment '已招人数（过）',
   plan_num             int(4) comment '计划人数',
   start_date           datetime comment '招聘开始时间',
   end_date             datetime comment '招聘结束时间',
   days                 int(4) comment '天数',
   resume_num           int(4) comment '简历数（过）',
   interview_num        int(4) comment '面试数（过）',
   offer_num            int(4) comment '通过数（过）',
   entry_num            int(4) comment '入职数（过）',
   is_public            tinyint(1) comment '招聘岗位状态',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (recruit_public_id)
);

alter table `mup-source`.source_recruit_public comment '业务表-招聘发布（`mup-source`.source_recruit_public）';

create table `mup-source`.source_recruit_result
(
   recruit_result_id    varchar(32) not null comment '应聘结果ID',
   customer_id          varchar(32) comment '客户ID',
   recruit_result_key   varchar(20) comment '应聘者编号',
   username             varchar(6) comment '姓名',
   sex                  char(3) comment '性别',
   age                  double(5,2) comment '年龄',
   degree_id            varchar(32) comment '学历ID',
   major                varchar(30) comment '专业',
   school               varchar(30) comment '毕业院校',
   is_resume            tinyint(1) comment '是否投简',
   is_interview         tinyint(1) comment '是否面试',
   is_offer             tinyint(1) comment '是否通过',
   is_entry             tinyint(1) comment '是否入职',
   url                  text comment '简历url',
   recruit_public_id    varchar(32) comment '招聘发布ID',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (recruit_result_id)
);

alter table `mup-source`.source_recruit_result comment '业务表-招聘结果（`mup-source`.source_recruit_result）';
create unique index un_cid on `mup-source`.source_recruit_result
(
   c_id
);

create table `mup-source`.source_recruit_channel
(
   recruit_channel_id   varchar(32) not null comment '应聘渠道ID',
   customer_id          varchar(32) comment '客户ID',
   channel_id           varchar(32) comment '渠道ID',
   position_id          varchar(32) comment '职位ID',
   employ_num           int(4) comment '已招人数',
   outlay               double(10,2) comment '花费（单位：元）',
   start_date           datetime comment '招聘开始时间',
   end_date             datetime comment '招聘结束时间',
   days                 int(4) comment '天数',
   recruit_public_id    varchar(32) comment '招聘发布ID',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (recruit_channel_id)
);

alter table `mup-source`.source_recruit_channel comment '业务表-招聘渠道（recruit_channel）';
create unique index un_cid on `mup-source`.source_recruit_channel
(
   c_id
);
-- 销售看板
-- ===============================================================
CREATE TABLE `mup-source`.`source_sales_emp_target` (
  `emp_id` varchar(32) DEFAULT NULL,
  `sales_target` double(10,4) DEFAULT NULL,
  `return_amount` double(10,4) DEFAULT NULL,
  `payment` double(10,4) DEFAULT NULL,
  `year_month` int(6) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

create table `mup-source`.source_sales_detail
(
   sales_detail_id      varchar(32) not NULL comment '销售明细ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   product_id           varchar(32) comment '商品ID',
   product_number       int(5) comment '商品数量',
   sales_money          double(10,2) comment '实际销售金额（单位：元）',
   sales_profit         double(10,2) comment '销售利润（单位：元）',
   sales_province_id    varchar(32) comment '销售地点(省)',
   sales_city_id        varchar(32) comment '销售地点(市)',
   sales_date           date comment '销售时间',
   primary key (sales_detail_id)
);

alter table `mup-source`.source_sales_detail comment '销售明细(`mup-source`.source_sales_detail)';

create table `mup-source`.source_sales_pro_target
(
   product_id           varchar(32) not null comment '产品ID',
   customer_id          varchar(32) comment '客户ID',
   sales_target         double(14,4) comment '目标销售额(单位：万元',
   return_amount        double(14,4) comment '回款额(单位：万元)',
   payment              double(10,4) comment '已回款(单位：万元)',
   `year_month`         int(6) not null comment '年月',
   primary key (product_id, `year_month`)
);

alter table `mup-source`.source_sales_pro_target comment '产品考核(`mup-source`.source_sales_pro_target)';

create table `mup-source`.source_sales_org_target
(
   organization_id      varchar(32) not null comment '机构ID',
   customer_id          varchar(32) comment '客户ID',
   sales_target         double(10,4) comment '目标销售额(单位：万元)',
   sales_number         int(10) comment '目标销售量',
   sales_profit         double(10,4) comment '目标销售利润',
   return_amount        double(10,4) comment '回款额(单位：万元)',
   payment              double(10,4) comment '已回款(单位：万元)',
   `year_month`         int(6) not null comment '年月',
   primary key (organization_id, `year_month`)
);

alter table `mup-source`.source_sales_org_target comment '机构考核(`mup-source`.source_sales_org_target)';

create table `mup-source`.source_sales_ability
(
   emp_id               varchar(32) not null comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   status               int(1) comment '考核状态',
   check_date           date comment '考核时间',
   primary key (emp_id)
);

alter table sales_ability comment '业务能力考核(`mup-source`.source_sales_ability)';

create table `mup-source`.source_sales_team_rank
(
   team_id              varchar(32) not null comment '团队ID',
   customer_id          varchar(32) comment '客户ID',
   team_rank            int(6) comment '排名',
   rank_date            date not null comment '时间',
   primary key (team_id, rank_date)
);

alter table `mup-source`.source_sales_team_rank comment '团队销售排名(`mup-source`.source_sales_team_rank)';

create table `mup-source`.source_sales_team_target
(
   team_id              varchar(32) not null comment '团队ID',
   customer_id          varchar(32) comment '客户ID',
   sales_target         int(10) comment '目标销售额',
   payment              int(10) comment '已回款',
   return_amount        int(10) comment '回款额',
   `year_month`         int(6) not null comment '年月',
   primary key (team_id, `year_month`)
);

alter table `mup-source`.source_sales_team_target comment '团队考核(`mup-source`.source_sales_team_target)';
/*==============================================================*/
/* Table: `mup-source`.source_sales_emp_rank                                        */
/*==============================================================*/
drop table if exists `mup-source`.source_sales_emp_rank;
create table `mup-source`.source_sales_emp_rank
(
   emp_id               varchar(32) not null comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   emp_rank             int(6) comment '排名',
   organization_id      varchar(32) not null comment '所排名机构',
   rank_date            date not null comment '时间',
   `year_month`         int(6) comment '年月',
   proportion_id        varchar(32) comment '占比ID',
   primary key (emp_id, organization_id, rank_date)
);

alter table `mup-source`.source_sales_emp_rank comment '员工销售排名(`mup-source`.source_sales_emp_rank)';

-- 员工表
-- ================================
create table `mup-source`.source_v_dim_emp
(
   v_dim_emp_id         varchar(32) comment '员工维度ID',
   emp_id               varchar(32) not null comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   emp_key              varchar(20) comment '员工编码',
   user_name            varchar(20) comment '用户名称',
   user_name_ch         varchar(5) comment '中文名',
   emp_hf_id            varchar(32) comment '上级ID',
   emp_hf_key           varchar(20) comment '上级编码',
   email                varchar(100) comment '邮箱',
   img_path             varchar(32) comment '头像',
   passtime_or_fulltime varchar(1) comment '工作性质(p、f、s)',
   blood                varchar(2) comment '血型',
   age                  int(3) comment '年龄（生日日期计算）',
   sex                  varchar(1) comment '性别',
   nation               varchar(9) comment '民族',
   degree_id            varchar(32) comment '学历ID',
   degree               varchar(20) comment '学历',
   native_place         varchar(90) comment '籍贯',
   country              varchar(20) comment '国籍',
   birth_place          varchar(90) comment '出生地',
   birth_date           datetime comment '出生日期',
   national_id          varchar(60) comment '证件号码',
   national_type        varchar(60) comment '证件类型',
   marry_status         int(1) comment '婚姻状况',
   patty_name           varchar(20) comment '政治面貌',
   company_age          int(3) comment '司龄（入职日期计算）',
   is_key_talent        int(1) comment '是否关键人才',
   organization_parent_id varchar(32) comment '单位ID（dept为：2）',
   organization_parent_name varchar(60) comment '单位名称',
   organization_id      varchar(32) comment '部门ID',
   organization_name    varchar(60) comment '部门名称',
   position_id          varchar(32) comment '岗位ID',
   position_name varchar(60) comment '岗位名称',
   sequence_id          varchar(32) comment '主序列ID',
   sequence_name        varchar(60) comment '主序列名称',
   sequence_sub_id      varchar(32) comment '子序列ID',
   sequence_sub_name    varchar(60) comment '子序列名称',
   ability_id           varchar(32) comment '职位层级ID',
   ability_name         varchar(60) comment '职位层级名称',
   job_title_id         varchar(32) comment '职衔ID',
   job_title_name       varchar(20) comment '职衔名称',
   ability_lv_id        varchar(32) comment '职级ID',
   ability_lv_name      varchar(60) comment '职级名称',
   rank_name            varchar(5) comment '主子序列层级职级（短名）',
   performance_id       varchar(32) comment '绩效ID',
   performance_name     varchar(20) comment '绩效名称',
   population_id        varchar(32) comment '人群ID',
   population_name      varchar(20) comment '人群名称',
   tel_phone            varchar(11) comment '手机',
   qq                   varchar(20) comment 'qq号码',
   wx_code              varchar(32) comment '微信号码',
   address              varchar(60) comment '住址',
   contract_unit        varchar(32) comment '合同单位',
   work_place_id        varchar(32) comment '工作地点ID',
   work_place           varchar(60) comment '工作地点',
   regular_date         datetime comment '转正日期',
   channel_id           varchar(32) comment '应聘渠道ID',
   speciality           varchar(32) comment '特长',
   is_regular           int(1) comment '是否正职（1 主岗位  0 负岗位）',
   area_id              varchar(32) comment '地区',
   entry_date           datetime comment '入职日期',
   run_off_date         datetime not null comment '流失日期',
   refresh_date         datetime comment '更新日期',
   mark                 tinyint(1) comment '标记',
   primary key (emp_id)
);

alter table `mup-source`.source_v_dim_emp comment '维度表-员工信息维(`mup-source`.source_v_dim_emp)';

create index index_eId on `mup-source`.source_v_dim_emp
(
   emp_id
);


-- 人才损益
-- ==================================================================
create table `mup-source`.source_job_change
(
   emp_job_change_id    varchar(32) not null comment '变更ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_key              varchar(20) comment '员工编码',
   user_name_ch         varchar(20) comment '员工中文名',
   change_date          date comment '异动日期',
   change_type_id       varchar(32) comment '异动类型ID',
   change_type          int(3) comment '异动类型',
   organization_id      varchar(32) comment '部门ID（机构类别为3）',
   organization_name    varchar(60) comment '部门名称',
   position_id          varchar(32) comment '岗位ID',
   position_name        varchar(20) comment '岗位名称',
   sequence_id          varchar(32) comment '序列ID',
   sequence_name        varchar(20) comment '序列名称',
   sequence_sub_id      varchar(32) comment '子序列ID',
   sequence_sub_name    varchar(20) comment '子序列名称',
   ability_id           varchar(32) comment '层级ID',
   ability_name         varchar(20) comment '层级名称',
   ability_lv_id        varchar(32) comment '职级ID',
   ability_lv_name      varchar(20) comment '职级名称',
   job_title_id         varchar(32) comment '职衔ID',
   job_title_name       varchar(60)  comment '职衔名称',
   rank_name            varchar(5) comment '简称',
   rank_name_ed         varchar(5) comment '前简称',
   position_id_ed       varchar(32) comment '前岗位ID',
   position_name_ed     varchar(20) comment '前岗位名称',
   note                 varchar(5) comment '备注',
   refresh              datetime comment '更新时间',
   `year_month`         int(6) comment '年月',
   primary key (emp_job_change_id)
);

alter table `mup-source`.source_job_change comment '历史表-工作异动表（`mup-source`.source_job_change）';

create index index_eId on `mup-source`.source_job_change
(
   emp_id
);
-- 团队画像
-- ===========================================
create table `mup-source`.source_emp_personality
(
   emp_personality_id   varchar(32) not null comment '员工性格ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   personality_id       varchar(32) comment '性格ID',
   type                 int(1) comment '性格类型',
   refresh              datetime comment '更新时间',
   primary key (emp_personality_id)
);

alter table `mup-source`.source_emp_personality comment '业务表-员工性格（`mup-source`.source_emp_personality）';

-- 培训看板
-- ===================================================
create table `mup-source`.source_train_outlay
(
   train_outlay_id      varchar(32) not null comment '培训花费用ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   outlay               double(6,2) comment '花费（万元）汇',
   date                 datetime comment '时间',
   note                 varchar(200) comment '备注',
   year                 int(4) comment '年',
   primary key (train_outlay_id)
);

alter table `mup-source`.source_train_outlay comment '业务表-培训实际花费用（`mup-source`.source_train_outlay）';

create table `mup-source`.source_train_value
(
   train_value_id       varchar(32) not null comment '培训年度预算费用ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   budget_value         double(6,2) comment '预算度本（万元）',
   year                 int(4) comment '年',
   primary key (train_value_id)
);

alter table `mup-source`.source_train_value comment '业务表-培训年度预算费用（年）（`mup-source`.source_train_value）';

create table `mup-source`.source_lecturer_course_speak
(
   lecturer_course_speak_id varchar(32) not null comment '讲师授课ID',
   customer_id          varchar(32) comment '客户ID',
   lecturer_id           varchar(32) comment '讲师ID',
   course_id            varchar(32) comment '课程ID',
   start_date           datetime comment '授课开始时间',
   end_date             datetime comment '授课结束时间',
   year                 int(4) comment '年',
   primary key (lecturer_course_speak_id)
);

alter table `mup-source`.source_lecturer_course_speak comment '关系表-讲师授课（`mup-source`.source_lecturer_course_speak）';

create table `mup-source`.source_train_plan
(
   train_plan_id        varchar(32) not null comment '培训计划ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   train_name           varchar(20) comment '计划名称',
   train_num            int(3) comment '计划培训人数',
   date                 datetime comment '制定发布时间',
   start_date           datetime comment '计划实施开始时间',
   end_date             datetime comment '计划实施结束时间',
   year                 int(4) comment '计划实施年',
   status               tinyint(1) comment '状态（0进行中，1已完成）',
   primary key (train_plan_id)
);

alter table `mup-source`.source_train_plan comment '业务表-培训计划(`mup-source`.source_train_plan)';

create table `mup-source`.source_train_satfac
(
   train_satfac_id      varchar(32) not null comment '培训满意度ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   soure                double(5,4) comment '得分',
   year                 int(4) comment '年',
   primary key (train_satfac_id)
);

alter table `mup-source`.source_train_satfac comment '业务表-培训满意度（`mup-source`.source_train_satfac）';

create table `mup-source`.source_lecturer
(
   lecturer_id          varchar(32) not null comment '讲师ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID（内部讲师）',
   lecturer_name        varchar(20) comment '讲师名称',
   level_id             varchar(32) comment '讲师级别ID',
   type                 tinyint(1) comment '讲师类别（1外部，2内部）',
   primary key (lecturer_id)
);

alter table `mup-source`.source_lecturer comment '业务表-讲师（`mup-source`.source_lecturer）';

create table `mup-source`.source_course_record
(
   course_record_id     varchar(32) not null comment '课程安排记录ID',
   customer_id          varchar(32) comment '客户ID',
   course_id            varchar(32) comment '课程ID',
   start_date           datetime comment '培训时间',
   end_date             datetime comment '结束时间',
   status               tinyint(1) comment '状态（0进行中，1已完成）',
   train_unit           varchar(20) comment '主办方名称',
   train_plan_id        varchar(32) comment '培训计划ID',
   year                 int(4) comment '年',
   primary key (course_record_id)
);

alter table `mup-source`.source_course_record comment '业务表-课程安排记录(`mup-source`.source_course_record)';

create table `mup-source`.source_emp_train_experience
(
   emp_train_experience_id varchar(32) not null comment '培训经历ID',
   course_record_id     varchar(32) comment '课程进程记录ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   course_name          varchar(20) comment '课程名称/项目',
   start_date           datetime comment '开始日期',
   finish_date          datetime comment '完成日期',
   train_time           double(5,2) comment '学时',
   status               tinyint(1) comment '状态（1已完成，0进行中）',
   result               varchar(8) comment '成绩（A、B.../98.5、88.0）',
   train_unit           varchar(20) comment '培训机构',
   gain_certificate     varchar(20) comment '所获证书',
   lecturer_name        varchar(12) comment '讲师名称',
   note                 varchar(20) comment '备注',
   year                 int(4) comment '年',
   mark                 tinyint(1) comment '标记',
   primary key (emp_train_experience_id)
);

alter table `mup-source`.source_emp_train_experience comment '业务表-培训经历（`mup-source`.source_emp_train_experience）';

-- 人均效益
-- ======================
create table `mup-source`.source_target_benefit_value
(
   target_benefit_value_id varchar(32) not null comment '目标人均效益ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   target_value         double(6,2) comment '目标人均效益值',
   year                 int(4) comment '年',
   primary key (target_benefit_value_id)
);

alter table `mup-source`.source_target_benefit_value comment '业务表-目标人均效益（年）（`mup-source`.source_target_benefit_value）';

create table `mup-source`.source_trade_profit
(
   trade_profit_id      varchar(32) not null comment '营业利润ID',
   customer_id          varchar(32) comment '客户ID',
   business_unit_id     varchar(32) comment '业务单位ID',
   organization_id      varchar(32) comment '机构ID',
   sales_amount         decimal(10,4) comment '营业收入、销售总额（单位：万元）',
   expend_amount        double(7,2) comment '营业支出、企业总成本（单位：万元）',
   gain_amount          decimal(10,4) comment '营业利润（单位：万元）',
   target_value         double(10,4) comment '目标人均效益值（单位：万元）',
   `year_month`         int(6) comment '年月',
   primary key (trade_profit_id)
);

alter table `mup-source`.source_trade_profit comment '业务表-营业利润（`mup-source`.source_trade_profit）';

-- 主动流失率
-- ==========================
create table `mup-source`.source_run_off_record
(
   run_off_record_id    varchar(32) not null comment '流失记录ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   run_off_id           varchar(32) comment '流失项ID',
   where_abouts_id      varchar(32) comment '流失去向ID',
   where_abouts         varchar(200) comment '流失去向',
   run_off_date         datetime not null comment '流失日期',
   is_warn              int(1) comment '是否有过预警',
   re_hire              int(1) comment '建议重新录用',
   primary key (run_off_record_id)
);

alter table `mup-source`.source_run_off_record comment '业务表-流失记录(`mup-source`.source_run_off_record)';

/*==============================================================*/
/* Index: ind_eId_run_off_record                                */
/*==============================================================*/
create index ind_eId_run_off_record on `mup-source`.source_run_off_record
(
   emp_id
);

-- 晋级看板
-- ======================

create table `mup-source`.source_promotion_element_scheme
(
   promotion_element_scheme_id varchar(32) not null comment '晋级要素方案ID',
   customer_id          varbinary(32) comment '客户ID',
   scheme_id            varchar(32) comment '方案ID',
   scheme_name          varchar(50) comment '方案名称',
   company_age          int(3) comment '司龄',
   curt_name_per        varchar(30) comment '绩效短名',
   curt_name_eval       int(4) comment '能力评价短名',
   certificate_id       varchar(32) comment '证书ID',
   certificate_type_id  varchar(32) comment '证书类型ID',
   create_user_id       varchar(32) comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   start_date           datetime comment '生效时间',
   invalid_date         datetime comment '失效时间',
   primary key (promotion_element_scheme_id)
);

alter table `mup-source`.source_promotion_element_scheme comment '晋级要素方案(`mup-source`.source_promotion_element_scheme)';

create table `mup-source`.source_promotion_results
(
   promotion_results_id varchar(32) not null comment '员工晋级结果ID',
   emp_id               varchar(32) comment '员工ID',
   company_age          int(2) comment '司龄',
   oranization_parent_id varchar(32) comment '父部门ID',
   organization_id      varchar(32) comment '部门ID',
   full_path            varchar(20) comment '部门全路径',
   performance_id       varchar(32) comment '绩效ID',
   sequence_id          varchar(32) comment '主序列ID',
   is_key_talent        int(1) comment '是否关键人才',
   customer_id          varchar(32) comment '客户ID',
   entry_date           date comment '入职时间',
   show_index           int(1) comment '排序',
   rank_name            varchar(5) comment '当前简称',
   show_index_af        int(1) comment '晋级后排序',
   rank_name_af         varchar(5) comment '晋级后简称',
   status               int(1) comment '晋级状态',
   status_date          date comment '结果时间',
   promotion_date       date comment '晋级时间',
   primary key (promotion_results_id)
);

alter table `mup-source`.source_promotion_results comment '员工晋级结果(`mup-source`.source_promotion_results)';

create table `mup-source`.source_promotion_plan
(
   promotion_plan_id    varbinary(32) not null comment '职级晋升方案ID',
   customer_id          varchar(32) comment '客户ID',
   rank_name_af         varbinary(5) comment '晋级后简称',
   scheme_id            varchar(32) comment '方案ID',
   create_user_id       varchar(32) comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   primary key (promotion_plan_id)
);

alter table `mup-source`.source_promotion_plan comment '职级晋升方案(`mup-source`.source_promotion_plan)';
-- 薪酬看板
-- ================
create table `mup-source`.source_welfare_cpm
(
   id       varchar(32) not null comment '企业福利明细ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   user_name_ch         varchar(50) comment '员工中文名',
   cpm_id               varchar(32) comment '企业福利ID',
   welfare_value        double(10,4) comment '发放金额（单位：元）',
   date                 tinyint(1) comment '发放时间',
   `year_month`         int(6) not null comment '年月',
   refresh              datetime comment '更新时间',
   primary key (id, `year_month`)
);

alter table `mup-source`.source_welfare_cpm comment '业务表-企业福利明细（货币）（`mup-source`.source_welfare_cpm）';

create table `mup-source`.source_welfare_nfb
(
   id           varchar(32) not null comment '福利ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   user_name_ch          varchar(50) comment '员工中文名',
   nfb_id               varchar(32) comment '国家固定福利ID',
   welfare_value        double(10,4) comment '缴费金额（单位：元）',
   date                 tinyint(1) comment '缴费时间',
   `year_month`         int(6) comment '年月',
   refresh              datetime comment '更新时间',
   primary key (id)
);

alter table `mup-source`.source_welfare_nfb comment '业务表-固定福利明细（`mup-source`.source_welfare_nfb）';

create table `mup-source`.source_welfare_uncpm
(
   id     varchar(32) not null comment '企业福利明细ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   uncpm_id             varchar(32) comment '福利类别ID',
   note                 varchar(50) comment '备注',
   date                 tinyint(1) comment '发放时间',
   `year_month`         int(6) comment '年月',
   refresh              datetime comment '更新时间',
   primary key (id)
);

alter table `mup-source`.source_welfare_uncpm comment '业务表-企业福利明细（非货币）（`mup-source`.source_welfare_uncpm）';


create table `mup-source`.source_salary
(
   id            varchar(32) not null comment '工资ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   structure_id         varchar(32) comment '工资结构ID',
   salary_value         double(10,5) comment '值（单位：万元）',
   `year_month`         int(10) not null comment '年月',
   refresh              datetime comment '更新时间',
   primary key (id, `year_month`)
);

alter table `mup-source`.source_salary comment '业务表-工资明细（`mup-source`.source_salary）';

create table `mup-source`.source_salary_year
(
   id            varchar(32) not null comment '工资ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   structure_id         varchar(32) comment '工资结构ID',
   salary_value_year    double(10,4) comment '值（单位：万元）',
   `year`                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (id)
);

alter table `mup-source`.source_salary_year comment '业务表-工资明细（`mup-source`.source_salary_year）';

create table share_holding
(
   id     varchar(32) not null comment '持股明细ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   usre_name_ch         varchar(5) comment '中文名',
   organization_id      varchar(32) comment '机构ID',
   full_path            varchar(200) comment '机构全路径',
   now_share            int(4) comment '当前数量（单位：股）',
   confer_share         int(4) comment '授予数量（单位：股）',
   price                double(10,4) comment '授予价（单位：元/股）',
   hold_period          varchar(5) comment '持有期',
   sub_num              int(4) comment '最近减持数量',
   sub_date             datetime comment '最近减持时间',
   `year`                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (id)
);

alter table share_holding comment '业务表-持股明细（share_holding）';

create table `mup-source`.source_pay
(
   id               varchar(32) not null comment '薪酬费用ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   usre_name_ch         varchar(5) comment '中文名',
   organization_id      varchar(32) comment '机构ID',
   full_path            varchar(200) comment '机构全路径',
   postion_id           varchar(32) comment '岗位ID',
   pay_contract         blob comment '合同薪酬（单位：元）',
   pay_should           blob comment '应发薪酬（单位：元）',
   pay_actual           blob comment '实发工资（单位：元）',
   salary_actual        blob comment '应发工资（单位：元）',
   welfare_actual       varchar(50) comment '应发福利（单位：元）',
   cr_value             double(6,4) comment 'CR值（过）',
   `year_month`         int(6) comment '年月',
   refresh              datetime comment '更新时间',
   `year`                 int(4) comment '年',
   bonus                 varchar(10) comment 'bonus',
   primary key (id)
);

alter table `mup-source`.source_pay comment '业务表-薪酬费用（`mup-source`.source_pay）';

create table `mup-source`.source_profession_quantile_relation
(
   id 					varchar(32) not null comment '行业分位ID',
   customer_id          varchar(32) comment '客户ID',
   profession_id        varchar(32) comment '行业ID',
   quantile_id          varchar(32) comment '分位ID',
   quantile_value       double(4,3) comment '分位值（单位：万元）',
   type                 tinyint(1) comment '类别（1人均；2成本；3薪酬）',
   `year`                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (id)
);

alter table `mup-source`.source_profession_quantile_relation comment '关系表-行业分位（`mup-source`.source_profession_quantile_relation）';

-- 职位序列
create table `mup-source`.source_job_relation
(
   job_relation_id      varchar(32) not null comment '职位序列ID',
   customer_id          varchar(32) comment '客户ID',
   sequence_sub_id      varchar(32) comment '子序列ID',
   sequence_id          varchar(32) comment '主序列ID',
   ability_id           varchar(32) comment '能力层级ID',
   job_title_id         varchar(32) comment '职衔ID',
   ability_lv_id        varchar(32) comment '职级ID',
   year                 int(4) comment '年',
   type                 int(1) comment '类型（0：默认名称。1：特定名称）',
   rank_name            varchar(5) comment '主子序列层级职级',
   show_index           int(4) comment '排序',
   refresh              datetime comment '更新日期',
   primary key (job_relation_id)
);

alter table `mup-source`.source_job_relation comment '关系表-职位序列关系（`mup-source`.source_job_relation）';

-- 岗位胜任度
-- ==================
create table `mup-source`.source_position_quality
(
   position_quality_id  varchar(32) not null comment '岗位能力素质ID',
   customer_id          varchar(32) comment '客户ID',
   position_id          varchar(32) comment '岗位ID',
   quality_id           varchar(32) comment '能力素质ID',
   matching_soure_id    varchar(32) comment '分数映射ID',
   show_index           tinyint(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (position_quality_id)
);

alter table `mup-source`.source_position_quality comment '关系表-岗位能力素质（`mup-source`.source_position_quality）';

create table `mup-source`.source_emp_pq_relation
(
   emp_pq_relation_id   varchar(32) not null comment '员工岗位素质得分ID',
   quality_id           varchar(32) comment '能力素质ID',
   matching_soure_id    varchar(32) comment '所得分数ID',
   demands_matching_soure_id varchar(32) comment '要求分数ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   date                 date comment '考核日期',
   refresh              datetime comment '更新时间',
   `year_month`         int(6) comment '年月',
   primary key (emp_pq_relation_id)
);

alter table `mup-source`.source_emp_pq_relation comment '关系表-员工岗位素质得分（`mup-source`.source_emp_pq_relation）';

-- 人力成本
-- ==============


create table `mup-source`.source_manpower_cost_value
(
   manpower_cost_value_id varchar(32) not null comment '人力年度预算成本ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   budget_value         double(20,4) comment '预算度本',
   year                 int(4) comment '年',
   primary key (manpower_cost_value_id)
);
alter table `mup-source`.source_manpower_cost_value comment '业务表-人力年度预算成本（年）（`mup-source`.source_manpower_cost_value）';

-- ********************************`mup-source`库 END****************************************


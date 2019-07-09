/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:42:36                           */
/*==============================================================*/


drop table if exists `mup-source`.source_code_item;

drop table if exists dept_kpi;

-- drop index index_abKey on dim_ability;

-- drop index index_abId on dim_ability;

drop table if exists dim_ability;

drop table if exists dim_organization;

-- drop index index_posId on dim_position;

-- drop index un_cId on dim_position;

drop table if exists dim_position;

drop table if exists dim_profession;

-- drop index index_seqId on dim_sequence;

drop table if exists dim_sequence;

drop table if exists dim_structure;

drop table if exists fact_fte;

drop table if exists manpower_cost;

-- drop index ind_org_pay on pay;

-- drop index index_ym_orgId on pay;

-- drop index index_ym on pay;

-- drop index index_fp on pay;

-- drop index "index_eId_ym_pay y" on pay;

-- drop index index_cusId on pay;

drop table if exists pay;

-- drop index index_ym on pay_collect;

drop table if exists pay_collect;

drop table if exists pay_collect_year;

drop table if exists pay_value;

-- drop index index_ym on performance_change;

-- drop index index_perId_ym_performance_change on performance_change;

-- drop index index_perId on performance_change;

-- drop index index_orgId on performance_change;

-- drop index index_eKey on performance_change;

-- drop index index_customerId on performance_change;

-- drop index index_eId on performance_change;

drop table if exists performance_change;

drop table if exists profession_quantile_relation;

-- drop index ind_strId_y_salary_year on salary_year;

drop table if exists salary_year;

drop table if exists share_holding;

drop table if exists trade_profit;

-- drop index ind_Uch on v_dim_emp;

-- drop index ind_rn_v_dim_emp on v_dim_emp;

-- drop index ind_perId_v_dim_emp on v_dim_emp;

-- drop index ind_Org on v_dim_emp;

-- drop index ind_mark on v_dim_emp;

-- drop index ind_eId_unc_rk_VDE on v_dim_emp;

-- drop index indx_pos on v_dim_emp;

-- drop index indx_eORG on v_dim_emp;

-- drop index index_eKey on v_dim_emp;

-- drop index index_eId on v_dim_emp;

-- drop index index_ed on v_dim_emp;

drop table if exists v_dim_emp;

-- drop index ind_ym on welfare_cpm;

-- drop index ind_eId_ym_welfare_cpm on welfare_cpm;

-- drop index ind_cId on welfare_cpm;

-- drop index index_ym_eId on welfare_cpm;

-- drop index index_eId on welfare_cpm;

drop table if exists welfare_cpm;

drop table if exists welfare_cpm_total;

-- drop index ind_ym_unc_welfare_nfb on welfare_nfb;

-- drop index ind_ym_eId on welfare_nfb;

-- drop index ind_eId_ym on welfare_nfb;

-- drop index ind_ym on welfare_nfb;

-- drop index ind_eId on welfare_nfb;

drop table if exists welfare_nfb;

drop table if exists welfare_nfb_total;

-- drop index ind_ym_eId on welfare_uncpm;

-- drop index ind_Uid on welfare_uncpm;

-- drop index ind_ym on welfare_uncpm;

-- drop index ind_eId on welfare_uncpm;

drop table if exists welfare_uncpm;

/*==============================================================*/
/* Table: `mup-source`.source_code_item                                             */
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
   refresh_date         datetime comment '更新时间',
   primary key (organization_id)
);

alter table dim_organization comment '维度表-机构维（dim_organization）';

/*==============================================================*/
/* Table: dim_position                                          */
/*==============================================================*/
create table dim_position
(
   position_id          varchar(32) not null comment '岗位ID',
   customer_id          varchar(32) comment '客户ID',
   position_key         varchar(20) comment '岗位编码',
   position_name        varchar(20) comment '岗位名称',
   refresh              datetime comment '更新时间',
   c_id                 varchar(0) comment '标识ID',
   show_index       	int(1) comment '排序',
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
/* Table: dim_profession                                        */
/*==============================================================*/
create table dim_profession
(
   profession_id        varchar(32) not null comment '行业ID',
   profession_name      varchar(80) comment '行业名称',
   profession_key       varchar(32) comment '行业编码',
   show_index       	int(1) comment '排序',
   refresh              datetime comment '更新日期',
   primary key (profession_id)
);

alter table dim_profession comment '维度表-行业维（dim_profession）';

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
/* Table: dim_structure                                         */
/*==============================================================*/
create table dim_structure
(
   structure_id         varchar(32) not null comment '工资结构ID',
   customer_id          varchar(32) comment '客户ID',
   structure_name       varchar(20) comment '结构名称',
   is_fixed             tinyint(1) comment '是否固定',
   show_index       	int(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (structure_id)
);

alter table dim_structure comment '维度表-工资结构维（dim_structure）';

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
   position_name
   position_idposition_name varchar(60) comment '岗位名称',
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
   tel_phone            int(11) comment '手机',
   qq                   int(20) comment 'qq号码',
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


--drop index index_ym on salary;

--drop index index_eId_ym_salary on salary;

drop table if exists salary;

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

drop table if exists nfb_proportion;

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


drop table if exists `mup-source`.source_welfare_cpm;
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

drop table if exists `mup-source`.source_welfare_nfb;
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

drop table if exists `mup-source`.source_welfare_uncpm;
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


drop table if exists `mup-source`.source_salary;
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

drop table if exists `mup-source`.source_salary_year;
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

drop table if exists `mup-source`.source_share_holding;
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

drop table if exists `mup-source`.source_pay;
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

drop table if exists `mup-source`.source_profession_quantile_relation;
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

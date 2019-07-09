/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:28:09                           */
/*==============================================================*/


drop table if exists dim_organization;

drop table if exists dim_profession;

drop table if exists fact_fte;

drop table if exists manpower_cost;

drop table if exists manpower_cost_item;

drop table if exists manpower_cost_value;

drop table if exists profession_value;

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


-- drop index index_ym on pay_collect;

drop table if exists pay_collect;

/*==============================================================*/
/* Table: pay_collect                                           */
/*==============================================================*/
create table pay_collect
(
   pay_collect_id       char(32) not null comment '薪酬汇总ID',
   customer_id          char(32) comment '客户ID',
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

drop table if exists train_outlay;

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


-- drop index un_cid on recruit_value;

drop table if exists recruit_value;

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
-- 人力成本
-- ==============
drop table if exists `mup-source`.source_manpower_cost_value;
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


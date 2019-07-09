/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:29:01                           */
/*==============================================================*/


drop table if exists dim_organization;

drop table if exists dim_profession;

drop table if exists fact_fte;

drop table if exists profession_value;

drop table if exists target_benefit_value;

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



drop table if exists trade_profit;

/*==============================================================*/
/* Table: trade_profit                                          */
/*==============================================================*/
create table trade_profit
(
   trade_profit_id      char(32) not null comment '营业利润ID',
   customer_id          char(32) comment '客户ID',
   business_unit_id     char(32) comment '业务单位ID',
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
/* Table: `mup-source`.source_target_benefit_value                                  */
/*==============================================================*/
drop table if exists `mup-source`.source_target_benefit_value;
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

/*==============================================================*/
/* Table: `mup-source`.source_trade_profit                                          */
/*==============================================================*/
drop table if exists `mup-source`.source_trade_profit;
create table `mup-source`.source_trade_profit
(
   trade_profit_id      char(32) not null comment '营业利润ID',
   customer_id          char(32) comment '客户ID',
   business_unit_id     char(32) comment '业务单位ID',
   organization_id      varchar(32) comment '机构ID',
   sales_amount         decimal(10,4) comment '营业收入、销售总额（单位：万元）',
   expend_amount        double(7,2) comment '营业支出、企业总成本（单位：万元）',
   gain_amount          decimal(10,4) comment '营业利润（单位：万元）',
   target_value         double(10,4) comment '目标人均效益值（单位：万元）',
   `year_month`         int(6) comment '年月',
   primary key (trade_profit_id)
);

alter table `mup-source`.source_trade_profit comment '业务表-营业利润（`mup-source`.source_trade_profit）';

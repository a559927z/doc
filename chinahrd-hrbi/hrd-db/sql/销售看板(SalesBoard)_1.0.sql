/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:43:04                           */
/*==============================================================*/


-- drop index ind_Org_y on ability_change;

-- drop index ind_eId_y on ability_change;

-- drop index ind_abId on ability_change;

drop table if exists ability_change;

drop table if exists `mup-source`.source_code_item;

drop table if exists config;

drop table if exists dim_ability_number;

-- drop index ind_pId_dim_city on dim_city;

drop table if exists dim_city;

drop table if exists dim_change_type;

drop table if exists dim_organization;

drop table if exists dim_province;

drop table if exists dim_sales_product;

drop table if exists dim_sales_team;

drop table if exists history_dim_organization_month;

drop table if exists history_emp_count;

drop table if exists history_sales_ability;

-- drop index ind_sh on history_sales_detail;

-- drop index ind_pId on history_sales_detail;

-- drop index ind_eId_history_sales_detail on history_sales_detail;

drop table if exists history_sales_detail;

-- drop index index_seqSubId on job_change;

-- drop index ind_Ch_Us_job_change on job_change;

-- drop index index_seqId on job_change;

-- drop index index_posId on job_change;

-- drop index index_orgId_ym_job_change on job_change;

-- drop index index_jtId on job_change;

-- drop index index_eKey on job_change;

-- drop index index_eId on job_change;

-- drop index index_abLvId on job_change;

-- drop index index_abId on job_change;

drop table if exists job_change;

-- drop index ind_Ch_Date_Uch on organization_change;

-- drop index ind_Ch_Date on organization_change;

-- drop index index_orgId_Day on organization_change;

-- drop index index_eKey on organization_change;

-- drop index index_eId on organization_change;

drop table if exists organization_change;

-- drop index index_ym on run_off_total;

-- drop index index_ofp on run_off_total;

drop table if exists run_off_total;

drop table if exists sales_ability;

drop table if exists sales_config;

-- drop index ind_eId_sales_detail on sales_detail;

-- drop index Index_1ind_cId_sm_sales_detail on sales_detail;

-- drop index ind_orgId_sales_emp on sales_emp;

drop table if exists sales_emp;

drop table if exists sales_emp_month;

-- drop index ind_ym on sales_emp_rank;

drop table if exists sales_emp_rank;

drop table if exists sales_emp_target;

drop table if exists sales_org_day;

drop table if exists sales_org_month;

drop table if exists sales_org_prod_month;

drop table if exists sales_org_target;

drop table if exists sales_pro_target;

drop table if exists sales_team;

drop table if exists sales_team_rank;

drop table if exists sales_team_target;

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

drop table if exists history_sales_order;

drop table if exists sales_detail;

drop table if exists sales_order;

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
   sex                  int(1) comment '性别',
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
/* Table: dim_ability_number                                    */
/*==============================================================*/
create table dim_ability_number
(
   ability_number_id    varchar(32) not null comment '能力ID',
   ability_number_key    varchar(20) comment '能力编码',
   ability_number_name  varchar(20) comment '能力名称',
   customer_id          varchar(32) comment '客户ID',
   show_index           int(1) comment '排序',
   create_date          date comment '创建时间',
   primary key (ability_number_id)
);

alter table dim_ability_number comment '员工能力记录(dim_ability_number)';

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
/* Table: dim_change_type                                         */
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
   c_id                 varchar(32) comment '标识ID',
   primary key (province_id)
);

alter table dim_province comment '维度表-省（dim_province）';

/*==============================================================*/
/* Table: dim_sales_product                                     */
/*==============================================================*/
create table dim_sales_product
(
   product_id           varchar(32) not null comment '商品ID',
   customer_id          varchar(32) comment '客户ID',
   product_name         varchar(50) comment '商品名称',
   product_price        double(10,2) comment '商品价格（单位：元）',
   product_cost         double(10,2) comment '商品成本（单位：元）',
   show_index       	int(1) comment '排序',
   primary key (product_id)
);

alter table dim_sales_product comment '产品明细(dim_sales_product)';

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
   show_index       	int(1) comment '排序',
   primary key (team_id)
);

alter table dim_sales_team comment '团队信息(dim_sales_team)';

/*==============================================================*/
/* Table: history_dim_organization_month                        */
/*==============================================================*/
create table history_dim_organization_month
(
   history_dim_organization_month_id varchar(32) not null comment '机构纬度ID',
   organization_id      varchar(32) comment '机构ID',
   customer_id          varchar(32) comment '客户ID',
   organization_type_id varchar(32) comment '机构级别ID',
   organization_key     varchar(20) comment '机构编码',
   organization_parent_id varchar(32) comment '上级机构',
   organization_name    varchar(20) comment '机构名称',
   organization_name_full varchar(50) comment '机构全称',
   organization_parent_key varchar(20) comment '上级机构编码',
   organization_company_id varchar(32) comment '分公司ID',
   business_unit_id     varchar(32) comment '业务单位ID',
   note                 varchar(200) comment '描述',
   is_single            int(1) comment '是否独立核算',
   full_path            longtext comment '全路径',
   has_children         tinyint(1) comment '是否有子节点',
   depth                int(4) comment '深度',
   profession_id        varchar(32) comment '行业ID',
   refresh_date         datetime comment '更新时间',
   `year_month`         int(6) not null comment '年月',
   c_id                 varchar(32) comment '标识ID',
   primary key (history_dim_organization_month_id, `year_month`)
);

alter table history_dim_organization_month comment '历史表-机构维（history_dim_organization_month）';

/*==============================================================*/
/* Table: history_emp_count                                     */
/*==============================================================*/
create table history_emp_count
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

alter table history_emp_count comment '历史表-每月总人数（history_emp_count_month）';

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
   sales_money          double(10,2) comment '实际销售金额（单位：元）',
   sales_profit         double(10,2) comment '销售利润（单位：元）',
   sales_province_id    varchar(32) comment '销售地点(省)',
   sales_city_id        varchar(32) comment '销售地点(市)',
   sales_date           date comment '销售时间',
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
/* Index: Index_1ind_cId_sm_sales_detail                        */
/*==============================================================*/
create index Index_1ind_cId_sm_sales_detail on sales_detail
(
   sales_money,
   sales_city_id
);

/*==============================================================*/
/* Index: ind_eId_sales_detail                                  */
/*==============================================================*/
create index ind_eId_sales_detail on sales_detail
(
   emp_id
);

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
/* Index: ind_ym                                                */
/*==============================================================*/
create index ind_er on sales_emp_rank
(
   `emp_rank`
);/*==============================================================*/
/* Index: ind_ym                                                */
/*==============================================================*/
create index ind_pid on sales_emp_rank
(
   `proportion_id`
);
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
/* Table: history_sales_order                                   */
/*==============================================================*/
create table history_sales_order
(
   sales_order_id       varchar(32) comment '历史订单ID',
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
/* Table: `mup-source`.source_sales_detail                                          */
/*==============================================================*/
drop table if exists `mup-source`.source_sales_detail;
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
/*==============================================================*/
/* Table: `mup-source`.source_sales_pro_target                                      */
/*==============================================================*/
drop table if exists `mup-source`.source_sales_pro_target;
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
/*==============================================================*/
/* Table: `mup-source`.source_sales_org_target                                      */
/*==============================================================*/
drop table if exists `mup-source`.source_sales_org_target;
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
/*==============================================================*/
/* Table: `mup-source`.source_sales_ability                                         */
/*==============================================================*/
drop table if exists `mup-source`.source_sales_ability;
create table `mup-source`.source_sales_ability
(
   emp_id               varchar(32) not null comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   status               int(1) comment '考核状态',
   check_date           date comment '考核时间',
   primary key (emp_id)
);

alter table sales_ability comment '业务能力考核(`mup-source`.source_sales_ability)';
/*==============================================================*/
/* Table: `mup-source`.source_sales_team_rank                                       */
/*==============================================================*/
drop table if exists `mup-source`.source_sales_team_rank;
create table `mup-source`.source_sales_team_rank
(
   team_id              varchar(32) not null comment '团队ID',
   customer_id          varchar(32) comment '客户ID',
   team_rank            int(6) comment '排名',
   rank_date            date not null comment '时间',
   `year_month`         int(6) comment '年月',
   primary key (team_id, rank_date)
);

alter table `mup-source`.source_sales_team_rank comment '团队销售排名(`mup-source`.source_sales_team_rank)';
/*==============================================================*/
/* Table: `mup-source`.source_sales_team_target                                     */
/*==============================================================*/
drop table if exists `mup-source`.source_sales_team_target;
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
/* Table: `mup-source`.source_sales_emp                                             */
/*==============================================================*/
drop table if exists `mup-source`.source_sales_emp;
create table `mup-source`.source_sales_emp
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

alter table `mup-source`.source_sales_emp comment '销售人员信息表(`mup-source`.source_sales_emp)';

/*==============================================================*/
/* Index: ind_orgId_sales_emp                                   */
/*==============================================================*/
create index ind_orgId_sales_emp on `mup-source`.source_sales_emp
(
   organization_id
);
/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:40:48                           */
/*==============================================================*/


drop table if exists course_record;

-- drop index index_abKey on dim_ability;

-- drop index index_abId on dim_ability;

drop table if exists dim_ability;

drop table if exists dim_organization;

-- drop index FK_Reference_35 on dim_run_off;

drop table if exists dim_run_off;

drop table if exists dim_sales_product;

-- drop index index_seqId on dim_sequence;

drop table if exists dim_sequence;

drop table if exists emp_train_experience;

drop table if exists history_emp_count;

drop table if exists lecturer;

drop table if exists lecturer_course_design;

drop table if exists lecturer_course_speak;

drop table if exists monthly_report_save;

drop table if exists monthly_report_share;

-- drop index ind_Ch_Date_Uch on organization_change;

-- drop index ind_Ch_Date on organization_change;

-- drop index index_orgId_Day on organization_change;

-- drop index index_eKey on organization_change;

-- drop index index_eId on organization_change;

drop table if exists organization_change;

-- drop index ind_eId_run_off_record on run_off_record;

drop table if exists run_off_record;

drop table if exists sales_org_month;

drop table if exists sales_org_prod_month;

drop table if exists sales_pro_target;

drop table if exists train_outlay;

drop table if exists train_plan;

drop table if exists train_value;

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
/* Table: dim_run_off                                           */
/*==============================================================*/
create table dim_run_off
(
   run_off_id           varchar(32) not null comment '流失项ID',
   customer_id          varchar(32) comment '客户ID',
   run_off_key          varchar(3) comment '流失编码',
   run_off_name         varchar(200) comment '流失名称',
   `type` int(1) comment '(1：主动 2：被动 3：其它)',
   show_index       	int(1) comment '排序',
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
/* Table: emp_train_experience                                  */
/*==============================================================*/
create table emp_train_experience
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

alter table emp_train_experience comment '业务表-培训经历（emp_train_experience）';

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
   lecture_id           varchar(32) comment '讲师ID',
   course_id            varchar(32) comment '课程ID',
   start_date           datetime comment '授课开始时间',
   end_date             datetime comment '授课结束时间',
   year                 int(4) comment '年',
   primary key (lecturer_course_speak_id)
);

alter table lecturer_course_speak comment '关系表-讲师授课（lecturer_course_speak）';

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
/* Table: sales_pro_target                                      */
/*==============================================================*/
create table sales_pro_target
(
   product_id           varchar(32) not null comment '产品ID',
   customer_id          varchar(32) comment '客户ID',
   sales_target         double(10,4) comment '目标销售额(单位：万元',
   return_amount        double(10,4) comment '回款额(单位：万元)',
   payment              double(10,4) comment '已回款(单位：万元)',
   `year_month`         int(6) not null comment '年月',
   primary key (product_id, `year_month`)
);

alter table sales_pro_target comment '产品考核(sales_pro_target)';

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


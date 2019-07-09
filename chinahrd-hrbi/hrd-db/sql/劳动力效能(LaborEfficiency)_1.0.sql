/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:36:14                           */
/*==============================================================*/


drop table if exists dim_checkwork_type;

drop table if exists dim_organization;

-- drop index index_username on dim_user;

-- drop index index_pwd on dim_user;

-- drop index index_key on dim_user;

-- drop index index_customerId on dim_user;

drop table if exists dim_user;

drop table if exists emp_att_info;

drop table if exists emp_att_item_info;

-- drop index ind_y_org on emp_attendance_month;

-- drop index ind_y_eId on emp_attendance_month;

-- drop index ind_eId_y on emp_attendance_month;

drop table if exists emp_attendance_month;

drop table if exists emp_attendance_day;

-- drop index un_cId on emp_other_day;

-- drop index ind_eId_ym on emp_other_day;

-- drop index ind_days_eId on emp_other_day;

-- drop index ind_days on emp_other_day;

drop table if exists emp_other_day;

-- drop index index_ym on emp_overtime_day;

-- drop index index_days_name on emp_overtime_day;

-- drop index index_days_eId on emp_overtime_day;

-- drop index index_orgId on emp_overtime_day;

drop table if exists emp_overtime_day;

drop table if exists history_dim_organization_month;

drop table if exists history_emp_count;

drop table if exists permiss_import;

-- drop index ind_eId_day on population_relation;

-- drop index ind_eId on population_relation;

-- drop index ind_day_pId on population_relation;

-- drop index ind_day_eId on population_relation;

-- drop index ind_days on population_relation;

drop table if exists population_relation;

-- drop index ind_ym_pop on population_relation_month;

-- drop index ind_eId_ym_pop on population_relation_month;

drop table if exists population_relation_month;

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
   "year_month"         int(6) comment '年月',
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
   "year_month"
);

/*==============================================================*/
/* Index: ind_y_eId                                             */
/*==============================================================*/
create index ind_y_eId on emp_attendance_month
(
   "year_month",
   emp_id
);

/*==============================================================*/
/* Index: ind_y_org                                             */
/*==============================================================*/
create index ind_y_org on emp_attendance_month
(
   "year_month",
   organization_id
);

/*==============================================================*/
/* Table: emp_attendance_day                             */
/*==============================================================*/
create table emp_attendance_day
(
   emp_attendance_day_id varchar(32) not null comment '出勤记录ID',
   customer_id          varchar(32)) comment '客户ID',
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
/* Table: emp_other_day                                         */
/*==============================================================*/
create table emp_other_day
(
   emp_other_day_id     varchar(32) not null comment '记录ID',
   customer_id          varchar(32)) comment '客户ID',
   emp_key              varchar(20) comment '员工编号',
   emp_id               varchar(32) comment '员工ID',
   user_name_ch         varchar(5) comment '员工名称',
   hour_count           double(4,2) comment '休假小时',
   organization_id      varchar(32) comment '机构ID',
   checkwork_type_id    varchar(32) comment '考勤类型ID',
   days                  date comment '日期',
   `year_months`         int(6) comment '年月',
   c_id                 varchar(32) comment '标识ID',
   primary key (emp_other_day_id)
);

alter table emp_other_day comment '业务表-其他考勤记录（emp_other_day）';

/*==============================================================*/
/* Index: ind_days                                              */
/*==============================================================*/
create index ind_days on emp_other_day
(
   day
);

/*==============================================================*/
/* Index: ind_days_eId                                          */
/*==============================================================*/
create index ind_days_eId on emp_other_day
(
   emp_id,
   day
);

/*==============================================================*/
/* Index: ind_eId_ym                                            */
/*==============================================================*/
create index ind_eId_ym on emp_other_day
(
   emp_id,
   "year_month"
);

/*==============================================================*/
/* Index: un_cId                                                */
/*==============================================================*/
create unique index un_cId on emp_other_day
(
   c_id
);

/*==============================================================*/
/* Table: emp_overtime_day                                      */
/*==============================================================*/
create table emp_overtime_day
(
   emp_overtime_day_id  varchar(32) not null comment '员工加班记录ID',
   customer_id          varchar(32)) comment '客户ID',
   emp_key              varchar(20) comment '员工编号',
   emp_id               varchar(32) comment '员工ID',
   user_name_ch         varchar(5) comment '员工名称',
   hour_count           double(4,2) comment '加班小时',
   organization_id      varchar(32) comment '机构ID',
   checkwork_type_id    varchar(32) comment '考勤类型ID',
   days                 date comment '日期',
   "year_month"         int(6) comment '年月',
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
   "year_month"
);

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
   "year_month"         int(6) not null comment '年月',
   c_id                 varchar(32) comment '标识ID',
   primary key (history_dim_organization_month_id, "year_month")
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
   "year_month"         int(6) comment '年月',
   refresh              date comment '更新时间',
   primary key (history_emp_count_id)
);

alter table history_emp_count comment '历史表-每月总人数（history_emp_count_month）';

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
create table population_relation
(
   population_relation_id varchar(32) not null comment '人员人群关系ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   organization_id      varchar(32) comment '机构ID',
   population_id        varchar(32) comment '人群范围ID',
   days                 date comment '日期',
   primary key (population_relation_id)
);

alter table population_relation comment '关系表-人员人群关系表(population_relation)';

/*==============================================================*/
/* Index: ind_days                                              */
/*==============================================================*/
create index ind_days on population_relation
(
   days
);

/*==============================================================*/
/* Index: ind_day_eId                                           */
/*==============================================================*/
create index ind_day_eId on population_relation
(
   days,
   emp_id
);

/*==============================================================*/
/* Index: ind_day_pId                                           */
/*==============================================================*/
create index ind_day_pId on population_relation
(
   days,
   population_id
);

/*==============================================================*/
/* Index: ind_eId                                               */
/*==============================================================*/
create index ind_eId on population_relation
(
   emp_id
);

/*==============================================================*/
/* Index: ind_eId_day                                           */
/*==============================================================*/
create index ind_eId_day on population_relation
(
   emp_id,
   days
);

/*==============================================================*/
/* Table: population_relation_month                             */
/*==============================================================*/
create table population_relation_month
(
   population_relation_month_id varchar(32) not null comment '人员人群关系ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   organization_id      varchar(32) comment '机构ID',
   population_id        varchar(32) comment '人群范围ID',
   "year_month"         int(6) comment '年月',
   primary key (population_relation_month_id)
);

alter table population_relation_month comment '关系表-人员人群关系表(population_relation_month)';

/*==============================================================*/
/* Index: ind_eId_ym_pop                                        */
/*==============================================================*/
create index ind_eId_ym_pop on population_relation_month
(
   emp_id,
   "year_month"
);

/*==============================================================*/
/* Index: ind_ym_pop                                            */
/*==============================================================*/
create index ind_ym_pop on population_relation_month
(
   population_id,
   "year_month"
);

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


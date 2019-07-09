/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:32:01                           */
/*==============================================================*/


-- drop index index_abKey on dim_ability;

-- drop index index_abId on dim_ability;

drop table if exists dim_ability;

-- drop index ind_pId_dim_city on dim_city;

drop table if exists dim_city;

-- drop index index_orgId_Days on dim_emp;

-- drop index index_eKey on dim_emp;

-- drop index index_eId_orgId on dim_emp;

-- drop index index_eId on dim_emp;

-- drop index index_days_eId on dim_emp;

-- drop index index_days on dim_emp;

drop table if exists dim_emp;

-- drop index ind_ym_org on dim_emp_month;

-- drop index ind_ym_eId on dim_emp_month;

drop table if exists dim_emp_month;

drop table if exists dim_change_type;

drop table if exists dim_organization;

-- drop index un_cId on dim_population;

drop table if exists dim_population;

drop table if exists dim_province;

-- drop index index_seqId on dim_sequence;

drop table if exists dim_sequence;

-- drop index un_cId on emp_edu;

-- drop index ind_name on emp_edu;

-- drop index ind_eId on emp_edu;

drop table if exists emp_edu;

drop table if exists history_dim_organization_month;

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
/* Table: dim_emp                                               */
/*==============================================================*/
create table dim_emp
(
   dim_emp_id           varchar(32) not null comment '员工维度ID',
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
   address              varchar(60) comment '住址',
   contract_unit        varchar(32) comment '合同单位',
   contract             varchar(20) comment '合同性质',
   work_place_id        varchar(32) comment '工作地点ID',
   work_place           varchar(60) comment '工作地点',
   is_regular           int(1) comment '是否正职（1 主岗位  0 负岗位）',
   entry_date           datetime comment '入职日期',
   run_off_date         datetime not null comment '流失日期',
   days                 date comment '日期',
   primary key (dim_emp_id)
);

alter table dim_emp comment '历史表-员工信息日切片(dim_emp)-程序不推荐使用';

/*==============================================================*/
/* Index: index_days                                            */
/*==============================================================*/
create index index_days on dim_emp
(
   days
);

/*==============================================================*/
/* Index: index_days_eId                                        */
/*==============================================================*/
create index index_days_eId on dim_emp
(
   days,
   emp_id
);

/*==============================================================*/
/* Index: index_eId                                             */
/*==============================================================*/
create index index_eId on dim_emp
(
   emp_id
);

/*==============================================================*/
/* Index: index_eId_orgId                                       */
/*==============================================================*/
create index index_eId_orgId on dim_emp
(
   emp_id,
   organization_id
);

/*==============================================================*/
/* Index: index_eKey                                            */
/*==============================================================*/
create index index_eKey on dim_emp
(
   emp_key
);

/*==============================================================*/
/* Index: index_orgId_Days                                      */
/*==============================================================*/
create index index_orgId_Days on dim_emp
(
   days,
   organization_id
);

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
   address              varchar(60) comment '住址',
   contract_unit        varchar(32) comment '合同单位',
   contract             varchar(20) comment '合同性质',
   work_place_id        varchar(32) comment '工作地点ID',
   work_place           varchar(60) comment '工作地点',
   is_regular           int(1) comment '是否正职（1 主岗位  0 负岗位）',
   entry_date           datetime comment '入职日期',
   run_off_date         datetime not null comment '流失日期',
   "year_month"         int(6) comment '年月',
   primary key (dim_emp_month_id)
);

alter table dim_emp_month comment '历史表-员工信息月切片(dim_emp_month)';

/*==============================================================*/
/* Index: ind_ym_eId                                            */
/*==============================================================*/
create index ind_ym_eId on dim_emp_month
(
   emp_id,
   "year_month"
);

/*==============================================================*/
/* Index: ind_ym_org                                            */
/*==============================================================*/
create index ind_ym_org on dim_emp_month
(
   "year_month",
   organization_id
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
/* Table: dim_population                                        */
/*==============================================================*/
create table dim_population
(
   population_id        varchar(32) not null comment '人群范围ID',
   customer_id          varchar(32) comment '客户ID',
   population_key       varchar(20) comment '人群范围编码',
   population_name      varchar(20) comment '人群范围名称',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   show_index       	int(1) comment '排序',
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
   c_id                 varchar(32) comment '标识ID',
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
   "year_month"         int(6) comment '年月',
   primary key (emp_job_change_id)
);

alter table job_change comment '历史表-工作异动表（job_change）';

/*==============================================================*/
/* Index: index_eId                                             */
/*==============================================================*/
create index index_eId on job_change
(
   emp_id
);

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
   "year_month"
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
   "year_month"         int(6) comment '年月',
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
/*==============================================================*/
/* Table: `mup-source`.source_job_change                                            */
/*==============================================================*/
drop table if exists `mup-source`.source_job_change;
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
   "year_month"         int(6) comment '年月',
   primary key (emp_job_change_id)
);

alter table `mup-source`.source_job_change comment '历史表-工作异动表（`mup-source`.source_job_change）';

/*==============================================================*/
/* Index: index_eId                                             */
/*==============================================================*/
create index index_eId on `mup-source`.source_job_change
(
   emp_id
);


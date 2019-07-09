/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:38:02                           */
/*==============================================================*/


drop table if exists `mup-source`.source_code_item;

drop table if exists course_record;

drop table if exists dim_course;

drop table if exists dim_course_type;

drop table if exists dim_organization;

drop table if exists emp_train_experience;

drop table if exists lecturer;

drop table if exists lecturer_course_design;

drop table if exists lecturer_course_speak;

drop table if exists manpower_cost;

drop table if exists train_outlay;

drop table if exists train_plan;

drop table if exists train_satfac;

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
/* Table: dim_course                                            */
/*==============================================================*/
create table dim_course
(
   course_id            varchar(32) not null comment '课程维ID',
   customer_id          varchar(32) comment '客户ID',
   course_key           varchar(20) comment '课程编号',
   course_name          varchar(20) comment '课程名称',
course_type_id       varchar(32) comment '课程类别ID',show_index       int(1) comment '排序',
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
   course_type_key      char(20) comment '课程类别编号',
   course_type_name     varchar(20) comment '类别名称',
   show_index           tinyint(1) comment '排序',
   primary key (course_type_id)
);

alter table dim_course_type comment '维度表-课程类别维(dim_course_type)';

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
   "year_month"         int(6) comment '年月',
   primary key (manpower_cost_id)
);

alter table manpower_cost comment '业务表-人力成本（manpower_cost）';

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
/* Table: `mup-source`.source_train_outlay                                          */
/*==============================================================*/
drop table if exists `mup-source`.source_train_outlay;
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
/*==============================================================*/
/* Table: `mup-source`.source_train_value                                           */
/*==============================================================*/
drop table if exists `mup-source`.source_train_value;
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
/*==============================================================*/
/* Table: `mup-source`.source_lecturer_course_speak                                 */
/*==============================================================*/
drop table if exists `mup-source`.source_lecturer_course_speak;
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
/*==============================================================*/
/* Table: `mup-source`.source_train_plan                                            */
/*==============================================================*/
drop table if exists `mup-source`.source_train_plan;
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
/*==============================================================*/
/* Table: `mup-source`.source_train_satfac                                          */
/*==============================================================*/
drop table if exists `mup-source`.source_train_satfac;
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
/*==============================================================*/
/* Table: `mup-source`.source_lecturer                                              */
/*==============================================================*/
drop table if exists `mup-source`.source_lecturer;
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
/*==============================================================*/
/* Table: course_record                                         */
/*==============================================================*/
drop table if exists `mup-source`.source_course_record;
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

/*==============================================================*/
/* Table: emp_train_experience                                  */
/*==============================================================*/
drop table if exists `mup-source`.source_emp_train_experience;
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


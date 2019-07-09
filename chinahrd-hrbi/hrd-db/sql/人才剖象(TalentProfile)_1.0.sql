/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:30:52                           */
/*==============================================================*/


drop table if exists 360_report;

drop table if exists 360_time;



drop table if exists dim_change_type;

drop table if exists dim_performance;

-- drop index un_cId on emp_bonus_penalty;

drop table if exists emp_bonus_penalty;

drop table if exists emp_contact_person;

-- drop index un_cId on emp_edu;

-- drop index ind_name on emp_edu;

-- drop index ind_eId on emp_edu;

drop table if exists emp_edu;

drop table if exists emp_family;

drop table if exists emp_past_resume;

drop table if exists emp_prof_title;

drop table if exists emp_train_experience;

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

-- drop index index_ym on performance_change;

-- drop index index_perId_ym_performance_change on performance_change;

-- drop index index_perId on performance_change;

-- drop index index_orgId on performance_change;

-- drop index index_eKey on performance_change;

-- drop index index_customerId on performance_change;

-- drop index index_eId on performance_change;

drop table if exists performance_change;

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
/* Table: emp_bonus_penalty                                     */
/*==============================================================*/
create table emp_bonus_penalty
(
   emp_bonus_penalty_id varchar(32) not null comment '奖惩信息ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_bonus_penalty_name varchar(20) comment '奖惩信息名称',
   type                 int(1) comment '奖惩类型（1：奖，0：惩）',
   grant_unit           varchar(32) comment '授予单位',
   witness_name         varchar(32) comment '证明人',
   bonus_penalty_date   datetime comment '奖惩时间',
   c_id                 varchar(32) comment '标识ID',
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
/* Table: emp_contact_person                                    */
/*==============================================================*/
create table emp_contact_person
(
   emp_contact_person_id varchar(32) not null comment '联系人ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_contact_person_name varchar(20) comment '联系人名称',
   tel_phone            varchar(11) comment '联系电话',
   "call"               varchar(10) comment '称呼（配偶、子女、朋友、同事）',
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
/* Table: emp_family                                            */
/*==============================================================*/
create table emp_family
(
   emp_family_id        varchar(32) not null comment '家族ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_family_name      varchar(20) comment '家属名称',
   "call"               varchar(6) comment '称呼(父、母、子、女)',
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
   "year_month"         int(6) comment '年月',
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
   "year_month"
);

/*==============================================================*/
/* Index: index_ym                                              */
/*==============================================================*/
create index index_ym on performance_change
(
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


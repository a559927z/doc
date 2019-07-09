/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:38:38                           */
/*==============================================================*/


-- drop index index_abKey on dim_ability;

-- drop index index_abId on dim_ability;

drop table if exists dim_ability;

-- drop index ind_ym_org on dim_emp_month;

-- drop index ind_ym_eId on dim_emp_month;

drop table if exists dim_emp_month;

drop table if exists dim_organization;

drop table if exists dim_performance;

-- drop index index_posId on dim_position;

-- drop index un_cId on dim_position;

drop table if exists dim_position;

-- drop index un_cid on dim_quality;

-- drop index idx_QId on dim_quality;

drop table if exists dim_quality;

drop table if exists emp_past_resume;

-- drop index un_cid on emp_pq_relation;

-- drop index ind_mId_ym on emp_pq_relation;

-- drop index ind_date_eId on emp_pq_relation;

drop table if exists emp_pq_relation;

drop table if exists emp_train_experience;

drop table if exists history_dim_organization_month;

-- drop index un_cid on matching_soure;

drop table if exists matching_soure;

-- drop index index_ym on performance_change;

-- drop index index_perId_ym_performance_change on performance_change;

-- drop index index_perId on performance_change;

-- drop index index_orgId on performance_change;

-- drop index index_eKey on performance_change;

-- drop index index_customerId on performance_change;

-- drop index index_eId on performance_change;

drop table if exists performance_change;

-- drop index un_cid on position_quality;

-- drop index ind_pId_qId on position_quality;

drop table if exists position_quality;

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
   address              varchar(60) comment '住址',
   contract_unit        varchar(32) comment '合同单位',
   contract             varchar(20) comment '合同性质',
   work_place_id        varchar(32) comment '工作地点ID',
   work_place           varchar(60) comment '工作地点',
   is_regular           int(1) comment '是否正职（1 主岗位  0 负岗位）',
   entry_date           datetime comment '入职日期',
   run_off_date         datetime not null comment '流失日期',
   `year_month`         int(6) comment '年月',
   primary key (dim_emp_month_id)
);

alter table dim_emp_month comment '历史表-员工信息月切片(dim_emp_month)';

/*==============================================================*/
/* Index: ind_ym_eId                                            */
/*==============================================================*/
create index ind_ym_eId on dim_emp_month
(
   emp_id,
   `year_month`
);

/*==============================================================*/
/* Index: ind_ym_org                                            */
/*==============================================================*/
create index ind_ym_org on dim_emp_month
(
   `year_month`,
   organization_id
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
/* Table: dim_quality                                           */
/*==============================================================*/
create table dim_quality
(
   quality_id           varchar(32) not null comment '能力素质ID',
   customer_id          varchar(32) comment '客户ID',
   vocabulary           varchar(10) comment '能力素质词条',
   note                 varchar(80) comment '能力素质定义',
   show_index       	int(1) comment '排序',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
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
/* Table: emp_pq_relation                                       */
/*==============================================================*/
create table emp_pq_relation
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
   c_id                 varchar(32) comment '标识ID',
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
/* Table: matching_soure                                        */
/*==============================================================*/
create table matching_soure
(
   matching_soure_id    varchar(32) not null comment '分数映射ID',
   customer_id          varchar(32) comment '客户ID',
   v1                   varchar(10) comment '字符',
   show_index           tinyint(1) comment '排名',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (matching_soure_id)
);

alter table matching_soure comment '匹配表-分数映射（matching_soure）';

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create unique index un_cid on matching_soure
(
   c_id
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

drop table if exists `mup-source`.source_position_quality;
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

drop table if exists `mup-source`.source_emp_pq_relation;
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

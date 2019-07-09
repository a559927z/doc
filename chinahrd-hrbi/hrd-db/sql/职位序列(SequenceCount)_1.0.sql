/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:42:03                           */
/*==============================================================*/


-- drop index index_abKey on dim_ability;

-- drop index index_abId on dim_ability;

drop table if exists dim_ability;

drop table if exists dim_ability_lv;

drop table if exists dim_job_title;

-- drop index index_seqId on dim_sequence;

drop table if exists dim_sequence;

drop table if exists dim_sequence_sub;

-- drop index index_y on job_relation;

-- drop index index_seqSubId on job_relation;

-- drop index index_seqId on job_relation;

-- drop index index_rN on job_relation;

-- drop index index_abLvId on job_relation;

-- drop index index_abId on job_relation;

drop table if exists job_relation;

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
/* Table: dim_ability_lv                                        */
/*==============================================================*/
create table dim_ability_lv
(
   ability_lv_id        varchar(32) not null comment '职级ID',
   customer_id          varchar(32) comment '客户ID',
   ability_lv_key       varchar(20) comment '职级编号',
   ability_lv_name      varchar(60) comment '职级名称',
   curt_name            varchar(1) comment '短名',
   show_index           int(3) comment '排序',
   primary key (ability_lv_id)
);

alter table dim_ability_lv comment '维度表-职级维（dim_ability_lv）';

/*==============================================================*/
/* Table: dim_job_title                                         */
/*==============================================================*/
create table dim_job_title
(
   job_title_id         varchar(32) not null comment '职衔ID',
   customer_id          varchar(32) comment '客户ID',
   job_title_key        varchar(20) not null comment '职衔编号',
   job_title_name       varchar(20) comment '职衔名称',
   curt_name            varchar(1) comment '短名',
   show_index           int(3) comment '排序',
   primary key (job_title_id)
);

alter table dim_job_title comment '维度表-职衔（dim_job_title）';

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
/* Table: dim_sequence_sub                                      */
/*==============================================================*/
create table dim_sequence_sub
(
   sequence_sub_id      varchar(32) not null comment '子序列ID',
   customer_id          varchar(32) comment '客户ID',
   sequence_id          varchar(32) comment '序列',
   sequence_sub_key     varchar(20) comment '子序列编号',
   sequence_sub_name    varchar(60) comment '子序列名称',
   curt_name            varchar(1) comment '短名',
   show_index           int(3) comment '排序',
   primary key (sequence_sub_id)
);

alter table dim_sequence_sub comment '维度表-子序列维(dim_sequence_sub)';

/*==============================================================*/
/* Table: job_relation                                          */
/*==============================================================*/
create table job_relation
(
   job_relation_id      varchar(32) not null comment '职位序列ID',
   customer_id          varchar(32) comment '客户ID',
   sequence_sub_id      varchar(32) comment '子序列ID',
   sequence_id          varchar(32) comment '主序列ID',
   ability_id           varchar(32) comment '能力层级ID',
   job_title_id         varchar(32) comment '职衔ID',
   ability_lv_id        varchar(32) comment '职级ID',
   year                 int(4) comment '年',
   type                 int(1) comment '类型（0：默认名称。1：特定名称）',
   rank_name            varchar(5) comment '主子序列层级职级',
   show_index           int(4) comment '排序',
   refresh              datetime comment '更新日期',
   primary key (job_relation_id)
);

alter table job_relation comment '关系表-职位序列关系（job_relation）';

/*==============================================================*/
/* Index: index_abId                                            */
/*==============================================================*/
create index index_abId on job_relation
(
   ability_id
);

/*==============================================================*/
/* Index: index_abLvId                                          */
/*==============================================================*/
create index index_abLvId on job_relation
(
   ability_lv_id
);

/*==============================================================*/
/* Index: index_rN                                              */
/*==============================================================*/
create index index_rN on job_relation
(
   rank_name
);

/*==============================================================*/
/* Index: index_seqId                                           */
/*==============================================================*/
create index index_seqId on job_relation
(
   sequence_id
);

/*==============================================================*/
/* Index: index_seqSubId                                        */
/*==============================================================*/
create index index_seqSubId on job_relation
(
   sequence_sub_id
);

/*==============================================================*/
/* Index: index_y                                               */
/*==============================================================*/
create index index_y on job_relation
(
   year
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

drop table if exists `mup-source`.source_job_relation;
create table `mup-source`.source_job_relation
(
   job_relation_id      varchar(32) not null comment '职位序列ID',
   customer_id          varchar(32) comment '客户ID',
   sequence_sub_id      varchar(32) comment '子序列ID',
   sequence_id          varchar(32) comment '主序列ID',
   ability_id           varchar(32) comment '能力层级ID',
   job_title_id         varchar(32) comment '职衔ID',
   ability_lv_id        varchar(32) comment '职级ID',
   year                 int(4) comment '年',
   type                 int(1) comment '类型（0：默认名称。1：特定名称）',
   rank_name            varchar(5) comment '主子序列层级职级',
   show_index           int(4) comment '排序',
   refresh              datetime comment '更新日期',
   primary key (job_relation_id)
);

alter table `mup-source`.source_job_relation comment '关系表-职位序列关系（`mup-source`.source_job_relation）';

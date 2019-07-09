/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:39:24                           */
/*==============================================================*/


-- drop index un_cid on budget_position_number;

drop table if exists budget_position_number;

drop table if exists `mup-source`.source_code_item;

-- drop index un_cid on dim_channel;

drop table if exists dim_channel;

-- drop index un_cid on dim_dismission_week;

drop table if exists dim_dismission_week;

drop table if exists dim_change_type;

drop table if exists dim_organization;

drop table if exists dim_performance;

-- drop index index_posId on dim_position;

-- drop index un_cId on dim_position;

drop table if exists dim_position;

-- drop index un_cid on dim_quality;

-- drop index idx_QId on dim_quality;

drop table if exists dim_quality;

-- drop index un_cId on emp_edu;

-- drop index ind_name on emp_edu;

-- drop index ind_eId on emp_edu;

drop table if exists emp_edu;

-- drop index un_cid on emp_pq_relation;

-- drop index ind_mId_ym on emp_pq_relation;

-- drop index ind_date_eId on emp_pq_relation;

drop table if exists emp_pq_relation;

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

-- drop index ind_eId on key_talent;

drop table if exists key_talent;

-- drop index ind_kId on key_talent_tags;

drop table if exists key_talent_tags;

drop table if exists matching_school;

-- drop index un_cid on matching_soure;

drop table if exists matching_soure;

drop table if exists out_talent;

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

-- drop index un_cid on recruit_channel;

drop table if exists recruit_channel;

drop table if exists recruit_public;

-- drop index un_cid on recruit_result;

drop table if exists recruit_result;

-- drop index ind_PosY on recruit_salary_statistics;

drop table if exists recruit_salary_statistics;

-- drop index un_cid on recruit_value;

drop table if exists recruit_value;

drop table if exists recruitment_process;

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
/* Table: budget_position_number                                */
/*==============================================================*/
create table budget_position_number
(
   budget_position_number_id varchar(32) not null comment '岗位编制数ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   position_id          varchar(32) comment '岗位ID',
   number               int(4) comment '编制数',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (budget_position_number_id)
);

alter table budget_position_number comment '业务表-岗位编制数（budget_position_number）';

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create index un_cid on budget_position_number
(
   c_id
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
/* Table: dim_channel                                           */
/*==============================================================*/
create table dim_channel
(
   channel_id           varchar(32) not null comment '应聘渠道ID',
   customer_id          varchar(32) comment '客户ID',
   channel_key          varchar(20) comment '招聘渠道编号',
   channel_name         varchar(20) comment '招聘渠道名称',
   show_index           tinyint(3) comment '排序',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (channel_id)
);

alter table dim_channel comment '维度表-招聘渠道维（dim_channel）';

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create index un_cid on dim_channel
(
   c_id
);

/*==============================================================*/
/* Table: dim_dismission_week                                   */
/*==============================================================*/
create table dim_dismission_week
(
   dismission_week_id   varchar(32) not null comment '离职周期范围维ID',
   customer_id          varchar(32) comment '客户ID',
   name                 varchar(10) comment '周期名称',
   days                 tinyint(4) comment '天数',
   type                 tinyint(1) comment '类型',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   show_index       	int(1) comment '排序',
   primary key (dismission_week_id)
);

alter table dim_dismission_week comment '维度表-离职周期范围维（dim_dismission_week）';

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create index un_cid on dim_dismission_week
(
   c_id
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
   "year_month"         int(6) comment '年月',
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
   "year_month"
);

/*==============================================================*/
/* Index: ind_mId_ym                                            */
/*==============================================================*/
create index ind_mId_ym on emp_pq_relation
(
   matching_soure_id,
   "year_month"
);

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create unique index un_cid on emp_pq_relation
(
   c_id
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
/* Table: key_talent                                            */
/*==============================================================*/
create table key_talent
(
   key_talent_id        varchar(32) not null comment '关键人才ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   key_talent_type_id   varchar(32) comment '关键人才',
   is_sychron           int(1) comment '是否同步数据',
   is_delete            int(1) comment '是否删除',
   note                 varchar(500) comment '备注',
   create_emp_id        varchar(32) comment '创建人',
   create_time          datatime comment '创建时间',
   modity_encourage_emp_id varchar(32) comment '修改激励要素员工ID',
   refresh              datetime comment '更新时间',
   refresh_tag1         datetime comment '优势标签更新时间',
   refresh_tag2         datetime comment '短板标签更新时间',
   refresh_log          datetime comment '日志更新时间',
   refresh_encourage    datetime comment '激励要素更新时间',
   primary key (key_talent_id)
);

alter table key_talent comment '业务表-关键人才库(key_talent)';

/*==============================================================*/
/* Index: ind_eId                                               */
/*==============================================================*/
create index ind_eId on key_talent
(
   emp_id
);

/*==============================================================*/
/* Table: key_talent_tags                                       */
/*==============================================================*/
create table key_talent_tags
(
   key_talent_tags_id   varchar(32) not null comment '关键人才标签ID',
   customer_id          varchar(32) comment '客户ID',
   type                 int(1) comment '标签类型（1:优势,2:短板）',
   content              varchar(10) comment '标签内容',
   key_talent_id        varchar(32) comment '关键人才ID',
   create_emp_id        varchar(32) comment '操作员工ID',
   create_time          datetime comment '创建时间',
   primary key (key_talent_tags_id)
);

alter table key_talent_tags comment '业务表-关键人才标签表(key_talent_tags)';

/*==============================================================*/
/* Index: ind_kId                                               */
/*==============================================================*/
create index ind_kId on key_talent_tags
(
   key_talent_tags_id
);

/*==============================================================*/
/* Table: matching_school                                       */
/*==============================================================*/
create table matching_school
(
   matching_school_id   varchar(32) not null comment '学校ID',
   customer_id          varchar(32) comment '客户ID',
   name                 varchar(50) comment '学校名称',
   school_type          varchar(10) comment '院校类型',
   level                int(1) comment '等级',
   primary key (matching_school_id)
);

alter table matching_school comment '匹配表-学校表(matching_school)';

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
/* Table: out_talent                                            */
/*==============================================================*/
create table out_talent
(
   out_talent_id        varchar(32) not null comment '外部人才ID',
   customer_id          varchar(32) comment '客户ID',
   user_name_ch         varchar(5) comment '中文名',
   user_name            varchar(20) comment '英文名',
   email                text comment '邮箱',
   age                  int(3) comment '年龄',
   sex                  char(3) comment '姓别',
   degree_id            varchar(32) comment '学历ID',
   url                  text comment '简历url',
   school               varchar(30) comment '毕业院校',
   refresh              datetime comment '更新时间',
   img_path             varchar(500) comment '图片路径',
   tag                  varchar(100) comment '标签',
   c_id                 varchar(32) comment '标识ID',
   primary key (out_talent_id)
);

alter table out_talent comment '关系表-外部人才库（out_talent）';

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
/* Table: recruit_channel                                       */
/*==============================================================*/
create table recruit_channel
(
   recruit_channel_id   varchar(32) not null comment '应聘渠道ID',
   customer_id          varchar(32) comment '客户ID',
   channel_id           varchar(32) comment '渠道ID',
   position_id          varchar(32) comment '职位ID',
   employ_num           int(4) comment '已招人数',
   outlay               double(10,2) comment '花费（单位：元）',
   start_date           datetime comment '招聘开始时间',
   end_date             datetime comment '招聘结束时间',
   days                 int(4) comment '天数',
   recruit_public_id    varchar(32) comment '招聘发布ID',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (recruit_channel_id)
);

alter table recruit_channel comment '业务表-招聘渠道（recruit_channel）';

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create unique index un_cid on recruit_channel
(
   c_id
);

/*==============================================================*/
/* Table: recruit_public                                        */
/*==============================================================*/
create table recruit_public
(
   recruit_public_id    varchar(32) not null comment '招聘发布ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '发布机构ID',
   position_id          varchar(32) comment '招聘岗位ID',
   employ_num           int(4) comment '已招人数（过）',
   plan_num             int(4) comment '计划人数',
   start_date           datetime comment '招聘开始时间',
   end_date             datetime comment '招聘结束时间',
   days                 int(4) comment '天数',
   resume_num           int(4) comment '简历数（过）',
   interview_num        int(4) comment '面试数（过）',
   offer_num            int(4) comment '通过数（过）',
   entry_num            int(4) comment '入职数（过）',
   is_public            tinyint(1) comment '招聘岗位状态',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (recruit_public_id)
);

alter table recruit_public comment '业务表-招聘发布（recruit_public）';

/*==============================================================*/
/* Table: recruit_result                                        */
/*==============================================================*/
create table recruit_result
(
   recruit_result_id    varchar(32) not null comment '应聘结果ID',
   customer_id          varchar(32) comment '客户ID',
   recruit_result_key   varchar(20) comment '应聘者编号',
   username             varchar(6) comment '姓名',
   sex                  char(3) comment '性别',
   age                  double(5,2) comment '年龄',
   degree_id            varchar(32) comment '学历ID',
   major                varchar(30) comment '专业',
   school               varchar(30) comment '毕业院校',
   is_resume            tinyint(1) comment '是否投简',
   is_interview         tinyint(1) comment '是否面试',
   is_offer             tinyint(1) comment '是否通过',
   is_entry             tinyint(1) comment '是否入职',
   url                  text comment '简历url',
   recruit_public_id    varchar(32) comment '招聘发布ID',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (recruit_result_id)
);

alter table recruit_result comment '业务表-招聘结果（recruit_result）';

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create unique index un_cid on recruit_result
(
   c_id
);

/*==============================================================*/
/* Table: recruit_salary_statistics                             */
/*==============================================================*/
create table recruit_salary_statistics
(
   recruit_salary_statistics_id varchar(32) not null comment '招聘岗位薪酬ID',
   customer_id          varchar(32) comment '客户ID',
   positition_id        varchar(32) comment '岗位ID',
   positition_name      varbinary(60) comment '岗位名称',
   rank_name            varchar(5) comment '主子序列层级职级(短名)',
   avg_sal              double(10,2) comment '平均工资（单位：元）',
   emp_total            int(5) comment '员工总数',
   year_months          int(10) comment '年月',
   primary key (recruit_salary_statistics_id)
);

alter table recruit_salary_statistics comment '招聘岗位薪酬(recruit_salary_statistics
)';

/*==============================================================*/
/* Index: ind_PosY                                              */
/*==============================================================*/
create index ind_PosY on recruit_salary_statistics
(
   year_months,
   positition_id
);

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

/*==============================================================*/
/* Table: recruitment_process                                   */
/*==============================================================*/
create table recruitment_process
(
   recruitment_process_id varchar(32) not null comment '招聘进程ID',
   customer_id          varchar(32) comment '客户ID',
   publice_job_num      int(3) comment '发布职位数',
   resume_num           int(3) comment '简历数',
   accept_num           int(3) comment '应聘数',
   offer_num            int(3) comment 'offer数',
   organization_id      varchar(32) comment '机构ID',
   primary key (recruitment_process_id)
);

alter table recruitment_process comment '业务表-招聘进程（recruitment_process）';

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

DROP TABLE IF EXISTS `mup-source`.source_recruit_value;
create table `mup-source`.source_recruit_value
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

alter table `mup-source`.source_recruit_value comment '业务表-招聘年度费用（年）（`mup-source`.source_recruit_value）';
create unique index un_cid on `mup-source`.source_recruit_value
(
   c_id
);
/*==============================================================*/
/* Table: mup-source.out_talent                                            */
/*==============================================================*/
drop table if exists `mup-source`.source_out_talent;
create table `mup-source`.source_out_talent
(
   out_talent_id        varchar(32) not null comment '外部人才ID',
   customer_id          varchar(32) comment '客户ID',
   user_name_ch         varchar(5) comment '中文名',
   user_name            varchar(20) comment '英文名',
   email                text comment '邮箱',
   age                  int(3) comment '年龄',
   sex                  char(3) comment '姓别',
   degree_id            varchar(32) comment '学历ID',
   url                  text comment '简历url',
   school               varchar(30) comment '毕业院校',
   refresh              datetime comment '更新时间',
   img_path             varchar(500) comment '图片路径',
   tag                  varchar(100) comment '标签',
   c_id                 varchar(32) comment '标识ID',
   primary key (out_talent_id)
);

alter table `mup-source`.source_out_talent comment '关系表-外部人才库（`mup-source`.source_out_talent）';
/*==============================================================*/
/* Table: `mup-source`.source_recruit_public                                        */
/*==============================================================*/
drop table if exists `mup-source`.source_recruit_public;
create table `mup-source`.source_recruit_public
(
   recruit_public_id    varchar(32) not null comment '招聘发布ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '发布机构ID',
   position_id          varchar(32) comment '招聘岗位ID',
   employ_num           int(4) comment '已招人数（过）',
   plan_num             int(4) comment '计划人数',
   start_date           datetime comment '招聘开始时间',
   end_date             datetime comment '招聘结束时间',
   days                 int(4) comment '天数',
   resume_num           int(4) comment '简历数（过）',
   interview_num        int(4) comment '面试数（过）',
   offer_num            int(4) comment '通过数（过）',
   entry_num            int(4) comment '入职数（过）',
   is_public            tinyint(1) comment '招聘岗位状态',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   primary key (recruit_public_id)
);

alter table `mup-source`.source_recruit_public comment '业务表-招聘发布（`mup-source`.source_recruit_public）';
/*==============================================================*/
/* Table: `mup-source`.source_recruit_result                                        */
/*==============================================================*/
drop table if exists `mup-source`.source_recruit_result;
create table `mup-source`.source_recruit_result
(
   recruit_result_id    varchar(32) not null comment '应聘结果ID',
   customer_id          varchar(32) comment '客户ID',
   recruit_result_key   varchar(20) comment '应聘者编号',
   username             varchar(6) comment '姓名',
   sex                  char(3) comment '性别',
   age                  double(5,2) comment '年龄',
   degree_id            varchar(32) comment '学历ID',
   major                varchar(30) comment '专业',
   school               varchar(30) comment '毕业院校',
   is_resume            tinyint(1) comment '是否投简',
   is_interview         tinyint(1) comment '是否面试',
   is_offer             tinyint(1) comment '是否通过',
   is_entry             tinyint(1) comment '是否入职',
   url                  text comment '简历url',
   recruit_public_id    varchar(32) comment '招聘发布ID',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (recruit_result_id)
);

alter table `mup-source`.source_recruit_result comment '业务表-招聘结果（`mup-source`.source_recruit_result）';

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create unique index un_cid on `mup-source`.recruit_result
(
   c_id
);
/*==============================================================*/
/* Table: recruit_channel                                       */
/*==============================================================*/
drop table if exists `mup-source`.source_recruit_channel;
create table `mup-source`.source_recruit_channel
(
   recruit_channel_id   varchar(32) not null comment '应聘渠道ID',
   customer_id          varchar(32) comment '客户ID',
   channel_id           varchar(32) comment '渠道ID',
   position_id          varchar(32) comment '职位ID',
   employ_num           int(4) comment '已招人数',
   outlay               double(10,2) comment '花费（单位：元）',
   start_date           datetime comment '招聘开始时间',
   end_date             datetime comment '招聘结束时间',
   days                 int(4) comment '天数',
   recruit_public_id    varchar(32) comment '招聘发布ID',
   year                 int(4) comment '年',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (recruit_channel_id)
);

alter table `mup-source`.source_recruit_channel comment '业务表-招聘渠道（recruit_channel）';

/*==============================================================*/
/* Index: un_cid                                                */
/*==============================================================*/
create unique index un_cid on `mup-source`.source_recruit_channel
(
   c_id
);
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


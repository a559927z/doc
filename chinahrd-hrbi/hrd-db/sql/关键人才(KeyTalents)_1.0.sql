/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:35:41                           */
/*==============================================================*/


-- -- drop index un_cId on dim_encourages;

drop table if exists dim_encourages;

drop table if exists dim_key_talent_type;

drop table if exists dim_organization_type;

-- -- drop index index_eId on dimission_risk;

drop table if exists dimission_risk;

-- -- drop index index_day_orgId on history_dim_organization;

-- -- drop index ind_cId_dim_organization on history_dim_organization;

-- -- drop index index_orgId on history_dim_organization;

-- -- drop index index_fp on history_dim_organization;

drop table if exists history_dim_organization;

-- -- drop index ind_eId on key_talent;

drop table if exists key_talent;

drop table if exists key_talent_encourages;

drop table if exists key_talent_focuses;

drop table if exists key_talent_logs;

-- -- drop index ind_kId on key_talent_tags;

drop table if exists key_talent_tags;

drop table if exists key_talent_tags_auto;

drop table if exists key_talent_tags_ed;

-- -- drop index ind_Uch on v_dim_emp;

-- -- drop index ind_rn_v_dim_emp on v_dim_emp;

-- -- drop index ind_perId_v_dim_emp on v_dim_emp;

-- -- drop index ind_Org on v_dim_emp;

-- -- drop index ind_mark on v_dim_emp;

-- -- drop index ind_eId_unc_rk_VDE on v_dim_emp;

-- -- drop index indx_pos on v_dim_emp;

-- -- drop index indx_eORG on v_dim_emp;

-- -- drop index index_eKey on v_dim_emp;

-- -- drop index index_eId on v_dim_emp;

-- -- drop index index_ed on v_dim_emp;

drop table if exists v_dim_emp;

/*==============================================================*/
/* Table: dim_encourages                                        */
/*==============================================================*/
create table dim_encourages
(
   encourages_id        varchar(32) not null comment '激励要素ID',
   customer_id          varchar(32) comment '客户ID',
   encourages_key       varchar(20) comment '激励要素编码',
   content              varchar(20) comment '激励要素内容',
   c_id                 varchar(32) comment '标识ID',
   show_index       	int(1) comment '排序',
   primary key (encourages_id)
);

alter table dim_encourages comment '维度表-激励要素表(dim_encourages)';

/*==============================================================*/
/* Index: un_cId                                                */
/*==============================================================*/
create unique index un_cId on dim_encourages
(
   c_id
);

/*==============================================================*/
/* Table: dim_key_talent_type                                   */
/*==============================================================*/
create table dim_key_talent_type
(
   key_talent_type_id   varchar(32) not null comment '关键人才',
   customer_id          varchar(32) comment '客户ID',
   key_talent_type_key  varchar(20) comment '关键人才类型编号',
   key_talent_type_name varchar(60) comment '关键人才名称',
   show_idnex           int(1) comment '排序',
   primary key (key_talent_type_id)
);

alter table dim_key_talent_type comment '维度表-关键人才库维(dim_key_talent_type)';

/*==============================================================*/
/* Table: dim_organization_type                                 */
/*==============================================================*/
create table dim_organization_type
(
   organization_type_id varchar(32) not null comment '机构级别ID',
   customer_id          varchar(32) comment '客户ID',
   organization_type_key varchar(20) comment '机构级别编码',
   organization_type_level int(2) comment '机构级别层级',
   organization_type_name varchar(20) comment '机构级别名称',
   show_index       	int(1) comment '排序',
   primary key (organization_type_id)
);

alter table dim_organization_type comment '维度表-机构级别维（dim_organization_type）';

/*==============================================================*/
/* Table: dimission_risk                                        */
/*==============================================================*/
create table dimission_risk
(
   dimission_risk_id    varchar(32) not null comment '离职风险评估ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   note                 longtext comment '备注',
   risk_date            datetime comment '评估日期',
   risk_flag            int(1) comment '风险标识',
   is_last              int(1) comment '是否最近一次的离职风险',
   primary key (dimission_risk_id)
);

alter table dimission_risk comment '业务表-离职风险评估(dimission_risk)';

/*==============================================================*/
/* Index: index_eId                                             */
/*==============================================================*/
create index index_eId on dimission_risk
(
   emp_id
);

/*==============================================================*/
/* Table: history_dim_organization                              */
/*==============================================================*/
create table history_dim_organization
(
   history_dim_organization_id varchar(32) not null comment '机构纬度ID',
   organization_id      varchar(32) comment '机构ID',
   customer_id          varchar(32) comment '客户ID',
   organization_type_id varchar(32) comment '机构级别ID',
   organization_key     varchar(20) comment '机构编码',
   organization_parent_id varchar(32) comment '上级机构',
   organization_name    varchar(20) comment '机构名称',
   city_id              varchar(32) comment '城市ID',
   business_unit_id     varchar(32) comment '业务单位ID',
   organization_company_id varchar(32) comment '分公司ID',
   organization_parent_key varchar(20) comment '父机构编码',
   organization_name_full varchar(50) comment '机构全称',
   note                 varchar(200) comment '描述',
   is_single            int(1) comment '是否独立核算',
   full_path            varchar(200) comment '全路径',
   has_children         tinyint(1) comment '是否有子节点',
   depth                int(4) comment '深度',
   profession_id        varchar(32) comment '行业ID',
   refresh_date         datetime comment '更新时间',
   days                 date comment '日期',
   show_index           int(3) comment '排序',
   c_id                 varchar(32) comment '标志ID',
   primary key (history_dim_organization_id)
);

alter table history_dim_organization comment '历史表-机构维（history_dim_organization）';

/*==============================================================*/
/* Index: index_fp                                              */
/*==============================================================*/
create index index_fp on history_dim_organization
(
   full_path
);

/*==============================================================*/
/* Index: index_orgId                                           */
/*==============================================================*/
create index index_orgId on history_dim_organization
(
   organization_id
);

/*==============================================================*/
/* Index: ind_cId_dim_organization                              */
/*==============================================================*/
create index ind_cId_dim_organization on history_dim_organization
(
   city_id
);

/*==============================================================*/
/* Index: index_day_orgId                                       */
/*==============================================================*/
create index index_day_orgId on history_dim_organization
(
   organization_id,
   days
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
   create_time          datetime comment '创建时间',
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
/* Table: key_talent_encourages                                 */
/*==============================================================*/
create table key_talent_encourages
(
   key_talent_encourages_id varchar(32) not null comment '关键人才激励要素ID',
   customer_id          varchar(32) comment '客户ID',
   key_talent_id        varchar(32) comment '关键人才ID',
   encourages_id        varchar(32) comment '激励要素ID',
   create_emp_id        varchar(32) comment '操作员工ID',
   create_time          datetime comment '创建时间',
   primary key (key_talent_encourages_id)
);

alter table key_talent_encourages comment '业务表-关键人才激励要素表(key_talent_encourages)';

/*==============================================================*/
/* Table: key_talent_focuses                                    */
/*==============================================================*/
create table key_talent_focuses
(
   key_talent_focuses_id varchar(32) not null comment '关注ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   key_talent_id        varchar(32) comment '关键人才ID',
   create_time          datetime comment '创建时间',
   primary key (key_talent_focuses_id)
);

alter table key_talent_focuses comment '业务表-关键人才关注表(key_talent_focuses)';

/*==============================================================*/
/* Table: key_talent_logs                                       */
/*==============================================================*/
create table key_talent_logs
(
   key_talent_logs_id   varchar(32) not null comment '关键人才日志ID',
   customer_id          varchar(32) comment '客户ID',
   content              longtext comment '日志内容',
   key_talent_id        varchar(32) comment '关键人才ID',
   create_emp_id        varchar(32) comment '操作员工ID',
   create_time          datetime comment '创建时间',
   refresh              datetime comment '更新时间',
   primary key (key_talent_logs_id)
);

alter table key_talent_logs comment '业务表-关键人才日志表(key_talent_logs)';

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
/* Table: key_talent_tags_auto                                  */
/*==============================================================*/
create table key_talent_tags_auto
(
   key_talent_tags_auto_id varchar(32) not null comment '关键人才自动标签ID',
   customer_id          varchar(32) comment '客户ID',
   content              varchar(10) comment '标签内容',
   key_talent_id        varchar(32) comment '关键人才ID',
   create_time          datetime comment '创建时间',
   primary key (key_talent_tags_auto_id)
);

alter table key_talent_tags_auto comment '业务表-关键人才自动标签表(key_talent_tags_auto)';

/*==============================================================*/
/* Table: key_talent_tags_ed                                    */
/*==============================================================*/
create table key_talent_tags_ed
(
   key_talent_tags_ed_id varchar(32) not null comment '关键人才标签历史ID',
   customer_id          varchar(32) comment '客户ID',
   type                 int(1) comment '标签类型（1：优势，2：短板）',
   content              varchar(10) comment '标签内容',
   key_talent_id        varchar(32) comment '关键人才ID',
   opt_username         varchar(32) comment '操作员工名称',
   action_type          int(1) comment '动作类型（1：新增，2：删除）',
   create_time          datetime comment '创建时间',
   primary key (key_talent_tags_ed_id)
);

alter table key_talent_tags_ed comment '历史表-关键人才标签历史表(key_talent_tags_ed)';

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
   position_name varchar(60) comment '岗位名称',
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
/* Table: `mup-source`.source_v_dim_emp                                             */
/*==============================================================*/
drop table if exists `mup-source`.source_v_dim_emp;
create table `mup-source`.source_v_dim_emp
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
   position_name varchar(60) comment '岗位名称',
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

alter table `mup-source`.source_v_dim_emp comment '维度表-员工信息维(`mup-source`.source_v_dim_emp)';


/*==============================================================*/
/* Index: index_eId                                             */
/*==============================================================*/
create index index_eId on `mup-source`.source_v_dim_emp
(
   emp_id
);

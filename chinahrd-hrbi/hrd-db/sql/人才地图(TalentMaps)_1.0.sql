/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:31:27                           */
/*==============================================================*/


-- drop index ind_Org_y on ability_change;

-- drop index ind_eId_y on ability_change;

-- drop index ind_abId on ability_change;

drop table if exists ability_change;

-- drop index index_abKey on dim_ability;

-- drop index index_abId on dim_ability;

drop table if exists dim_ability;

drop table if exists dim_ability_number;

-- drop index un_cId on dim_encourages;

drop table if exists dim_encourages;

drop table if exists dim_organization;

drop table if exists dim_performance;

-- drop index ind_z on dim_z_info;

drop table if exists dim_z_info;

-- drop index index_eId on dimission_risk;

drop table if exists dimission_risk;

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

drop table if exists key_talent_encourages;

-- drop index ind_kId on key_talent_tags;

drop table if exists key_talent_tags;

drop table if exists map_config;

drop table if exists map_management;

drop table if exists map_public;

-- drop index Index_1 on map_talent;

drop table if exists map_talent;

drop table if exists map_talent_info;

drop table if exists map_talent_result;

drop table if exists map_team;

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
   sex                  varchar(3) comment '性别',
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
/* Table: dim_z_info                                            */
/*==============================================================*/
create table dim_z_info
(
   z_id                 varchar(32) not null comment 'z轴ID',
   z_name               varchar(50) comment 'z轴名称',
   customer_id          varchar(32) comment '客户名称',
   show_index           int(1) comment '排序',
   primary key (z_id)
);

alter table dim_z_info comment 'z轴信息(dim_z_info)';

/*==============================================================*/
/* Index: ind_z                                                 */
/*==============================================================*/
create index ind_z on dim_z_info
(
   z_name
);

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
/* Table: map_config                                           */
/*==============================================================*/
create table map_config
(
   map_config_id        varchar(32) not null comment '地图颜色配置ID',
   customer_id          varchar(32) comment '客户ID',
   x_number             int(2) comment 'x轴',
   y_number             int(2) comment 'y轴',
   color               varchar(50) comment '颜色',
   update_time          date comment '更新时间',
   z_name               varchar(100) comment 'z轴名称',
   primary key (map_config_id)
);

alter table map_config comment '人才地图配置表（map_config）';

/*==============================================================*/
/* Table: map_management                                        */
/*==============================================================*/
create table map_management
(
   map_management_id    varchar(32) not null comment '人才地图管理ID',
   customer_id          varchar(32) comment '客户ID',
   top_id               varchar(32) comment '顶级ID',
   status               int(1) comment '状态',
   adjustment_id        varchar(32) comment '调整人ID',
   emp_id               varchar(32) comment '审核人ID',
   starte_date          datetime comment '生成时间',
   motify_date          datetime comment '调整时间',
   release_date         datetime comment '发布时间',
   year_months          int(6) comment '年月',
   primary key (map_management_id)
);

alter table map_management comment '人才地图管理（map_management）';

/*==============================================================*/
/* Table: map_public                                            */
/*==============================================================*/
create table map_public
(
   organization_id      varchar(32) not null comment '顶级ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   full_path            varchar(200) comment '全路径',
   update_time          datetime comment '更新时间',
   "year_month"         int(10) not null comment '年月',
   primary key (organization_id, "year_month")
);

alter table map_public comment '人才地图发布(map_public)';

/*==============================================================*/
/* Table: map_talent                                            */
/*==============================================================*/
create table map_talent
(
   map_talent_id        varchar(32) not null comment '员工人才地图ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   x_axis_id            varchar(32) comment 'x轴ID',
   x_axis_id_af         varchar(32) comment 'x轴ID后',
   x_axis_id_af_r       varchar(32) comment 'x轴ID后R',
   y_axis_id            varchar(32) comment 'y轴ID',
   y_axis_id_af         varchar(32) comment 'y轴ID后',
   y_axis_id_af_r       varchar(32) comment 'y轴ID后R',
   update_time          datetime comment '更新时间',
   is_update            int(1) comment '更新标识',
   modify_id            varchar(32) comment '调整人ID',
   release_id           varchar(32) comment '发布人ID',
   year_months          int(6) comment '年月',
   note                 varchar(500) comment '第一次调整说明',
   note1                varchar(500) comment '第二次调整说明',
   date_time            date comment '人才地图数据抽取时间',
   primary key (map_talent_id)
);

alter table map_talent comment '人才地图（map_talent）';

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on map_talent
(
   emp_id,
   year_months
);

/*==============================================================*/
/* Table: map_talent_info                                       */
/*==============================================================*/
create table map_talent_info
(
   emp_id               varchar(32) not null comment '员工ID',
   user_name_ch         varchar(50) comment '员工中文名',
   customer_id          varchar(32) comment '客户ID',
   full_path            varchar(200) comment '员工机构全路径',
   organization_parent_id varchar(32) comment '父机构ID',
   organization_id      varchar(32) comment '部门ID',
   sequence_id          varchar(32) comment '主序列',
   sequence_sub_id      varchar(32) comment '子序列',
   ability_id           varchar(32) comment '职位层级ID',
   degree_id            varchar(32) comment '学位ID',
   performance_id       varchar(32) comment '绩效ID',
   position_id          varchar(32) comment '职位ID',
   age                  int(3) comment '年龄',
   sex                  varchar(1) comment '性别',
   refresh              datetime comment '更新时间',
   primary key (emp_id)
);

alter table map_talent_info comment '人才地图员工信息(map_talent_info)';

/*==============================================================*/
/* Table: map_talent_result                                     */
/*==============================================================*/
create table map_talent_result
(
   map_talent_id        varchar(32) not null comment '员工人才地图ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   x_axis_id            varchar(32) comment 'x轴ID',
   y_axis_id            varchar(32) comment 'y轴ID',
   update_time          datetime comment '更新时间',
   year_months          int(6) comment '年月',
   note                 varchar(500) comment '调整说明',
   primary key (map_talent_id)
);

alter table map_talent_result comment '人才地图结果（map_talent_result）';

/*==============================================================*/
/* Table: map_team                                              */
/*==============================================================*/
create table map_team
(
   map_team_id          varchar(32) not null comment '自定义团队',
   emp_id               varchar(32) comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   team_name            varchar(50) comment '团队名称',
   requirement          varchar(4000) comment '要求',
   pk_view              int(1) comment '是否团队PK查看',
   create_time          date comment '创建时间',
   primary key (map_team_id)
);

alter table map_team comment '自定义团队（map_team）';

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

drop table if exists `mup-source`.source_ability_change;
create table `mup-source`.source_ability_change
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
   sex                  varchar(3) comment '性别',
   degree_id            varchar(32) comment '学历ID',
   ability_number_id    varchar(32) comment '能力编码',
   update_time          datetime comment '更新时间',
   year_months          int(6) comment '年月',
   primary key (ability_change_id)
);

alter table `mup-source`.source_ability_change comment '员工能力记录(`mup-source`.source_ability_change)';
drop table if exists `mup-source`.source_map_talent_info;
create table `mup-source`.source_map_talent_info
(
   emp_id               varchar(32) not null comment '员工ID',
   user_name_ch         varchar(50) comment '员工中文名',
   customer_id          varchar(32) comment '客户ID',
   full_path            varchar(200) comment '员工机构全路径',
   organization_parent_id varchar(32) comment '父机构ID',
   organization_id      varchar(32) comment '部门ID',
   sequence_id          varchar(32) comment '主序列',
   sequence_sub_id      varchar(32) comment '子序列',
   ability_id           varchar(32) comment '职位层级ID',
   degree_id            varchar(32) comment '学位ID',
   performance_id       varchar(32) comment '绩效ID',
   position_id          varchar(32) comment '职位ID',
   age                  int(3) comment '年龄',
   sex                  varchar(1) comment '性别',
   refresh              datetime comment '更新时间',
   primary key (emp_id)
);

alter table `mup-source`.source_map_talent_info comment '人才地图员工信息(`mup-source`.source_map_talent_info)';
/*==============================================================*/
/* Table: `mup-source`.source_map_config                                           */
/*==============================================================*/

drop table if exists `mup-source`.source_map_config;
create table `mup-source`.source_map_config
(
   map_config_id        varchar(32) not null comment '地图颜色配置ID',
   customer_id          varchar(32) comment '客户ID',
   x_number             int(2) comment 'x轴',
   y_number             int(2) comment 'y轴',
   color               varchar(50) comment '颜色',
   update_time          date comment '更新时间',
   z_name               varchar(100) comment 'z轴名称',
   primary key (map_config_id)
);

alter table `mup-source`.source_map_config comment '人才地图配置表（`mup-source`.source_map_config）';
/*==============================================================*/
/* Table: `mup-source`.source_map_talent_result                                     */
/*==============================================================*/
drop table if exists `mup-source`.source_map_talent_result;
create table `mup-source`.source_map_talent_result
(
   map_talent_id        varchar(32) not null comment '员工人才地图ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   x_axis_id            varchar(32) comment 'x轴ID',
   y_axis_id            varchar(32) comment 'y轴ID',
   update_time          datetime comment '更新时间',
   year_months          int(6) comment '年月',
   note                 varchar(500) comment '调整说明',
   primary key (map_talent_id)
);

alter table `mup-source`.source_map_talent_result comment '人才地图结果（`mup-source`.source_map_talent_result）';

/*==============================================================*/
/* Table: `mup-source`.source_map_management                                        */
/*==============================================================*/
drop table if exists `mup-source`.source_map_management;
create table `mup-source`.source_map_management
(
   map_management_id    varchar(32) not null comment '人才地图管理ID',
   customer_id          varchar(32) comment '客户ID',
   top_id               varchar(32) comment '顶级ID',
   status               int(1) comment '状态',
   adjustment_id        varchar(32) comment '调整人ID',
   emp_id               varchar(32) comment '审核人ID',
   starte_date          datetime comment '生成时间',
   motify_date          datetime comment '调整时间',
   release_date         datetime comment '发布时间',
   year_months          int(6) comment '年月',
   primary key (map_management_id)
);

alter table `mup-source`.source_map_management comment '人才地图管理（`mup-source`.source_map_management）';

/*==============================================================*/
/* Table: `mup-source`.source_map_team                                              */
/*==============================================================*/
drop table if exists `mup-source`.source_map_team;
create table `mup-source`.source_map_team
(
   map_team_id          varchar(32) not null comment '自定义团队',
   emp_id               varchar(32) comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   team_name            varchar(50) comment '团队名称',
   requirement          varchar(4000) comment '要求',
   pk_view              int(1) comment '是否团队PK查看',
   create_time          date comment '创建时间',
   primary key (map_team_id)
);

alter table `mup-source`.source_map_team comment '自定义团队（`mup-source`.source_map_team）';

/*==============================================================*/
/* Table: `mup-source`.source_map_talent                                            */
/*==============================================================*/
drop table if exists `mup-source`.source_map_talent;
create table `mup-source`.source_map_talent
(
   map_talent_id        varchar(32) not null comment '员工人才地图ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   x_axis_id            varchar(32) comment 'x轴ID',
   x_axis_id_af         varchar(32) comment 'x轴ID后',
   x_axis_id_af_r       varchar(32) comment 'x轴ID后R',
   y_axis_id            varchar(32) comment 'y轴ID',
   y_axis_id_af         varchar(32) comment 'y轴ID后',
   y_axis_id_af_r       varchar(32) comment 'y轴ID后R',
   update_time          datetime comment '更新时间',
   is_update            int(1) comment '更新标识',
   modify_id            varchar(32) comment '调整人ID',
   release_id           varchar(32) comment '发布人ID',
   year_months          int(6) comment '年月',
   note                 varchar(500) comment '第一次调整说明',
   note1                varchar(500) comment '第二次调整说明',
   date_time            date comment '人才地图数据抽取时间',
   primary key (map_talent_id)
);

alter table `mup-source`.source_map_talent comment '人才地图（`mup-source`.source_map_talent）';

/*==============================================================*/
/* Table: `mup-source`.source_map_public                                            */
/*==============================================================*/
drop table if exists `mup-source`.source_map_public;
create table `mup-source`.source_map_public
(
   organization_id      varchar(32) not null comment '顶级ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   full_path            varchar(200) comment '全路径',
   update_time          datetime comment '更新时间',
   `year_month`         int(10) not null comment '年月',
   primary key (organization_id, `year_month`)
);

alter table `mup-source`.source_map_public comment '人才地图发布(`mup-source`.source_map_public)';

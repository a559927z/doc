/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:43:48                           */
/*==============================================================*/


drop table if exists `mup-source`.source_code_item;

-- drop index index_abKey on dim_ability;

-- drop index index_abId on dim_ability;

drop table if exists dim_ability;

drop table if exists dim_organization;

drop table if exists dim_project_input_type;

drop table if exists dim_project_type;

-- drop index index_seqId on dim_sequence;

drop table if exists dim_sequence;

drop table if exists project_cost;

drop table if exists project;

drop table if exists project_input_detail;

drop table if exists project_manpower;

drop table if exists project_maxvalue;

drop table if exists project_partake_relation;

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
/* Table: dim_project_input_type                                */
/*==============================================================*/
create table dim_project_input_type
(
   project_input_type_id varchar(32) not null comment '项目投入类型ID',
   customer_id          varchar(32) comment '客户ID',
   project_input_type_name varchar(10) comment '项目投入类型名称',
   show_index           tinyint(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (project_input_type_id)
);

alter table dim_project_input_type comment '维度表-项目投入费用类型维（dim_project_input_type）';

/*==============================================================*/
/* Table: dim_project_type                                      */
/*==============================================================*/
create table dim_project_type
(
   project_type_id      varchar(32) not null comment '项目类型ID',
   customer_id          varchar(32) comment '客户ID',
   project_type_name    varchar(10) comment '项目类型名称',
   show_index           tinyint(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (project_type_id)
);

alter table dim_project_type comment '维度表-项目类型维（dim_project_type）';

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
/* Table: project_cost                                           */
/*==============================================================*/
create table project_cost
(
   projec_cost_id       varchar(32) not null comment '项目费用明细ID',
   customer_id          varchar(32) comment '客户ID',
   input                double(10,5) comment '投入（单位：万元）',
   output               double(10,5) comment '产出（单位：万元）',
   gain                 double(10,5) comment '利润（单位：万元）',
   project_id           varchar(32) comment '项目ID',
   date                 date comment '盘点日期',
   type                 tinyint(1) comment '日期类型(1季、2月、3双月)',
   primary key (projec_cost_id)
);

alter table project_cost comment '业务表-项目费用明细（project_cost）';

/*==============================================================*/
/* Table: project                                               */
/*==============================================================*/
create table project
(
   project_id           varchar(32) not null comment '项目ID',
   customer_id          varchar(32) comment '客户ID',
   project_key          varchar(32) comment '项目编码',
   project_name         varchar(30) comment '项目名称',
   emp_id               varchar(32) comment '负责人',
   organization_id      varchar(32) comment '主导机构',
   project_type_id      varchar(32) comment '项目类型ID',
   project_progress_id  varchar(32) comment '项目进度ID',
   project_parent_id    varchar(32) comment '父项目ID',
   full_path            text comment '全路径',
   has_chilren          tinyint(1) comment '是否有子节点',
   start_date           date comment '项目开始时间',
   end_date             date comment '项目结束时间',
   refresh              date comment '更新日期',
   primary key (project_id)
);

alter table project comment '业务表-项目（project）';

/*==============================================================*/
/* Table: project_input_detail                                  */
/*==============================================================*/
create table project_input_detail
(
   project_input_detail_id varchar(32) not null comment '项目投入明细ID',
   customer_id          varchar(32) comment '客户ID',
   project_id           varchar(32) comment '主导项目ID',
   project_input_type_id varchar(32) comment '投入类型ID',
   outlay               double(12,7) comment '花费（单位：万元）',
   date                 date comment '盘点日期',
   type                 tinyint(1) comment '日期类型(1季、2月、3双月)',
   primary key (project_input_detail_id)
);

alter table project_input_detail comment '关系表-项目投入费用明细（project_input_detail）';

/*==============================================================*/
/* Table: project_manpower                                      */
/*==============================================================*/
create table project_manpower
(
   project_manpower_id  varchar(32) not null comment '项目人力明细ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '参与员工',
   input                double(10,2) comment '人力投入',
   note                 text comment '工作内容',
   project_id           varchar(32) comment '项目ID',
   project_sub_id       varchar(32) comment '所服务子项目',
   date                 date comment '盘点日期',
   type                 tinyint(1) comment '日期类型(1季、2月、3双月)',
   primary key (project_manpower_id)
);

alter table project_manpower comment '业务表-项目人力明细（project_manpower）';

/*==============================================================*/
/* Table: project_maxvalue                                      */
/*==============================================================*/
create table project_maxvalue
(
   project_maxvalue_id  varchar(32) not null comment '项目最大负荷数ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   maxval               int(4) comment '数值',
   total_work_hours     double(5,2) comment '部门工时总数',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
   primary key (project_maxvalue_id)
);

alter table project_maxvalue comment '业务表-项目最大负荷数（project_maxvalue）';

/*==============================================================*/
/* Table: project_partake_relation                              */
/*==============================================================*/
create table project_partake_relation
(
   project_partake_id   varchar(32) not null comment '主导项目参与项目ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '参与机构ID',
   project_id           varchar(32) comment '项目ID',
   refresh              date comment '更新日期',
   primary key (project_partake_id)
);

alter table project_partake_relation comment '关系表-主导项目参与项目关系（project_partake_relation）';

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


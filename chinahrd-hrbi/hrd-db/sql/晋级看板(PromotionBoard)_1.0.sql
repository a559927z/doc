/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:40:23                           */
/*==============================================================*/


drop table if exists dim_organization;

drop table if exists dim_performance;

-- drop index index_y on job_relation;

-- drop index index_seqSubId on job_relation;

-- drop index index_seqId on job_relation;

-- drop index index_rN on job_relation;

-- drop index index_abLvId on job_relation;

-- drop index index_abId on job_relation;

drop table if exists job_relation;

-- drop index ind_org_pay on pay;

-- drop index index_ym_orgId on pay;

-- drop index index_ym on pay;

-- drop index index_fp on pay;

-- drop index "index_eId_ym_pay y" on pay;

-- drop index index_cusId on pay;

drop table if exists pay;

-- drop index ind_eId on promotion_analysis;

drop table if exists promotion_analysis;

-- drop index idx_sId on promotion_element_scheme;

drop table if exists promotion_element_scheme;

drop table if exists promotion_plan;

-- drop index Index_4 on promotion_results;

-- drop index ind_org_pd_promotion_date on promotion_results;

-- drop index ind_Org_date on promotion_results;

-- drop index idx_eId on promotion_results;

drop table if exists promotion_results;

-- drop index ind_tod on promotion_total;

drop table if exists promotion_total;

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
/* Table: pay                                                   */
/*==============================================================*/
create table pay
(
   pay_id               varchar(32) not null comment '薪酬费用ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   usre_name_ch         varchar(5) comment '中文名',
   organization_id      varchar(32) comment '机构ID',
   full_path            varchar(200) comment '机构全路径',
   postion_id           varchar(32) comment '岗位ID',
   pay_contract         blob comment '合同薪酬（单位：元）',
   pay_should           blob comment '应发薪酬（单位：元）',
   pay_actual           blob comment '实发工资（单位：元）',
   salary_actual        blob comment '应发工资（单位：元）',
   welfare_actual       varchar(50) comment '应发福利（单位：元）',
   cr_value             double(6,4) comment 'CR值（过）',
   `year_month`         int(6) comment '年月',
   refresh              datetime comment '更新时间',
   year                 int(4) comment '年',
   bonus                 varchar(10) comment 'bonus',
   primary key (pay_id)
);

alter table pay comment '业务表-薪酬费用（pay）';

/*==============================================================*/
/* Index: index_cusId                                           */
/*==============================================================*/
create index index_cusId on pay
(
   customer_id
);

/*==============================================================*/
/* Index: "index_eId_ym_pay y"                                  */
/*==============================================================*/
create index "index_eId_ym_pay y" on pay
(
   `year_month`,
   emp_id
);

/*==============================================================*/
/* Index: index_fp                                              */
/*==============================================================*/
create index index_fp on pay
(
   full_path
);

/*==============================================================*/
/* Index: index_ym     大量行具有重复值，无大效果，浪费空间                 */
/*==============================================================*/
/*create index index_ym on pay*/
/*(*/
 /*  `year_month`*/
/*);*/

/*==============================================================*/
/* Index: index_ym_orgId                                        */
/*==============================================================*/
create index index_ym_orgId on pay
(
   `year_month`,
   organization_id
);

/*==============================================================*/
/* Index: ind_org_pay                                           */
/*==============================================================*/
create index ind_org_pay on pay
(
   organization_id
);

/*==============================================================*/
/* Table: promotion_analysis                                    */
/*==============================================================*/
create table promotion_analysis
(
   promotion_analysis_id varchar(32) not null comment '员工晋级分析ID',
   emp_id               varchar(32) comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   promotion_req        varbinary(50) comment '晋级要求',
   persoma_status       varbinary(50) comment '个人状态',
   is_accord            int(1) comment '是否符合',
   analysis_date        date comment '分析时间',
   note                 varchar(40) comment '备注',
   primary key (promotion_analysis_id)
);

alter table promotion_analysis comment '员工占比分析(promotion_analysis)';

/*==============================================================*/
/* Index: ind_eId                                               */
/*==============================================================*/
create index ind_eId on promotion_analysis
(
   emp_id
);

/*==============================================================*/
/* Table: promotion_element_scheme                              */
/*==============================================================*/
create table promotion_element_scheme
(
   promotion_element_scheme_id varchar(32) not null comment '晋级要素方案ID',
   customer_id          varbinary(32) comment '客户ID',
   scheme_id            varchar(32) comment '方案ID',
   scheme_name          varchar(50) comment '方案名称',
   company_age          int(3) comment '司龄',
   curt_name_per        varchar(30) comment '绩效短名',
   curt_name_eval       int(4) comment '能力评价短名',
   certificate_id       varchar(32) comment '证书ID',
   certificate_type_id  varchar(32) comment '证书类型ID',
   create_user_id       varchar(32) comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   start_date           datetime comment '生效时间',
   invalid_date         datetime comment '失效时间',
   primary key (promotion_element_scheme_id)
);

alter table promotion_element_scheme comment '晋级要素方案(promotion_element_scheme)';

/*==============================================================*/
/* Index: idx_sId                                               */
/*==============================================================*/
create index idx_sId on promotion_element_scheme
(
   scheme_id
);

/*==============================================================*/
/* Table: promotion_plan                                        */
/*==============================================================*/
create table promotion_plan
(
   promotion_plan_id    varbinary(32) not null comment '职级晋升方案ID',
   customer_id          varchar(32) comment '客户ID',
   rank_name_af         varbinary(5) comment '晋级后简称',
   scheme_id            varchar(32) comment '方案ID',
   create_user_id       varchar(32) comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   primary key (promotion_plan_id)
);

alter table promotion_plan comment '职级晋升方案(promotion_plan)';

/*==============================================================*/
/* Table: promotion_results                                     */
/*==============================================================*/
create table promotion_results
(
   promotion_results_id varchar(32) not null comment '员工晋级结果ID',
   emp_id               varchar(32) comment '员工ID',
   company_age          int(2) comment '司龄',
   oranization_parent_id varchar(32) comment '父部门ID',
   organization_id      varchar(32) comment '部门ID',
   full_path            varchar(20) comment '部门全路径',
   performance_id       varchar(32) comment '绩效ID',
   sequence_id          varchar(32) comment '主序列ID',
   is_key_talent        int(1) comment '是否关键人才',
   customer_id          varchar(32) comment '客户ID',
   entry_date           date comment '入职时间',
   show_index           int(1) comment '排序',
   rank_name            varchar(5) comment '当前简称',
   show_index_af        int(1) comment '晋级后排序',
   rank_name_af         varchar(5) comment '晋级后简称',
   status               int(1) comment '晋级状态',
   status_date          date comment '结果时间',
   promotion_date       date comment '晋级时间',
   primary key (promotion_results_id)
);

alter table promotion_results comment '员工晋级结果(promotion_results)';

/*==============================================================*/
/* Index: idx_eId                                               */
/*==============================================================*/
create index idx_eId on promotion_results
(
   emp_id
);

/*==============================================================*/
/* Index: ind_Org_date                                          */
/*==============================================================*/
create index ind_Org_date on promotion_results
(
   organization_id,
   status_date
);

/*==============================================================*/
/* Index: ind_org_pd_promotion_date                             */
/*==============================================================*/
create index ind_org_pd_promotion_date on promotion_results
(
   promotion_date,
   organization_id
);

/*==============================================================*/
/* Index: Index_4                                               */
/*==============================================================*/
create index Index_4 on promotion_results
(
   
);

/*==============================================================*/
/* Table: promotion_total                                       */
/*==============================================================*/
create table promotion_total
(
   promotion_total_id   varchar(32) not null comment '员工晋级统计ID',
   scheme_id            varbinary(32) comment '方案ID',
   emp_id               varchar(32) comment '员工ID',
   customer_id          varchar(32) comment '客户ID',
   rank_name            varchar(5) comment '当前简称',
   rank_name_af         varchar(5) comment '晋级后简称',
   organization_id      varchar(32) comment '部门ID',
   status               int(1) comment '申请状态',
   condition_prop       double(3,1) comment '条件符合占比',
   total_date           date comment '统计时间',
   primary key (promotion_total_id)
);

alter table promotion_total comment '员工占比统计(promotion_total)';

/*==============================================================*/
/* Index: ind_tod                                               */
/*==============================================================*/
create index ind_tod on promotion_total
(
   total_date,
   customer_id
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



drop table if exists `mup-source`.source_promotion_element_scheme;
create table `mup-source`.source_promotion_element_scheme
(
   promotion_element_scheme_id varchar(32) not null comment '晋级要素方案ID',
   customer_id          varbinary(32) comment '客户ID',
   scheme_id            varchar(32) comment '方案ID',
   scheme_name          varchar(50) comment '方案名称',
   company_age          int(3) comment '司龄',
   curt_name_per        varchar(30) comment '绩效短名',
   curt_name_eval       int(4) comment '能力评价短名',
   certificate_id       varchar(32) comment '证书ID',
   certificate_type_id  varchar(32) comment '证书类型ID',
   create_user_id       varchar(32) comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   start_date           datetime comment '生效时间',
   invalid_date         datetime comment '失效时间',
   primary key (promotion_element_scheme_id)
);

alter table `mup-source`.source_promotion_element_scheme comment '晋级要素方案(`mup-source`.source_promotion_element_scheme)';


drop table if exists `mup-source`.source_promotion_results;
create table `mup-source`.source_promotion_results
(
   promotion_results_id varchar(32) not null comment '员工晋级结果ID',
   emp_id               varchar(32) comment '员工ID',
   company_age          int(2) comment '司龄',
   oranization_parent_id varchar(32) comment '父部门ID',
   organization_id      varchar(32) comment '部门ID',
   full_path            varchar(20) comment '部门全路径',
   performance_id       varchar(32) comment '绩效ID',
   sequence_id          varchar(32) comment '主序列ID',
   is_key_talent        int(1) comment '是否关键人才',
   customer_id          varchar(32) comment '客户ID',
   entry_date           date comment '入职时间',
   show_index           int(1) comment '排序',
   rank_name            varchar(5) comment '当前简称',
   show_index_af        int(1) comment '晋级后排序',
   rank_name_af         varchar(5) comment '晋级后简称',
   status               int(1) comment '晋级状态',
   status_date          date comment '结果时间',
   promotion_date       date comment '晋级时间',
   primary key (promotion_results_id)
);

alter table `mup-source`.source_promotion_results comment '员工晋级结果(`mup-source`.source_promotion_results)';

drop table if exists `mup-source`.source_promotion_plan;
create table `mup-source`.source_promotion_plan
(
   promotion_plan_id    varbinary(32) not null comment '职级晋升方案ID',
   customer_id          varchar(32) comment '客户ID',
   rank_name_af         varbinary(5) comment '晋级后简称',
   scheme_id            varchar(32) comment '方案ID',
   create_user_id       varchar(32) comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   primary key (promotion_plan_id)
);

alter table `mup-source`.source_promotion_plan comment '职级晋升方案(`mup-source`.source_promotion_plan)';

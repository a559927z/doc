/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/3/31 10:21:20                           */
/*==============================================================*/


drop index index_abKey on dim_ability;

drop index index_abId on dim_ability;

drop table if exists dim_ability;

drop table if exists dim_ability_lv;

drop table if exists dim_ability_number;

drop table if exists dim_certificate_info;

drop table if exists dim_change_type;

drop index un_cid on dim_channel;

drop table if exists dim_channel;

drop table if exists dim_checkwork_type;

drop index ind_pId_dim_city on dim_city;

drop table if exists dim_city;

drop table if exists dim_course;

drop table if exists dim_course_type;

drop table if exists dim_customer;

drop table if exists dim_dedicat_genre;

drop index un_cid on dim_dismission_week;

drop table if exists dim_dismission_week;

drop index ind_ym_org on dim_emp_month;

drop index ind_ym_eId on dim_emp_month;

drop table if exists dim_emp_month;

drop index un_cId on dim_encourages;

drop table if exists dim_encourages;

drop index index_parentId on dim_function;

drop index index_key on dim_function;

drop index index_customerId on dim_function;

drop table if exists dim_function;

drop table if exists dim_function_item;

drop table if exists dim_job_title;

drop table if exists dim_key_talent_type;

drop table if exists dim_organization;

drop table if exists dim_organization_type;

drop table if exists dim_performance;

drop index un_cId on dim_population;

drop table if exists dim_population;

drop index index_posId on dim_position;

drop index un_cId on dim_position;

drop table if exists dim_position;

drop table if exists dim_product_category;

drop table if exists dim_product_modul;

drop table if exists dim_product_supplier;

drop table if exists dim_profession;

drop table if exists dim_project_input_type;

drop table if exists dim_project_type;

drop table if exists dim_province;

drop index un_cid on dim_quality;

drop index idx_QId on dim_quality;

drop table if exists dim_quality;

drop index index_key on dim_role;

drop index index_customerId on dim_role;

drop table if exists dim_role;

drop index FK_Reference_35 on dim_run_off;

drop table if exists dim_run_off;

drop table if exists dim_sales_client;

drop table if exists dim_sales_product;

drop table if exists dim_sales_team;

drop table if exists dim_satfac_genre;

drop table if exists dim_separation_risk;

drop index index_seqId on dim_sequence;

drop table if exists dim_sequence;

drop table if exists dim_sequence_sub;

drop table if exists dim_structure;

drop table if exists dim_teacher;

drop index index_username on dim_user;

drop index index_pwd on dim_user;

drop index index_key on dim_user;

drop index index_customerId on dim_user;

drop table if exists dim_user;

drop index ind_z on dim_z_info;

drop table if exists dim_z_info;

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
/* Table: dim_ability_number                                    */
/*==============================================================*/
create table dim_ability_number
(
   ability_number_id    varchar(32) not null comment '能力ID',
   ability_number_key   varchar(20) comment '能力编码',
   ability_number_name  varchar(20) comment '能力名称',
   customer_id          varchar(32) comment '客户ID',
   show_index           int(1) comment '排序',
   create_date          date comment '创建时间',
   primary key (ability_number_id)
);

alter table dim_ability_number comment '员工能力记录(dim_ability_number)';

/*==============================================================*/
/* Table: dim_certificate_info                                  */
/*==============================================================*/
create table dim_certificate_info
(
   certificate_info_id  varchar(32) not null comment '证书信息ID',
   certificate_name     varchar(100) comment '证书名称',
   customer_id          char(32) comment '客户ID',
   certificate_type_id  varchar(32) comment '证书类型ID',
   curt_name            tinyint(2) comment '短名',
   primary key (certificate_info_id)
);

alter table dim_certificate_info comment '证书信息（dim_certificate_info）';

/*==============================================================*/
/* Table: dim_change_type                                       */
/*==============================================================*/
create table dim_change_type
(
   change_type_id       varchar(32) not null comment '异动类型ID',
   customer_id          char(32) comment '客户ID',
   change_type_name     varchar(20) comment '名称',
   curt_name            char(1) comment '简称',
   show_index           tinyint(3) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (change_type_id)
);

alter table dim_change_type comment '维度表-异动类型维（dim_change_type）';

/*==============================================================*/
/* Table: dim_channel                                           */
/*==============================================================*/
create table dim_channel
(
   channel_id           varchar(32) not null comment '应聘渠道ID',
   customer_id          char(32) comment '客户ID',
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
/* Table: dim_checkwork_type                                    */
/*==============================================================*/
create table dim_checkwork_type
(
   checkwork_type_id    varchar(32) not null comment '考勤类型ID',
   customer_id          varchar(32) comment '客户ID',
   checkwork_type_name  varchar(10) comment '考勤类型名称',
   curt_name            tinyint(1) comment '短名称',
   show_index           tinyint(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (checkwork_type_id)
);

alter table dim_checkwork_type comment '维度表-考勤类型（dim_checkwork_type）';

/*==============================================================*/
/* Table: dim_city                                              */
/*==============================================================*/
create table dim_city
(
   city_id              varchar(32) not null comment '城市ID',
   customer_id          char(32) comment '客户ID',
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
/* Table: dim_course                                            */
/*==============================================================*/
create table dim_course
(
   course_id            varchar(32) not null comment '课程维ID',
   customer_id          char(32) comment '客户ID',
   course_key           varchar(20) comment '课程编号',
   course_name          varchar(20) comment '课程名称',
   course_type_id       varchar(32) comment '课程类别ID',
   show_index           int(1) comment '排序',
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
/* Table: dim_customer                                          */
/*==============================================================*/
create table dim_customer
(
   customer_id          varchar(32) not null comment '客户ID',
   customer_key         varchar(20) comment '客户编码',
   customer_name        varchar(20) comment '客户名称',
   note                 varchar(200) comment '描述',
   primary key (customer_id)
);

alter table dim_customer comment '维度表-客户维（dim_customer）';

/*==============================================================*/
/* Table: dim_dedicat_genre                                     */
/*==============================================================*/
create table dim_dedicat_genre
(
   dedicat_genre_id     char(32) not null comment '敬业度ID',
   customer_id          char(32) comment '客户ID',
   dedicat_name         varchar(100) comment '名称',
   dedicat_genre_parent_id char(32) comment '上级ID',
   is_children          tinyint(1) comment '是否有子节点',
   create_date          date comment '创建日期',
   show_index           int(1) comment '排序',
   primary key (dedicat_genre_id)
);

alter table dim_dedicat_genre comment '维度表-敬业度分类（dim_dedicat_genre）';

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
   show_index           int(1) comment '排序',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
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
/* Table: dim_encourages                                        */
/*==============================================================*/
create table dim_encourages
(
   encourages_id        varchar(32) not null comment '激励要素ID',
   customer_id          varchar(32) comment '客户ID',
   encourages_key       varchar(20) comment '激励要素编码',
   content              varchar(20) comment '激励要素内容',
   show_index           int(1) comment '排序',
   c_id                 varchar(32) comment '标识ID',
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
/* Table: dim_function                                          */
/*==============================================================*/
create table dim_function
(
   function_id          varchar(32) not null comment '功能ID',
   customer_id          varchar(32) comment '客户ID',
   function_key         varchar(20) comment '功能编码',
   function_name        varchar(20) comment '功能名称',
   function_parent_id   varchar(32) comment '上级功能',
   full_path            varchar(1000) comment '全路径',
   note                 varchar(200) comment '描述',
   show_index           int(4) comment '排序',
   quota_or_fun         tinyint(1) comment '指标或功能',
   create_user_id       varchar(32) not null comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime not null comment '创建时间',
   modify_time          datetime comment '修改时间',
   url                  varchar(200) comment '连接',
   is_menu              int(1) comment '是否菜单',
   primary key (function_id)
);

alter table dim_function comment '维度表-功能维（dim_function）';

/*==============================================================*/
/* Index: index_customerId                                      */
/*==============================================================*/
create index index_customerId on dim_function
(
   customer_id
);

/*==============================================================*/
/* Index: index_key                                             */
/*==============================================================*/
create index index_key on dim_function
(
   function_key
);

/*==============================================================*/
/* Index: index_parentId                                        */
/*==============================================================*/
create index index_parentId on dim_function
(
   function_parent_id
);

/*==============================================================*/
/* Table: dim_function_item                                     */
/*==============================================================*/
create table dim_function_item
(
   function_item_id     varchar(32) not null comment '功能操作ID',
   customer_id          varchar(32) comment '客户ID',
   function_id          varchar(32) comment '功能ID',
   item_code            varchar(20) comment '操作项',
   note                 varchar(200) comment '描述',
   create_user_id       varchar(32) not null comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime not null comment '创建时间',
   modify_time          datetime comment '修改时间',
   primary key (function_item_id)
);

alter table dim_function_item comment '维度表-功能操作项维（dim_function_item）';

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
/* Table: dim_organization                                      */
/*==============================================================*/
create table dim_organization
(
   organization_id      varchar(32) not null comment '机构ID',
   customer_id          char(32) comment '客户ID',
   organization_company_id varchar(32) comment '分公司',
   organization_type_id varchar(32) comment '机构级别ID',
   organization_key     varchar(20) comment '机构编码',
   organization_parent_key varchar(20) comment '上级机构编码',
   organization_parent_id char(32) comment '上级机构',
   organization_name    varchar(20) comment '机构名称',
   organization_name_full varchar(20) comment '全名称',
   note                 varchar(200) comment '描述',
   is_single            int(1) comment '是否独立核算',
   full_path            longtext comment '全路径',
   has_children         tinyint(1) comment '是否有子节点',
   depth                int(4) comment '深度',
   profession_id        varchar(32) comment '行业ID',
   refresh              datetime comment '更新时间',
   primary key (organization_id)
);

alter table dim_organization comment '维度表-机构维（dim_organization）';

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
   show_index           int(1) comment '排序',
   primary key (organization_type_id)
);

alter table dim_organization_type comment '维度表-机构级别维（dim_organization_type）';

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
/* Table: dim_population                                        */
/*==============================================================*/
create table dim_population
(
   population_id        varchar(32) not null comment '人群范围ID',
   customer_id          char(32) comment '客户ID',
   population_key       varchar(20) comment '人群范围编码',
   population_name      varchar(20) comment '人群范围名称',
   show_index           int(1) comment '排序',
   refresh              datetime comment '更新时间',
   c_id                 varchar(32) comment '标识ID',
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
/* Table: dim_position                                          */
/*==============================================================*/
create table dim_position
(
   position_id          varchar(32) not null comment '岗位ID',
   customer_id          char(32) comment '客户ID',
   position_key         varchar(20) comment '岗位编码',
   position_name        varchar(20) comment '岗位名称',
   show_index           int(1) comment '排序',
   refresh              datetime comment '更新时间',
   c_id                 varchar(0) comment '标识ID',
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
/* Table: dim_product_category                                  */
/*==============================================================*/
create table dim_product_category
(
   product_category_id  varchar(32) not null comment '产品类别ID',
   customer_id          varchar(32) comment '客户ID',
   product_category_name varchar(50) comment '产品名称',
   show_index           int(1) comment '排序',
   primary key (product_category_id)
);

alter table dim_product_category comment '商品类别(dim_product_category)';

/*==============================================================*/
/* Table: dim_product_modul                                     */
/*==============================================================*/
create table dim_product_modul
(
   product_modul_id     varchar(32) not null comment '产品模块ID',
   customer_id          varchar(32) comment '客户ID',
   product_modul_name   varchar(50) comment '产品模块名称',
   show_index           int(1) comment '排序',
   primary key (product_modul_id)
);

alter table dim_product_modul comment '商品模块(dim_product_modul)';

/*==============================================================*/
/* Table: dim_product_supplier                                  */
/*==============================================================*/
create table dim_product_supplier
(
   product_supplier_id  varchar(32) not null comment '产品供应商ID',
   product_supplier_name varchar(50) comment '产品供应商ID',
   customer_id          varchar(32) comment '客户ID',
   show_index           int(1) comment '排序',
   primary key (product_supplier_id)
);

alter table dim_product_supplier comment '商品供应商(dim_product_supplier)';

/*==============================================================*/
/* Table: dim_profession                                        */
/*==============================================================*/
create table dim_profession
(
   profession_id        varchar(32) not null comment '行业ID',
   profession_name      varchar(80) comment '行业名称',
   profession_key       varchar(32) comment '行业编码',
   show_index           int(1) comment '排序',
   refresh              datetime comment '更新日期',
   primary key (profession_id)
);

alter table dim_profession comment '维度表-行业维（dim_profession）';

/*==============================================================*/
/* Table: dim_project_input_type                                */
/*==============================================================*/
create table dim_project_input_type
(
   project_input_type_id varchar(32) not null comment '项目投入类型ID',
   customer_id          char(32) comment '客户ID',
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
   customer_id          char(32) comment '客户ID',
   project_type_name    varchar(10) comment '项目类型名称',
   show_index           tinyint(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (project_type_id)
);

alter table dim_project_type comment '维度表-项目类型维（dim_project_type）';

/*==============================================================*/
/* Table: dim_province                                          */
/*==============================================================*/
create table dim_province
(
   province_id          varchar(32) not null comment '省ID',
   customer_id          char(32) comment '客户ID',
   province_key         varchar(20) comment '省编码',
   province_name        varchar(20) comment '省名称',
   show_index           int(3) comment '排序',
   curt_name            char(1) comment '短名',
   c_id                 varchar(32) comment '标识ID',
   primary key (province_id)
);

alter table dim_province comment '维度表-省（dim_province）';

/*==============================================================*/
/* Table: dim_quality                                           */
/*==============================================================*/
create table dim_quality
(
   quality_id           varchar(32) not null comment '能力素质ID',
   customer_id          char(32) comment '客户ID',
   vocabulary           varchar(10) comment '能力素质词条',
   note                 varchar(80) comment '能力素质定义',
   show_index           int(1) comment '排序',
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
/* Table: dim_role                                              */
/*==============================================================*/
create table dim_role
(
   role_id              varchar(32) not null comment '角色ID',
   customer_id          varchar(32) comment '客户ID',
   role_key             varchar(20) comment '角色编码',
   role_name            varchar(20) comment '角色名称',
   note                 varchar(200) comment '描述',
   create_user_id       varchar(32) not null comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime not null comment '创建时间',
   modify_time          datetime comment '修改时间',
   show_index           int(1) comment '排序',
   primary key (role_id)
);

alter table dim_role comment '维度表-角色维（dim_role）';

/*==============================================================*/
/* Index: index_customerId                                      */
/*==============================================================*/
create index index_customerId on dim_role
(
   customer_id
);

/*==============================================================*/
/* Index: index_key                                             */
/*==============================================================*/
create index index_key on dim_role
(
   role_key
);

/*==============================================================*/
/* Table: dim_run_off                                           */
/*==============================================================*/
create table dim_run_off
(
   run_off_id           varchar(32) not null comment '流失项ID',
   customer_id          varchar(32) comment '客户ID',
   run_off_key          varchar(3) comment '流失编码',
   run_off_name         varchar(200) comment '流失名称',
   type                 int(1) comment '(1：主动 2：被动 3：其它)',
   show_index           int(1) comment '排序',
   primary key (run_off_id)
);

alter table dim_run_off comment '维度表-流失维(dim_run_off)';

/*==============================================================*/
/* Index: FK_Reference_35                                       */
/*==============================================================*/
create index FK_Reference_35 on dim_run_off
(
   run_off_key
);

/*==============================================================*/
/* Table: dim_sales_client                                      */
/*==============================================================*/
create table dim_sales_client
(
   client_id            varchar(32) not null comment '销售客户ID',
   customer_id          varchar(32) comment '客户ID',
   client_key           varchar(20) comment '销售客户编码',
   client_name          varchar(50) comment '销售客户名称',
   curt_name            varchar(10) comment '销售客户短名称',
   company_nature       varchar(32) comment '公司性质',
   main_business        varchar(200) comment '主营业务',
   emp_num              int(7) comment '员工数量',
   leader_num           int(7) comment '管理干部数量',
   client_parent_name   varchar(100) comment '销售客户母公司',
   client_owned_industry varchar(100) comment '销售客户所属行业',
   client_type          int(1) comment '销售客户类型',
   client_turnover      double(10,4) comment '销售客户年均营业额(万元)',
   client_email         varchar(100) comment '销售客户e-mail',
   client_telephone     varchar(100) comment '销售客户电话',
   client_credit_rating int(1) comment '销售客户信用等级',
   country              varchar(100) comment '国家',
   province             varchar(100) comment '省',
   city                 varchar(100) comment '市',
   area                 varchar(100) comment '区',
   street               varchar(100) comment '街道',
   show_index           int(1) comment '排序',
   primary key (client_id)
);

alter table dim_sales_client comment '销售客户表(dim_sales_client)';

/*==============================================================*/
/* Table: dim_sales_product                                     */
/*==============================================================*/
create table dim_sales_product
(
   product_id           varchar(32) not null comment '商品ID',
   customer_id          varchar(32) comment '客户ID',
   product_key          varchar(32) comment '商品编码',
   product_name         varchar(50) comment '商品名称',
   product_cost         double(10,2) comment '商品成本（单位：元）',
   product_price        double(10,2) comment '商品价格（单位：元）',
   product_supplier_id  varchar(32) comment '产品供应商ID',
   product_modul_id     varchar(32) comment '产品模块ID',
   product_category_id  varchar(32) comment '产品类型ID',
   primary key (product_id)
);

alter table dim_sales_product comment '商品（dim_sales_product）';

/*==============================================================*/
/* Table: dim_sales_team                                        */
/*==============================================================*/
create table dim_sales_team
(
   team_id              varchar(32) not null comment '团队ID',
   customer_id          varchar(32) comment '客户ID',
   team_name            varchar(40) comment '团队名称',
   emp_id               varchar(200) comment '负责人ID',
   emp_names            varchar(100) comment '负责人名称',
   organization_id      varchar(32) comment '机构ID',
   show_index           int(1) comment '排序',
   primary key (team_id)
);

alter table dim_sales_team comment '团队信息(dim_sales_team)';

/*==============================================================*/
/* Table: dim_satfac_genre                                      */
/*==============================================================*/
create table dim_satfac_genre
(
   satfac_genre_id      char(32) not null comment '满意度ID',
   customer_id          char(32) comment '客户ID',
   satfac_name          varchar(100) comment '名称',
   satfac_genre_parent_id char(32) comment '上级ID',
   is_children          tinyint(1) comment '是否有子节点',
   create_date          date comment '创建日期',
   show_index           int(1) comment '排序',
   primary key (satfac_genre_id)
);

alter table dim_satfac_genre comment '维度表-满意度分类（dim_satfac_genre）';

/*==============================================================*/
/* Table: dim_separation_risk                                   */
/*==============================================================*/
create table dim_separation_risk
(
   separation_risk_id   varchar(32) not null comment '离职风险ID',
   customer_id          varchar(32) comment '客户ID',
   separation_risk_key  varchar(100) comment '离职风险编号',
   separation_risk_parent_id varchar(32) comment '上级ID',
   separation_risk_parent_key varchar(100) comment '上级编号',
   separation_risk_name varchar(20) comment '离职风险名称',
   refer                longtext comment '参考',
   show_index           int(3) comment '排序',
   primary key (separation_risk_id)
);

alter table dim_separation_risk comment '维度表-离职风险维(dim_separation_risk)';

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
/* Table: dim_structure                                         */
/*==============================================================*/
create table dim_structure
(
   structure_id         char(32) not null comment '工资结构ID',
   customer_id          char(32) comment '客户ID',
   structure_name       varchar(20) comment '结构名称',
   is_fixed             tinyint(1) comment '是否固定',
   show_index           int(1) comment '排序',
   refresh              datetime comment '更新时间',
   primary key (structure_id)
);

alter table dim_structure comment '维度表-工资结构维（dim_structure）';

/*==============================================================*/
/* Table: dim_teacher                                           */
/*==============================================================*/
create table dim_teacher
(
   teacher_id           varchar(32) not null comment '产品供应商ID',
   customer_id          varchar(32) comment '客户ID',
   teacher_name         varchar(50) comment '老师名称',
   good_field           varchar(100) comment '擅长领域',
   show_index           int(1) comment '排序',
   primary key (teacher_id)
);

alter table dim_teacher comment '教师信息(dim_teacher)';

/*==============================================================*/
/* Table: dim_user                                              */
/*==============================================================*/
create table dim_user
(
   user_id              varchar(32) not null comment '用户ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   user_key             varchar(20) comment '用户编码',
   user_name            varchar(20) comment '用户名称',
   user_name_ch         varchar(5) comment '中文名',
   password             varchar(20) comment '密码',
   email                varchar(50) comment '邮箱',
   note                 varchar(200) comment '描述',
   sys_deploy           int(1) comment '系统部署标识',
   show_index           int(5) comment '排序',
   create_user_id       varchar(32) not null comment '创建人',
   modify_user_id       varchar(32) comment '修改人',
   create_time          datetime not null comment '创建时间',
   modify_time          datetime comment '修改时间',
   is_lock              tinyint(1) comment '是否锁',
   primary key (user_id)
);

alter table dim_user comment '维度表-用户信息表(dim_user)';

/*==============================================================*/
/* Index: index_customerId                                      */
/*==============================================================*/
create index index_customerId on dim_user
(
   customer_id
);

/*==============================================================*/
/* Index: index_key                                             */
/*==============================================================*/
create index index_key on dim_user
(
   user_key
);

/*==============================================================*/
/* Index: index_pwd                                             */
/*==============================================================*/
create index index_pwd on dim_user
(
   password
);

/*==============================================================*/
/* Index: index_username                                        */
/*==============================================================*/
create index index_username on dim_user
(
   user_name
);

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


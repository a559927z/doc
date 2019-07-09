/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/1/12 17:15:28                           */
/*==============================================================*/


drop table if exists client_credit;

drop table if exists dim_product_category;

drop table if exists dim_product_modul;

drop table if exists dim_product_supplier;

drop table if exists dim_sales_client;

drop table if exists dim_sales_client_dimension;

drop table if exists dim_sales_product;

drop table if exists dim_teacher;

drop table if exists extend_product_course;

drop table if exists sales_client_contacts;

drop table if exists sales_client_dimension_relation;

drop table if exists sales_client_plan;

drop table if exists sales_client_summary;

drop table if exists sales_emp_client_relation;

/*==============================================================*/
/* Table: client_credit                                         */
/*==============================================================*/
create table client_credit
(
   client_credit_id     varchar(32) comment '销售客户信用度ID',
   customer_id          varchar(32) comment '客户ID',
   client_id            varchar(32) comment '销售客户ID',
   arrears              double(8,4) comment '欠款数',
   stay_back            double(8,4) comment '待回款数',
   create_date          date comment '创建时间'
);

alter table client_credit comment '销售客户信用度（client_credit）';

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
/* Table: dim_sales_client_dimension                            */
/*==============================================================*/
create table dim_sales_client_dimension
(
   client_dimension_id  varchar(32) not null comment '销售客户维度ID',
   customer_id          varchar(32) comment '客户ID',
   client_dimension_name varchar(32) comment '销售客户维度名称',
   show_index           int(1) comment '排序',
   primary key (client_dimension_id)
);

alter table dim_sales_client_dimension comment '销售客户维度表（dim_sales_client_dimension）';

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
/* Table: extend_product_course                                 */
/*==============================================================*/
create table extend_product_course
(
   product_id           varchar(32) not null comment '商品ID',
   customer_id          varchar(32) comment '客户ID',
   teaching_date_id     varchar(32) comment '授课日期ID',
   teacher_id           varchar(32) comment '教师ID',
   place                varchar(50) comment '地点',
   start_date           varchar(32) comment '课程开始时间',
   end_date             varchar(32) comment '课程结束时间',
   duration             double(4,1) comment '课程时长',
   primary key (product_id)
);

alter table extend_product_course comment '商品扩展表-课程（extend_product_course）';

/*==============================================================*/
/* Table: sales_client_contacts                                 */
/*==============================================================*/
create table sales_client_contacts
(
   contacts_id          varchar(32) not null comment '客户联系人ID',
   customer_id          varchar(32) comment '客户ID',
   contacts_name        varchar(32) comment '客户联系人名称',
   client_id            varchar(32) comment '销售客户ID',
   contacts_dept        varchar(50) comment '客户联系人部门',
   sex                  varchar(1) comment '性别',
   age                  int(3) comment '年龄',
   native_place         varchar(90) comment '籍贯',
   contacts_parent_name varchar(32) comment '直属上司',
   post                 varchar(32) comment '职务',
   marriage             int(1) comment '婚姻',
   children             int(1) comment '子女数量',
   interest             varchar(100) comment '兴趣',
   disposition          varchar(100) comment '性格',
   company_age          int(3) comment '司龄',
   contacts_telephone   varchar(100) comment '联系手机',
   contacts_email       varchar(100) comment '联系e-mail',
   primary key (contacts_id)
);

alter table sales_client_contacts comment '销售客户联系人表（sales_client_contacts）';

/*==============================================================*/
/* Table: sales_client_dimension_relation                       */
/*==============================================================*/
create table sales_client_dimension_relation
(
   sales_client_dimension_id varchar(32) not null comment '关系ID',
   client_dimension_id  varchar(32) comment '销售客户维度ID',
   client_id            varchar(32) comment '销售客户ID',
   customer_id          varchar(32) comment '客户ID',
   primary key (sales_client_dimension_id)
);

alter table sales_client_dimension_relation comment '销售客户与客户维度关系表（sales_client_dimension_relation）';

/*==============================================================*/
/* Table: sales_client_plan                                     */
/*==============================================================*/
create table sales_client_plan
(
   summary_id           varchar(32) not null comment '沟通纪要ID',
   customer_id          varchar(32) comment '客户ID',
   client_id            varchar(32) comment '销售客户ID',
   plan_note            text comment '沟通纪要内容',
   plan_key_note        varchar(200) comment '沟通纪要要点',
   plan_time            datetime comment '创建时间',
   interested_product   varchar(100) comment '感兴趣产品',
   summary_principal    varchar(20) comment '沟通负责人',
   contacts_id          varchar(32) comment '沟通计划联系人',
   primary key (summary_id)
);

alter table sales_client_plan comment '销售客户沟通计划表（sales_client_plan）';

/*==============================================================*/
/* Table: sales_client_summary                                  */
/*==============================================================*/
create table sales_client_summary
(
   summary_id           varchar(32) not null comment '沟通纪要ID',
   customer_id          varchar(32) comment '客户ID',
   client_id            varchar(32) comment '销售客户ID',
   note                 text comment '沟通纪要内容',
   key_note             varchar(200) comment '沟通纪要要点',
   interested_product   varchar(100) comment '感兴趣产品',
   summary_principal    varchar(20) comment '沟通负责人',
   contacts_id          varchar(32) comment '沟通联系人',
   create_time          datetime comment '创建时间',
   primary key (summary_id)
);

alter table sales_client_summary comment '销售客户沟通纪要表（sales_client_summary）';

/*==============================================================*/
/* Table: sales_emp_client_relation                             */
/*==============================================================*/
create table sales_emp_client_relation
(
   sales_emp_client_id  varchar(32) not null comment '销售人员与客户关系表',
   emp_id               varchar(32) comment '销售人员ID',
   customer_id          varchar(32) comment '客户ID',
   client_id            varchar(32) comment '销售客户ID',
   client_type          varchar(1),
   primary key (sales_emp_client_id)
);

alter table sales_emp_client_relation comment '销售人员与客户关系表（sales_emp_client_relation）';




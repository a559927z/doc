/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:39:56                           */
/*==============================================================*/


drop table if exists dedicat_genre_soure;

drop table if exists dedicat_organ;

drop table if exists dim_dedicat_genre;

drop table if exists dim_organization;

drop table if exists dim_satfac_genre;

drop table if exists satfac_genre_soure;

drop table if exists satfac_organ;

/*==============================================================*/
/* Table: dedicat_genre_soure                                   */
/*==============================================================*/
create table dedicat_genre_soure
(
   dedicat_soure_id     varchar(32) not null comment '敬业度评分ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   dedicat_genre_id     varchar(32) comment '敬业度分类ID',
   soure                double(5,4) comment '得分',
   date                 date comment '报告日期',
   primary key (dedicat_soure_id)
);

alter table dedicat_genre_soure comment '历史表-敬业度评分（dedicat_genre_soure）';

/*==============================================================*/
/* Table: dedicat_organ                                         */
/*==============================================================*/
create table dedicat_organ
(
   dedicat_organ_id     varchar(32) not null comment '敬业机构评分ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   organization_name    varchar(20) comment '机构名称',
   soure                double(4,4) comment '得分',
   date                 date comment '报告日期',
   primary key (dedicat_organ_id)
);

alter table dedicat_organ comment '历史表-敬业度机构评分（dedicat_organ）';

/*==============================================================*/
/* Table: dim_dedicat_genre                                     */
/*==============================================================*/
create table dim_dedicat_genre
(
   dedicat_genre_id     varchar(32) not null comment '敬业度ID',
   customer_id          varchar(32) comment '客户ID',
   dedicat_name         varchar(100) comment '名称',
   dedicat_genre_parent_id varchar(32) comment '上级ID',
   is_children          tinyint(1) comment '是否有子节点',
   create_date          date comment '创建日期',
   show_index       	int(1) comment '排序',
   primary key (dedicat_genre_id)
);

alter table dim_dedicat_genre comment '维度表-敬业度分类（dim_dedicat_genre）';

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
/* Table: dim_satfac_genre                                      */
/*==============================================================*/
create table dim_satfac_genre
(
   satfac_genre_id      varchar(32) not null comment '满意度ID',
   customer_id          varchar(32) comment '客户ID',
   satfac_name          varchar(100) comment '名称',
   satfac_genre_parent_id varchar(32) comment '上级ID',
   is_children          tinyint(1) comment '是否有子节点',
   create_date          date comment '创建日期',
   show_index       	int(1) comment '排序',
   primary key (satfac_genre_id)
);

alter table dim_satfac_genre comment '维度表-满意度分类（dim_satfac_genre）';

/*==============================================================*/
/* Table: satfac_genre_soure                                    */
/*==============================================================*/
create table satfac_genre_soure
(
   satfac_soure_id      varchar(32) not null comment '满意度评分ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   satfac_genre_id      varchar(32) comment '满意度分类ID',
   soure                double(5,4) comment '得分',
   date                 date comment '报告日期',
   primary key (satfac_soure_id)
);

alter table satfac_genre_soure comment '历史表-满意度评分（satfac_genre_soure）';

/*==============================================================*/
/* Table: satfac_organ                                          */
/*==============================================================*/
create table satfac_organ
(
   satfac_organ_id      varchar(32) not null comment '满意机构评分ID',
   customer_id          varchar(32) comment '客户ID',
   organization_id      varchar(32) comment '机构ID',
   organization_name    varchar(20) comment '机构名称',
   soure                double(4,4) comment '得分',
   date                 date comment '报告日期',
   primary key (satfac_organ_id)
);

alter table satfac_organ comment '历史表-满意度机构评分（satfac_organ）';


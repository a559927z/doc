﻿/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/12/15 9:33:09                           */
/*==============================================================*/


-- drop index index_abKey on dim_ability;

-- drop index index_abId on dim_ability;

drop table if exists dim_ability;

drop table if exists dim_key_talent_type;

drop table if exists dim_performance;

-- drop index FK_Reference_35 on dim_run_off;

drop table if exists dim_run_off;

drop table if exists dim_separation_risk;

drop table if exists dim_separation_risk2;

-- drop index index_eId on dimission_risk;

drop table if exists dimission_risk;

-- drop index FK_Reference_29 on dimission_risk_item;

-- drop index FK_Reference_28 on dimission_risk_item;

drop table if exists dimission_risk_item;

-- drop index ind_eId on key_talent;

drop table if exists key_talent;

-- drop index ind_eId_run_off_record on run_off_record;

drop table if exists run_off_record;

drop table if exists underling;

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
/* Table: dim_run_off                                           */
/*==============================================================*/
create table dim_run_off
(
   run_off_id           varchar(32) not null comment '流失项ID',
   customer_id          varchar(32) comment '客户ID',
   run_off_key          varchar(3) comment '流失编码',
   run_off_name         varchar(200) comment '流失名称',
   `type` int(1) comment '(1：主动 2：被动 3：其它)',
   show_index       	int(1) comment '排序',
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
/* Table: dim_separation_risk2                                  */
/*==============================================================*/
create table dim_separation_risk2
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

alter table dim_separation_risk2 comment '维度表-离职风险维(dim_separation_risk)2';

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
/* Table: dimission_risk_item                                   */
/*==============================================================*/
create table dimission_risk_item
(
   dimission_risk_item_id varchar(32) not null comment '离职风险评估项ID',
   customer_id          varchar(32) comment '客户ID',
   dimission_risk_id    varchar(32) comment '离职风险评估ID',
   separation_risk_id   varchar(32) comment '离职风险ID',
   risk_flag            int(1) comment '风险标识',
   primary key (dimission_risk_item_id)
);

alter table dimission_risk_item comment '业务表-离职风险评估细项(dimission_risk_item)';

/*==============================================================*/
/* Index: FK_Reference_28                                       */
/*==============================================================*/
create index FK_Reference_28 on dimission_risk_item
(
   dimission_risk_id
);

/*==============================================================*/
/* Index: FK_Reference_29                                       */
/*==============================================================*/
create index FK_Reference_29 on dimission_risk_item
(
   separation_risk_id
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
/* Table: run_off_record                                        */
/*==============================================================*/
create table run_off_record
(
   run_off_record_id    varchar(32) not null comment '流失记录ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   run_off_id           varchar(32) comment '流失项ID',
   where_abouts_id      varchar(32) comment '流失去向ID',
   where_abouts         varchar(200) comment '流失去向',
   run_off_date         datetime not null comment '流失日期',
   is_warn              int(1) comment '是否有过预警',
   re_hire              int(1) comment '建议重新录用',
   primary key (run_off_record_id)
);

alter table run_off_record comment '业务表-流失记录(run_off_record)';

/*==============================================================*/
/* Index: ind_eId_run_off_record                                */
/*==============================================================*/
create index ind_eId_run_off_record on run_off_record
(
   emp_id
);

/*==============================================================*/
/* Table: underling                                             */
/*==============================================================*/
create table underling
(
   emp_relation_id      varchar(32) not null comment '我的下属员工ID',
   customer_id          varchar(32) comment '客户ID',
   emp_id               varchar(32) comment '员工ID',
   emp_key              varchar(20) comment '员工编码',
   underling_emp_id     varchar(32) comment '下属员工ID',
   underling_emp_key    varchar(20) comment '下属员工编码',
   primary key (emp_relation_id)
);

alter table underling comment '业务表-我的下属员工（underling）';

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


create table  `pub_datasource_info`
(
       `datasource_id`   VARCHAR(32) not null comment '数据源id',
       `datasource_name` VARCHAR(100) comment '数据源名称',
       `db_type`         VARCHAR(20) comment '数据库类型 mysql,oracle,postgresql,sqlserver',
       `username`        VARCHAR(32) comment '账号',
       `password`        VARCHAR(32) comment '密码',
       `url`             VARCHAR(200) comment 'jdbcUrl',
       `initial_size`    INT comment '初始化连接数量',
       `max_active`      INT comment '最大活动连接',
       `max_idle`        INT comment '最大空闲连接',
       `note`            VARCHAR(200) comment '备注',
	   `revision`        INT comment '乐观锁',
       `create_by`       VARCHAR(32) comment '创建人',
       `create_date`     DATETIME comment '创建日期',
       `update_by`       VARCHAR(32) comment '更新人',
       `update_date`     DATETIME comment '更新日期'
);
alter  table `pub_datasource_info`
       add constraint `PK_pub_datnfo_datasou_id` primary key (`datasource_id`);
alter table `pub_datasource_info` comment= '数据源管理';

create table  `pub_tenant_datasource_rel`
(
       `rel_id`          VARCHAR(32) not null comment '租户与数据源关系id',
       `tenant_id`       VARCHAR(32) comment '租户id',
       `datasource_id`   VARCHAR(32) comment '数据源id',
	   `revision`        INT comment '乐观锁',
       `create_by`       VARCHAR(32) comment '创建人',
       `create_date`     DATETIME comment '创建日期',
       `update_by`       VARCHAR(32) comment '更新人',
       `update_date`     DATETIME comment '更新日期'
);
alter  table `pub_tenant_datasource_rel`
       add constraint `PK_pub_tenrel_rel_id` primary key (`rel_id`);
alter table `pub_tenant_datasource_rel` comment= '租户与数据源关系表';


create table  `pub_tenant_info`
(
       `tenant_id`       VARCHAR(32) not null comment '租户id',
       `tenant_name`     VARCHAR(100) comment '租户名称',
       `tenant_state`    VARCHAR(20) comment '租户状态',
       `tenant_address`  VARCHAR(200) comment '租户地址',
       `contact`         VARCHAR(32) comment '联系人',
       `contact_tel`     VARCHAR(32) comment '联系人电话',
       `contact_email`   VARCHAR(64) comment '联系人邮箱',
	   `revision`        INT comment '乐观锁',
       `create_by`       VARCHAR(32) comment '创建人',
       `create_date`     DATETIME comment '创建日期',
       `update_by`       VARCHAR(32) comment '更新人',
       `update_date`     DATETIME comment '更新日期'
);
alter  table `pub_tenant_info`
       add constraint `PK_pub_tennfo_tenant_id` primary key (`tenant_id`);
alter table `pub_tenant_info` comment= '租户管理';

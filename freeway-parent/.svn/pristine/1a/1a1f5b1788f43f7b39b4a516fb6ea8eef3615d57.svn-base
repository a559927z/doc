
CREATE TABLE pub_log_info(
    log_id VARCHAR(32) NOT NULL   COMMENT '主键id' ,
    op_name VARCHAR(64)    COMMENT '操作名称' ,
    op_desc VARCHAR(200)    COMMENT '操作描述' ,
    user_name VARCHAR(64)    COMMENT '用户名' ,
    log_type VARCHAR(20)    COMMENT '日志类型' ,
    request_ip VARCHAR(64)    COMMENT '客户端请求ip' ,
    request_method VARCHAR(8)    COMMENT '请求方法类型' ,
    request_uri VARCHAR(200)    COMMENT '请求Uri' ,
    remote_host VARCHAR(64)    COMMENT '远程主机' ,
    user_agent VARCHAR(200)    COMMENT '用户代理' ,
    params TEXT    COMMENT '请求参数' ,
    exec_time INT    COMMENT '执行时间' ,
    error_info VARCHAR(2000)    COMMENT '错误信息' ,
    revision INT(10)    COMMENT '乐观锁' ,
    create_by VARCHAR(32)    COMMENT '创建人' ,
    create_date DATETIME    COMMENT '创建日期' ,
    update_by VARCHAR(32)    COMMENT '更新人' ,
    update_date DATETIME    COMMENT '更新日期' ,
    PRIMARY KEY (log_id)
) COMMENT = '日志记录表 ';/*SQL@Run*/

CREATE TABLE pub_param_info
(
param_id VARCHAR(32) COMMENT '配置id',
param_type VARCHAR(20) COMMENT '参数分类',
param_key VARCHAR(20) COMMENT '参数key',
param_value VARCHAR(100) COMMENT '参数值',
param_name VARCHAR(64) COMMENT '参数名',
revision INT  COMMENT '乐观锁',
create_by VARCHAR(32)COMMENT '创建人',
create_date DATETIME COMMENT '创建日期',
update_by VARCHAR(32) COMMENT '更新人',
update_date DATETIME COMMENT '更新日期',
PRIMARY KEY (param_id)
) COMMENT = '系统参数配置表';

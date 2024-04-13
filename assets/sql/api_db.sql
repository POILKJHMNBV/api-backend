create database api_db;
use api_db;

create table if not exists api_user
(
    id              bigint auto_increment comment 'id' primary key,
    user_account    varchar(256)                       not null comment '账号',
    user_password   varchar(512)                       not null comment '密码',
    user_name       varchar(256)                       null comment '用户昵称',
    user_email      varchar(256)                       null comment '用户邮箱',
    user_phone      varchar(256)                       null comment '用户电话',
    user_avatar     varchar(1024)                      null comment '用户头像',
    user_profile    varchar(512)                       null comment '用户简介',
    user_role       tinyint  default 0                 not null comment '用户角色：0-普通用户  1-管理员',
    user_status     tinyint  default 0                 not null comment '用户状态 0-正常 1-已删除',
    user_accessKey  varchar(256)                       not null comment '访问密钥',
    user_secretKey  varchar(256)                       not null comment '加密密钥',
    user_publicKey  varchar(1024)                      not null comment '用户公钥',
    user_privateKey varchar(1024)                      not null comment '用户私钥',
    user_login_ip   varchar(64)                        null comment '用户登录ip',
    user_login_time datetime                           null comment '用户登录时间',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '用户表';

create table if not exists api_interface_info
(
    id                               bigint auto_increment comment '主键' primary key,
    interface_token                  char(32)                           not null comment '接口token',
    interface_name                   varchar(256)                       not null comment '接口名称',
    interface_description            varchar(256)                       not null comment '接口描述',
    interface_vendor                 varchar(128)                       not null comment '接口提供系统',
    interface_vendor_name            varchar(256)                       not null comment '接口提供系统名',
    interface_host                   varchar(256)                       not null comment '访问主机',
    interface_path                   varchar(512)                       not null comment '访问路径',
    interface_request_params_mime    varchar(256)                       null comment '接口请求参数MIME类型',
    interface_request_params_charset tinyint                            null comment '接口请求参数编码格式（0-GBK，1-UTF-8）',
    interface_request_params         json                               null comment '接口请求参数',
    interface_request_header         json                               null comment '接口请求头',
    interface_response_header        json                               null comment '接口响应头',
    interface_status                 tinyint  default 0                 not null comment '接口状态（0-关闭，1-开启）',
    interface_request_method         tinyint                            not null comment '接口请求方法类型（0-GET，1-POST，2-DELETE，3-PUT，4-PATCH）',
    interface_publish_userId         bigint                             not null comment '接口发布人',
    interface_delete                 tinyint  default 1                 not null comment '接口是否删除(0-已删, 1-未删)',
    create_time                      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time                      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '接口信息表';

create table if not exists api_user_interface_info
(
    id                bigint auto_increment comment '主键' primary key,
    user_id           bigint                             not null comment '调用用户 id',
    interface_info_id bigint                             not null comment '接口 id',
    total_num         int      default 0                 not null comment '总调用次数',
    left_num          int      default 0                 not null comment '剩余调用次数',
    use_status        int      default 0                 not null comment '用户使用接口状态 0-正常，1-禁用',
    is_delete         tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)',
    create_time       datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time       datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '用户调用接口关系表';

create table if not exists api_user_operate_log
(
    id                    bigint                             not null comment '主键' primary key,
    user_id               bigint                             null comment '操作用户id',
    operation_description varchar(64)                        not null comment '操作描述',
    request_uri           varchar(256)                       not null comment '请求uri',
    request_method        varchar(16)                        not null comment '请求类型',
    request_ip            varchar(64)                        null comment '请求ip',
    request_parameter     json                               null comment '请求参数',
    response_status       int      default 200               not null comment '响应状态',
    cost_time             int                                not null comment '耗时 单位：ms',
    error_reason          text                               null comment '错误原因',
    create_time           datetime default CURRENT_TIMESTAMP not null comment '创建时间'
) comment '用户操作日志表';

create table if not exists api_user_invoke_log
(
    id                bigint                             not null comment '主键' primary key,
    user_id           bigint                             not null comment '操作用户id',
    interface_info_id bigint                             not null comment '调用接口id',
    request_ip        varchar(64)                        null comment '请求ip',
    request_parameter text                               null comment '请求参数',
    response_status   int      default 200               not null comment '响应状态',
    cost_time         int                                not null comment '耗时 单位：ms',
    error_reason      text                               null comment '错误原因',
    create_time       datetime default CURRENT_TIMESTAMP not null comment '创建时间'
) comment '用户调用接口日志表';
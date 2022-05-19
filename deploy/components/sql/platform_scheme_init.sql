create table common_dictionary
(
    id                 bigint auto_increment comment 'ID'
        primary key,
    code               varchar(64)                        not null comment '编码
一颗树仅仅有一个统一的编码',
    name               varchar(64)                        not null comment '名称',
    description        varchar(200)                       null comment '描述',
    status             bit      default b'1'              null comment '状态',
    readonly           bit      default b'0'              null comment '内置角色',
    sequence           tinyint(3)                         null comment '排序',
    created_by         bigint   default 0                 null comment '创建人id',
    created_name       varchar(50)                        null comment '创建人名称',
    created_time       datetime default CURRENT_TIMESTAMP null comment '创建时间',
    last_modified_by   bigint   default 0                 null comment '更新人id',
    last_modified_name varchar(50)                        null comment '更新人名称',
    last_modified_time datetime                           null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '字典类型';

create table common_dictionary_item
(
    id                 bigint auto_increment comment 'ID'
        primary key,
    dictionary_id      bigint                                 not null comment '类型ID',
    dictionary_code    varchar(64)                            not null comment '类型',
    value              varchar(64)  default ''                not null comment '编码',
    label              varchar(64)  default ''                not null comment '名称',
    status             bit          default b'1'              null comment '状态',
    color              varchar(255)                           null comment '颜色',
    description        varchar(255) default ''                null comment '描述',
    sequence           int          default 1                 null comment '排序',
    created_by         bigint       default 0                 null comment '创建人id',
    created_name       varchar(255)                           null,
    created_time       datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    last_modified_by   bigint       default 0                 null comment '更新人id',
    last_modified_name varchar(255)                           null,
    last_modified_time datetime                               null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '字典项';

create index dict_code_item_code_uniq
    on common_dictionary_item (dictionary_code, value)
    comment '字典编码与字典项目编码联合唯一';

create table common_login_log
(
    id              bigint auto_increment comment '主键'
        primary key,
    user_id         bigint                             null comment '登录人ID',
    ip              varchar(50)                        null comment '登录IP',
    client_id       varchar(50)                        null comment '登录人客户端ID',
    name            varchar(50)                        null comment '登录人姓名',
    principal       varchar(30)                        null comment '登录人账号',
    platform        varchar(255)                       null comment '平台',
    engine          varchar(255)                       null comment '引擎类型',
    engine_version  varchar(255)                       null comment '引擎版本',
    browser         varchar(255)                       null comment '浏览器名称',
    browser_version varchar(255)                       null comment '浏览器版本',
    os              varchar(255)                       null comment '操作系统',
    location        varchar(50)                        null comment '登录地点',
    created_time    datetime default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '登录日志';

create table common_opt_log
(
    id              bigint auto_increment
        primary key,
    ip              varchar(50)                           null comment '操作IP',
    location        varchar(255)                          null,
    trace           varchar(255)                          null comment '日志链路追踪id日志标志',
    type            varchar(5)  default 'OPT'             null comment '日志类型
#LogType{OPT:操作类型;EX:异常类型}',
    description     varchar(255)                          null comment '操作描述',
    class_path      varchar(255)                          null comment '类路径',
    action_method   varchar(50)                           null comment '请求方法',
    request_uri     varchar(50)                           null comment '请求地址',
    http_method     varchar(10) default 'GET'             null comment '请求类型
#HttpMethod{GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}',
    params          longtext                              null comment '请求参数',
    result          longtext                              null comment '返回值',
    ex_desc         longtext                              null comment '异常详情信息',
    ex_detail       longtext                              null comment '异常描述',
    start_time      timestamp                             null comment '开始时间',
    finish_time     timestamp                             null comment '完成时间',
    consuming_time  bigint      default 0                 null comment '消耗时间',
    browser         varchar(500)                          null comment '浏览器',
    os              varchar(500)                          null comment '浏览器',
    engine          varchar(500)                          null comment '浏览器',
    engine_version  varchar(500)                          null comment '浏览器',
    platform        varchar(500)                          null comment '浏览器',
    browser_version varchar(500)                          null comment '浏览器',
    created_by      bigint      default 0                 null comment '创建人id',
    created_name    varchar(50)                           null comment '操作人',
    created_time    datetime    default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '系统日志';

create index index_type
    on common_opt_log (type)
    comment '日志类型';

create table sys_oauth_client_details
(
    client_id               varchar(32)               not null comment '客户端ID'
        primary key,
    client_secret           varchar(256)              null comment '客户端秘钥',
    status                  bit        default b'1'   null comment '应用状态',
    type                    tinyint(2) default 0      null comment '应用类型（0=综合应用,1=服务应用,2=PC网页,3=手机网页,4=小程序）',
    resource_ids            varchar(255)              null comment '微服务应用名（暂时不建议用）',
    client_name             varchar(255)              null comment '客户端名称',
    scope                   varchar(256)              null comment '范围',
    authorized_grant_types  varchar(256)              null comment '认证类型',
    web_server_redirect_uri varchar(256)              null comment 'web服务站点',
    authorities             varchar(256)              null,
    access_token_validity   int        default 43200  null comment 'token 有效期默认12小时',
    refresh_token_validity  int        default 604800 null comment 'refresh token  有效期默认7天',
    additional_information  varchar(4096)             null comment '附加信息',
    autoapprove             varchar(256)              null comment '自动审批'
)
    comment '终端信息表';

create table sys_org
(
    id                 bigint auto_increment comment 'ID'
        primary key,
    label              varchar(255) default ''                not null comment '名称',
    tenant_id          bigint                                 null comment '租户ID',
    alias              varchar(255) default ''                null comment '简称',
    tel                varchar(255)                           null comment '联系方式',
    tree_path          varchar(255) default ','               null comment '所有父级ID',
    parent_id          bigint       default 0                 null comment '父ID',
    sequence           int          default 1                 null comment '排序',
    status             bit          default b'1'              null comment '状态',
    description        varchar(255) default ''                null comment '描述',
    created_by         bigint       default 0                 null comment '创建人id',
    created_name       varchar(50)                            null comment '创建人名称',
    created_time       datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    last_modified_by   bigint       default 0                 null comment '更新人id',
    last_modified_name varchar(50)                            null comment '更新人名称',
    last_modified_time datetime                               null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '组织';

create table sys_resource
(
    id                 bigint auto_increment comment '主键'
        primary key,
    label              varchar(100) default ''                not null comment '名称',
    tree_path          varchar(500) default ','               null comment '该节点的所有父节点',
    permission         varchar(255)                           null comment '权限',
    parent_id          bigint       default 0                 null comment '父级菜单ID',
    path               varchar(255) default ''                null comment '路径',
    component          varchar(255)                           null comment '组件',
    sequence           int          default 1                 null comment '排序',
    icon               varchar(255) default ''                null comment '菜单图标',
    style              varchar(255)                           null comment '样式',
    type               tinyint(2)   default 1                 null comment '类型（1=菜单;2=按钮）',
    status             bit          default b'1'              null comment '1=启用;0=禁用',
    readonly           bit          default b'0'              null comment '内置菜单（0=否;1=是）',
    global             bit          default b'0'              null comment '公共资源
True是无需分配所有人就可以访问的',
    display            bit          default b'1'              null comment '0=隐藏;1=显示',
    description        varchar(200) default ''                null comment '描述',
    created_by         bigint                                 null comment '创建人id',
    created_name       varchar(255)                           null comment '创建人名称',
    created_time       datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    last_modified_by   bigint                                 null comment '更新人id',
    last_modified_name varchar(255)                           null comment '更新人名称',
    last_modified_time datetime                               null comment '更新时间'
)
    comment '菜单';

create index INX_STATUS
    on sys_resource (global);

create table sys_role
(
    id                 bigint auto_increment
        primary key,
    tenant_id          int(20)                               null comment '租户编码',
    code               varchar(30)                           null comment '角色编码',
    name               varchar(30) default ''                not null comment '名称',
    description        varchar(255)                          null comment '描述信息',
    scope_type         tinyint(2)                            null comment '数据权限范围，值越大，权限越大',
    locked             tinyint(1)  default 0                 null comment '0=正常1=禁用',
    super              tinyint(1)  default 0                 null comment '0=非 1=管理员',
    readonly           tinyint(1)  default 0                 null comment '是否内置角色',
    created_by         bigint      default 0                 null comment '创建人id',
    created_name       varchar(255)                          null,
    created_time       datetime    default CURRENT_TIMESTAMP null comment '创建时间',
    last_modified_by   bigint      default 0                 null comment '更新人id',
    last_modified_name varchar(255)                          null,
    last_modified_time datetime                              null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '角色';

create table sys_role_org
(
    role_id bigint not null comment '角色ID',
    org_id  bigint not null comment '组织ID',
    constraint role_id
        unique (role_id, org_id)
)
    comment '用户角色表';

create table sys_role_res
(
    role_id      bigint(8)                          not null comment '角色ID',
    res_id       bigint(8)                          not null comment '菜单ID',
    created_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    constraint idx_role_res
        unique (role_id, res_id)
)
    comment '角色权限表' charset = utf8;

create table sys_user_role
(
    user_id bigint not null comment '用户ID',
    role_id bigint not null comment '角色ID',
    constraint role_id
        unique (user_id, role_id)
)
    comment '用户角色表';

create table t_dynamic_datasource
(
    id                 bigint auto_increment
        primary key,
    pool_name          varchar(100)                          null comment '连接池名称',
    username           varchar(100)                          not null comment '用户名',
    password           varchar(100)                          null comment '密码',
    db_type            varchar(255)                          null comment '数据库类型',
    driver_class_name  varchar(255)                          null,
    `database`         varchar(255)                          null comment '数据库名称',
    connection_type    tinyint(2)  default 0                 null comment '连接类型（0=单库多schema ,1 = 单库单schema）',
    host               varchar(50) default 'localhost'       not null comment '数据库连接',
    port               int(8)      default 3306              not null comment '数据库端口',
    description        varchar(255)                          null comment '描述信息',
    locked             bit         default b'0'              null comment '0=正常1=禁用',
    created_by         bigint      default 0                 null comment '创建人id',
    created_name       varchar(255)                          null comment '创建人名称',
    created_time       datetime    default CURRENT_TIMESTAMP null comment '创建时间',
    last_modified_by   bigint      default 0                 null comment '更新人id',
    last_modified_name varchar(255)                          null comment '最后修改人名称',
    last_modified_time datetime                              null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '动态数据源';

create table t_tenant
(
    id                 bigint auto_increment
        primary key,
    code               varchar(20)                          null comment '租户编码',
    name               varchar(64)                          not null comment '租户名称',
    type               tinyint(2) default 0                 null comment '0=其它,1=企业',
    status             tinyint(2) default 0                 null comment '0=未认证,1=已认证',
    alias              varchar(50)                          null comment '简称',
    logo               varchar(255)                         null comment 'LOGO',
    email              varchar(50)                          null comment '租户邮箱',
    contact_person     varchar(50)                          null comment '联系人',
    contact_phone      varchar(30)                          null comment '联系人方式',
    industry           varchar(255)                         null comment '行业',
    province_id        int(8)                               null comment '省份',
    province_name      varchar(64)                          null comment '省份',
    city_id            int(8)                               null comment '市',
    city_name          varchar(64)                          null comment '市',
    address            varchar(250)                         null comment '详细地址',
    district_id        int(8)                               null comment '区县',
    district_name      varchar(64)                          null comment '区县',
    credit_code        varchar(50)                          null comment '统一信用代码',
    legal_person_name  varchar(50)                          null comment '法人',
    web_site           varchar(200)                         null comment '企业网址',
    description        varchar(1000)                        null comment '描述',
    locked             bit        default b'0'              null comment '是否启用 0=未锁定 1=锁定(逻辑删除用)',
    created_by         bigint     default 0                 null comment '创建人id',
    created_name       varchar(50)                          null comment '创建人名称',
    created_time       datetime   default CURRENT_TIMESTAMP null comment '创建时间',
    last_modified_by   bigint     default 0                 null comment '更新人id',
    last_modified_name varchar(50)                          null comment '更新人名称',
    last_modified_time datetime                             null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '租户信息' charset = utf8;

create table t_tenant_config
(
    id                    bigint auto_increment
        primary key,
    tenant_id             bigint                             not null comment '租户ID',
    dynamic_datasource_id bigint                             not null comment '动态数据源ID',
    created_by            bigint   default 0                 null comment '创建人id',
    created_name          varchar(50)                        null comment '创建人名称',
    created_time          datetime default CURRENT_TIMESTAMP null comment '创建时间',
    last_modified_by      bigint   default 0                 null comment '更新人id',
    last_modified_name    varchar(50)                        null comment '更新人名称',
    last_modified_time    datetime                           null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '租户配置信息' charset = utf8;

create table t_user
(
    id                 bigint auto_increment comment 'ID'
        primary key,
    tenant_id          bigint                                 null comment '租户ID',
    username           varchar(30)                            not null comment '账号',
    password           varchar(200) default ''                null comment '密码',
    nick_name          varchar(50)                            null comment '昵称',
    org_id             bigint                                 null comment '组织ID
#c_core_org
@InjectionField(api = ORG_ID_CLASS, method = ORG_ID_METHOD, beanClass = Org.class) RemoteData<Long, com.github.zuihou.authority.entity.core.Org>',
    station_id         bigint                                 null comment '岗位ID
#c_core_station
@InjectionField(api = STATION_ID_CLASS, method = STATION_ID_NAME_METHOD) RemoteData<Long, String>',
    readonly           bit          default b'0'              not null comment '是否内置',
    email              varchar(50)                            null comment '邮箱',
    mobile             varchar(20)  default ''                null comment '手机',
    id_card            varchar(50)                            null comment '身份证',
    sex                tinyint(2)   default 1                 null comment '性别
#Sex{W:女;M:男;N:未知}',
    status             bit          default b'0'              null comment '状态 
1启用 0禁用',
    avatar             varchar(255) default ''                null comment '头像',
    description        varchar(255)                           null comment '描述',
    nation             varchar(20)                            null comment '民族
@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD, dictType = DictionaryType.NATION) RemoteData<String, String>
',
    education          varchar(20)                            null comment '学历
@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD, dictType = DictionaryType.EDUCATION) RemoteData<String, String>',
    position_status    varchar(20)                            null comment '职位状态
@InjectionField(api = DICTIONARY_ITEM_CLASS, method = DICTIONARY_ITEM_METHOD, dictType = DictionaryType.POSITION_STATUS) RemoteData<String, String>',
    birthday           date                                   null comment '生日',
    created_by         bigint       default 0                 null comment '创建人id',
    created_name       varchar(50)                            null comment '创建人名称',
    created_time       datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    last_modified_by   bigint       default 0                 null comment '更新人id',
    last_modified_name varchar(50)                            null comment '更新人名称',
    last_modified_time datetime                               null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint UN_ACCOUNT
        unique (username)
)
    comment '用户';

create table xxl_job_group
(
    id           int auto_increment
        primary key,
    app_name     varchar(64)       not null comment '执行器AppName',
    title        varchar(12)       not null comment '执行器名称',
    address_type tinyint default 0 not null comment '执行器地址类型：0=自动注册、1=手动录入',
    address_list text              null comment '执行器地址列表，多地址逗号分隔',
    update_time  datetime          null
);

create table xxl_job_info
(
    id                        int auto_increment
        primary key,
    job_group                 int                              not null comment '执行器主键ID',
    job_desc                  varchar(255)                     not null,
    add_time                  datetime                         null,
    update_time               datetime                         null,
    author                    varchar(64)                      null comment '作者',
    alarm_email               varchar(255)                     null comment '报警邮件',
    schedule_type             varchar(50) default 'NONE'       not null comment '调度类型',
    schedule_conf             varchar(128)                     null comment '调度配置，值含义取决于调度类型',
    misfire_strategy          varchar(50) default 'DO_NOTHING' not null comment '调度过期策略',
    executor_route_strategy   varchar(50)                      null comment '执行器路由策略',
    executor_handler          varchar(255)                     null comment '执行器任务handler',
    executor_param            varchar(512)                     null comment '执行器任务参数',
    executor_block_strategy   varchar(50)                      null comment '阻塞处理策略',
    executor_timeout          int         default 0            not null comment '任务执行超时时间，单位秒',
    executor_fail_retry_count int         default 0            not null comment '失败重试次数',
    glue_type                 varchar(50)                      not null comment 'GLUE类型',
    glue_source               mediumtext                       null comment 'GLUE源代码',
    glue_remark               varchar(128)                     null comment 'GLUE备注',
    glue_updatetime           datetime                         null comment 'GLUE更新时间',
    child_jobid               varchar(255)                     null comment '子任务ID，多个逗号分隔',
    trigger_status            tinyint     default 0            not null comment '调度状态：0-停止，1-运行',
    trigger_last_time         bigint(13)  default 0            not null comment '上次调度时间',
    trigger_next_time         bigint(13)  default 0            not null comment '下次调度时间'
);

create table xxl_job_lock
(
    lock_name varchar(50) not null comment '锁名称'
        primary key
);

create table xxl_job_log
(
    id                        bigint auto_increment
        primary key,
    job_group                 int               not null comment '执行器主键ID',
    job_id                    int               not null comment '任务，主键ID',
    executor_address          varchar(255)      null comment '执行器地址，本次执行的地址',
    executor_handler          varchar(255)      null comment '执行器任务handler',
    executor_param            varchar(512)      null comment '执行器任务参数',
    executor_sharding_param   varchar(20)       null comment '执行器任务分片参数，格式如 1/2',
    executor_fail_retry_count int     default 0 not null comment '失败重试次数',
    trigger_time              datetime          null comment '调度-时间',
    trigger_code              int               not null comment '调度-结果',
    trigger_msg               text              null comment '调度-日志',
    handle_time               datetime          null comment '执行-时间',
    handle_code               int               not null comment '执行-状态',
    handle_msg                text              null comment '执行-日志',
    alarm_status              tinyint default 0 not null comment '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败'
);

create index I_handle_code
    on xxl_job_log (handle_code);

create index I_trigger_time
    on xxl_job_log (trigger_time);

create table xxl_job_log_report
(
    id            int auto_increment
        primary key,
    trigger_day   datetime      null comment '调度-时间',
    running_count int default 0 not null comment '运行中-日志数量',
    suc_count     int default 0 not null comment '执行成功-日志数量',
    fail_count    int default 0 not null comment '执行失败-日志数量',
    update_time   datetime      null,
    constraint i_trigger_day
        unique (trigger_day)
);

create table xxl_job_logglue
(
    id          int auto_increment
        primary key,
    job_id      int          not null comment '任务，主键ID',
    glue_type   varchar(50)  null comment 'GLUE类型',
    glue_source mediumtext   null comment 'GLUE源代码',
    glue_remark varchar(128) not null comment 'GLUE备注',
    add_time    datetime     null,
    update_time datetime     null
);

create table xxl_job_registry
(
    id             int auto_increment
        primary key,
    registry_group varchar(50)  not null,
    registry_key   varchar(255) not null,
    registry_value varchar(255) not null,
    update_time    datetime     null
);

create index i_g_k_v
    on xxl_job_registry (registry_group, registry_key, registry_value);

create table xxl_job_user
(
    id         int auto_increment
        primary key,
    username   varchar(50)  not null comment '账号',
    password   varchar(50)  not null comment '密码',
    role       tinyint      not null comment '角色：0-普通用户、1-管理员',
    permission varchar(255) null comment '权限：执行器ID列表，多个逗号分割',
    constraint i_username
        unique (username)
);
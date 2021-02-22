-- auto-generated definition
create table qjwdzs_instance
(
    deinid       varchar not null,
    dclid     varchar,
    deinname     varchar,
    deintype     varchar,
    deincode     varchar,
    content     varchar,
    status      varchar,
    configs     text,
    description varchar,
    icon        varchar,
    cuserid     varchar,
    ctime       date,
    seqno       double precision,
    enable      varchar
);

comment on table qjwdzs_instance is '数据实例';

comment on column qjwdzs_instance.deinid is '实例编号（唯一）';

comment on column qjwdzs_instance.dclid is '目录编号（上级目录）';

comment on column qjwdzs_instance.deinname is '实例名称';

comment on column qjwdzs_instance.deintype is '实例类型';

comment on column qjwdzs_instance.deincode is '实例代码';

comment on column qjwdzs_instance.content is '内容';

comment on column qjwdzs_instance.status is '状态';

comment on column qjwdzs_instance.configs is '配置';

comment on column qjwdzs_instance.description is '描述';

comment on column qjwdzs_instance.icon is '图标';

comment on column qjwdzs_instance.cuserid is '用户编号（创建人）';

comment on column qjwdzs_instance.ctime is '创建时间';

comment on column qjwdzs_instance.seqno is '排序号';

comment on column qjwdzs_instance.enable is '是否启用（1 启用/0 禁用）';

alter table qjwdzs_instance
    owner to postgres;

-- auto-generated definition
create table qjwdzs_catalog
(
    dclid     varchar not null,
    dclpid    varchar,
    dclcode   varchar,
    dclname   varchar,
    dcltype   varchar,
    description varchar,
    cuserid     varchar,
    ctime       date,
    seqno       double precision,
    enable      varchar
);

comment on table qjwdzs_catalog is '项目目录';

comment on column qjwdzs_catalog.dclid is '目录编号';

comment on column qjwdzs_catalog.dclpid is '上级目录编号';

comment on column qjwdzs_catalog.dclcode is '目录编码';

comment on column qjwdzs_catalog.dclname is '目录名称';

comment on column qjwdzs_catalog.dcltype is '目录类型';

comment on column qjwdzs_catalog.description is '描述说明';

comment on column qjwdzs_catalog.cuserid is '用户编号（创建人）';

comment on column qjwdzs_catalog.ctime is '创建时间';

comment on column qjwdzs_catalog.seqno is '排序';

comment on column qjwdzs_catalog.enable is '是否启用';

alter table qjwdzs_catalog
    owner to postgres;


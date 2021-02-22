
-- create table
create table qjwdzs_instance
(
  deinid        varchar2(100) default sys_guid() not null comment '考核环节编号',
  dclid       varchar2(100),
  deincode      varchar2(50),
  deinname      varchar2(500),
  deintype      varchar2(50),
  content     clob,
  configs     clob,
  status      varchar2(10),
  icon     varchar2(50),
  description varchar2(500),
  cuserid     varchar2(50),
  ctime       date default sysdate,
  seqno       number,
  enable      varchar2(10)
);
-- add comments to the table
comment on table qjwdzs_instance
  is '数据实例';
-- add comments to the columns
comment on column qjwdzs_instance.deinid
  is '编号';
comment on column qjwdzs_instance.dclid
  is '数据目录编号';
comment on column qjwdzs_instance.deincode
  is '编码';
comment on column qjwdzs_instance.deinname
  is '名称';
comment on column qjwdzs_instance.deintype
  is '类型';
comment on column qjwdzs_instance.content
  is '内容';
comment on column qjwdzs_instance.configs
  is '配置';
comment on column qjwdzs_instance.status
  is '状态';
comment on column qjwdzs_instance.icon
  is '图标';
comment on column qjwdzs_instance.description
  is '描述';
comment on column qjwdzs_instance.cuserid
  is '创建人';
comment on column qjwdzs_instance.ctime
  is '创建时间';
comment on column qjwdzs_instance.seqno
  is '排序';
comment on column qjwdzs_instance.enable
  is '是否启用';


  -- create table
create table qjwdzs_catalog
(
  dclid       varchar2(100) default sys_guid() not null,
  dclpid      varchar2(50) default 0,
  dclcode     varchar2(50),
  dclname     varchar2(500),
  dcltype     varchar2(50),
  description varchar2(500),
  cuserid     varchar2(50),
  ctime       date default sysdate,
  seqno       number,
  enable      varchar2(10)
) ;
-- add comments to the table
comment on table qjwdzs_catalog
  is '项目目录';
-- add comments to the columns
comment on column qjwdzs_catalog.dclid
  is '编号';
comment on column qjwdzs_catalog.dclpid
  is '父级编号';
comment on column qjwdzs_catalog.dclcode
  is '代码';
comment on column qjwdzs_catalog.dclname
  is '名称';
comment on column qjwdzs_catalog.description
  is '描述';
comment on column qjwdzs_catalog.cuserid
  is '创建人';
comment on column qjwdzs_catalog.ctime
  is '创建时间';
comment on column qjwdzs_catalog.seqno
  is '排序';
comment on column qjwdzs_catalog.enable
  is '是否启用';


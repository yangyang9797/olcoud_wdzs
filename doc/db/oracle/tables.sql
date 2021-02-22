
-- Create table
create table DEMO_INSTANCE
(
  deinid        VARCHAR2(100) default sys_guid() not null,
  dclid       VARCHAR2(100),
  deincode      VARCHAR2(50),
  deinname      VARCHAR2(500),
  deintype      VARCHAR2(50),
  content     CLOB,
  configs     CLOB,
  status      VARCHAR2(10),
  icon     VARCHAR2(50),
  description VARCHAR2(500),
  cuserid     VARCHAR2(50),
  ctime       DATE default sysdate,
  seqno       NUMBER,
  enable      VARCHAR2(10)
);
-- Add comments to the table
comment on table DEMO_INSTANCE
  is '数据实例';
-- Add comments to the columns
comment on column DEMO_INSTANCE.deinid
  is '编号';
comment on column DEMO_INSTANCE.dclid
  is '数据目录编号';
comment on column DEMO_INSTANCE.deincode
  is '编码';
comment on column DEMO_INSTANCE.deinname
  is '名称';
comment on column DEMO_INSTANCE.deintype
  is '类型';
comment on column DEMO_INSTANCE.content
  is '内容';
comment on column DEMO_INSTANCE.configs
  is '配置';
comment on column DEMO_INSTANCE.status
  is '状态';
comment on column DEMO_INSTANCE.icon
  is '图标';
comment on column DEMO_INSTANCE.description
  is '描述';
comment on column DEMO_INSTANCE.cuserid
  is '创建人';
comment on column DEMO_INSTANCE.ctime
  is '创建时间';
comment on column DEMO_INSTANCE.seqno
  is '排序';
comment on column DEMO_INSTANCE.enable
  is '是否启用';


  -- Create table
create table DEMO_CATALOG
(
  dclid       VARCHAR2(100) default sys_guid() not null,
  dclpid      VARCHAR2(50) default 0,
  dclcode     VARCHAR2(50),
  dclname     VARCHAR2(500),
  dcltype     VARCHAR2(50),
  description VARCHAR2(500),
  cuserid     VARCHAR2(50),
  ctime       DATE default sysdate,
  seqno       NUMBER,
  enable      VARCHAR2(10)
) ;
-- Add comments to the table
comment on table DEMO_CATALOG
  is '项目目录';
-- Add comments to the columns
comment on column DEMO_CATALOG.dclid
  is '编号';
comment on column DEMO_CATALOG.dclpid
  is '父级编号';
comment on column DEMO_CATALOG.dclcode
  is '代码';
comment on column DEMO_CATALOG.dclname
  is '名称';
comment on column DEMO_CATALOG.description
  is '描述';
comment on column DEMO_CATALOG.cuserid
  is '创建人';
comment on column DEMO_CATALOG.ctime
  is '创建时间';
comment on column DEMO_CATALOG.seqno
  is '排序';
comment on column DEMO_CATALOG.enable
  is '是否启用';


-- Create table
drop table SN_NUMBER_LOG;
create table SN_NUMBER_LOG
(
  id            NUMBER,
  sn           VARCHAR2(32),
  rundate       DATE,
  ruid          VARCHAR2(32),
  runame        VARCHAR2(32),
  prefix        VARCHAR2(32),
  bus_type      VARCHAR2(32),
  sn_type       number(2),
  invoke_code   VARCHAR2(1000),
  option_info   VARCHAR2(32),
  run_memo      VARCHAR2(32),
  runtime_duration NUMBER(16),
  runtime_start TIMESTAMP(6),
  runtime_end   TIMESTAMP(6)
);
-- Add comments to the table 
comment on table SN_NUMBER_LOG
  is '单号生成日志表';
-- Add comments to the columns 
comment on column SN_NUMBER_LOG.sn
  is '单号';
comment on column SN_NUMBER_LOG.ruid
  is '请求人id';
comment on column SN_NUMBER_LOG.runame
  is '请求人姓名';
comment on column SN_NUMBER_LOG.prefix
  is '前缀';
comment on column SN_NUMBER_LOG.bus_type
  is '编号业务类型';
comment on column SN_NUMBER_LOG.sn_type
  is '单号计数类型 0每天计数从1开始 1计数累加不清零';
comment on column SN_NUMBER_LOG.rundate
  is '单号生成时间';
comment on column SN_NUMBER_LOG.invoke_code
  is '备注（调用类）';
comment on column SN_NUMBER_LOG.option_info
  is '备注如精度等';
comment on column SN_NUMBER_LOG.run_memo
  is '运行备注';
comment on column SN_NUMBER_LOG.runtime_duration
  is '运行时长单位毫秒';
comment on column SN_NUMBER_LOG.runtime_start
  is '运行开始时间精确到毫秒';
comment on column SN_NUMBER_LOG.runtime_end
  is '运行结束时间精确到毫秒';

  alter table SN_NUMBER_LOG
  add constraint PK_SN_NUMBER_LOG primary key (ID);
alter table sn_number_log modify (rundate default sysdate);

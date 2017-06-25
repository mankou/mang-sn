-- Create table
drop table SN_NUMBER;
create table SN_NUMBER
(
  id        NUMBER,
  sn_type number(2),
  bus_type  VARCHAR2(32),
  maxindex  INTEGER,
  num_date  DATE
)
;
comment on table SN_NUMBER
  is '编号记录表';
comment on column SN_NUMBER.maxindex
  is '当前最大编号值';
comment on column SN_NUMBER.bus_type
  is '编号业务类型';
comment on column SN_NUMBER.num_date
  is '当前日期(只用于code_type=0的情况)';
comment on column SN_NUMBER.sn_type
  is '单号类型0时间类型 Z20150616-2 数字每天从1开始  1数字类型T00000001 数字累加 2时间数字类型 Z20150616-2 但数字是累加的';
alter table SN_NUMBER
  add constraint PK_SN_NUMBER primary key (ID);



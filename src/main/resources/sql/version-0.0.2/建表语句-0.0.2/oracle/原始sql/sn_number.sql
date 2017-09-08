-- Create table
drop table SN_NUMBER;
create table SN_NUMBER
(
  id       varchar2(32),
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
  is '单号计数类型0每天从1开始 1计数一直累加不清零 2计数每周从1开始 3计数每月从1开始 4计数每年从1开始';
alter table SN_NUMBER
  add constraint PK_SN_NUMBER primary key (ID);


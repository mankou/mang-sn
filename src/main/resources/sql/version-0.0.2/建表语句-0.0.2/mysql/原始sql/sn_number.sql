-- create table
DROP TABLE IF EXISTS `sn_number`;
CREATE TABLE sn_number
(
  id       VARCHAR(32),
  sn_type  DECIMAL(2) COMMENT '单号计数类型0每天从1开始 1计数一直累加不清零 2计数每周从1开始 3计数每月从1开始 4计数每年从1开始' ,
  bus_type  VARCHAR(32) COMMENT '编号业务类型',
  maxindex  INTEGER COMMENT '当前最大编号值',
  num_date  DATETIME COMMENT '当前日期(只用于code_type=0的情况)'
)
;

ALTER TABLE sn_number COMMENT '编号记录表';


ALTER TABLE sn_number
  ADD CONSTRAINT pk_sn_number PRIMARY KEY (id);


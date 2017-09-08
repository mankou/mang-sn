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

-- create table
DROP TABLE  IF EXISTS sn_number_log;
CREATE TABLE sn_number_log
(
  id           VARCHAR(32),
  sn           VARCHAR(32)  COMMENT '单号' ,
  rundate       DATETIME  DEFAULT CURRENT_TIMESTAMP() COMMENT '单号生成时间' ,
  ruid          VARCHAR(32) COMMENT '请求人id',
  runame        VARCHAR(32) COMMENT '请求人姓名',
  prefix        VARCHAR(32) COMMENT '前缀',
  bus_type      VARCHAR(32) COMMENT '编号业务类型',
  sn_type       DECIMAL(2) COMMENT '单号计数类型0每天从1开始 1计数一直累加不清零 2计数每周从1开始 3计数每月从1开始 4计数每年从1开始',
  invoke_code   VARCHAR(1000) COMMENT '备注（调用类）',
  option_info   VARCHAR(32) COMMENT '备注如精度等',
  run_memo      VARCHAR(32) COMMENT '运行备注',
  runtime_duration DECIMAL COMMENT '运行时长单位毫秒',
  runtime_start DATETIME(6) COMMENT '运行开始时间精确到毫秒',
  runtime_end   DATETIME(6) COMMENT '运行结束时间精确到毫秒'
) COMMENT '单号生成日志表'
;

ALTER TABLE sn_number_log ADD CONSTRAINT pk_sn_number_log PRIMARY KEY (id);

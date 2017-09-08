SELECT * FROM sn_number t;



-- 计数每天从1开始计

SELECT 
  * 
FROM
  sn_number t 
WHERE 1 = 1 
  AND t.bus_type = 'test' 
  AND DATE_FORMAT(t.num_date, '%Y-%m-%d') = DATE_FORMAT(SYSDATE(), '%Y-%m-%d') 
  AND t.sn_type = 0 
  FOR  UPDATE 
    ;






SELECT t.*,t.rowid FROM  sn_number t;





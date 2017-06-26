select * from sn_number t;

select * from sn_number_log t order by t.rundate desc;


select trunc(sysdate,'iw') - 7 from dual;---ÉÏÖÜÒ» 

select trunc(sysdate,'d')+1 from dual;


select t.*,t.rowid from  sn_number t;

select *
  from sn_number t
 where t.bus_type ='test'
   and t.num_date>=trunc(sysdate,'d')+1 
   and t.num_date<sysdate
   and t.sn_type =1
   for update
   ;




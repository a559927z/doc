drop procedure if exists `pro_fetch_population_relation_month`; 
create procedure pro_fetch_population_relation_month(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32))
begin 
  declare _day date;
  declare _year_month,_toatl int(8);
  declare _total int;
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '����Ⱥ�±�';
	DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_procedure_info where pro_name = 'pro_fetch_population_relation_month');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	
  START TRANSACTION;

	set _day=(select date_format(now(),'%Y%m%d'));
	set _year_month = (select cast(substr(now(),1,4) as int)) ;
  select count(*) into _toatl from population_relation_month where `year_month`= _year_month;
  if _toatl = 0 then 
		insert into population_relation_month select replace(uuid(),'-',''),customer_id,population_id,emp_id,_year_month from
    population_relation a,
    v_dim_emp b
    where 
    a.emp_id=b.emp_id and 
    a.days=_day and 
    ifnull(b.run_off_date,_day)>=_day;
  else 
    update population_relation_month a set population_id=(select population_id from population_relation b where a.emp_id=b.emp_id and days=_day) where `year_month`=_year_month;
  end if;
 	  
    INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_population_relation_month', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		
   set @bass_error_mgs = p_error;    

end;

DROP PROCEDURE IF EXISTS `pro_fetch_emp_overtime_other_day`;
create  procedure pro_fetch_emp_overtime_other_day(p_customer_id varchar(32),p_user varchar(10))
BEGIN
    DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

  START TRANSACTION;
   INSERT INTO emp_overtime_day (
    emp_overtime_day_id,
    customer_id,
    emp_id,
    emp_key,
    user_name_ch,
    population_id,
    hour_count,
    organization_id,
    checkwork_type_id,
    days,
    c_id)
    select replace(uuid(),'-',''),
           customer_id,
           emp_id,
           emp_key,
           user_name_ch,
           population_id,
           (TIMESTAMPDIFF(MINUTE,startdate,enddate) / 60) as hour_count,
           organization_id,
           checkwork_type_id,
           days,
           c_id
    from soure_emp_overtime_other_day t where checkwork_type_id='7f533380bf574dd9916972a08808e121' 
		on duplicate key update 
          emp_id = t.emp_id,
          emp_key=t.emp_key,
          user_name_ch = t.user_name_ch,
          hour_count= TIMESTAMPDIFF(MINUTE,startdate,enddate) / 60,
          organization_id =t.organization_id,
          checkwork_type_id=t.checkwork_type_id,
          days=t.days
   ;

    INSERT INTO emp_other_day (
    emp_other_day_id,
    customer_id,
    emp_id,
    emp_key,
    user_name_ch,
    hour_count,
    organization_id,
    checkwork_type_id,
    days,
    c_id)
    select replace(uuid(),'-',''),
           customer_id,
           emp_id,
           emp_key,
           user_name_ch,
           (TIMESTAMPDIFF(MINUTE,startdate,enddate) / 60) as hour_count,
           organization_id,
           checkwork_type_id,
           days,
           c_id
    from soure_emp_overtime_other_day t1 where checkwork_type_id<>'7f533380bf574dd9916972a08808e121' 
		on duplicate key update 
          emp_id = t1.emp_id,
          emp_key=t1.emp_key,
          user_name_ch = t1.user_name_ch,
          hour_count= TIMESTAMPDIFF(MINUTE,startdate,enddate) / 60,
          organization_id =t1.organization_id,
          checkwork_type_id=t1.checkwork_type_id,
          days=t1.days;

		IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
	END IF;

END;
CALL pro_fetch_emp_overtime_other_day('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');
-- TRUNCATE TABLE emp_overtime_day;
-- TRUNCATE TABLE emp_other_day;
-- 
-- select * from soure_emp_overtime_other_day;
-- -- 

-- select * from dim_checkwork_type;


#=======================================================pro_fetch_dim_emp_days=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_emp_days`;
CREATE PROCEDURE pro_fetch_dim_emp_days(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_now datetime)
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE now TIMESTAMP DEFAULT p_now -- '2015-12-17'
-- 	DECLARE now TIMESTAMP DEFAULT now() -- '2015-12-17'
	;
	

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		DELETE FROM dim_emp where days = now AND customer_id = customerId;

					INSERT INTO dim_emp (
						dim_emp_id,customer_id,emp_id,emp_key,user_name,user_name_ch,emp_hf_id,emp_hf_key,
						report_relation,email,img_path,passtime_or_fulltime,contract,blood,age,company_age,is_key_talent,
						sex,nation,degree_id,degree,native_place,country,birth_place,birth_date,national_id,national_type,marry_status,
						patty_name,position_id,position_name,organization_parent_id,organization_parent_name,organization_id,organization_name,
						sequence_id,sequence_name,sequence_sub_id,sequence_sub_name,performance_id,performance_name,ability_id,job_title_id,ability_lv_id,
						ability_name,job_title_name,ability_lv_name,rank_name,population_id,population_name,area_id,run_off_date,entry_date,tel_phone,qq,wx_code,
						address,contract_unit,work_place_id,work_place,regular_date,apply_type,channel_id,speciality,is_regular,refresh_date,c_id, days, year
					)
SELECT replace(UUID(),'-',''),customer_id,emp_id,emp_key,user_name,user_name_ch,emp_hf_id,emp_hf_key,
						report_relation,email,img_path,passtime_or_fulltime,contract,blood,age,company_age,is_key_talent,
						sex,nation,degree_id,degree,native_place,country,birth_place,birth_date,national_id,national_type,marry_status,
						patty_name,position_id,position_name,organization_parent_id,organization_parent_name,organization_id,organization_name,
						sequence_id,sequence_name,sequence_sub_id,sequence_sub_name,performance_id,performance_name,ability_id,job_title_id,ability_lv_id,
						ability_name,job_title_name,ability_lv_name,rank_name,population_id,population_name,area_id,run_off_date,entry_date,tel_phone,qq,wx_code,
						address,contract_unit,work_place_id,work_place,regular_date,apply_type,channel_id,speciality,is_regular,refresh_date,c_id, now, YEAR(now)
FROM (
					SELECT 
						customer_id,emp_id,emp_key,user_name,user_name_ch,emp_hf_id,emp_hf_key,
						report_relation,email,img_path,passtime_or_fulltime,contract,blood,age,company_age,is_key_talent,
						sex,nation,degree_id,degree,native_place,country,birth_place,birth_date,national_id,national_type,marry_status,
						patty_name,position_id,position_name,organization_parent_id,organization_parent_name,organization_id,organization_name,
						sequence_id,sequence_name,sequence_sub_id,sequence_sub_name,performance_id,performance_name,ability_id,job_title_id,ability_lv_id,
						ability_name,job_title_name,ability_lv_name,rank_name,population_id,population_name,area_id,run_off_date,entry_date,tel_phone,qq,wx_code,
						address,contract_unit,work_place_id,work_place,regular_date,apply_type,channel_id,speciality,is_regular,refresh_date,c_id, now, YEAR(now)
					FROM v_dim_emp t 
					WHERE t.customer_id = customerId
						AND
						t.entry_date >= '2010-01-01 00:00:00' AND
						t.run_off_date <= p_now 
					UNION 
						SELECT 
						customer_id,emp_id,emp_key,user_name,user_name_ch,emp_hf_id,emp_hf_key,
						report_relation,email,img_path,passtime_or_fulltime,contract,blood,age,company_age,is_key_talent,
						sex,nation,degree_id,degree,native_place,country,birth_place,birth_date,national_id,national_type,marry_status,
						patty_name,position_id,position_name,organization_parent_id,organization_parent_name,organization_id,organization_name,
						sequence_id,sequence_name,sequence_sub_id,sequence_sub_name,performance_id,performance_name,ability_id,job_title_id,ability_lv_id,
						ability_name,job_title_name,ability_lv_name,rank_name,population_id,population_name,area_id,run_off_date,entry_date,tel_phone,qq,wx_code,
						address,contract_unit,work_place_id,work_place,regular_date,apply_type,channel_id,speciality,is_regular,refresh_date,c_id, now, YEAR(now)
					FROM v_dim_emp t 
					WHERE  t.run_off_date is null 
) t;




				IF p_error = 1 THEN ROLLBACK;
				ELSE COMMIT;
				END IF;


	
END;
-- DELIMITER ;

CALL pro_fetch_dim_emp_days('b5c9410dc7e4422c8e0189c7f8056b5f','DBA','2013-06-16');

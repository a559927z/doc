-- 360测评时段
drop procedure if exists pro_fetch_360_time;
CREATE  PROCEDURE `pro_fetch_360_time`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【历史表-360测评时段】';

	INSERT INTO 360_time(
		360_time_id,customer_id,emp_id,report_date,report_name,path,type,`year`)
	SELECT 
		360_time_id,customerId,emp_id,report_date,report_name,path,type,`year` 
	FROM `mup-source`.source_360_time a
	ON DUPLICATE KEY UPDATE 
		report_date=a.report_date,
		report_name=a.report_name,
		path=a.path,
		type=a.type,
		`year`=a.`year`;
END;

 -- 360测评报告
DROP PROCEDURE IF EXISTS pro_fetch_360_report;
CREATE  PROCEDURE `pro_fetch_360_report`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【历史表-360测评报告】';
 
	INSERT INTO 360_report(360_report_id,customer_id,emp_id,360_ability_id,360_ability_name,360_ability_lv_id,360_ability_lv_name,standard_score,gain_score,score,module_type,360_time_id)
	SELECT 360_report_id,p_customer_id,emp_id,360_ability_id,360_ability_name,360_ability_lv_id,360_ability_lv_name,standard_score,gain_score,score,module_type,360_time_id 
	FROM `mup-source`.source_360_report a
	ON DUPLICATE KEY UPDATE 
		360_ability_id=a.360_ability_id,
		360_ability_name=a.360_ability_name,
		360_ability_lv_id=a.360_ability_lv_id,
		360_ability_lv_id=a.360_ability_lv_id,
		standard_score=a.standard_score,
		gain_score=a.gain_score,
		score=a.score,
		module_type=a.module_type,
		360_time_id=a.360_time_id;
end;

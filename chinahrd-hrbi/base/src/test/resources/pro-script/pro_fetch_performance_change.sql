-- =======================================pro_fetch_performance_change============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_organization、dim_emp、dim_performance、dim_grade表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_performance_change`;
CREATE PROCEDURE pro_fetch_performance_change(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_nowTime datetime)
BEGIN

	 	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(p_nowTime, Interval 0 minute),'%Y%m');


	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【绩效-历史表】：',yearMonth,'数据刷新完成');
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;


		START TRANSACTION;
			REPLACE INTO performance_change(
						performance_change_id, customer_id, 
						emp_key, emp_id,
						organization_name,  performance_name, grade_name,
						organization_id, performance_id, grade_id,
						organization_parent_id, organization_parent_name,
						position_id, position_name,
						score,
						rank_name,
						`type`,
						`year_month`
						)
			SELECT 
				tt.id, tt.customer_id,
				t2.emp_key, t2.emp_id,
				tt.organization_name, tt.performance_name, tt.grade_name, 
				t2.organization_id, t3.performance_id, t4.grade_id,
				t5.organization_id organization_parent_id, t5.organization_name organization_parent_name,
				t2.position_id, t2.position_name,
				tt.score,
				t7.rank_name,
				tt.`type`,
				tt.`year_month`
			FROM soure_performance_change tt
			INNER JOIN v_dim_emp t2 on t2.emp_key = tt.emp_key
					AND t2.customer_id = tt.customer_id
			INNER JOIN dim_performance t3 on t3.performance_key = tt.performance_key
					AND t3.customer_id = tt.customer_id
			INNER JOIN dim_grade t4 on t4.grade_key = tt.grade_key
					AND t4.customer_id = tt.customer_id
			INNER JOIN dim_organization t5 ON tt.organization_parent_key = t5.organization_key
					AND t5.customer_id = tt.customer_id
			INNER JOIN dim_position t6 ON t6.position_key = tt.position_key
					AND t6.customer_id = tt.customer_id
			INNER JOIN job_change t7 on t7.emp_key = tt.emp_key AND  t7.change_date = (SELECT MAX(change_date) FROM job_change WHERE emp_key= tt.emp_key) 
				AND t7.customer_id = tt.customer_id
			WHERE tt.customer_id = customerId
				AND	tt.`year_month` = yearMonth 
			;

		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【绩效-历史表】：",yearMonth,"数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'performance_change', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'performance_change', p_message, startTime, now(), 'sucess' );
		END IF;


END;
-- DELIMITER ;
SELECT count(1) FROM soure_performance_change;
SELECT count(1) FROM performance_change;
-- DELETE FROM performance_change where `YEAR_MONTH` = 201506
	truncate performance_change;
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2010-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2010-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2011-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2011-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2012-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2012-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-06-10'); -- 绩效信息（周期事件）
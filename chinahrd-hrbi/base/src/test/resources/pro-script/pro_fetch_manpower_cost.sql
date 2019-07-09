#=======================================================pro_fetch_manpower_cost=======================================================
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 'demo','jxzhang'
--  保证：dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_manpower_cost`;
CREATE PROCEDURE pro_fetch_manpower_cost(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_datetime DATETIME)
BEGIN

	DECLARE pCurDate TIMESTAMP DEFAULT p_datetime;

	-- 本月第一天
	DECLARE firstCurDate DATETIME DEFAULT (SELECT DATE_SUB(pCurDate, INTERVAL extract( DAY FROM pCurDate)-1 DAY));
	-- 本月最后一天
	-- DECLARE lastCurDate DATE DEFAULT (SELECT DATE_SUB( DATE_ADD(pCurDate, INTERVAL 1 MONTH), INTERVAL DAY (pCurDate) DAY ));

	-- 上月第一天
-- 	DECLARE firstCurDate DATE DEFAULT  (SELECT DATE_SUB(DATE_SUB(pCurDate, INTERVAL extract( DAY FROM pCurDate)-1 DAY), interval 1 MONTH));
	-- 上月最后一天
-- 	DECLARE lastCurDate DATE DEFAULT (SELECT DATE_SUB(DATE_SUB(pCurDate, INTERVAL extract( DAY FROM pCurDate)-1 DAY), INTERVAL 1 DAY));

	DECLARE ym INT DEFAULT date_format(pCurDate,'%Y%m');


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();


	DECLARE funId VARCHAR(50);
	DECLARE empRank VARCHAR(50);

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【人力成本-业务表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;
				
				-- 人力成本ID
				SET funId = (SELECT function_id FROM dim_function WHERE function_key = 'RenLiChengBen' and customer_id = customerId );
				-- 配置表，人类型范围
				SET empRank = (SELECT config_val FROM config WHERE config_key = 'RLCB-personType' AND function_id = funId AND customer_id = customerId);


-- TODO empRank = 2，3，4 要带入history_emp_count_month.type 里条件
					REPLACE INTO manpower_cost (
						manpower_cost_id,
						customer_id,
						organization_id,
						cost,
						emp_num,
						cost_avg,
						cost_budget,
						cost_company,
						`year_month`
					)
					SELECT 
						t.id,
						customerId,
						t1.organization_id,
						t.cost,
						t2.month_avg_sum AS empSum,
						t.cost / t2.month_avg_sum AS costAvg,
						t.cost_budget,
						t.cost_company,
						t.`year_month`
					FROM soure_manpower_cost t 
					INNER JOIN dim_organization t1 on  t.organization_key = t1.organization_key and t1.customer_id = t.customer_id
					INNER JOIN history_emp_count_month t2 on t2.organization_id = t1.organization_id AND t2.`year_month` = ym AND t2.customer_id = t1.customer_id
					WHERE t.customer_id = customerId;


	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
	END IF;


	
END;
-- DELIMITER ;
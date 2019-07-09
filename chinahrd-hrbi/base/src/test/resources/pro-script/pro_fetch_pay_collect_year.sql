#=======================================================pro_fetch_pay_collect_year=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_pay_collect_year`;
CREATE PROCEDURE pro_fetch_pay_collect_year(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_y INTEGER(4))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE limitStart INT;
	
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
							
			DELETE FROM pay_collect_year WHERE `year` = p_y;
			INSERT INTO pay_collect_year (
				pay_collect_year_id,
				customer_id,
				organization_id,
				sum_pay,
				avg_pay,
				sum_salary,
				avg_salary,
				sum_welfare,
				avg_welfare,
				sum_bonus,
				sum_share,
				count_share,
				`year`
			)
			SELECT 
				replace(UUID(), '-', ''),
				customer_id,
				organization_id,
				sum(sum_pay),
				sum(sum_pay) / (sum(avg_emp_num)/12), 
				sum(sum_salary),
				sum(sum_salary) / (sum(avg_emp_num)/12), 
				sum(sum_welfare),
				sum(sum_welfare) / (sum(avg_emp_num)/12), 
				sum(sum_bonus),
				0,0,
				p_y
			FROM pay_collect t
			WHERE t.customer_id = customerId 
				AND	locate(p_y, t.`year_month`)
			GROUP BY t.organization_id
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 

			LB_CURSOR:BEGIN
					
					DECLARE sShare, cShare	INT;

					DECLARE orgId VARCHAR(50);
					DECLARE fp TEXT;
					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT 
							t2.organization_id orgId, t3.full_path fp
						FROM pay_collect_year t2 
						INNER JOIN dim_organization t3 on t2.organization_id = t3.organization_id
						WHERE t2.customer_id = customerId 
							AND t2.`year` = p_y
						GROUP BY t2.organization_id
						;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;

					OPEN s_cur;
					FETCH s_cur INTO orgId, fp;
						WHILE1: WHILE done != 1 DO
							SELECT IFNULL(SUM(now_share),0) sShare, IFNULL(count(emp_id),0) cShare 
							INTO	sShare, cShare
							FROM share_holding t 
							WHERE 
								 t.customer_id = customerId
-- 								AND t.organization_id = orgId 
								AND LOCATE(fp, t.full_path)
								;

-- 							UPDATE pay_collect_year SET sum_share = sShare, count_share = cShare
-- 							WHERE organization_id = orgId AND `year` = p_y AND customer_id = customerId;
							FETCH s_cur INTO orgId, fp;
						END WHILE WHILE1;
					CLOSE s_cur;


				END LB_CURSOR;



	END IF;

END;
-- DELIMITER ;
-- 	CALL pro_fetch_pay_collect_year('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2011);	
-- 	CALL pro_fetch_pay_collect_year('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2012);	
	CALL pro_fetch_pay_collect_year('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2013);	
-- 	CALL pro_fetch_pay_collect_year('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2014);	
-- 	CALL pro_fetch_pay_collect_year('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2015);	
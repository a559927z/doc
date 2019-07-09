#=======================================================pro_fetch_share_holding=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_share_holding`;
CREATE PROCEDURE pro_fetch_share_holding(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

	-- 删除六年前数据
			DELETE FROM soure_share_holding 
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
-- 						SELECT id FROM soure_share_holding t where `year_month` = DATE_FORMAT(DATE_SUB(now(), INTERVAL 6 YEAR), '%Y%m')
						SELECT id FROM soure_share_holding t where `year_month` = DATE_SUB(now(), INTERVAL 6 YEAR)
					) t
			);
		

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO share_holding (
							share_holding_id,
							customer_id,
							emp_id,
							usre_name_ch,
							organization_id,
							full_path,
							now_share,
							confer_share,
							price,
							hold_period,
							sub_num,
							sub_date
						)
						SELECT 
							id,
							customer_id,
							emp_id,
							usre_name_ch,
							organization_id,
							full_path,
							now_share,
							confer_share,
							price,
							hold_period,
							sub_num,
							sub_date
						FROM soure_share_holding t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								emp_id = t.emp_id,
								usre_name_ch = t.usre_name_ch,
								organization_id = t.organization_id,
								`full_path` = t.`full_path`,
								`now_share` = t.`now_share`,
								`confer_share` = t.`confer_share`,
								`price` = t.`price`,
								`hold_period` = t.`hold_period`,
								`sub_num` = t.`sub_date`,
								`sub_date` = t.`sub_date`
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM share_holding WHERE share_holding_id NOT IN 
			( SELECT t2.id FROM soure_share_holding t2 WHERE t2.customer_id = customerId); 
	END IF;


END;
-- DELIMITER ;

-- TRUNCATE TABLE share_holding;

CALL pro_fetch_share_holding('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');
#=======================================================pro_update_pay_collect_year_scShare=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_update_pay_collect_year_scShare`;
CREATE PROCEDURE pro_update_pay_collect_year_scShare(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_y INTEGER(4))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE limitStart INT;
	
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
							
			LB_CURSOR:BEGIN
					
					DECLARE sShare, cShare	INT;

					DECLARE pcId, orgId VARCHAR(32);
					DECLARE fp TEXT;
					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT 
							t2.pay_collect_year_id pcId, t2.organization_id orgId, t3.full_path fp
						FROM pay_collect_year t2 
						INNER JOIN dim_organization t3 on t2.organization_id = t3.organization_id
						WHERE t2.customer_id = customerId AND t2.`year` = p_y
						GROUP BY t2.organization_id
						;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;

					OPEN s_cur;
					FETCH s_cur INTO pcId, orgId, fp;
						WHILE1: WHILE done != 1 DO
							SELECT IFNULL(SUM(sum_share),0) sShare, IFNULL(sum(count_share),0) cShare 
							INTO	sShare, cShare
							FROM pay_collect_year t 
							INNER JOIN dim_organization t3 on t.organization_id = t3.organization_id
							WHERE t.customer_id = customerId
								AND LOCATE(fp, t3.full_path)
								AND `year` = p_y 
								-- AND locate(p_y, t.`year_month`) 
								;


							UPDATE pay_collect_year SET sum_share = sShare, count_share = cShare
							WHERE organization_id = orgId AND `year` = p_y AND customer_id = customerId;


							FETCH s_cur INTO pcId, orgId, fp;
						END WHILE WHILE1;
					CLOSE s_cur;
				END LB_CURSOR;


END;
-- DELIMITER ;
-- 	CALL pro_update_pay_collect_year_scShare('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2011);	
-- 	CALL pro_update_pay_collect_year_scShare('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2012);	
-- 	CALL pro_update_pay_collect_year_scShare('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2013);	
-- 	CALL pro_update_pay_collect_year_scShare('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2014);		
	CALL pro_update_pay_collect_year_scShare('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2015);	
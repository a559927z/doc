	#=======================================================pro_update_pay_crValue=======================================================
	-- 应响的表：pay,pay_collect,pay_collect_year
	-- 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '解密key', 发工资年月
	-- DELIMITER //
	DROP PROCEDURE IF EXISTS `pro_update_pay_crValue`;
	CREATE PROCEDURE pro_update_pay_crValue(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key2 VARCHAR(50), in p_ym INTEGER(6))
	BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;

		
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				
		-- 更新cr
			LB_CURSOR_CR:BEGIN

						DECLARE orgId VARCHAR(32);
						DECLARE qv DOUBLE(10,4);
						
						DECLARE payId, empId VARCHAR(32);
						DECLARE cr DOUBLE(10,4);

						DECLARE done INT DEFAULT 0;
						DECLARE s_cur1 CURSOR FOR
							SELECT 
								organization_id orgId,
								quantile_value qv
							FROM pay_collect WHERE customer_id = customerId	AND `year_month` = p_ym;

						DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
									
						OPEN s_cur1;
						FETCH s_cur1 INTO orgId, qv;

							WHILE1: WHILE done != 1 DO
							
								INSERT INTO pay
								(
									pay_id,
									customer_id,
									emp_id,
									usre_name_ch,
									organization_id,
									full_path,
									postion_id,
									pay_contract,
									pay_should,
									pay_actual,
									salary_actual,
									welfare_actual,
									bonus,
									cr_value,
									`year_month`

								)
								SELECT  
									pay_id,customer_id, emp_id, usre_name_ch, organization_id, full_path, postion_id, pay_contract, pay_should, pay_actual, salary_actual, welfare_actual, bonus, cr, `year_month`
								FROM (
										SELECT 
											pay_id,
											customer_id,
											emp_id,
											usre_name_ch,
											organization_id,
											full_path,
											postion_id,
											pay_contract,
											pay_should,
											pay_actual,
											salary_actual,
											welfare_actual,
											bonus,
											AES_DECRYPT(salary_actual, p_key2) / qv AS cr,
											`year_month`
										FROM pay 
										WHERE `year_month` = p_ym AND organization_id = orgId
								) t
							ON DUPLICATE KEY UPDATE
									cr_value = t.cr;
							
								FETCH s_cur1 INTO orgId, qv;
							END WHILE WHILE1;
						CLOSE s_cur1;

		END LB_CURSOR_CR;

		IF p_error = 1 THEN  
				ROLLBACK;  
		ELSE  
				COMMIT; 

				
		END IF;

	END;
	-- DELIMITER ;

		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201401);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201402);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201403);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201404);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201405);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201406);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201407);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201408);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201409);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201410);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201411);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201412);	

		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201501);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201502);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201503);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201504);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201505);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201506);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201507);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201508);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201509);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201510);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201511);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201512);	


		
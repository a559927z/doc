#=======================================================pro_fetch_pay=======================================================
-- 应响的表：pay,pay_collect,pay_collect_year
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '解密key', 发工资年月
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_pay`;
CREATE PROCEDURE pro_fetch_pay(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key2 VARCHAR(50), in p_ym INTEGER(6))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	-- 硬编码
	DECLARE curDate DATE DEFAULT '2015-12-18'; --  CURDATE();
	
	DECLARE 5yearhAGo INT DEFAULT p_ym - 500;
	DECLARE limitStart INT;
	DECLARE y INT DEFAULT SUBSTR(p_ym FROM 1 FOR 4);
	DECLARE m INT DEFAULT SUBSTR(p_ym FROM 5 FOR 6);
	
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
			
			-- 	删除五年前今天以往的数据
-- 			DELETE FROM soure_pay
-- 			WHERE customer_id = customerId AND `year_month` < 5yearhAGo;

			DELETE FROM pay WHERE `year_month` = p_ym;
			INSERT INTO pay (
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
				id,
				customer_id,
				emp_id,
				usre_name_ch,
				organization_id,
				full_path,
				postion_id,
				AES_ENCRYPT(pay_contract, p_key2),
				AES_ENCRYPT(pay_should, p_key2),
				AES_ENCRYPT(pay_actual, p_key2),
				AES_ENCRYPT(salary_actual, p_key2),
				AES_ENCRYPT(welfare_actual, p_key2),
				AES_ENCRYPT(bonus, p_key2),
				cr_value,
				`year_month`
			FROM soure_pay t
			WHERE t.customer_id = customerId 
			AND t.`year_month` = p_ym
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 

		-- 薪酬汇总表
		LB_PAY_COLLECT :BEGIN

				CALL pro_fetch_pay_collect(customerId,'DBA', p_key2, p_ym);	

				-- 薪酬年汇总
				IF (m=12) THEN
					CALL pro_fetch_pay_collect_year(customerId,'DBA', y);
				END IF;

		END LB_PAY_COLLECT;

	END IF;

END;
-- DELIMITER ;
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201401);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201402);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201403);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201404);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201405);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201406);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201407);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201408);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201409);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201410);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201411);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201412);	

	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201501);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201502);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201503);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201504);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201505);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201506);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201507);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201508);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201509);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201510);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201511);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201512);	


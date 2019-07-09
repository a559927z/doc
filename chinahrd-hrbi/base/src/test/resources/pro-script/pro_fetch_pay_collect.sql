#=======================================================pro_fetch_pay_collect=======================================================
-- 应响的表：pay,pay_collect,pay_collect_year
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '解密key', 发工资年月
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_pay_collect`;
CREATE PROCEDURE pro_fetch_pay_collect(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key2 VARCHAR(50), in p_ym INTEGER(6))
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
	DECLARE sp, ss, sw, sb double(10, 4);


	
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
			
	-- 薪酬汇总表
		LB_CURSOR_SUM:BEGIN

					DECLARE orgId VARCHAR(32);
					DECLARE fp VARCHAR(1000);

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur1 CURSOR FOR
						SELECT 
							organization_id orgId,
							full_path fp
						FROM dim_organization WHERE customer_id = customerId;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								
					DELETE FROM pay_collect WHERE `year_month` = p_ym;

					OPEN s_cur1;
					FETCH s_cur1 INTO orgId, fp;

						WHILE1: WHILE done != 1 DO

								SELECT IFNULL(sum(sumPay),0) sp, 
											 IFNULL(sum(sumSalary),0) ss, IFNULL(sum(sumWelfare),0) sw,  IFNULL(sum(sumBonus),0) sb
								INTO sp,  ss, sw, sb
								-- SELECT sumPay, avgPay, sumSalary, sumWelfare, t2.full_path
								FROM dim_organization t2 
								LEFT JOIN 
									(
										SELECT 
												t.organization_id,
												SUM(AES_DECRYPT(t.pay_should, p_key2)) sumPay,
												SUM(AES_DECRYPT(t.salary_actual, p_key2)) sumSalary,
												SUM(AES_DECRYPT(t.welfare_actual, p_key2)) sumWelfare,
												SUM(AES_DECRYPT(t.bonus, p_key2)) sumBonus,
												t1.full_path
											FROM pay t
											INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id
											WHERE  t.`year_month` = p_ym
											GROUP BY organization_id
								) t on t.organization_id = t2.organization_id
								WHERE LOCATE(fp, t2.full_path)
								;

								SET @mas = (SELECT month_avg_sum FROM history_emp_count_month t3 
														WHERE `year_month` = p_ym AND t3.organization_id = orgId AND t3.customer_id = customerId);
								

								INSERT INTO pay_collect VALUES(REPLACE(uuid(),'-',''), customerId, orgId, 
										sp, (sp / @mas) , @mas, 
										ss, (ss / @mas),
										sw, (sw / @mas),
										sb, 0, p_ym, null);


						
							FETCH s_cur1 INTO orgId, fp;
						END WHILE WHILE1;
					CLOSE s_cur1;

	END LB_CURSOR_SUM;

				-- 50分位值计算：
				LB_CURSOR_50:BEGIN
					
					DECLARE orgId VARCHAR(50);
					DECLARE fp TEXT;

-- 					DECLARE pc INTEGER DEFAULT 0; 
-- 					DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET pc_error = 1; END;

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
-- 						SELECT t2.organization_id orgId
-- 						FROM soure_pay t2 
-- 						WHERE t2.customer_id = customerId
-- 						AND t2.`year_month` = p_ym
-- 						GROUP BY t2.organization_id
-- 						;
							SELECT 
								organization_id orgId,
								full_path fp
							FROM dim_organization WHERE customer_id = customerId;


					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;

					OPEN s_cur;
					FETCH s_cur INTO orgId, fp;
						WHILE1: WHILE done != 1 DO

								-- 机构下总人数：
								SET @orgCountEmp = (SELECT emp_count_sum 
																		FROM history_emp_count t 
																		WHERE t.`day` = curDate AND type = 1 -- 当天全职的人数
																		AND t.organization_id = orgId);

								-- 奇数
								IF( @orgCountEmp % 2 = 1) THEN
									SET limitStart = (@orgCountEmp / 2) -0.5;

									UPDATE pay_collect SET quantile_value = (

										SELECT
												t2.pay_should  -- 应发薪酬
										FROM soure_pay t2 
										WHERE t2.customer_id = customerId
										AND t2.`year_month` = p_ym
-- 										AND t2.organization_id in (
-- 												SELECT t1.organization_id FROM dim_organization t1 
-- 												WHERE locate(( SELECT t.full_path FROM dim_organization t WHERE t.organization_id = orgId AND t.customer_id = customerId ),
-- 																			t1.full_path )
-- 												)
										AND locate(fp, t2.full_path)
										ORDER BY t2.pay_should DESC
										LIMIT limitStart, 1
									)
									WHERE organization_id = orgId AND `year_month` = p_ym AND customer_id = customerId
									;
								-- 偶数
								ELSE
									SET limitStart = (@orgCountEmp / 2);
				
									UPDATE pay_collect SET quantile_value = (
										SELECT AVG(ps)
										FROM (
													SELECT t2.pay_should ps
													FROM soure_pay t2 
													WHERE t2.customer_id = customerId
													AND t2.`year_month` = p_ym
-- 													AND t2.organization_id in (
-- 														SELECT t1.organization_id FROM dim_organization t1 
-- 														WHERE locate(( SELECT t.full_path FROM dim_organization t WHERE t.organization_id = orgId AND t.customer_id = customerId ),
-- 																					t1.full_path )
-- 														)
													AND locate(fp, t2.full_path)
													ORDER BY t2.pay_should DESC
													LIMIT limitStart, 2
												) t3
									)
									WHERE organization_id = orgId AND `year_month` = p_ym AND customer_id = customerId
									;
								END IF;

							FETCH s_cur INTO orgId, fp;
						END WHILE WHILE1;
					CLOSE s_cur;
				END LB_CURSOR_50;


	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 

			CALL pro_update_pay_crValue(customerId,optUserId, p_key2, p_ym);	
	END IF;

END;
-- DELIMITER ;
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201401);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201402);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201403);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201404);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201405);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201406);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201407);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201408);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201409);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201410);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201411);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201412);	

	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201501);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201502);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201503);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201504);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201505);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201506);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201507);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201508);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201509);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201510);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201511);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201512);	


#=======================================================pro_fetch_recruit_public=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_recruit_public`;
CREATE PROCEDURE pro_fetch_recruit_public(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_y int(4))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN

						INSERT INTO recruit_public (
							recruit_public_id, customer_id, position_id, employ_num, plan_num, start_date, end_date, days, 
							resume_num, interview_num, offer_num, entry_num, is_public, YEAR,  c_id
						)
						SELECT 
							REPLACE (UUID(), '-', ''), customer_id, position_id, 0, plan_num, start_date, end_date, TIMESTAMPDIFF(DAY, start_date, end_date) + 1,
							0, 0, 0, 0, is_public, YEAR, c_id
						FROM soure_recruit_public t 
						WHERE customer_id = customerId AND t.year = p_y
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								position_id = t.position_id,
								plan_num = t.plan_num,
								start_date = t.start_date,
								end_date = t.end_date,
								days = TIMESTAMPDIFF(DAY, t.start_date, t.end_date) +1,
								is_public = t.is_public,
								year = t.year
								
						;
				END LB_INSERT;



	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			
				LB_CURSOR:BEGIN
					
					DECLARE rpId VARCHAR(32);
					DECLARE done INT DEFAULT 0;
					DECLARE ir,ii,io,ie INT(4);
					DECLARE s_cur CURSOR FOR
						SELECT 
							recruit_public_id	rpId
						FROM recruit_public;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								
					OPEN s_cur;
					FETCH s_cur INTO  rpId;

						WHILE1: WHILE done != 1 DO
							
						SET ir = (SELECT COUNT(is_resume) FROM recruit_result
											where  recruit_public_id = rpId and is_resume = 1 AND customer_id = customerId);
													
						SET ii = (SELECT COUNT(is_interview) FROM recruit_result
											where  recruit_public_id = rpId and is_interview = 1 AND customer_id = customerId);
													
						SET io = (SELECT COUNT(is_offer) FROM recruit_result
											where  recruit_public_id = rpId and is_offer = 1 AND customer_id = customerId);

						SET ie = (SELECT COUNT(is_entry) FROM recruit_result
											where  recruit_public_id = rpId and is_entry = 1 AND customer_id = customerId);

							UPDATE recruit_public SET employ_num = ie, resume_num = ir, interview_num = ii, offer_num = io, entry_num = ie
							WHERE recruit_public_id = rpId AND customer_id = customerId;

							FETCH s_cur INTO  rpId;
						END WHILE WHILE1;
				
				
					CLOSE s_cur;
				END LB_CURSOR;

	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_recruit_public('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2015);


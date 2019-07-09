#=======================================================pro_fetch_recruit_channel=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_recruit_channel`;
CREATE PROCEDURE pro_fetch_recruit_channel(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_y INT(4))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN

						INSERT INTO recruit_channel (
							recruit_channel_id, customer_id,position_id, channel_id, employ_num, outlay, start_date, end_date,
							days, recruit_public_id,  YEAR,  c_id
						)
						SELECT 
							REPLACE (UUID(), '-', ''), customer_id, position_id, channel_id, employ_num, outlay, start_date, end_date, 
							TIMESTAMPDIFF(DAY, start_date, end_date) + 1, recruit_public_id, YEAR, now(), c_id
						FROM soure_recruit_channel t 
						WHERE customer_id = customerId AND YEAR=p_y
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								position_id = t.position_id,
								channel_id = t.channel_id,
								employ_num = t.employ_num,
								outlay = t.outlay,
								start_date = t.start_date,
								end_date = t.end_date,
								days = TIMESTAMPDIFF(DAY, t.start_date, t.end_date) +1,
								recruit_public_id = t.recruit_public_id,
								year = t.year								
						;
				END LB_INSERT;



	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_recruit_channel('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2015);


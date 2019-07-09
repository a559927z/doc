-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_evaluation_report`;
CREATE PROCEDURE pro_fetch_evaluation_report(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE eId VARCHAR(32);
	DECLARE eKey, erName VARCHAR(20);
	DECLARE hS,lS, bS,curS DOUBLE(5,2);
	DECLARE curEnclosure, curPath VARCHAR(60);
	DECLARE curType INT(1);

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE exist int;
	DECLARE startTime TIMESTAMP;

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
					SELECT 
						t.emp_id eId, tt.emp_key eKey, evaluation_report_name erName, 
						hd_score hS, lw_score lS, board_score bS, score curS, enclosure_name curEnclosure, path curPath, type curType
					FROM soure_evaluation_report tt
					INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
					WHERE t.customer_id = customerId;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【360测评表-维度表】：", eKey, "数据失败"), startTime, now(), "error");
		END;

		SET startTime = now();
		
		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO eId, eKey, erName, hS, lS, bS, curS ,curEnclosure , curPath, curType;
				START TRANSACTION;

				SELECT count(1) as exist into exist FROM evaluation_report
				WHERE emp_id = eId AND path = curPath AND customer_id = customerId;
							
				IF exist>0 THEN

					UPDATE evaluation_report 
					SET evaluation_report_name = erName, hd_score = hS, lw_score = lS, board_score = bS, score = curS , enclosure_name = curEnclosure, path =curPath,  type = curType
						WHERE emp_id = eId AND path = curPath AND customer_id = customerId;

				ELSE

					INSERT INTO evaluation_report
					VALUES(replace(UUID(),'-',''), customerId, eId, erName, hS, lS, bS, curS ,curEnclosure , curPath, curType);

			END IF;
		END WHILE;
		CLOSE s_cur;

END;
-- DELIMITER ;

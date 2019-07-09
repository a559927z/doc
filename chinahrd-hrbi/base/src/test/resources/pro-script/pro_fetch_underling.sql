#=======================================================pro_fetch_underling=======================================================
DROP PROCEDURE IF EXISTS pro_fetch_underling;
CREATE PROCEDURE pro_fetch_underling (in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【我的下属-业务表】：数据刷新完成';

	DECLARE  eHfKey VARCHAR(20);
	DECLARE  eHfId VARCHAR(32);
	
	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
		SELECT DISTINCT emp_hf_id eHfId, emp_hf_key eHfKey FROM v_dim_emp WHERE emp_hf_id is NOT NULL and emp_hf_id != "-1";

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;


	START TRANSACTION;

	DELETE FROM underling where customer_id = customerId;
	OPEN s_cur;
	

		WHILE done != 1 DO
			FETCH s_cur INTO eHfId, eHfKey;
			IF NOT done THEN
				INSERT INTO underling 
						(underling_id, customer_id, 
							emp_id, emp_key, underling_emp_id, underling_emp_key)
				SELECT REPLACE(uuid(), '-',''), customerId,
					eHfId, eHfKey, emp_id, emp_key
				FROM v_dim_emp WHERE locate(eHfKey, report_relation) AND emp_key != eHfKey;
			END IF;
		END WHILE;
		CLOSE s_cur;


		IF p_error = 1 THEN  
				ROLLBACK;  
-- 				SET p_message = concat("【我的下属-业务表】：数据刷新失败。操作用户：", optUserId);
-- 				INSERT INTO db_log 
-- 				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'underling', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
-- 				INSERT INTO db_log 
-- 				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'underling', p_message, startTime, now(), 'sucess' );
		END IF;

END;


-- 	CALL pro_fetch_underling ('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');

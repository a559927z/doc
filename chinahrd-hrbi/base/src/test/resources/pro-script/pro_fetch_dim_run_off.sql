#=======================================================pro_fetch_dim_run_off=======================================================
-- 'demo','jxzhang'
-- 不保存历史
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_run_off`;
CREATE PROCEDURE pro_fetch_dim_run_off(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
-- 	DECLARE p_message VARCHAR(10000) DEFAULT '【流失-维度表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;


		REPLACE INTO dim_run_off(run_off_id, customer_id, run_off_key, run_off_name, type)
		SELECT id, t2.customer_id, t2.run_off_key, t2.run_off_name, t2.type
			FROM soure_dim_run_off t2
		WHERE	 t2.customer_id = customerId;

	IF p_error = 1 THEN  
			ROLLBACK;  
-- 			SET p_message = concat("【流失-维度表】：数据刷新失败。操作用户：", optUserId);
-- 			INSERT INTO db_log 
-- 			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_run_off', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
-- 			INSERT INTO db_log 
-- 			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_run_off', p_message, startTime, now(), 'sucess' );

			-- 去掉已删除数据
			DELETE FROM dim_run_off where run_off_id not in ( SELECT t2.id from soure_dim_run_off t2 where t2.customer_id = customerId);

	END IF;

END;
-- DELIMITER ;

-- CALL pro_fetch_dim_run_off('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');
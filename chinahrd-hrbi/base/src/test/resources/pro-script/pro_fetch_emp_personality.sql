#=======================================================pro_fetch_emp_personality=======================================================
-- 'demo','jxzhang','2015-11-01'
-- 测试时输入上个月时间。（ 这里没有对上个月时间处理，上个月时间处理在事件里完成。）
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_personality`;
CREATE PROCEDURE pro_fetch_emp_personality(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
-- 	DECLARE lastYearMonth int(6) DEFAULT date_format(DATE_ADD(p_last_curdate, Interval 0 minute),'%Y%m');


	-- 定义接收临时表数据的变量 
	DECLARE exist INT;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工性格-业务表】：数据刷新完成';

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;


	START TRANSACTION;

			REPLACE INTO emp_personality(
				emp_personality_id,
				customer_id,
				emp_id,
				type 
			)
			SELECT 
				id,
				customer_id,
				emp_id,
				type
			FROM soure_emp_personality 
			;



		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【员工性格-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_personality', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_personality', p_message, startTime, now(), 'sucess' );
		END IF;


END;
-- DELIMITER ;


CALL pro_fetch_emp_personality('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19' );

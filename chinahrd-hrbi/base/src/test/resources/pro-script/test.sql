#=======================================================test=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `test`;
CREATE PROCEDURE test(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_datetime DATETIME)
BEGIN

		DECLARE pCurDate TIMESTAMP DEFAULT p_datetime;
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT '【test-DEMO表】：数据刷新完成';
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
		


INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''),'12', customerId, optUserId, 'test',null, p_message, startTime, now(), 'test' );


	
END;
-- DELIMITER ;
DROP PROCEDURE IF EXISTS `test`;


-- CALL test('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-01-01');


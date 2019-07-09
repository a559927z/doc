-- 员工性格
DROP PROCEDURE IF EXISTS pro_fetch_emp_personality;
CREATE PROCEDURE `pro_fetch_emp_personality`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),  IN p_refresh TIMESTAMP(6))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-员工性格】';
	
    INSERT INTO emp_personality(emp_personality_id,customer_id,emp_id, personality_id,type) 
    SELECT emp_personality_id,p_customer_id,emp_id,personality_id,type 
    FROM `mup-source`.source_emp_personality  a
    ON DUPLICATE KEY UPDATE 
	    personality_id=a.personality_id,
	    type=a.type;
END;
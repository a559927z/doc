DROP PROCEDURE IF EXISTS dowhile_update1;
CREATE PROCEDURE dowhile_update1 ()
BEGIN

	DECLARE eId VARCHAR(32);
	DECLARE i INT DEFAULT 1;


-- ======================================游标模板start======================================
-- 	DECLARE done INT DEFAULT 0;
-- 	DECLARE s_cur CURSOR FOR
-- 		SELECT emp_key eKey, emp_id eId FROM dim_emp;
-- 	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
-- 
-- 	OPEN s_cur;
-- 		
-- 		WHILE done != 1 DO
-- 			FETCH s_cur INTO  eKey, eId ;
-- 
-- 		END WHILE;
-- 
-- 
-- 	CLOSE s_cur;
-- 
-- END;
-- ======================================游标模板end======================================


	

		WHILE i < 10000 DO
			INSERT INTO p_range(`name`) VALUES (CONCAT('a',i));

			SET i = i + 1;
		END WHILE;



END;
-- 游标模板end


 CALL dowhile_update1 ();


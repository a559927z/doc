DROP PROCEDURE IF EXISTS add_data_overtime_day;
CREATE PROCEDURE add_data_overtime_day(IN p_otDate DATE)
BEGIN

	DECLARE eKey VARCHAR(20);
	DECLARE eId, orgId VARCHAR(32);

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
		SELECT emp_key eKey, emp_id eId, organization_id orgId 
		FROM v_dim_emp WHERE emp_hf_key = 'gyhe' AND run_off_date IS NULL
	;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	
	OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO   eKey, eId, orgId;

				DELETE FROM emp_overtime_day WHERE organization_id = eId AND emp_id = eId;
	
				SET @rand = (SELECT FLOOR( 1 + RAND() * 9));

				INSERT INTO emp_overtime_day VALUES(
					replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f',
					orgId, eId, eKey, @rand, 
					p_otDate
				)
						;
		END WHILE;
	CLOSE s_cur;

END;


 CALL add_data_overtime_day('2015-12-11');
 CALL add_data_overtime_day('2015-12-10');
 CALL add_data_overtime_day('2015-12-09');
 CALL add_data_overtime_day('2015-12-08');
 CALL add_data_overtime_day('2015-12-07');
 CALL add_data_overtime_day('2015-12-04');
 CALL add_data_overtime_day('2015-12-03');
 CALL add_data_overtime_day('2015-12-02');
 CALL add_data_overtime_day('2015-12-01');

-- 前三天
SET @v1 = (SELECT DATE_SUB(CURDATE(),INTERVAL 1 DAY));
SET @v2 = (SELECT DATE_SUB(CURDATE(),INTERVAL 2 DAY));
SET @v3 = (SELECT DATE_SUB(CURDATE(),INTERVAL 3 DAY));
 CALL add_data_overtime_day(@v1);
 CALL add_data_overtime_day(@v2);
 CALL add_data_overtime_day(@v3);


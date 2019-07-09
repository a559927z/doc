DROP PROCEDURE IF EXISTS update_data_benefit_value;
CREATE PROCEDURE update_data_benefit_value ()
BEGIN


	DECLARE  fid VARCHAR(32);
	DECLARE  fV DOUBLE(6,2);
	DECLARE  gA DECIMAL(10,2);

	
	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR

		SELECT fte_id fid, fte_value fV, gain_amount gA FROM `fact_fte` ;

	
-- 	DELETE FROM underling ;
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	OPEN s_cur;
		
		WHILE done != 1 DO
			FETCH s_cur INTO  fid, fV, gA;

			
UPDATE fact_fte ttt set
 ttt.benefit_value = gA/fV
WHERE ttt.fte_id = fid
		;


		END WHILE;
		CLOSE s_cur;

END;


 CALL update_data_benefit_value ();


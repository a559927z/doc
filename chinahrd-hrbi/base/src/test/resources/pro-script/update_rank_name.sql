DROP PROCEDURE IF EXISTS update_rank_name;
CREATE PROCEDURE update_rank_name ()
BEGIN

	DECLARE  eKey VARCHAR(20);
	DECLARE  abName VARCHAR(20);
	DECLARE  rName VARCHAR(5);
	
	
	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
		SELECT emp_key eKey FROM soure_v_dim_emp;


	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	OPEN s_cur;
		
		WHILE done != 1 DO
			FETCH s_cur INTO   eKey;

			
			UPDATE soure_v_dim_emp ttt set
			 ttt.rank_name = 
			(
				SELECT tttt.rName FROM (
				SELECT  CONCAT(t2.curt_name, t6.curt_name, t3.curt_name, ".", t4.curt_name) rName

									FROM soure_v_dim_emp tt
									
									LEFT JOIN dim_sequence t2 on t2.sequence_id = tt.sequence_id
											
									LEFT JOIN dim_sequence_sub t6 on t6.sequence_sub_id = tt.sequence_sub_id
										
									LEFT JOIN dim_ability t3 on t3.ability_id = tt.ability_id

									LEFT JOIN dim_ability_lv t4 on t4.ability_lv_id = tt.ability_lv_id
									WHERE tt.emp_key = eKey
				) tttt
			)
			WHERE ttt.emp_key = eKey
		;


		END WHILE;
		CLOSE s_cur;

END;


 CALL update_rank_name ();


DROP PROCEDURE IF EXISTS dowhile_update;
CREATE PROCEDURE dowhile_update ()
BEGIN
	DECLARE sKey, aKey, abLvKey, eKey, seKey VARCHAR(20);
	
	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
				SELECT 
						 upper(left(t2.sequence_key, 1)) sKey, t3.ability_key aKey, t4.ability_lv_key abLvKey, tt.emp_key eKey, IFNULL(t6.sequence_sub_key, 1) seKey
					FROM soure_job_change tt
					LEFT JOIN dim_organization t on t.organization_key = tt.organization_key
							AND t.customer_id = tt.customer_id
							AND now() >= t.effect_date
							AND t.expiry_date IS NULL
							AND t.enabled = 1
					LEFT JOIN dim_position t1 on t1.position_key = tt.position_key
							AND t1.customer_id = tt.customer_id
							AND now() >= t1.effect_date
							AND t1.expiry_date IS NULL
							AND t1.enabled = 1
					LEFT JOIN dim_sequence t2 on t2.sequence_key = tt.sequence_key
							AND t2.customer_id = tt.customer_id

					LEFT JOIN dim_sequence_sub t6 on t6.sequence_sub_key = tt.sequence_sub_key
							AND t6.customer_id = tt.customer_id

					LEFT JOIN dim_ability t3 on t3.ability_key = tt.ability_key
							AND t3.customer_id = tt.customer_id
					LEFT JOIN dim_ability_lv t4 on t4.ability_lv_key = tt.ability_lv_key
							AND t4.customer_id = tt.customer_id
					LEFT JOIN dim_emp t5 on t5.emp_key = tt.emp_key
							AND t5.customer_id = tt.customer_id
							AND now() >= t5.effect_date
							AND t5.expiry_date IS NULL
							AND t5.enabled = 1;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO sKey, aKey, abLvKey, eKey, seKey;
			
-- INSERT INTO debug(type) VALUES(seKey);
				IF seKey=1 THEN

					UPDATE job_change 
					SET rank_name = concat(sKey, aKey,".", abLvKey )
					WHERE emp_key = eKey;
				ELSE
					UPDATE job_change 
					SET rank_name = concat(sKey, left(substring_index(seKey, "_", -1), 1), aKey,".", abLvKey )
					WHERE emp_key = eKey;
				end if;
			
		END WHILE;
		CLOSE s_cur;

END;

 CALL dowhile_update ();

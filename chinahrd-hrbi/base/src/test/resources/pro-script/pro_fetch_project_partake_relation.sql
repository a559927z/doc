#=======================================================pro_fetch_project_partake_relation=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_project_partake_relation`;
CREATE PROCEDURE pro_fetch_project_partake_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;


				LB_CURSOR:BEGIN
					
					DECLARE proId, pproId VARCHAR(32);
					DECLARE fp TEXT;

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT 
							project_id proId,
							project_parent_id pproId,
							full_path fp
						FROM project;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								

					OPEN s_cur;
					FETCH s_cur INTO proId, pproId, fp;

						WHILE1: WHILE done != 1 DO
							
							DELETE FROM project_partake_relation WHERE project_id = proId;
							INSERT INTO project_partake_relation(
								project_partake_id,
								customer_id,
								organization_id,
								project_id
							)
							-- 子项目的主导机构，就是主项目的参与机构。（这个业务可能会变要看实施情况）
							SELECT 
								REPLACE(UUID(),'-',''), customer_id,
								organization_id,
								project_id
							FROM project
							WHERE LOCATE(fp,full_path) 
								AND project_id != proId -- 不包括自己
							;

						FETCH s_cur INTO proId, pproId, fp;
						END WHILE WHILE1;
				
					CLOSE s_cur;
				END LB_CURSOR;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
	END IF;

	
END;
-- DELIMITER ;

CALL pro_fetch_project_partake_relation('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');


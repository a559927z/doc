DROP PROCEDURE IF EXISTS add_data_job_change;
CREATE PROCEDURE add_data_job_change ()
BEGIN

	DECLARE  orgId, posId, seqId VARCHAR(32);
	DECLARE  orgName, posName, seqName VARCHAR(20);
	DECLARE  orgKey, posKey, seqKey, eKey VARCHAR(20);
	DECLARE  eId VARCHAR(32);
		
	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
		SELECT 
				t1.organization_id orgId, t2.position_id posId, t3.sequence_id seqId,
				t1.organization_name orgName, t2.position_name posName, t3.sequence_name seqName,
				t1.organization_key orgKey, t2.position_key  poskey , t3.sequence_key seqKey,
				t.emp_id eId, t4.emp_key eKey
			FROM emp_position_relation t
		INNER JOIN  dim_emp t4 on t4.emp_id = t.emp_id
		INNER JOIN  dim_organization t1 on t1.organization_id = t.organization_id
		INNER JOIN  dim_position t2 on t2.position_id = t.position_id
		INNER JOIN  dim_sequence t3 on t2.sequence_id = t3.sequence_id;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	OPEN s_cur;
		
		WHILE done != 1 DO
			FETCH s_cur INTO  orgId, posId,seqId, orgName, posName,seqName, orgKey, posKey, seqKey, eId, eKey;

					update soure_job_change t SET
								organization_key = orgKey, position_key = posKey,
								organization_name=orgName, position_name=posName, sequence_key = seqKey,
								sequence_name = seqName
					WHERE t.emp_key = eKey and change_type = 3;

					update job_change t SET
								organization_id = orgId, position_id = posId,
								organization_name=orgName, position_name=posName, sequence_id = seqId,
								sequence_name = seqName
					WHERE t.emp_id = eId and change_type = 3;



		END WHILE;
		CLOSE s_cur;

END;


 CALL add_data_job_change ();


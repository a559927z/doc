DROP PROCEDURE IF EXISTS update_jobId_and_job_Name;
CREATE PROCEDURE update_jobId_and_job_Name ()
BEGIN


	DECLARE  eKey, jobKey VARCHAR(20);
	DECLARE  jobName VARCHAR(20);


	DECLARE  abId VARCHAR(32);
	DECLARE  abLvId VARCHAR(32);
	DECLARE  seqId VARCHAR(32);
	DECLARE  seqsubId VARCHAR(32);
	DECLARE  jobId VARCHAR(32);


	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
-- 		SELECT emp_key eKey, ability_id abId, ability_lv_id abLvId,
-- 			sequence_id seqId, sequence_sub_id seqsubId
-- 		FROM soure_v_dim_emp 
-- 		where run_off_date is null;
	 
SELECT t.emp_key eKey, t1.job_title_id jobId, t.job_title_name jobName
FROM soure_job_change t
INNER JOIN dim_job_title t1 on t.job_title_key = t1.job_title_key
GROUP BY emp_key
ORDER BY change_date DESC;



	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	OPEN s_cur;
		
		WHILE done != 1 DO
-- 			FETCH s_cur INTO   eKey, abId, abLvId, seqId, seqsubId;
			FETCH s_cur INTO   eKey, jobId, jobName;

-- 			select jr.job_title_id , t1.job_title_name 
-- 			INTO jobId, jobName
-- 			from job_relation jr 
-- 			left JOIN dim_job_title t1 on t1.job_title_id = jr.job_title_id
-- 			where abId=jr.ability_id and abLvId=jr.ability_lv_id
-- 			and seqId=jr.sequence_id and  seqsubId=jr.sequence_sub_id			
-- 			;

-- 			SELECT eKey, jobId, jobName;
			UPDATE soure_v_dim_emp t
			SET t.job_title_id = jobId, t.job_title_name = jobName
			WHERE
				t.emp_key = eKey
				;


		END WHILE;
		CLOSE s_cur;

END;


	CALL update_jobId_and_job_Name ();
	SELECT * FROM soure_v_dim_emp where job_title_id is null;



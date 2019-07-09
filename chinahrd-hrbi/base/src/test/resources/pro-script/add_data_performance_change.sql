DROP PROCEDURE IF EXISTS add_data_performance_change;
CREATE PROCEDURE  add_data_performance_change( in ym int(6))
BEGIN

	DECLARE  orgId, posId, seqId, pOrgId VARCHAR(32);
	DECLARE  orgName, posName, seqName, pOrgName VARCHAR(20);
	DECLARE  orgKey, posKey, seqKey, eKey, pOrgKey VARCHAR(20);
	DECLARE  eId VARCHAR(32);
		
	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
		SELECT 
			t1.organization_id orgId, t2.position_id posId, t3.sequence_id seqId,
			t1.organization_name orgName, t2.position_name posName, t3.sequence_name seqName,
			t1.organization_key orgKey, t2.position_key  poskey , t3.sequence_key seqKey,
			t.emp_id eId, t.emp_key eKey
			FROM v_dim_emp t
		INNER JOIN  dim_organization t1 on t1.organization_id = t.organization_id
		INNER JOIN  dim_position t2 on t2.position_id = t.position_id
		INNER JOIN  dim_sequence t3 on t2.sequence_id = t3.sequence_id
		;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	OPEN s_cur;
		
		WHILE done != 1 DO
			FETCH s_cur INTO  orgId, posId,seqId, orgName, posName,seqName, orgKey, posKey, seqKey, eId, eKey;

				-- 找出负公司
					SELECT organization_id pOrgId, organization_Key pOrgKey, organization_name pOrgName 
					INTO pOrgId, pOrgKey, pOrgName 
					FROM dim_organization t1
					WHERE t1.full_path = (
						SELECT SUBSTRING_INDEX(t2.full_path,'_',2) FROM dim_organization t2 WHERE t2.organization_id = orgId
					);
			
			SET @randNum = floor(1 + rand() * 5);
			SELECT performance_name, performance_key into @perName, @perKey FROM dim_performance where performance_key = @randNum;

			SET @randNum2 = floor(1 + rand() * 3);
			SELECT grade_key, grade_name into @gKey, @gName FROM dim_grade WHERE show_index = @randNum2;
			-- SELECT grade_key, grade_name FROM dim_grade WHERE show_index = @randNum2;

			INSERT INTO soure_performance_change VALUES(
				replace(uuid(), '-', ''),
				'b5c9410dc7e4422c8e0189c7f8056b5f',
				eKey, 
				pOrgName, orgName,
				posName, @perName, @gName, 100 - FLOOR(10 + (RAND() * 6)), 
				ym, 
				pOrgKey, orgKey, 
				posKey, @perKey, @gKey
			);

		END WHILE;
		CLOSE s_cur;

END;

DELETE FROM soure_performance_change;
DELETE FROM performance_change;


 CALL add_data_performance_change (201006);
 CALL add_data_performance_change (201012);
 CALL add_data_performance_change (201106);
 CALL add_data_performance_change (201112);
 CALL add_data_performance_change (201206);
 CALL add_data_performance_change (201212);
 CALL add_data_performance_change (201306);
 CALL add_data_performance_change (201312);
 CALL add_data_performance_change (201406);
 CALL add_data_performance_change (201412);
 CALL add_data_performance_change (201506);

SELECT count(1) FROM soure_performance_change ;
SELECT count(1)FROM performance_change;

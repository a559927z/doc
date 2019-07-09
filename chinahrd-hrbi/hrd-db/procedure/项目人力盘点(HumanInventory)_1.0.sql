-- 项目人力明细
drop procedure if exists pro_fetch_project_manpower;
CREATE PROCEDURE `pro_fetch_project_manpower`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【业务表-项目人力明细】');
  
	INSERT INTO project_manpower(
		project_manpower_id,customer_id,emp_id,input,note,project_id,project_sub_id,date,type)
	SELECT 
		project_manpower_id,customerId,emp_id,input,note,project_id,project_sub_id,date,type 
	FROM `mup-source`.source_project_manpower a
	ON DUPLICATE KEY UPDATE 
		emp_id=a.emp_id,
		input=a.input,
		note=a.note,
		project_id=a.project_id,
		project_sub_id=a.project_sub_id,
		date=a.date,
		type=a.type;
END;

-- 主导项目参与项目关系
drop procedure if exists pro_fetch_project_partake_relation;
CREATE  PROCEDURE `pro_fetch_project_partake_relation`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【维度表-主导项目参与项目关系】');
		LB_CURSOR:BEGIN
			DECLARE proId, pproId VARCHAR(32);
			DECLARE fp TEXT;
			DECLARE done INT DEFAULT 0;
			DECLARE s_cur CURSOR FOR SELECT project_id proId,project_parent_id pproId,full_path fp FROM project;
			DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
			OPEN s_cur;
			FETCH s_cur INTO proId, pproId, fp;
				WHILE1: WHILE done != 1 DO
					-- 先删除本项目相关的所有参与项目关系
					DELETE FROM project_partake_relation WHERE project_id = proId;
					
					INSERT INTO project_partake_relation(
						project_partake_id,
						customer_id,
						organization_id,
						project_id
					)
					-- 子项目的主导机构，就是主项目的参与机构。（这个业务可能会变要看实施情况）
					SELECT 
						fn_getId(), customerId,
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
END;

-- 项目投入费用明细
drop procedure if exists pro_fetch_project_input_detail;
CREATE  PROCEDURE `pro_fetch_project_input_detail`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【关系表-项目投入费用明细】');

   INSERT INTO project_input_detail(
   		project_input_detail_id,customer_id,project_id,project_input_type_id,outlay,date,type)
   SELECT
		project_input_detail_id,customerId,project_id,project_input_type_id,outlay,date,type 
   FROM `mup-source`.source_project_input_detail a
   ON DUPLICATE KEY UPDATE 
	   outlay=a.outlay,
	   date=a.date,
	   type=a.type
	;
END;


-- 项目投入费用类型维
drop procedure if exists pro_fetch_project_cost;
CREATE  PROCEDURE `pro_fetch_project_cost`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【维度表-项目投入费用类型维】');

    insert into project_cost(
    	project_cost_id,customer_id,input,output,gain,project_id,date,type)
    select 
    	project_cost_id,p_customer_id,input,output,gain,project_id,date,type 
	from `mup-source`.source_project_cost a 
    on duplicate key update 
	    input=a.input,
	    output=a.output,
	    gain=a.gain,
	    date=a.date,
	    type=a.type;
END;

-- 项目
drop procedure if exists pro_fetch_project;
CREATE PROCEDURE `pro_fetch_project`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh timestamp(6))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【业务表-项目】');
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;

	LB_INSERT:BEGIN
		INSERT INTO project (
			project_id, customer_id, project_key, project_name, emp_id, organization_id, project_type_id, project_progress_id, project_parent_id, full_path, has_chilren, start_date, end_date)
		SELECT 
			project_id, customer_id, project_key, project_name, emp_id, organization_id, project_type_id, project_progress_id, project_parent_id, full_path, has_chilren, start_date, end_date
		FROM `mup-source`.source_project t
		ON DUPLICATE KEY UPDATE
			customer_id = t.customer_id, 
			project_key = t.project_key,
			project_name = t.project_name,
			emp_id = t.emp_id,
			organization_id = t.organization_id,
			project_type_id = t.project_type_id,
			project_progress_id = t.project_progress_id,
			project_parent_id = t.project_parent_id,
			full_path = t.full_path,
			has_chilren = t.has_chilren,
			start_date = t.start_date,
			end_date = t.end_date;
	END LB_INSERT;

END;


-- 项目最大负荷数
drop procedure if exists pro_fetch_project_maxvalue;
CREATE PROCEDURE `pro_fetch_project_maxvalue`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【业务表-项目最大负荷数】');
   
  INSERT INTO project_maxvalue(
  	project_maxvalue_id,customer_id,organization_id,maxval,total_work_hours)
  SELECT 
  	project_maxvalue_id,p_customer_id,organization_id,maxval,total_work_hours
  FROM `mup-source`.source_project_maxvalue a 
  ON DUPLICATE KEY UPDATE
  maxval=a.maxval,
  total_work_hours=a.total_work_hours;

END;
	


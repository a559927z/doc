/*==============================================================*/
/* 机构                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_organization;
CREATE PROCEDURE `pro_fetch_dim_organization`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE refresh TIMESTAMP DEFAULT p_refresh;

			INSERT INTO dim_organization(
				organization_id, customer_id, organization_key, organization_name, organization_name_full,
				organization_parent_id,
				organization_parent_key, 
				organization_company_id, organization_type_id, note, is_single,
				full_path, has_children, depth,
				 profession_id
			)
			SELECT
				id, sorg.customer_id, sorg.organization_key, sorg.organization_name, sorg.organization_name_full,
				fn_key_to_id(sorg.organization_parent_key, sorg.customer_id, 'org'),
				sorg.organization_parent_key,
				sorg.organization_company_id, ot.organization_type_id, sorg.note, sorg.is_single,
				NULL, NULL, null,
				 sorg.profession_id
			FROM `mup-source`.source_dim_organization sorg
			INNER JOIN dim_organization_type ot on sorg.organization_type_id = ot.organization_type_id
			WHERE sorg.customer_id = customerId
			ORDER BY ot.organization_type_level
			ON DUPLICATE KEY UPDATE
				organization_id = sorg.id,
				organization_name = sorg.organization_name,
				organization_name_full = sorg.organization_name_full,
				organization_parent_id = fn_key_to_id(sorg.organization_parent_key, sorg.customer_id, 'org'),
				organization_parent_key = sorg.organization_parent_key,
				organization_type_id = ot.organization_type_id,
				note = sorg.note,
				is_single = sorg.is_single,
				profession_id = sorg.profession_id
			;
END;

drop procedure if exists pro_update_dim_organization;
CREATE  PROCEDURE `pro_update_dim_organization`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE refresh TIMESTAMP DEFAULT p_refresh;
		DECLARE p_message VARCHAR(10000) DEFAULT '【机构维-全路径，深度，是否有子节点】';

		LB_CURSOR:BEGIN
			DECLARE oKey, oId VARCHAR(32);
			DECLARE hasChild INTEGER(1) DEFAULT 0;

			DECLARE done INT DEFAULT 0;
			DECLARE s_cur CURSOR FOR
				SELECT organization_key oKey, organization_id oId FROM dim_organization;
			DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;

			OPEN s_cur;
			FETCH s_cur INTO  oKey, oId;

				WHILE1: WHILE done != 1 DO

						SET @fp = SUBSTRING(fn_get_tree_list_dim_organization(oKey), 4); -- 去掉-1
						SET @depth = (SELECT LENGTH(@fp) - LENGTH(REPLACE(@fp,'_','')) as deept);

						SET @fp2 = left(@fp, (LENGTH(@fp) -1));  -- 去掉最后_
						SET hasChild = (SELECT count(1) from dim_organization t where t.organization_parent_id = oId);
						IF hasChild > 0 THEN
							SET hasChild = 1;
						END IF;

						UPDATE dim_organization SET full_path = @fp2, depth = @depth, has_children = hasChild 
						where organization_key = oKey and organization_id = oId;

					FETCH s_cur INTO  oKey, oId;
				END WHILE WHILE1;

			CLOSE s_cur;
		END LB_CURSOR;
END;
/*==============================================================*/
/* 岗位                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_position;
CREATE  PROCEDURE `pro_fetch_dim_position`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;

		INSERT INTO dim_position (
			position_id,
			customer_id,
			position_key,
			position_name,
			show_index
		)
		SELECT
			position_id,
			customerId,
			t.position_key,
			t.position_name,
			show_index
		FROM `mup-source`.source_dim_position t
		WHERE t.customer_id = customerId
		ON DUPLICATE KEY UPDATE
			customer_id = t.customer_id,
			position_key = t.position_key,
			position_name = t.position_name,
			show_index = t.show_index
		;
END;
/*==============================================================*/
/* 主序列                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_sequence;
CREATE PROCEDURE `pro_fetch_dim_sequence`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【主序列维】';

			REPLACE INTO dim_sequence (
				sequence_id,
				customer_id,
				sequence_key,
				sequence_name,
				curt_name,
				show_index
			)
			SELECT
				sequence_id,
				customerId,
				sequence_key,
				sequence_name,
				curt_name,
				show_index
			FROM `mup-source`.source_dim_sequence t;
END;
/*==============================================================*/
/* 子序列                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_sequence_sub;
CREATE  PROCEDURE `pro_fetch_dim_sequence_sub`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh TIMESTAMP(6) )
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【子序列维】';

			REPLACE INTO dim_sequence_sub (
				sequence_sub_id,customer_id, sequence_id,
				sequence_sub_key,sequence_sub_name, curt_name, show_index
			)
			SELECT 
				sequence_sub_id,customer_id, sequence_id,
				sequence_sub_key,sequence_sub_name, curt_name, show_index
			FROM `mup-source`.source_dim_sequence_sub t
			WHERE t.customer_id = customerId 
			;
END;
/*==============================================================*/
/* 职衔                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_job_title;
CREATE  PROCEDURE `pro_fetch_dim_job_title`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh TIMESTAMP(6) )
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【职衔维】';

		REPLACE INTO dim_job_title (
			job_title_id,
			customer_id,
			job_title_key,
			job_title_name,
			curt_name,
			show_index
		)
		SELECT
			job_title_id,
			customer_id,
			job_title_key,
			job_title_name,
			curt_name,
			show_index
		FROM `mup-source`.source_dim_job_title t
		WHERE t.customer_id = customerId 
		;
END;

/*==============================================================*/
/* 大职级（能力层级）                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_ability;
CREATE  PROCEDURE `pro_fetch_dim_ability`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32) ,in p_refresh TIMESTAMP(6))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【能力层级维】');

		REPLACE INTO dim_ability (
			ability_id,
			customer_id,
			ability_key,
			ability_name,
			curt_name,
			type,
			show_index
		)
		SELECT
			ability_id,
			customer_id,
			ability_key,
			ability_name,
			curt_name,
			type,
			show_index
		FROM `mup-source`.source_dim_ability t
		WHERE t.customer_id = customerId 
		;
END;

/*==============================================================*/
/* 小职级（职级）                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_ability_lv;
CREATE  PROCEDURE `pro_fetch_dim_ability_lv`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6) )
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【能力层级子级维】';

	REPLACE INTO dim_ability_lv (ability_lv_id,customer_id,ability_lv_key,ability_lv_name,curt_name,show_index)
	SELECT
		ability_lv_id,customer_id,ability_lv_key,ability_lv_name,curt_name,show_index
	FROM `mup-source`.source_dim_ability_lv t
	WHERE t.customer_id = customerId 
	;
END;
/*==============================================================*/
/* 关键人才类别                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_key_talent_type;
CREATE  PROCEDURE `pro_fetch_dim_key_talent_type`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【维度表-关键人才库维】';

     INSERT INTO dim_key_talent_type (
     		key_talent_type_id,	customer_id,	key_talent_type_key,	key_talent_type_name,	show_index) 
     SELECT 
				key_talent_type_id,	customerId,	key_talent_type_key,	key_talent_type_name,	show_index
     FROM `mup-source`.source_dim_key_talent_type a 
     ON DUPLICATE KEY UPDATE
	     key_talent_type_key=a.key_talent_type_key,
	     key_talent_type_name=a.key_talent_type_name,
	     show_index=a.show_index;
end;
/*==============================================================*/
/* 课程维                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_course;
CREATE  PROCEDURE `pro_fetch_dim_course`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【课程维】';

			REPLACE INTO dim_course (
				course_id,
				customer_id,
				course_key,
				course_name,
				course_type_id,
				show_index
			)
			SELECT
				course_id,
				customer_id,
				course_key,
				course_name,
				course_type_id,
				show_index
			FROM `mup-source`.source_dim_course t
			WHERE t.customer_id = customerId
			;
END;
/*==============================================================*/
/* 课程类别维                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_course_type;
CREATE  PROCEDURE `pro_fetch_dim_course_type`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh TIMESTAMP(6))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【课程类别维】';

	REPLACE INTO dim_course_type (
		course_type_id,
		customer_id,
		course_type_key,
		course_type_name,
		show_index
	)
	SELECT
		course_type_id,
		customer_id,
		course_type_key,
		course_type_name,
		show_index
	FROM `mup-source`.source_dim_course_type t
	WHERE t.customer_id = customerId
	;
END;
/*==============================================================*/
/* 流失                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_run_off;
CREATE  PROCEDURE `pro_fetch_dim_run_off`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【维度表-流失维】';

   insert into dim_run_off (
   		  run_off_id,customer_id,run_off_key,run_off_name,type,show_index)
   select 
   		id,customerId,run_off_key,run_off_name,type,show_index
   from `mup-source`.source_dim_run_off a
   on duplicate key update
	   run_off_key=a.run_off_key,
	   run_off_name=a.run_off_name,
	   type=a.type,
	   show_index=a.show_index
	   ;

--  truncate table `mup-source`.source_dim_run_off;
end;
/*==============================================================*/
/* 工资结构                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_structure;
CREATE  PROCEDURE `pro_fetch_dim_structure`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【工资结构】');

	LB_INSERT:BEGIN
			INSERT INTO dim_structure (
				structure_id, customer_id, structure_name, is_fixed,show_index
			)
			SELECT
				structure_id, customer_id, structure_name, is_fixed,show_index
			FROM `mup-source`.source_dim_structure t
			ON DUPLICATE KEY UPDATE
					customer_id = t.customer_id,
					structure_name = t.structure_name,
					is_fixed = t.is_fixed,
					show_index = t.show_index
			;
	END LB_INSERT;
END;
/*==============================================================*/
/* 项目类型维                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_project_type;
CREATE  PROCEDURE `pro_fetch_dim_project_type`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【维度表-项目类型维】');

    insert into dim_project_type(project_type_id,customer_id,project_type_name,show_index)
    select project_type_id,p_customer_id,project_type_name,show_index 
    from `mup-source`.source_dim_project_type a
    on duplicate key update
    project_type_name=a.project_type_name,
    show_index=a.show_index;
END;
/*==============================================================*/
/* 项目投入费用类型维                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_project_input_type;
CREATE  PROCEDURE `pro_fetch_dim_project_input_type`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【维度表-项目投入费用类型维】');

    INSERT INTO dim_project_input_type(
    	project_input_type_id,customer_id,project_input_type_name,show_index)
    SELECT project_input_type_id,customerId,project_input_type_name,show_index 
    FROM `mup-source`.source_dim_project_input_type a
    ON DUPLICATE KEY UPDATE
	    project_input_type_name=a.project_input_type_name,
	    show_index=a.show_index;
END;
/*==============================================================*/
/* 异动类型维                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_change_type;
CREATE PROCEDURE `pro_fetch_dim_change_type`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【维度表-异动类型维】';

    insert into dim_change_type 
    select change_type_id,p_customer_id,change_type_name,curt_name,show_index,null,null 
    from `mup-source`.source_dim_change_type a on duplicate key update
	    change_type_name=a.change_type_name,
	    curt_name=a.curt_name,
	    show_index=a.show_index;
end;
/*==============================================================*/
/* 招聘渠道维                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_channel;
CREATE PROCEDURE `pro_fetch_dim_channel`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE refresh TIMESTAMP DEFAULT p_refresh;
		DECLARE p_message VARCHAR(10000) DEFAULT '【维度表-招聘渠道维】';

		insert into dim_channel (
			channel_id, customer_id,channel_key,channel_name,show_index)
		select 
			channel_id, customerId,channel_key,channel_name,show_index 
		from `mup-source`.source_dim_channel a 
		on duplicate key update
		channel_key=a.channel_key,
		channel_name=a.channel_name,
		show_index=a.show_index
	;
		--  truncate table  `mup-source`.source_dim_channel;
end;



/*==============================================================*/
/* 离职周期范围维                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_dismission_week;
CREATE PROCEDURE `pro_fetch_dim_dismission_week`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【维度表-离职周期范围维】';

    INSERT INTO dim_dismission_week (
    	dismission_week_id,customer_id,`name`,days,type,show_index
    )
    SELECT
    	dismission_week_id,customerId,`name`,days,type,show_index
    FROM `mup-source`.source_dim_dismission_week a
    ON DUPLICATE KEY UPDATE
      `name`=a.`name`,
       days=a.days,
       type=a.type,
       show_index=a.show_index;

--   truncate table `mup-source`.source_dim_dismission_week;
end;
/*==============================================================*/
/* 激励要素表                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_encourages;
CREATE  PROCEDURE `pro_fetch_dim_encourages`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【维度表-激励要素表】';

    insert into dim_encourages (encourages_id,customer_id,encourages_key,content, show_index)
    select encourages_id,p_customer_id,encourages_key,content ,show_index
    from `mup-source`.source_dim_encourages a
    on duplicate key update
    encourages_key=a.encourages_key,
    content=a.content,
    show_index=a.show_index
    ;
end;
/*==============================================================*/
/* 绩效                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_performance;
CREATE PROCEDURE `pro_fetch_dim_performance`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【维度表-绩效维】';

   insert into dim_performance(performance_id,customer_id,performance_key,performance_name,curt_name,show_index) 
   select performance_id,p_customer_id,performance_key,performance_name,curt_name,show_index
   from `mup-source`.source_dim_performance a on duplicate key update
    performance_key=a.performance_key,
    performance_name=a.performance_name,
    curt_name=a.curt_name,
    show_index=a.show_index;

--   truncate table  `mup-source`.source_dim_performance;
end;
/*==============================================================*/
/* 人群                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_population;
CREATE PROCEDURE `pro_fetch_dim_population`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;

	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【人群范围维】');

		LB_INSERT:BEGIN

				INSERT INTO dim_population (
					population_id,
					customer_id,
					population_key,
					population_name,
					show_index
				)
				SELECT
					population_id,
					customer_id,
					population_key,
					population_name,
					show_index
				FROM `mup-source`.source_dim_population t
				WHERE customer_id = customerId
				ON DUPLICATE KEY UPDATE
						customer_id = t.customer_id,
						population_key = t.population_key,
						population_name = t.population_name,
						show_index = t.show_index
				;
		END LB_INSERT;
END;
/*==============================================================*/
/* 能力素质维                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_quality;
CREATE  PROCEDURE `pro_fetch_dim_quality`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【维度表-能力素质维】';

   insert into dim_quality(quality_id,customer_id,vocabulary,note) 
   select  quality_id,customerId,vocabulary,note 
   from `mup-source`.source_dim_quality a
   ON DUPLICATE KEY UPDATE
    vocabulary=a.vocabulary,
    note=a.note,
    show_index=a.show_index;

--  truncate table `mup-soure`.dim_quality;
end;
/*==============================================================*/
/* 考勤类型                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_checkwork_type;
CREATE  PROCEDURE `pro_fetch_dim_checkwork_type`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【维度表-考勤类型】';

     INSERT INTO dim_checkwork_type (
     	checkwork_type_id,customer_id,checkwork_type_name,curt_name,show_index)
     SELECT 
     	checkwork_type_id,customerId,checkwork_type_name,curt_name,show_index 
     FROM `mup-source`.source_dim_checkwork_type a 
     ON DUPLICATE KEY UPDATE
	     checkwork_type_name=a.checkwork_type_name,
	     curt_name=a.curt_name,
	     show_index=a.show_index
	;

--    truncate table `mup-source`.source_dim_checkwork_type;
end;
/*==============================================================*/
/* 证书信息                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_certificate_info;
CREATE PROCEDURE `pro_fetch_dim_certificate_info`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【证书维】';

	INSERT INTO dim_certificate_info (
		certificate_info_id,
		certificate_name,
		customer_id,
		certificate_type_id,
		curt_name,
		show_index
		)
	SELECT 
		certificate_info_id,
		certificate_name,
		customer_id,
		certificate_type_id,
		curt_name,
		show_index
 	FROM `mup-source`.source_dim_certificate_info a
 	ON DUPLICATE KEY UPDATE
	 	certificate_name    = a.certificate_name,
	 	customer_id         = a.customer_id,
	 	certificate_type_id = a.certificate_type_id,
	 	curt_name           = a.curt_name,
	 	show_index           = a.show_index
 	;
END;
/*==============================================================*/
/* 销售看板                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_sales_team;
CREATE  PROCEDURE `pro_fetch_dim_sales_team`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【团队信息】';


    insert into dim_sales_team (team_id,customer_id,team_name,emp_id,emp_names, show_index) 
    select team_id,p_customer_id,team_name,emp_id,emp_names, show_index
    from `mup-source`.source_dim_sales_team a on duplicate key update
    team_name=a.team_name,
    emp_id=a.emp_id,
    emp_names=a.emp_names,
    show_index=a.show_index
    ;
end;
/*==============================================================*/
/* 产品明细                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_sales_product;
CREATE PROCEDURE `pro_fetch_dim_sales_product`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【产品明细】';

   INSERT INTO dim_sales_product (
		product_id,customer_id,product_name,product_price,product_cost) 
   SELECT 
   		product_id,customerId,product_name,product_price,product_cost 
   FROM `mup-source`.source_dim_sales_product a
   ON DUPLICATE KEY UPDATE
	   product_name=a.product_name,
	   product_price=a.product_price,
	   product_cost=a.product_cost
	;
end;
/*==============================================================*/
/* 离职风险                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_separation_risk;
CREATE  PROCEDURE `pro_fetch_dim_separation_risk`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【维度表-离职风险维】';

   insert into dim_separation_risk(
   		  separation_risk_id,customer_id,separation_risk_key,separation_risk_parent_id,separation_risk_parent_key,separation_risk_name,refer,show_index)
   select separation_risk_id,customerId,separation_risk_key,separation_risk_parent_id,separation_risk_parent_key,separation_risk_name,refer,show_index 
   from `mup-source`.source_dim_separation_risk a
   on duplicate key update
   separation_risk_key=a.separation_risk_key,
   separation_risk_parent_id=a.separation_risk_parent_id,
   separation_risk_parent_key=a.separation_risk_parent_key,
   separation_risk_name=a.separation_risk_name,
   refer=a.refer,
   show_index=a.show_index;

  -- truncate table `mup-source`.source_dim_separation_risk;
end;
/*==============================================================*/
/* 机构类型                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_organization_type;
CREATE PROCEDURE `pro_fetch_dim_organization_type`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【机构级别-维度表】：数据刷新完成';

  	INSERT INTO dim_organization_type
  	SELECT organization_type_id,customer_id,organization_type_key,organization_type_level,organization_type_name,show_index,null,null
  	FROM `mup-source`.source_dim_organization_type a
  	ON DUPLICATE KEY UPDATE
  		organization_type_level=a.organization_type_level,
		organization_type_name=a.organization_type_name,
		show_index=a.show_index
	;
END;
/*==============================================================*/
/* 满意度分类                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_satfac_genre;
CREATE PROCEDURE `pro_fetch_dim_satfac_genre`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【维度表-满意度分类】');

	INSERT INTO dim_satfac_genre
  	SELECT satfac_genre_id,customer_id,satfac_name,satfac_genre_parent_id,is_children,show_index,null,null
  	FROM `mup-source`.source_dim_satfac_genre a
  	ON DUPLICATE KEY UPDATE
  		satfac_name=a.satfac_name,
		satfac_genre_parent_id=a.satfac_genre_parent_id,
		is_children=a.is_children,
		show_index=a.show_index
	;
END;
/*==============================================================*/
/* 敬业度分类                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_dedicat_genre;
CREATE PROCEDURE `pro_fetch_dim_dedicat_genre`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【维度表-敬业度分类】');

   insert into dim_dedicat_genre(dedicat_genre_id,customer_id,dedicat_name,dedicat_genre_parent_id,is_children)
   select dedicat_genre_id,customer_id,dedicat_name,dedicat_genre_parent_id,is_children
   from `mup-source`.source_dim_dedicat_genre a
   on duplicate key update
   dedicat_name=a.dedicat_name,
   dedicat_genre_parent_id=a.dedicat_genre_parent_id,
   is_children=a.is_children;
END;
/*==============================================================*/
/* 行业维                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_profession;
CREATE PROCEDURE `pro_fetch_dim_profession`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh TIMESTAMP(6) )
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【维度表-行业维】');

	INSERT INTO dim_profession (profession_id,profession_name,`profession_key`,show_index )
	SELECT profession_id,profession_name,`profession_key`,show_index 
	FROM `mup-source`.source_dim_profession a 
	ON DUPLICATE KEY UPDATE
	    profession_name=a.profession_name,
	    profession_key=a.profession_key,
	    show_index=a.show_index
	;
END;
/*==============================================================*/
/* 市                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_city;
CREATE  PROCEDURE `pro_fetch_dim_city`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【维度表-城市】';

	insert into dim_city (city_id,customer_id,city_key,city_name,province_id,show_index)
	select city_id,customer_id,city_key,city_name,province_id,show_index
	from  `mup-source`.source_dim_city;
end;
/*==============================================================*/
/* 省                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_dim_province;
CREATE  PROCEDURE `pro_fetch_dim_province`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【维度表-省】';

	insert into dim_province (province_id,customer_id,province_key,province_name,curt_name,`show_index`)
	select province_id,p_customer_id,province_key,province_name,curt_name,`show_index` 
	from `mup-source`.source_dim_province;
end;


/*==============================================================*/
/* 学校                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_matching_school;
CREATE  PROCEDURE `pro_fetch_matching_school`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【匹配表-学校表】';
   
	insert into matching_school (matching_school_id,customer_id,`name`,school_type,`level`)  
	select matching_school_id,p_customer_id,`name`,school_type,`level` 
	from `mup-source`.source_matching_school a
	on duplicate key update 
	 `name`=a.`name`,
	 school_type=a.school_type,
	 `level`=a.`level`;
   
	-- truncate table `mup-source`.source_matching_school;
end;

-- 分数映射
drop procedure if exists pro_fetch_matching_score;
CREATE  PROCEDURE `pro_fetch_matching_score`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【匹配表-分数映射】';
  	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
  
    INSERT INTO matching_score (
    	matching_score_id,customer_id,v1,show_index
    )
    SELECT 
    	matching_score_id,customer_id,v1,show_index
    FROM `mup-source`.source_matching_score a 
    ON DUPLICATE KEY UPDATE 
	    v1=a.v1,
	    show_index=a.show_index
	    ;
end;

/*==============================================================*/
/* 代码组（字典表）                                              */
/*==============================================================*/
drop procedure if exists pro_fetch_sys_code_item;
CREATE  PROCEDURE `pro_fetch_sys_code_item`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【代码组（字典表）】';
	
	-- 360_abilit
	DELETE FROM `sys_code_item` where code_group_id = '360_ability';
	INSERT INTO `sys_code_item` VALUES ('AB_11', customerId, '1', '全局观,1', '360_ability', '1',null,null);
	INSERT INTO `sys_code_item` VALUES ('AB_12', customerId, '2', '商业敏感,1', '360_ability', '2',null,null);
	INSERT INTO `sys_code_item` VALUES ('AB_13', customerId, '3', '创新突破,1', '360_ability', '3',null,null);
	INSERT INTO `sys_code_item` VALUES ('AB_14', customerId, '4', '归纳思维,1', '360_ability', '4',null,null);
	INSERT INTO `sys_code_item` VALUES ('AB_15', customerId, '5', '战略导向,1', '360_ability', '5',null,null);
	INSERT INTO `sys_code_item` VALUES ('AB_21', customerId, '6', '沟通协调,2', '360_ability', '6',null,null);
	INSERT INTO `sys_code_item` VALUES ('AB_22', customerId, '7', '动手能力,2', '360_ability', '7',null,null);
	INSERT INTO `sys_code_item` VALUES ('AB_31', customerId, '8', '人际理解,3', '360_ability', '8',null,null);
	INSERT INTO `sys_code_item` VALUES ('AB_32', customerId, '9', '组织学习,3', '360_ability', '9',null,null);
	INSERT INTO `sys_code_item` VALUES ('AB_33', customerId, '10', '团队领导,3', '360_ability', '10',null,null);
	-- 360_ability_lv
	DELETE FROM `sys_code_item` where code_group_id = '360_ability_lv';
	INSERT INTO `sys_code_item` VALUES ('AB_LV_1', customerId, '1', '1级', '360_ability_lv','1',null,null);
	INSERT INTO `sys_code_item` VALUES ('AB_LV_2', customerId, '2', '2级', '360_ability_lv','2',null,null);
	INSERT INTO `sys_code_item` VALUES ('AB_LV_3', customerId, '3', '3级', '360_ability_lv','3',null,null);
	INSERT INTO `sys_code_item` VALUES ('AB_LV_4', customerId, '4', '4级', '360_ability_lv','4',null,null);
	-- 地区
	DELETE FROM `sys_code_item` where code_group_id = 'area';
	INSERT INTO `sys_code_item` VALUES ('REGION_CH',	customerId,	'CH',	'中方地区',	'area',1,null,null);
	INSERT INTO `sys_code_item` VALUES ('REGION_UK',	customerId,	'UK',	'英方地区',	'area',2,null,null);
	INSERT INTO `sys_code_item` VALUES ('REGION_JP',	customerId,	'JP',	'日方地区',	'area',3,null,null);
	INSERT INTO `sys_code_item` VALUES ('REGION_US',	customerId,	'US',	'美方地区',	'area',4,null,null);
	INSERT INTO `sys_code_item` VALUES ('REGION_RU',	customerId,	'RU',	'俄方地区',	'area',5,null,null);
	
	-- 员工考勤类型
	DELETE FROM `sys_code_item` where code_group_id = 'opt_reason';
	INSERT INTO `sys_code_item` VALUES ('REASON_TRIP', customerId, '1',	'出差',	'opt_reason','1',null,null);
	INSERT INTO `sys_code_item` VALUES ('REASON_LATE', customerId, '2',	'迟到',	'opt_reason','2',null,null);
	INSERT INTO `sys_code_item` VALUES ('REASON_FORGET', customerId, '3',	'忘打卡',	'opt_reason','3',null,null);
	INSERT INTO `sys_code_item` VALUES ('REASON_TO_GO_OUT', customerId, '4',	'外出',	'opt_reason','4',null,null);
	INSERT INTO `sys_code_item` VALUES ('REASON_OTHER', customerId, '6',	'其他',	'opt_reason','6',null,null);
	INSERT INTO `sys_code_item` VALUES ('REASON_OVERTIME', customerId, '5',	'加班',	'opt_reason','5',null,null);
	
	-- 证书类别
	DELETE FROM `sys_code_item` where code_group_id = 'certificate_type';
	INSERT INTO `sys_code_item` VALUES ('CERT_DB',		customerId, 'SJK',	'数据库证书',	'certificate_type','1',null,null);
	INSERT INTO `sys_code_item` VALUES ('CERT_En', 		customerId, 'YY',	'英语证书',	'certificate_type','2',null,null);
	INSERT INTO `sys_code_item` VALUES ('CERT_Intel',	customerId, 'WL',	'网络证书',	'certificate_type','3',null,null);
	-- 分位值
	DELETE FROM `sys_code_item` where code_group_id = 'quantile';
	INSERT INTO `sys_code_item` VALUES ('QV_10', customerId,'1', '10',	'quantile',	'1',null,null);
	INSERT INTO `sys_code_item` VALUES ('QV_25', customerId,'2', '25',	'quantile',	'2',null,null);
	INSERT INTO `sys_code_item` VALUES ('QV_50', customerId,'3', '50',	'quantile',	'3',null,null);
	INSERT INTO `sys_code_item` VALUES ('QV_75', customerId,'4', '75',	'quantile',	'4',null,null);
	INSERT INTO `sys_code_item` VALUES ('QV_90', customerId,'5', '90',	'quantile',	'5',null,null);
	-- 固定福利
	DELETE FROM `sys_code_item` where code_group_id = 'nfb';
	INSERT INTO `sys_code_item` VALUES ('NFB_1', customerId,'1', '养老保险',	'nfb',	'1',null,null);
	INSERT INTO `sys_code_item` VALUES ('NFB_2', customerId,'2', '失业保险',	'nfb',	'2',null,null);
	INSERT INTO `sys_code_item` VALUES ('NFB_3', customerId,'3', '医疗保险',	'nfb',	'3',null,null);
	INSERT INTO `sys_code_item` VALUES ('NFB_4', customerId,'4', '工伤保险',	'nfb',	'4',null,null);
	INSERT INTO `sys_code_item` VALUES ('NFB_5', customerId,'5', '生育保险',	'nfb',	'5',null,null);
	INSERT INTO `sys_code_item` VALUES ('NFB_6', customerId,'6', '公积金',	'nfb',	'6',null,null);
	-- 货币福利
	DELETE FROM `sys_code_item` where code_group_id = 'cpm';
	INSERT INTO `sys_code_item` VALUES ('CPM_1', customerId,'1', '企业年金',	'cpm',	'1',null,null);
	INSERT INTO `sys_code_item` VALUES ('CPM_2', customerId,'2', '节日礼物',	'cpm',	'2',null,null);
	INSERT INTO `sys_code_item` VALUES ('CPM_3', customerId,'3', '商业保险',	'cpm',	'3',null,null);
	INSERT INTO `sys_code_item` VALUES ('CPM_4', customerId,'4', '年度旅游',	'cpm',	'4',null,null);
	INSERT INTO `sys_code_item` VALUES ('CPM_5', customerId,'5', '婚育津贴',	'cpm',	'5',null,null);
	INSERT INTO `sys_code_item` VALUES ('CPM_6', customerId,'6', '高温补贴',	'cpm',	'6',null,null);
	-- 非货币福利
	DELETE FROM `sys_code_item` where code_group_id = 'uncpm';
	INSERT INTO `sys_code_item` VALUES ('UNCPM_1', customerId,'1', '年假',	'uncpm',	'1',null,null);
	INSERT INTO `sys_code_item` VALUES ('UNCPM_2', customerId,'2', '产假',	'uncpm',	'2',null,null);
	INSERT INTO `sys_code_item` VALUES ('UNCPM_3', customerId,'3', '婚假',	'uncpm',	'3',null,null);
	-- x轴
	DELETE FROM `sys_code_item` where code_group_id = 'sales_emp_rank';
	INSERT INTO `sys_code_item` VALUES ('Sale_XAxis_1', customerId,'1', '前20%', 	'sales_emp_rank',	'1',null,null);
	INSERT INTO `sys_code_item` VALUES ('Sale_XAxis_2', customerId,'2', '40%-20%', 	'sales_emp_rank',	'2',null,null);
	INSERT INTO `sys_code_item` VALUES ('Sale_XAxis_3', customerId,'3', '70%-40%', 	'sales_emp_rank',	'3',null,null);
	INSERT INTO `sys_code_item` VALUES ('Sale_XAxis_4', customerId,'4', '90%-70%', 	'sales_emp_rank',	'4',null,null);
	INSERT INTO `sys_code_item` VALUES ('Sale_XAxis_5', customerId,'5', '后10%', 	'sales_emp_rank',	'5',null,null);

	-- 人才地图配置
	DELETE FROM `sys_code_item` WHERE code_group_id = 'talent_map_zindex';
	INSERT INTO `sys_code_item` VALUES ('Z_IND_1',customerId,'1', '舒畅', 'talent_map_zindex','1',null,null);
	INSERT INTO `sys_code_item` VALUES ('Z_IND_2',customerId,'2', '庆幸', 'talent_map_zindex','2',null,null);
	INSERT INTO `sys_code_item` VALUES ('Z_IND_3',customerId,'3', '快乐', 'talent_map_zindex','3',null,null);
	INSERT INTO `sys_code_item` VALUES ('Z_IND_4',customerId,'4', '快活', 'talent_map_zindex','4',null,null);
	INSERT INTO `sys_code_item` VALUES ('Z_IND_5',customerId,'5', '开心', 'talent_map_zindex','5',null,null);
	INSERT INTO `sys_code_item` VALUES ('Z_IND_6',customerId,'6', '好受', 'talent_map_zindex','6',null,null);
	INSERT INTO `sys_code_item` VALUES ('Z_IND_7',customerId,'7', '高兴', 'talent_map_zindex','7',null,null);
	
	-- 人才地图配置表
--	-- *****5#6#E1F5E2分解为x_number,y_number color
--	DELETE FROM `sys_code_item` WHERE code_group_id = 'map_config';
--	INSERT INTO `sys_code_item` VALUES ('5#6#E1F5E2',customerId,'5#6#E1F5E2','庆幸','map_config','1');
--	INSERT INTO `sys_code_item` VALUES ('5#7#B3E6B2',customerId,'5#7#B3E6B2','舒畅','map_config','2');
--	INSERT INTO `sys_code_item` VALUES ('1#1#FECAC2',customerId,'1#1#FECAC2','高兴','map_config','3');
--	INSERT INTO `sys_code_item` VALUES ('1#2#FDDFD2',customerId,'1#2#FDDFD2','好受','map_config','4');
--	INSERT INTO `sys_code_item` VALUES ('2#1#FECAC2',customerId,'2#1#FECAC2','高兴','map_config','5');
--	INSERT INTO `sys_code_item` VALUES ('2#2#FDDFD2',customerId,'2#2#FDDFD2','好受','map_config','6');
--	INSERT INTO `sys_code_item` VALUES ('2#3#FFFF92',customerId,'2#3#FFFF92','开心','map_config','7');
--	INSERT INTO `sys_code_item` VALUES ('2#4#FFFFB2',customerId,'2#4#FFFFB2','快活','map_config','8');
--	INSERT INTO `sys_code_item` VALUES ('3#2#FDDFD2',customerId,'3#2#FDDFD2','好受','map_config','9');
--	INSERT INTO `sys_code_item` VALUES ('3#3#FFFF92',customerId,'3#3#FFFF92','开心','map_config','10');
--	INSERT INTO `sys_code_item` VALUES ('3#4#FFFFB2',customerId,'3#4#FFFFB2','快活','map_config','11');
--	INSERT INTO `sys_code_item` VALUES ('3#5#FFFFD2',customerId,'3#5#FFFFD2','快乐','map_config','12');
--	INSERT INTO `sys_code_item` VALUES ('4#3#FFFF92',customerId,'4#3#FFFF92','开心','map_config','13');
--	INSERT INTO `sys_code_item` VALUES ('4#4#FFFFB2',customerId,'4#4#FFFFB2','快活','map_config','14');
--	INSERT INTO `sys_code_item` VALUES ('4#5#FFFFD2',customerId,'4#5#FFFFD2','快乐','map_config','15');
--	INSERT INTO `sys_code_item` VALUES ('4#6#E1F5E2',customerId,'4#6#E1F5E2','庆幸','map_config','16');
--	INSERT INTO `sys_code_item` VALUES ('4#7#B3E6B2',customerId,'4#7#B3E6B2','舒畅','map_config','17');

	-- 员工岗位能力总评价
	INSERT INTO `sys_code_item` VALUES ('PQ_Eval_1',customerId,'A', '优', 'pqeval','1',null,null);
	INSERT INTO `sys_code_item` VALUES ('PQ_Eval_2',customerId,'B', '良', 'pqeval','2',null,null);
	INSERT INTO `sys_code_item` VALUES ('PQ_Eval_3',customerId,'C', '中', 'pqeval','3',null,null);
	INSERT INTO `sys_code_item` VALUES ('PQ_Eval_4',customerId,'D', '差', 'pqeval','4',null,null);
	
END;


-- 员工能力维度
drop procedure if exists pro_fetch_dim_ability_number;
CREATE PROCEDURE `pro_fetch_dim_ability_number`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_refresh TIMESTAMP(6))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工能力维度】';
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
  
  INSERT INTO dim_ability_number(
  	ability_number_id,customer_id,ability_number_key,ability_number_name,show_index) 
  SELECT 
  	ability_number_id,customerId,ability_number_key,ability_number_name,show_index
  FROM `mup-source`.source_dim_ability_number a
  ON DUPLICATE KEY UPDATE 
	  ability_number_key=a.ability_number_key,
	  ability_number_name=a.ability_number_name,
	  show_index=a.show_index ;
  -- truncate table `mup-source`.source_dim_ability_number;
end;
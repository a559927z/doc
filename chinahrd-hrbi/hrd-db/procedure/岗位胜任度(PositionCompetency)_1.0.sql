-- 岗位能力素质
drop procedure if exists pro_fetch_position_quality;
CREATE  PROCEDURE `pro_fetch_position_quality`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【关系表-岗位能力素质】';

	insert into position_quality 
    select position_quality_id,customer_id,position_id,quality_id,matching_score_id,show_index,null,NULL
    from `mup-source`.source_position_quality a
    on duplicate key update 
    position_id=a.position_id,
    matching_score_id=a.matching_score_id,
    show_index=a.show_index;
  truncate table `mup-source`.source_position_quality;
end;


-- 员工岗位素质得分
drop procedure if exists pro_fetch_emp_pq_relation;
CREATE PROCEDURE `pro_fetch_emp_pq_relation`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【关系表-员工岗位素质得分】';
					
		INSERT INTO emp_pq_relation 
		select 
			emp_pq_relation_id,p_customer_id,emp_id,date,matching_score_id,demands_matching_score_id,quality_id,null,`year_month`,null,null 
		from `mup-source`.source_emp_pq_relation a
     on duplicate key update 
     emp_id=a.emp_id,
     date=a.date,
     matching_score_id=a.matching_score_id,
	 demands_matching_score_id=a.demands_matching_score_id,
     quality_id=a.quality_id,
     `year_month`=a.`year_month`;					

   truncate table `mup-source`.source_emp_pq_relation;
end;

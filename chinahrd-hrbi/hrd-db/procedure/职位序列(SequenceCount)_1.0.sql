
-- 职位序列关系
drop procedure if exists pro_fetch_job_relation;
CREATE  PROCEDURE `pro_fetch_job_relation`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【关系表-职位序列关系】';
   
 
	INSERT INTO job_relation(
		job_relation_id,customer_id,sequence_id,sequence_sub_id,ability_id,ability_lv_id,job_title_id,`year`,type,rank_name,show_index)
	SELECT 
		job_relation_id,customer_id,sequence_id,sequence_sub_id,ability_id,ability_lv_id,job_title_id,`year`,type,rank_name,show_index 
	FROM `mup-source`.source_job_relation a
	ON DUPLICATE KEY UPDATE 
		sequence_id=a.sequence_id,
		sequence_sub_id=a.sequence_sub_id,
		ability_id=a.ability_id,
		ability_lv_id=a.ability_lv_id,
		job_title_id=a.job_title_id,
		`year`=a.`year`,
		type=a.type,
		rank_name=a.rank_name,
		show_index=a.show_index
		;
end;

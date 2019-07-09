DROP INDEX index_customerId ON dim_organization;
DROP INDEX index_key ON dim_organization;
DROP INDEX index_parentId ON dim_organization;
DROP INDEX index_fullPath ON dim_organization;
DROP INDEX index_effectDate ON dim_organization;
DROP INDEX index_expiryDate ON dim_organization;
ALTER TABLE `dim_organization` ADD INDEX index_customerId ( `customer_id` );
ALTER TABLE `dim_organization` ADD INDEX index_key ( `organization_key` );
ALTER TABLE `dim_organization` ADD INDEX index_parentId ( `organization_parent_id` );
ALTER TABLE `dim_organization` ADD INDEX index_fullPath ( `full_path` );
ALTER TABLE `dim_organization` ADD INDEX index_effectDate ( `effect_date` );
ALTER TABLE `dim_organization` ADD INDEX index_expiryDate ( `expiry_date` );

DROP INDEX index_customerId ON role_organization_relation;
DROP INDEX index_halfCheck ON role_organization_relation;
ALTER TABLE `role_organization_relation` ADD INDEX index_customerId ( `customer_id` );
ALTER TABLE `role_organization_relation` ADD INDEX index_halfCheck ( `half_check` );

DROP INDEX index_customerId ON dim_role;
DROP INDEX index_key ON dim_role;
ALTER TABLE `dim_role` ADD INDEX index_customerId ( `customer_id` );
ALTER TABLE `dim_role` ADD INDEX index_key ( `role_key` );

DROP INDEX index_customerId ON dim_function;
DROP INDEX index_key ON dim_function;
DROP INDEX index_parentId ON dim_function;
ALTER TABLE `dim_function` ADD INDEX index_customerId ( `customer_id` );
ALTER TABLE `dim_function` ADD INDEX index_key ( `function_key` );
ALTER TABLE `dim_function` ADD INDEX index_parentId ( `function_parent_id` );

DROP INDEX index_customerId ON dim_user;
DROP INDEX index_key ON dim_user;
DROP INDEX index_username ON dim_user;
DROP INDEX index_pwd ON dim_user;
ALTER TABLE `dim_user` ADD INDEX index_customerId ( `customer_id` );
ALTER TABLE `dim_user` ADD INDEX index_key ( `user_key` );
ALTER TABLE `dim_user` ADD INDEX index_username ( `user_name` );
ALTER TABLE `dim_user` ADD INDEX index_pwd ( `password` );


DROP INDEX index_empId ON emp_position_relation;
ALTER TABLE `emp_position_relation` ADD INDEX index_empId ( `emp_id` );

DROP INDEX index_empId ON emp_relation;
DROP INDEX index_empHfId ON emp_relation;
ALTER TABLE `emp_relation` ADD INDEX index_empId ( `emp_id` );
ALTER TABLE `emp_relation` ADD INDEX index_empHfId ( `emp_hf_id` );

DROP INDEX index_eKey ON dim_emp;
DROP INDEX index_eId ON dim_emp;
DROP INDEX index_ed ON dim_emp;
DROP INDEX index_rod ON dim_emp;
ALTER TABLE `dim_emp` ADD INDEX index_eKey ( `emp_key` );
ALTER TABLE `dim_emp` ADD INDEX index_eId ( `emp_id` );
ALTER TABLE `dim_emp` ADD INDEX index_ed ( `effect_date` );
ALTER TABLE `dim_emp` ADD INDEX index_rod( `run_off_date` );

DROP INDEX index_eKey ON dim_emp;
DROP INDEX index_eId ON dim_emp;
DROP INDEX index_ed ON dim_emp;
DROP INDEX index_rod ON dim_emp;
ALTER TABLE `v_dim_emp` ADD INDEX index_eKey ( `emp_key` );
ALTER TABLE `v_dim_emp` ADD INDEX index_eId ( `emp_id` );
ALTER TABLE `v_dim_emp` ADD INDEX index_ed ( `entry_date` );
ALTER TABLE `v_dim_emp` ADD INDEX index_rod( `run_off_date` );


DROP INDEX index_posId ON dim_position;
ALTER TABLE `dim_position` ADD INDEX index_posId ( `position_key` );

DROP INDEX index_abKey ON dim_ability;
ALTER TABLE `dim_ability` ADD INDEX index_abKey ( `ability_key` );
DROP INDEX index_abId ON dim_ability;
ALTER TABLE `dim_ability` ADD INDEX index_abId ( `ability_id` );

DROP INDEX index_abLvKey ON dim_ability_lv;
ALTER TABLE `dim_ability_lv` ADD INDEX index_abLvKey ( `ability_lv_key` );

DROP INDEX index_seqId ON dim_sequence;
ALTER TABLE `dim_sequence` ADD INDEX index_seqId ( `sequence_key` );

DROP INDEX index_eKey ON job_change;
DROP INDEX index_eId ON job_change;
DROP INDEX index_abId ON job_change;
DROP INDEX index_seqId ON job_change;
DROP INDEX index_seqSubId ON job_change;
DROP INDEX index_abLvId ON job_change;
DROP INDEX index_jtId ON job_change;
DROP INDEX index_posId ON job_change;
DROP INDEX index_orgId ON job_change;
ALTER TABLE `job_change` ADD INDEX index_eKey( `emp_key` );
ALTER TABLE `job_change` ADD INDEX index_eId( `emp_id` );
ALTER TABLE `job_change` ADD INDEX index_abId( `ability_id` );
ALTER TABLE `job_change` ADD INDEX index_seqId( `sequence_id` );
ALTER TABLE `job_change` ADD INDEX index_seqSubId( `sequence_sub_id` );
ALTER TABLE `job_change` ADD INDEX index_abLvId( `ability_lv_id` );
ALTER TABLE `job_change` ADD INDEX index_jtId( `job_title_id` );
ALTER TABLE `job_change` ADD INDEX index_posId( `position_id` );
ALTER TABLE `job_change` ADD INDEX index_orgId( `organization_id` );



DROP INDEX index_eKey ON performance_change;
DROP INDEX index_eId ON performance_change;
DROP INDEX index_ym ON performance_change;
DROP INDEX index_customerId ON performance_change;
ALTER TABLE `performance_change` ADD INDEX index_eKey( `emp_key` );
ALTER TABLE `performance_change` ADD INDEX index_eId( `emp_id` );
ALTER TABLE `performance_change` ADD INDEX index_ym( `year_month` );
ALTER TABLE `performance_change` ADD INDEX index_customerId( `customer_id` );

DROP INDEX index_eId ON dimission_risk;
ALTER TABLE `dimission_risk` ADD INDEX index_eId( `emp_id` );


DROP INDEX index_mBegin ON monthly_emp_count;
DROP INDEX index_ym ON monthly_emp_count;
DROP INDEX index_ofp ON monthly_emp_count;
ALTER TABLE `monthly_emp_count` ADD INDEX index_mBegin( `month_begin` );
ALTER TABLE `monthly_emp_count` ADD INDEX index_ym( `YEAR_MONTH` );
ALTER TABLE `monthly_emp_count` ADD INDEX index_ofp( `organization_full_path` );



DROP INDEX index_seqId ON job_relation;
DROP INDEX index_seqSubId ON job_relation;
DROP INDEX index_abLvId ON job_relation;
DROP INDEX index_y ON job_relation;
ALTER TABLE `job_relation` ADD INDEX index_seqId( `sequence_id` );
ALTER TABLE `job_relation` ADD INDEX index_seqSubId( `sequence_sub_id` );
ALTER TABLE `job_relation` ADD INDEX index_abId( `ability_id` );
ALTER TABLE `job_relation` ADD INDEX index_abLvId( `ability_lv_id` );
ALTER TABLE `job_relation` ADD INDEX index_y( `YEAR` );


DROP INDEX index_orgId ON tmp_stewards_under;
ALTER TABLE `tmp_stewards_under` ADD INDEX index_orgId( `organization_id` );

DROP INDEX index_tId ON 360_report;
ALTER TABLE `360_report` ADD INDEX index_tId( `360_time_id` );


DROP INDEX index_ym ON pay_collect;
ALTER TABLE `pay_collect` ADD INDEX index_ym( `year_month` );



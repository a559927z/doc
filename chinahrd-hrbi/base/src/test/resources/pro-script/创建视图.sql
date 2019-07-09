-- DROP VIEW IF EXISTS v_dim_emp;
-- CREATE view v_dim_emp(
-- 			emp_id,customer_id,emp_key,user_name,user_name_ch, email, img_path,is_post,passtime_or_fulltime,age,company_age,is_key_talent,sex,nation,degree,
-- 			native_place,country,birth_place,birth_date,national_id,national_type,marry_status,patty_name,
-- 			position_id, position_name,
-- 			organization_parent_id, organization_parent_name, organization_id,organization_name,
-- 			sequence_id, sequence_name, sequence_sub_id, sequence_sub_name,
-- 			performance_id, performance_name,
-- 			ability_id, job_title_id, ability_lv_id,
-- 			ability_name, job_title_name, ability_lv_name,
-- 			rank_name, run_off_date,
-- 			entry_date, tel_phone, qq, wx_code, address, contract_unit, work_place, regular_date, apply_type, apply_channel, speciality,
-- 			is_regular
-- 				)
-- as 
-- SELECT 	de.emp_id, de.customer_id, de.emp_key, de.user_name, de.user_name_ch, t0.email, de.img_path, de.is_post,
-- 				de.passtime_or_fulltime, de.age, de.company_age, de.is_key_talent, de.sex, de.nation, de.degree,
-- 				de.native_place, de.country, de.birth_place, de.birth_date, de.national_id, de.national_type, de.marry_status, de.patty_name,
-- 				t5.position_id, t5.position_name,
-- 				t6.organization_parent_id, t6.organization_parent_name, t9.organization_id, t9.organization_name,
-- 				t5.sequence_id, t5.sequence_name, t5.sequence_sub_id, t5.sequence_sub_name,
-- 				t6.performance_id, t6.performance_name,
-- 				t5.ability_id, t5.job_title_id, t5.ability_lv_id,
-- 				t5.ability_name, t5.job_title_name, t5.ability_lv_name,
-- 				t5.rank_name, run_off_date,
-- 				entry_date, tel_phone, qq, wx_code, address, contract_unit, work_place, regular_date, apply_type, apply_channel, speciality,
-- 				t1.is_regular 
-- FROM dim_emp de
-- LEFT JOIN dim_user t0 on t0.user_key = de.emp_key
-- 	AND t0.customer_id = de.customer_id AND t0.sys_deploy = 0
-- LEFT JOIN emp_position_relation t1 on  t1.emp_id = de.emp_id 
-- 	AND t1.customer_id = de.customer_id
-- LEFT JOIN dim_organization t9 on t9.organization_id = t1.organization_id
-- 	AND t9.customer_id = t1.customer_id
-- LEFT JOIN job_change t5 on t5.emp_key = de.emp_key
-- 	AND t5.customer_id = de.customer_id 
-- 	AND t5.emp_job_change_id = (SELECT t7.emp_job_change_id FROM job_change t7 WHERE de.emp_key = t7.emp_key ORDER BY change_date DESC LIMIT 0,1)
-- LEFT JOIN performance_change t6 on t6.emp_key = de.emp_key
-- 	AND t6.customer_id = de.customer_id 
-- 	AND t6.performance_change_id = (SELECT t8.performance_change_id FROM performance_change t8 WHERE de.emp_key = t8.emp_key ORDER BY `year_month` DESC LIMIT 0,1)
-- where de.enabled = 1 and now()>=de.effect_date and (de.expiry_date is null or de.effect_date>now())
-- ;


DROP VIEW IF EXISTS v_dim_organization;
CREATE view v_dim_organization(organization_id, customer_id, business_unit_id, organization_type_id, 
organization_key, organization_parent_id, organization_parent_key, organization_name, note,is_single, full_path,
has_children,depth)
as 
select 
organization_id, customer_id, business_unit_id, organization_type_id, 
organization_key, organization_parent_id, organization_parent_key, organization_name, note,is_single, full_path,
has_children,depth
from dim_organization t;
SELECT * FROM v_dim_organization;



DROP VIEW IF EXISTS v_dim_business_unit;
CREATE view v_dim_business_unit(business_unit_id, profession_id, customer_id, business_unit_key, business_unit_name)
as 
select 
business_unit_id, profession_id, customer_id, business_unit_key, business_unit_name
from dim_business_unit t
where now()>=t.effect_date  and t.expiry_date is null and t.enabled = 1 
;



DROP VIEW IF EXISTS v_emp_position_relation;
CREATE view v_emp_position_relation(emp_position_id, customer_id, emp_id, position_id, organization_id,is_regular)
as 
select 
emp_position_id, customer_id, emp_id, position_id, organization_id,is_regular
from emp_position_relation t;

 
drop procedure if exists proc_sql_soure_v_dim_emp;
create procedure proc_sql_soure_v_dim_emp()
begin 
 start transaction;
 update soure_v_dim_emp a left join (select * from soure_code_item where code_group_id='degree') b on a.degree=b.code_item_name 
              left join dim_performance c on a.performance_name=c.performance_name
              left join dim_sequence_sub d on a.sequence_sub_name=d.sequence_sub_name 
              left join dim_sequence e on a.sequence_name=e.sequence_name
              left join dim_organization f on a.organization_parent_name=f.organization_name
              left join dim_job_title g on a.job_title_name=g.job_title_name
              left join dim_ability_lv h on  a.ability_lv_name=h.ability_lv_name
                         set a.degree_id=b.code_item_id,
                             a.performance_id=c.performance_id,
                             a.sequence_sub_id=d.sequence_sub_id,
                             a.sequence_id=e.sequence_id,
                             a.organization_parent_id=f.organization_id,
                             a.job_title_id=g.job_title_id,
                             a.ability_lv_id=h.ability_lv_id;


  update soure_v_dim_emp set customer_id='b5c9410dc7e4422c8e0189c7f8056b5f';
  
  update soure_v_dim_emp a set  a.position_id=(select position_id from dim_position b where a.position_name=b.position_name); 
 
  update soure_v_dim_emp SET organization_name = '上海业务部' where organization_parent_name = '上海中心' and organization_name ='业务部';
  update soure_v_dim_emp SET organization_name = '业务部tets' where organization_parent_name = '广州中心' and organization_name ='业务部';

  UPDATE soure_v_dim_emp a INNER JOIN dim_ability b ON a.ability_name = b.ability_name AND b.type = 1 SET a.ability_id = b.ability_id WHERE a.sequence_name = '管理序列';
  UPDATE soure_v_dim_emp a INNER JOIN dim_ability b ON a.ability_name = b.ability_name AND b.type = 0 SET a.ability_id = b.ability_id WHERE a.sequence_name != '管理序列';

  update soure_v_dim_emp a set a.organization_id=(select organization_id from dim_organization b  where a.organization_name=b.organization_name and a.organization_parent_id=b.organization_parent_id); 

  update soure_v_dim_emp a set emp_hf_id=(select emp_id from (select * from soure_v_dim_emp) b where a.emp_hf_key=b.emp_key);
  update soure_v_dim_emp a set emp_hf_id=(select emp_id from (select * from v_dim_emp) b where a.emp_hf_key=b.emp_key) where emp_hf_id is null;

  update soure_v_dim_emp a set user_name=(select emp_key from (select * from soure_v_dim_emp) b where a.emp_key=b.emp_key);
-- 分公司
  update soure_v_dim_emp a set branch_company_id=(select organization_parent_id from (select * from soure_v_dim_emp) b where a.emp_key=b.emp_key);

  update soure_v_dim_emp set ability_lv_name='1级' where ability_lv_name='级';
  commit;
end;

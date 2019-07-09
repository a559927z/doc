
-- 员工能力记录
drop procedure if exists pro_fetch_ability_change;
CREATE  PROCEDURE `pro_fetch_ability_change`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT NOW();
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工能力记录】';

  
  INSERT INTO ability_change(
  emp_id,user_name_ch,customer_id,full_path,organization_parent_id,organization_id,sequence_id,sequence_sub_id,ability_id,age,sex,degree_id,ability_number_id,update_time,year_months)
  SELECT 
  emp_id,user_name_ch,p_customer_id,full_path,organization_parent_id,organization_id,sequence_id,sequence_sub_id,ability_id,age,sex,degree_id,ability_number_id,NOW(),year_months 
  FROM `mup-source`.source_ability_change t
  ON DUPLICATE KEY
  UPDATE              
	user_name_ch           = t.user_name_ch          ,
	customer_id            = t.customer_id           ,
	full_path              = t.full_path             ,
	organization_parent_id = t.organization_parent_id,
	organization_id        = t.organization_id       ,
	sequence_id            = t.sequence_id           ,
	sequence_sub_id        = t.sequence_sub_id       ,
	ability_id             = t.ability_id            ,
	age                    = t.age                   ,
	sex                    = t.sex                   ,
	degree_id              = t.degree_id             ,
	ability_number_id      = t.ability_number_id     ,
	update_time            = t.update_time  ;         
  TRUNCATE TABLE `mup-source`.source_ability_change;;
end;

-- 人才地图员工信息
drop procedure if exists pro_fetch_map_talent_info;
CREATE  PROCEDURE `pro_fetch_map_talent_info`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【人才地图员工信息】';
  
  
  insert into map_talent_info(
  emp_id,user_name_ch,customer_id,full_path,organization_parent_id,organization_id,sequence_id,sequence_sub_id,ability_id,degree_id,age,sex)
  select 
  emp_id,user_name_ch,p_customer_id,full_path,organization_parent_id,organization_id,sequence_id,sequence_sub_id,ability_id,degree_id,age,sex 
  from `mup-source`.source_map_talent_info a
  on duplicate key 
  update 
    user_name_ch=a.user_name_ch,
    full_path=a.full_path,
	organization_parent_id=a.organization_parent_id,
	organization_id=a.organization_id,
    sequence_id=a.sequence_id,
    sequence_sub_id=a.sequence_sub_id,
    ability_id=a.ability_id,
    degree_id=a.degree_id,
    age=a.age,
    sex=a.sex;
   
  truncate table `mup-source`.source_map_talent_info;
   
end;

-- 人才地图
drop procedure if exists pro_fetch_map_talent;
CREATE PROCEDURE `pro_fetch_map_talent`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【人才地图】';
     
   insert into map_talent(
   map_talent_id,customer_id,emp_id, x_axis_id,x_axis_id_af,y_axis_id,y_axis_id_af,update_time,is_update,year_months,date_time,note)
   SELECT 
   	fn_getId(),a.customer_id,a.emp_id,a.performance_id,null, ability_number_id,null,null,0,a.`year_month`,now(),null 
   from
   	performance_change a,ability_change b 
   where a.emp_id=b.emp_id and  a.`year_month`=b.`year_months` 
   	and a.`year_month`=(select max(`year_month`) from performance_change);

end;

-- 人才地图配置表
drop procedure if exists pro_fetch_dim_z_info;
CREATE  PROCEDURE `pro_fetch_dim_z_info`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT NOW();
	DECLARE p_message VARCHAR(10000) DEFAULT '【人才地图配置表】';
 

  INSERT INTO dim_z_info (
  z_id,z_name,customer_id,show_index) 
  SELECT 
    a.`sys_code_item_id`,a.`sys_code_item_name`,p_customer_id,a.show_index 
  FROM sys_code_item a WHERE a.`code_group_id`='talent_map_zindex'
  ON DUPLICATE KEY 
  UPDATE 
  z_name=a.sys_code_item_name,
  show_index=a.show_index;

END;

-- 人才地图配置表
drop procedure if exists pro_fetch_map_config;
CREATE  PROCEDURE `pro_fetch_map_config`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN 
	DECLARE p_message VARCHAR(10000) DEFAULT '【人才地图配置表】';
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
 
    TRUNCATE table map_config;
  	insert into map_config values(fn_getId(),	customerId,	'5',	'6',	'#E1F5E2',	'2017-04-21',	'庆幸');
  	insert into map_config values(fn_getId(),	customerId,	'5',	'7',	'#B3E6B2',	'2017-04-21',	'舒畅');
  	insert into map_config values(fn_getId(),	customerId,	'1',	'1',	'#FECAC2',	'2017-04-21',	'高兴');
  	insert into map_config values(fn_getId(),	customerId,	'1',	'2',	'#FDDFD2',	'2017-04-21',	'好受');
  	insert into map_config values(fn_getId(),	customerId,	'2',	'1',	'#FECAC2',	'2017-04-21',	'高兴');
  	insert into map_config values(fn_getId(),	customerId,	'2',	'2',	'#FDDFD2',	'2017-04-21',	'好受');
  	insert into map_config values(fn_getId(),	customerId,	'2',	'3',	'#FFFF92',	'2017-04-21',	'开心');
  	insert into map_config values(fn_getId(),	customerId,	'2',	'4',	'#FFFFB2',	'2017-04-21',	'快活');
  	insert into map_config values(fn_getId(),	customerId,	'3',	'2',	'#FDDFD2',	'2017-04-21',	'好受');
  	insert into map_config values(fn_getId(),	customerId,	'3',	'3',	'#FFFF92',	'2017-04-21',	'开心');
  	insert into map_config values(fn_getId(),	customerId,	'3',	'4',	'#FFFFB2',	'2017-04-21',	'快活');
  	insert into map_config values(fn_getId(),	customerId,	'3',	'5',	'#FFFFD2',	'2017-04-21',	'快乐');
  	insert into map_config values(fn_getId(),	customerId,	'4',	'3',	'#FFFF92',	'2017-04-21',	'开心');
  	insert into map_config values(fn_getId(),	customerId,	'4',	'4',	'#FFFFB2',	'2017-04-21',	'快活');
  	insert into map_config values(fn_getId(),	customerId,	'4',	'5',	'#FFFFD2',	'2017-04-21',	'快乐');
  	insert into map_config values(fn_getId(),	customerId,	'4',	'6',	'#E1F5E2',	'2017-04-21',	'庆幸');
    insert into map_config values(fn_getId(),	customerId,	'4',	'7',	'#B3E6B2',	'2017-04-21',	'舒畅');

END;



-- 历史表-机构异动表
drop procedure if exists pro_fetch_organization_change;
CREATE  PROCEDURE `pro_fetch_organization_change`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
  DECLARE _done,_organization_type_level,p_error int default 0;
  DECLARE _times int default 1;
  DECLARE _emp_id,_organization_id_2,_organization_id_4,_full_path_2,_full_path_4 varchar(32);
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【历史表-机构异动表】';
 
 -- tmp_job_change利用临时表去处理相对应的调入调出记录。
  DECLARE cur cursor for select distinct emp_id  from `mup-source`.source_job_change where change_type in (2,4);
  DECLARE exit handler for not found set _done = 1;
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET p_error = 1;
   
     set @length_2='' ,@length_4='',@_full_path_2='',@_full_path_4='',@_organization_id_2='',@_organization_id_4='',@length_total='';
   
    open cur;

      while _done = 0 do 

        fetch cur into _emp_id;

        select max(organization_id)  into _organization_id_2 from tmp_job_change where emp_id=_emp_id and change_type=2;
        select max(organization_id)  into _organization_id_4 from tmp_job_change where emp_id=_emp_id and change_type=4;         
        select max(full_path) into _full_path_2 from dim_organization a where a.organization_id=_organization_id_2;
        select max(full_path) into _full_path_4 from dim_organization a where a.organization_id=_organization_id_4;
        
        set  @length_2 =(select length(_full_path_2) - length(replace(_full_path_2,'_',''))+1);
        set  @length_4 =(select length(_full_path_4) - length(replace(_full_path_4,'_',''))+1);
         
        if @length_4 is null  then 
 
           set @length_4 = 0;
        
        end if;

        if @length_2 is null  then 
 
           set @length_2 = 0;
        
        end if;
         
        if  @length_2 > @length_4 then 
            set @length_total =   @length_2 ;
        else 
            set  @length_total =  @length_4 ;
        end if;
        
        while _times <= @length_total do 
            
            if _times> @length_2 then 
              set @_full_path_2 = 'A';
            else 
              set @_full_path_2=(select substring_index(substring_index(_full_path_2,'_',_times),'_',-1));
            end if;
      
            if _times>@length_4 then 
              set @_full_path_4 = 'B' ;
            else 
              set @_full_path_4=(select substring_index(substring_index(_full_path_4,'_',_times),'_',-1)); 
            end if;   
                       

             if @_full_path_2 != @_full_path_4 then 
              
              select max(organization_id),max(organization_name) into @_organization_id_2,@_organization_name_2 from dim_organization where organization_key= @_full_path_2;
              select max(organization_id),max(organization_name) into @_organization_id_4,@_organization_name_4 from dim_organization where organization_key= @_full_path_4;
             
              insert into organization_change_111 select fn_getId(),p_customer_id,_emp_id,user_name_ch,emp_key,change_date,4,change_type_id,@_organization_id_2,
              @_organization_name_2,@_organization_id_4,@_organization_name_4,position_id,position_name,sequence_id,sequence_name,ability_id,ability_name,now(),null,cast(date_format(change_date,'%Y%m') as int )
              from `mup-source`.source_job_change a where a.emp_id=_emp_id and change_type=4; 
              
              insert into organization_change_111 select fn_getId(),p_customer_id,_emp_id,user_name_ch,emp_key,change_date,2,change_type_id,@_organization_id_2,
              @_organization_name_2,@_organization_id_4,@_organization_name_4,position_id,position_name,sequence_id,sequence_name,ability_id,ability_name,now(),null,cast(date_format(change_date,'%Y%m') as int )
              from `mup-source`.source_job_change a where a.emp_id=_emp_id and change_type=2; 
             
             end if;
             set _times = _times +1 ;
         end while;
     end while;
end;

-- 工作异动表
drop procedure if exists pro_fetch_job_change;
CREATE  PROCEDURE `pro_fetch_job_change`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【历史表-工作异动表】';
  
   
   insert into job_change 
   select emp_job_change_id,p_customer_id,emp_id,user_name_ch,emp_key,change_date,change_type,change_type_id,organization_id,organization_name,position_id,position_name,
   sequence_id,sequence_name,sequence_sub_id,sequence_sub_name,ability_id,ability_name,job_title_id,job_title_name,ability_lv_id,ability_lv_name,rank_name,rank_name_ed,position_id_ed,position_name_ed,
   note,`year_month`,null,null
   from `mup-source`.source_job_change a on duplicate key update 
   user_name_ch=a.user_name_ch,
   change_date=a.change_date, 
   change_type=a.change_type,
   change_type_id=a.change_type_id,
   organization_id=a.organization_id,
   organization_name=a.organization_name,
   position_id=a.position_id,
   position_name=a.position_name,
   sequence_id=a.sequence_id,
   sequence_name=a.sequence_name,
   sequence_sub_id=a.sequence_sub_id,
   sequence_sub_name=a.sequence_sub_name,
   ability_id=a.ability_id,
   ability_name=a.ability_name,
   job_title_id=a.job_title_id,
   job_title_name=a.job_title_name,
   ability_lv_id=a.ability_lv_id,
   ability_lv_name=a.ability_lv_name,
   rank_name=a.rank_name,
   rank_name_ed=a.rank_name_ed,
   position_id_ed=a.position_id_ed,
   position_name_ed=a.position_name_ed,
   note=a.note,
   `year_month`=a.`year_month`;

  truncate table `mup-source`.source_job_change;
end;
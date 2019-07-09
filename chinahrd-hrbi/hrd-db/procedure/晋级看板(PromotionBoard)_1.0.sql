

-- 员工占比统计
drop procedure if exists pro_fetch_promotion_total;
CREATE PROCEDURE `pro_fetch_promotion_total`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工占比统计】';
   
	insert into promotion_total(
		promotion_total_id,scheme_id,emp_id,customer_id,rank_name,rank_name_af,organization_id,`status`,condition_prop,total_date)
	select 
		promotion_total_id,scheme_id,emp_id,customerId,rank_name,rank_name_af,organization_id,`status`,null,total_date 
	from `mup-source`.source_promotion_total a
	ON DUPLICATE KEY UPDATE 
		scheme_id = a.scheme_id,
		emp_id = a.emp_id,
		rank_name = a.rank_name,
		rank_name_af = a.rank_name_af,
		organization_id = a.organization_id,
		`status` = a.`status`
	;
	-- 更新:员工占比统计占比-条件符合占比
	-- call pro_update_promotion_total(p_customer_id,p_user_id);

end;

-- 员工晋级结果
drop procedure if exists pro_fetch_promotion_results;
CREATE  PROCEDURE `pro_fetch_promotion_results`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工晋级结果】';
     
     insert into promotion_results(
     	promotion_results_id,emp_id,full_path,oranization_parent_id,sequence_id,organization_id,is_key_talent,performance_id,
     	customer_id,rank_name,rank_name_af,`status`,status_date,show_index,show_index_af,entry_date)
     select 
     	fn_getId(),emp_id,full_path,oranization_parent_id,a.sequence_id,organization_id,is_key_talent,performance_id,p_customer_id,a.rank_name,
     	rank_name_af,`status`,status_date,b.show_index,c.show_index,entry_date 
     from `mup-source`.source_promotion_results a,job_relation b,job_relation c
     where 
     	a.rank_name=b.rank_name and a.rank_name_af=c.rank_name and b.show_index is not null and c.show_index is not null ;
end;

-- 晋级要素方案
drop procedure if exists pro_fetch_promotion_element_scheme;
CREATE  PROCEDURE `pro_fetch_promotion_element_scheme`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【晋级要素方案】';
  
     INSERT INTO promotion_element_scheme(
     	scheme_id,customer_id,scheme_name,company_age,curt_name_per,curt_name_eval,certificate_id,certificate_type_id,create_user_id,modify_user_id,create_time,modify_time,start_date,invalid_date)
     SELECT 
     	scheme_id,customerId,scheme_name,company_age,curt_name_per,curt_name_eval,certificate_id,a.certificate_type_id,create_user_id,modify_user_id,create_time,modify_time,start_date,invalid_date 
     FROM `mup-source`.source_promotion_element_scheme a
     ON DUPLICATE KEY UPDATE 
		scheme_name=a.scheme_name,
		company_age=a.company_age,
		curt_name_per=a.curt_name_per,
		curt_name_eval=a.curt_name_eval,
		certificate_id=a.certificate_id,
		certificate_type_id=a.certificate_type_id,
		create_user_id=a.create_user_id,
		modify_user_id=a.modify_user_id,
		create_time=a.create_time,
		modify_time=a.modify_time,
		start_date=a.start_date,
		invalid_date=a.invalid_date;
end;

-- 职级晋升方案
drop procedure if exists pro_fetch_promotion_plan;
CREATE PROCEDURE `pro_fetch_promotion_plan`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【职级晋升方案】';
   
	
     INSERT INTO promotion_plan(
     	promotion_plan_id,customer_id,rank_name_af,scheme_id,create_user_id,modify_user_id,create_time,modify_time)
     SELECT 
     	promotion_plan_id,customerId,rank_name_af,scheme_id,create_user_id,modify_user_id,create_time,modify_time 
     FROM `mup-source`.source_promotion_plan a
     ON DUPLICATE KEY UPDATE 
	     rank_name_af=a.rank_name_af,
	     scheme_id=a.scheme_id,
	     create_user_id=a.create_user_id,
	     modify_user_id=a.modify_user_id,
	     create_time=a.create_time,
	     modify_time=a.modify_time
		;
end;


-- 员工占比分析  **********需优化效率
drop procedure if exists pro_fetch_promotion_analysis;
create procedure `pro_fetch_promotion_analysis`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE analysisDate TIMESTAMP DEFAULT CURDATE();	-- 当天分析日期
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工占比分析】';

	DECLARE _done INT DEFAULT 0;
	DECLARE _company_age,_curt_name_eval INT(4);
	DECLARE _scheme_id,_emp_id,_certificate_id,_certificate_type_id,_curt_name_per VARCHAR(32);
	
	-- 员工占比统计-晋级后不为空的，因为可能已经是顶级了
	DECLARE cur CURSOR for SELECT scheme_id as _scheme_id, emp_id as _emp_id FROM promotion_total WHERE rank_name_af IS NOT NULL;
	DECLARE exit handler for NOT found SET _done = 1;
	
	SELECT count(1) FROM `promotion_total` WHERE rank_name_af IS NOT NULL;
	
	OPEN cur;
    WHILE _done = 0 DO 
		FETCH CUR INTO _scheme_id,_emp_id;
		IF _scheme_id IS NOT NULL THEN
			-- 晋级要素方案 -每员工的晋级方案对应的晋级要素要求  
			SELECT company_age, curt_name_per, curt_name_eval, certificate_id, certificate_type_id 
			INTO _company_age, _curt_name_per, _curt_name_eval, _certificate_id, _certificate_type_id 
			FROM promotion_element_scheme WHERE scheme_id=_scheme_id LIMIT 1;
	  
			-- 先删除当前员工最新一次分析的所有记录
			DELETE FROM promotion_analysis WHERE emp_id=_emp_id AND analysis_date=analysisDate;
			
			-- 司龄分析
			IF _company_age IS NOT NULL THEN 
				CALL pro_fetch_promotion_analysis_company_age(_emp_id,_company_age, customerId);
			END IF;
	  
			-- 能力评价
			IF _curt_name_eval is not null then 
				CALL pro_fetch_promotion_analysis_curt_name_eval(_emp_id, _curt_name_eval, customerId);
			END IF;
	
			-- 资格证书
			IF _certificate_id IS NOT NULL THEN 
				CALL pro_fetch_promotion_analysis_certificate(_emp_id,_certificate_id,customerId);
		  	END IF;
	
			-- 资格证书类型
			IF _certificate_type_id IS NOT NULL THEN 
				CALL pro_fetch_promotion_analysis_certificate_type(_emp_id,_certificate_type_id,customerId);
			END IF;
	
			-- 绩效
			IF _curt_name_per IS NOT NULL THEN 
	        	CALL pro_fetch_promotion_analysis_curt_name_per(_emp_id,_curt_name_per,customerId);
			END IF;
		END IF;
    END WHILE;
END;

-- 司龄分析
drop procedure if exists `pro_fetch_promotion_analysis_company_age`;
CREATE  PROCEDURE `pro_fetch_promotion_analysis_company_age`(analysis_emp_id varchar(32), analysis_company_age int, analysis_customer_id varchar(32))
BEGIN 
	DECLARE companyAge, caCol int;
 
	SELECT company_age , count(*)  INTO companyAge, caCol
	FROM v_dim_emp WHERE emp_id=analysis_emp_id;

	IF caCol != 0 THEN 
		
	   IF analysis_company_age <= companyAge THEN 
	     INSERT INTO promotion_analysis VALUES(fn_getId(),analysis_emp_id, analysis_customer_id, analysis_company_age, companyAge,1, now(),'工作年限');
	   ELSE 
	     INSERT INTO promotion_analysis VALUES(fn_getId(), analysis_emp_id, analysis_customer_id, analysis_company_age, companyAge,0, now(),'工作年限');
	   END IF;
  END IF;
END;


-- 能力评价
drop procedure if exists pro_fetch_promotion_analysis_curt_name_eval;
CREATE PROCEDURE `pro_fetch_promotion_analysis_curt_name_eval`(analysis_emp_id VARCHAR(32), analysis_curt_name_eval INT, analysis_customer_id VARCHAR(32))
BEGIN 
 DECLARE curtName, cnCol int;
 DECLARE examinationResult, analysisEResult varchar(10);
 
	-- 最后一次能力评价
	SELECT curt_name,count(*),examination_result INTO curtName,cnCol,examinationResult FROM emp_pq_eval_relation 
	WHERE emp_id=analysis_emp_id order by `date` desc limit 1;
	
	-- 字典表里'排序'来分区方案里的要求，得出中文名称
	SELECT sys_code_item_name INTO analysisEResult FROM sys_code_item WHERE show_index=analysis_curt_name_eval AND code_group_id='pqeval';
 
	IF cnCol != 0 THEN
	-- 数字越高，等级超高
		IF curtName <= analysis_curt_name_eval then 
			INSERT INTO promotion_analysis VALUES(fn_getId(), analysis_emp_id, analysis_customer_id, analysisEResult, examinationResult, 1,now(),'能力评价');
		ELSE
			INSERT INTO promotion_analysis VALUES(fn_getId(), analysis_emp_id, analysis_customer_id, analysisEResult, examinationResult, 0,now(),'能力评价');
		END IF;
	END IF;
END;


-- 资格证书
drop procedure if exists pro_fetch_promotion_analysis_certificate;
CREATE PROCEDURE `pro_fetch_promotion_analysis_certificate`(analysis_emp_id VARCHAR(32), analysis_certificate_id VARCHAR(32), analysis_customer_id VARCHAR(32))
BEGIN 
	DECLARE _total int(1);
	DECLARE _certificate_name varchar(50);
	
	select count(*) into _total from emp_certificate_info where emp_id=analysis_emp_id and certificate_id=analysis_certificate_id;
	-- 获取证书名
	select certificate_name into _certificate_name from dim_certificate_info where certificate_info_id=analysis_certificate_id;
	
	IF _total != 0 THEN 
		insert into promotion_analysis values(fn_getId(),analysis_emp_id,analysis_customer_id,_certificate_name,'有证书',1,now(),'资格证书');
	ELSE 
		insert into promotion_analysis values(fn_getId(),analysis_emp_id,analysis_customer_id,_certificate_name,'无证书',0,now(),'资格证书');
	END IF;
END;


-- 资格证书类型
drop procedure if exists pro_fetch_promotion_analysis_certificate_type;
CREATE PROCEDURE `pro_fetch_promotion_analysis_certificate_type`(analysis_emp_id char(32),analysis_certificate_type_id varchar(32),analysis_customer_id char(32))
begin 
	DECLARE ctnTotal int;
	DECLARE certificateTypeName varchar(50);
	select count(*) into ctnTotal from emp_certificate_info a,dim_certificate_info b where a.certificate_id=b.certificate_info_id and b.certificate_type_id=analysis_certificate_type_id;
	-- 获取证书类型名
	select sys_code_item_name into certificateTypeName from sys_code_item where code_group_id='certificate_type' and sys_code_item_id=analysis_certificate_type_id;
	IF ctnTotal != 0 THEN 
		insert into promotion_analysis values(fn_getId(),analysis_emp_id,analysis_customer_id,certificateTypeName,'有此类证书',1,now(),'资格证书类别');
	ELSE
		insert into promotion_analysis values(fn_getId(),analysis_emp_id,analysis_customer_id,certificateTypeName,'无此类证书',0,now(),'资格证书类别');
	END IF;
END;

-- 绩效等级
drop procedure if exists pro_fetch_promotion_analysis_curt_name_per;
CREATE PROCEDURE `pro_fetch_promotion_analysis_curt_name_per`(analysis_emp_id varchar(32),analysis_curt_name_per varchar(20),analysis_customer_id varchar(32))
begin 
	
	DECLARE acnp int default replace(analysis_curt_name_per,'_','');
	-- 连续(周期)
	DECLARE _times int default (select substr(acnp,1,1));
	-- 绩效要求
	DECLARE _curt_name_per int default (select substr(acnp,2,1));
	-- 连续达标次数
	DECLARE	_least_times int default (select substr(acnp,3,1));
	
	-- 下标， 满足方案里要求绩效级别总数， 
	DECLARE _i,_total,_curt_name int default 0;
	DECLARE _performance_times int default 0;
	DECLARE _performance_name varchar(10);
	
	-- 获取当前员工所有绩效
	SELECT count(*) into _performance_times from performance_change where emp_id=analysis_emp_id;
	-- 最后一次绩效名称
	select performance_name into _performance_name from performance_change where emp_id=analysis_emp_id order by `year_month` desc limit 1; 
	
	-- 1.绩效次数大于方案里要求次数 
	IF _performance_times >= _times THEN
		-- 2.循环累计曾经满足方案里要求过绩效次数 _total
		WHILE _i < _times DO 
			select b.curt_name into _curt_name 
			from performance_change a, dim_performance b 
			where a.performance_id=b.performance_id and emp_id=analysis_emp_id order by `year_month` desc limit _i,1;
			
			if _curt_name >= _curt_name_per then 
				set _total = _total + 1;
			end if;
			set _i = _i + 1;
		END WHILE;
		-- 3.满足方案里的绩效
		IF _total >= _least_times THEN
			insert into promotion_analysis values(fn_getId(),analysis_emp_id,analysis_customer_id,analysis_curt_name_per,_performance_name,1,now(),'绩效等级');
		ELSE 
			insert into promotion_analysis values(fn_getId(),analysis_emp_id,analysis_customer_id,analysis_curt_name_per,_performance_name,0,now(),'绩效等级'); 
		END IF;
	ELSE 
	     insert into promotion_analysis values(fn_getId(),analysis_emp_id,analysis_customer_id,analysis_curt_name_per,_performance_name,0,now(),'绩效等级');
	END IF; 
	
	
	-- 1.员工小于要求的绩效总数，但也绩效次满足达标次数，也要计算他累计是否得过方案要求绩效总数
	IF _performance_times< _times and _performance_times >= _least_times THEN
		-- 2.循环累计曾经满足方案里要求过绩效次数 _total			
		WHILE _i <  _performance_times DO 
			select b.curt_name into _curt_name from performance_change a,dim_performance b where a.performance_id=b.performance_id and emp_id=analysis_emp_id order by `year_month` desc limit _i,1;
			if _curt_name >= _curt_name_per then 
				set _total = _total + 1;
			end if;
			set _i = _i + 1;
		END WHILE;
		-- 3.满足方案里的绩效
		IF _total >= _least_times THEN
			insert into promotion_analysis values(fn_getId(),analysis_emp_id,analysis_customer_id,analysis_curt_name_per,_performance_name,1,now(),'绩效等级');
		else 
			insert into promotion_analysis values(fn_getId(),analysis_emp_id,analysis_customer_id,analysis_curt_name_per,_performance_name,0,now(),'绩效等级'); 
		end if;
	ELSE 
	     insert into promotion_analysis values(fn_getId(),analysis_emp_id,analysis_customer_id,analysis_curt_name_per,_performance_name,0,now(),'绩效等级');
	END IF; 
	
END;

-- 更新:员工占比统计占比-条件符合占比
drop procedure if exists `pro_update_promotion_total`;
CREATE PROCEDURE `pro_update_promotion_total`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工占比统计占比更新】';
   
	update promotion_total b 
	set condition_prop=(
		select zb from 
			(	-- 计算百分比 =符合总数 / 参与项目
				select emp_id, FORMAT(fh/tj,2)*100 zb 
				from 
					( -- 符合累计总数 倒序
						select emp_id,fh,tj from 
						( -- 伪列符合累计总数(fh), 参与项目累计总数(tj)
							select 
								emp_id,
								if(@a=emp_id, @b:=@b+is_accord, @b:=is_accord) fh, -- 当Condition为TRUE时，返回B列累计符合总数；当Condition为FALSE时，返回1。
								if(@a=emp_id, @c:=@c+1, @c:=1) tj, -- 当Condition为TRUE时，返回C列+1；当Condition为FALSE时，返回1。
								@a:=emp_id 
							from promotion_analysis a, (select @a:=null,@b:=null,@c:=null) b 
							order by emp_id
						) a 
						order by emp_id,fh desc
					) a 
				group by emp_id
			) a 
		where a.emp_id=b.emp_id
	)
	where condition_prop is null;
END;

-- 员工岗位能力评价
drop procedure if exists `pro_fetch_emp_pq_eval_relation`;
CREATE PROCEDURE `pro_fetch_emp_pq_eval_relation`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工岗位能力评价】';
   
	INSERT INTO emp_pq_eval_relation (
     	emp_id,customer_id,examination_result_id,examination_result,`date`,curt_name) 
	SELECT 
		emp_id,customerId,examination_result_id,examination_result,date,curt_name 
	FROM `mup-source`.source_emp_pq_eval_relation a
	ON DUPLICATE KEY UPDATE 
		examination_result_id = a.examination_result_id,
		examination_result = a.examination_result,
		curt_name = a.curt_name
     ;
end;


-- 流失率月度总人数
drop procedure if exists pro_fetch_run_off_total;
CREATE PROCEDURE `pro_fetch_run_off_total`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT NOW();
	DECLARE _done,total_bjg_zd,total_bjg_bd,total_zs_zd,total_zs_bd INT DEFAULT 0;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-流失率月度总人数】';
 
	DECLARE _doen INT DEFAULT 0;
	DECLARE _organization_id,_full_path VARCHAR(100);
	DECLARE cur CURSOR FOR SELECT organization_id,full_path FROM dim_organization;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET _done = 1;
  
	OPEN cur;
	FETCH cur INTO _organization_id,_full_path;
	WHILE _doen = 0 DO 

   		-- 机构本月范围-主动离职总人数
		SELECT COUNT(*) INTO total_bjg_zd 
		FROM run_off_record a, v_dim_emp b, dim_run_off c
		WHERE a.run_off_id=c.run_off_id AND c.type=1 AND a.emp_id=b.emp_id 
			AND a.run_off_date>= fn_getYMDOne_li(NOW()) AND a.run_off_date<= fn_getYMDLast_li(NOW()) 
			AND b.organization_id=_organization_id;

		-- 机构本月范围-被动离职总人数
		SELECT COUNT(*) INTO total_bjg_bd 
		FROM run_off_record a,v_dim_emp b,dim_run_off c 
		WHERE a.run_off_id=c.run_off_id AND c.type=2 AND a.emp_id=b.emp_id 
			AND a.run_off_date>= fn_getYMDOne_li(NOW()) AND a.run_off_date<= fn_getYMDLast_li(NOW())
			AND b.organization_id=_organization_id;
         
 		-- 本机构的子孙本月范围-主动离职总人数
     	SELECT COUNT(*) INTO total_zs_zd  
     	FROM run_off_record a,v_dim_emp b, dim_run_off c 
     	WHERE a.run_off_id=c.run_off_id AND c.type=1 AND a.emp_id=b.emp_id 
     		AND a.run_off_date>= fn_getYMDOne_li(NOW()) AND a.run_off_date<= fn_getYMDLast_li(NOW())
     		AND organization_id IN (SELECT	t1.organization_id FROM	dim_organization t1 WHERE LOCATE ((SELECT t.full_path FROM dim_organization t WHERE t.organization_id = _organization_id),t1.full_path));
     
     	-- 本机构的子孙本月范围-被动离职总人数
     	SELECT COUNT(*) INTO total_zs_bd  
     	FROM run_off_record a,v_dim_emp b, dim_run_off c 
     	WHERE a.run_off_id=c.run_off_id AND c.type=2 AND a.emp_id=b.emp_id 
     		AND a.run_off_date>= fn_getYMDOne_li(NOW()) AND a.run_off_date<= fn_getYMDLast_li(NOW())
     		AND organization_id IN (SELECT	t1.organization_id FROM	dim_organization t1 WHERE LOCATE ((SELECT t.full_path FROM dim_organization t WHERE t.organization_id = _organization_id),t1.full_path));

     	-- 是否本月第一天
	     IF 1 = fn_isYMDOne(now())  THEN 
	       INSERT INTO run_off_total SELECT fn_getId(), p_customer_id,_organization_id,_full_path,total_bjg_zd,total_bjg_bd,total_zs_zd,total_zs_bd, fn_getYM(), null, null, null;
	     ELSE 
	       UPDATE run_off_total SET act_count=total_bjg_zd,un_act_count=total_bjg_bd,act_count_sum=total_zs_zd,un_act_count_sum=total_zs_bd WHERE `year_month`=fn_getYM() AND organization_id=_organization_id;
	     END IF;
	END WHILE;
	CLOSE cur;

END;




-- 流失记录
drop procedure if exists pro_fetch_run_off_record;
CREATE  PROCEDURE `pro_fetch_run_off_record`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT NOW();
	DECLARE p_message VARCHAR(10000) DEFAULT '【维度表-离职风险维】';

		INSERT INTO run_off_record(
			run_off_record_id,
			customer_id,
			emp_id,
			run_off_id,
			where_abouts,
			run_off_date,
			is_warn,
			re_hire
		)
		SELECT
			run_off_record_id,
			customerId,
			emp_id,
			run_off_id,
			where_abouts,
			run_off_date,
			is_warn,
			re_hire
		FROM `mup-source`.source_run_off_record t2
		WHERE t2.customer_id = customerId;
/*
		-- 同时更新员工的离职时间
		UPDATE `mup-source`.source_v_dim_emp t SET t.run_off_date = (SELECT t2.run_off_date FROM `mup-source`.source_run_off_record t2 WHERE t2.emp_key = t.emp_key);
		UPDATE v_dim_emp t SET t.run_off_date = (SELECT t2.run_off_date FROM `mup-source`.source_run_off_record t2 WHERE t2.emp_key = t.emp_key);

		-- 更新是否有预警
		UPDATE run_off_record t2 , (
				SELECT 
					t.emp_id
				FROM
					dimission_risk t
				INNER JOIN v_dim_emp t1 ON t.emp_id = t1.emp_id
				AND t1.customer_id = t.customer_id
				AND t1.run_off_date >= t.risk_date	-- 流失日期 => 评估时间
-- 			AND t1.enabled = 1 AND t1.effect_date <=NOW() AND (t1.expiry_Date IS NULL OR t1.expiry_Date> NOW())
			 ) t 
		SET t2.is_warn = 1
		WHERE t2.emp_id = t.emp_id;
*/
END;


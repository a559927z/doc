#=======================================================pro_init_factfte=======================================================
-- '2015-06-11','demo'
-- 测试时输入上个月时间。（ 这里没有对上个月时间处理，上个月时间处理在事件里完成。）
-- 涉及的表和作用：
-- 		dim_organization  按机构分组
-- 		dim_emp				按正职和兼职
-- 		emp_overtime	加班时间
-- 		trade_profit	机构的利润
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_init_factfte`;
CREATE PROCEDURE pro_init_factfte(in p_nowTime datetime, in p_customer_key VARCHAR(50))
BEGIN

	-- 定义接收游标数据的变量 
	DECLARE orgId VARCHAR(32);
	DECLARE orgName, orgKey VARCHAR(50);
	DECLARE fullpath text;

	-- 定义接收临时表数据的变量 
	DECLARE fulltimeSum, passtimeSum, overtimeSum, fteCalc DOUBLE(6,2) DEFAULT 0.0;
	DECLARE	gainAmount DECIMAL(10,2);
	DECLARE benefitValue DOUBLE(6,2);


	-- 定义获得输入参数的最后一天变量 
	DECLARE lastDay datetime DEFAULT LAST_DAY(p_nowTime);
	DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(p_nowTime, Interval 0 minute),'%Y%m');
	DECLARE customerId VARCHAR(32) DEFAULT (SELECT dc.customer_id FROM dim_customer dc WHERE dc.customer_key=p_customer_key);
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【人均效益-业务表】：数据刷新完成';

  -- 遍历数据结束标志
  DECLARE done INT DEFAULT FALSE;
	-- 游标
	DECLARE cur CURSOR FOR
			SELECT 
				org.organization_id as orgId, 
				org.organization_name as orgName,
				org.organization_key as orgKey,
				org.full_path as fullpath
			FROM v_dim_organization org 
			WHERE org.is_single = 1 -- 独立核算
				-- AND org.effect_date <= lastDay AND (org.expiry_date is NULL or org.expiry_date > lastDay) AND org.enabled = 1
			;


	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	-- 将结束标志绑定到游标
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


	START TRANSACTION;
	-- 删除所有有关的月份数据，准备重新写入。
	delete from fact_fte where `YEAR_MONTH` = yearMonth and customer_id = customerId;

	-- SELECT lastDay, yearMonth;

 -- 打开游标
  OPEN cur;
 -- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据
    FETCH cur INTO orgId, orgName, orgKey, fullpath;
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
-- 		START TRANSACTION;

			-- 当前有效范围的机构
			DROP TABLE IF EXISTS mup.tmp_effectOrganId;   
			CREATE TABLE tmp_effectOrganId (
				SELECT o.organization_id FROM v_dim_organization o
				where full_path LIKE CONCAT('%',orgKey,'%')
					-- and o.effect_date <= lastDay and (o.expiry_date is NULL or o.expiry_date > lastDay) and o.enabled = 1
					-- and o.is_single = 1
			);

    -- 正职人员*1 + 兼职人员*0.5 + 共加班时间 /8
		select 
			sum(tt.fulltimeSum) as fulltimeSum, sum(tt.passtimeSum) as passtimeSum, sum(tt.overtimeSum) as overtimeSum,
			(sum(tt.fulltimeSum) + sum(tt.passtimeSum) + sum(tt.overtimeSum)) as fteCalc,
			tp.gain_amount as gainAmount
		into fulltimeSum, passtimeSum, overtimeSum, fteCalc, gainAmount  -- 插入定义变量
		from(
			SELECT IFNULL(sum(t.fulltimeSum),0.0) as fulltimeSum, 0.0 as passtimeSum, 0.0 as overtimeSum, organizationId	-- 包装结构
			FROM (
				-- 正职人员
				SELECT
					count(t1.emp_id) as fulltimeSum, org2.organization_id as organizationId
				FROM
					v_dim_emp t1
				INNER JOIN tmp_effectOrganId as org2 on org2.organization_id = t1.organization_id
				where t1.passtime_or_fulltime = 'f'
					and (t1.run_off_date is NULL or t1.run_off_date > lastDay)		-- 离职时间
				GROUP BY
					org2.organization_id
			) t
			union ALL
			SELECT 0.0 as fulltimeSum, IFNULL(sum(t2.passtimeSum), 0.0) * 0.5 as passtimeSum, 0.0 as overtimeSum, organizationId	-- 包装结构
			FROM (
				-- 兼职人员
				SELECT
					count(t1.emp_id) as passtimeSum, org2.organization_id as organizationId
				FROM
					v_dim_emp t1
				INNER JOIN tmp_effectOrganId as org2 on org2.organization_id = t1.organization_id
				where t1.passtime_or_fulltime = 'p'
					and (t1.run_off_date is NULL or t1.run_off_date > lastDay)		-- 离职时间
				GROUP BY
					org2.organization_id
			) t2
			union ALL
			SELECT 0.0 as fulltimeSum, 0.0 as passtimeSum, t3.overtimeSum as overtimeSum, organizationId	-- 包装结构
			FROM (
				-- 共加班时间 /8
				SELECT
					IFNULL(sum(eo.hour_count),0.0) / 8 as overtimeSum, org2.organization_id as organizationId
				FROM
					v_dim_emp t
				INNER JOIN tmp_effectOrganId as org2
					on org2.organization_id = t.organization_id
				inner JOIN emp_overtime eo on eo.emp_id = t.emp_id
				WHERE eo.`year_month` = yearMonth
				GROUP BY
					org2.organization_id
			) t3
		) tt
		left JOIN trade_profit tp on tp.organization_id = orgId and tp.`year_month` = yearMonth
		;

	INSERT into fact_fte(fte_id, customer_id, organization_id, organization_name, 
											fulltime_sum, passtime_sum, overtime_sum, fte_value,
											gain_amount, benefit_value, `year_month`)
	VALUES(replace(UUID(),'-',''), customerId, orgId, orgName, fulltimeSum, passtimeSum, overtimeSum, fteCalc,
				gainAmount, round(gainAmount/fteCalc,4), yearMonth) ;


  END LOOP;
  -- 关闭游标
  CLOSE cur;
	
	-- 变化幅度
	CALL pro_update_ffRange(p_nowTime, customerId);



		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【人均效益-业务表】：数据刷新失败。操作用户：", 'system-evn-execut');
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'factfte', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'factfte', p_message, startTime, now(), 'sucess' );
		END IF;



END;
-- DELIMITER ;
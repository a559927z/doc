#=======================================================pro_fetch_factfte=======================================================
-- '2015-06-11','demo'
-- 测试时输入上个月时间。（ 这里没有对上个月时间处理，上个月时间处理在事件里完成。）
-- 涉及的表和作用：
-- 		dim_organization  按机构分组
-- 		dim_emp				按正职和兼职
-- 		emp_overtime	加班时间
-- 		trade_profit	机构的利润
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_factfte`;
CREATE PROCEDURE pro_fetch_factfte(in p_customer_id VARCHAR(50), in p_user_id VARCHAR(32),in p_nowTime datetime)
BEGIN

	-- 定义接收游标数据的变量 
	DECLARE orgId VARCHAR(32);
	DECLARE orgName, orgKey VARCHAR(50);
	DECLARE fullpath text;

	-- 定义接收临时表数据的变量 
	DECLARE fulltimeSum, passtimeSum, overtimeSum, fteCalc DOUBLE(6,2) DEFAULT 0.0;
	DECLARE	gainAmount DECIMAL(10,2);
	DECLARE benefitValue, targetValue DOUBLE(6,2);


	-- 定义获得输入参数的最后一天变量 
	DECLARE lastDay datetime DEFAULT LAST_DAY(p_nowTime);
	DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(p_nowTime, Interval 0 minute),'%Y%m');
-- 	DECLARE customerId VARCHAR(32) DEFAULT (SELECT dc.customer_id FROM dim_customer dc WHERE dc.customer_key=p_customer_id);
 	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;

	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime DATETIME DEFAULT now();

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
			FROM dim_organization org 
			WHERE org.is_single = 1 -- 独立核算
			;


	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	-- 将结束标志绑定到游标
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


	START TRANSACTION;
	-- 删除所有有关的月份数据，准备重新写入。
	delete from fact_fte where `YEAR_MONTH` = yearMonth and customer_id = customerId;


	-- 抽营业利润
	REPLACE INTO fact_fte(
		fte_id,
		customer_id,
		organization_id,
		organization_name,
		fulltime_sum,
		passtime_sum,
		overtime_sum,
		fte_value,
		`year_month`,
		gain_amount,
		sales_amount,
-- 		expend_amount,
		target_value,
		benefit_value,
		range_per
	)
	SELECT 
		id,
		customerId,
		t1.organization_id, t1.organization_name,
		0, 0, 0, 0,		-- fulltime_sum, passtime_sum, overtime_sum, fte_value
		`year_month`,
		gain_amount,
		sales_amount,
-- 		expend_amount,
		target_value,
		0, 0		-- benefit_value, range_per
	FROM soure_trade_profit t
	INNER JOIN dim_organization t1 on t.organization_key = t1.organization_key AND t.customer_id = t1.customer_id
	WHERE `year_month` = yearMonth AND t.customer_id = customerId
	;


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


			-- 当前机构下所有子孙机构包括自己
			DROP TABLE IF EXISTS mup.tmp_effectOrganId;   
			CREATE TABLE tmp_effectOrganId (
					SELECT t1.organization_id FROM dim_organization t1
					WHERE locate( orgKey, t1.full_path ) AND t1.customer_id =  customerId
			);

					
			-- 正职人员 * 1
-- ----------------------------------------------------------- 过时写法---------------
			SET @fulltimeSum = (-- 过时写法
							-- 当前父机构里总 正职人数
							SELECT IFNULL(SUM(t.fSum),0)
							FROM (
							-- 当前父机构里，各个机构子孙的正职人数
								SELECT
									count(t1.emp_id) AS fSum
								FROM v_dim_emp t1
								INNER JOIN tmp_effectOrganId t2 on t2.organization_id = t1.organization_id
								where t1.passtime_or_fulltime = 'f'
									and (t1.run_off_date is NULL or t1.run_off_date > lastDay)		-- 离职时间
								GROUP BY t2.organization_id
						) t
			);

			-- 兼职人员 * 0.5
-- ----------------------------------------------------------- 过时写法---------------
			SET @passtimeSum = (
							SELECT IFNULL(SUM(t.fSum) * 0.5, 0)
							FROM (
								SELECT
									count(t1.emp_id) AS fSum
								FROM v_dim_emp t1
								INNER JOIN tmp_effectOrganId t2 on t2.organization_id = t1.organization_id
								where t1.passtime_or_fulltime = 'p'
									and (t1.run_off_date is NULL or t1.run_off_date > lastDay)		-- 离职时间
								GROUP BY t2.organization_id
						) t
			);


			-- 目前使用硬编码。机构总人数应该是用day = p_nowTime
			SET @fulltimeSum = (SELECT emp_count_sum FROM history_emp_count t where `day` = '2015-12-18' AND type = 2 AND t.organization_id = orgId AND customer_id = customerId);
			SET @passtimeSum = IFNULL((SELECT emp_count_sum FROM history_emp_count t where `day` = '2015-12-18' AND type = 3 AND t.organization_id = orgId AND customer_id = customerId)* 0.5, 0);

			-- 共加班时间 /8
			SET @overtimeSum = (
							SELECT IFNULL(SUM(t.otSum),0)
							FROM (
								-- 当前父机构里，各个机构子孙的加班时数/8
								SELECT IFNULL(sum(t3.hour_count),0.0) / 8 AS otSum
								FROM
									v_dim_emp t
								INNER JOIN tmp_effectOrganId as t2 on t2.organization_id = t.organization_id
								INNER JOIN emp_overtime t3 on t3.emp_id = t.emp_id AND t.customer_id = t3.customer_id
								WHERE t3.`year_month` = yearMonth 
								GROUP BY t2.organization_id
							) t
			);

-- 			SELECT @fulltimeSum , @passtimeSum , @overtimeSum ,@fteValue, yearMonth, orgId , orgName;

		-- 人均效益 = (正职人员*1) + (兼职人员*0.5) + (共加班时间 /8)
		SET @fteValue = @fulltimeSum + @passtimeSum + @overtimeSum;


		UPDATE fact_fte SET 
				fulltime_sum = @fulltimeSum, passtime_sum = @passtimeSum, overtime_sum =@overtimeSum,
				fte_value = @fteValue,
				benefit_value = round(gain_amount/@fteValue,4)
		WHERE organization_id = orgId AND `year_month` = yearMonth
		;


  END LOOP;
  -- 关闭游标
  CLOSE cur;
	

		IF p_error = 1 THEN  
				ROLLBACK;  
		ELSE  
				COMMIT;  
				-- 变化幅度
				CALL pro_update_ffRange(p_nowTime, customerId);
		END IF;


END;
-- DELIMITER ;
-- 
-- CALL pro_fetch_factfte (DATE_ADD('2010-02-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2010-03-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2010-04-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2010-05-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2010-06-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2010-07-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2010-08-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2010-09-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2010-10-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2010-11-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2010-12-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2011-01-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2011-02-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2011-03-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2011-04-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2011-05-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2011-06-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2011-07-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2011-08-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2011-09-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2011-10-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2011-11-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2011-12-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2012-01-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2012-02-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2012-03-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2012-04-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2012-05-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2012-06-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2012-07-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2012-08-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2012-09-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2012-10-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2012-11-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2012-12-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2013-01-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2013-02-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2013-03-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2013-04-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2013-05-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2013-06-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2013-07-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2013-08-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2013-09-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2013-10-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2013-11-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2013-12-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2014-01-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2014-02-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2014-03-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2014-04-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2014-05-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2014-06-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2014-07-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2014-08-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2014-09-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2014-10-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2014-11-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2014-12-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2015-01-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2015-02-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2015-03-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2015-04-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2015-05-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2015-06-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2015-07-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2015-08-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2015-09-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2015-10-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte (DATE_ADD('2015-11-15', INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );
-- CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'system-execute-pro', DATE_ADD('2015-12-15', INTERVAL - 1 MONTH));
-- CALL pro_fetch_factfte (DATE_ADD(CURDATE(), INTERVAL - 1 MONTH), 'demo', 'system-execute-pro' );



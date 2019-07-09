-- 明年目标人均效益(已验证)
drop procedure if exists pro_fetch_target_benefit_value;
CREATE PROCEDURE `pro_fetch_target_benefit_value`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh datetime )
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【人均效益-明年目标人均效益】');
	-- 获取明年（当前时间加一年）
	DECLARE y int(4) DEFAULT fn_getNextY();	

	SET @isNotNull = (SELECT count(1) from `mup-source`.source_target_benefit_value);
	IF @isNotNull >1 THEN
	
		INSERT INTO target_benefit_value (
			target_benefit_value_id,
			customer_id,
			organization_id,
			target_value,
			`year`
		)
		SELECT 
			fn_getId(),
			customerId,
			t.organization_id,
			target_value,
			`year`
		FROM `mup-source`.source_target_benefit_value t
		WHERE t.customer_id = customerId 
			and `year` = y
		;
	END IF;
END;

-- 营业利润(已验证)
drop procedure if exists pro_fetch_trade_profit;
CREATE PROCEDURE `pro_fetch_trade_profit`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh TIMESTAMP(6))
BEGIN
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【人均效益-营业利润】';
	DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(now(), Interval 0 minute),'%Y%m');

	SET @isNotNull = (SELECT count(1) from `mup-source`.source_trade_profit);
	IF @isNotNull >1 THEN
		DELETE FROM trade_profit WHERE `year_month` = yearMonth;
		LB_INSERT:BEGIN
			INSERT INTO trade_profit(
				trade_profit_id, customer_id, organization_id, gain_amount, sales_amount, target_value, expend_amount, `year_month`
			)
			SELECT 
				fn_getId(),
				customerId,
				t.organization_id,
				t.gain_amount,
				t.sales_amount, 
				t.target_value,
				(t.sales_amount - t.gain_amount) expend_amount, -- t.expend_amount,
				t.`year_month`
			FROM `mup-source`.source_trade_profit t 
			WHERE t.customer_id = customerId
			ON DUPLICATE KEY UPDATE
				customer_id = t.customer_id, 
				organization_id = t.organization_id,
				gain_amount = t.gain_amount,
				sales_amount = t.sales_amount,
				target_value = t.target_value,
				expend_amount = (t.sales_amount - t.gain_amount),
				`year_month` = t.`year_month`
			;
		END LB_INSERT;
	END IF;
END;



-- 变化幅度
drop PROCEDURE if EXISTS pro_update_ffRange;
CREATE  PROCEDURE `pro_update_ffRange`(in p_customer_id VARCHAR(50), in p_user_id VARCHAR(32), in p_nowTime datetime)
BEGIN
			DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
			DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
			DECLARE startTime DATETIME DEFAULT now();
			DECLARE p_message VARCHAR(10000) DEFAULT '【人均效益-变化幅度】';
			-- 定义接收游标数据的变量 
			DECLARE orgId VARCHAR(32);

			-- 定义接收临时表数据的变量 
			DECLARE	nowBv, bv, rangeVal DOUBLE(6,2);
			DECLARE p_time datetime DEFAULT DATE_ADD(p_nowTime, Interval -1 month);
			-- 当前传入来的月份
			DECLARE nowYearMonth int(6) DEFAULT date_format(DATE_ADD(p_nowTime, Interval 0 minute),'%Y%m');
			-- 上个月份时间
			DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(p_nowTime, Interval -1 month),'%Y%m');

		-- 	DECLARE customerId VARCHAR(32);

			-- 遍历数据结束标志
			DECLARE done INT DEFAULT FALSE;
			-- 游标
			DECLARE cur CURSOR FOR SELECT ff.organization_id as orgId FROM fact_fte ff;	

			-- 将结束标志绑定到游标
			DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		 -- 打开游标
			OPEN cur;
		 -- 开始循环
			read_loop: LOOP
				-- 提取游标里的数据
				FETCH cur INTO orgId;
				-- 声明结束的时候
				IF done THEN
					LEAVE read_loop;
				END IF;

			-- （本期数－上期数）/上期数*100%
						SELECT 
								max(IF(ff.`year_month` = nowYearMonth, ff.benefit_value, 0)) nowBv,
								max(IF(ff.`year_month` = yearMonth, ff.benefit_value, 0)) bv
						INTO nowBv, bv
						from fact_fte ff
						WHERE ff.organization_id = orgId and (
								ff.`year_month` = nowYearMonth or ff.`year_month` = yearMonth);
					
					set rangeVal = ((nowBv-bv)/bv*100);

					UPDATE fact_fte ff SET ff.range_per = rangeVal
					WHERE ff.organization_id = orgId and ff.`year_month` = nowYearMonth and ff.customer_id = customerId;

			END LOOP;
			-- 关闭游标
			CLOSE cur;
END;



-- '2015-06-11','b5c9410dc7e4422c8e0189c7f8056b5f'
-- 测试时输入上个月时间。（ 这里没有对上个月时间处理，上个月时间处理在event事件里完成。）
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_update_ffRange`;
CREATE PROCEDURE pro_update_ffRange(in p_nowTime datetime, in p_customerId VARCHAR(32))
BEGIN

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
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
		END;


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
    	START TRANSACTION;
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
	WHERE ff.organization_id = orgId and ff.`year_month` = nowYearMonth and ff.customer_id = p_customerId;

  END LOOP;
  -- 关闭游标
  CLOSE cur;
	
END;
-- DELIMITER ;

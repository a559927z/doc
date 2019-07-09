-- Key2Id
drop function if exists `fn_key_to_id`;
CREATE  FUNCTION `fn_key_to_id`(parentKey VARCHAR(20), customerId VARCHAR(32), p_type VARCHAR(60)) RETURNS varchar(32) CHARSET utf8
BEGIN     
  DECLARE parentId VARCHAR(32);

	IF parentKey = "-1" THEN
		SET parentId = "-1";
	ELSE

		IF p_type = "org" THEN
			SELECT org.id INTO parentId FROM `mup-source`.source_dim_organization org WHERE org.organization_key = parentKey and org.customer_id = customerId;
		END IF;

		IF p_type = "risk" THEN
			SELECT t.separation_risk_id INTO parentId FROM dim_separation_risk t WHERE t.separation_risk_key = parentKey and t.customer_id = customerId;
		END IF;


	END IF;

  RETURN parentId;
END;

-- 员工汇报链全路径
drop function if exists `fn_get_tree_list_v_dim_emp`;
CREATE FUNCTION `fn_get_tree_list_v_dim_emp`(_emp_key varchar(100)) RETURNS varchar(1000) CHARSET utf8
BEGIN
	SET @temp = '';   
	SET @temps = _emp_key ;	
	WHILE @temps IS NOT NULL DO 
		SET @temp = CONCAT_WS('/',@temps, @temp);
		SET @_emp_key1 = SUBSTRING_INDEX(@temp,'/',1);	
		SELECT GROUP_CONCAT(emp_hf_key) INTO @temps FROM v_dim_emp  WHERE emp_key = @_emp_key1;
	END WHILE ;
	RETURN  @temp;
END;
SELECT fn_get_tree_list_v_dim_emp('jxzhang');


drop function if exists `fn_encode`;
CREATE FUNCTION `fn_encode`(in_column varchar(50),in_values varchar(20)) RETURNS varchar(2048) CHARSET utf8
begin 
	
  set @in_column= '' ,@in_values='',@length_values='',@rand_length='',@new_values='',@encode_values='';
  set @in_column = in_column;
  set @in_values = in_values;
  set @length_values = length(@in_column);
  set @rand_length = (select floor(@length_values*rand()));
  set @new_values = concat(substr(in_column,1,@rand_length),@in_values,substr(in_column,@rand_length+1));
  set @encode_values = (select hex(@new_values));
  return @encode_values;

end;


-- 机构全路径
drop function if exists `fn_get_tree_list_dim_organization`;
CREATE FUNCTION `fn_get_tree_list_dim_organization`(p_key varchar(100)) RETURNS varchar(1000) CHARSET utf8
BEGIN
	SET @temp = '';   
	SET @temps = p_key ;	
	WHILE @temps IS NOT NULL DO 
		SET @temp = CONCAT_WS('_',@temps, @temp);
		SET @_key = SUBSTRING_INDEX(@temp,'_',1);	
		SELECT GROUP_CONCAT(organization_parent_key) INTO @temps FROM dim_organization  WHERE organization_key = @_key;
	END WHILE ;
    
	RETURN  @temp;
END;


-- 获取Rank
-- ============================================================================
DROP FUNCTION IF EXISTS fn_getRank;
CREATE FUNCTION fn_getRank(p_emp_id VARCHAR(32)) RETURNS TEXT
DETERMINISTIC
BEGIN
    DECLARE rs TEXT DEFAULT "";
    SET rs =
				(SELECT 
						CONCAT(t2.curt_name, t6.curt_name, t3.curt_name, ".", t4.curt_name)
					FROM v_dim_emp tt
					
					LEFT JOIN dim_sequence t2 on t2.sequence_id = tt.sequence_id
							AND t2.customer_id = tt.customer_id
					LEFT JOIN dim_sequence_sub t6 on t6.sequence_sub_id = tt.sequence_sub_id
							AND t6.customer_id = tt.customer_id
					LEFT JOIN dim_ability t3 on t3.ability_id = tt.ability_id
							AND t3.customer_id = tt.customer_id
					LEFT JOIN dim_ability_lv t4 on t4.ability_lv_id = tt.ability_lv_id
							AND t4.customer_id = tt.customer_id
					where tt.emp_id = p_emp_id
					);
    RETURN rs;
END;
SELECT fn_getRank('e673c034589448a0bc05830ebf85c4d6');

-- 全局ID
-- ============================================================================
DROP  TABLE IF  EXISTS db_tickets64;
CREATE TABLE db_tickets64 (
      id bigint(20) unsigned NOT NULL auto_increment,
      stub char(1) NOT NULL default '',
      PRIMARY KEY (id),
      UNIQUE KEY stub (stub)
  ) ENGINE=MyISAM
;
DROP FUNCTION IF EXISTS fn_getId;
CREATE FUNCTION fn_getId() RETURNS TEXT
DETERMINISTIC
BEGIN
    DECLARE rs TEXT DEFAULT "";
    REPLACE INTO db_tickets64 (stub) VALUES ('a');
    -- TO_DAYS(date):返回日期date是西元0年至今多少天(不计算1582年以前)
    SET rs = CONCAT(TO_DAYS(CURDATE()), '-',(SELECT LAST_INSERT_ID()));
    RETURN rs;
END;
SELECT fn_getId();

-- 时间函数
-- ============================================================================
/*
-- 昨天 yyyy-mm-dd
DROP FUNCTION IF EXISTS fn_getYesterdayYM;
CREATE FUNCTION fn_getYesterdayYM() RETURNS VARCHAR(4)
DETERMINISTIC
BEGIN
    RETURN date_sub(curdate(),interval 1 day);
END;
SELECT fn_getYesterday_li();
-- 昨天 yyyy-mm-dd
DROP FUNCTION IF EXISTS fn_getYesterday_li;
CREATE FUNCTION fn_getYesterday_li() RETURNS VARCHAR(10)
DETERMINISTIC
BEGIN
    RETURN date_sub(curdate(),interval 1 day);
END;
SELECT fn_getYesterday_li();

-- 昨天 yyyymmdd
DROP FUNCTION IF EXISTS fn_getYesterday;
CREATE FUNCTION fn_getYesterday() RETURNS VARCHAR(10)
DETERMINISTIC
BEGIN
    RETURN date_format(date_sub(curdate(),interval 1 day),'%Y%m%d');
END;
SELECT fn_getYesterday();
*/

-- 以下时间都是-1天
-- 本年yyyy
DROP FUNCTION IF EXISTS fn_getY;
CREATE FUNCTION fn_getY() RETURNS VARCHAR(4)
DETERMINISTIC
BEGIN
    RETURN date_format(DATE_ADD( DATE_ADD('2016-01-01',INTERVAL -1 DAY), Interval 0 minute),'%Y');
END;
SELECT fn_getY();
-- 本月yyyymm
DROP FUNCTION IF EXISTS fn_getYM;
CREATE FUNCTION fn_getYM() RETURNS VARCHAR(6)
DETERMINISTIC
BEGIN
    RETURN date_format(DATE_ADD( DATE_ADD(now(),INTERVAL -1 DAY), Interval 0 minute),'%Y%m');
END;
SELECT fn_getYM();
-- 今天yyyymmddd
DROP FUNCTION IF EXISTS fn_getYMD;
CREATE FUNCTION fn_getYMD() RETURNS VARCHAR(8)
DETERMINISTIC
BEGIN
    RETURN date_format(DATE_ADD( DATE_ADD(now(),INTERVAL -1 DAY), Interval 0 minute),'%Y%m%d');
END;
SELECT fn_getYMD();
-- 今天yyyy-mm-ddd
DROP FUNCTION IF EXISTS fn_getYMD_li;
CREATE FUNCTION fn_getYMD_li() RETURNS VARCHAR(10)
DETERMINISTIC
BEGIN
    RETURN CURDATE();
END;
SELECT fn_getYMD_li();

-- 是否本月第一天
DROP FUNCTION IF EXISTS fn_isYMDOne;
CREATE FUNCTION fn_isYMDOne(p_day datetime) RETURNS INT(1)
DETERMINISTIC
BEGIN
	IF date_format(p_day,'%Y-%m-%d')=date_format(date_sub(p_day,INTERVAL day(p_day)-1 day),'%Y-%m-%d') then
		RETURN 1;
	ELSE
		RETURN 0;
	END IF;
END;

-- 本月第一天
DROP FUNCTION IF EXISTS fn_getYMDOne_li;
CREATE FUNCTION fn_getYMDOne_li(p_day datetime) RETURNS VARCHAR(10)
DETERMINISTIC
BEGIN
	RETURN date_format(date_sub(p_day,INTERVAL day(p_day)-1 day),'%Y-%m-%d');
END;

-- 本月第一天
DROP FUNCTION IF EXISTS fn_getYMDLast_li;
CREATE FUNCTION fn_getYMDLast_li(p_day datetime) RETURNS VARCHAR(10)
DETERMINISTIC
BEGIN
	RETURN LAST_DAY(p_day);
END;

-- 获取明年（当前时间加一年）
DROP FUNCTION IF EXISTS fn_getNextY;
CREATE FUNCTION fn_getNextY() RETURNS VARCHAR(10)
DETERMINISTIC
BEGIN
	RETURN DATE_FORMAT(DATE_ADD(now(), Interval 1 YEAR),'%Y');
END;
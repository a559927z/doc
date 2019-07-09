
-- 销售明细
drop procedure if exists pro_fetch_sales_detail;
CREATE  PROCEDURE `pro_fetch_sales_detail`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【销售明细】';
		
	TRUNCATE TABLE sales_detail;
	truncate table sales_order;
	
	  
	INSERT INTO sales_detail( sales_detail_id, customer_id,product_id,sales_order_id,product_number,sales_money,sales_profit,product_modul_id,product_category_id) 
	SELECT d.sales_detail_id, p_customer_id,d.product_id,fn_getId(),d.product_number, d.sales_money,d.sales_profit,p.product_modul_id,p.product_category_id
	FROM `mup-source`.source_sales_detail d
	right join dim_sales_product p on (d.product_id = p.product_id)
	where d.sales_detail_id is not null	;
	
	INSERT INTO sales_order(sales_order_id, customer_id,emp_id,province_id,client_id,city_id,sales_date)
	SELECT sd.sales_order_id,p_customer_id,d.emp_id,d.sales_province_id,null,d.sales_city_id,d.sales_date
	FROM `mup-source`.source_sales_detail d 
	INNER JOIN sales_detail sd  ON (d.sales_detail_id = sd.sales_detail_id) ;
END;

-- 历史销售明细
drop procedure if exists pro_fetch_history_sales_detail;
CREATE  PROCEDURE `pro_fetch_history_sales_detail`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT NOW();
	DECLARE p_message VARCHAR(10000) DEFAULT '【历史销售明细】';
 
	INSERT INTO history_sales_detail 
	(history_sales_detail_id, customer_id,emp_id,organization_id,product_id,product_number,sales_money,sales_profit,sales_province_id,sales_city_id,sales_date,`year_month`)
	SELECT 
  		fn_getId(), d.customer_id,o.emp_id,se.organization_id,d.product_id,d.product_number,d.sales_money,d.sales_profit,o.province_id,o.city_id,o.sales_date,CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT) 
	FROM sales_detail d 
	INNER JOIN sales_order o 
	ON d.sales_order_id = o.sales_order_id
	INNER JOIN sales_emp se 
    ON o.`emp_id` = se.`emp_id`;
	TRUNCATE TABLE sales_detail;
	
END;

-- 机构销售日统计
drop procedure if exists pro_fetch_sales_org_day;
CREATE  PROCEDURE `pro_fetch_sales_org_day`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【机构销售日统计】';

	INSERT INTO sales_org_day (
		organization_id,customer_id,sales_money_day,sales_profit_day,sales_number_day
		,sales_date)  
	SELECT 
		a.organization_id,a.customer_id,SUM(sales_money),SUM(sales_profit),SUM(product_number)
		,sales_date
	FROM history_sales_detail a, sales_emp b 
	WHERE a.emp_id=b.emp_id AND sales_date= CURRENT_DATE()
	GROUP BY  a.organization_id,a.customer_id;
END;

-- 员工销售月统计,机构产品月销售统计,机构销售月统计
DROP PROCEDURE IF EXISTS pro_fetch_sales_emp_org_pro_month;
CREATE PROCEDURE `pro_fetch_sales_emp_org_pro_month`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工销售月统计,机构产品月销售统计,机构销售月统计】';

  IF CURRENT_DATE()= LAST_DAY(NOW()) THEN 
   
   INSERT INTO sales_emp_month (
	emp_id,customer_id,sales_target,payment,return_amount
	,sales_money_month,sales_profit_month,sales_number_month,`year_month`) 
   SELECT 
	emp_id,customer_id,NULL,NULL,NULL
	,SUM(sales_money),SUM(sales_profit),SUM(product_number),CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT) 
   FROM history_sales_detail WHERE  `year_month`= CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT) 
   GROUP BY  emp_id,customer_id;
   INSERT INTO sales_org_month (
	organization_id,customer_id,sales_money_month,sales_profit_month,sales_number_month
	,`year_month`) 
   SELECT 
	a.organization_id,a.customer_id,SUM(sales_money),SUM(sales_profit),SUM(product_number)
	,CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT) 
   FROM history_sales_detail a, sales_emp b WHERE  a.emp_id=b.emp_id  AND `year_month`= CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT) 
   GROUP BY a.organization_id,a.customer_id;
   INSERT INTO sales_org_prod_month (
	organization_id,product_id,customer_id,sales_money_month,sales_profit_month
	,sales_number,sales_number_staff,`year_month`) 
   SELECT 
	a.organization_id,product_id,a.customer_id,SUM(sales_money),SUM(sales_profit)
	,SUM(product_number),SUM(product_number) ,CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT)
   FROM history_sales_detail a,sales_emp b WHERE a.emp_id=b.emp_id  AND `year_month`= CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT) 
   GROUP BY a.organization_id,product_id,a.customer_id;
  
  ELSE 
   
   DELETE FROM sales_org_prod_month WHERE `year_month`=  CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT);
   
   DELETE FROM sales_org_month WHERE `year_month`=  CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT);
   DELETE FROM sales_emp_month WHERE `year_month`=  CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT);
   INSERT INTO sales_emp_month (
	emp_id,customer_id,sales_target,payment,return_amount
	,sales_money_month,sales_profit_month,sales_number_month,`year_month`) 
   SELECT 
	emp_id,customer_id,NULL,NULL,NULL
	,SUM(sales_money),SUM(sales_profit),SUM(product_number),CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT) 
   FROM history_sales_detail WHERE  `year_month`= CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT) 
   GROUP BY  emp_id,customer_id;
   INSERT INTO sales_org_month (
	organization_id,customer_id,sales_money_month,sales_profit_month,sales_number_month,`year_month`) 
   SELECT 
	a.organization_id,a.customer_id,SUM(sales_money),SUM(sales_profit),SUM(product_number),CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT) 
   FROM history_sales_detail a, sales_emp b WHERE  a.emp_id=b.emp_id  AND `year_month`= CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT) 
   GROUP BY a.organization_id,a.customer_id;
   INSERT INTO sales_org_prod_month (
	organization_id,product_id,customer_id,sales_money_month,sales_profit_month,sales_number,`year_month`) 
   SELECT 
	a.organization_id,product_id,a.customer_id,SUM(sales_money),SUM(sales_profit),SUM(product_number),CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT) 
   FROM history_sales_detail a,sales_emp b WHERE a.emp_id=b.emp_id  AND `year_month`= CAST(DATE_FORMAT(NOW(),'%Y%m') AS INT) 
   GROUP BY a.organization_id,product_id,a.customer_id;
  END IF;
end;

-- 员工销售月统计 
drop procedure if exists pro_fetch_sales_emp_month;
CREATE  PROCEDURE `pro_fetch_sales_emp_month`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工销售月统计】';
   
  delete from sales_emp_month where `year_month`=date_format(date_sub(now(),interval 1 day),'%Y%m');
  insert into sales_emp_month (
  	emp_id,customer_id,sales_target,payment,return_amount,
  	sales_money_month,sales_profit_month,sales_number_month,`year_month`)  
  select  emp_id,customer_id,null,null,null,
  	sum(sales_money),sum(sales_profit),sum(product_number),date_format(date_sub(now(),interval 1 day),'%Y%m')
  from history_sales_detail where `year_month`=date_format(date_sub(now(),interval 1 day),'%Y%m') group by  emp_id,customer_id;
 
end;

-- 机构销售月统计
drop procedure if exists pro_fetch_sales_org_month;
CREATE  PROCEDURE `pro_fetch_sales_org_month`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【机构销售月统计】';
   
  delete from sales_org_month where `year_month`=date_format(date_sub(now(),interval 1 day),'%Y%m');
  insert into sales_emp_month (emp_id,customer_id,sales_money_month,sales_profit_month,sales_number_month,`year_month`)
  select emp_id,customer_id,sum(sales_money)/10000,sum(sales_profit)/10000,sum(product_number),date_format(date_sub(now(),interval 1 day),'%Y%m') 
  from history_sales_detail where `year_month`=date_format(date_sub(now(),interval 1 day),'%Y%m') group by organization_id;

 end;

-- 机构产品销售月统计
drop procedure if exists pro_fetch_sales_org_prod_month;
CREATE  PROCEDURE `pro_fetch_sales_org_prod_month`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【机构产品销售月统计】';
   
  delete from sales_org_prod_month where `year_month`=date_format(date_sub(now(),interval 1 day),'%Y%m');
  insert into sales_org_prod_month (organization_id,product_id,customer_id,sales_money_month,sales_profit_month,sales_number_month,sales_number,`year_month`)
  select organization_id,product_id,customer_id,sum(sales_money)/10000,sum(sales_profit)/10000,sum(product_number),count(distinct emp_id),date_format(date_sub(now(),interval 1 day),'%Y%m')
  from history_sales_detail where `year_month`=date_format(date_sub(now(),interval 1 day),'%Y%m') group by organization_id,product_id,customer_id;
  
end;

-- 产品考核
DROP PROCEDURE IF EXISTS pro_fetch_sales_pro_target;
CREATE PROCEDURE `pro_fetch_sales_pro_target`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【产品考核】';
    
     
     INSERT INTO sales_pro_target (product_id,customer_id,sales_target,return_amount,payment,`year_month`) 
     SELECT t.product_id,p_customer_id,t.sales_target,t.return_amount,t.payment,t.`year_month`
     FROM `mup-source`.source_sales_pro_target t
     ON DUPLICATE KEY UPDATE 
     sales_target = t.sales_target,
     return_amount = t.return_amount,
     payment = t.payment,
     `year_month` = t.`year_month`
     ;
     TRUNCATE TABLE `mup-source`.source_sales_pro_target;
END;


-- 机构考核
drop procedure if exists pro_fetch_sales_org_target;
CREATE  PROCEDURE `pro_fetch_sales_org_target`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【机构考核】';
    
     insert into sales_org_target (organization_id,customer_id,sales_target,return_amount,payment,`year_month`) 
     select t.organization_id,p_customer_id,t.sales_target,t.return_amount,t.payment,t.`year_month`
     from `mup-source`.source_sales_org_target t
     on duplicate key update 
     sales_target = t.sales_target,
     return_amount = t.return_amount,
     payment = t.payment,
     `year_month` = t.`year_month`
     ;
   
     truncate table `mup-source`.source_sales_org_target;
end;


-- 员工销售排名
drop procedure if exists pro_fetch_sales_emp_rank;
CREATE PROCEDURE `pro_fetch_sales_emp_rank`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工销售排名】';
 
  INSERT INTO sales_emp_rank (emp_id,emp_rank,organization_id,rank_date,`year_month`)  
  SELECT emp_id ,emp_rank,organization_id,rank_date ,`year_month` FROM  `mup-source`.source_sales_emp_rank t
  ON DUPLICATE KEY UPDATE
	emp_rank = t.emp_rank,
	`year_month` = t.`year_month`
  ;
	BEGIN
	  DECLARE _organization_id VARCHAR(32);
      DECLARE _total INT(7);
      DECLARE _done INT DEFAULT 0;
      DECLARE _code_item_key_1 INT(6);
      DECLARE _code_item_key_2 INT(6);
      DECLARE _code_item_key_3 INT(6);
      DECLARE cur CURSOR FOR SELECT organization_id,COUNT(*) FROM sales_emp_rank GROUP BY organization_id;
      DECLARE EXIT HANDLER FOR NOT FOUND SET _done = 1;
      
	      OPEN cur;
	        WHILE _done= 0 DO 
	          FETCH cur INTO _organization_id, _total;
	           
	          SET _code_item_key_1= (SELECT FLOOR(_total*01)+1);
	          SET _code_item_key_2= (SELECT FLOOR(_total*02)+1);
	          SET _code_item_key_3= (SELECT FLOOR(_total*03)+1);
	           
	          DROP TABLE IF EXISTS tmp1;  
	          DROP TABLE IF EXISTS tmp2; 
	          DROP TABLE IF EXISTS tmp3;
	     
	          CREATE TABLE tmp1 AS 
	          SELECT  emp_rank FROM sales_emp_rank WHERE organization_id=_organization_id AND proportion_id IS NULL ORDER BY emp_rank LIMIT 0,_code_item_key_1;
	          
	          UPDATE sales_emp_rank t,(SELECT DISTINCT emp_rank FROM tmp1) t1 SET t.proportion_id=(SELECT sys_code_item_id FROM sys_code_item WHERE code_group_id='sales_emp_rank' AND sys_code_item_key='10') 
	          WHERE t.emp_rank = t1.emp_rank AND t.proportion_id IS NULL;
	                 
	           CREATE TABLE tmp2 AS 
	          SELECT  emp_rank FROM sales_emp_rank WHERE organization_id=_organization_id AND proportion_id IS NULL ORDER BY emp_rank LIMIT 0,_code_item_key_2;
	                    
	          UPDATE sales_emp_rank t,(SELECT DISTINCT emp_rank FROM tmp2) t1 SET t.proportion_id=(SELECT sys_code_item_id FROM sys_code_item WHERE code_group_id='sales_emp_rank' AND sys_code_item_key='20') 
	          WHERE t.emp_rank = t1.emp_rank AND t.proportion_id IS NULL;
	
	          CREATE TABLE tmp3 AS 
	          SELECT  emp_rank FROM sales_emp_rank WHERE organization_id=_organization_id AND proportion_id IS NULL ORDER BY emp_rank LIMIT 0,_code_item_key_3;
	          
	          UPDATE sales_emp_rank t,(SELECT DISTINCT emp_rank FROM tmp3) t1 SET t.proportion_id=(SELECT sys_code_item_id FROM sys_code_item WHERE code_group_id='sales_emp_rank' AND sys_code_item_key='30') 
	          WHERE t.emp_rank = t1.emp_rank AND t.proportion_id IS NULL;
	      
	          UPDATE sales_emp_rank SET  proportion_id=(SELECT sys_code_item_id FROM sys_code_item WHERE code_group_id='sales_emp_rank' AND sys_code_item_key='31') 
	          WHERE   proportion_id IS NULL;
	
	          DROP TABLE IF EXISTS tmp1;  
	          DROP TABLE IF EXISTS tmp2; 
	          DROP TABLE IF EXISTS tmp3;         
	          
	       END WHILE;
	     CLOSE cur;
   END;
END;

-- 业务能力考核
drop procedure if exists pro_fetch_sales_ability;
CREATE  PROCEDURE `pro_fetch_sales_ability`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务能力考核】';
	
    INSERT INTO history_sales_ability(
    	history_sales_ability_id,emp_id,customer_id,STATUS,check_date
    	,`year_month`) 
    SELECT 
    	fn_getId(), emp_id,customer_id,STATUS,check_date
    	,CAST(DATE_FORMAT(check_date,'%Y%m') AS INT) 
    FROM sales_ability;
    
    TRUNCATE TABLE sales_ability;

    INSERT INTO sales_ability (emp_id,customer_id,STATUS,check_date) 
    SELECT emp_id,p_customer_id,STATUS,check_date
    FROM `mup-source`.source_sales_ability;
  
    TRUNCATE TABLE `mup-source`.source_sales_ability;
END;

-- 员工考核
drop procedure if exists pro_fetch_sales_emp_target;
CREATE PROCEDURE `pro_fetch_sales_emp_target`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工考核】';
     
     INSERT INTO sales_emp_target (
	emp_id,cusomer_id,sales_target,return_amount
	,payment,`year_month`) 
     SELECT 
	emp_id,p_customer_id,sales_target,return_amount
	,payment,`year_month` 
     FROM `mup-source`.source_sales_emp_target;
   
     TRUNCATE TABLE `mup-source`.source_sales_emp_target;

END;

-- 团队销售排名
drop procedure if exists pro_fetch_sales_team_rank;
CREATE  PROCEDURE `pro_fetch_sales_team_rank`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【团队销售排名】';
  
	INSERT INTO sales_team_rank(team_id,customer_id,organization_id,team_rank,rank_date,`year_month`)
	SELECT team_id,p_customer_id,organization_id,team_rank,rank_date,cast(date_format(now(),'%Y%m') as int) 
	from  `mup-source`.source_sales_team_rank;
  
	truncate table `mup-source`.source_sales_team_rank;
end;

-- 团队考核
drop procedure if exists pro_fetch_sales_team_target;
CREATE PROCEDURE `pro_fetch_sales_team_target`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【团队考核】';
    
     INSERT INTO sales_team_target (
	team_id,customer_id,sales_target,return_amount
	,payment,`year_month`) 
     
     SELECT 
	team_id,customer_id,sales_target,return_amount
	,payment,`year_month` 
     FROM `mup-source`.source_sales_team_target;
   
     TRUNCATE TABLE `mup-source`.source_sales_team_target;
END;

-- 销售人员信息
drop procedure if exists pro_fetch_sales_emp;
CREATE PROCEDURE `pro_fetch_sales_emp`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【销售人员信息表】';
     
     insert into sales_emp (emp_id,customer_id,user_name_ch,organization_id,age,performance_id,sex,degree_id,team_id) 
     select emp_id,p_customer_id,user_name_ch,organization_id,age,performance_id,sex,degree_id,team_id 
     from `mup-source`.source_sales_emp a on duplicate key update 
	     user_name_ch=a.user_name_ch,
	     organization_id=a.organization_id,
	     age=a.age,
	     performance_id=a.performance_id,
	     degree_id=a.degree_id,
	     team_id=a.team_id;
   
     truncate table `mup-source`.source_sales_emp;
end;

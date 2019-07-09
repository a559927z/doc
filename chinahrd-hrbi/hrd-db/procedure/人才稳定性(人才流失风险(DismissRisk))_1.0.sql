
-- 离职风险评估
drop procedure if exists pro_fetch_dimission_risk;
CREATE  PROCEDURE `pro_fetch_dimission_risk`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-离职风险评估】';
  
	-- 把最新要同步的员工的最后一次离职风险更新为0先
	UPDATE dimission_risk t1 
	INNER JOIN (SELECT emp_id FROM `mup-source`.source_dimission_risk) t2 ON t1.emp_id = t2.emp_id
	SET is_last = 0;
	
	-- 最新要同步的员工的 最后一次离职风险 固定1
	INSERT INTO dimission_risk (
		dimission_risk_id,customer_id,emp_id,note,risk_date,risk_flag,is_last) 
	SELECT dimission_risk_id,customerId,emp_id,note,risk_date,risk_flag, 1 
	FROM `mup-source`.source_dimission_risk  a
	ON DUPLICATE KEY UPDATE
	   note=a.note,
	   risk_date=a.risk_date,
	   risk_flag=a.risk_flag, 
	   is_last=1;
 
end;

-- 离职风险评估细项
drop procedure if exists pro_fetch_dimission_risk_item;
CREATE  PROCEDURE `pro_fetch_dimission_risk_item`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-离职风险评估细项】';
   
   insert into dimission_risk_item(dimission_risk_item_id,customer_id,dimission_risk_id,separation_risk_id,risk_flag) 
   select dimission_risk_item_id,p_customer_id,dimission_risk_id,separation_risk_id,risk_flag 
   from `mup-source`.source_dimission_risk_item ;
  
end;

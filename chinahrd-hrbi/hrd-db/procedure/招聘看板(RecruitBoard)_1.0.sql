-- 目前岗位平均薪酬
drop procedure if exists pro_recruit_salary_statistics;
CREATE PROCEDURE `pro_recruit_salary_statistics`(_year_months INT, _years INT, _customerid CHAR(32))
BEGIN 
	INSERT INTO recruit_salary_statistics(
				recruit_salary_statistics_id,
        		customer_id,
				position_id,
				position_name,
				rank_name,
				avg_sal,
				emp_total,
				year_months
      )
			SELECT 
				fn_getId(),
       			 _customerid, 
				rp.position_id AS positionId,
				dp.position_name AS positionName,
				vde.rank_name AS rankName,
				AVG(AES_DECRYPT(pay.pay_should, 'hrbi')) AS pay,COUNT(*) AS EMP_TOTAL, _year_months
			FROM recruit_public rp
			LEFT JOIN dim_position dp ON rp.customer_id = dp.customer_id AND dp.position_id = rp.position_id
			LEFT JOIN v_dim_emp vde ON vde.customer_id = dp.customer_id AND vde.position_id = dp.position_id
			LEFT JOIN pay ON pay.customer_id = vde.customer_id AND pay.emp_id = vde.emp_id AND pay.`year_month` = _year_months
			WHERE
			rp.customer_id = _customerid
			AND rp.`year` = _years
			GROUP BY rp.position_id, dp.position_name, vde.rank_name, _customerid;
END;

-- 招聘年度费用
drop procedure if exists pro_fetch_recruit_value;
CREATE  PROCEDURE `pro_fetch_recruit_value`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT NOW();
	DECLARE p_message VARCHAR(10000) DEFAULT '【关系表-招聘年度费用】';

   INSERT INTO recruit_value 
   SELECT 
	recruit_value_id,p_customer_id,organization_id,budget_value,outlay,`year`,NULL,NULL 
   FROM `mup-source`.source_recruit_value a 
	   ON DUPLICATE KEY 
	   UPDATE 
	   organization_id=a.organization_id,
	   budget_value=a.budget_value,
	   outlay=a.outlay,
	   `year`=a.`year`;

  TRUNCATE TABLE `mup-source`.source_recruit_value;
END;

-- 外部人才库
drop procedure if exists pro_fetch_out_talent;
CREATE  PROCEDURE `pro_fetch_out_talent`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT NOW();
	DECLARE p_message VARCHAR(10000) DEFAULT '【关系表-外部人才库】';
  
   INSERT INTO out_talent 
	SELECT out_talent_id,p_customer_id,img_path,user_name_ch,user_name,email,age,sex,degree_id,url,school,tag,NULL,NULL 
	FROM `mup-source`.source_out_talent a
   ON DUPLICATE KEY 
	UPDATE 
	   img_path=a.img_path,
	   user_name_ch=a.user_name_ch,
	   user_name=a.user_name,
	   email=a.email,
	   age=a.age,
	   sex=a.sex,
	   degree_id=a.degree_id,
	   url=a.url,
	   school=a.school,
	   tag=a.tag;

  TRUNCATE TABLE `mup-source`.source_out_talent;

END;

-- 招聘岗位薪酬
DROP PROCEDURE IF EXISTS `pro_fetch_recruit_salary_statistics`;
CREATE PROCEDURE `pro_fetch_recruit_salary_statistics` (
  IN p_customer_id VARCHAR (32),
  IN p_user_id VARCHAR (32)
) 
BEGIN
  DECLARE customerId VARCHAR (32) DEFAULT p_customer_id ;DECLARE optUserId VARCHAR (32) DEFAULT p_user_id ;DECLARE startTime TIMESTAMP DEFAULT NOW() ;DECLARE p_message VARCHAR (10000) DEFAULT '【招聘岗位薪酬】' ;IF DATE_FORMAT(NOW(), '%Y%m%d') = LAST_DAY(NOW()) 
  THEN 
  INSERT INTO recruit_salary_statistics (
    recruit_salary_statistics_id,
    customer_id,
    position_id,
    position_name,
    rank_name,
    avg_sal,
    emp_total,
    year_months
  ) 
  SELECT 
    fn_getId(), p_customer_id, vde.position_id AS positionId, dp.position_name AS positionName, vde.rank_name AS rankName,
    AVG(AES_DECRYPT(pay.pay_should, 'hrbi')) AS pay,
    COUNT(*) AS EMP_TOTAL,
    _year_months 
  FROM
    dim_position dp 
    INNER JOIN v_dim_emp vde ON vde.customer_id = dp.customer_id AND vde.position_id = dp.position_id 
    INNER JOIN pay ON pay.customer_id = vde.customer_id AND pay.emp_id = vde.emp_id AND pay.`year_month` = DATE_FORMAT(NOW(), '%Y%m') 
  WHERE rp.customer_id = p_customer_id 
  GROUP BY rp.position_id,
    dp.position_name,
    vde.rank_name,
    _customerid ;
    END IF ;
END ;

-- 招聘发布
DROP PROCEDURE IF EXISTS `pro_fetch_recruit_public`;
CREATE PROCEDURE `pro_fetch_recruit_public`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT NOW();
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-招聘发布】';
  
   INSERT INTO recruit_public 
   SELECT recruit_public_id,p_customer_id,organization_id,position_id,NULL,plan_num,start_date,end_date,days,NULL,NULL,NULL,NULL,is_public,`year`,NULL,NULL 
   FROM `mup-source`.source_recruit_public a
   ON DUPLICATE KEY 
	UPDATE 
	   organization_id=a.organization_id,
	   position_id=a.position_id,
	   start_date=a.start_date,
	   end_date=a.end_date,
	   days=a.days;
	   
   TRUNCATE TABLE `mup-source`.source_recruit_public;
   
   UPDATE recruit_public a SET employ_num=(SELECT COUNT(*) FROM recruit_result b WHERE a.recruit_public_id=b.recruit_public_id AND is_entry=1);
   UPDATE recruit_public a SET resume_num=(SELECT COUNT(*) FROM recruit_result b WHERE a.recruit_public_id=b.recruit_public_id AND is_entry=1);
   UPDATE recruit_public a SET interview_num=(SELECT COUNT(*) FROM recruit_result b WHERE a.recruit_public_id=b.recruit_public_id AND is_interview=1);
   UPDATE recruit_public a SET offer_num=(SELECT COUNT(*) FROM recruit_result b WHERE a.recruit_public_id=b.recruit_public_id AND is_offer=1);
   UPDATE recruit_public a SET entry_num=(SELECT COUNT(*) FROM recruit_result b WHERE a.recruit_public_id=b.recruit_public_id AND is_entry=1);

END ;

-- 招聘结果-年
drop procedure if exists pro_fetch_recruit_result;
CREATE  PROCEDURE `pro_fetch_recruit_result`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT NOW();
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-招聘结果】';
   
   INSERT INTO recruit_result 
   SELECT recruit_result_id,p_customer_id,recruit_result_key,username,sex,age,degree_id,major,school,is_resume,is_interview,is_offer,is_entry,url,recruit_public_id,`year`,NULL,NULL 
   FROM `mup-source`.source_recruit_result a
   ON DUPLICATE KEY UPDATE 
   is_resume=a.is_resume,
   is_interview=a.is_interview,
   is_offer=a.is_offer,
   is_entry=a.is_entry;

   TRUNCATE TABLE `mup-source`.source_recruit_result;

END;

-- 招聘渠道
drop procedure if exists pro_fetch_recruit_channel;
CREATE PROCEDURE `pro_fetch_recruit_channel`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-招聘渠道】';

  insert into recruit_channel 
  select recruit_channel_id,p_customer_id,position_id,channel_id,employ_num,outlay,start_date,end_date,days,recruit_public_id,`year`,null,null 
  from `mup-source`.source_recruit_channel a 
  on duplicate key update 
  employ_num=a.employ_num,
  outlay=a.outlay,
  days=a.days,
  start_date=a.start_date,
  end_date=a.end_date;

  truncate table `mup-source`.source_recruit_channel;  
end;

-- 招聘进程
drop procedure if exists pro_fetch_recruitment_process;
CREATE PROCEDURE `pro_fetch_recruitment_process`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT NOW();
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-招聘进程】';
  
  INSERT INTO recruitment_process (recruitment_process_id,customer_id,publice_job_num,resume_num,accept_num,offer_num,organization_id)
  SELECT recruitment_process_id,p_customer_id,publice_job_num,resume_num,accept_num,offer_num,organization_id 
  FROM recruitment_process a
  ON DUPLICATE KEY 
	UPDATE 
	  publice_job_num=a.publice_job_num,
	  resume_num=a.resume_num,
	  accept_num=a.accept_num,
	  offer_num=a.offer_num,
	  organization_id=a.organization_id;

END;


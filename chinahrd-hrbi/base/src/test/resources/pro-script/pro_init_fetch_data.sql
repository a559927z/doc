#=======================================================pro_init_fetch_data=======================================================
-- 'demo','jxzhang'
-- 试用说明：---------------------------------------------------
-- 1、保证dim_business_unit数据存在，
-- 2、插入【源表】时，抓取过来的数据要插入cutsomerId、上传上来的数据要插入
-- 2.1、抓取过来的数据cutsomerId获取：在复制数据时指定customerId = (SELECT dc.customer_id FROM dim_customer dc WHERE dc.customer_key = p_customer_key)
-- 2.2、上传上来的数据cutsomerId获取：在java controller层得getCustomerId()：dto.setCustomeId(getCustomerId())
-- 3、第二次之后所有数据有抓取过来的数据xxxKey不能有改动过。因为存在数据更新找不到旧数据，让xxxKey保护与客户连接桥梁。
-- -------------------------------------------------------------
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_init_fetch_data`;
CREATE PROCEDURE pro_init_fetch_data(in p_customer_key VARCHAR(50), in p_user_name VARCHAR(50))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT (SELECT dc.customer_id FROM dim_customer dc WHERE dc.customer_key = p_customer_key);
	DECLARE userId VARCHAR(32) DEFAULT (SELECT du.user_id FROM dim_user du WHERE du.user_name = p_user_name and du.customer_id = customerId);
	DECLARE startTime TIMESTAMP DEFAULT now();

	SELECT customerId, userId, startTime;

	-- 以下的存储过程都会必须用customerId做过滤条件，所以请保证【源表】数据已有customerId
	-- 以下的顺序不能调乱
	-- 维度表
	CALL pro_fetch_dim_ability(customerId, userId, 'ability');	-- 能力
	CALL pro_fetch_dim_key_talent_type(customerId, userId, 'keyTalent');	-- 关键人才类别
	CALL pro_fetch_dim_performance(customerId, userId, 'performance');	-- 绩效

	CALL pro_fetch_dim_emp(customerId, 'dba');
	CALL pro_fetch_dim_user(customerId, 'dba');
	
	CALL pro_fetch_dim_role(customerId, userId);
	
  CALL pro_fetch_organization_type(customerId, userId);
  CALL pro_fetch_organization(customerId, userId);
	CALL pro_init_organization('demo');		-- 初始机构，全路径、是否有子节点

	CALL pro_fetch_dim_sequence(customerId, userId, 'sequence');	-- 序列
	CALL pro_fetch_dim_position(customerId, userId);
	CALL pro_fetch_emp_position_relation(customerId, userId);

	-- 关系表
	CALL pro_fetch_user_role_relation(customerId, userId);
	CALL pro_fetch_role_organization_relation(customerId, userId);

-- 生产力--------------------------------------------------------------------------
	-- 业务表
	CALL pro_fetch_emp_overtime(customerId, userId);	-- 加班时间
	CALL pro_fetch_trade_profit(customerId, userId);	-- 营业利润
	CALL pro_init_factfte(DATE_ADD(now(), Interval -1 month), p_customer_key);	-- 人均效益
-- --------------------------------------------------------------------------------


-- 驱动力--------------------------------------------------------------------------
	
	CALL pro_fetch_dim_separation_risk(customerId, userId);	-- 绩效
	CALL pro_fetch_emp_relation(customerId, userId);		-- 员工链
	CALL pro_fetch_dim_run_off(customerId, userId);  		-- 流失
	CALL pro_fetch_run_off_record(customerId, userId); 	-- 流失记录
	
-- --------------------------------------------------------------------------------

END
-- DELIMITER ;

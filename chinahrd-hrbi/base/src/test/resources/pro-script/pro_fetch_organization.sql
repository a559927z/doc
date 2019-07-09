#=======================================================pro_fetch_organization=======================================================
-- 'demo'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 保证dim_business_unit、dim_organization_type数据存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_organization`;
CREATE PROCEDURE pro_fetch_organization(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;

	-- 定义接收临时表数据的变量 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE exist INT;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【机构-维度表】：数据刷新完成';

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;

			REPLACE INTO dim_organization(
				organization_id, customer_id, business_unit_id, organization_type_id, organization_key,
				organization_parent_id, organization_parent_key,
				organization_name, is_single, 
				has_children
			)
			SELECT 
				sorg.id, customerId, bu.business_unit_id, ot.organization_type_id, sorg.organization_key,
				fn_key_to_id(sorg.organization_parent_key, customerId, 'org'), sorg.organization_parent_key,
				sorg.organization_name,
				sorg.is_single,
				0
			FROM soure_dim_organization sorg
			INNER JOIN dim_business_unit bu on bu.business_unit_key = sorg.business_unit_key
			INNER JOIN dim_organization_type ot on sorg.organization_type_key = ot.organization_type_key
			WHERE sorg.customer_id = customerId
			ORDER BY ot.organization_type_level;

		-- 初始机构，全路径、是否有子节点
		CALL pro_init_organization('demo');		

		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【机构-维度表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_organization.id、buId、organTypeId、organKey、pId、organName, isSingle', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_organization.id、buId、organTypeId、organKey、pId、organName, isSingle', p_message, startTime, now(), 'sucess' );
		END IF;

END;
-- DELIMITER ;

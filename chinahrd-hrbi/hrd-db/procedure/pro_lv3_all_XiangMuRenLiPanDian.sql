drop procedure if exists `pro_lv3_all_XiangMuRenLiPanDian`;
CREATE PROCEDURE `pro_lv3_all_XiangMuRenLiPanDian`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE refresh TIMESTAMP DEFAULT now();
		
	update sys_sync_status set status = 2 where code = 'pro_lv3_all_XiangMuRenLiPanDian';
		-- 项目
		CALL pro_fetch_project(customerId,optUserId, refresh);
		-- 项目人力明细
		CALL pro_fetch_project_manpower(customerId,optUserId);
		-- 项目投入费用明细
		CALL pro_fetch_project_input_detail(customerId,optUserId);
       	-- 业务表-项目费用明细
		CALL pro_fetch_project_cost(customerId,optUserId);

		
		-- 主导项目参与项目关系
		CALL pro_fetch_project_partake_relation(customerId,optUserId);
		-- 项目最大负荷数
		CALL pro_fetch_project_maxvalue(customerId,optUserId);
       
	update sys_sync_status set status = 3 where code = 'pro_lv3_all_XiangMuRenLiPanDian';

	-- INSERT INTO log VALUES(fn_getId(), customerId, optUserId, 'pro_lv3_all_XiangMuRenLiPanDian', "【项目人力盘点-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;


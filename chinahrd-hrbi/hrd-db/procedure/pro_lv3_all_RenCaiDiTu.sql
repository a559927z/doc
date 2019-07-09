CREATE PROCEDURE `pro_lv3_all_RenCaiDiTu`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 


		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
   
 
     -- 员工能力记录
	CALL pro_fetch_ability_change(p_customer_id,p_user_id);
     
     -- 人才地图员工信息
       call pro_fetch_map_talent_info(p_customer_id,p_user_id);
     
     -- 人才地图配置表
       call pro_fetch_map_config(p_customer_id,p_user_id);
    
     -- z轴信息 
       call pro_fetch_dim_z_info(p_customer_id,p_user_id);
 
     -- 人才地图
       call pro_fetch_map_talent(p_customer_id,p_user_id);


	-- 更新
	update sys_sync_status set status = 2 where code = 'all_RenCaiDiTu' and status = 1;

	INSERT INTO log 
	VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_RenCaiDiTu', "【人才地图-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;


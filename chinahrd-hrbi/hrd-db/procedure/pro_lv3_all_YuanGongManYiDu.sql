drop procedure if EXISTS `pro_lv3_all_YuanGongManYiDu`;
CREATE PROCEDURE `pro_lv3_all_YuanGongManYiDu`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
    
	update sys_sync_status set status = 2 where code = 'pro_lv3_all_YuanGongManYiDu';
     
      -- call pro_fetch_organization_emp_relation(p_customer_id,p_user_id);
      -- 历史表-敬业度评分
      call pro_fetch_dedicat_genre_score(customerId,optUserId);
      -- 历史表-满意度评分
      call pro_fetch_satfac_genre_score(customerId,optUserId);
      -- 历史表-满意度机构评分
      call pro_fetch_satfac_organ(customerId,optUserId);
      -- 历史表-敬业度机构评分
      call pro_fetch_dedicat_organ(customerId,optUserId);

      
	update sys_sync_status set status = 3 where code = 'pro_lv3_all_YuanGongManYiDu';

	-- INSERT INTO log VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_ZuZhiJiGou', "【组织架构（编制与空缺）-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;


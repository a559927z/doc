/**
 * p_key2,加密Key  目前值为 'hrbi'
 */
drop procedure if exists pro_lv3_all_XinChouKanBan;
CREATE PROCEDURE `pro_lv3_all_XinChouKanBan`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
		DECLARE p_key2 VARCHAR(32) DEFAULT 'hrbi';
		DECLARE p_ym INT DEFAULT fn_getYM();

		
		DECLARE y INT DEFAULT SUBSTR(p_ym FROM 1 FOR 4);
		DECLARE m INT DEFAULT SUBSTR(p_ym FROM 5 FOR 6);
		update sys_sync_status set status = 2 where code = 'pro_lv3_all_XinChouKanBan' ;

		-- 持股明细
		CALL pro_fetch_share_holding(customerId, optUserId);
		-- 薪酬费用
		CALL pro_fetch_pay(customerId, optUserId, p_key2, p_ym);
		-- 薪酬汇总(并计算50分位值)
		-- CALL pro_fetch_pay_collect(customerId, optUserId, p_key2, p_ym);
		-- 薪酬费用cr值
		CALL pro_update_pay_crValue(customerId, optUserId, p_key2, p_ym);
		-- 薪酬汇总过往
		-- 由java代码调用
		-- IF (m=12) THEN
		--	CALL pro_fetch_pay_collect_year(customerId, optUserId, y);
		-- END IF;
		-- 工资明细（月单位）
		CALL pro_fetch_salary(customerId, optUserId, y);
		-- 工资明细（年单位）
		CALL pro_fetch_salary_year(customerId, optUserId);
		-- 固定福利明细
		CALL pro_fetch_welfare_nfb(customerId, optUserId);
		-- 企业福利明细（货币）
		CALL pro_fetch_welfare_cpm(customerId, optUserId, y);
		-- 薪酬看板-企业福利明细（非货币）
		CALL pro_fetch_welfare_uncpm(customerId, optUserId, p_ym);
		-- 固定福利月统计（末月清空）
		CALL pro_fetch_welfare_nfb_total(customerId, optUserId);
		-- 企业福利月统计（末月清空）
		CALL pro_fetch_welfare_cpm_total(customerId, optUserId);
		-- 行业分位值
		CALL pro_fetch_profession_quantile_relation(customerId, optUserId);
		-- 薪酬预算表
		CALL pro_fetch_pay_value(customerId, optUserId, y);
		
-- source_pay_value
	-- 更新
	update sys_sync_status set status = 3 where code = 'pro_lv3_all_XinChouKanBan' ;

-- 	INSERT INTO log VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_XinChouKanBan', "【薪酬看板-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;


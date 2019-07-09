drop procedure if exists pro_lv2_all_quota;
CREATE PROCEDURE `pro_lv2_all_quota`(
	in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), 
	in p_refresh TIMESTAMP(6),
	in p_getYM VARCHAR(6)
	)
BEGIN
	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	
	update sys_sync_status set status = 2 where code = 'pro_lv2_all_quota';
	
	-- 职位序列统计
	CALL pro_lv3_all_ZhiWeiXuLie(customerId, optUserId);
		
	-- 人均效益
	CALL pro_lv3_all_RenJunXiaoYi(customerId, optUserId, refresh);
	-- 人力成本
	CALL pro_lv3_all_RenLiChengBen(customerId, optUserId);
	-- 员工绩效
	CALL pro_lv3_all_YuanGongJiXiao(customerId, optUserId);
	-- 薪酬看板
	CALL pro_lv3_all_XinChouKanBan(customerId, optUserId);
	-- 关键人才
	CALL pro_lv3_all_GuanJianRenCai(customerId, optUserId);
	-- 主动流失率
	CALL pro_lv3_all_ZhuDongLiuShiLv(customerId, optUserId);
	-- 人才稳定性(人才流失风险Dismissrisk)
	CALL pro_lv3_all_RenCaiLiuShiFenXian(customerId, optUserId);
	-- 销售看板
	CALL pro_lv3_all_XiaoShouKanBan(customerId, optUserId);
	
	-- 人才剖象
	CALL pro_lv3_all_RenCaiPouXiang(customerId, optUserId);
	
	-- 员工忠诚度满意度
	CALL pro_lv3_all_YuanGongManYiDu(customerId, optUserId);
	-- 培训看板
	CALL pro_lv3_all_PeiXunKanBan(customerId, optUserId);
	-- 团队画像
	CALL pro_lv3_all_TuanDuiHuaXiang(customerId, optUserId, refresh);
	-- 人才损益
	CALL pro_lv3_all_RenCaiSunYi(customerId, optUserId);
	-- 项目人才盘点
	CALL pro_lv3_all_XiangMuRenLiPanDian(customerId, optUserId);
	-- 劳动力效能
	CALL pro_lv3_all_LaoDongLiXiaoNeng(customerId, optUserId);
	-- 岗位胜任度
	CALL pro_lv3_all_GangWeiShengRenDu(customerId, optUserId);
	-- 人才地图
	CALL pro_lv3_all_RenCaiDiTu(customerId, optUserId);
	-- 招聘看板
	CALL pro_lv3_all_ZhaoPinKanBan(customerId, optUserId);
	-- 晋级看板
	CALL pro_lv3_all_JinJiKanBan(customerId, optUserId);
	
	-- 更新
	update sys_sync_status set status = 3 where code = 'pro_lv2_all_quota';
END;

package net.chinahrd.db.input.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.chinahrd.db.input.Entity.CheckInputdataStatusEntity;
import net.chinahrd.db.input.service.DbImportBaseService;
import net.chinahrd.db.input.service.DbImportChurnRateService;
import net.chinahrd.db.input.service.DbImportPerBenefitService;
import net.chinahrd.db.input.service.impl.DbImportQuotaServiceImpl;
import net.chinahrd.utils.CollectionKit;

/**
 * 
 * @author jxzhang on 2016-02-29
 *
 */
@Controller
@RequestMapping("/dbImport")
public class DbImportController {

	private static Logger log = LoggerFactory.getLogger(DbImportController.class);

	// 基础相关（基础维度相关）
	@Autowired
	private DbImportBaseService dbImportBaseService;

	// 人均效益相关
	@Autowired
	private DbImportPerBenefitService dbImportPerBenefitService;

	// 流失率相关
	@Autowired
	private DbImportChurnRateService dbImportChurnRateService;

	// 指标相关
	@Autowired
	private DbImportQuotaServiceImpl dbImportQuotaService;

	/**
	 * 检验ETL是否已对soure表抽取完成。时间任务 </ br>
	 * 
	 * 0：customerTable 2 soureTable error... </ br> 1：customerTable 2 soureTable
	 * finish... </ br> 2：soureTable 2 dataTable running... </ br> 3：soureTable
	 * 2 dataTable finish... </ br>
	 */
	public void checkInputdateStatus() {
		CheckInputdataStatusEntity entity = dbImportBaseService.findCheckInputdataStatus();
		Integer code = entity.getCode();
		String id = entity.getId();
		if (code == 1) {
			dbImportAll(id);
		}
	}

	/**
	 * 源表到数据表抽取。
	 */
	public void dbImportAll(String id) {
		dbImportBaseService.updateCheckInputdataStatus(id, 2, "soureTable 2 dataTable running...");
		boolean initDimOrganization = false;
		boolean initVDimEmp = false;
		// 维度表
		boolean callDimTables = dbImportBaseService.callDimTables2();
		// // 基础表
		// boolean organRs =
		// dbImportBaseService.replaceDimOrganization(initDimOrganization);
		// boolean posRs = dbImportBaseService.callDimPosition();
		// boolean callHistoryEmpCount = false;
		// 业务表
		// emp
		// if (organRs && callDimTables && posRs) {
		// if (organRs && callDimTables) {
		// 员工表（删除五年前已离职数据）
		// boolean rs = dbImportService.replaceVDimEmp(initVDimEmp);
		// if (rs) {
		// 我的下属
		// dbImportService.callUnderling();
		// }
		// // 总人数(每天和每月)
		// callHistoryEmpCount = dbImportService.callHistoryEmpCount();
		// // 工作异动
		// dbImportService.replaceJobChange();
		// }

		// // 指标表
		// if (callHistoryEmpCount) {
		// dbImportRenJunXiaoYi();
		// dbImportChurnRate();
		// dbImportPay();
		// }

		System.out.println(new DateTime().toLocalDateTime());
		dbImportBaseService.updateCheckInputdataStatus(id, 3, "soureTable 2 dataTable finish...");
	}

	/**
	 * 所有指标
	 */
	private void dbImportQuota() {
		boolean isOk = false;
		isOk = dbImportQuotaService.callQuotaRenJunXiaoYi();
		if (isOk) {
			// 下一指标
		}
		log.info("指标：" + isOk);
	}

	/**
	 * 人均效益-指标
	 */
	private void dbImportRenJunXiaoYi() {
		// 明年目标人均效益
		dbImportPerBenefitService.callTargetBenefitValue();
		// 营业利润
		boolean callTradeProfit = dbImportPerBenefitService.callTradeProfit();
		if (callTradeProfit) {
			// 等效员工数据
			dbImportPerBenefitService.callFactfte();
		}
	}

	/**
	 * 流失率-指标
	 */
	private void dbImportChurnRate() {
		// 月度总人数
		dbImportChurnRateService.replaceMonthlyEmpCount();

	}

	/**
	 * 薪酬看板
	 */
	private void dbImportPay() {

	}

}

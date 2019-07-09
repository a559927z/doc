package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.promotionBoard.SourceEmpPqEvalRelationETL;
import net.chinahrd.etl.impl.promotionBoard.SourcePromotionElementSchemeETL;
import net.chinahrd.etl.impl.promotionBoard.SourcePromotionPlanETL;
import net.chinahrd.etl.impl.promotionBoard.SourcePromotionResultsETL;
import net.chinahrd.etl.impl.promotionBoard.SourcePromotionTotalETL;

/**
 * 晋级看板
 * 
 * @author jxzhang on 2017年5月17日
 * @Verdion 1.0 版本
 */
public class PromotionBoardSyncModule extends ModuleThread {

	@Override
	protected void execute() {
		// 晋级要素方案
		new SourcePromotionElementSchemeETL().execute();
		// 职级晋升方案
		new SourcePromotionPlanETL().execute();
		// 员工岗位能力评价
		new SourceEmpPqEvalRelationETL().execute();
		// 员工占比统计
		new SourcePromotionTotalETL().execute();
		// 员工晋级结果
		new SourcePromotionResultsETL().execute();
	}

	public static void main(String[] m) {
		new PromotionBoardSyncModule().start();
	}

}

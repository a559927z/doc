package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.dismissRisk.SourceDimissionRiskETL;
import net.chinahrd.etl.impl.dismissRisk.SourceDimissionRiskItemETL;

/**
 * 人才稳定性(人流失风险)
 * 
 * @author jxzhang on 2017年5月5日
 * @Verdion 1.0 版本
 */
public class DismissRiskSyncModule extends ModuleThread {

	@Override
	public void execute() {
		// 离职风险评估
		new SourceDimissionRiskETL().execute();
		// 离职风险评估细项
		new SourceDimissionRiskItemETL().execute();
	}

	public static void main(String[] d) {
		new DismissRiskSyncModule().start();
	}

}

package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.benefit.SourceTargetBenefitValueETL;
import net.chinahrd.etl.impl.benefit.SourceTradeProfitETL;

/**
 * 人均效益
 * 
 * @author jxzhang on 2017年5月4日
 * @Verdion 1.0 版本
 */
public class BenefitSyncModule extends ModuleThread {

	@Override
	protected void execute() {
		// 明年目标人均效益(已验证)
		new SourceTargetBenefitValueETL().execute();
		// 营业利润(已验证)
		new SourceTradeProfitETL().execute();
	}

	public static void main(String[] d) {
		new BenefitSyncModule().start();
	}
}

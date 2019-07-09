package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.accordDismiss.SourceRunOffRecordETL;

/**
 * 主动流失率
 * 
 * @author jxzhang on 2017年5月4日
 * @Verdion 1.0 版本
 */
public class AccordDismissSyncModule extends ModuleThread {

	@Override
	protected void execute() {
		// 流失记录
		new SourceRunOffRecordETL().execute();
	}

	public static void main(String[] m) {
		new AccordDismissSyncModule().start();
	}

}

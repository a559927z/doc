package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.talentProfile.Source360ReportETL;
import net.chinahrd.etl.impl.talentProfile.Source360TimeETL;


/**
 * 人才剖象
 * @author zhiwei
 * @time 2017年5月16日下午3:27:04
 * @version 20172017年5月16日
 */
public class TalentProfitModule extends ModuleThread {

	private Source360TimeETL jobchangeETL = new Source360TimeETL();
	private Source360ReportETL orgChangeETL = new Source360ReportETL();

	@Override
	public void execute() {
		jobchangeETL.execute();
		orgChangeETL.execute();
	}

	public static void main(String[] d) {
		new TalentProfitModule().start();
	}

}

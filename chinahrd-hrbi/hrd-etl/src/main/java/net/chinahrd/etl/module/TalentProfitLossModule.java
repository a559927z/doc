package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.talentProfitLoss.SourceJobChangeETL;
import net.chinahrd.etl.impl.talentProfitLoss.SourceOrganizationChangeETL;

/**
 * 人才损益
 * @author zhiwei
 * @time 2017年5月16日下午3:27:04
 * @version 20172017年5月16日
 */
public class TalentProfitLossModule extends ModuleThread {

	private SourceJobChangeETL jobchangeETL = new SourceJobChangeETL();
	private SourceOrganizationChangeETL orgChangeETL = new SourceOrganizationChangeETL();

	@Override
	public void execute() {
		jobchangeETL.execute();
		orgChangeETL.execute();
	}

	public static void main(String[] d) {
		new TalentProfitLossModule().start();
	}

}

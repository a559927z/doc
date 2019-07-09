package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.salesBoard.SourceSalesAbilityETL;
import net.chinahrd.etl.impl.salesBoard.SourceSalesDetailETL;
import net.chinahrd.etl.impl.salesBoard.SourceSalesEmpETL;
import net.chinahrd.etl.impl.salesBoard.SourceSalesEmpRankETL;
import net.chinahrd.etl.impl.salesBoard.SourceSalesEmpTargetETL;
import net.chinahrd.etl.impl.salesBoard.SourceSalesOrgTargetETL;
import net.chinahrd.etl.impl.salesBoard.SourceSalesProTargetETL;
import net.chinahrd.etl.impl.salesBoard.SourceSalesTeamRankETL;
import net.chinahrd.etl.impl.salesBoard.SourceSalesTeamTargetETL;

/**
 * 销售看板
 * @author zhiwei
 * @time 2017年5月24日下午4:44:59
 * @version 20172017年5月24日
 */
public class SalesBoardSyncModule extends ModuleThread {

	@Override
	protected void execute() {
		new SourceSalesEmpETL().execute();
		new SourceSalesTeamTargetETL().execute();
		new SourceSalesProTargetETL().execute();
		new SourceSalesOrgTargetETL().execute();
		new SourceSalesEmpTargetETL().execute();
		new SourceSalesEmpRankETL().execute();
		new SourceSalesTeamRankETL().execute();
		new SourceSalesAbilityETL().execute();
		new SourceSalesDetailETL().execute();
	}

	public static void main(String... strings ){
		new SalesBoardSyncModule().start();
	}
}

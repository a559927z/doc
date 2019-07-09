/**
*net.chinahrd.etl.module
*/
package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.salaryBoard.SourcePayETL;
import net.chinahrd.etl.impl.salaryBoard.SourcePayValueETL;
import net.chinahrd.etl.impl.salaryBoard.SourceProfessionQuantileRelationETL;
import net.chinahrd.etl.impl.salaryBoard.SourceSalaryETL;
import net.chinahrd.etl.impl.salaryBoard.SourceShareHoldingETL;
import net.chinahrd.etl.impl.salaryBoard.SoureWelfareCpmETL;
import net.chinahrd.etl.impl.salaryBoard.SoureWelfareNfbETL;
import net.chinahrd.etl.impl.salaryBoard.SoureWelfareUncpmETL;

/**
 * 薪酬看板
 * @author htpeng
 *2017年5月18日下午5:34:20
 */
public class SalaryBoardSyncModule extends ModuleThread {


	@Override
	protected void execute() {
		//薪酬费用
		new SourcePayETL().execute();
		//薪酬预算
		new SourcePayValueETL().execute();
		//行业分位
		new SourceProfessionQuantileRelationETL().execute();
		//工资明细
		new SourceSalaryETL().execute();
		//持股明细
		new SourceShareHoldingETL().execute();
		//固定福利明细
		new SoureWelfareCpmETL().execute();
		//企业福利明细(货币)
		new SoureWelfareNfbETL().execute();
		//企业福利明细(非货币)
		new SoureWelfareUncpmETL().execute();
	}

}

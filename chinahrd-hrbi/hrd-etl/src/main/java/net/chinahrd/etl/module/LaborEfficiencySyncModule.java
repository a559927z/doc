/**
*net.chinahrd.etl.module
*/
package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.laborEfficiency.SourceEmpAttendAnceDayEtl;
import net.chinahrd.etl.impl.laborEfficiency.SourceEmpOtherDayETL;
import net.chinahrd.etl.impl.laborEfficiency.SourceEmpOvertimeDayETL;

/**
 * 劳动力效能指标
 * 
 * @author jxzhang on 2017年5月16日
 * @Verdion 1.0 版本
 */
public class LaborEfficiencySyncModule extends ModuleThread {

	@Override
	public void execute() {

		// 考勤表-日
		new SourceEmpAttendAnceDayEtl().execute();

		// 其它考勤表-日
		new SourceEmpOtherDayETL().execute();

		// 加班记录-日
		new SourceEmpOvertimeDayETL().execute();
	}

	public static void main(String[] d) {
		new LaborEfficiencySyncModule().start();
	}
}

/**
*net.chinahrd.etl.module
*/
package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
//import net.chinahrd.etl.impl.empAttendance.AtdEtl;

/**
 * 员工考勤指标-同步
 * 
 * @author htpeng 2017年4月12日下午12:40:28
 */
public class EmpAttendanceSyncModule extends ModuleThread {

	@Override
	public void execute() {
		// 考勤机-打卡机(zrw)
//		new AtdEtl().execute();

	}

	public static void main(String[] d) {
		new EmpAttendanceSyncModule().start();
	}
}

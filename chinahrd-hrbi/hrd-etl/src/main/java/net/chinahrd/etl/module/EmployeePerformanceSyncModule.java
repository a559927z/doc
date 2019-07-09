/**
*net.chinahrd.etl.module
*/
package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.employeePerformance.SourcePerformanceChangeETL;

/**
 * 员工绩效
 * 
 * @author jxzhang on 2017年5月10日
 * @Verdion 1.0 版本
 */
public class EmployeePerformanceSyncModule extends ModuleThread {

	@Override
	public void execute() {
		// 绩效信息
		new SourcePerformanceChangeETL().execute();
	}

	public static void main(String[] d) {
		new EmployeePerformanceSyncModule().start();
	}
}

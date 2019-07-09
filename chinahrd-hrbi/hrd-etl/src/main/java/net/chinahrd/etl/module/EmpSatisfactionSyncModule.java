/**
*net.chinahrd.etl.module
*/
package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.empSatisfaction.SourceDedicatGenreScoreETL;
import net.chinahrd.etl.impl.empSatisfaction.SourceDedicatOrganETL;
import net.chinahrd.etl.impl.empSatisfaction.SourceSatfacGenreScoreETL;
import net.chinahrd.etl.impl.empSatisfaction.SourceSatfacOrganETL;

/**
 * 员工忠诚度满意度(员工满意度)
 * 
 * @author jxzhang on 2017年5月12日
 * @Verdion 1.0 版本
 */
public class EmpSatisfactionSyncModule extends ModuleThread {

	@Override
	public void execute() {
		// 敬业度评分
		new SourceDedicatGenreScoreETL().execute();
		// 满意度评分
		new SourceSatfacGenreScoreETL().execute();
		// 满意度机构评分
		new SourceSatfacOrganETL().execute();
		// 敬业度机构评分
		new SourceDedicatOrganETL().execute();
		
	}

	public static void main(String[] d) {
		new EmpSatisfactionSyncModule().start();
	}
}

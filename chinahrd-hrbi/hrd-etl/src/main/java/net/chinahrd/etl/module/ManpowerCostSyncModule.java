/**
*net.chinahrd.etl.module
*/
package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.manpowerCost.SourceManpowerCostETL;
import net.chinahrd.etl.impl.manpowerCost.SourceManpowerCostItemETL;
import net.chinahrd.etl.impl.manpowerCost.SourceManpowerCostValueETL;

/**
 * 人力成本-同步
 * 
 * @author htpeng 2017年5月16日下午6:32:35
 */
public class ManpowerCostSyncModule extends ModuleThread {

	@Override
	protected void execute() {
		// 人力成本结构
		new SourceManpowerCostItemETL().execute();
		// 人力成本
		new SourceManpowerCostETL().execute();
		// 人力成本结构
		new SourceManpowerCostValueETL().execute();
	}

}

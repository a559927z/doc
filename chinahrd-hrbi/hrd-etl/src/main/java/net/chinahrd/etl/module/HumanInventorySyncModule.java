/**
*net.chinahrd.etl.module
*/
package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.humanInventory.SourceProjectCostETL;
import net.chinahrd.etl.impl.humanInventory.SourceProjectETL;
import net.chinahrd.etl.impl.humanInventory.SourceProjectInputDetailETL;
import net.chinahrd.etl.impl.humanInventory.SourceProjectManpowerETL;
import net.chinahrd.etl.impl.humanInventory.SourceProjectMaxvalueETL;

/**
 * 项目人力盘点
 * 
 * @author jxzhang on 2017年5月12日
 * @Verdion 1.0 版本
 */
public class HumanInventorySyncModule extends ModuleThread {

	@Override
	public void execute() {
		// 项目
		new SourceProjectETL().execute();
		// 项目人力明细
		new SourceProjectManpowerETL().execute();
		// 项目投入费用明细
		new SourceProjectInputDetailETL().execute();
		// 业务表-项目费用明细
		new SourceProjectCostETL().execute();

		// 项目最大负荷数
		new SourceProjectMaxvalueETL().execute();
	}

	public static void main(String[] d) {
		new HumanInventorySyncModule().start();
	}
}

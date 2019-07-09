/**
*net.chinahrd.etl.module
*/
package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.teamImg.SourceEmpPersonalityETL;
/**
 * 团队画像-同步
 * @author htpeng
 *2017年5月16日下午6:32:35
 */
public class TeamImgSyncModule extends ModuleThread {

	
	@Override
	protected void execute() {
		//员工星座
		new SourceEmpPersonalityETL().execute();
	}

}

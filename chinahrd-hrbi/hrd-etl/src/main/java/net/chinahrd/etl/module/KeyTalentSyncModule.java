/**
*net.chinahrd.etl.module
*/
package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.keyTalent.SourceKeyTalentEtl;

/**
 * @author htpeng
 *2017年5月26日下午6:30:32
 */
public class KeyTalentSyncModule extends ModuleThread{


	@Override
	protected void execute() {
		new SourceKeyTalentEtl().execute();
	}

}

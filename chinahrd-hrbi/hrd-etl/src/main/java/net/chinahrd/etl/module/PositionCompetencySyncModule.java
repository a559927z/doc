/**
*net.chinahrd.etl.module
*/
package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.positionCompetency.SourceEmpPqRelationETL;
import net.chinahrd.etl.impl.positionCompetency.SourcePositionQualityETL;

/**
 * 岗位胜任度-同步
 * @author htpeng
 *2017年5月16日下午6:32:35
 */
public class PositionCompetencySyncModule extends ModuleThread {

	
	@Override
	protected void execute() {
		//人员得分
		new SourceEmpPqRelationETL().execute();
		//岗位能力素质
		new SourcePositionQualityETL().execute();
	}

}

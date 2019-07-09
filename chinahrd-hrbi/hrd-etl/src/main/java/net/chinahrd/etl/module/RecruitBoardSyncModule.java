/**
*net.chinahrd.etl.module
*/
package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.recruitBoard.SourceOutTalentETL;
import net.chinahrd.etl.impl.recruitBoard.SourceRecruitChannelETL;
import net.chinahrd.etl.impl.recruitBoard.SourceRecruitPublicETL;
import net.chinahrd.etl.impl.recruitBoard.SourceRecruitResultETL;
import net.chinahrd.etl.impl.recruitBoard.SourceRecruitValueETL;

/**
 * 招聘看板
 * @author htpeng
 *2017年5月19日上午10:39:34
 */
public class RecruitBoardSyncModule extends ModuleThread {

	
	@Override
	protected void execute() {
		//招聘渠道
		new SourceRecruitChannelETL().execute();
		//招聘发布
		new SourceRecruitPublicETL().execute();
		//招聘结果
		new SourceRecruitResultETL().execute();
		//招聘年度费用（年）
		new SourceRecruitValueETL().execute();
		// 外部人才
		new SourceOutTalentETL().execute();
	}

}

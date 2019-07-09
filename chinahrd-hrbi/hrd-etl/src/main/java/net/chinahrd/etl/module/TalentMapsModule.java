package net.chinahrd.etl.module;

import net.chinahrd.etl.ModuleThread;
import net.chinahrd.etl.impl.talentMaps.SourceAbilityChangeETL;
import net.chinahrd.etl.impl.talentMaps.SourceMapManagementETL;
import net.chinahrd.etl.impl.talentMaps.SourceMapPublicETL;
import net.chinahrd.etl.impl.talentMaps.SourceMapTalentETL;
import net.chinahrd.etl.impl.talentMaps.SourceMapTalentInfoETL;
import net.chinahrd.etl.impl.talentMaps.SourceMapTalentResultETL;
import net.chinahrd.etl.impl.talentMaps.SourceMapTeamETL;


/**
 * 人才地图
 * @author zhiwei
 * @time 2017年5月16日下午3:34:52
 * @version 20172017年5月16日
 */
public class TalentMapsModule extends ModuleThread {

	private SourceMapManagementETL    map_management    = new SourceMapManagementETL();
	private SourceMapTalentResultETL  map_talent_result = new SourceMapTalentResultETL();
	private SourceMapTalentInfoETL    map_talent_info   = new SourceMapTalentInfoETL();
	private SourceAbilityChangeETL    ability_change    = new SourceAbilityChangeETL();
	private SourceMapTeamETL          map_team          = new SourceMapTeamETL();
	private SourceMapTalentETL        map_talent        = new SourceMapTalentETL();
	private SourceMapPublicETL        map_public        = new SourceMapPublicETL();


	@Override
	public void execute() {
		map_management   .execute();
		map_talent_result.execute();
		map_talent_info  .execute();
		ability_change   .execute();
		map_team         .execute();
		map_talent       .execute();
		map_public       .execute();
	}

	public static void main(String[] d) {
		new TalentMapsModule().start();
	}

}

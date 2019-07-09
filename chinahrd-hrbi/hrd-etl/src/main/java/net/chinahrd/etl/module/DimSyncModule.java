package net.chinahrd.etl.module;

import net.chinahrd.etl.Etl;
import net.chinahrd.etl.Module;
import net.chinahrd.etl.impl.allDim.SourceDimAbilityETL;
import net.chinahrd.etl.impl.allDim.SourceDimAbilityLvETL;
import net.chinahrd.etl.impl.allDim.SourceDimAbilityNumberETL;
import net.chinahrd.etl.impl.allDim.SourceDimCertificateInfoETL;
import net.chinahrd.etl.impl.allDim.SourceDimChangeTypeETL;
import net.chinahrd.etl.impl.allDim.SourceDimChannelETL;
import net.chinahrd.etl.impl.allDim.SourceDimCheckworkTypeETL;
import net.chinahrd.etl.impl.allDim.SourceDimCityETL;
import net.chinahrd.etl.impl.allDim.SourceDimCourseETL;
import net.chinahrd.etl.impl.allDim.SourceDimCourseTypeETL;
import net.chinahrd.etl.impl.allDim.SourceDimDedicatGenreETL;
import net.chinahrd.etl.impl.allDim.SourceDimDismissionWeekETL;
import net.chinahrd.etl.impl.allDim.SourceDimEncouragesETL;
import net.chinahrd.etl.impl.allDim.SourceDimJobTitleETL;
import net.chinahrd.etl.impl.allDim.SourceDimKeyTalentTypeETL;
import net.chinahrd.etl.impl.allDim.SourceDimOrganizationETL;
import net.chinahrd.etl.impl.allDim.SourceDimOrganizationType;
import net.chinahrd.etl.impl.allDim.SourceDimPerformanceETL;
import net.chinahrd.etl.impl.allDim.SourceDimPopulationETL;
import net.chinahrd.etl.impl.allDim.SourceDimPositionETL;
import net.chinahrd.etl.impl.allDim.SourceDimProfessionETL;
import net.chinahrd.etl.impl.allDim.SourceDimProjectInputTypeETL;
import net.chinahrd.etl.impl.allDim.SourceDimProjectTypeETL;
import net.chinahrd.etl.impl.allDim.SourceDimProvinceETL;
import net.chinahrd.etl.impl.allDim.SourceDimQualityETL;
import net.chinahrd.etl.impl.allDim.SourceDimRunOffETL;
import net.chinahrd.etl.impl.allDim.SourceDimSalesProductETL;
import net.chinahrd.etl.impl.allDim.SourceDimSalesTeamETL;
import net.chinahrd.etl.impl.allDim.SourceDimSatfacGenreETL;
import net.chinahrd.etl.impl.allDim.SourceDimSeparationRiskETL;
import net.chinahrd.etl.impl.allDim.SourceDimSequenceETL;
import net.chinahrd.etl.impl.allDim.SourceDimSequenceSubETL;
import net.chinahrd.etl.impl.allDim.SourceDimStructureETL;
import net.chinahrd.etl.impl.allDim.SourceMatchingSchoolETL;
import net.chinahrd.etl.impl.allDim.SourceMatchingScoreETL;

/**
 * AllDim
 *
 * @author zhiwei
 * @time 2017年4月24日下午12:53:46
 * @version 20172017年4月24日
 */
public class DimSyncModule implements Module {

	 // 机构类型
	 private Etl sourceDimOrganizationTypeETL = new SourceDimOrganizationType();
	 //机构
	 private Etl sourceDimOrganizationETL = new SourceDimOrganizationETL();

	 // 主、子序列；职衔；能力层职； 子职；人群范围
	 private Etl sourceDimSequenceETL         = new SourceDimSequenceETL();
	 private Etl sourceDimSequenceSubETL      = new SourceDimSequenceSubETL();
	 private Etl sourceDimJobTitleETL         = new SourceDimJobTitleETL();
	 private Etl sourceDimAbilityETL          = new SourceDimAbilityETL();
	 private Etl sourceDimAbilityLvETL        = new SourceDimAbilityLvETL();
	 private Etl sourceDimPositionETL         = new SourceDimPositionETL();
	 private Etl sourceDimKeyTalentTypeETL    = new SourceDimKeyTalentTypeETL();
	 private Etl sourceDimCourseETL           = new SourceDimCourseETL();
	 private Etl sourceDimCourseTypeETL       = new SourceDimCourseTypeETL();
	 private Etl sourceDimStructureETL        = new SourceDimStructureETL();
	 private Etl sourceDimProjectTypeETL      = new SourceDimProjectTypeETL();
	 private Etl sourceDimProjectInputTypeETL = new SourceDimProjectInputTypeETL();
	 private Etl sourceDimChangeTypeETL       = new SourceDimChangeTypeETL();
	 private Etl sourceDimChannelETL          = new SourceDimChannelETL();
	 private Etl sourceDimDismissionWeekETL   = new SourceDimDismissionWeekETL();
	 private Etl sourceDimEncouragesETL       = new SourceDimEncouragesETL();
	 private Etl sourceDimPerformanceETL      = new SourceDimPerformanceETL ();
	 private Etl sourceDimPopulationETL       = new SourceDimPopulationETL();
	 private Etl sourceDimQualityETL          = new SourceDimQualityETL();
	 private Etl sourceDimCheckWorkTypeETL    = new SourceDimCheckworkTypeETL();
	 private Etl sourceDimCertificateInfoETL  = new SourceDimCertificateInfoETL();

	 private Etl sourceDimSalesTeamETL        = new SourceDimSalesTeamETL();
	 private Etl sourceDimSalesProductETL     = new SourceDimSalesProductETL();
	 private Etl sourceDimSeparationRiskETL   = new SourceDimSeparationRiskETL();
	 private Etl sourceDimRunOffETL           = new SourceDimRunOffETL();
	 private Etl sourceDimSatfacGenreETL      = new SourceDimSatfacGenreETL();

	 private Etl sourceDimDedicatGenreETL     = new SourceDimDedicatGenreETL();
	 // 行业
	 private Etl sourceDimProfessionETL       = new SourceDimProfessionETL();
	 // 省
	 private Etl sourceDimProvinceETL         = new SourceDimProvinceETL();
	 // 市
	 private Etl sourceDimCityETL             = new SourceDimCityETL();
	 // 学校
	 private Etl sourceMatchingSchoolETL      = new SourceMatchingSchoolETL();
	 // 分数映射
	 private Etl sourceMatchingSoureETL      = new SourceMatchingScoreETL();
	 // 员工能力记录
	 private Etl sourceDimAbilityNumberETL = new SourceDimAbilityNumberETL();

	public void execute() {
		sourceDimOrganizationTypeETL.execute();
		sourceDimOrganizationETL.execute();
		sourceDimSequenceETL.execute();
		sourceDimSequenceSubETL.execute();
		sourceDimJobTitleETL.execute();
		sourceDimAbilityETL.execute();
		sourceDimAbilityLvETL.execute();
		sourceDimPositionETL.execute();
		sourceDimKeyTalentTypeETL.execute();
		sourceDimCourseETL.execute();
		sourceDimCourseTypeETL.execute();
		sourceDimStructureETL.execute();
		sourceDimProjectTypeETL.execute();
		sourceDimProjectInputTypeETL.execute();
		sourceDimChangeTypeETL    .execute();
		sourceDimChannelETL       .execute();
		sourceDimDismissionWeekETL.execute();
		sourceDimEncouragesETL    .execute();
		sourceDimPerformanceETL   .execute();
		sourceDimPopulationETL    .execute();
		sourceDimQualityETL       .execute();
		sourceDimCheckWorkTypeETL .execute();
		sourceDimCertificateInfoETL.execute();
		sourceDimSalesTeamETL     .execute();
		sourceDimSalesProductETL  .execute();
		sourceDimSeparationRiskETL.execute();
		sourceDimRunOffETL        .execute();
		sourceDimSatfacGenreETL   .execute();
		sourceDimDedicatGenreETL  .execute();
		sourceDimProfessionETL    .execute();
		sourceDimProvinceETL      .execute();
		sourceDimCityETL          .execute();
		sourceMatchingSchoolETL   .execute();
		sourceMatchingSoureETL   .execute();
		sourceDimAbilityNumberETL .execute();

//		new SourceDimOrganizationType().execute();
//		new SourceDimOrganizationETL().execute();
		
//		new Thread(new SourceDimSequenceETL()).start();
//		new Thread(new SourceDimSequenceSubETL()).start();
//		new Thread(new SourceDimJobTitleETL()).start();
//		new Thread(new SourceDimAbilityETL()).start();
//		new Thread(new SourceDimAbilityLvETL()).start();
//		new Thread(new SourceDimPositionETL()).start();
//		new Thread(new SourceDimKeyTalentTypeETL()).start();
//		new Thread(new SourceDimCourseETL()).start();
//		new Thread(new SourceDimCourseTypeETL()).start();
//		new Thread(new SourceDimStructureETL()).start();
//		new Thread(new SourceDimProjectTypeETL()).start();
//		new Thread(new SourceDimProjectInputTypeETL()).start();
//		new Thread(new SourceDimChangeTypeETL()).start();
//		new Thread(new SourceDimChannelETL()).start();
//		new Thread(new SourceDimDismissionWeekETL()).start();
//		new Thread(new SourceDimEncouragesETL()).start();
//		new Thread(new SourceDimPerformanceETL()).start();
//		new Thread(new SourceDimPopulationETL()).start();
//		new Thread(new SourceDimQualityETL()).start();
//		new Thread(new SourceDimCheckworkTypeETL()).start();
//		new Thread(new SourceDimCertificateInfoETL()).start();
//		new Thread(new SourceDimSalesTeamETL()).start();
//		new Thread(new SourceDimSalesProductETL()).start();
//		new Thread(new SourceDimSeparationRiskETL()).start();
//		new Thread(new SourceDimRunOffETL()).start();
//		new Thread(new SourceDimSatfacGenreETL()).start();
//		new Thread(new SourceDimDedicatGenreETL()).start();
//		new Thread(new SourceDimProfessionETL()).start();
//		new Thread(new SourceDimProvinceETL()).start();
//		new Thread(new SourceDimCityETL()).start();
//		new Thread(new SourceMatchingSchoolETL()).start();
//		new Thread(new SourceMatchingSourceETL()).start();
//		new Thread(new SourceDimAbilityNumberETL()).start();
	}

	public static void main(String[] d) {
		new DimSyncModule().start();
	}

	@Override
	public void start() {
		new DimSyncModule().execute();
	}

}

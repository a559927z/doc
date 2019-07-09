/**
*net.chinahrd.eis.permission
*/
package net.chinahrd.eis.permission;

/**
 * @author htpeng
 *2017年1月9日下午2:48:50
 */

import java.io.File;

import org.springframework.stereotype.Component;

import net.chinahrd.core.web.eis.license.util.Base64Utils;
import net.chinahrd.core.web.eis.license.util.RSAUtils;
import net.chinahrd.core.web.eis.license.LicenseConfig;
import net.chinahrd.core.web.eis.license.LicenseDeveloperMode;
@Component("licenseDeveloperMode")
public class LicenseDeveloperModeImpl implements LicenseDeveloperMode{
	private static final String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKJQ5bURI9hjALVUw9FV/bWfFU0Q"
			+ "3AsKgIyLl5Bgu/00GFEO26dTE4SEFcNfSI6hk9jMP3xvCPHGM41NxChVwy8292ne9bzuAwNMYXSK"
			+ "LLW9UNNmjhTOvUbq4ji3u+DSFXesTFZ+8FAF89OrcDqWfXo5nTLq6i5ZWbwObAcoZSN3AgMBAAEC"
			+ "gYEAhwAZzZtP4O2W7TpvE3Ed7y09HjfjjBtUB5EioiaStGnbv69H6cci+JiTMUfSpS6mNS7Lrsb6"
			+ "/XwH7IK9n36VMx+LkpoLN59gmZqy1o1xb4J4z+ptZIWTXxPv05TPBxIZnScF54uupBEgJlY8MmkL"
			+ "S06rQOHHEM4y25MvYVd85IECQQDd1cTr8SAbcvn+2vOK3g9bm2PcHFTSAjgmLWDHVIoQ9sLSDlGO"
			+ "9Hm13vnCeX+CxfbSTUaohKtNYc0W/VRwpk2RAkEAu1B8s5sevGTSydnCM/cQZJAPBUzXBiSiHK//"
			+ "ua+XX6wrmOh+BqrQNVj9zJ9L7oy3c3RyCAJYlXw8kBgg8SB8hwJASwXEt1aw2RelXH+H5er7nquK"
			+ "tiIOvYdqnd6y4DbDCsGpWYjmJEah8tQZ+59DdGKm+rCPuOZFWMI+rgu2xvI3QQJBAJvVs1lNNT3w"
			+ "93jcxHetFSNEwmpbagx/T6tkNPFLhzkeKMs2qRps43luhv/Ei0x0H29bMj89jGsZB92tZUKWD2MC"
			+ "QH0EfupFSKrZylVuxMGgdVtxMm/dyo3TWkAsTMB/EOOcskKKNWoECkEzPQYBDo/akjmXPBXg5iyP"
			+ "Z/7fT0T25+Y=";

	public  void execute(String path){
		try {
//			String empnum=PropertiesUtil.getProperty("EMPNUM").trim();
			String str="SN:"+LicenseConfig.sequenceService.getSequence()+"\n";
			 str+="EMPNUM:10000\n";
			 str+="SV:emp/list,"
			 		+ "perChange/toPerChangeView,"
			 		+ "role/list,"
			 		+ "user/list,"
			 		+ "common/noAuthor,"
			 		+ "manpowerCost/toManpowerCostView,"
			 		+ "talentContrast/toTalentContrastView,"
			 		+ "sequenceCount/toSequenceCountView,"
			 		+ "accordDismiss/toAccordDismissView,"
			 		+ "keyTalents/toKeyTalentsView,"
			 		+ "dismissRisk/toDismissRiskView,"
			 		+ "function/list,"
			 		+ "perBenefit/toPerBenefitView,"
			 		+ "talentProfile/toTalentProfileView,"
			 		+ "importData/home,"
			 		+ "manageHome/toTeamImgView,"
			 		+ "organ/list,"
			 		+ "orgChart/toOrgView,"
			 		+ "monthlyReport/home,"
			 		+ "exponents/home,"
					+ "talentStructure/toTalentStructureView,"
					+ "mobile/talentContrast/toTalentContrastView,"
					+ "mobile/accordDismiss/toAccordDismissView,"
			 		+ "talentSearch/toTalentSearchView,"
			 		+ "config/toConfigView,"
			 		+ "talentSearch/toTalentSearchView,"
			 		+ "mobile/perBenefit/toPerBenefitView,"
			 		+ "humanInventory/toHumanInventoryView,"
			 		+ "mobile/talentStructure/toTalentStructureView,"
			 		+ "trainBoard/toTrainBoardView,"
			 		+ "salaryBoard/toSalaryBoardView,"
					+ "recruitBoard/toRecruitBoardView,"
			 		+ "empSatisfaction/toEmpSatisfactionView,"
					+ "talentProfitLoss/toTalentProfitLossView,"
					+ "laborEfficiency/toLaborEfficiencyView,"
					+ "promotionBoard/toPromotionBoardView,"
					+ "monthReport/toMonthReportView,"
					+ "salesBoard/toSalesBoardView,"
					+ "talentMaps/toTalentMapsView,"
					+ "positionCompetency/toPositionCompetencyView"
			 		+ "\n";
			byte[] encodedData = RSAUtils.encryptByPrivateKey(str.getBytes(), privateKey);
			encodedData = RSAUtils.encryptByPrivateKey(encodedData, privateKey);
			String folderName= path+File.separator+LicenseConfig.folderName;
			String fileName=folderName+File.separator+ LicenseConfig.filename;
			System.out.println(fileName);
			
			File folder = new File(folderName);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			Base64Utils.byteArrayToFile(encodedData, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


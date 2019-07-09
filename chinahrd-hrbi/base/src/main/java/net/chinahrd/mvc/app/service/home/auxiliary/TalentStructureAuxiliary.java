/**
*net.chinahrd.biz.paper.mobile.service.home.auxiliary
*/
package net.chinahrd.mvc.app.service.home.auxiliary;

import java.util.List;

import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;
import net.chinahrd.entity.dto.app.talent.structure.TalentstructureDto;
import net.chinahrd.mvc.app.dao.home.MobileHomeDao;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliary;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliaryModel;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;

/**
 * @author htpeng
 *2016年7月14日下午5:21:47
 */
public class TalentStructureAuxiliary implements MobileHomeAuxiliary{

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.util.MobileHomeAuxiliary#getData(net.chinahrd.biz.paper.dto.app.KanbanConfigMobileDto, net.chinahrd.biz.paper.mobile.dao.MobileHomeDao)
	 */
	@Override
	public void getData(MobileHomeAuxiliaryModel model,KanbanConfigMobileDto kanbanConfigMobileDto,
			MobileHomeDao mobileHomeDao) {
		// TODO Auto-generated method stub

		List<Integer> personTypeKey = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RLCB_personType);
		
		TalentstructureDto dto= mobileHomeDao.findBudgetAnalyse(model.getOrganId(), model.getCustomerId(), DateUtil.getDBCurdate(), personTypeKey);
		kanbanConfigMobileDto.setTotalDate("可用编制");
		
		if(dto==null){
			kanbanConfigMobileDto.setTotalValue("0");
			kanbanConfigMobileDto.setBackgroundColor(NORMAL_BACKGROUD_CORLOR);
			kanbanConfigMobileDto.setImgColor(NORMAL_CORLOR);
//			kanbanConfigMobileDto.setTextColor(NORMAL_TEXT_CORLOR);
		}
		Double normal = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.RLJG_NORMAL);
		Double risk = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.RLJG_RISK);
		int usableEmpCount=(int)(dto.getNumber()-dto.getEmpCount());
		double rate=dto.getEmpCount()/dto.getNumber();
		kanbanConfigMobileDto.setTotalValue(usableEmpCount+"");
		if(rate<normal){
			kanbanConfigMobileDto.setBackgroundColor(NORMAL_BACKGROUD_CORLOR);
			kanbanConfigMobileDto.setImgColor(NORMAL_CORLOR);
//			kanbanConfigMobileDto.setTextColor(NORMAL_TEXT_CORLOR);
		}else if(rate>risk){
			kanbanConfigMobileDto.setBackgroundColor(RISK_BACKGROUD_CORLOR);
			kanbanConfigMobileDto.setImgColor(RISK_CORLOR);
//			kanbanConfigMobileDto.setTextColor(RISK_TEXT_CORLOR);
		}else{
			kanbanConfigMobileDto.setBackgroundColor(WARN_BACKGROUD_CORLOR);
			kanbanConfigMobileDto.setImgColor(WARN_CORLOR);
//			kanbanConfigMobileDto.setTextColor(WARN_TEXT_CORLOR);
		}
	}

}

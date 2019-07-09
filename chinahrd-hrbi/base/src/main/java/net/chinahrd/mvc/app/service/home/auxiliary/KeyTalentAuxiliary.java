/**
*net.chinahrd.biz.paper.mobile.service.home.auxiliary
*/
package net.chinahrd.mvc.app.service.home.auxiliary;

import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;
import net.chinahrd.mvc.app.dao.home.MobileHomeDao;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliary;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliaryModel;

/**
 * @author htpeng
 *2016年7月14日下午5:21:47
 */
public class KeyTalentAuxiliary implements MobileHomeAuxiliary{

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.util.MobileHomeAuxiliary#getData(net.chinahrd.biz.paper.dto.app.KanbanConfigMobileDto, net.chinahrd.biz.paper.mobile.dao.MobileHomeDao)
	 */
	@Override
	public void getData(MobileHomeAuxiliaryModel model,KanbanConfigMobileDto kanbanConfigMobileDto,
			MobileHomeDao mobileHomeDao) {
		// TODO Auto-generated method stub
		int count=mobileHomeDao.queryRunoffRiskCount(model.getCustomerId(),model.getOrganList());
		kanbanConfigMobileDto.setTotalDate("离职预警");
		kanbanConfigMobileDto.setTotalValue(count+"");
		kanbanConfigMobileDto.setBackgroundColor(RISK_BACKGROUD_CORLOR);
		kanbanConfigMobileDto.setImgColor(RISK_CORLOR);
//		kanbanConfigMobileDto.setTextColor(RISK_TEXT_CORLOR);
	}

}

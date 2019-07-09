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
public class PerBenefitAuxiliary implements MobileHomeAuxiliary{

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.util.MobileHomeAuxiliary#getData(net.chinahrd.biz.paper.dto.app.KanbanConfigMobileDto, net.chinahrd.biz.paper.mobile.dao.MobileHomeDao)
	 */
	@Override
	public void getData(MobileHomeAuxiliaryModel model,KanbanConfigMobileDto kanbanConfigMobileDto,
			MobileHomeDao mobileHomeDao) {
		// TODO Auto-generated method stub
		String lastMonth = mobileHomeDao.queryBenfitLastMonth(model.getCustomerId());
		String year=lastMonth.substring(0,4);
		String time=year+"."+lastMonth.substring(4,6);
		Double benefit=mobileHomeDao.queryBenefitValue(model.getCustomerId(), model.getOrganId(), year);
		if(benefit==null){
			benefit=0.0;
		}
		kanbanConfigMobileDto.setTotalDate(time);
		kanbanConfigMobileDto.setTotalValue(benefit+"");
		kanbanConfigMobileDto.setBackgroundColor(NORMAL_BACKGROUD_CORLOR);
		kanbanConfigMobileDto.setImgColor(NORMAL_CORLOR);
//		kanbanConfigMobileDto.setTextColor(NORMAL_TEXT_CORLOR);
	}
	
}

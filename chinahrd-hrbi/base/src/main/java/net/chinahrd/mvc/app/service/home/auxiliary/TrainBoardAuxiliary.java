/**
*net.chinahrd.biz.paper.mobile.service.home.auxiliary
*/
package net.chinahrd.mvc.app.service.home.auxiliary;

import java.text.DecimalFormat;
import java.util.Calendar;

import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;
import net.chinahrd.mvc.app.dao.home.MobileHomeDao;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliary;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliaryModel;
import net.chinahrd.utils.DateUtil;

/**
 * @author htpeng
 *2016年7月14日下午5:21:47
 */
public class TrainBoardAuxiliary implements MobileHomeAuxiliary{

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.util.MobileHomeAuxiliary#getData(net.chinahrd.biz.paper.dto.app.KanbanConfigMobileDto, net.chinahrd.biz.paper.mobile.dao.MobileHomeDao)
	 */
	@Override
	public void getData(MobileHomeAuxiliaryModel model,KanbanConfigMobileDto kanbanConfigMobileDto,
			MobileHomeDao mobileHomeDao) {
		// TODO Auto-generated method stub
		Double rate=mobileHomeDao.queryTrainRate(model.getCustomerId(),model.getOrganId(),getYear());
		if(rate==null){
			rate=0.0;
		}
		kanbanConfigMobileDto.setTotalDate("费用执行率");
		DecimalFormat df = new DecimalFormat("#0.0#");  
		kanbanConfigMobileDto.setTotalValue(df.format(rate*100));
		kanbanConfigMobileDto.setBackgroundColor(NORMAL_BACKGROUD_CORLOR);
		kanbanConfigMobileDto.setImgColor(NORMAL_CORLOR);
//		kanbanConfigMobileDto.setTextColor(NORMAL_TEXT_CORLOR);
	}
	private int getYear(){
		Calendar cal=Calendar.getInstance();
    	cal.setTime(DateUtil.getDate());
    	return cal.get(Calendar.YEAR);
	}
}

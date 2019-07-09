/**
*net.chinahrd.biz.paper.mobile.service.home.auxiliary
*/
package net.chinahrd.mvc.app.service.home.auxiliary;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;
import net.chinahrd.mvc.app.dao.home.MobileHomeDao;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliary;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliaryModel;
import net.chinahrd.utils.DateUtil;

/**
 * @author htpeng
 *2016年7月14日下午5:21:47
 */
public class ManpowerAuxiliary implements MobileHomeAuxiliary{

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.util.MobileHomeAuxiliary#getData(net.chinahrd.biz.paper.dto.app.KanbanConfigMobileDto, net.chinahrd.biz.paper.mobile.dao.MobileHomeDao)
	 */
	@Override
	public void getData(MobileHomeAuxiliaryModel model,KanbanConfigMobileDto kanbanConfigMobileDto,
			MobileHomeDao mobileHomeDao) {
		// TODO Auto-generated method stub
		List<Double> list=mobileHomeDao.queryYearTotalAndBudget(model.getCustomerId(),model.getOrganId(),getYear());
		Double budget=0.0;
		Double total=0.0;
		Double rate=0.0;
		Double normal=0.8;
		Double risk=1.0;
		if(list!=null){
			if(list.size()>0){
				budget=list.get(0);
			}
			if(list.size()>1){
				total=list.get(1);
			}
		}
		if(budget==null||budget==0){
			rate=0.0;
		}else{
			rate=total/budget;
		}
		kanbanConfigMobileDto.setTotalDate("成本执行率");
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
		DecimalFormat df =new DecimalFormat("#0.0#");  
		kanbanConfigMobileDto.setTotalValue(df.format(rate*100));
	}
	private int getYear(){
		Calendar cal=Calendar.getInstance();
    	cal.setTime(DateUtil.getDate());
    	return cal.get(Calendar.YEAR);
	}
}

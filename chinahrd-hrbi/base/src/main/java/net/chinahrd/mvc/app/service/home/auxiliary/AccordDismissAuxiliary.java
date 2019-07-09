/**
*net.chinahrd.biz.paper.mobile.service.home.auxiliary
*/
package net.chinahrd.mvc.app.service.home.auxiliary;

import java.util.Calendar;
import java.util.List;

import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;
import net.chinahrd.entity.dto.app.dismiss.DismissTrendDto;
import net.chinahrd.mvc.app.dao.home.MobileHomeDao;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliary;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliaryModel;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;

/**
 * @author htpeng
 *2016年7月14日下午5:21:47
 */
public class AccordDismissAuxiliary implements MobileHomeAuxiliary{

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.util.MobileHomeAuxiliary#getData(net.chinahrd.biz.paper.dto.app.KanbanConfigMobileDto, net.chinahrd.biz.paper.mobile.dao.MobileHomeDao)
	 */
	@Override
	public void getData(MobileHomeAuxiliaryModel model,KanbanConfigMobileDto kanbanConfigMobileDto,
			MobileHomeDao mobileHomeDao) {
        double normal = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.ZDLSL_NORMAL);
        double risk = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.ZDLSL_RISK);
		String time=mobileHomeDao.queryQuarterLastDay(model.getCustomerId());
		List<DismissTrendDto> dtos = mobileHomeDao.queryDisminss(model.getOrganId(), time, model.getCustomerId());
		double quarterBegin = 0, quarterEnd = 0, minMonth = 0, maxMonth = 0, accordCount = 0;
		for (DismissTrendDto dto : dtos) {
			// 累计流失人数
			accordCount = ArithUtil.sum(accordCount, dto.getAccordCount());
			// 计算季度总人数
			double yearMonth = Double.valueOf(dto.getYearMonth());
			if (quarterBegin == 0) {
				quarterBegin = dto.getMonthBegin();
				quarterEnd = dto.getMonthEnd();
				minMonth = yearMonth;
				maxMonth = yearMonth;
				continue;
			}
			if (minMonth > yearMonth) {
				minMonth = yearMonth;
				quarterBegin = dto.getMonthBegin();
				continue;
			}
			if (maxMonth < yearMonth) {
				maxMonth = yearMonth;
				quarterEnd = dto.getMonthEnd();
				continue;
			}
		}
		// 季度总人数
		double monthCount = ArithUtil.div(
				ArithUtil.sum(quarterBegin, quarterEnd), 2, 4);
		// 季度流失率
		double rate = accordCount == 0 ? 0d : ArithUtil.div(accordCount,
				monthCount, 4);
		kanbanConfigMobileDto.setTotalDate(getYearAndQ(time));
		kanbanConfigMobileDto.setTotalValue(rate*100+"");
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

	private String getYearAndQ(String date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(DateUtil.getDate());
    	cal.add(Calendar.DAY_OF_MONTH, -1);
    	int year=cal.get(Calendar.YEAR);
    	int month=cal.get(Calendar.MONTH)+1;
    	String q="Q1";
    	switch (month) {
		case 1:
		case 2:
		case 3:
			q="Q1";
			break;
		case 4:
		case 5:
		case 6:
			q="Q2";
			break;
		case 7:
		case 8:
		case 9:
			q="Q3";
			break;
		case 10:
		case 11:
		case 12:
			q="Q4";
			break;
		default:
			break;
		}
		
		return year+q;

	}
}

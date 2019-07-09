/**
*net.chinahrd.biz.paper.mobile.service.home.auxiliary
*/
package net.chinahrd.mvc.app.service.home.auxiliary;

import java.util.List;

import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;
import net.chinahrd.mvc.app.dao.home.MobileHomeDao;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliary;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliaryModel;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.ConfigKeyUtil;

/**
 * @author htpeng
 *2016年7月14日下午5:21:47
 */
public class PerChangeAuxiliary implements MobileHomeAuxiliary{

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.util.MobileHomeAuxiliary#getData(net.chinahrd.biz.paper.dto.app.KanbanConfigMobileDto, net.chinahrd.biz.paper.mobile.dao.MobileHomeDao)
	 */
	@Override
	public void getData(MobileHomeAuxiliaryModel model,KanbanConfigMobileDto kanbanConfigMobileDto,
			MobileHomeDao mobileHomeDao) {
        int perWeek = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.GRJXJBH_PERFMANWEEK).intValue();
        //查询绩效日期
        int time = mobileHomeDao.queryPreYearMonth(model.getCustomerId(), perWeek);
    	List<Integer> lowList =  CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.GRJXJBH_LOWPERFMAN);
		int lowL=-1;
		int lowH=-1;
    	if(lowList!=null){
			if(lowList.size()>0){
				lowL=lowList.get(0);
				lowH=lowList.get(0);
			}
			if(lowList.size()>1){
				lowH=lowList.get(1);
			}
		}
		int count = mobileHomeDao.queryLowPerCount(model.getCustomerId(),model.getOrganId(),time, perWeek,lowL,lowH);
		kanbanConfigMobileDto.setTotalDate("低绩效");
		kanbanConfigMobileDto.setTotalValue(count+"");
		kanbanConfigMobileDto.setBackgroundColor(NORMAL_BACKGROUD_CORLOR);
		kanbanConfigMobileDto.setImgColor(NORMAL_CORLOR);
//		kanbanConfigMobileDto.setTextColor(NORMAL_TEXT_CORLOR);
	}

}

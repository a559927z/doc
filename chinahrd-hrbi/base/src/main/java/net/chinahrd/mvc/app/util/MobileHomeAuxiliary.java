/**
*net.chinahrd.biz.paper.mobile.util
*/
package net.chinahrd.mvc.app.util;

import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;
import net.chinahrd.mvc.app.dao.home.MobileHomeDao;


/**
 * 移动端首页辅助查询类接口
 * @author htpeng
 *2016年7月14日下午4:31:41
 */
public interface MobileHomeAuxiliary {
	 static final String NORMAL_CORLOR="#4dc7ee";
	 static final String WARN_CORLOR="#fec047";
	 static final String RISK_CORLOR="#ff5b5a";
	 
	 static final String ADD_CORLOR="#666666";
	 
	 static final String NORMAL_BACKGROUD_CORLOR="#dcedf4";
	 static final String WARN_BACKGROUD_CORLOR="#f5eddd";
	 static final String RISK_BACKGROUD_CORLOR="#f4e6e5";

	 
//	 
//	 static final String NORMAL_TEXT_CORLOR="#daeef6";
//	 static final String WARN_TEXT_CORLOR="#FFF6E3";
//	 static final String RISK_TEXT_CORLOR="#FEEEEB";
	void getData(MobileHomeAuxiliaryModel model,KanbanConfigMobileDto kanbanConfigMobileDto,MobileHomeDao mobileHomeDao);
}

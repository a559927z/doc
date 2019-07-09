///**
//*net.chinahrd.entity.module
//*/
//package net.chinahrd.api;
//
//
//
//import net.chinahrd.api.module.Modules;
//import net.chinahrd.entity.dto.pc.accordDismiss.DismissTrendDto;
//import net.chinahrd.core.api.define.ApiDoc;
//
///**
// * 主动流失率定义的 Api 接口
// * @author htpeng
// *2016年10月17日下午6:09:24
// */
//public class AccordDismissApi {
//
//	public static final ApiDoc<DismissTrendDto> queryDisminss4Quarter=new ApiDoc<DismissTrendDto>(Modules.AccordDismiss,"queryDisminss4Quarter");
//
//}
/**
*net.chinahrd.entity.module
*/
package net.chinahrd.api;



import java.util.List;

import net.chinahrd.entity.dto.pc.accordDismiss.DismissRecordDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissTrendDto;

/**
 * 主动流失率定义的 Api 接口
 * @author htpeng
 *2016年10月17日下午6:09:24
 */
public interface AccordDismissApi {

	DismissTrendDto queryDisminss4Quarter(String customerId,String organId);
	
	List<DismissRecordDto> getDismissRecord(String customerId, String organizationId, String prevQuarter, String yearMonths);

}

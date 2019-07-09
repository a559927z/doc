///**
//*net.chinahrd.cache
//*/
//package net.chinahrd.positionCompetency.module;
//
//import org.apache.log4j.Logger;
//
//import net.chinahrd.api.AccordDismissApi;
//import net.chinahrd.core.api.ApiRegisterAbstract;
//import net.chinahrd.core.Injection;
//import net.chinahrd.core.api.config.ApiType;
//import net.chinahrd.entity.dto.pc.accordDismiss.DismissTrendDto;
//import net.chinahrd.positionCompetency.mvc.pc.service.AccordDismissService;
//
///**
// * @author htpeng
// *2016年10月13日上午11:54:56
// */
//public class ApiDefine extends ApiRegisterAbstract implements AccordDismissApi{
//	private static final Logger log = Logger.getLogger(ApiDefine.class);
//	
//	  @Injection
//	  private AccordDismissService accordDismissService;
//	
//	/* (non-Javadoc)
//	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
//	 */
//	@Override
//	public ApiType getApiType() {
//		return ApiType.INTERFACE;
//	}
//
//
//	
//	public DismissTrendDto queryDisminss4Quarter(String organId,  String customerId){
//		DismissTrendDto dto=accordDismissService.queryDisminss4Quarter(organId,Cache.getInstance().queryQuarterLastDay.get(),customerId);
//		return dto;
//	}
//}

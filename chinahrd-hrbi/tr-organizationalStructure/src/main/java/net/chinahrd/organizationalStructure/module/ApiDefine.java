/**
*net.chinahrd.cache
*/
package net.chinahrd.organizationalStructure.module;

import java.util.List;

import org.apache.log4j.Logger;

import net.chinahrd.api.OrganizationalStructureApi;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.entity.dto.pc.OrgChartDto;
import net.chinahrd.organizationalStructure.mvc.pc.dao.OrgChartDao;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;

/**
 * @author htpeng
 *2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract implements OrganizationalStructureApi{
	private static final Logger log = Logger.getLogger(ApiDefine.class);
	
	  @Injection
	  private OrgChartDao orgChartDao;
//	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		return ApiType.INTERFACE;
	}


	public List<OrgChartDto>  queryChildOrgById(String customerId, String organId){
		 Double maxVal = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.ZZJGBZ_MAXVAL);
			return orgChartDao.queryChildOrgListById(customerId,organId,DateUtil.getDBNow(), maxVal);
		 
	 }

}

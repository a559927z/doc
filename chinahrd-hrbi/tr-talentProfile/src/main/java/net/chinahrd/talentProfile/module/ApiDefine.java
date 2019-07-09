package net.chinahrd.talentProfile.module;
/**
*net.chinahrd.cache
*/


import java.util.List;

import net.chinahrd.api.TalentProfileApi;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.JobChangeDto;
import net.chinahrd.talentProfile.mvc.pc.dao.TalentProfileDao;
import net.chinahrd.utils.CollectionKit;

/**
 * @author htpeng
 *2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract implements TalentProfileApi{
//	private static final Logger log = Logger.getLogger(ApiDefine.class);
	
	@Injection
	private TalentProfileDao talentProfileDao;
	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		// TODO Auto-generated method stub
		return ApiType.INTERFACE;
	}

	@Override
	public List<JobChangeDto> queryJobChange(String empId, String customerId,
			Integer changeType) {
		List<String> empIds = CollectionKit.strToList(empId);
		return talentProfileDao.queryJobChange(empIds, customerId,changeType);
	}

	@Override
	public EmpDetailDto findEmpDetail(String empId, String customerId) {
		return talentProfileDao.findEmpDetail(empId, customerId);
	}
}

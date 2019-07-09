/**
*net.chinahrd.cache
*/
package net.chinahrd.teamImg.module;

import java.util.List;

import org.apache.log4j.Logger;

import net.chinahrd.api.TeamImgApi;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.teamImg.TeamImgEmpDto;
import net.chinahrd.teamImg.mvc.pc.dao.TeamImgDao;

/**
 * @author htpeng 2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract implements TeamImgApi {
	private static final Logger log = Logger.getLogger(ApiDefine.class);

	@Injection
	TeamImgDao teamImgDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		return ApiType.INTERFACE;
	}

	/**
	 * 管理者首页-团队画像
	 *
	 * @param organId
	 * @param customerId
	 * @return
	 */
	public List<TeamImgEmpDto> queryTeamImgAb(String organId, String customerId) {
		return teamImgDao.queryTeamImg(organId, customerId);
	}

	/**
	 * 管理者首页-分组统计能力层级人数
	 */
	@Override
	public List<KVItemDto> groupCountTeamImgAb(String organId, String customerId) {
		return teamImgDao.groupCountTeamImgAb(organId, customerId);
	}

}

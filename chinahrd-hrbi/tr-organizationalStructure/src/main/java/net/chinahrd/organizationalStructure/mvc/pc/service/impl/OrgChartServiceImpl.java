package net.chinahrd.organizationalStructure.mvc.pc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.chinahrd.entity.dto.pc.OrgChartDto;
import net.chinahrd.organizationalStructure.mvc.pc.dao.OrgChartDao;
import net.chinahrd.organizationalStructure.mvc.pc.service.OrgChartService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;

@Service("orgChartService")
public class OrgChartServiceImpl implements OrgChartService {
	
	@Autowired
	private OrgChartDao orgChartDao;
	@Override
	public List<OrgChartDto> queryChildOrgById(String customerId,String organizationId) {
		Double maxVal = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.ZZJGBZ_MAXVAL);
		return orgChartDao.queryChildOrgListById(customerId,organizationId,DateUtil.getDBNow(), maxVal);
	}
	@Override
	public OrgChartDto queryOrgChartInitDataById(String customerId,String organizationId) {
		Double maxVal = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.ZZJGBZ_MAXVAL);
		OrgChartDto dto = orgChartDao.queryOrgById(customerId,organizationId,DateUtil.getDBNow(), maxVal);
		return dto;
	}

}

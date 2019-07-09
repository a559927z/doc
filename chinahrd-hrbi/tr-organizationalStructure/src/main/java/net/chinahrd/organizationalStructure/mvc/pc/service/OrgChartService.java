package net.chinahrd.organizationalStructure.mvc.pc.service;

import java.util.List;

import net.chinahrd.entity.dto.pc.OrgChartDto;


/**
 * 组织机构图（编制和空缺）service
 * @author guanjian
 *
 */
public interface OrgChartService {
	/**
	 * 由机构ID查询当前子机构列表
	 * @param orgId 机构ID
	 * @return 
	 */
	public List<OrgChartDto> queryChildOrgById(String customerId,String organizationId);
	
	/**
	 * 由机构ID查询组织机构图初始化数据
	 * @param orgId 机构ID
	 * @return 
	 */
	public OrgChartDto queryOrgChartInitDataById(String customerId,String organizationId);
}

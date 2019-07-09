package net.chinahrd.organizationalStructure.mvc.pc.dao;

import java.util.List;

import net.chinahrd.entity.dto.pc.OrgChartDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository("orgChartDao")
public interface OrgChartDao {
	/** 由机构ID查询子机构编制列表
	 * 
	 * @param maxVal
	 *        TODO
	 * @param orgId
	 *        机构ID
	 * @return */
	List<OrgChartDto> queryChildOrgListById(
			@Param("customerId") String customerId,
			@Param("organizationId") String organizationId,
			@Param("now") String now, @Param("maxVal") Double maxVal);

	/** 由机构ID查询当前机构编制列表
	 * 
	 * @param orgId
	 *        机构ID
	 * @return */
	OrgChartDto queryOrgById(@Param("customerId") String customerId,
			@Param("organizationId") String organizationId,
			@Param("now") String now, @Param("maxVal") Double maxVal);
}

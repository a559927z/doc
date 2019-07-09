package net.chinahrd.organizationalStructure.mvc.pc.dao;


import net.chinahrd.entity.dto.pc.OrgChartDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 绩效dao
 */
@Repository("orgChartApiDao")
public interface OrgChartApiDao {

	  /**
     * 管理者首页-绩效目标 部门
     *
     * @param organId
     * @return
     */
    List<OrgChartDto> queryChildOrgById(@Param("customerId") String customerId,@Param("organId") String organId );

   
}

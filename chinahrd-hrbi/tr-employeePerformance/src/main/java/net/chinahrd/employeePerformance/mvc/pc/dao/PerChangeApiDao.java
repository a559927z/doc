package net.chinahrd.employeePerformance.mvc.pc.dao;


import net.chinahrd.entity.dto.pc.manage.PerformanceDto;
import net.chinahrd.entity.dto.pc.manage.PerformanceEmpDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 绩效dao
 */
@Repository("perChangeApiDao")
public interface PerChangeApiDao {

	  /**
     * 管理者首页-绩效目标 部门
     *
     * @param organId
     * @return
     */
    List<PerformanceDto> queryPerformance(@Param("organId") String organId, @Param("customerId") String customerId);

    /**
     * 管理者首页-绩效目标 下属
     *
     * @param organId
     * @return
     */
    List<PerformanceEmpDto> queryPerformanceEmp(@Param("organId") String organId, @Param("customerId") String customerId);

}

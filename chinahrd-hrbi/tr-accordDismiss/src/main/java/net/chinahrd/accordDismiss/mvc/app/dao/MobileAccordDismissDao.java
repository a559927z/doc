package net.chinahrd.accordDismiss.mvc.app.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.app.dismiss.AccordDismissDto;
import net.chinahrd.entity.dto.app.dismiss.DismissTrendDto;
import net.chinahrd.entity.dto.app.dismiss.DismissTypeDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRecordDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 主动流率dao
 * @author htpeng
 *2016年3月30日下午5:30:38
 */
@Repository("mobileAccordDismissDao")
public interface MobileAccordDismissDao {

    /**
     * 查询流失记录
     *
     * @param customerId
     * @param organizationId
     * @return
     */
    List<DismissRecordDto> queryDismissRecord(@Param("customerId") String customerId, @Param("organizationId") String organizationId, @Param("quarterLastDay") String quarterLastDay);

    /**
     * 查询主动流失率趋势信息
     * @param organId
     * @param prevQuarter
     * @param customerId
     * @return
     */
    List<DismissTrendDto> queryDismissTrend(Map<String,Object>map);

  
    /**
     * 查询当前机构及子机构的流失数据
     */
    List<DismissTrendDto> querySubDismissData(@Param("customerId") String customerId, @Param("organId") String organId, @Param("startYearMonth") int startYearMonth);

    /**
     * 查询已有记录的上一个季度最后一天
     *
     * @param customerId
     * @return
     */
    String queryQuarterLastDay(@Param("customerId") String customerId);

    /**
     * 查询季度总人数
     * @param organId
     * @param customerId
     * @param quarterLastDay
     * @return
     */
    List<DismissTrendDto> queryDisminss4Quarter(@Param("organId") String organId, @Param("quarterLastDay") String quarterLastDay, @Param("customerId") String customerId);

    /**
     * 根据绩效查询季度流失人员统计信息
     * @param organId
     * @param quarterLastDay
     * @param customerId
     * @return
     */
    List<DismissTypeDto> queryDismissPref(Map<String,Object>map);

    /**
     * 根据能力层级查询季度流失人员统计信息
     * @param organId
     * @param quarterLastDay
     * @param customerId
     * @return
     */
    List<DismissTypeDto> 	queryDismissAbility(Map<String,Object>map);

    /**
     * 根据司龄查询季度流失人员统计信息
     * @param organId
     * @param quarterLastDay
     * @param customerId
     * @return
     */
    List<DismissTypeDto> queryDismissCompanyAge(Map<String,Object>map);

    /**
     * 查询流失人员明细
     *
     * @param customerId
     * @param organId
     * @return
     */
	List<AccordDismissDto> queryRunOffDetail(Map<String, Object> mapParam);
	
	int queryRunOffCount(Map<String, Object> mapParam);
	

	/**
	 * 获取时间段内 流失总人数
	 * @param parmMap
	 * @return
	 */
	Integer queryDismissEmpCount(Map<String, Object> parmMap);
	
	
	
}

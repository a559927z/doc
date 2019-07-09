package net.chinahrd.accordDismiss.mvc.pc.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.accordDismiss.AccordDismissDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissContrastDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRecordDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissTrendDto;
import net.chinahrd.entity.dto.pc.accordDismiss.QuarterDismissCountDto;
import net.chinahrd.entity.dto.pc.common.CompareDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 主动流率dao
 */
@Repository("accordDismissDao")
public interface AccordDismissDao {

    /**
     * 查询流失记录
     *
     * @param customerId
     * @param organizationId
     * @return
     */
    List<DismissRecordDto> queryDismissRecord(@Param("customerId") String customerId, @Param("organizationId") String organizationId, @Param("yearMonths") String yearMonths,@Param("quarterLastDay") String quarterLastDay);

    /**
     * 查询主动流失率趋势信息
     * @param organId
     * @param prevQuarter
     * @param customerId
     * @return
     */
    List<DismissTrendDto> queryDismissTrend(@Param("organId") String organId, @Param("prevQuarter") String prevQuarter, @Param("customerId") String customerId);
    
    /**
     * 统计近两个季度的关键人才流失人数
     * @param organId 机构id
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param customerId
     * @return
     */
    List<CompareDto> countQuaKeyPerson(@Param("organId") String organId, @Param("beginDate") String beginDate,@Param("endDate") String endDate, @Param("customerId") String customerId);
    
    /**
     * 统计近两个季度的高绩效流失人数
     * @param organId 机构id
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param customerId
     * @return
     */
    List<CompareDto> countQuaHighPerform(@Param("organId") String organId, @Param("beginDate") String beginDate,@Param("endDate") String endDate, @Param("customerId") String customerId);
    
    /**
     * 查询当前机构及子机构的流失数据
     */
    List<DismissContrastDto> querySubDismissData(@Param("customerId") String customerId, @Param("organId") String organId, @Param("beginYearMonth") int beginYearMonth,@Param("endYearMonth") int endYearMonth);

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
    List<QuarterDismissCountDto> queryQuarterDismiss4Pref(@Param("organId") String organId, @Param("quarterLastDay") String quarterLastDay, @Param("customerId") String customerId);

    /**
     * 根据能力层级查询季度流失人员统计信息
     * @param organId
     * @param quarterLastDay
     * @param customerId
     * @return
     */
    List<QuarterDismissCountDto> queryQuarterDismiss4Ability(@Param("organId") String organId, @Param("quarterLastDay") String quarterLastDay, @Param("customerId") String customerId);

    /**
     * 根据司龄查询季度流失人员统计信息
     * @param organId
     * @param quarterLastDay
     * @param customerId
     * @return
     */
    List<QuarterDismissCountDto> queryQuarterDismiss4CompanyAge(@Param("organId") String organId, @Param("quarterLastDay") String quarterLastDay, @Param("customerId") String customerId);

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
     * 查询流失人员明细
     *
     * @param customerId
     * @param organId
     * @return
     */
	List<AccordDismissDto> queryRunOffDetailByName(Map<String, Object> mapParam);
	
	int queryRunOffByNameCount(Map<String, Object> mapParam);
	
	
}

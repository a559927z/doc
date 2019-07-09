package net.chinahrd.accordDismiss.mvc.pc.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.accordDismiss.AccordDismissDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRecordDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissTrendDto;
import net.chinahrd.entity.dto.pc.accordDismiss.QuarterDismissCountDto;
import net.chinahrd.entity.dto.pc.common.CompareDto;


/**
 *
 */
public interface AccordDismissService {


    /**
     * 查询流失人员明细
     *
     * @param queryType
     * @param keyName
     * @param customerId
     * @param organizationId
     * @param beginDate
     * @param endDate
     * @param dto
     * @param roType
     * @param sequenceId
     * @param abilityId
     * @param isKeyTalent
     * @param keyTalentTypeId
     * @param performanceKey
     * @return
     */
    PaginationDto<AccordDismissDto> queryRunOffDetail(String queryType, String keyName, String customerId, String organizationId, String beginDate, String endDate, PaginationDto<AccordDismissDto> dto,
                                                      String roType, String sequenceId, String abilityId, String isKeyTalent, String keyTalentTypeId, String performanceKey);

    /**
     * 查询流失记录
     *
     * @param customerId
     * @param organizationId
     * @return
     */
    List<DismissRecordDto> queryDismissRecord(String customerId, String organizationId, String yearMonths, String quarterLastDay);

    /**
     * 查询主动流失率趋势信息
     *
     * @param organId
     * @param prevQuarter
     * @param customerId
     * @return
     */
    List<DismissTrendDto> queryDismissTrend(String organId, String prevQuarter, String customerId);

    /**
     * 获取“关键人才流失人数”的数据
     *
     * @return
     */
    CompareDto getKeyDismissData(String organId, String date, String customerId);

    /**
     * 获取“高绩效流失人数”的数据
     *
     * @return
     */
    CompareDto getPerformDismissData(String organId, String date, String customerId);

    /**
     * 查询当前机构及子机构的流失数据
     *
     * @param organizationId
     * @param date           上个季度的最后一天
     * @return Map<String,Object> 其中map的key:curRate->value:当前机构的主动流失率;key:sub->value：子机构的流失率相关数据
     */
    Map<String, Object> getSubDismissData(String customerId, String organizationId, String date);

    /**
     * 查询已有记录的上一个季度最后一天
     *
     * @param customerId
     * @return
     */
    String queryQuarterLastDay(String customerId);


    /**
     * 查询季度流失信息
     *
     * @param organId
     * @param quarterLastDay
     * @param customerId
     * @return
     */
    DismissTrendDto queryDisminss4Quarter(String organId, String quarterLastDay, String customerId);


    /**
     * 查询季度流失人员统计信息
     *
     * @param organId
     * @param quarterLastDay
     * @param customerId
     * @return
     */
    Map<String, List<QuarterDismissCountDto>> queryQuarterDismissInfo(String organId, String quarterLastDay, String customerId);

}

package net.chinahrd.accordDismiss.mvc.app.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.dismiss.AccordDismissDto;
import net.chinahrd.entity.dto.app.dismiss.DismissTrendDto;
import net.chinahrd.entity.dto.app.dismiss.DismissTypeDto;


/**
 * 
 * @author htpeng
 *2016年3月30日下午5:31:34
 */
public interface MobileccordDismissService {


    /**
     * 查询流失人员明细
     *
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
	 PaginationDto<AccordDismissDto> queryRunOffDetail(String customerId,String organizationId, String date,  PaginationDto<AccordDismissDto> dto);

    /**
     * 查询主动流失率趋势信息
     *
     * @param organId
     * @param prevQuarter
     * @param customerId
     * @return
     */
    Map<String, List<DismissTrendDto>> queryDismissTrend(String organId,String firstOrganId, String prevQuarter, String customerId);

  
    /**
     * 查询当前机构及子机构的流失数据
     * @param organizationId
     * @param date 上个季度的最后一天
     * @return  Map<String,Object> 其中map的key:curRate->value:当前机构的主动流失率;key:sub->value：子机构的流失率相关数据
     */
    List<DismissTrendDto> getSubDismissData(String customerId,String organizationId,String date);

    /**
     * 查询已有记录的上一个季度最后一天
     *
     * @param customerId
     * @return
     */
    String queryQuarterLastDay(String customerId);


    /**
     * 查询季度流失信息
     * @param organId
     * @param quarterLastDay
     * @param customerId
     * @return
     */
    DismissTrendDto queryDisminss4Quarter(String organId, String quarterLastDay, String customerId);


    /**
     * 查询流失人员统计信息
     * @param organId
     * @param quarterLastDay
     * @param customerId
     * @return
     */
    Map<String, List<DismissTypeDto>> queryDismissInfo(String organId, String quarterLastDay, String customerId);

}

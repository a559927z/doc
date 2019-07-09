package net.chinahrd.talentStructure.mvc.pc.service;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import net.chinahrd.entity.dto.pc.common.TableGridDto;
import net.chinahrd.entity.dto.pc.talentStructure.TalentStructureDto;

import java.util.List;
import java.util.Map;


public interface TalentStructureService {

    /**
     * 编制分析
     *
     * @param organId
     * @param customerId
     * @return
     */
    TalentStructureDto findBudgetAnalyse(String organId, String customerId);

    /**
     * 预警值设置
     *
     * @param customerId
     * @param normal
     * @param risk
     * @return
     */
    boolean updateConfigWarnVal(String customerId, Double normal, Double risk);


    /**
     * 人力结构数据
     *
     * @param organId
     * @param customerId
     * @return
     */
    Map<String, Object> getTalentStuctureData(String organId, String customerId);

    /**
     * 获取当前组织管理者员工分布
     *
     * @param organId
     * @return
     */
    List<KVItemDto> queryManageEmp4Organ(String organId, String customerId);

    /**
     * 查询下级组织管理者员工分布
     *
     * @param organId
     * @return
     */
    TableGridDto<Map<String, Object>> queryManagerOrEmpList(String organId, String customerId);

    /**
     * 获取当前组织职级分布
     *
     * @param organId
     * @param sequenceId
     * @param customerId
     * @return
     */
    List<KVItemDto> queryAbility4Organ(String organId, String sequenceId, String customerId);

    /**
     * 查询下级组织职级分布
     *
     * @param organId
     * @return
     */
    TableGridDto<Map<String, Object>> queryAbilityToSubOrgan(String organId, String customerId);

    /**
     * 获取职位序列分布
     *
     * @param organId
     * @param customerId
     * @return
     */
    List<SequenceItemsDto> querySequence4Organ(String organId, String customerId);


    /***
     * 获取职位序列职级统计信息
     *
     * @param organId
     * @param customerId
     * @return
     */
    TableGridDto<Map<String, Object>> querySequenceAbilityCount(String organId, String customerId);

    /**
     * 获取组织分布
     *
     * @param organId
     * @return
     */
    List<KVItemDto> querySubOrganCount(String organId, String customerId);

    /**
     * 获取工作地分布
     *
     * @param organId
     * @return
     */
    List<KVItemDto> queryWorkplaceCount(String organId, String customerId);

    /**
     * 获取当前组织学历分布
     *
     * @param organId
     * @return
     */
    List<KVItemDto> queryDegree4Organ(String organId, String customerId);

    /**
     * 查询下级组织学历分布
     *
     * @param organId
     * @return
     */
    TableGridDto<Map<String, Object>> queryDegreeToSubOrgan(String organId, String customerId);

    /**
     * 获取当前组织司龄分布
     *
     * @param organId
     * @return
     */
    List<KVItemDto> queryCompanyAge4Organ(String organId, String customerId);

    /**
     * 查询下级组织司龄分布
     *
     * @param organId
     * @return
     */
    TableGridDto<Map<String, Object>> queryCompanyAgeToSubOrgan(String organId, String customerId);


    /**
     * 人力结构数据To月报
     *
     * @param organId
     * @param customerId
     * @return
     */
    Map<String, Object> queryTalentStuctureByMonth(String organId, String customerId);

    /**
     * 根据主序列查询能力层级分布-柱状图 by jxzhang 2016-02-26
     *
     * @param organId
     * @param customerId
     * @param seqId
     * @return
     */
    Map<String, Integer> queryAbEmpCountBarBySeqId(String organId, String customerId, String seqId);
}

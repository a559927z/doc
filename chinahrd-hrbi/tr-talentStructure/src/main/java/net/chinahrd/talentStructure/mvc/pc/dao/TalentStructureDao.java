package net.chinahrd.talentStructure.mvc.pc.dao;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import net.chinahrd.entity.dto.pc.talentStructure.TalentStructureDto;
import net.chinahrd.entity.dto.pc.talentStructure.TalentStructureResultDto;
import net.chinahrd.entity.dto.pc.talentStructure.TalentStructureResultSubDto;
import net.chinahrd.entity.enums.CompanyAgeEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 人力结构Dao
 *
 * @author jxzhang by 2016-02-21
 */
@Repository("talentStructureDao")
public interface TalentStructureDao {

    /**
     * 编制分析
     *
     * @param organId
     * @param customerId
     * @param now
     * @param personTypeKey 配置人员范围
     * @return
     */
    TalentStructureDto findBudgetAnalyse(@Param("organId") String organId,
                                         @Param("customerId") String customerId, @Param("now") String now,
                                         @Param("personTypeKey") List<Integer> personTypeKey);

    /**
     * 预警值设置
     *
     * @param customerId
     * @param normal
     */
    void updateConfigWarnValByNormal(@Param("customerId") String customerId, @Param("normal") Double normal);

    void updateConfigWarnValByRisk(@Param("customerId") String customerId, @Param("risk") Double risk);

    /**
     * @param customerId
     * @return
     */
    List<String> queryOrganId(@Param("customerId") String customerId);

    /**
     * 人力结构核心接口
     *
     * @param customerId
     * @return
     */
    List<TalentStructureDto> queryEmpInfo(@Param("organId") String organId, @Param("customerId") String customerId);


    /**
     * 序列点击查询
     *
     * @param organId
     * @param customerId
     * @param seqId
     * @return
     */
    List<TalentStructureDto> queryAbEmpCountBarBySeqId(@Param("organId") String organId,
                                                       @Param("customerId") String customerId, @Param("seqId") String seqId);


    /**
     * 获取当前组织管理者员工分布
     *
     * @param organList
     * @param customerId
     * @return
     */
    List<TalentStructureResultSubDto> queryManageEmp4Organ(@Param("organList") List<String> organList, @Param("maxIdx") Integer maxIdx, @Param("manageSequence") String manageSequence, @Param("customerId") String customerId);

    /**
     * 查询下级组织管理者员工分布
     *
     * @param organId
     * @param customerId
     * @return
     */
    List<TalentStructureResultDto> queryManagerOrEmpList(@Param("organId") String organId, @Param("maxIdx") Integer maxIdx, @Param("manageSequence") String manageSequence, @Param("customerId") String customerId);

    /**
     * 获取当前组织职级分布
     *
     * @param organList
     * @param customerId
     * @return
     */
    List<KVItemDto> queryAbility4Organ(@Param("organList") List<String> organList, @Param("sequenceId") String sequenceId, @Param("maxIdx") Integer maxIdx, @Param("customerId") String customerId);

    /**
     * 查询下级组织职级分布
     *
     * @param organId
     * @param customerId
     * @return
     */
    List<TalentStructureResultDto> queryAbilityToSubOrgan(@Param("organId") String organId, @Param("maxIdx") Integer maxIdx, @Param("customerId") String customerId);


    /**
     * 获取职位序列分布
     *
     * @param organList
     * @param customerId
     * @return
     */
    List<SequenceItemsDto> querySequence4Organ(@Param("organList") List<String> organList, @Param("customerId") String customerId);


    /***
     * 获取职位序列职级统计
     * @param organList
     * @param customerId
     * @return
     */
    List<TalentStructureResultDto> querySequenceAbilityCount(@Param("organList") List<String> organList, @Param("customerId") String customerId);

    /**
     * 获取组织分布
     *
     * @param organId
     * @param customerId
     * @return
     */
    List<KVItemDto> querySubOrganCount(@Param("organId") String organId, @Param("customerId") String customerId);

    /**
     * 获取工作地分布
     *
     * @param organList
     * @param customerId
     * @return
     */
    List<KVItemDto> queryWorkplaceCount(@Param("organList") List<String> organList, @Param("customerId") String customerId);

    /**
     * 获取当前组织学历分布
     *
     * @param organList
     * @param customerId
     * @return
     */
    List<KVItemDto> queryDegree4Organ(@Param("organList") List<String> organList, @Param("customerId") String customerId);

    /**
     * 查询下级组织学历分布
     *
     * @param organId
     * @param customerId
     * @return
     */
    List<TalentStructureResultDto> queryDegreeToSubOrgan(@Param("organId") String organId, @Param("customerId") String customerId);

    /**
     * 获取当前组织司龄分布
     *
     * @param organList
     * @param customerId
     * @return
     */
    Map<String, Object> queryCompanyAge4Organ(@Param("organList") List<String> organList, @Param("companyAges") List<CompanyAgeEnum> companyAges, @Param("customerId") String customerId);

    /**
     * 查询下级组织司龄分布
     *
     * @param organId
     * @param customerId
     * @return
     */
    List<Map<String, Object>> queryCompanyAgeToSubOrgan(@Param("organId") String organId, @Param("companyAges") List<CompanyAgeEnum> companyAges, @Param("customerId") String customerId);


}

/**
 * net.chinahrd.cache
 */
package net.chinahrd.module;

import java.util.List;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.DegreeDto;
import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import net.chinahrd.entity.dto.pc.SubSequenceItemsDto;
import net.chinahrd.entity.dto.pc.admin.FunctionDto;
import net.chinahrd.entity.dto.pc.common.ConfigDto;

import org.apache.ibatis.annotations.Param;

/**
 * sys缓存
 *
 * @author jxzhang on 2016-11-07
 */
public interface SysCacheDao {

    /**
     * 配置表
     *
     * @return
     */
    List<ConfigDto> queryConfig(@Param("customerId") String customerId);

    /**
     * 机构与所有子孙机构的关系
     *
     * @param customerId
     * @return
     */
    List<KVItemDto> queryAllOrganRelation(@Param("customerId") String customerId);

    /**
     * 机构与所有子机构的关系
     *
     * @param customerId
     * @return
     */
    List<KVItemDto> queryUnderAllOrganRelation(@Param("customerId") String customerId);

    /**
     * 员工日切片表最少最大日期
     *
     * @param customerId
     * @return
     */
    List<KVItemDto> queryEmpMinMaxDays(@Param("customerId") String customerId);


    //=================================
    //				维度表
    //=================================

    /**
     * 获取职位层级
     *
     * @param customerId
     * @return
     */
    List<KVItemDto> queryPositionAbility(@Param("customerId") String customerId);
    
    /**
     * 获取管理序列层级
     *
     * @param customerId
     * @return
     */
    List<KVItemDto> queryManageAbility(@Param("customerId") String customerId);

    /**
     * 大职级（能力层级）
     *
     * @param customerId
     * @return
     */
    List<KVItemDto> queryAbility(@Param("customerId") String customerId);
    
    List<String> queryAbilityKey(@Param("customerId") String customerId);

    List<KVItemDto> querySoureCodeItem(@Param("customerId") String customerId, @Param("groupId") String groupId);

    /**
     * 主序列
     *
     * @param customerId
     * @return
     */
    List<SequenceItemsDto> querySeq(@Param("customerId") String customerId);
    
    /**
     * 子序列
     * @param customerId
     * @return
     */
    List<SubSequenceItemsDto> querySubSeq(@Param("customerId") String customerId);

    List<KVItemDto> queryPosition(@Param("customerId") String customerId);

    List<KVItemDto> queryOrgan(@Param("customerId") String customerId);

    List<KVItemDto> queryPerformance(@Param("customerId") String customerId);

    List<KVItemDto> queryAbilityLv(@Param("customerId") String customerId);

    List<DegreeDto> queryDegree(@Param("customerId") String customerId, @Param("groupId") String groupId);

    List<KVItemDto> queryJobTitle(@Param("customerId") String customerId);

    List<KVItemDto> queryCity(@Param("customerId") String customerId);

    /**
     * 关键人才类型表
     *
     * @param customerId
     * @return
     */
    List<KVItemDto> queryKeyTalentType(@Param("customerId") String customerId);

    List<FunctionDto> queryFunction(@Param("customerId") String customerId);
    /**
     * 招聘渠道
     */
    List<KVItemDto> queryChannel(@Param("customerId") String customerId);


}

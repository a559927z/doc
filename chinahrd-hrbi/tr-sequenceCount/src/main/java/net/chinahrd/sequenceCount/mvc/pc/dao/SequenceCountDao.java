package net.chinahrd.sequenceCount.mvc.pc.dao;

import net.chinahrd.entity.dto.pc.sequenceCount.SequenceCountDto;
import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 职位序列统计dto
 * Created by wqcai on 15/10/30 0030.
 */
@Repository("sequenceCountDao")
public interface SequenceCountDao {
    /**
     * 查询所有主序列以及子序列
     * o
     *
     * @param customerId
     * @return
     */
    List<SequenceItemsDto> querySequenceOrSub(@Param("customerId") String customerId);

    /**
     * 是否有职衔
     *
     * @param customerId
     * @return
     */
    int findJobTitleCount(@Param("customerId") String customerId);

    /**
     * 查询职位序列统计信息
     *
     * @param organList      机构ID集合
     * @param sequenceId     序列ID
     * @param subSequenceIds 子序列集合
     * @param hasSub         是否有子序列
     * @param hasJobTitle    是否有职衔
     * @param customerId     客户ID
     * @return
     */
    List<SequenceCountDto> querySequenceCount(@Param("organList") List<String> organList, @Param("sequenceId") String sequenceId, @Param("subSequenceIds") List<String> subSequenceIds, @Param("hasSub") boolean hasSub, @Param("hasJobTitle") boolean hasJobTitle, @Param("customerId") String customerId);
}

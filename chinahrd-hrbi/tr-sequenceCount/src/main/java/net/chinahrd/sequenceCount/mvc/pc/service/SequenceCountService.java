package net.chinahrd.sequenceCount.mvc.pc.service;


import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import net.chinahrd.entity.dto.pc.sequenceCount.SequenceParentItemsDto;

import java.util.List;

/**
 * 职位序列统计Service接口类
 * Created by wqcai on 15/10/30 0030.
 */
public interface SequenceCountService {
    /**
     * 查询所有主序列以及子序列
     *
     * @param customerId
     * @return
     */
    List<SequenceItemsDto> querySequenceOrSub(String customerId);

    /**
     * 查询职位序列统计信息
     *
     * @param customerId
     * @param organId
     * @param hasSub
     * @param hasJobTitle
     * @param sequenceId
     * @param subSequenceStr
     * @return
     */
    SequenceParentItemsDto querySequenceCount(String customerId, String organId, boolean hasSub, Boolean hasJobTitle, String sequenceId, String subSequenceStr);


    /**
     * 重写querySequenceCount()走缓存
     */
	SequenceParentItemsDto querySequenceCount4Ctrl(String customerId,String organId, boolean hasSub, Boolean hasJobTitle,String sequenceId, String subSequenceStr);
}

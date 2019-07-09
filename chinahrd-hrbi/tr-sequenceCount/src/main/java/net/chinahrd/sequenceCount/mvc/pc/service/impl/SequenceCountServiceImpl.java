package net.chinahrd.sequenceCount.mvc.pc.service.impl;

import net.chinahrd.core.tools.collection.CollectionKit;
import net.chinahrd.entity.dto.pc.sequenceCount.SequenceCountDto;
import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import net.chinahrd.entity.dto.pc.sequenceCount.SequenceParentItemsDto;
import net.chinahrd.sequenceCount.module.Cache;
import net.chinahrd.sequenceCount.mvc.pc.dao.SequenceCountDao;
import net.chinahrd.sequenceCount.mvc.pc.service.SequenceCountService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.Str;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 职位序列统计Service接口类
 * Created by wqcai on 15/10/30 0030.
 */
@Service("sequenceCountService")
public class SequenceCountServiceImpl implements SequenceCountService {

    @Autowired
    private SequenceCountDao sequenceCountDao;

    @Override
    public List<SequenceItemsDto> querySequenceOrSub(String customerId) {
        return sequenceCountDao.querySequenceOrSub(customerId);
    }

    /**
     * 获取各种职业主序列，子序列的人数
     */
    @Override
    public SequenceParentItemsDto querySequenceCount(String customerId, String organId, boolean hasSub, Boolean hasJobTitle, String sequenceId, String subSequenceStr) {
        List<String> subSequenceIds = CollectionKit.strToList(subSequenceStr);
        if (hasJobTitle == null) {
            hasJobTitle = sequenceCountDao.findJobTitleCount(customerId) > 0;
        }
        List<String> organList = CacheHelper.getSubOrganIdList(organId);
        List<SequenceCountDto> dtos = sequenceCountDao.querySequenceCount(organList, sequenceId, subSequenceIds, hasSub, hasJobTitle, customerId);
        SequenceParentItemsDto parent = new SequenceParentItemsDto();
        parent.setHasJobTitle(hasJobTitle);
        for (SequenceCountDto dto : dtos) {
            addChild(dto, parent, hasSub, hasJobTitle);
        }
        return parent;
    }
    
    /**
     * 重写querySequenceCount()走缓存
     * @param customerId
     * @param organId
     * @param hasSub
     * @param hasJobTitle
     * @param sequenceId
     * @param subSequenceStr
     * @return
     */
    @Override
    public SequenceParentItemsDto querySequenceCount4Ctrl(String customerId, String organId, boolean hasSub, Boolean hasJobTitle, String sequenceId, String subSequenceStr){
    	//缓存中获取
    	Map<String, HashMap<String, SequenceParentItemsDto>> qspisMap = Cache.queryOrg_SequenceParentItems.get();
    	//无则直接从数据库查询
    	if(hasSub == false || hasJobTitle != null && hasJobTitle == false || Str.IsEmpty(sequenceId))
    		return querySequenceCount(customerId, organId, hasSub, hasJobTitle, sequenceId, subSequenceStr);
    	Map<String, SequenceParentItemsDto> sepaHashMap = qspisMap.get(sequenceId);
    	SequenceParentItemsDto target = sepaHashMap.get(organId);
    	if(target == null || target.getLegends() == null || target.getLegends().isEmpty()){
    		Cache.queryOrg_SequenceParentItems.update();
    		return querySequenceCount(customerId, organId, hasSub, hasJobTitle, sequenceId, subSequenceStr);
    	}
    	return target;
    		
    }

    private void addChild(SequenceCountDto dto, SequenceParentItemsDto root, boolean hasSub, boolean hasJobTitle) {
        getLegend(root, dto.getAbilityId(), dto.getAbilityName(), dto.getAbilityShowIndex());
        SequenceItemsDto subSeqNode;
        if (hasSub) {
            subSeqNode = getChild(root, dto.getSequenceSubId(), null);
        } else {
            subSeqNode = getChild(root, dto.getSequenceId(), null);
        }
        SequenceItemsDto subAbilityNode = getSubChild(subSeqNode, dto.getAbilityId(), dto.getAbilityName(), dto.getAbilityPrefix(), dto.getAbilityShowIndex(), 0);
        if (hasJobTitle) {
            SequenceItemsDto jobTitleNode = getSubChild(subAbilityNode, dto.getJobTitleId(), dto.getJobTitleName(), null, dto.getJobTitleShowIndex(), 0);
            getSubChild(jobTitleNode, dto.getAbilityLvId(), null, dto.getAbilityLvPrefix(), dto.getAbilityLvShowIndex(), dto.getEmpCount());
        } else {
            getSubChild(subAbilityNode, dto.getAbilityLvId(), null, dto.getAbilityLvPrefix(), dto.getAbilityLvShowIndex(), dto.getEmpCount());
        }
    }


    /**
     * 第一节点序列（主序列或子序列）
     */
    private SequenceItemsDto getChild(SequenceParentItemsDto parent, String id, String name) {
        SequenceItemsDto child = parent.findSubById(id);
        if (null == child) {
            child = new SequenceItemsDto(id, name);
            parent.addSubItem(child);
        }
        return child;
    }

    /**
     * 其它子级
     */
    private SequenceItemsDto getSubChild(SequenceItemsDto parent, String id, String name, String prefix, Integer showIndex, int count) {
        SequenceItemsDto child = parent.findSubById(id);
        if (null == child) {
            child = new SequenceItemsDto(id, name, prefix, showIndex, count);
            parent.addSubItem(child);
        }
        return child;
    }

    /**
     * 能力层级
     */
    private SequenceItemsDto getLegend(SequenceParentItemsDto parent, String id, String name, Integer showIndex) {
        SequenceItemsDto legend = parent.findLegendById(id);
        if (null == legend) {
            legend = new SequenceItemsDto(id, name, showIndex);
            parent.addLegend(legend);
        }
        return legend;
    }
}

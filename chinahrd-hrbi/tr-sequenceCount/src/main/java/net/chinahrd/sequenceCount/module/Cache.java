package net.chinahrd.sequenceCount.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.chinahrd.core.cache.CacheBlock;
import net.chinahrd.core.cache.CacheBlockConstructor;
import net.chinahrd.core.cache.CustomBlock;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.sequenceCount.SequenceParentItemsDto;
import net.chinahrd.mvc.pc.service.admin.OrganService;
import net.chinahrd.sequenceCount.mvc.pc.service.SequenceCountService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.Str;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class Cache{

    /***
     * 只有根+第一层的（职业序列人数）组织org_id作为分集<\p>
     * 只查询主序列的统计结果<\p>
     * 只考虑hasSub，hasJobTitle都为true的情况
     * 数据结构:[{sequenceId : {k(org_id):v(SequenceParentItemsDto)}}]<\p>
     */
    public static CacheBlock<Map<String, HashMap<String, SequenceParentItemsDto>>> queryOrg_SequenceParentItems = 
    		new CacheBlockConstructor<Map<String, HashMap<String, SequenceParentItemsDto>>>("querySequenceCount",true)
    		.getCustomBlock(new CustomBlock<Map<String,HashMap<String,SequenceParentItemsDto>>>() {
				
				@Override
				public Map<String, HashMap<String, SequenceParentItemsDto>> formatData(
						Object arg0) {
					WebApplicationContext wc = ContextLoader.getCurrentWebApplicationContext();
					OrganService orgService = (OrganService) wc.getBean(OrganService.class);
					SequenceCountService seqCountService = (SequenceCountService)wc.getBean(SequenceCountService.class);
					//按照org表，只获取第一第二层的职业序列人数（SequenceParentItemsDto）
					OrganDto org = new OrganDto();
					org.setCustomerId(EisWebContext.getCustomerId());
					org.setOrganizationParentId("-1");
					//获取树根
					List<OrganDto> orglist = orgService.findList(org);
					//获取二级组织
					for(int i = 0, size = orglist.size(); i < size ; i++){
						OrganDto orgpar  = orglist.get(i);
						OrganDto org2son = new OrganDto();
						org2son.setOrganizationParentId(orgpar.getOrganizationId());
						org2son.setCustomerId(EisWebContext.getCustomerId());
						orglist.addAll(orgService.findList(org2son));
					}
					List<SequenceItemsDto> seqlist = CacheHelper.getSequence();
					//获取主序列
					
					//逐个儿子获取职业序列人数
					//放置到缓存中，[{sequenceId : {k(org_id):v(SequenceParentItemsDto)}}]
					if(orglist == null || orglist.isEmpty()){return null;}
					//
					Map<String, HashMap<String, SequenceParentItemsDto>> target = new HashMap<String, HashMap<String,SequenceParentItemsDto>>();

					if(seqlist == null || seqlist.isEmpty()){return null;};
					for(SequenceItemsDto seq : seqlist){
						if(seq == null || Str.IsEmpty(seq.getItemId())){continue;}
						HashMap<String, SequenceParentItemsDto> ele = new HashMap<String, SequenceParentItemsDto>();
						
						for(OrganDto dto : orglist){
							if(dto == null || Str.IsEmpty(dto.getOrganizationId())){continue;}
							SequenceParentItemsDto one = seqCountService.querySequenceCount(EisWebContext.getCustomerId(), dto.getOrganizationId(), true, true, seq.getItemId(), "");
							ele.put(dto.getOrganizationId(), one);
						}
						target.put(seq.getItemId(), ele);
					}
					return target;
				}
			});
    
}

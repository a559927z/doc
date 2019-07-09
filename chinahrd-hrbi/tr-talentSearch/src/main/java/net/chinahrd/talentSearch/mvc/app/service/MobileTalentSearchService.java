package net.chinahrd.talentSearch.mvc.app.service;


import java.util.List;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.common.EmpDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;


/**
 * 
 * @author htpeng
 *2016年3月30日下午5:31:34
 */
public interface MobileTalentSearchService {


    /**
     * 根据名称搜索人员
     *

     * @return
     */
	PaginationDto<EmpDto> queryTalentSearch(String customerId,String key,List<OrganDto> organPermitIds,PaginationDto<EmpDto> dto);

    
}

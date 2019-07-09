package net.chinahrd.talentSearch.mvc.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.common.EmpDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.talentSearch.mvc.app.dao.MobileTalentSearchDao;
import net.chinahrd.talentSearch.mvc.app.service.MobileTalentSearchService;
import net.chinahrd.utils.CollectionKit;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 主动流失率service
 * 
 * @author htpeng 2016年3月30日下午5:30:52
 */
@Service("mobileTalentSearchService")
public class MobileTalentSearchServiceImpl implements
	MobileTalentSearchService {

	@Autowired
	private MobileTalentSearchDao mobileTalentSearchDao;

	@Override
	public PaginationDto<EmpDto> queryTalentSearch(String customerId,String key,List<OrganDto> organPermitIds,PaginationDto<EmpDto> dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String, Object> mapParam = new HashMap<String, Object>();
		List<String> list=CollectionKit.newList();
		for(OrganDto organDto:organPermitIds){
			list.add(organDto.getOrganizationId());
		}
		mapParam.put("customerId", customerId);
		mapParam.put("organPermitIds", list);
		mapParam.put("keyName", key);
		mapParam.put("rowBounds", rowBounds);

		int count = mobileTalentSearchDao.queryTalentSearchCount(mapParam);
		List<EmpDto> dtos = mobileTalentSearchDao
				.queryTalentSearch(mapParam);
		dto.setRecords(count);
		dto.setRows(dtos);
		return dto;
	}

	
}

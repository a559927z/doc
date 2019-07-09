package net.chinahrd.mvc.pc.service.admin.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.admin.OrganTypeDto;
import net.chinahrd.entity.dto.pc.admin.SoureOrganDto;
import net.chinahrd.mvc.pc.dao.admin.ImportDataDao;
import net.chinahrd.mvc.pc.dao.admin.OrganDao;
import net.chinahrd.mvc.pc.service.admin.ImportDataService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

/**
 * Created by jxzhang
 */
@Transactional
@Service("importDataService")
public class ImportDataServiceImpl implements ImportDataService {

	@Autowired
	private OrganDao organDao;

	@Autowired
	private ImportDataDao importDataDao;

	@Override
	public boolean addOrgans(List<Map<String, Object>> excelDataList,
			String customerId, String createUserId) {
		try {
			List<SoureOrganDto> dtos = getSourceOrgans(excelDataList,customerId);
			if(CollectionKit.isEmpty(dtos)){
				return false;
			}
			importDataDao.deleteSoureOrgan(customerId);
			importDataDao.addSoureOrgan(dtos);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private List<SoureOrganDto> getSourceOrgans(
			List<Map<String, Object>> excelDataList,String customerId) {
		List<SoureOrganDto> dtos = new ArrayList<SoureOrganDto>();
		for (Map<String, Object> excelData : excelDataList) {
			String buKey = (String) excelData.get("business_unit_key");
			String oTypeKey = (String) excelData.get("organization_type_key");
			String organKey = (String) excelData.get("organization_key");
			String parentKey = (String) excelData.get("organization_parent_key");
			String organName = (String) excelData.get("organization_name");
			String isSingle = (String) excelData.get("is_single");
			//TODO 字符专为 timestamp
			Timestamp effectData = DateUtil.getTimestamp();//(String) excelData.get("effect_data");
			String enabled = (String) excelData.get("enabled");

			if (!validateLen(buKey, 20) || !validateLen(organKey, 20) ||!validateLen(oTypeKey, 20)) { 
				return  null; 
			}

			SoureOrganDto soureOrganDto = new SoureOrganDto();
			soureOrganDto.setCustomerId(customerId);
			soureOrganDto.setBusinessUnitKey(buKey);
			soureOrganDto.setOrganizationTypeKey(oTypeKey);
			soureOrganDto.setOrganizationKey(organKey);
			soureOrganDto.setOrganizationParentKey(parentKey);
			soureOrganDto.setOrganizationName(organName);
			soureOrganDto.setIsSingle(Integer.valueOf(isSingle));
			soureOrganDto.setEffectData(effectData);
			soureOrganDto.setEnabled(Integer.valueOf(enabled));
			dtos.add(soureOrganDto);
		}
		return dtos;
	}

	
	/**
	 * 这里的验证是根据库里指定长度有关
	 * 
	 * @param source
	 * @param MaxLen
	 * @return
	 */
	private boolean validateLen(String source, int MaxLen) {
		return source.length() <= MaxLen;
	}
	
	
	
	// @Override
	public void analyseLogin2(List<Map<String, Object>> excelDataList,
			String customerId, String createUserId) {
		
		// 查出所有机构级别
		List<OrganTypeDto> oTypeDtos = importDataDao
				.queryOrganTypeAll(customerId);
		Map<String, Object> oTypeMap = CollectionKit.newMap();
		for (OrganTypeDto dto : oTypeDtos) {
			oTypeMap.put(dto.getOrganizationTypeKey(),
					dto.getOrganizationTypeId());
		}

		// 查出所有机构
		List<String> organKeyList = CollectionKit.newList();
		for (Map<String, Object> excelData : excelDataList) {
			String orgKey = (String) excelData.get("organization_key");
			organKeyList.add(orgKey);
		}
		List<OrganDto> organDtos = organDao.queryOrganAll(customerId,
				organKeyList);
		Map<String, Object> organMap = CollectionKit.newMap();
		if (organDtos != null) {
			for (OrganDto dto : organDtos) {
				organMap.put(dto.getOrganizationKey(), dto.getOrganizationId());
			}
		}

		// TODO Exception
		boolean isSuccess = saveOrganList(excelDataList, oTypeMap, organMap,
				customerId, createUserId);
	}

	private boolean saveOrganList(List<Map<String, Object>> excelDataList,
			 Map<String, Object> oTypeMap,
			Map<String, Object> OrganMap, String customerId, String createUserId) {

		boolean flg = true;
		List<OrganDto> dtos = new ArrayList<OrganDto>();
		for (Map<String, Object> excelData : excelDataList) {
			// TODO new object 时要快外面传入来 object type
			String buKey = (String) excelData.get("business_unit_key");
			String oTypeKey = (String) excelData.get("organization_type_key");
			String organKey = (String) excelData.get("organization_key");
			String organName = (String) excelData.get("organization_name");
			int isSingle = (int) excelData.get("is_single");

			if (!validateLen(buKey, 20)) {
				return flg = false;
			}
			if (!validateLen(organKey, 20)) {
				return flg = false;
			}
			if (!validateLen(oTypeKey, 20)) {
				return flg = false;
			}

			String organTypeId = (String) oTypeMap.get(oTypeKey);
			OrganDto organDto = new OrganDto();
			organDto.setOrganizationId(Identities.uuid2());
			organDto.setCustomerId(customerId);
			organDto.setOrganizationTypeId(organTypeId);
			organDto.setOrganizationKey(organKey);
			organDto.setOrganizationName(organName);
			organDto.setIsSingle(isSingle);
			dtos.add(organDto);
		}
		return flg;
	}

	

}

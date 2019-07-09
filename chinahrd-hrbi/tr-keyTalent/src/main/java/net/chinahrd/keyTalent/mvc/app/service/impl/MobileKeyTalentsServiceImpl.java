package net.chinahrd.keyTalent.mvc.app.service.impl;

import java.util.List;
import java.util.Map;

import net.chinahrd.keyTalent.mvc.app.dao.MobileKeyTalentsDao;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.keyTalent.mvc.app.service.MobileKeyTalentsService;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.entity.dto.app.keyTalents.KeyTalentPanelDto;
import net.chinahrd.entity.dto.app.keyTalents.KeyTalentsCardDto;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.entity.dto.app.common.EmpDto;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 关键人才库Service实现类 Created by htpeng on 2016/5/23 .
 */
@Service("mobileKeyTalentsService")
public class MobileKeyTalentsServiceImpl implements MobileKeyTalentsService {

	@Autowired
	private MobileKeyTalentsDao mobileKeyTalentsDao;

	@Override
	public List<KeyTalentsCardDto> queryRunoffRiskWarnEmp(String customerId,
			List<String> orgList) {
		return mobileKeyTalentsDao.queryRunoffRiskWarnEmp(customerId, orgList);
	}

	// @Override
	// public boolean addFocuseEmp(String customerId,String empId,String
	// keyTalentId) {
	// FocusesDto focusesDto=new FocusesDto();
	// focusesDto.setKeyTalentFocusesId(Identities.uuid2());
	// focusesDto.setCreateTime(DateUtil.getDBCurdate());
	// focusesDto.setCustomerId(customerId);
	// focusesDto.setEmpId(empId);
	// focusesDto.setKeyTalentId(keyTalentId);
	// try {
	// keyTalentsDao.removeFocuseEmp(customerId,empId,keyTalentId);
	// keyTalentsDao.addFocuseEmp(focusesDto);
	// return true;
	// } catch (Exception e) {
	// return false;
	// }
	//
	// }
	//
	// @Override
	// public boolean removeFocuseEmp(String customerId, String empId,String
	// keyTalentId) {
	// try {
	// keyTalentsDao.removeFocuseEmp(customerId,empId,keyTalentId);
	// return true;
	// } catch (Exception e) {
	// return false;
	// }
	// }
	//
	// @Override
	// public List<KeyTalentTagDto> queryTag(String customerId, String
	// keyTalentId) {
	// return keyTalentsDao.queryTag(customerId,keyTalentId);
	// }
	//

	// @Override
	// public KeyTalentDto queryKeyTalentById(String customerId, String
	// keyTalentId) {
	// return keyTalentsDao.queryKeyTalentById(customerId,keyTalentId);
	// }
	//

	// @Override
	// public List<KeyTalentLogDto> queryKeyTalentLog(String customerId,
	// String keyTalentId) {
	// return keyTalentsDao.queryKeyTalentLog(customerId,keyTalentId);
	// }

	@Override
	public List<KeyTalentPanelDto> queryKeyTalentTypePanel(String customerId,
			List<String> organList) {
		List<KeyTalentPanelDto> list = mobileKeyTalentsDao
				.queryKeyTalentTypePanel(customerId, "", organList);
		setTotal(list);
		return list;
	}

	@Override
	public List<KeyTalentPanelDto> queryKeyTalentOrganPanel(RbacUser user,
			String customerId, List<String> organList, boolean first) {
		List<KeyTalentPanelDto> list = CollectionKit.newList();

		if (organList.size() == 1) {
			list = mobileKeyTalentsDao.queryKeyTalentOrganPanel(customerId,
					organList.get(0), null, false);
		} else {
			list = mobileKeyTalentsDao.queryKeyTalentOrganPanel(customerId,
					null, organList, true);
		}
		if (first) {
			int total = setTotal(list);
			user.setCache("OrganTotal", total);
		} else {
			Object obj = user.getCache("OrganTotal");
			if (obj != null) {
				int total = Integer.parseInt(obj.toString());
				setTotal(list, total);
			}
		}
		return list;
	}

	private int setTotal(List<KeyTalentPanelDto> list, int total) {
		if (total == 0) {
			for (KeyTalentPanelDto keyTalentPanelDto : list) {
				total += keyTalentPanelDto.getCount();
			}
		}
		KeyTalentPanelDto keyTalentPanelDto = new KeyTalentPanelDto();
		keyTalentPanelDto.setId("-1");
		keyTalentPanelDto.setName("全部");
		keyTalentPanelDto.setCount(total);
		list.add(0, keyTalentPanelDto);
		return total;
	}

	private int setTotal(List<KeyTalentPanelDto> list) {
		return setTotal(list, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.chinahrd.biz.paper.mobile.service.MobileKeyTalentsService#getKeyTalentAll
	 * (java.lang.String, java.util.List,
	 * net.chinahrd.biz.paper.mobile.dto.common.PaginationDto)
	 */
	@Override
	public PaginationDto<KeyTalentsCardDto> getKeyTalentAll(String customerId,
			String empId, List<String> organPermitIds,
			PaginationDto<KeyTalentsCardDto> dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String, Object> mapParam = CollectionKit.newMap();
		mapParam.put("customerId", customerId);
		mapParam.put("empId", empId);
		mapParam.put("organPermitIds", organPermitIds);
		mapParam.put("rowBounds", rowBounds);
		int count = mobileKeyTalentsDao.queryKeyTalentAllCount(mapParam);
		List<KeyTalentsCardDto> dtos = mobileKeyTalentsDao
				.queryKeyTalentAll(mapParam);

		dto.setRecords(count);
		dto.setRows(dtos);
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.chinahrd.biz.paper.mobile.service.MobileKeyTalentsService#
	 * getKeyTalentByType(java.lang.String, java.lang.String, java.util.List,
	 * net.chinahrd.biz.paper.mobile.dto.common.PaginationDto)
	 */
	@Override
	public PaginationDto<KeyTalentsCardDto> getKeyTalentByType(
			String customerId, String empId, String key,
			List<String> organPermitIds, PaginationDto<KeyTalentsCardDto> dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String, Object> mapParam = CollectionKit.newMap();
		mapParam.put("customerId", customerId);
		mapParam.put("empId", empId);
		mapParam.put("organPermitIds", organPermitIds);
		mapParam.put("keyTalentTypeId", key);
		mapParam.put("rowBounds", rowBounds);
		int count = mobileKeyTalentsDao.queryKeyTalentByTypeCount(mapParam);
		List<KeyTalentsCardDto> dtos = mobileKeyTalentsDao
				.queryKeyTalentByType(mapParam);

		dto.setRecords(count);
		dto.setRows(dtos);
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.chinahrd.biz.paper.mobile.service.MobileKeyTalentsService#
	 * getKeyTalentByOrgan(java.lang.String, java.lang.String,
	 * net.chinahrd.biz.paper.mobile.dto.common.PaginationDto)
	 */
	@Override
	public PaginationDto<KeyTalentsCardDto> getKeyTalentByOrgan(
			String customerId, String empId, String key,
			PaginationDto<KeyTalentsCardDto> dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String, Object> mapParam = CollectionKit.newMap();
		mapParam.put("customerId", customerId);
		mapParam.put("empId", empId);
		mapParam.put("organizationId", key);
		mapParam.put("rowBounds", rowBounds);
		int count = mobileKeyTalentsDao.queryKeyTalentByOrganCount(mapParam);
		List<KeyTalentsCardDto> dtos = mobileKeyTalentsDao
				.queryKeyTalentByOrgan(mapParam);

		dto.setRecords(count);
		dto.setRows(dtos);
		return dto;
	}

	
	@Override
	public PaginationDto<EmpDto> queryKeyTalentByName(
			String customerId, String keyName, String empId,
			List<String> organPermitIds,PaginationDto<EmpDto> dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String, Object> mapParam = CollectionKit.newMap();
		mapParam.put("customerId", customerId);

		mapParam.put("empId", empId);
		mapParam.put("rowBounds", rowBounds);
		mapParam.put("keyName", keyName);
		mapParam.put("organPermitIds", organPermitIds);
		int count = mobileKeyTalentsDao.queryKeyTalentByNameCount(mapParam);
		List<EmpDto> dtos = mobileKeyTalentsDao
				.queryKeyTalentByName(mapParam);

		dto.setRecords(count);
		dto.setRows(dtos);
		return dto;
	}

}

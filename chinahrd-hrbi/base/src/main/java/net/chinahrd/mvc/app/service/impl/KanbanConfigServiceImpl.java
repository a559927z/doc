package net.chinahrd.mvc.app.service.impl;

import java.sql.Timestamp;
import java.util.List;

import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;
import net.chinahrd.mvc.app.dao.KanbanConfigDao;
import net.chinahrd.mvc.app.service.KanbanConfigService;
import net.chinahrd.utils.Identities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("kanbanConfigService")
public class KanbanConfigServiceImpl implements KanbanConfigService {
	@Autowired
	private KanbanConfigDao kanbanConfigDao;

	@Override
	public List<KanbanConfigMobileDto> getKanbanList(String customerId,
			String empId) {
		return kanbanConfigDao.getKanbanList(customerId, empId);
	}

	@Override
	public List<KanbanConfigMobileDto> getAlreadyAddedList(String customerId,
			String empId) {
		return kanbanConfigDao.getAlreadyAddedList(customerId, empId);
	}

	@Override
	public List<KanbanConfigMobileDto> getNotAddedList(String customerId,
			String empId) {
		return kanbanConfigDao.getNotAddedList(customerId, empId);
	}

	@Override
	public boolean addKanbanItem(KanbanConfigMobileDto dto) {
		return kanbanConfigDao.addKanbanItem(dto);
	}

	@Override
	public boolean deleteKanbanItem(String functionConfigId) {
		return kanbanConfigDao.deleteKanbanItem(functionConfigId);
	}

	@Override
	public void updateKanbanItem(String customerId,
			String empId, List<KanbanConfigMobileDto> dtos) {
		 kanbanConfigDao.updateKanbanItem(customerId, empId, dtos);
	}

	@Override
	public boolean addKanbanItem(String customerId, String empId,
			String functionId) {
		String uuid = Identities.uuid2();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		KanbanConfigMobileDto dto  = new KanbanConfigMobileDto();
		dto.setCreateTime(timestamp);
		dto.setEmpId(empId);
		dto.setCustomerId(customerId);
		dto.setFunctionId(functionId);
		dto.setFunctionConfigMobileId(uuid);
		return kanbanConfigDao.addKanbanItem(dto);
	}

}

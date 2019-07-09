package net.chinahrd.mvc.pc.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.chinahrd.entity.dto.pc.admin.DaysDto;
import net.chinahrd.mvc.pc.dao.admin.DaysDao;
import net.chinahrd.mvc.pc.service.admin.DaysService;

@Service("daysService")
public class DaysServiceImpl implements DaysService{

	@Autowired
	private DaysDao daysDao;
	@Override
	public List<DaysDto> queryDays(DaysDto days) {
		return daysDao.queryDays(days);
	}

}

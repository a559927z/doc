package net.chinahrd.mvc.pc.service.admin;

import java.util.List;

import net.chinahrd.entity.dto.pc.admin.DaysDto;

public interface DaysService {
	public List<DaysDto> queryDays(DaysDto days);
}

package net.chinahrd.mvc.pc.dao.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.pc.admin.BusinessUnitDto;
import net.chinahrd.entity.dto.pc.admin.OrganTypeDto;
import net.chinahrd.entity.dto.pc.admin.SoureOrganDto;

/**
 * Created by jxzhang
 */
@Repository("importDataDao")
public interface ImportDataDao {

	/**
	 * 查询业务单位
	 * 
	 * @param customerId
	 * @return
	 */
	List<BusinessUnitDto> queryBusinessUnitAll(
			@Param("customerId") String customerId);
	
	/**
	 * 查询机构级别
	 * @param customerId
	 * @return
	 */
	List<OrganTypeDto> queryOrganTypeAll(@Param("customerId") String customerId);
	
	Integer queryTest();
	
	void deleteSoureOrgan(@Param("customerId") String customerId);
	
	void addSoureOrgan(@Param("dtos") List<SoureOrganDto> dtos);
}

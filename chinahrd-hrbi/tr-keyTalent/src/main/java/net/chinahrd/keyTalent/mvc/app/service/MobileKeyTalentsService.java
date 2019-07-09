package net.chinahrd.keyTalent.mvc.app.service;

import java.util.List;

import net.chinahrd.entity.dto.app.common.EmpDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.entity.dto.app.keyTalents.KeyTalentPanelDto;
import net.chinahrd.entity.dto.app.keyTalents.KeyTalentsCardDto;

/**
 * 关键人才库Service接口类
 * Created by htpeng on 2016/5/23 .
 */
public interface MobileKeyTalentsService {

	/**
	 * 离职风险预警 查询
	 */
	
	List<KeyTalentsCardDto> queryRunoffRiskWarnEmp(String customerId,List<String> organList);
	
//	/**
//	 * 手动便签 查询
//	 */
//	List<KeyTalentTagDto> queryTag(String customerId,String keyTalentId);
//

//	/**
//	 * 根据关键人才Id
//	 */
//	KeyTalentDto queryKeyTalentById(String customerId,String keyTalentId);
//	/**
//	 * 关键人才核心激励要素查询
//	 * @return
//	 */
//	Map<String,Object> queryKeyTalentEncourage(String customerId,String keyTalentId);
//	


//	/**
//	 * 查询跟踪日志
//	 * @param customerId
//	 * @param keyTalentId
//	 */
//	List<KeyTalentLogDto> queryKeyTalentLog(String customerId,String keyTalentId);
//	

	
	/**
	 * 		 查询各个类型的关键人才数量 
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	List<KeyTalentPanelDto> queryKeyTalentTypePanel(String customerId,
			List<String> organList);


	/**
	 * 		 查询各个部门的关键人才数量 （含有下级）
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	List<KeyTalentPanelDto> queryKeyTalentOrganPanel(RbacUser user,String customerId,
			List<String> organList,boolean first);

	/**
	 * @param customerId
	 * @param organPermitId
	 * @param dto
	 * @return
	 */
	PaginationDto<KeyTalentsCardDto> getKeyTalentAll(
			String customerId,
			String empId,
			List<String> organPermitId,
			PaginationDto<KeyTalentsCardDto> dto);

	/**
	 * @param customerId
	 * @param key
	 * @param organPermitId
	 * @param dto
	 * @return
	 */
	PaginationDto<KeyTalentsCardDto> getKeyTalentByType(
			String customerId,
			String empId,
			String key,
			List<String> organPermitId,
			PaginationDto<KeyTalentsCardDto> dto);

	/**
	 * @param customerId
	 * @param key
	 * @param dto
	 * @return
	 */
	PaginationDto<KeyTalentsCardDto> getKeyTalentByOrgan(
			String customerId,
			String empId,
			String key,
			PaginationDto<KeyTalentsCardDto> dto);
	

	

	/**
	 * 关键人才列表
	 */

	PaginationDto<EmpDto> queryKeyTalentByName(String customerId,
			String keyName,String empId,List<String> organPermitIds,PaginationDto<EmpDto>dto);
}

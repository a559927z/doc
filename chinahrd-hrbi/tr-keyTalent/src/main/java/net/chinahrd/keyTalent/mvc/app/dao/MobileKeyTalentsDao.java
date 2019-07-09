package net.chinahrd.keyTalent.mvc.app.dao;


//import net.chinahrd.biz.paper.dto.common.EncourageDto;
//import net.chinahrd.biz.paper.dto.emp.FocusesDto;
//import net.chinahrd.biz.paper.dto.emp.KeyTalentDto;
//import net.chinahrd.biz.paper.dto.emp.KeyTalentLogDto;
//import net.chinahrd.biz.paper.dto.emp.KeyTalentTagDto;
import net.chinahrd.entity.dto.app.common.EmpDto;
import net.chinahrd.entity.dto.app.keyTalents.KeyTalentPanelDto;
import net.chinahrd.entity.dto.app.keyTalents.KeyTalentsCardDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 关键人才库Dao接口类
 * @author htpeng 2016年5月16日下午5:53:37
 */
@Repository("mobileKeyTalentsDao")
public interface MobileKeyTalentsDao {

	
	/**
	 * 离职风险预警 查询
	 */
	
	List<KeyTalentsCardDto> queryRunoffRiskWarnEmp(@Param("customerId")String customerId,
			@Param("list")List<String> organPermitIds
			);

   


	
	/**
	 * 		 查询各个类型的关键人才数量 
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	List<KeyTalentPanelDto> queryKeyTalentTypePanel(@Param("customerId")String customerId,
			@Param("userId")String userId,
			@Param("organPermitIds")List<String> organPermitIds
			);



	/**
	 * 		 查询各个部门的关键人才数量 （含有下级）
	 * @param customerId
	 * @param organizationId
	 * @return
	 */
	List<KeyTalentPanelDto> queryKeyTalentOrganPanel(@Param("customerId")String customerId,
			@Param("organId")String organId,@Param("list")List<String> organList,@Param("more")boolean more);
	


	

	/**
	 * 关键人才列表
	 */

	List<EmpDto> queryKeyTalentByName(Map<String, Object> mapParam);
	Integer queryKeyTalentByNameCount(Map<String, Object> mapParam);

	/**
	 * @param mapParam
	 * @return
	 */
	List<KeyTalentsCardDto> queryKeyTalentAll(Map<String, Object> mapParam);

	/**
	 * @param mapParam
	 * @return
	 */
	Integer queryKeyTalentAllCount(Map<String, Object> mapParam);

	/**
	 * 根据类型查询关键人才数量
	 * @param customerId
	 * @param organizationId
	 * @param curdate
	 * @return
	 */
	Integer queryKeyTalentByTypeCount(Map<String, Object> mapParam);

	/**
	 * 根据类型查询关键人才
	 * @param customerId
	 * @param organizationId
	 * @param curdate
	 * @return
	 */
	List<KeyTalentsCardDto> queryKeyTalentByType(Map<String, Object> mapParam);

	
	/**
	 * 		根据部门查询关键人才  数量
	 * @return
	 */
	Integer queryKeyTalentByOrganCount(Map<String, Object> mapParam);

	/**
	 * 		根据部门查询关键人才 
	 * @return
	 */
	List<KeyTalentsCardDto> queryKeyTalentByOrgan(Map<String, Object> mapParam);
	
}

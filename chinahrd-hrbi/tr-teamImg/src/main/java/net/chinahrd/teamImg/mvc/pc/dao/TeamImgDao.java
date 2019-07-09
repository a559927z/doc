package net.chinahrd.teamImg.mvc.pc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.teamImg.TeamImgEmpDto;

/**
 * by jxzhang
 */
@Repository("teamImgDao")
public interface TeamImgDao {
	
	Integer abc();
	void abc2();
	void abc3();

	/**
	 * 管理者首页-团队画像
	 * 
	 * @param organId
	 * @param customerId
	 * @return
	 */
	List<TeamImgEmpDto> queryTeamImg(@Param("organId") String organId, @Param("customerId") String customerId);

	/**
	 * 员工性格
	 * 
	 * @param organId
	 * @param customerId
	 * @return
	 */
	List<KVItemDto> queryEmpPersonality(@Param("organId") String organId, @Param("customerId") String customerId);

	/**
	 * 分组统计能力层级人数
	 * 
	 * @param organId
	 * @param customerId
	 * @return
	 */
	List<KVItemDto> groupCountTeamImgAb(@Param("organId") String organId, @Param("customerId") String customerId);


}

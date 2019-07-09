package net.chinahrd.teamImg.mvc.app.dao;

import java.util.List;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.app.teamImg.TeamImgDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 团队画像 app dao
 * @author htpeng
 *2016年6月15日下午5:12:19
 */
@Repository("mobileTeamImgDao")
public interface MobileTeamImgDao {


	/**
	 * 管理者首页-团队画像
	 * @param organId
	 * @param customerId
	 * @return
	 */
    List<TeamImgDto> queryTeamImg(@Param("organId")String organId, @Param("customerId") String customerId);
    
    /**
     * 员工性格
     * @param organId
     * @param customerId
     * @return
     */
    List<KVItemDto> queryEmpPersonality(@Param("organId")String organId, @Param("customerId") String customerId);
}

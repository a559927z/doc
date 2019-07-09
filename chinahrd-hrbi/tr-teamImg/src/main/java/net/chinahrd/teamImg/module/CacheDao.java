package net.chinahrd.teamImg.module;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CacheDao {
	List<String> queryTeamImgOrgan(@Param("customerId")String customerId);
}

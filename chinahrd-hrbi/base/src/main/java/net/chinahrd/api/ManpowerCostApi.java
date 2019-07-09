package net.chinahrd.api;

import java.util.List;

import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerCompareDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerItemDto;

/**
 * 人力成本分析的 Api 接口
 * @author xwli
 * 2017年5月8日
 */
public interface ManpowerCostApi {
	List<ManpowerItemDto> queryItemDetail(String customerId, String organId, String time);
	
	List<ManpowerDto> getAllDetailData(String customerId, String organId, String time);
	
	List<ManpowerCompareDto> getProportionYear(String customerId, String organId, String time);
}

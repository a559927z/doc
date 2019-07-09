package net.chinahrd.api;

import java.util.List;

import net.chinahrd.entity.dto.pc.recruitBoard.RecruitChannelResultDto;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitPositionMeetRateDto;

/**
 * 招聘看板的 Api 接口
 * @author xwli
 * 2017年5月8日
 */
public interface RecruitBoardApi {
	List<RecruitPositionMeetRateDto> getPositionMeetRate(String customerId, String empId, String organId);
    
    List<RecruitChannelResultDto> getRecruitChannel(String customerId, String keyName, String organId);
}

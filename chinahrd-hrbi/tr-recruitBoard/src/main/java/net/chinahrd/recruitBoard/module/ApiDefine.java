/**
 * net.chinahrd.cache
 */
package net.chinahrd.recruitBoard.module;

import net.chinahrd.api.RecruitBoardApi;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitChannelResultDto;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitPositionMeetRateDto;
import net.chinahrd.recruitBoard.mvc.pc.service.RecruitBoardService;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author wqcai
 */
public class ApiDefine extends ApiRegisterAbstract implements RecruitBoardApi {
    private static final Logger log = Logger.getLogger(ApiDefine.class);
    
    @Injection
    private RecruitBoardService recruitBoardService;

    /* (non-Javadoc)
     * @see net.chinahrd.core.api.ApiRegister#getApiType()
     */
    @Override
    public ApiType getApiType() {
        return ApiType.INTERFACE;
    }
    
    public List<RecruitPositionMeetRateDto> getPositionMeetRate(String customerId, String empId, String organId) {
        List<RecruitPositionMeetRateDto> dtos = recruitBoardService.queryPositionMeetRate(organId, empId, customerId);
        return dtos;
    }
    
    public List<RecruitChannelResultDto> getRecruitChannel(String customerId, String keyName, String organId) {
        return recruitBoardService.queryRecruitChannel(keyName, organId, customerId);
    }
}

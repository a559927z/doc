/**
*net.chinahrd.cache
*/
package net.chinahrd.trainBoard.module;

import net.chinahrd.api.TrainBoardApi;
import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.api.ApiRegisterAbstract;
import net.chinahrd.core.api.config.ApiType;
import net.chinahrd.entity.dto.pc.trainBoard.TrainTypeDto;
import net.chinahrd.trainBoard.mvc.pc.service.TrainBoardService;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author htpeng
 *2016年10月13日上午11:54:56
 */
public class ApiDefine extends ApiRegisterAbstract implements TrainBoardApi {
	private static final Logger log = Logger.getLogger(ApiDefine.class);
	
	@Injection
	private TrainBoardService trainBoardService;
	/* (non-Javadoc)
	 * @see net.chinahrd.core.api.ApiRegister#getApiType()
	 */
	@Override
	public ApiType getApiType() {
		return ApiType.INTERFACE;
	}
	
	public Map<String, Object> getSubOrganizationCover(String customerId, String organId) {
        return trainBoardService.getSubOrganizationCover(customerId, organId);
    }
	
	public Map<String, Object>  getTrainingType(String customerId, String organId) {
        return trainBoardService.getTrainingType(customerId, organId);
    }
	
	public List<TrainTypeDto> findTrainingTypeRecord(String customerId, String type,String organId) {
        return trainBoardService.findTrainingTypeRecordNoPage(type, organId, customerId);
    }
}

package net.chinahrd.mvc.pc.service.common;

import java.util.List;

import net.chinahrd.entity.dto.pc.common.MessageResultDto;

/**
 * 消息Service接口类
 * Created by htpeng on 16/1/13.
 */
public interface MessageService {

    /**
     * 查询消息信息
     * @param customerId 客户ID
     * @param quotaId 指标ID
     * @param organizationId 机构ID
     * @param empId 员工ID
     * @return
     */
    List<MessageResultDto> findAllMessage(String customerId,String organizationId,String empId);

	boolean readMessage(String customerId, String organizationId, String userEmpId);
	
	/**
	 * 添加点对点消息
	 * @param customerId
	 * @param createEmpId
	 * @param content
	 * @param url
	 * @param EmpList
	 * @return 
	 */
	boolean addPtpMessage(String customerId, String createEmpId, String content, List<String> empList, String url);
    
}

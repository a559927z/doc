package net.chinahrd.mvc.pc.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.chinahrd.entity.dto.pc.common.MessageCreateDto;
import net.chinahrd.entity.dto.pc.common.MessageResultDto;
import net.chinahrd.entity.dto.pc.common.MessageStatusCreateDto;

/**
 * 消息Dao接口类
 * Created by htpeng on 16/1/13.
 */
@Repository("messageDao")
public interface MessageDao {

    /**
     * 查询消息信息
     * @param quotaId
     * @param organizationId
     * @param empId
     * @return
     */
    List<MessageResultDto> findAllMessage(@Param("customerId")String customerId,@Param("organizationId")String organizationId,@Param("empId")String empId);

    
    void readMessage(@Param("customerId")String customerId,@Param("organizationId")String organizationId,@Param("empId")String empId);

    void deleteMessage(@Param("customerId")String customerId,@Param("messageId")String messageId);
    
    /**
     * 添加消息信息
     * @param dto
     */
    void addMessage(@Param("dto")MessageCreateDto dto);
    /**
     * 添加消息状态信息
     * @param dto
     */
    void addMessageStatus(@Param("list")List<MessageStatusCreateDto>list);
    
    void readMessageById(@Param("customerId")String customerId,@Param("messageId")String messageId,@Param("empId")String empId);
}

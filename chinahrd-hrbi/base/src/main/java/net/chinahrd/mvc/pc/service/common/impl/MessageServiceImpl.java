package net.chinahrd.mvc.pc.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.chinahrd.entity.dto.pc.common.MessageCreateDto;
import net.chinahrd.entity.dto.pc.common.MessageResultDto;
import net.chinahrd.entity.dto.pc.common.MessageStatusCreateDto;
import net.chinahrd.mvc.pc.dao.common.MessageDao;
import net.chinahrd.mvc.pc.service.common.MessageService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

/**
 * 备忘录Service实现类
 * Created by wqcai on 15/6/30.
 */
@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public List<MessageResultDto> findAllMessage(String customerId,  String organizationId, String empId) {
        List<MessageResultDto> dtos = messageDao.findAllMessage(customerId, organizationId, empId);
        return dtos;
    }

	@Override
	public boolean readMessage(String customerId, String organizationId,
			String empId) {
		 messageDao.readMessage(customerId, organizationId, empId);
		return true;
	}

	@Override
	@Transactional
	public boolean addPtpMessage(String customerId, String createEmpId, String content, List<String> empList, String url) {
		try {
			MessageCreateDto mcd = new MessageCreateDto();
			String messageId=Identities.uuid2();
			mcd.setMessageId(messageId);
			mcd.setCustomerId(customerId);
			mcd.setQuataId("");
			mcd.setOrganizationId("");
			mcd.setContent(content);  //+链接地址
		    mcd.setType(1);
		    mcd.setUrl(url);
		    mcd.setCreateEmpId(createEmpId);
		    mcd.setCreateTime(DateUtil.getTimestamp());
		    messageDao.addMessage(mcd);
			    
			List<MessageStatusCreateDto> dtos = CollectionKit.newList();
			for(String s : empList){
				MessageStatusCreateDto mscd = new MessageStatusCreateDto();
				mscd.setMessageStatusId(Identities.uuid2());
				mscd.setCustomerId(customerId);
				mscd.setMessageId(messageId);
				mscd.setEmpId(s);
				dtos.add(mscd);
			}
			messageDao.addMessageStatus(dtos);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

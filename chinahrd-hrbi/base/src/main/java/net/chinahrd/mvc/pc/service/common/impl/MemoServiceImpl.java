package net.chinahrd.mvc.pc.service.common.impl;

import java.util.List;

import net.chinahrd.entity.dto.pc.admin.UserDto;
import net.chinahrd.entity.dto.pc.common.ItemDto;
import net.chinahrd.entity.dto.pc.common.MemoCreateDto;
import net.chinahrd.entity.dto.pc.common.MemoResultDto;
import net.chinahrd.entity.dto.pc.common.MemoStatusDto;
import net.chinahrd.entity.dto.pc.common.MessageCreateDto;
import net.chinahrd.entity.dto.pc.common.MessageStatusCreateDto;
import net.chinahrd.mvc.pc.dao.admin.OrganDao;
import net.chinahrd.mvc.pc.dao.common.MemoDao;
import net.chinahrd.mvc.pc.dao.common.MessageDao;
import net.chinahrd.mvc.pc.service.common.MemoService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 备忘录Service实现类
 * Created by wqcai on 15/6/30.
 */
@Service("memoService")
@Transactional
public class MemoServiceImpl implements MemoService {

    @Autowired
    private MemoDao memoDao;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private OrganDao organDao;
    
    @Override
    public List<MemoResultDto> findAllMemo(String customerId, String quotaId, String organizationId, String empId) {
        List<MemoResultDto> dtos = memoDao.findAllMemo(customerId, quotaId, organizationId, empId);
        return dtos;
    }

    @Override
    public void addMemo(MemoCreateDto dto) {
        memoDao.addMemo(dto);
        MessageCreateDto mcd=new MessageCreateDto();
        mcd.setMessageId(dto.getMemoId());
        mcd.setCustomerId(dto.getCustomerId());
        mcd.setQuataId(dto.getQuataId());
        mcd.setOrganizationId(dto.getOrganizationId());
        mcd.setContent(dto.getContent());
        mcd.setType(2);
        mcd.setCreateEmpId(dto.getCreateEmpId());
        mcd.setCreateTime(dto.getCreateTime());
        messageDao.addMessage(mcd);
        
        MemoStatusDto statusDto = new MemoStatusDto();
        statusDto.setMemoStatusId(Identities.uuid2());
        statusDto.setCustomerId(dto.getCustomerId());
        statusDto.setMemoId(dto.getMemoId());
        statusDto.setEmpId(dto.getCreateEmpId());
        statusDto.setReadTime(DateUtil.getTimestamp());
        memoDao.addMemoStatus(statusDto);
        
        List<UserDto> list = organDao.queryEmpsByOrganId(dto.getCustomerId(),dto.getOrganizationId());
        List<MessageStatusCreateDto> dtos = CollectionKit.newList();
        for (UserDto user : list) {
        	  if(user.getEmpId().equals(dto.getCreateEmpId())){
        		  continue;
        	  }
        	  MessageStatusCreateDto mscd = new MessageStatusCreateDto();
              mscd.setMessageStatusId(Identities.uuid2());
              mscd.setCustomerId(dto.getCustomerId());
              mscd.setMessageId(dto.getMemoId());
              mscd.setEmpId(user.getEmpId());
              mscd.setReadTime(DateUtil.getTimestamp());

            dtos.add(mscd);
        }
      // memoDao.addMemoStatus(dtos);
      
        messageDao.addMessageStatus(dtos);
       
    }


    @Override
    public void deleteMemo(String customerId, String memoId) {
        memoDao.deleteMemo(customerId, memoId);
        messageDao.deleteMessage(customerId, memoId);
    }

    @Override
    public void updateMemoIsReadStatus(String customerId, String memoId, String empId) {
        MemoStatusDto statusDto = new MemoStatusDto();
        statusDto.setMemoStatusId(Identities.uuid2());
        statusDto.setCustomerId(customerId);
        statusDto.setMemoId(memoId);
        statusDto.setEmpId(empId);
        statusDto.setReadTime(DateUtil.getTimestamp());
        memoDao.addMemoStatus(statusDto);
        messageDao.readMessageById(customerId, memoId, empId);
    }

    @Override
    public List<ItemDto> findMemoTips(String customerId) {
        return memoDao.findMemoTips(customerId);
    }
}

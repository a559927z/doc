package net.chinahrd.keyTalent.mvc.pc.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.common.EncourageDto;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.common.MessageCreateDto;
import net.chinahrd.entity.dto.pc.common.MessageStatusCreateDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.emp.FocusesDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentEncourageDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentLogDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentPanelDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentTagDto;
import net.chinahrd.entity.dto.pc.emp.KeyTalentsCardDto;
import net.chinahrd.keyTalent.mvc.pc.dao.KeyTalentsDao;
import net.chinahrd.keyTalent.mvc.pc.service.KeyTalentsService;
import net.chinahrd.module.SysCache;
import net.chinahrd.mvc.pc.dao.common.MessageDao;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 关键人才库Service实现类
 * Created by wqcai on 2016/1/14 014.
 */
@Service("keyTalentsService")
public class KeyTalentsServiceImpl implements KeyTalentsService {

    @Autowired
    private KeyTalentsDao keyTalentsDao;
    
    @Autowired
    private  MessageDao messageDao;

	@Override
	public List<KeyTalentsCardDto> queryRunoffRiskWarnEmp(String customerId,
			String userId,String empId) {
		return keyTalentsDao.queryRunoffRiskWarnEmp(customerId,userId,empId, getOrganPermitId());
	}

	@Override
	public List<KeyTalentsCardDto> queryFocuseEmp(String customerId,
			String userId, String empId) {
		return keyTalentsDao.queryFocuseEmp(customerId,userId,empId, getOrganPermitId());
	}

	@Override
	public Map<String, Object> queryLastRefreshEmp(String customerId,
			String userId,String empId, PaginationDto<KeyTalentsCardDto> dto) {
		Map<String, Object> rmap = new HashMap<String, Object>();
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("customerId", customerId);
		mapParam.put("curdate", DateUtil.getDBCurdate());
		mapParam.put("empId", empId);
		mapParam.put("organPermitIds", getOrganPermitId());
		mapParam.put("userId", userId);
		mapParam.put("rowBounds", rowBounds);
		rmap.put("list", keyTalentsDao.queryLastRefreshEmp(mapParam));
		rmap.put("count", keyTalentsDao.queryLastRefreshEmp_Count(mapParam));
		return rmap;
	}

	@Override
	public boolean addFocuseEmp(String customerId,String empId,String keyTalentId) {
		FocusesDto focusesDto=new FocusesDto();
		focusesDto.setKeyTalentFocusesId(Identities.uuid2());
		focusesDto.setCreateTime(DateUtil.getDBCurdate());
		focusesDto.setCustomerId(customerId);
		focusesDto.setEmpId(empId);
		focusesDto.setKeyTalentId(keyTalentId);
		 try {
			 keyTalentsDao.removeFocuseEmp(customerId,empId,keyTalentId);
			keyTalentsDao.addFocuseEmp(focusesDto);
			 return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean removeFocuseEmp(String customerId, String empId,String keyTalentId) {
		 try {
				keyTalentsDao.removeFocuseEmp(customerId,empId,keyTalentId);
				 return true;
			} catch (Exception e) {
				return false;
			}
	}

	@Override
	public boolean deleteKeyTalent(String customerId,String createEmpId, String keyTalentId) {
		 try {
				KeyTalentDto kto=keyTalentsDao.queryKeyTalentById(customerId,keyTalentId);
				keyTalentsDao.deleteKeyTalent(customerId,keyTalentId);
				keyTalentsDao.updateEmpTable(customerId,kto.getEmpId(),0);
				String parentId=keyTalentsDao.queryEmpInfo(customerId,kto.getEmpId());
				List<String> list=CollectionKit.newList();
				list.add(kto.getCreateEmpId());
				list.add(parentId);
				//				您确定要将“刘颖”从关键人才库中移除吗？
//				删除信息会通知添加者以及“刘颖”的直接上级
				   MessageCreateDto mcd=new MessageCreateDto();
				   String messageId=Identities.uuid2();
			        mcd.setMessageId(messageId);
			        mcd.setCustomerId(customerId);
			        mcd.setQuataId("");
			        mcd.setOrganizationId("");
			        mcd.setContent("将\""+kto.getEmpName()+"\"从关键人才库中移除");
			        mcd.setType(1);
			        mcd.setCreateEmpId(createEmpId);
			        mcd.setCreateTime(DateUtil.getTimestamp());
			        messageDao.addMessage(mcd);
			        
			       //上级领导  添加该关键人才的人
			        List<MessageStatusCreateDto> dtos = CollectionKit.newList();
			        for(String s:list){
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

	@Override
	public boolean addKeyTalent(String customerId,String createEmpId,String empId,String keyTalentTypeId) {
		
		KeyTalentDto keyTalentDto=new KeyTalentDto();
		String keyTalentId=Identities.uuid2();
		String createTime=DateUtil.getDBCurdate();
		keyTalentDto.setKeyTalentId(keyTalentId);
		keyTalentDto.setCustomerId(customerId);
		keyTalentDto.setCreateEmpId(createEmpId);
		keyTalentDto.setEmpId(empId);
		keyTalentDto.setKeyTalentTypeId(keyTalentTypeId);
		keyTalentDto.setDelete(0);
		keyTalentDto.setSychron(0);
		keyTalentDto.setRefresh(createTime);
		keyTalentDto.setCreateTime(createTime);
		keyTalentDto.setRefreshEncourage(createTime);
		keyTalentDto.setRefreshLog(createTime);
		keyTalentDto.setRefreshTag1(createTime);
		keyTalentDto.setRefreshTag2(createTime);

		FocusesDto focusesDto=new FocusesDto();
		focusesDto.setKeyTalentFocusesId(Identities.uuid2());
		focusesDto.setCreateTime(createTime);
		focusesDto.setCustomerId(customerId);
		focusesDto.setEmpId(createEmpId);
		focusesDto.setKeyTalentId(keyTalentId);
		 try {
				keyTalentsDao.addKeyTalent(keyTalentDto);
				keyTalentsDao.updateEmpTable(customerId,empId,1);
				String typeKey=SysCache.KeyTalentType.get().get(keyTalentTypeId);
				if(typeKey.equals("other")){
					keyTalentsDao.addFocuseEmp(focusesDto);
				}
				 return true;
			} catch (Exception e) {
				return false;
			}
	}

	@Override
	public List<KeyTalentTagDto> queryTag(String customerId, String keyTalentId) {
		return keyTalentsDao.queryTag(customerId,keyTalentId);
	}

	@Override
	public List<KeyTalentTagDto> queryHistoryTag(String customerId,
			String keyTalentId) {

		return keyTalentsDao.queryHistoryTag(customerId,keyTalentId);
	}

	@Override
	public boolean addKeyTalentTag(String customerId,String keyTalentId,String createEmpId,String createEmpName,String tags ,String type) {

		List<KeyTalentTagDto> list =CollectionKit.newList();
		String createTime=DateUtil.getDBCurdate();
		if(tags.indexOf(",")>0){
			tags=tags.replace(",", ";");
		}
		if(tags.indexOf("，")>0){
			tags=tags.replace("，", ";");
		}
		if(tags.indexOf("\n")>-1){
			tags=tags.replace("\n", ";");
		}
		if(!(tags.indexOf(",")>0 || tags.indexOf("，")>0 || tags.indexOf("\n")>0)){
			tags=tags+";";
		}
		String []lableName = tags.split(";");
		for(String tag:lableName){
			if (!"".equals(tag)) {
				KeyTalentTagDto ktt = new KeyTalentTagDto();
				ktt.setTagId(Identities.uuid2());
				ktt.setCustomerId(customerId);
				ktt.setHistoryTagId(Identities.uuid2());
				ktt.setCreateTime(createTime);
				ktt.setContent(tag);
				ktt.setActionType(1);
				ktt.setType(Integer.parseInt(type));
				ktt.setCreateEmpId(createEmpId);
				ktt.setCreateEmpName(createEmpName);
				ktt.setKeyTalentId(keyTalentId);
				list.add(ktt);
			}
		}
		try {
			keyTalentsDao.addKeyTalentTag(list);
			keyTalentsDao.addKeyTalentHistoryTag(list);
			if(Integer.parseInt(type)==1){
				keyTalentsDao.refreshKeyTalentTag1(customerId,keyTalentId,createTime);
			}else{
				keyTalentsDao.refreshKeyTalentTag2(customerId,keyTalentId,createTime);
			}
			
			 return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteKeyTalentTag(String customerId,String createEmpName, String tagId,String type) {
		try {
			KeyTalentTagDto ktto=keyTalentsDao.queryTagById(customerId, tagId);
			String createTime=DateUtil.getDBCurdate();
			keyTalentsDao.deleteKeyTalentTag(customerId,tagId);
			List<KeyTalentTagDto> list =CollectionKit.newList();
			KeyTalentTagDto ktt=new KeyTalentTagDto();
			ktt.setHistoryTagId(Identities.uuid2());
			ktt.setCreateTime(createTime);
			ktt.setContent(ktto.getContent());
			ktt.setActionType(2);
			ktt.setCustomerId(customerId);
			ktt.setType(Integer.parseInt(type));
		//	ktt.setCreateEmpId(createEmpId);
			ktt.setCreateEmpName(createEmpName);
			ktt.setKeyTalentId(ktto.getKeyTalentId());
			list.add(ktt);
			keyTalentsDao.addKeyTalentHistoryTag(list);
			if(Integer.parseInt(type)==1){
				keyTalentsDao.refreshKeyTalentTag1(customerId,ktto.getKeyTalentId(),createTime);
			}else{
				keyTalentsDao.refreshKeyTalentTag2(customerId,ktto.getKeyTalentId(),createTime);
			}
			 return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public KeyTalentDto queryKeyTalentById(String customerId, String keyTalentId) {
		return keyTalentsDao.queryKeyTalentById(customerId,keyTalentId);
	}

	@Override
	public Map<String,Object> queryKeyTalentEncourage(String customerId,
			String keyTalentId) {
		List<KVItemDto> list=keyTalentsDao.queryKeyTalentEncourage(customerId,keyTalentId);
		KeyTalentDto kto=keyTalentsDao.queryKeyTalentById(customerId,keyTalentId);
		Map<String,Object> map=CollectionKit.newMap();
		if(null==kto){
			kto=new KeyTalentDto();
		}
		List<String> contentList=CollectionKit.newList();
		if(null!=list&&list.size()>0){
			for(int i=0;i<list.size();i++){
				contentList.add(list.get(i).getV());
			}
			
		}
		map.put("note", kto.getNote());
		map.put("time", kto.getRefreshEncourage());
		map.put("optUser", kto.getModityEncourageEmpName() );
		map.put("content", contentList);
		return map;
	}

	@Override
	public List<EncourageDto> queryEncourage(String customerId,
			String keyTalentId) {
		return keyTalentsDao.queryEncourage(customerId, keyTalentId);
	}

	
//	private boolean deleteKeyTalentEncourage(String customerId, String keyTalentId) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public boolean updateKeyTalentEncourage(String customerId,String createEmpId,String str,String keyTalentId,String note) {
		try {
			List<KeyTalentEncourageDto> list =CollectionKit.newList();
			keyTalentsDao.deleteKeyTalentEncourage(customerId, keyTalentId);
			String createTime=DateUtil.getDBCurdate();
			if(str!=null&&str.trim().length()>0){
				String []encourages = str.split(";");
				for(String encourage:encourages){
					KeyTalentEncourageDto kto=new KeyTalentEncourageDto();
					kto.setKeyTalentEncourageId(Identities.uuid2());
					kto.setCreateEmpId(createEmpId);
					kto.setCreateTime(createTime);
					kto.setCustomerId(customerId);
					kto.setEncourageId(encourage);
					kto.setKeyTalentId(keyTalentId);
					list.add(kto);
				}
				
				keyTalentsDao.addKeyTalentEncourage(list);
			}
			keyTalentsDao.refreshKeyTalentEncourage(customerId,keyTalentId,createTime,note,createEmpId);
			return true;
		} catch (Exception e) {
			return false;
		}
		
		
	}

	@Override
	public boolean addKeyTalentLog(String customerId,String createEmpId,String keyTalentId,String content) {
		try {
			String createTime=DateUtil.getDBCurdate();
			KeyTalentLogDto keyTalentLogDto =new KeyTalentLogDto();
			keyTalentLogDto.setContent(content);
			keyTalentLogDto.setCreateEmpId(createEmpId);
			keyTalentLogDto.setCreateTime(createTime);
			keyTalentLogDto.setCustomerId(customerId);
			keyTalentLogDto.setKeyTalentId(keyTalentId);
			keyTalentLogDto.setKeyTalentLogId(Identities.uuid2());
			keyTalentLogDto.setRefresh(createTime);
			keyTalentsDao.addKeyTalentLog(keyTalentLogDto);
			keyTalentsDao.refreshKeyTalentLog(customerId,keyTalentId,createTime);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<KeyTalentLogDto> queryKeyTalentLog(String customerId,
			String keyTalentId) {
		return keyTalentsDao.queryKeyTalentLog(customerId,keyTalentId);
	}

	@Override
	public boolean updateKeyTalentLog(String customerId,String keyTalentId,String createEmpId, String keyTalentLogId,
			String content) {
		try {
			String createTime=DateUtil.getDBCurdate();
			keyTalentsDao.updateKeyTalentLog(customerId,keyTalentLogId,content,createTime);
			keyTalentsDao.refreshKeyTalentLog(customerId,keyTalentId,createTime);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteKeyTalentLog(String customerId,String keyTalentId,String createEmpId, String keyTalentLogId) {
		try {
			keyTalentsDao.deleteKeyTalentLog(customerId,keyTalentLogId);
			keyTalentsDao.refreshKeyTalentLog(customerId,keyTalentId,DateUtil.getDBCurdate());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<KeyTalentPanelDto> queryKeyTalentTypePanel(
			String customerId, String userId) {
		return keyTalentsDao.queryKeyTalentTypePanel(customerId,userId, getOrganPermitId());
	}

	@Override
	public PaginationDto<KeyTalentsCardDto> queryKeyTalentByType(String customerId,
			String userId,String keyTalentTypeId,String empId,String order,PaginationDto<KeyTalentsCardDto>dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String,Object> mapParam=CollectionKit.newMap();
		mapParam.put("customerId", customerId);
		mapParam.put("userId", userId);
		mapParam.put("keyTalentTypeId", keyTalentTypeId);
		mapParam.put("empId", empId);
		mapParam.put("rowBounds", rowBounds);
		mapParam.put("order", getOrder(order));
		mapParam.put("organPermitIds",getOrganPermitId());

		int count = keyTalentsDao.queryKeyTalentByTypeCount(mapParam);
        List<KeyTalentsCardDto> dtos = keyTalentsDao.queryKeyTalentByType(mapParam);
        
        dto.setRecords(count);
        dto.setRows(dtos);
		return dto;
	}

	@Override
	public List<KeyTalentPanelDto> queryKeyTalentEncouragePanel(
			String customerId, String userId) {
		return keyTalentsDao.queryKeyTalentEncouragePanel(customerId,userId, getOrganPermitId());
	}

	@Override
	public PaginationDto<KeyTalentsCardDto> queryKeyTalentByEncourage(String customerId,
			String userId,String encouragesId,String empId,
			String order,PaginationDto<KeyTalentsCardDto>dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String,Object> mapParam=CollectionKit.newMap();
		mapParam.put("customerId", customerId);
		mapParam.put("userId", userId);
		mapParam.put("encouragesId", encouragesId);
		mapParam.put("empId", empId);
		mapParam.put("rowBounds", rowBounds);
		mapParam.put("order", getOrder(order));
		mapParam.put("organPermitIds",getOrganPermitId());
		int count = keyTalentsDao.queryKeyTalentByEncourageCount(mapParam);
        List<KeyTalentsCardDto> dtos = keyTalentsDao.queryKeyTalentByEncourage(mapParam);
        
        dto.setRecords(count);
        dto.setRows(dtos);
		return dto;
	}

	@Override
	public List<KeyTalentPanelDto> queryKeyTalentOrganPanel(
			String customerId, String userId) {

		return handleOrganData(keyTalentsDao.queryKeyTalentOrganPanel(customerId,userId, getOrganPermitId()));
	}

	@Override
	public PaginationDto<KeyTalentsCardDto> queryKeyTalentByOrgan(String customerId,
			String organizationId,String empId,String order,PaginationDto<KeyTalentsCardDto>dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String,Object> mapParam=CollectionKit.newMap();
		mapParam.put("customerId", customerId);
		mapParam.put("organizationId", organizationId);
		mapParam.put("empId", empId);
		mapParam.put("rowBounds", rowBounds);
		mapParam.put("order", getOrder(order));
		int count = keyTalentsDao.queryKeyTalentByOrganCount(mapParam);
        List<KeyTalentsCardDto> dtos = keyTalentsDao.queryKeyTalentByOrgan(mapParam);
        
        dto.setRecords(count);
        dto.setRows(dtos);
		return dto;
	}
	@Override
	public List<KVItemDto> queryKeyTalentType(String customerId) {
		return keyTalentsDao.queryKeyTalentType(customerId);
	}

	/**
	 * 获取排序
	 * @param order
	 * @return
	 */
	private String getOrder(String order){
		if(null==order){
			return "";
		}else if(order.equals("warn")){
			//return " ORDER BY dr.risk_flag ";
			return "1";
		}else if(order.equals("refresh")){
//			return " ORDER BY kt.refresh ";
			return "2";
		}else if(order.equals("ability")){
//			return " ORDER BY vde.ability_id ";
			return "3";
		}else if(order.equals("createTime")){
//			return " ORDER BY kt.create_time ";
			return "4";
		}
		return "";
	}
	
	private List<KeyTalentPanelDto> handleOrganData(List<KeyTalentPanelDto> list){
		if(null==list){
			return null;
		}
		Collections.reverse(list);
		for(KeyTalentPanelDto k:list){
			for(KeyTalentPanelDto o:list){
				if(k.getPid().equals(o.getId())){
					o.setCount(o.getCount()+k.getCount());
					break;
				}
			}
		}
		Collections.reverse(list);
		return list;
	}

	@Override
	public PaginationDto<EmpDetailDto> queryNotKeyTalentByName(String customerId,
			String userId, String keyName, PaginationDto<EmpDetailDto> dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String,Object> mapParam=CollectionKit.newMap();
		mapParam.put("customerId", customerId);
		mapParam.put("userId", userId);
		mapParam.put("rowBounds", rowBounds);
		mapParam.put("keyName", keyName);
		mapParam.put("organPermitIds",getOrganPermitId());
		int count = keyTalentsDao.queryNotKeyTalentByNameCount(mapParam);
        List<EmpDetailDto> dtos = keyTalentsDao.queryNotKeyTalentByName(mapParam);
        
        dto.setRecords(count);
        dto.setRows(dtos);
		return dto;
	}

	@Override
	public PaginationDto<KeyTalentsCardDto> queryKeyTalentByName(String customerId,
			String userId, String keyName,String empId, String order, PaginationDto<KeyTalentsCardDto> dto) {
		RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
		Map<String,Object> mapParam=CollectionKit.newMap();
		mapParam.put("customerId", customerId);
		mapParam.put("userId", userId);
		mapParam.put("empId", empId);
		mapParam.put("order", getOrder(order));
		mapParam.put("rowBounds", rowBounds);
		mapParam.put("keyName", keyName);
		mapParam.put("organPermitIds",getOrganPermitId());
		int count = keyTalentsDao.queryKeyTalentByNameCount(mapParam);
        List<KeyTalentsCardDto> dtos = keyTalentsDao.queryKeyTalentByName(mapParam);
        
        dto.setRecords(count);
        dto.setRows(dtos);
		return dto;
	}

    /**
     * 登录人所有数据权限ID集	by jxzhang
     * @return
     */
	private List<String> getOrganPermitId(){
		List<OrganDto> organPermit = EisWebContext.getCurrentUser().getOrganPermit();
		List<String> rs = CollectionKit.newList();
    	if(null == organPermit){
    		rs.add("");
    		return rs;
        }
    	for (OrganDto organDto : organPermit) {
    		rs.add(organDto.getOrganizationId());
		}
		return  rs;
	}
	
}

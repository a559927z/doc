package net.chinahrd.db.input.service.impl;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.chinahrd.db.input.Entity.CheckInputdataStatusEntity;
import net.chinahrd.db.input.Entity.SoureDimOrganizationEntity;
import net.chinahrd.db.input.dao.DbImportBaseDao;
import net.chinahrd.db.input.enums.LogCode;
import net.chinahrd.db.input.service.DbImportBaseService;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础数据
 * 
 * @author jxzhang on 2016-03-04
 *
 */
@Transactional
@Service("dbImportBaseServiceImpl")
public class DbImportBaseServiceImpl implements DbImportBaseService {

	private static Logger log = LoggerFactory.getLogger(DbImportBaseServiceImpl.class);
	
	@Autowired
	private DbImportBaseDao dbImportBaseDao;
	
	
	@Override
	public CheckInputdataStatusEntity findCheckInputdataStatus() {
		return dbImportBaseDao.findCheckInputdataStatus(
				EisWebContext.getCustomerId(), DateUtil.getDBCurdate());
	}
	@Override
	public void updateCheckInputdataStatus(String id, int code, String note) {
		dbImportBaseDao.updateCheckInputdataStatus(id,
				EisWebContext.getCustomerId(), code, note);
	}
	
	
	
	@Transactional
	@Override
	public boolean replaceDimOrganization(boolean isInit) {
		Date start = new Date();
		String customer_id = EisWebContext.getCustomerId();
		List<SoureDimOrganizationEntity> dbList = dbImportBaseDao.querySoureDimOrganization(customer_id);
		
		if (null == dbList) { return false; }
		
		try {
			if(isInit){
				for (SoureDimOrganizationEntity entity : dbList) {
					entity.setId(Identities.uuid2());
				}
				setAttribute(customer_id, dbList);
				optDB(customer_id, dbList, true);
			}else{
				// 1. 找出soure表里是修改的
				List<String> changeIds = CollectionKit.newList();
				for(SoureDimOrganizationEntity l1 :dbList){
					if(null != l1.getC_id() || "".equals(l1.getC_id())){
						changeIds.add(l1.getC_id());
					}
				}
				// 2. 业务表和soure表匹配
				List<SoureDimOrganizationEntity> listMatch = dbImportBaseDao.queryDimOrganization(customer_id, changeIds);
				for(SoureDimOrganizationEntity soureTb : dbList){
					// 3. 生成id
					soureTb.setId(Identities.uuid2());
					for(SoureDimOrganizationEntity businessTb : listMatch){
						// 4. 如果存在把业务表原来id、fullpath覆盖
						if(soureTb.getC_id().equals(businessTb.getC_id())){
							soureTb.setId(businessTb.getId());
							soureTb.setFull_path((businessTb.getFull_path()));
							// 8. 默认是全添加，如果业务表存在这里改为修改。
							soureTb.setAdd(false);
						}
					}
					
				}
				// 5. 操作业务表。逻辑：先删除soure指定id。再添加。soure更新的和新加的。
				optDB(customer_id, dbList, false);
				// 6. 查出所有这里已包括了，新加入的和新修改的
				List<SoureDimOrganizationEntity> listNew = dbImportBaseDao.queryDimOrganizationNew(customer_id);
				// 7. 再为它们做属性
				setAttribute(customer_id, listNew);
				
				List<SoureDimOrganizationEntity> updateAttr = CollectionKit.newList();
				for(SoureDimOrganizationEntity entity : listNew){
					
					if(null == entity.getFull_path() ||
							!(entity.getFull_path().equals(entity.getFull_path2()))){
						updateAttr.add(entity);
					}
				}
				if(updateAttr.size() >0){
					dbImportBaseDao.updateAttr(updateAttr);
				}
				
				// 8-1. 加了新机构，要为已有父机构的角色、个人补上数据权限
				changePermit(listNew);
			}
			
			Map<String, String> mp = new HashMap<String, String>();
			mp.put("p_customer_id", EisWebContext.getCustomerId());
			mp.put("p_user_id", "System-Event");
			// 全路径
			dbImportBaseDao.callUpdateFullpathOrg(mp);
			// 日切片
			dbImportBaseDao.callDimOrganizationDays(mp);
			
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		
		@SuppressWarnings("unused")
		String useTime = DateUtil.getInterval(start, new Date());
		


		return true;
	}
	
	/**
	 * 1.加了新机构，要为已有父机构的角色、个人补上数据权限
	 * 2.删除机构，删除已删机构的数据权限
	 */
	private void changePermit(List<SoureDimOrganizationEntity> listNew){
		String customer_id = EisWebContext.getCustomerId();
		String create_user_id = "System-Event";
		List<KVItemDto> roList = dbImportBaseDao.queryRoleOrgan(customer_id);
		List<KVItemDto> uoList = dbImportBaseDao.queryUserOrgan(customer_id);
		
		List<Map<String, String>> addRoList = CollectionKit.newList();
		List<Map<String, String>> addUoList = CollectionKit.newList();
		
		// 1
		for (SoureDimOrganizationEntity soureT : listNew) {
			
			String soureId = soureT.getId();
			String sourePid = soureT.getOrganization_parent_id();
			
			// 角色数据权限
			for (KVItemDto roDto : roList) {
				if ((roDto.getV().equals(soureId) && sourePid.equals("-1"))
					||
					roDto.getV().equals(sourePid)) {
					
					Map<String, String> dto = new HashMap<String, String>();
					dto.put("id", Identities.uuid2());
					dto.put("customer_id", customer_id);
					dto.put("role_id", roDto.getK());
					dto.put("organization_id", soureId);
					dto.put("create_user_id", create_user_id);
					
					addRoList.add(dto);
				}
			}
			
			// 用户数据权限
			for (KVItemDto uoDto : uoList) {
				if((uoDto.getV().equals(soureId) && sourePid.equals("-1"))
					||
					uoDto.getV().equals(sourePid)){
					
					Map<String, String> dto = new HashMap<String, String>();
					dto.put("id", Identities.uuid2());
					dto.put("customer_id", customer_id);
					dto.put("user_id", uoDto.getK());
					dto.put("organization_id", soureId);
					dto.put("create_user_id", create_user_id);
					
					addUoList.add(dto);
				}
			}
		}
		
		optDBPermit(customer_id, addRoList, addUoList);
		
		
		// 2 删除已删机构的数据权限 TODO
		
		
	}
	
	private void optDBPermit(String customer_id, List<Map<String, String>> addRoList, List<Map<String, String>> addUoList){
		
		try {
			dbImportBaseDao.deleteRoleOrgan(customer_id);
			Map<Integer, List<Map<String, String>>> splitList = CollectionKit.splitList2(addRoList, 500);
			for(Entry<Integer, List<Map<String, String>>> itemList : splitList.entrySet()){
				dbImportBaseDao.addRoleOrganRelation(itemList.getValue());
			}
			
			dbImportBaseDao.deleteUserOrgan(customer_id);
			Map<Integer, List<Map<String, String>>> splitList2 = CollectionKit.splitList2(addUoList, 500);
			for(Entry<Integer, List<Map<String, String>>> itemList : splitList2.entrySet()){
				dbImportBaseDao.addUserOrganRelation(itemList.getValue());
			}
		} catch (Exception e) {
			log.debug("数据权限更新异常 :" + e.toString());
		}
		
	}
	
	
	/**
	 * 设置机构表部分属性
	 * @param dbList
	 */
	private void setAttribute(String customer_id, List<SoureDimOrganizationEntity> dbList){
		for (SoureDimOrganizationEntity en : dbList) {
			String pKey = en.getOrganization_parent_key();
			if(pKey.equals("-1")){
				en.setOrganization_parent_id("-1");
				continue;
			}
			for (SoureDimOrganizationEntity pIdObj : dbList) {
				if(pIdObj.getOrganization_key().equals(pKey)){
					// 父id
					en.setOrganization_parent_id(pIdObj.getId());
					break;
				}
			}
			// set 父对象
//			for (SoureDimOrganizationEntity fpObj : dbList) {
//				if(fpObj.getOrganization_key().equals(pKey)){
//					en.setHf(fpObj);
//				}
//			}
		}
		for (SoureDimOrganizationEntity en : dbList) {
			// 客户id
			en.setCustomer_id(customer_id);
			// 全路径
//			String fp = en.getFp();
//			en.setFull_path2(fp);
			// 深度
//			int depth = StringUtils.countMatches(fp, "_")+1;
//			en.setDepth(depth);
		}
		// 是否有子节点
		for (SoureDimOrganizationEntity en : dbList) {
			String key = en.getOrganization_key();
			for (SoureDimOrganizationEntity hasChildrenObj : dbList) {
				if(hasChildrenObj.getOrganization_parent_key().equals(key)){
					en.setHas_children(1);
					break;
				}
			}
		}
	}
	/**
	 * 操作数据入库
	 * @param customer_id
	 * @param dbList
	 * @param isInit
	 */
	private void optDB(String customer_id, List<SoureDimOrganizationEntity> dbList, boolean isInit){
		if(!isInit){
			dbImportBaseDao.deleteDimOrganization(customer_id, dbList);
		}
		Map<Integer, List<SoureDimOrganizationEntity>> splitList = CollectionKit.splitList2(dbList, 500);
		for(Entry<Integer, List<SoureDimOrganizationEntity>> itemList : splitList.entrySet()){
			dbImportBaseDao.replaceDimOrganization(itemList.getValue());
		}
	}
	
	
	
	@Transactional
	@Override
	public boolean callDimPosition(){
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("p_customer_id", EisWebContext.getCustomerId());
		mp.put("p_user_id", "System-Event");
		
		// 岗位
		return callTempletMethod("callDimPosition", mp,
				LogCode.DIM_POSITION.getModule(), 
				LogCode.DIM_POSITION.getValue(),
				LogCode.DIM_POSITION.getShowIndex());
	}
	
	
	@Transactional
	@Override
	public boolean callDimTables2(){
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("p_customer_id", EisWebContext.getCustomerId());
		mp.put("p_user_id", "System-Event");
		mp.put("module", "pro_fetch_dim_all");
		mp.put("is_error", 0);
		mp.put("curdate", DateUtil.getDBCurdate());
		dbImportBaseDao.callDimTable(mp);
		int eM = dbImportBaseDao.findLogInfo(mp);
		return eM ==1 ? true:false; 
	}
	
	
	@SuppressWarnings("unused")
	@Transactional
	@Override
	public boolean callDimTables(){
		Date start = new Date();
		
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("p_customer_id", EisWebContext.getCustomerId());
		mp.put("p_user_id", "System-Event");
	
		// 主、子序列；职衔；能力层职； 子职；人群范围
		boolean callDimSequence = callDimSequence(mp);
		boolean callDimSequenceSub = callDimSequenceSub(mp);
		boolean callDimJobTitle = callDimJobTitle(mp);
		boolean callAbility = callDimAbility(mp);
		boolean callAbilityLv = callDimAbilityLv(mp);
		boolean callDimPopulation = callDimPopulation(mp);
		
		// 关键人才库
		boolean callDimKeyTalentType = callDimKeyTalentType(mp);
		// 流失
		boolean callDimRunOff = callDimRunOff(mp);
		// 激励要素
		boolean callDimEncourages = callDimEncourages(mp);
		// 课程
		boolean callDimCourse = callDimCourse(mp);
		// 课程类型
		boolean callDimCourseType = callDimCourseType(mp);
		// 工资结构
		boolean callDimStructure = callDimStructure(mp);
		// 项目类型
		boolean callDimProjectType = callDimProjectType(mp);
		// 项目投入费用类型
		boolean callDimProjectInputType = callDimProjectInputType(mp);
		
		// 异动类型
		boolean callDimChangeType = callDimChangeType(mp);
		// 绩效
		boolean callDimPerformance = callDimPerformance(mp);
		
		// 招聘渠道
		boolean callDimChannel = callDimChannel(mp);
		// 离职周期
		boolean callDimDismissionWeek = callDimDismissionWeek(mp);
		// 岗位能力素质维
		boolean callDimQuality = callDimQuality(mp);
		
		// 异动类型维
		boolean callDimCheckworktype = callDimCheckworktype(mp);
		
		if(callDimSequence && callDimSequenceSub && callDimJobTitle && callAbility && callAbilityLv && callDimPopulation &&
			callDimKeyTalentType && 
			callDimRunOff && callDimEncourages &&
			callDimCourse && callDimCourseType &&
			callDimStructure &&
			callDimProjectType && callDimProjectInputType &&
			callDimChangeType && callDimPerformance &&
			callDimChannel && callDimDismissionWeek && callDimQuality &&
			callDimCheckworktype
		){
			String useTime = DateUtil.getInterval(start, new Date());
			return true;
		}
		String useTime = DateUtil.getInterval(start, new Date());
		return false;
	}
	
	
	private boolean callDimCheckworktype(Map<String, String> mp) {
		mp.put("p_key_work", "checkwork");
		return callTempletMethod("callDimCheckworktype", mp,
				LogCode.DIM_CHECKWORK_TYPE.getModule(), 
				LogCode.DIM_CHECKWORK_TYPE.getValue(),
				LogCode.DIM_CHECKWORK_TYPE.getShowIndex());
	}
	private boolean callDimQuality(Map<String, String> mp) {
		return callTempletMethod("callDimQuality", mp,
				LogCode.DIM_QUALITY.getModule(), 
				LogCode.DIM_QUALITY.getValue(),
				LogCode.DIM_QUALITY.getShowIndex());
	}
	private boolean callDimPopulation(Map<String, String> mp) {
		return callTempletMethod("callDimPopulation", mp,
				LogCode.DIM_POPULATION.getModule(), 
				LogCode.DIM_POPULATION.getValue(),
				LogCode.DIM_POPULATION.getShowIndex());
	}
	private boolean callDimPerformance(Map<String, String> mp) {
		mp.put("p_key_work", "performance");
		return callTempletMethod("callDimPerformance", mp,
				LogCode.DIM_PERFORMANCE.getModule(), 
				LogCode.DIM_PERFORMANCE.getValue(),
				LogCode.DIM_PERFORMANCE.getShowIndex());
	}
	private boolean callDimEncourages(Map<String, String> mp) {
		return callTempletMethod("callDimEncourages", mp,
				LogCode.DIM_ENCOURAGES.getModule(), 
				LogCode.DIM_ENCOURAGES.getValue(),
				LogCode.DIM_ENCOURAGES.getShowIndex());
	}
	private boolean callDimDismissionWeek(Map<String, String> mp) {
		return callTempletMethod("callDimDismissionWeek", mp,
				LogCode.DIM_DISMISSION_WEEK.getModule(), 
				LogCode.DIM_DISMISSION_WEEK.getValue(),
				LogCode.DIM_DISMISSION_WEEK.getShowIndex());
	}
	private boolean callDimChannel(Map<String, String> mp) {
		return callTempletMethod("callDimChannel", mp,
				LogCode.DIM_CHANNEL.getModule(), 
				LogCode.DIM_CHANNEL.getValue(),
				LogCode.DIM_CHANNEL.getShowIndex());
	}
	private boolean callDimChangeType(Map<String, String> mp) {
		mp.put("p_key_work", "changeType");
		return callTempletMethod("callDimChangeType", mp,
				LogCode.DIM_CHANGE_TYPE.getModule(), 
				LogCode.DIM_CHANGE_TYPE.getValue(),
				LogCode.DIM_CHANGE_TYPE.getShowIndex());
	}
	
	private boolean callDimProjectInputType(Map<String, String> mp) {
		return callTempletMethod("callDimProjectInputType", mp,
				LogCode.DIM_PROJECT_INPUT_TYPE.getModule(), 
				LogCode.DIM_PROJECT_INPUT_TYPE.getValue(),
				LogCode.DIM_PROJECT_INPUT_TYPE.getShowIndex());
	}


	private boolean callDimCourse(Map<String, String> mp) {
		return callTempletMethod("callDimCourse", mp,
				LogCode.DIM_COURSE.getModule(), 
				LogCode.DIM_COURSE.getValue(),
				LogCode.DIM_COURSE.getShowIndex());
	}


	private boolean callDimProjectType(Map<String, String> mp) {
		return callTempletMethod("callDimProjectType", mp,
				LogCode.DIM_PROJECT_TYPE.getModule(),
				LogCode.DIM_PROJECT_TYPE.getValue(),
				LogCode.DIM_PROJECT_TYPE.getShowIndex());
	}


	private boolean callDimStructure(Map<String, String> mp) {
		return callTempletMethod("callDimStructure", mp,
				LogCode.DIM_STRUCTURE.getModule(),
				LogCode.DIM_STRUCTURE.getValue(),
				LogCode.DIM_STRUCTURE.getShowIndex());
	}


	private boolean callDimRunOff(Map<String, String> mp) {
		return callTempletMethod("callDimRunOff", mp,
				LogCode.DIM_RUN_OFF.getModule(),
				LogCode.DIM_RUN_OFF.getValue(),
				LogCode.DIM_RUN_OFF.getShowIndex());
	}
	
	private boolean callDimCourseType(Map<String, String> mp) {
		mp.put("p_key_work", "courseType");
		return callTempletMethod("callDimCourseType", mp,
				LogCode.DIM_COURSE_TYPE.getModule(),
				LogCode.DIM_COURSE_TYPE.getValue(),
				LogCode.DIM_COURSE_TYPE.getShowIndex());
	}
	
	private boolean callDimJobTitle(Map<String, String> mp) {
		mp.put("p_key_work", "title");
		return callTempletMethod("callDimJobTitle", mp,
				LogCode.DIM_JOB_TITLE.getModule(),
				LogCode.DIM_JOB_TITLE.getValue(),
				LogCode.DIM_JOB_TITLE.getShowIndex());
	}
	
	private boolean callDimSequenceSub(Map<String, String> mp) {
		mp.put("p_key_work", "sequence_sub");
		return callTempletMethod("callDimSequenceSub", mp,
				LogCode.DIM_SEQUENCE_SUB.getModule(),
				LogCode.DIM_SEQUENCE_SUB.getValue(),
				LogCode.DIM_SEQUENCE_SUB.getShowIndex());
	}
	

	private boolean callDimSequence(Map<String, String> mp) {
		mp.put("p_key_work", "sequence");
		return callTempletMethod("callDimSequence", mp,
				LogCode.DIM_SEQUENCE.getModule(),
				LogCode.DIM_SEQUENCE.getValue(),
				LogCode.DIM_SEQUENCE.getShowIndex());
	}
	
	
	private boolean callDimKeyTalentType(Map<String, String> mp) {
		mp.put("p_key_work", "keyTalent");
		return callTempletMethod("callDimKeyTalentType", mp,
				LogCode.DIM_KEY_TALENT_TYPE.getModule(),
				LogCode.DIM_KEY_TALENT_TYPE.getValue(),
				LogCode.DIM_KEY_TALENT_TYPE.getShowIndex());
	}

	private boolean callDimAbility(Map<String, String> mp) {
		mp.put("p_key_work", "ability");
		return callTempletMethod("callDimAbility", mp,
				LogCode.DIM_ABILITY.getModule(), LogCode.DIM_ABILITY.getValue(),
				LogCode.DIM_ABILITY.getShowIndex());
	}

	private boolean callDimAbilityLv(Map<String, String> mp) {
		mp.put("p_key_work", "ability_lv");
		return callTempletMethod("callDimAbilityLv", mp,
				LogCode.DIM_ABILITY_LV.getModule(),
				LogCode.DIM_ABILITY_LV.getValue(),
				LogCode.DIM_ABILITY_LV.getShowIndex());
	}
	
	
	@SuppressWarnings("unused")
	private boolean callTempletMethod(String MethodName, Map<String, String> mp,
			String module, String value, int showIndex) {

		Date start = new Date();
		try {
			Method method = dbImportBaseDao.getClass().getMethod(MethodName,
					new Class[]{Map.class});
			method.invoke(dbImportBaseDao, mp);

		} catch (Exception e1) {
			
			return false;
		}
//		String useTime = DateUtil.getInterval(start, new Date());
//		dbImportService.insertLog(module, value + "：数据刷新完成", useTime, start,
//				new Date(), LogCode.SUCESS, showIndex);
		return true;
	}
}

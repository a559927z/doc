/**
*net.chinahrd.biz.paper.service
*/
package net.chinahrd.mvc.app.service.home.impl;

import java.util.List;

import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;
import net.chinahrd.mvc.app.dao.home.MobileHomeDao;
import net.chinahrd.mvc.app.service.home.MobileHomeService;
import net.chinahrd.mvc.app.service.home.auxiliary.AccordDismissAuxiliary;
import net.chinahrd.mvc.app.service.home.auxiliary.KeyTalentAuxiliary;
import net.chinahrd.mvc.app.service.home.auxiliary.ManpowerAuxiliary;
import net.chinahrd.mvc.app.service.home.auxiliary.PerBenefitAuxiliary;
import net.chinahrd.mvc.app.service.home.auxiliary.PerChangeAuxiliary;
import net.chinahrd.mvc.app.service.home.auxiliary.TalentStructureAuxiliary;
import net.chinahrd.mvc.app.service.home.auxiliary.TeamImgAuxiliary;
import net.chinahrd.mvc.app.service.home.auxiliary.TrainBoardAuxiliary;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliary;
import net.chinahrd.mvc.app.util.MobileHomeAuxiliaryModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author htpeng
 *2016年7月14日下午4:03:30
 */
@Service("mobileHomeService")
public class MobileHomeServiceImpl implements  MobileHomeService{
	
	@Autowired
	private MobileHomeDao mobileHomeDao;
	

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.home.MobileHomeService#getFunctionList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<KanbanConfigMobileDto> getFunctionList(String path,
			String customerId) {
		List<KanbanConfigMobileDto> list = mobileHomeDao
				.getFunctionList(customerId);
		for (KanbanConfigMobileDto dto:list) {
			dto.setPageUrl(path+dto.getPageUrl());
			dto.setImageUrl(path+dto.getImageUrl());
		}
		return list;
	}
	/**
	 * 获取已添加的管理看板数据
	 * @param customerId
	 * @param empId
	 * @return
	 */
	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.home.MobileHomeService#getAlreadyAddedList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<KanbanConfigMobileDto> getAlreadyAddedList(String path,
			String organId, String customerId, String empId,List<String> organList) {
		List<KanbanConfigMobileDto> alreadyAddedList = mobileHomeDao
				.getAlreadyAddedList(customerId, empId);
		MobileHomeAuxiliaryModel model=new MobileHomeAuxiliaryModel();
		model.setCustomerId(customerId);
		model.setEmpId(empId);
		model.setOrganId(organId);
		model.setOrganList(organList);
		
		for (KanbanConfigMobileDto dto:alreadyAddedList) {
			dto.setPageUrl(path+dto.getPageUrl());
			dto.setImageUrl(path+dto.getImageUrl());
			MobileHomeAuxiliary auxiliary=Auxiliary.getAuxiliary(dto.getFunctionCode());
			if(auxiliary!=null){
				auxiliary.getData(model,dto, mobileHomeDao);
			}
		}
		//最后一条，是一个加号无论是否有数据，都会有这么一个加号
		KanbanConfigMobileDto add =new KanbanConfigMobileDto();
		add.setBackgroundColor(MobileHomeAuxiliary.ADD_CORLOR);
		add.setImgColor(MobileHomeAuxiliary.ADD_CORLOR);
		add.setFunctionCode("Add");
		add.setImageUrl(path+"/assets/mobile/icon/kanban/add2x.png");
		alreadyAddedList.add(add);
		return alreadyAddedList;
	}

	static enum Auxiliary{
		accordDismiss("ZhuDongLiuShiLv",new AccordDismissAuxiliary()),
		keyTalent("GuanJianRenCaiKu",new KeyTalentAuxiliary()),
		manpower("RenLiChengBen",new ManpowerAuxiliary()),
		perBennefit("RenJunXiaoYi", new PerBenefitAuxiliary() ),
		perChange("GeRenJiXiaoJiBianHua",new PerChangeAuxiliary() ),
		talentStructure("RenLiJieGou",new TalentStructureAuxiliary()),
		teamImg("RuanDuiHuaXiang",new TeamImgAuxiliary()),
		trainBoard("PeiXunKanBan",new TrainBoardAuxiliary());
		
		private String key;
		private MobileHomeAuxiliary auxiliary;
		
		Auxiliary(String key,MobileHomeAuxiliary auxiliary){
			this.key=key;
			this.auxiliary=auxiliary;
		}
		
		public static MobileHomeAuxiliary getAuxiliary(String key){
			for(Auxiliary auxiliary:Auxiliary.values()){
				if(auxiliary.key.equals(key)){
					return auxiliary.auxiliary;
				}
			}
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.biz.paper.mobile.service.home.MobileHomeService#getQuotaByName(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public List<KanbanConfigMobileDto> getQuotaByName(String path,
			String organId,String name ,String customerId, String empId,
			List<String> organList) {
		List<KanbanConfigMobileDto> alreadyAddedList = mobileHomeDao
				.getQuotaByName(customerId, name);
		MobileHomeAuxiliaryModel model=new MobileHomeAuxiliaryModel();
		model.setCustomerId(customerId);
		model.setEmpId(empId);
		model.setOrganId(organId);
		model.setOrganList(organList);
		
		for (KanbanConfigMobileDto dto:alreadyAddedList) {
			dto.setPageUrl(path+dto.getPageUrl());
			dto.setImageUrl(path+dto.getImageUrl());
			MobileHomeAuxiliary auxiliary=Auxiliary.getAuxiliary(dto.getFunctionCode());
			if(auxiliary!=null){
				auxiliary.getData(model,dto, mobileHomeDao);
			}
		}
		return alreadyAddedList;
	}

}

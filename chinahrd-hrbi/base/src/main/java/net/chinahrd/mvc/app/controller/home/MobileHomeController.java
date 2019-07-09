/**
*net.chinahrd.biz.paper.mobile.controller
*/
package net.chinahrd.mvc.app.controller.home;

import java.util.List;

import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;
import net.chinahrd.mvc.app.AppUserMapping;
import net.chinahrd.mvc.app.DataPacket;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.mvc.app.service.home.MobileHomeService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * @author htpeng
 *2016年7月14日下午3:58:10
 */
@Controller
@RequestMapping("/mobile/home")
public class MobileHomeController extends BaseController{
	
	protected static final Logger log = Logger.getLogger(MobileHomeController.class);
	
	@Autowired
	public MobileHomeService mobileHomeService;
	
	/**
	 * 综合功能，获取功能列表
	 * 
	 * @param token
	 *            用户token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFunctionList", method = RequestMethod.GET)
	public DataPacket getFunctionList(String token) {
		DataPacket data = new DataPacket();
		// TODO 后期改为数据库查询
		
		List<KanbanConfigMobileDto> alreadyAddedList = mobileHomeService
				.getFunctionList(getCtx(),getCustomerId());
		data.setMessage(DataPacket.MSG_SUCCESS);
		data.setCode(DataPacket.CODE_SUCCESS);
		data.setData(alreadyAddedList);
		
		
//		List<IconItemDto> list = new ArrayList<IconItemDto>();
//		IconItemDto dto1=new IconItemDto("PK", "人才PK", getCtx() + "/assets/mobile/icon/w2.png", 
//				getCtx()+ "/mobile/talentContrast/toTalentContrastView",true,false);
//		dto1.setBackgroundColor("#E89F39");
//		list.add(dto1);
//		IconItemDto dto2=new IconItemDto("Search", "人才剖像",getCtx()+"/assets/mobile/icon/30.png");
//		dto2.setBackgroundColor("#E89F39");
//		list.add(dto2);
////		if(log.isDebugEnabled()){
//			IconItemDto dto3=new IconItemDto("Team", "缘分指数",getCtx()+"/assets/mobile/icon/u13.png");
//			dto3.setBackgroundColor("#E89F39");
//			list.add(dto3);
////		}
//		IconItemDto dto4=new IconItemDto("Search", "人员搜索",getCtx()+"/assets/mobile/icon/f2.png");
//		dto4.setBackgroundColor("#E89F39");
//		list.add(dto4);
		log.debug(new Gson().toJson(data));
		return data;
	}

	/**
	 * 管理看板
	 * 
	 * @param token
	 *            用户token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getKanbanList", method = RequestMethod.GET)
	public DataPacket getKanbanList(String token,String orgId) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<KanbanConfigMobileDto> alreadyAddedList = mobileHomeService
					.getAlreadyAddedList(getCtx(),orgId,getCustomerId(), user.getEmpId(),getOrganPermitId());
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(alreadyAddedList);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		log.debug(new Gson().toJson(data));
		return data;
	}
	
	/**
	 * 根据上级名称获取下级指标
	 * 
	 * @param token
	 *            用户token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getQuotaByName", method = RequestMethod.GET)
	public DataPacket getQuotaByName(String token,String orgId,String name) {
		DataPacket data = new DataPacket();
		// TODO 后期改为数据库查询
		RbacUser user = AppUserMapping.getUserByToken(token);
		List<KanbanConfigMobileDto> quotaByName = mobileHomeService
				.getQuotaByName(getCtx(),orgId,name,getCustomerId(), user.getEmpId(),getOrganPermitId());
		data.setMessage(DataPacket.MSG_SUCCESS);
		data.setCode(DataPacket.CODE_SUCCESS);
		data.setData(quotaByName);
		log.debug(new Gson().toJson(data));
		return data;
	}

}

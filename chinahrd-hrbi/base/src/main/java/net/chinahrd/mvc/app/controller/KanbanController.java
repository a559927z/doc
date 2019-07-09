package net.chinahrd.mvc.app.controller;

import java.util.List;

import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.entity.dto.app.KanbanConfigMobileDto;
import net.chinahrd.mvc.app.AppUserMapping;
import net.chinahrd.mvc.app.DataPacket;
import net.chinahrd.mvc.app.service.KanbanConfigService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller("AppKanbanController")
@RequestMapping("/mobile/kanban")
public class KanbanController extends BaseController{
	protected static final Logger log = Logger.getLogger(KanbanController.class);
	
	@Autowired
	public KanbanConfigService kanbanConfigService;
	
	/**
	 * 添加指标 已添加的指标
	 * @param token
	 *            用户token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAlreadyAddedList", method = RequestMethod.GET)
	public DataPacket getAlreadyAddedList(String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<KanbanConfigMobileDto> alreadyAddedList = kanbanConfigService
					.getAlreadyAddedList(getCustomerId(), user.getEmpId());
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
	 * 添加指标 未添加的指标
	 * @param token
	 *            用户token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getNotAddedList", method = RequestMethod.GET)
	public DataPacket getNotAddedList(String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<KanbanConfigMobileDto> notAddedList = kanbanConfigService
					.getNotAddedList(getCustomerId(), user.getEmpId());
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(notAddedList);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		log.debug(new Gson().toJson(data));
		return data;
	}
	
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public DataPacket add(String token,String functionId){
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			boolean addKanbanItem = kanbanConfigService.addKanbanItem(getCustomerId(), user.getEmpId(),functionId);
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(addKanbanItem);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		log.debug(new Gson().toJson(data));
		return data;
	}
	
	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public DataPacket del(String token,String functionConfigMobileId){
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			boolean deleteKanbanItem = kanbanConfigService.deleteKanbanItem(functionConfigMobileId);
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(deleteKanbanItem);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		log.debug(new Gson().toJson(data));
		return data;
	}
}

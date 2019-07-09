package net.chinahrd.mvc.pc.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.chinahrd.entity.dto.pc.common.MessageResultDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.common.MessageService;

/**
 * 提示消息相关Controller 
 * Created by htpeng on 16/1/13.
 */
@Controller
@RequestMapping(value = "/message")
public class MessageController extends BaseController {

	@Autowired
	private MessageService messageService;

	/**
	 * 查询消息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findMessage")
	public List<MessageResultDto> findMessage(String organizationId) {
		List<MessageResultDto> resultDtos = messageService.findAllMessage(
				getCustomerId(), organizationId, getUserEmpId());
		return resultDtos;
	}

	/**
	 * 查询消息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/readMessage")
	public boolean readMessage(String organizationId) {
		messageService.readMessage(getCustomerId(), organizationId,
				getUserEmpId());
		return true;
	}

}

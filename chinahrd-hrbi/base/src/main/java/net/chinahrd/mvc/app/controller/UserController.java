package net.chinahrd.mvc.app.controller;

import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.entity.dto.pc.common.EmpDto;
import net.chinahrd.mvc.app.AppUserMapping;
import net.chinahrd.mvc.app.DataPacket;
import net.chinahrd.mvc.pc.service.common.CommonService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * @author guanjian
 *
 */
@Controller("AppUserController")
@RequestMapping("/mobile/user")
public class UserController extends BaseController{
	protected static final Logger log = Logger.getLogger(UserController.class);
	
	@Autowired
    private CommonService commonService;
	
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public DataPacket getUserInfo(String token) {
		// XXX 此处应精简DTO
		RbacUser user = AppUserMapping.getUserByToken(token);
		DataPacket data = new DataPacket();
		data.setMessage(DataPacket.MSG_SUCCESS);
		data.setCode(DataPacket.CODE_SUCCESS);
		data.setData(user);
		return data;
	}
	
	@ResponseBody
    @RequestMapping(value = "/getBaseInfoByEmpId")
    public DataPacket getBaseInfoByEmpId(String token,String empId) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			EmpDto empBaseInfo = commonService.getEmpBaseInfo(getCustomerId(), empId);
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(empBaseInfo);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		log.debug(new Gson().toJson(data));
		return data;
    }
	
}

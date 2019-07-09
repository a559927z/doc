package net.chinahrd.mvc.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import net.chinahrd.entity.dto.app.OrganTreeDto;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.model.AuthenticationResult;
import net.chinahrd.eis.permission.service.RbacAuthorizerService;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.mvc.app.AppUserMapping;
import net.chinahrd.mvc.app.DataPacket;
import net.chinahrd.utils.CollectionKit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * AppController:处理app端的一般请求，后期可以将该类中的返回对象加密压缩
 * 
 * @author guanjian
 * 
 */
@Controller("AppLoginController")
@RequestMapping("/mobile")
public class LoginController {
	protected static final Logger log = Logger.getLogger(LoginController.class);
	@Autowired
	public RbacAuthorizerService authorizerService;

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public DataPacket login(HttpServletResponse response,String username, String password) {
		log.debug(username+"登录服务端！");
		//IOS下容易产生空格 去掉用户名中的空格
		if(!StringUtils.isBlank(username)){
			username = username.trim().replaceAll(" ", "");
		}
		DataPacket data = new DataPacket();
		// 该Service需要改成在前端加密发送
		AuthenticationResult result = authorizerService.authenticate(EisWebContext.getCustomerId(), username, password);
		String token = null;
		Map<String, Object> map=CollectionKit.newMap();
		if (result != null && result.getUser() != null) {
			token = AppUserMapping.addUserInMap(result.getUser());
			List<OrganDto> list=result.getUser().getOrganPermit();
			List<OrganDto> top=result.getUser().getOrganPermitTop();
			result.getUser().setMobileTree(getMobileOrganTree(list, top));
			
			System.out.println("加入用户成功---token："+token);
//			 Cookie cookie = new Cookie("token",token);
//	    	 //设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
//	    	 cookie.setPath("/");
//	    	 response.addCookie(cookie);
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			map.put("token", token);
			map.put("user", result.getUser());
			data.setData(map);
			
		} else {
			data.setMessage("用户名或密码错误！");
			data.setCode(DataPacket.CODE_LOGIN_FAIL);
		}
		log.debug("服务器返回数据！");
		return data;
	}

	@ResponseBody
	@RequestMapping(value = "/checkTokenIsValid", method = RequestMethod.GET)
	public DataPacket checkTokenIsValid(String token) {
		log.debug(token);
		boolean tokenIsExist = AppUserMapping.tokenIsExist(token);
		DataPacket data = new DataPacket();
		if(tokenIsExist){
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setData(AppUserMapping.getUserByToken(token));
		}else{
			data.setCode(DataPacket.CODE_FAIL);
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
		}
		return data;
	}
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public DataPacket logout(HttpServletResponse response,String token) {
		DataPacket data = new DataPacket();
		if (AppUserMapping.tokenIsExist(token)) {
			AppUserMapping.removeToken(token);
		}
		Cookie cookie = new Cookie("token", null);
		// 设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		data.setMessage(DataPacket.MSG_SUCCESS);
		data.setCode(DataPacket.CODE_SUCCESS);
		log.debug("服务器返回数据！");
		return data;
	}
	
	
	/**
	 * 构建移动端   机构树
	 * @param list
	 * @param top
	 * @return
	 */
	private List<OrganTreeDto> getMobileOrganTree(List<OrganDto> list,List<OrganDto> top){
		List<OrganTreeDto> result=CollectionKit.newList();
		for(OrganDto dto:top){
			result.add(new OrganTreeDto(dto));
		}
		constructTree(result,list);
		return result;
	}
	
	private void constructTree(List<OrganTreeDto> list,List<OrganDto> source){
		for(OrganTreeDto node :list){
			if(node.getHasChildren()==1){
				for(OrganDto dto:source){
					if(dto.getOrganizationParentId().equals(node.getOrganizationId())){
						node.setChildrens(dto);
					}
				}
				constructTree(node.getChildrens(),source);
			}
		}
	}
}

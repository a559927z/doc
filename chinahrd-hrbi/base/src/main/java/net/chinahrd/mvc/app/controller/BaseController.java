package net.chinahrd.mvc.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.Cookie;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.mvc.VersionController;
import net.chinahrd.mvc.app.AppUserMapping;
import net.chinahrd.utils.CollectionKit;

import org.apache.commons.lang3.StringUtils;


/**
 * 
 * @author htpeng 2016年3月30日下午5:3ex2:05
 */
public abstract class BaseController extends VersionController{

	private String token;
	
	private String ctx;
 	@Override
    public  void autowiredAfter(){
 		token = request.getParameter("token");
		if (token == null) {
			Cookie[] cookies = request.getCookies();// 这样便可以获取一个cookie数组
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("token")) {
						token = cookie.getValue();
						break;
					}
				}
			}
			
			if (token == null) {
				try {
					response.setContentType("application/json");
					PrintWriter out = response.getWriter();
					out.print("token失效请重新登入！");
					out.flush();
					out.close();
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			Cookie cookie = new Cookie("token", token);
			// 设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		
		
    }
	
	
	/**
	 * 获取登录用户ID
	 *
	 * @return
	 */
	public String getUserId() {
		return getUser().getUserId();
	}

	public String getCtx() {
		String path = request.getContextPath();
		this.ctx = request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + path;
		return ctx;
	}

	public RbacUser getUser() {

		return AppUserMapping.getUserByToken(token);
	}

	/**
	 * 获取登录用户客户ID
	 *
	 * @return
	 */
	public String getCustomerId() {
		String customerId = getUser().getCustomerId();
		if (StringUtils.isEmpty(customerId)) {
			customerId = EisWebContext.getCustomerId();
		}

		return customerId;
	}

	public String getUserEmpId() {
		return getUser().getEmpId();
	}

	public List<OrganDto> getOrganPermit() {
		return getUser().getOrganPermit();
	}
	public List<OrganDto> getTopOrgan() {
		return getUser().getOrganPermitTop();
	}
	public List<String> getTopOrganStr() {
		List<String> list=CollectionKit.newList();
		List<OrganDto> orgList=getTopOrgan();
		for(OrganDto org:orgList){
			list.add(org.getOrganizationId());
		}
		return list;
	}
	public List<String> getOrganPermitId() {
		List<OrganDto> organPermit = getOrganPermit();
		List<String> rs = CollectionKit.newList();
		if (null == organPermit) {
			rs.add("");
			return rs;
		}
		for (OrganDto organDto : organPermit) {
			rs.add(organDto.getOrganizationId());
		}
		return rs;
	}
	public String getUserEmpName() {
		return getUser().getUserNameCh();
	}

	/**
	 * 是否系统配置管理员
	 * 
	 * @return
	 */
	public boolean isSysDeploy() {
		return getUser().getSysDeploy() == 1;
	}
}

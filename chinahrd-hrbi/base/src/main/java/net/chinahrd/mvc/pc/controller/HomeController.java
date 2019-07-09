package net.chinahrd.mvc.pc.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.chinahrd.eis.annotation.log.ControllerAop;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.model.RbacFunction;
import net.chinahrd.eis.permission.model.RbacRole;
import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.admin.RoleFunctionDto;
import net.chinahrd.mvc.pc.service.admin.FunctionService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.TableKeyUtil;

/**
 * 登录之后的首页
 */
@Controller
public class HomeController extends BaseController {

    @Autowired
    private FunctionService functionService;

    @RequestMapping(value = "/index")
    public String index() {
        return "biz/index";
    }

    @RequestMapping(value = "/toUpdatePasswd")
    public String toUpdatePasswd() {
        return "biz/updatePasswd";
    }

	/**
	 * 登录成功跳转
	 * 
	 * @param request
	 * @param response
	 * @return 首页 、如果中人网版重定向员工考勤
	 * @throws IOException
	 * @author jxzhang 重构 by 2016-12-13
	 */
    @ControllerAop(description = "登录成功", writeDb = true, type = 1)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RbacUser user = EisWebContext.getCurrentUser();
        String customerId = user.getCustomerId();
        List<RbacRole> rbacRoles = user.getRbacRoles();
        List<RbacFunction> rbacFunctions = user.getRbacFunctions();
        List<OrganDto> organPermit = user.getOrganPermit();
        List<OrganDto> organPermitTop = user.getOrganPermitTop();
        String userRoleNames = "";
        List<String> roleIds = CollectionKit.newList();
        boolean multiRoles = false;
        if(CollectionKit.isNotEmpty(rbacRoles)){
            userRoleNames = CollectionKit.extractToString(rbacRoles, "roleName", "、");
            roleIds = CollectionKit.extractToList(rbacRoles, "roleId");
            multiRoles = 
            		rbacRoles.size() == 1 && TableKeyUtil.YG_ROLE_ID.equals(rbacRoles.get(0).getRoleId())
            		? false : true;
        }
		this.setSession("username", user.getUsername());
		this.setSession("userRoles", userRoleNames);
		this.setSession("multiRoles", multiRoles);
		// this.setSession("funDtos", rbacFunctions);
		

        // TODO 可以通过rbacFunctions获取。这块可以去掉 ==========================================
        boolean isSysDeploy = user.getSysDeploy() == 1;
        List<RoleFunctionDto> functionDtos = null;
        if (isSysDeploy) {
            functionDtos = functionService.findFunctionAll(customerId, null);
        } else {
            if (rbacRoles.size() > 0) {
                functionDtos = functionService.findFunctionAll(customerId, roleIds);
            }
        }
        this.setSession("funDtos", functionDtos);
        // TODO 这样获取存在问题，可以通过organPermitTop修正
	    if (CollectionKit.isNotEmpty(organPermit)) {
	    	OrganDto topOneOrgan = organPermit.get(0);
	    	this.setSession("topOrganId", topOneOrgan.getOrganizationId());
	    	this.setSession("topOrganName", topOneOrgan.getOrganizationName());
	    }
	    // ================================================================================
	    
        // 中人网版，支持员工首页
	    if(CollectionKit.isNotEmpty(rbacRoles) 
	    		&& TableKeyUtil.ZRW_CUSTOMER_ID.equals(customerId) 
        		&& rbacRoles.size() == 1
                && TableKeyUtil.YG_ROLE_ID.equals(rbacRoles.get(0).getRoleId()) ){
	    	return "redirect:/empAttendance/toEmpAttendanceView";
	    }
        return "biz/home";
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultIndex() {
        return "include/default";
    }
    
	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 * @author jxzhang
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			log.info("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

}

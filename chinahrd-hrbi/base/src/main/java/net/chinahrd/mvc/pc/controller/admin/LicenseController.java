package net.chinahrd.mvc.pc.controller.admin;


import javax.servlet.http.HttpServletRequest;

import net.chinahrd.mvc.pc.controller.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/license")
public class LicenseController extends BaseController {

  
    /**
     * 跳转授权页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String list(HttpServletRequest request) {
    	request.setAttribute("license", true);
        return "error/license";
    }

}

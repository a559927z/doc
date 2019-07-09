package net.chinahrd.mvc.pc.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import net.chinahrd.eis.aop.LogService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.chinahrd.eis.annotation.log.ControllerAop;
import net.chinahrd.eis.permission.service.RbacAuthorizerService;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.utils.eMail.MailUtil;
import net.chinahrd.utils.verifyCode.VerifyCodeUtils;

@Controller
public class LoginController extends BaseController {

    @Autowired
    private RbacAuthorizerService authorizerService;
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private LogService logService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
        return "login";
    }

    @ControllerAop(description = "退出登录", writeDb = true, type = 2)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return "/";
    }

    @RequestMapping(value = "/forgetPassword/toView", method = RequestMethod.GET)
    public String toView() {
        return "forgetPassword";
    }

    @ResponseBody
    @RequestMapping(value = "/forgetPassword/opt", method = RequestMethod.POST)
    public ResultDto<Object> forgetPasswordOpt(HttpServletRequest request, HttpServletResponse response, String userKey,
                                               String email, Model model) {
        ResultDto<Object> result = new ResultDto<Object>();
        boolean isExist = authorizerService.checkUserExist(userKey, email);
        result.setType(false);
        if (isExist) {
            request.getSession().setAttribute("userKey", userKey);
            // boolean isSuccess = authorizerService.initPassword(userKey);
            // if (isSuccess) {
            // seandUpdatePassswordUrl(request, response, userKey, email);
            // result.setType(true);
            // result.setMsg("代码君：已审核你的存在，修改密码连接已发送到你的邮箱里，请起干快去操作吧...");
            // }
            seandUpdatePassswordUrl(request, response, userKey, email);
            result.setType(true);
            result.setMsg("代码君：已审核你的存在，请查收邮件后，起快去操作吧...");

        } else {
            result.setMsg("代码君：审核不通过，账号与邮箱不匹配。");
        }
        return result;
    }

    private void seandUpdatePassswordUrl(HttpServletRequest request, HttpServletResponse response, String userKey,
                                         String email) {
        String verificationCode = VerifyCodeUtils.generateVerifyCode(4);
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 存入会话session
        HttpSession session = request.getSession(true);
        // 30分钟失效
        session.setMaxInactiveInterval(30 * 60);
        // 删除以前的
        session.removeAttribute("verCode_" + userKey);
        session.setAttribute("verCode_" + userKey, verifyCode.toLowerCase());

        List<String> toList = Arrays.asList(email);
        String title = "修改账号密码";

        logService.insertOperateLog("jxzhang", "接收修改密码的邮件", JSON.toJSONString(toList));
        mailUtil.send(toList, title, telmplet(verificationCode));
    }

    /**
     * 邮件模板
     *
     * @return
     */
    private String telmplet(String verificationCode) {
        StringBuffer buf = new StringBuffer();
        buf.append("<HTML><head></head><body>");
        buf.append("<p>");
        buf.append("小伙伴<br/>");
        buf.append("</p>");
        buf.append("<div>");
        buf.append("    <br/><div style='margin-left: 100px'>验证码30分钟里有效：" + verificationCode + "</div>");
        buf.append("</div>");
        buf.append("<div>");
        // buf.append( " <br/><a
        // href='http://zrw.gz.chinahrd.net:7080/forgetPassword/toView'><b>用力砸我这..</b></a>");
        buf.append("    <br/><div style='margin-left: 300px'>代码君</div>");
        buf.append("</div>");
        buf.append("</body></html>");
        return buf.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/forgetPassword/authVerifyCode", method = RequestMethod.POST)
    public ResultDto<Object> authVerifyCode(HttpServletRequest request, HttpServletResponse response, String userKey,
                                            String verifyCode) throws Exception {
        ResultDto<Object> result = new ResultDto<Object>();
        HttpSession session = request.getSession(true);
        String sessionVal = (String) session.getAttribute("verCode_" + userKey);
        if (StringUtils.equalsIgnoreCase(verifyCode, sessionVal)) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String basePath = net.chinahrd.utils.WebUtils.getBasePath(httpRequest);
            result.setType(true);
            result.setT(basePath + "/forgetPassword/toUpdatePassword/" + verifyCode + "/" + userKey);
            return result;
        }
        result.setType(false);
        result.setMsg("代码君：验证码错误，请查看你的邮收。");
        return result;
    }

    @RequestMapping(value = "/forgetPassword/toUpdatePassword/{verifyCode}/{userKey}", method = RequestMethod.GET)
    public String toUpdatePassword(@PathVariable("verifyCode") String verifyCode,
                                   @PathVariable("userKey") String userKey, Model model) {
        HttpSession session = request.getSession(true);
        String sessionVal = (String) session.getAttribute("verCode_" + userKey);
        if (StringUtils.equalsIgnoreCase(verifyCode, sessionVal)) {
            model.addAttribute("userKey", userKey);
            return "toUpdatePassword";
        }
        return null;
    }
}

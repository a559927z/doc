package net.chinahrd.platform.rbac.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.chinahrd.common.AbstractBaseController;
import net.chinahrd.common.BaseResponse;
import net.chinahrd.platform.rbac.service.UserInfoService;
import net.chinahrd.request.ModifyPasswdReq;
import net.chinahrd.response.RbacUserRes;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author wzmo
 *
 */
@Slf4j
@RestController
@RequestMapping(value = "/", produces = "application/json; charset=utf-8")
@Api(value = "/", description = "登录")
public class LoginController extends AbstractBaseController {

    @Reference
    private UserInfoService userInfoService;

    @ApiOperation(value = "登录前判断是否已经有用户，", notes = "loginCheck", httpMethod = "GET")
    @GetMapping(value = "/loginCheck")
    public BaseResponse<Object> loginCheck() {

        return setResultData("ok");
    }

    /**
     * 判断系统是否已有用户存在
     * @author wzmo
     * @return
     */
    private int checkUserCount() {
        return 1;
    }
    
    @ApiOperation(value = "login", notes = "login", httpMethod = "POST")
    @GetMapping(name = "登录", value = "/login")
    public BaseResponse<String> login(@RequestParam("userName") String userName, @RequestParam("password") String password) {

        try {
            // 首次登陆创建账号
            if(checkUserCount() == 0){

            }

            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            subject.login(token);
            // 获取已登录的用户信息
            RbacUserRes u = (RbacUserRes) subject.getPrincipal();
            Object obj = getSession().getAttribute("user");
            if (obj == null) {
                getSession().setAttribute("user", u);
                log.debug("保存用户信息到session");
            }
            log.info("shiro登录验证成功");
            return setResultData(null);
        } catch (AuthenticationException e) {
            log.error("用户名或密码错误！",e);
            return setFailedMsg("用户名或密码错误！");
        }

    }


    
    @ApiOperation(value = "logout", notes = "logout", httpMethod = "GET")
    @GetMapping(name = "退出登录", value = "/logout")
    public Object logout(){
        Subject subject = SecurityUtils.getSubject();
        getSession().removeAttribute("user");
        subject.logout();
        return setResultData(null);
    }
    
    @ApiOperation(value = "修改密码", notes = "modifyPasswd", httpMethod = "POST")
    @PostMapping(name = "修改密码", value = "/modifyPasswd")
    public Object modifyPasswd(@RequestBody ModifyPasswdReq req){

        return setSuceessMsg("密码修改成功");
    }
    
}

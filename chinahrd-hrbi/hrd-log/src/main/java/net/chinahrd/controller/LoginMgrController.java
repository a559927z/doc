package net.chinahrd.controller;

import com.google.common.collect.Maps;
import net.chinahrd.constants.BaseConstants;
import net.chinahrd.dto.BaseEmpDto;
import net.chinahrd.dto.EmpExtendDto;
import net.chinahrd.service.LoginMgrService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author jxzhang on 2018年05月01
 * @Verdion 1.0版本
 */
@Controller
@RequestMapping("/login")
public class LoginMgrController {

    @Autowired
    private LoginMgrService loginMgrService;

    /**
     * 登录
     *
     * @param request
     * @param baseEmpDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/in.do")
    public Map<String, Object> in(HttpServletRequest request, @RequestBody BaseEmpDto baseEmpDto) {
        Map<String, Object> result = Maps.newHashMap();

        String userNameCh = baseEmpDto.getUserNameCh();
        String empKey = baseEmpDto.getEmpKey();


        EmpExtendDto empDto = authenticate(userNameCh, empKey);
        if (null == empDto) {
            result.put("code", 0);
            result.put("msg", "账号密码错误或账号已被锁定...");
            return result;
        }
        List<String> roleKeyList = empDto.getRoleKeyList();

        boolean hasRole = roleKeyList.stream().anyMatch(i ->
                BaseConstants.SUPER_ADMIN.equals(i) || StringUtils.contains(i, BaseConstants.ADMIN)
        );

        if (!hasRole) {
            result.put("code", 0);
            result.put("msg", "没有管理员权限,无法操作...");
            return result;
        }
        HttpSession session = request.getSession();
        // 受权成功
        session.setAttribute("empInfo", empDto);
        result.put("code", 1);
        return result;
    }

    /**
     * 登出
     *
     * @param request
     */
    @RequestMapping(value = "/out.do")
    public void out(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("empInfo", null);
    }

    /**
     * 受权
     *
     * @param userKey
     * @param inputPwd
     * @return
     */
    private EmpExtendDto authenticate(String userKey, String inputPwd) {
        // 组装明密
        String keyPwd = inputPwd + StringUtils.substring(userKey, 1, 2);
        // 对输入时加密
        String enPwd = Base64.encodeToString(keyPwd.getBytes());
        EmpExtendDto user = loginMgrService.getUser(userKey, enPwd);
        return user;
    }
}

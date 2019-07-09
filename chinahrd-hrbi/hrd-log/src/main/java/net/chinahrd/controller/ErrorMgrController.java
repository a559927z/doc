package net.chinahrd.controller;

import net.chinahrd.dto.KVItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jxzhang on 2018年05月01
 * @Verdion 1.0版本
 */
@Controller
@RequestMapping("/error")
public class ErrorMgrController {

    @RequestMapping(value = "/unAuthorized.do")
    public String unAuthorized() {
        return "error/403";
    }


    /**
     * 没操作权限
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/unAuthorizedBtn.do")
    public KVItemDto<Boolean, String> unAuthorizedBtn() {
        return new KVItemDto<Boolean, String>(false, "没操作权限");
    }


}

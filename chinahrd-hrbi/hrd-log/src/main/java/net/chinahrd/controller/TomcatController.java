package net.chinahrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/log")
public class TomcatController {

    @RequestMapping(value = "/toTomcatLog", method = RequestMethod.GET)
    public String toTomcatLog() {
        return "tomcatLog";
    }
}

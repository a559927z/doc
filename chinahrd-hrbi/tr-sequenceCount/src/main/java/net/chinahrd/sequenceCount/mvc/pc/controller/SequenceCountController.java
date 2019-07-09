package net.chinahrd.sequenceCount.mvc.pc.controller;

import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import net.chinahrd.entity.dto.pc.sequenceCount.SequenceParentItemsDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.sequenceCount.mvc.pc.service.SequenceCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 职位序列统计
 * Created by wqcai on 15/10/27 0028.
 */
@Controller
@RequestMapping("/sequenceCount")
public class SequenceCountController extends BaseController {


    @Autowired
    private SequenceCountService sequenceCountService;

    /**
     * 跳转到职位序列统计页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/toSequenceCountView")
    public String toSequenceCountView(HttpServletRequest request) {
        return "biz/other/sequenceCount2";
    }

    /**
     * 获取主序列和子序列信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSequence")
    public List<SequenceItemsDto> getSequence() {
        return sequenceCountService.querySequenceOrSub(getCustomerId());
    }

    /**
     * 获取职位序列统计信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSequenceCount")
    public SequenceParentItemsDto getSequenceCount(String organId, boolean hasSub, String sequenceId, String subSequenceStr, Boolean hasJobTitle) {
//        return sequenceCountService.querySequenceCount(getCustomerId(), organId, hasSub, hasJobTitle, sequenceId, subSequenceStr);
    	
    	return sequenceCountService.querySequenceCount4Ctrl(getCustomerId(), organId, hasSub, hasJobTitle, sequenceId, subSequenceStr);
    }
}

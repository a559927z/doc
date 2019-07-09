package net.chinahrd.talentStructure.mvc.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.app.talent.structure.TalentstructureDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.talentStructure.mvc.app.service.MobileTalentStructureService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author htpeng
 *2016年4月7日下午2:23:37
 */
@Controller
@RequestMapping("mobile/talentStructure")
public class MobileTalentStructureController extends BaseController {
    @Autowired
    MobileTalentStructureService talentStructureService;

    @RequestMapping(value = "/toTalentStructureView")
    public String toTalentStructureView(HttpServletRequest request) {
    	DateTime dt=new DateTime(DateUtil.getDBCurdate());
    	request.setAttribute("time", dt.toString("yyyy.MM.dd"));
    	Object obj = request.getParameter("organId");
		List<OrganDto> organPermit = getOrganPermit();
		if (CollectionKit.isNotEmpty(organPermit)) {
			if (null == obj) {
				// 当前机构id
				OrganDto topOneOrgan = organPermit.get(0);
				request.setAttribute("reqOrganId",
						topOneOrgan.getOrganizationId());
				request.setAttribute("reqOrganName",
						topOneOrgan.getOrganizationName());
			} else {
				String orgId = obj.toString();
				request.setAttribute("reqOrganId", orgId);
				boolean bool = false;
				for (OrganDto topOneOrgan : organPermit) {
					if (topOneOrgan.getOrganizationId().equals(orgId)) {
						request.setAttribute("reqOrganName",
								topOneOrgan.getOrganizationName());
						bool = true;
						break;
					}
				}
				if (!bool) {
					request.setAttribute("reqOrganName", "没有找到");
				}
			}
		}
        return "mobile/competency/talentStructure";
    }

    /**
     * 编制分析 by jxzhag on 2016-02-22
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBudgetAnalyse", method = RequestMethod.POST)
    public TalentstructureDto getBudgetAnalyse(String organId) {
        return talentStructureService.findBudgetAnalyse(organId, getCustomerId());
    }


   
    /**
     * 人力结构数据 by jxzhang on 2016-02-23
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentStuctureData", method = RequestMethod.POST)
    public Map<String, Object> getTalentStuctureData(String organId) {
        return talentStructureService.getTalentStuctureData(organId, getCustomerId());
    }

    /**
     * 根据主序列查询能力层级分布-柱状图 by jxzhang 2016-02-26
     *
     * @param organId
     * @param customerId
     * @param seqId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAbEmpCountBarBySeqId", method = RequestMethod.POST)
    public Map<String, Integer> getAbEmpCountBarBySeqId(String organId, String seqId) {
        return talentStructureService.queryAbEmpCountBarBySeqId(organId, getCustomerId(), seqId);
    }
    
   /**
    * 查询所有序列
    * @param organId
    * @param seqId
    * @return
    */
    @ResponseBody
    @RequestMapping(value = "/getAllSeq", method = RequestMethod.POST)
    public List<KVItemDto> getAllSeq() {
        return talentStructureService.quertAllSeq(getCustomerId());
    }
}

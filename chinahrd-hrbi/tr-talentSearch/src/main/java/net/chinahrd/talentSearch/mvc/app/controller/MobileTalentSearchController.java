package net.chinahrd.talentSearch.mvc.app.controller;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.common.EmpDto;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.talentSearch.mvc.app.service.MobileTalentSearchService;
import net.chinahrd.utils.Transcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 移动端 人员搜索
 * 
 * @author htpeng 2016年5月16日下午5:53:37
 */
@Controller
@RequestMapping("mobile/talentSearch")
public class MobileTalentSearchController extends BaseController {
	@Autowired
	MobileTalentSearchService mobileTalentSearchService;
	
//	 public PaginationDto<AccordDismissDto> queryRunOffDetail(String organId, String prevQuarter,Integer page, Integer rows) {
//	        PaginationDto<AccordDismissDto> dto = new PaginationDto<AccordDismissDto>(page, rows);
//	        return mobileAccordDismissService.queryRunOffDetail( getCustomerId(), organId,prevQuarter, dto );
//	    }
	@ResponseBody
	@RequestMapping(value = "/getSearch", method = RequestMethod.GET)
	public PaginationDto<EmpDto> getTalentSearch(String key,Integer page,Integer pageSize) {
		 PaginationDto<EmpDto> dto = new PaginationDto<EmpDto>(page, pageSize);
		
		 return mobileTalentSearchService.queryTalentSearch(getCustomerId(), Transcode.toStringHex(key),getOrganPermit(), dto);

	}

	
	@RequestMapping(value = "/talentSearchJX", method = RequestMethod.GET)
	public String talentSearchJX(String empId) {
		 request.setAttribute("empId", empId);
		 return "mobile/talentSearch/talentSearch_jx";

	}
	
	@RequestMapping(value = "/talentSearchGroup", method = RequestMethod.GET)
	public String talentSearchGroup(String empId) {
		 request.setAttribute("empId", empId);
		 return "mobile/talentSearch/talentSearch_group";

	}
}

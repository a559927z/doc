package net.chinahrd.talentSearch.mvc.pc.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.common.EmpDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.emp.TalentSearch;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.OrganService;
import net.chinahrd.mvc.pc.service.common.CommonService;
import net.chinahrd.talentSearch.mvc.pc.service.TalentSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 人才搜索Controller
 * Created by qpzhu on 16/03/02 .
 */
@Controller
@RequestMapping("/talentSearch")
public class TalentSearchController extends BaseController {

    @Autowired
    private CommonService commonService;
    @Autowired
    private TalentSearchService talentSearchService;
    @Autowired
	private OrganService organService;
    /**
     * 跳转到人才搜索页面
     *
     * @return
     */
    @RequestMapping(value = "/toTalentSearchView")
    public String toTalentSearchView(Model model, String keyName) {
        if (keyName != null){
            try {
                keyName = URLDecoder.decode(keyName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("keyName", keyName);
        return "biz/employee/talentSearch";
    }
    
    /**
     * 数据导出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/toResultExport")
    public void resultExport(HttpServletRequest request,HttpServletResponse response) {
        JSONObject jsonMap = JSONObject.parseObject(request.getParameter("conditionMap"));
        talentSearchService.dataResultExport(parameterHandling(jsonMap), getCustomerId(),response);
    }
    
    
    /**
     * 查询员工信息
     *
     * @param keyName
     * @param page
     * @param rows
     * @param sidx
     * @param sord
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findEmpAll", method = RequestMethod.POST)
    public PaginationDto<EmpDto> findEmpAll(String keyName,String reqOrgId, Integer page, Integer rows, String sidx, String sord) {
        PaginationDto<EmpDto> dto = new PaginationDto<EmpDto>(page, rows);
        dto = commonService.findEmpAll(keyName,reqOrgId, dto, sidx, sord, getCustomerId());
        return dto;
    }
    
    /**
     * 查询员工信息
     *
     * @param request
     * @param page
     * @param rows
     * @param sidx
     * @param sord
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findEmpAdvancedAll", method = RequestMethod.POST)
    public PaginationDto<EmpDto> findEmpAdvancedAll(HttpServletRequest request, Integer page, Integer rows, String sidx, String sord) {
        PaginationDto<EmpDto> dto = new PaginationDto<EmpDto>(page, rows);
        JSONObject jsonMap = JSONObject.parseObject(request.getParameter("conditionMap"));
        dto = talentSearchService.findEmpAdvancedAll(parameterHandling(jsonMap), dto, sidx, sord, getCustomerId());
        return dto;
    }
    
    
    /**
     * 对前台传过来的数据处理
	 * @param 
	 * @return
	 */
	public TalentSearch parameterHandling(JSONObject json) {
		return talentSearchService.parameterHandling(json);
	}
	
	
	
    /**
     * 跳转到人才高级搜索页面
     *
     * @return
     */
    @RequestMapping(value = "/toTalentSearchAdvancedView")
    public String toTalentSearchAdvancedView(Model model, String keyName) {
        if (keyName != null){
            try {
                keyName = URLDecoder.decode(keyName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("keyName", keyName);
        return "biz/employee/talentSearchAdvanced";
    }

	/**
	 * @param UserId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTalentSearchOrgan", method = RequestMethod.POST)
	public List<OrganDto> getTalentSearchOrgan() {
		return organService.queryOrganPermit2(getUserId());
	}
    /**
     * 跳转到员工详情页面
     *
     * @return
     */
    @RequestMapping(value = "/toTalentDetailView")
    public String toTalentDetailView(HttpServletRequest request, @RequestParam("empId") String empId,String anchor) {
        final String customerId = getCustomerId();
		EmpDetailDto empDto = talentSearchService.findEmpDetail(empId, customerId);
        String performanceStr = commonService.getPerformanceStr(customerId);
        request.setAttribute("empDto", empDto);
        if(null == anchor){
        	anchor = "";
        }
        request.setAttribute("anchor", anchor);
        
        request.setAttribute("performanceStr", performanceStr);
        return "biz/employee/talentDetail";
    }
}

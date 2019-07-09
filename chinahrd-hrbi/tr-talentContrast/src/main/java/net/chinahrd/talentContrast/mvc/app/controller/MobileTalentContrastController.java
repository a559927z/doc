package net.chinahrd.talentContrast.mvc.app.controller;

import java.util.Collections;
import java.util.List;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.EvalReportDto;
import net.chinahrd.entity.dto.app.JobChangeDto;
import net.chinahrd.entity.dto.app.emp.EmpPastResumeDto;
import net.chinahrd.entity.dto.app.employeePerformance.PerfChangeDto;
import net.chinahrd.entity.dto.app.talentContrast.ContrastEmpDto;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.talentContrast.mvc.app.service.MobileTalentContrastService;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 移动端
 * 人员对比Controller
 * @author htpeng
 *2016年3月30日下午5:32:32
 */
@Controller
@RequestMapping("mobile/talentContrast")
public class MobileTalentContrastController extends BaseController{


    @Autowired
    private MobileTalentContrastService mobileTalentContrastService;


    @RequestMapping(value = "/toTalentContrastView")
    public String toAccordDismissView(String empIds) {
    	if(null==empIds){
    		//empIds="e673c034589448a0bc05830ebf85c4d6,1c1d7d8e52d64140ac13b2ded5a0e3c7";
    		empIds="";
    	}
//	    String performanceStr = commonService.getPerformanceStr(getCustomerId());
	    request.setAttribute("performanceStr", "一星,二星,三星,四星,五星");
	    request.setAttribute("performanceValue", "1,2,3,4,5");
    	request.setAttribute("empIds", empIds);
        return "mobile/employee/talentContrast";
    }

   





    /**
     * 根据员工ID查询员工信息
     *
     * @param empIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEmpInfo", method = RequestMethod.POST)
    public ContrastEmpDto getEmpInfo(@Param("empId") String empId) {
//        List<String> empList = CollectionKit.strToList(empIds);
        return mobileTalentContrastService.queryContrastAll(getCustomerId(), empId,getOrganPermit());
    }
    
    
    
    /**
     * 获取员工绩效信息（多个员工id用“,”号隔开）
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getPerfChange")
    public List<PerfChangeDto> getPerfChange(@RequestParam("empId") String empId) {
    	if(StringUtils.isEmpty(empId)){
    		return Collections.emptyList();
    	}
        List<PerfChangeDto> dtos = mobileTalentContrastService.queryPerfChange(empId, getCustomerId());
        return dtos;
    }
    
    
    /**
     * 获取员工工作异动信息（多个员工id用“,”号隔开）
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getJobChange", method = RequestMethod.POST)
    public List<JobChangeDto> getJobChange(@RequestParam("empId") String empId) {
    	if(StringUtils.isEmpty(empId)){
    		return Collections.emptyList();
    	}
        List<JobChangeDto> dtos = mobileTalentContrastService.queryJobChange(empId, getCustomerId(),null);
        return dtos;
    }
    
    
    /**
     * 获取员工最新一次的测评信息
     *
     * @param empId （多个员工id用“,”号隔开）
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getEvalReport")
    public List<EvalReportDto> getEvalReport(@RequestParam("empId") String empId) {
        if (StringUtils.isEmpty(empId)) {
//            return Collections.emptyList();
        	return null;
        }
        List<EvalReportDto> dtos = mobileTalentContrastService.queryNewEvalReport(getCustomerId(), empId);
        return dtos;
    }
    
    
    /**
     * 获取员工在本公司工作经历信息（多个员工id用“,”号隔开）
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getDepartChange")
    public List<JobChangeDto> getDepartChange(@RequestParam("empId") String empId) {
        if (StringUtils.isEmpty(empId)) {
            return Collections.emptyList();
        }
        List<JobChangeDto> dtos = mobileTalentContrastService.queryJobChange(empId, getCustomerId(), null);
        return filterData(dtos);
    }
    /**
     * 处理本公司过往经历  相邻 同岗位 部门 取后一条
     * @param sList
     * @return
     */
    private List<JobChangeDto> filterData(List<JobChangeDto> sList){
        String record="";
        //List<JobChangeDto> removeList=new ArrayList<JobChangeDto>();
        for(int i=0;i<sList.size();i++){
        	JobChangeDto obj=sList.get(i);
        	if(record.equals(obj.getOrganName()+"-"+obj.getPositionName())){
        	//	removeList.add(sList.get(i-1));
        		sList.remove(i-1);
        		i--;
        	}
        	record=obj.getOrganName()+"-"+obj.getPositionName();
        }
    	return sList;
    }
    
    /**
     *获取员工过往履历信息 （多个员工id用“,”号隔开）
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getPastResume")
    public List<EmpPastResumeDto> getPastResume(@RequestParam("empId") String empId) {
    	if(StringUtils.isEmpty(empId)){
    		return Collections.emptyList();
    	}
        List<EmpPastResumeDto> dtos = mobileTalentContrastService.getPastResume(empId, getCustomerId());
    	return dtos;
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
    @RequestMapping(value = "/getSearchEmpList", method = RequestMethod.POST)
    public PaginationDto<ContrastEmpDto> getSearchEmpList(String keyName, Integer page, Integer rows, String sidx, String sord) {
        PaginationDto<ContrastEmpDto> dto = new PaginationDto<ContrastEmpDto>(page, rows);
        dto = mobileTalentContrastService.queryContrastAll(getCustomerId(), keyName, dto,getOrganPermit());
        return dto;
    }
}

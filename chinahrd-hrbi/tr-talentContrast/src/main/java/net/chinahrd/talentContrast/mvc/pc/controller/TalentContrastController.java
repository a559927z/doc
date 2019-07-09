package net.chinahrd.talentContrast.mvc.pc.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.chinahrd.api.TalentProfileApi;
import net.chinahrd.core.api.ApiManagerCenter;
import net.chinahrd.core.tools.collection.CollectionKit;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.EvalReportDto;
import net.chinahrd.entity.dto.pc.JobChangeDto;
import net.chinahrd.entity.dto.pc.StoreContrastDto;
import net.chinahrd.entity.dto.pc.talentContrast.ContrastEmpDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.common.CommonService;
import net.chinahrd.talentContrast.mvc.pc.service.TalentContrastService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 人才对比Controller
 * Created by wqcai on 15/09/28 0028.
 */
@Controller
@RequestMapping("/talentContrast")
public class TalentContrastController extends BaseController {


    @Autowired
    private TalentContrastService talentContrastService;
    @Autowired
    private CommonService commonService;


    @RequestMapping(value = "/toTalentContrastView")
    public String toTalentContrastView(Model model, String empIds) {
//        String temp = "e673c034589448a0bc05830ebf85c4d6,7c1eeca5577611e5a5e608606e0aa89a,7b7c95d4577611e5a5e608606e0aa89a";
        String performanceStr = commonService.getPerformanceStr(getCustomerId());
        model.addAttribute("performanceStr", performanceStr);
        model.addAttribute("empIds", empIds);
        return "biz/employee/talentContrast";
    }


    /**
     * 根据员工ID查询员工信息
     *
     * @param empIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEmpInfo", method = RequestMethod.POST)
    public List<ContrastEmpDto> getEmpInfo(String empIds) {
        List<String> empList = CollectionKit.strToList(empIds);
        return talentContrastService.queryContrastAll(getCustomerId(), empList);
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
        dto = talentContrastService.queryContrastAll(getCustomerId(), keyName, dto);
        return dto;
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
            return CollectionKit.newList();
        }
        TalentProfileApi api = ApiManagerCenter.getApiDoc(TalentProfileApi.class);
        List<JobChangeDto> dtos = null;
        if (null != api) {
            dtos = api.queryJobChange(empId, getCustomerId(), null);
        }
        return filterData(dtos);
    }

    /**
     * 获取session对象的对比人员信息
     *
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getStoreContrastEmp")
    public List<ContrastEmpDto> getStockContrastEmp(HttpSession session) {
        List<String> empIds = (List<String>) session.getAttribute("storeEmpIds");
        List<ContrastEmpDto> empDtos;
        if (empIds != null) {
            empDtos = getContrastEmps(empIds);
        } else {
            empDtos = new ArrayList<ContrastEmpDto>();
        }
        return empDtos;
    }

    /**
     * 移除session对象的对比人员信息
     *
     * @param session
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/removeStoreContrastEmp")
    public StoreContrastDto removeStoreContrastEmp(HttpSession session, String id) {
        StoreContrastDto storeDto = new StoreContrastDto();
        List<String> empIds = (List<String>) session.getAttribute("storeEmpIds");
        boolean bool = true;
        if (empIds != null) {
            bool = empIds.remove(id);
        }
        if (bool) {
            storeDto.setType(0);
        }
        storeDto.setEmps(getContrastEmps(empIds));
        return storeDto;
    }

    /**
     * 添加对比人员信息
     *
     * @param session
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/setStoreContrastEmp")
    public StoreContrastDto setStoreContrastEmp(HttpSession session, String id) {
        StoreContrastDto storeDto = new StoreContrastDto();
        List<String> empIds = (List<String>) session.getAttribute("storeEmpIds");
        boolean bool = false;
        if (empIds != null) {
            if (empIds.size() >= 4) {     //判断是否超过4个对比人员
                storeDto.setType(1);
                bool = true;
            }
            if (!bool) bool = empIds.contains(id);
            if (bool) storeDto.setType(2);       //判断是否存在相同
        } else {
            empIds = new ArrayList<String>();
        }
        if (!bool) {
            storeDto.setType(0);
            empIds.add(id);
            session.setAttribute("storeEmpIds", empIds);
        }
        storeDto.setEmps(getContrastEmps(empIds));
        return storeDto;
    }

    /**
     * 获取存储员工信息
     *
     * @param empIds
     * @return
     */
    private List<ContrastEmpDto> getContrastEmps(List<String> empIds) {
        List<ContrastEmpDto> empDTOs = CollectionKit.newList();
        if (empIds.size() > 0) {
            List<ContrastEmpDto> dtos = talentContrastService.queryContrastAll(getCustomerId(), empIds);
            for (int j = 0; j < empIds.size(); j++) {
                String empId = empIds.get(j);
                for (int i = 0; i < dtos.size(); i++) {
                    if (empId.equals(dtos.get(i).getEmpId())) {
                        empDTOs.add(dtos.get(i));
                        break;
                    }
                }
            }
        }
        return empDTOs;
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
            return Collections.emptyList();
        }
        List<EvalReportDto> dtos = talentContrastService.queryNewEvalReport(getCustomerId(), empId);
        return dtos;
    }


    /**
     * 处理本公司过往经历  相邻 同岗位 部门 取后一条
     *
     * @param sList
     * @return
     */
    private List<JobChangeDto> filterData(List<JobChangeDto> sList) {
        String record = "";
        //List<JobChangeDto> removeList=new ArrayList<JobChangeDto>();
        for (int i = 0; i < sList.size(); i++) {
            JobChangeDto obj = sList.get(i);
            if (record.equals(obj.getOrganName() + "-" + obj.getPositionName())) {
                //	removeList.add(sList.get(i-1));
                sList.remove(i - 1);
                i--;
            }
            record = obj.getOrganName() + "-" + obj.getPositionName();
        }
        return sList;
    }
}

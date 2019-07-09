package net.chinahrd.talentProfile.mvc.pc.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.EvalReportDto;
import net.chinahrd.entity.dto.pc.JobChangeDto;
import net.chinahrd.entity.dto.pc.OtherReportDto;
import net.chinahrd.entity.dto.pc.common.EmpDto;
import net.chinahrd.entity.dto.pc.emp.BonusPenaltyDto;
import net.chinahrd.entity.dto.pc.emp.EmpEduDto;
import net.chinahrd.entity.dto.pc.emp.EmpFamilyDto;
import net.chinahrd.entity.dto.pc.emp.EmpPastResumeDto;
import net.chinahrd.entity.dto.pc.emp.ProfTitleDto;
import net.chinahrd.entity.dto.pc.emp.TrainExperienceDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PerfChangeDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.common.CommonService;
import net.chinahrd.talentProfile.mvc.pc.service.TalentProfileService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 人才剖像Controller
 * Created by wqcai on 15/09/28 0028.
 */
@Controller
@RequestMapping("/talentProfile")
public class TalentProfileController extends BaseController {

    @Autowired
    private CommonService commonService;
    @Autowired
    private TalentProfileService talentProfileService;

    /**
     * 跳转到人才剖像页面
     *
     * @return
     */
    @RequestMapping(value = "/toTalentProfileView")
    public String toTalentProfileView(Model model, String keyName) {
        if (keyName != null) {
            try {
                keyName = URLDecoder.decode(keyName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("keyName", keyName);
        return "biz/employee/talentProfile";
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
    public PaginationDto<EmpDto> findEmpAll(String keyName, String reqOrgId, Integer page, Integer rows, String sidx, String sord) {
        PaginationDto<EmpDto> dto = new PaginationDto<EmpDto>(page, rows);
        dto = commonService.findEmpAll(keyName, reqOrgId, dto, sidx, sord, getCustomerId());
        return dto;
    }

    /**
     * 跳转到员工详情页面
     *
     * @return
     */
    @RequestMapping(value = "/toTalentDetailView")
    public String toTalentDetailView(HttpServletRequest request, @RequestParam("empId") String empId, String anchor) {
        String customerId = getCustomerId();
        EmpDetailDto empDto = talentProfileService.findEmpDetail(empId, customerId);
        String performanceStr = commonService.getPerformanceStr(customerId);
        request.setAttribute("empDto", empDto);
        request.setAttribute("performanceStr", performanceStr);
        if (null == anchor) {
            anchor = "";
        }
        request.setAttribute("anchor", anchor);
        return "biz/employee/talentDetail";
    }

    /**
     * 获取员工工作异动信息（多个员工id用“,”号隔开）
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getJobChange")
    public List<JobChangeDto> getJobChange(@RequestParam("empId") String empId) {
        if (StringUtils.isEmpty(empId)) {
            return Collections.emptyList();
        }
        List<JobChangeDto> dtos = talentProfileService.queryJobChange(empId, getCustomerId(), null);
        return dtos;
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
        if (StringUtils.isEmpty(empId)) {
            return Collections.emptyList();
        }
        List<PerfChangeDto> dtos = talentProfileService.queryPerfChange(empId, getCustomerId());
        return dtos;
    }

    /**
     * 获取员工360测评报告信息
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get360Evaluation")
    public List<EvalReportDto> get360Evaluation(@RequestParam("empId") String empId) {
        if (StringUtils.isEmpty(empId)) {
            return Collections.emptyList();
        }
        List<EvalReportDto> dtos = talentProfileService.query360Evaluation(empId, getCustomerId());
        return dtos;
    }

    /**
     * 获取员工其它测评报告信息
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getOtherEvaluation")
    public List<OtherReportDto> getOtherEvaluation(@RequestParam("empId") String empId) {
        if (StringUtils.isEmpty(empId)) {
            return Collections.emptyList();
        }
        List<OtherReportDto> dtos = talentProfileService.queryOtherEvaluation(empId, getCustomerId());
        return dtos;
    }


    /**
     * 获取员工奖惩信息
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getBonusData")
    public List<BonusPenaltyDto> getBonusData(@RequestParam("empId") String empId) {
        if (StringUtils.isEmpty(empId)) {
            return Collections.emptyList();
        }
        List<BonusPenaltyDto> dtos = talentProfileService.getBonusData(empId, getCustomerId());
        return dtos;
    }

    /**
     * 获取员工培训经历信息
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getTrainExp")
    public List<TrainExperienceDto> getTrainExp(@RequestParam("empId") String empId) {
        if (StringUtils.isEmpty(empId)) {
            return Collections.emptyList();
        }
        List<TrainExperienceDto> dtos = talentProfileService.getTrainExp(empId, getCustomerId());
        return dtos;
    }

    /**
     * 获取员工过往履历信息 （多个员工id用“,”号隔开）
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getPastResume")
    public List<EmpPastResumeDto> getPastResume(@RequestParam("empId") String empId) {
        if (StringUtils.isEmpty(empId)) {
            return Collections.emptyList();
        }
        List<EmpPastResumeDto> dtos = talentProfileService.getPastResume(empId, getCustomerId());
        return dtos;
    }

    /**
     * 获取员工教育背景信息
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getEduBg")
    public List<EmpEduDto> getEduBg(@RequestParam("empId") String empId) {
        if (StringUtils.isEmpty(empId)) {
            return Collections.emptyList();
        }
        List<EmpEduDto> dtos = talentProfileService.getEduBg(empId, getCustomerId());
        return dtos;
    }

    /**
     * 获取员工所获职称信息
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getProfTitle")
    public List<ProfTitleDto> getProfTitle(@RequestParam("empId") String empId) {
        if (StringUtils.isEmpty(empId)) {
            return Collections.emptyList();
        }
        List<ProfTitleDto> dtos = talentProfileService.getProfTitle(empId, getCustomerId());
        return dtos;
    }

    /**
     * 获取员工家庭关系信息
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getFamilyData")
    public List<EmpFamilyDto> getFamilyData(@RequestParam("empId") String empId) {
        if (StringUtils.isEmpty(empId)) {
            return Collections.emptyList();
        }
        List<EmpFamilyDto> dtos = talentProfileService.getFamilyData(empId, getCustomerId());
        return dtos;
    }
}

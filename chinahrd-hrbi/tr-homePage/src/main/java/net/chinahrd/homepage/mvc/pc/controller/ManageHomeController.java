package net.chinahrd.homepage.mvc.pc.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.chinahrd.api.AccordDismissApi;
import net.chinahrd.api.OrganizationalStructureApi;
import net.chinahrd.core.api.ApiManagerCenter;
import net.chinahrd.eis.annotation.log.ControllerAop;
import net.chinahrd.entity.dto.pc.OrgChartDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissTrendDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.emp.WorkOvertimeDto;
import net.chinahrd.entity.dto.pc.manage.EmpBaseInfoDto;
import net.chinahrd.entity.dto.pc.manage.GainOfLossDto;
import net.chinahrd.entity.dto.pc.manage.HomeConfigDto;
import net.chinahrd.entity.dto.pc.manage.RemindEmpDto;
import net.chinahrd.entity.dto.pc.manage.TalentDevelEmpDto;
import net.chinahrd.entity.dto.pc.manage.WarnInfoDto;
import net.chinahrd.homepage.mvc.pc.service.HomeService;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.FunctionService;
import net.chinahrd.utils.CacheHelper;
//import net.chinahrd.mvc.pc.service.drivingforce.AccordDismissService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.jqgrid.TreeGridData;
import net.chinahrd.utils.jqgrid.TreeGridRow;

/**
 * 管理者首页
 * Created by wqcai on 15/11/09 0009.
 */
@Controller
@RequestMapping("/manageHome")
public class ManageHomeController extends BaseController {

    @Autowired
    private HomeService manageHomeService;

    @Autowired
    private FunctionService functionService;

    /**
     * 跳转到管理者首页
     *
     * @param request
     * @return
     */
    @ControllerAop(description = "跳转到管理者首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String home(HttpServletRequest request) {
        //最新数据更新时间
        Date updateDate = manageHomeService.findUpdateDate(getCustomerId());
        request.setAttribute("updateDate", updateDate);
        return "biz/manage/home2";
    }

    /**
     * 跳转到老板首页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/bossIndex", method = RequestMethod.GET)
    public String BossIndex(HttpServletRequest request) {
        List<OrganDto> organPermit = getOrganPermit();
        if (CollectionKit.isNotEmpty(organPermit)) {
            OrganDto topOneOrgan = organPermit.get(0);
            request.setAttribute("reqOrganId", topOneOrgan.getOrganizationId());
            request.setAttribute("reqOrganName", topOneOrgan.getOrganizationName());
        }
        //入司周年规则客户定制
        String entryAnnualStr = "";
        List<Integer> annuals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.SY_ANNUAL);
        for (Integer annual : annuals) {
            entryAnnualStr += ("".equals(entryAnnualStr) ? "" : ",") + annual.intValue();
        }
        request.setAttribute("entryAnnual", entryAnnualStr);
        //最新数据更新时间
        Date updateDate = manageHomeService.findUpdateDate(getCustomerId());
        request.setAttribute("updateDate", updateDate);
        return "biz/manage/bossHome";
    }

    /**
     * 跳转到直线首页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/lineIndex", method = RequestMethod.GET)
    public String LineIndex(HttpServletRequest request) {
        List<OrganDto> organPermit = getOrganPermit();
        if (CollectionKit.isNotEmpty(organPermit)) {
            OrganDto topOneOrgan = organPermit.get(0);
            request.setAttribute("reqOrganId", topOneOrgan.getOrganizationId());
            request.setAttribute("reqOrganName", topOneOrgan.getOrganizationName());
        }
        //入司周年规则客户定制
        String entryAnnualStr = "";
        List<Integer> annuals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.SY_ANNUAL);
        for (Integer annual : annuals) {
            entryAnnualStr += ("".equals(entryAnnualStr) ? "" : ",") + annual.intValue();
        }
        request.setAttribute("entryAnnual", entryAnnualStr);
        //最新数据更新时间
        Date updateDate = manageHomeService.findUpdateDate(getCustomerId());
        request.setAttribute("updateDate", updateDate);
        return "biz/manage/lineHome";
    }

    /**
     * 跳转到HR首页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/hrIndex", method = RequestMethod.GET)
    public String HRIndex(HttpServletRequest request) {
        List<OrganDto> organPermit = getOrganPermit();
        if (CollectionKit.isNotEmpty(organPermit)) {
            OrganDto topOneOrgan = organPermit.get(0);
            request.setAttribute("reqOrganId", topOneOrgan.getOrganizationId());
            request.setAttribute("reqOrganName", topOneOrgan.getOrganizationName());
        }
        //入司周年规则客户定制
        String entryAnnualStr = "";
        List<Integer> annuals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.SY_ANNUAL);
        for (Integer annual : annuals) {
            entryAnnualStr += ("".equals(entryAnnualStr) ? "" : ",") + annual.intValue();
        }
        request.setAttribute("entryAnnual", entryAnnualStr);
        //最新数据更新时间
        Date updateDate = manageHomeService.findUpdateDate(getCustomerId());
        request.setAttribute("updateDate", updateDate);
        return "biz/manage/hrHome";
    }

    /**
     * 获取页面拖拽数据
     */
    @ResponseBody
    @RequestMapping(value = "/findUserHomeConfig", method = RequestMethod.GET)
    public List<HomeConfigDto> findUserHomeConfig() {
        List<HomeConfigDto> homeConfigDtos = functionService.queryUserHomeConfig(getUserEmpId(), getCustomerId());
        return homeConfigDtos;
    }


    /**
     * 保存首页拖拽配置信息
     */
    @ResponseBody
    @RequestMapping(value = "/editHomeConfig", method = RequestMethod.POST)
    public boolean editHomeConfig(String homeConfigObj) {
        functionService.editUserHomeConfig(homeConfigObj, getUserEmpId(), getCustomerId());
        return true;
    }


    /**
     * 获取团队成员信息
     *
     * @param organId 机构ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTeamEmp", method = RequestMethod.POST)
    public PaginationDto<EmpBaseInfoDto> getTeamEmp(String organId, Integer page, Integer rows, String sidx, String sord) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        PaginationDto<EmpBaseInfoDto> dto = new PaginationDto<EmpBaseInfoDto>(page, rows);
        dto = manageHomeService.queryTeamEmp(organId, getCustomerId(), dto);
        return dto;
    }

    /**
     * 获取团队提醒-生日榜成员信息
     *
     * @param organId 机构ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRemindEmp", method = RequestMethod.POST)
    public PaginationDto<RemindEmpDto> getRemindEmp(String organId, Integer page, Integer rows, String sidx, String sord) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        PaginationDto<RemindEmpDto> dto = new PaginationDto<RemindEmpDto>(page, rows);
        dto = manageHomeService.queryRemindEmp(organId, getCustomerId(), dto);
        return dto;
    }

    /**
     * 获取团队提醒当月生日员工信息
     *
     * @param organId 机构ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTeamRemind", method = RequestMethod.POST)
    public Map<String, List<RemindEmpDto>> getTeamRemind(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        String customerId = getCustomerId();
        Timestamp currTime = DateUtil.getTimestamp();
        Map<String, List<RemindEmpDto>> map = CollectionKit.newMap();
        map.put("birthday", manageHomeService.queryBirthdayRemind(organId, currTime, customerId));
        map.putAll(manageHomeService.queryAnnualRemind(organId, currTime, customerId));
        return map;
    }

    /**
     * 获取当季人才损益信息
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getGainOfLoss", method = RequestMethod.POST)
    public GainOfLossDto getGainOfLoss(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manageHomeService.findGainOfLossInfo(organId, getCustomerId());
    }

    /**
     * 由获取组织机构图子节点数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getChildOrgData", method = RequestMethod.GET)
    public TreeGridData getChildOrgData(HttpServletRequest request, HttpServletResponse response) {
    	 String orgId = request.getParameter("nodeid");
         String lvl = request.getParameter("n_level");
         int level = 0;
         if (lvl != null && !"".equals(lvl)) {
             level = Integer.parseInt(lvl);
         }
         if (orgId == null || "".equals(orgId)) {
             //orgId="fcb4d31b3470460f93be81cf1dd64cf0";
             return null;
         }
//         queryChildOrgById
         List<OrgChartDto> list = null;
         OrganizationalStructureApi api=ApiManagerCenter.getApiDoc(OrganizationalStructureApi.class);
         //         ApiVaild<List<OrgChartDto>> apiVaild = OrganizationalStructureApi.queryChildOrgById.get(getCustomerId(), orgId);
//        if(apiVaild.isValid()){
//        	list=apiVaild.getResult();
//        }
         // 没有指标，不再往下走 不再建TreeGridData对像。by jxzhang on 2016-11-25
         if(null==api){
        	 return null;
         }
         list=api.queryChildOrgById(getCustomerId(), orgId);
         
         TreeGridData grid = new TreeGridData();
         grid.setPage(1);
         grid.setRecords(list.size());
         grid.setTotal(1);
         List<TreeGridRow> rows = new ArrayList<TreeGridRow>();
         for (OrgChartDto d : list) {
             TreeGridRow row = new TreeGridRow();
             row.setId(d.getOrganizationId());
             //level parent_id isLeaf expanded
             Object[] cell = null;
             if (level > 0) {
                 cell = new Object[]{d.getOrganizationId()
                         , d.getOrganizationName()
                         , d.getNumber()
                         , d.getUsableEmpCount()
                         , d.getEmpCount()
                         , d.getUserName()
                         , level
                         , d.getOrganizationParentId()
                         , d.getIsLeaf()
                         , false
                 };
             } else {
                 cell = new Object[]{d.getOrganizationId()
                         , d.getOrganizationName()
                         , d.getNumber()
                         , d.getUsableEmpCount()
                         , d.getEmpCount()
                         , d.getUserName()
                         , level
                         , null
                         , d.getIsLeaf()
                         , false
                 };
             }
             row.setCell(cell);
             rows.add(row);
         }
         grid.setRows(rows);
         return grid;
    }


//    /**
//     * 跳转到团队画像
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/toTeamImgView", method = RequestMethod.GET)
//    public String teamImg(HttpServletRequest request, String organId) {
//        List<OrganDto> organPermit = getOrganPermit();
//        if (null == organId) {
//            if (CollectionKit.isNotEmpty(organPermit)) {
//                OrganDto topOneOrgan = organPermit.get(0);
//                request.setAttribute("reqOrganId", topOneOrgan.getOrganizationId());
//                request.setAttribute("reqOrganName", topOneOrgan.getOrganizationName());
//            }
//        } else {
//            if (CollectionKit.isNotEmpty(organPermit)) {
//                request.setAttribute("reqOrganId", organId);
//                for (OrganDto organDto : organPermit) {
//                    if (organDto.getOrganizationId().equals(organId)) {
//                        request.setAttribute("reqOrganName", organDto.getOrganizationName());
//                        break;
//                    }
//                }
//            }
//        }
//        //最新数据更新时间
//        Date updateDate = manageHomeService.findUpdateDate(getCustomerId());
//        request.setAttribute("updateDate", updateDate);
//        return "biz/manage/teamImg";
//    }

    /**
     * 跳转到团队画像
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/toTeamImgView", method = RequestMethod.GET)
    public String teamImg2(HttpServletRequest request) {
        //最新数据更新时间
        Date updateDate = manageHomeService.findUpdateDate(getCustomerId());
        request.setAttribute("updateDate", updateDate);
        return "biz/manage/teamImg2";
    }


    /**
     * 团队画像
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTeamImgAb", method = RequestMethod.POST)
    public Map<Integer, Object> getTeamImgAb(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manageHomeService.getTeamImgAb(organId, getCustomerId());
    }

    /**
     * 管理者首页-人才发展
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentDevel", method = RequestMethod.POST)
    public Map<Integer, Object> getTalentDevel(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manageHomeService.getTalentDevel(organId, getCustomerId());
    }

    /**
     * 管理者首页-人才发展-测试项目
     *
     * @param yearInt
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentDevelExamItem", method = RequestMethod.POST)
    public List<TalentDevelEmpDto> getTalentDevelExamItem(Integer yearInt, String empId) {
        if (StringUtils.isEmpty(empId)) {
            return null;
        }
        return manageHomeService.getTalentDevelExamItem(yearInt, empId, getCustomerId());
    }


    /**
     * 绩效目标
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPerformance", method = RequestMethod.POST)
    public Map<Integer, Object> getPerformance(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manageHomeService.getPerformance(organId, getCustomerId());
    }

    /**
     * 获取最近2个月的加班信息
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWorkOvertimeInfo", method = RequestMethod.POST)
    public List<WorkOvertimeDto> getWorkOvertimeInfo(String empId) {
        if (StringUtils.isEmpty(empId)) {
            return null;
        }
        return manageHomeService.getWorkOvertimeInfo(empId, getCustomerId());
    }

//    /**
//     * 获取预警信息
//     *
//     * @param organId
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/getWarnInfo", method = RequestMethod.POST)
//    public  List<WarnSynopsisDto> getWarnInfo(String organId) {
//    	 if (StringUtils.isEmpty(organId)) {
//             return null;
//         }
//         return manageHomeService.getWarnInfo(organId, getCustomerId());
//    }

    /**
     * 获取高绩效与低绩效预警信息
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPerformanceInfo", method = RequestMethod.POST)
    public Map<String, List<WarnInfoDto>> getHighPerformanceInfo(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        Map<String, List<WarnInfoDto>> map = CollectionKit.newMap();
        map.put("highPerfEmp", manageHomeService.queryHighPerformanceEmp(organId, getCustomerId()));
        map.put("lowPerfEmp", manageHomeService.queryLowPerformanceEmp(organId, getCustomerId()));
        return map;
    }

    /**
     * 首页预警统计信息
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getHomeWarnCount", method = RequestMethod.POST)
    public Map<String, Object> getHomeWarnCount(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        String customerId = getCustomerId();
        Map<String, Object> map = CollectionKit.newMap();
        map.put("runOffWarn", manageHomeService.queryRunOffWarnCount(customerId, organId));
        
        AccordDismissApi accordDismissApi=ApiManagerCenter.getApiDoc(AccordDismissApi.class);
//        ApiVaild<DismissTrendDto> quarterLastDayApi=AccordDismissApi.queryDisminss4Quarter.get(organId, customerId);
//        if(quarterLastDayApi.isValid()){
        // 判空
        if(null==accordDismissApi){return null;}
        
		DismissTrendDto dto = accordDismissApi.queryDisminss4Quarter(customerId, organId);
		// 主动流失率-正常值和风险值
		double normal = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.ZDLSL_NORMAL);
		double risk = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.ZDLSL_RISK);
		double rate = 0d;
		if (null != dto) {
			rate = dto.getRate();
		}
		String rateCompate = "normal"; // 正常
		if (rate > normal && rate <= risk) {
			rateCompate = "risk"; // 预警
		} else if (rate > risk) {
			rateCompate = "exceed"; // 超出
		}
		map.put("dismissRate", rate);
		map.put("rateCompate", rateCompate);
        return map;
    }


    /**
     * 获取离职预警信息
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRunOffWarnEmpInfo", method = RequestMethod.POST)
    public List<WarnInfoDto> getRunOffWarnEmpInfo(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return manageHomeService.queryRunOffWarnEmp(getCustomerId(), organId);
    }

    /**
     * 获取加班超过预警的人员信息
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOvertimeEmp", method = RequestMethod.POST)
    public List<WarnInfoDto> getOvertimeEmp(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        return manageHomeService.queryOvertimeEmp(getCustomerId(), organId);
    }


}

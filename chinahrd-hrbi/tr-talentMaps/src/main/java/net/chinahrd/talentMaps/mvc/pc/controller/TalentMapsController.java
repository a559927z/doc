package net.chinahrd.talentMaps.mvc.pc.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.eis.permission.model.RbacUser;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsEmpCountDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsPointDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsSimpleCountDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsTeamInfoDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsTeamQueryDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.OrganService;
import net.chinahrd.mvc.pc.service.common.MessageService;
import net.chinahrd.talentMaps.mvc.pc.service.TalentMapsService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;
import net.chinahrd.utils.Transcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 人才地图 Created by wqcai on 16/7/4.
 */
@Controller
@RequestMapping("/talentMaps")
public class TalentMapsController extends BaseController {

    @Autowired
    private TalentMapsService talentMapsService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private OrganService organService;

    /**
     * 跳转到人才地图首页
     *
     * @param request
     * @return
     */
    @RequestMapping("/toTalentMapsView")
    public String toTalentMapsView(HttpServletRequest request) {
        
        // 改用缓存提取funId by jxzhang on 2016-11-10
        String functionId = CacheHelper.getFunctionId("talentMaps/toTalentMapsView");
        if(null !=functionId){request.setAttribute("quotaId", functionId); }
		
        //添加审核人权限
        boolean bool = talentMapsService.checkIsAuditor(getUserEmpId(), getCustomerId());
        request.setAttribute("isAuditor", bool);
        return "biz/competency/talentMaps";
    }

    /**
     * 获取人才地图最新时间
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentMapsNewDate", method = RequestMethod.GET)
    public KVItemDto getTalentMapsNewDate() {
        List<String> organIds = getOrganPermitId();
        if (organIds.isEmpty()) return null;

        List<KVItemDto> dateCycles = talentMapsService.queryTalentMapsDateCycle(organIds, getCustomerId());
        if (dateCycles.isEmpty()) return null;

        return dateCycles.get(0);
    }

    /**
     * 跳转到人才地图展示页面
     *
     * @return
     */
    @RequestMapping("/toTalentMapsShow")
    public String toTalentMapsShow(HttpServletRequest request, String cycleDate) {
        request.setAttribute("cycleDate", StringUtils.isEmpty(cycleDate) ? "" : cycleDate);
        List<OrganDto> organPermit = getOrganPermit();
        if (CollectionKit.isNotEmpty(organPermit)) {    //取得顶级机构
            OrganDto topOneOrgan = organPermit.get(0);
            request.setAttribute("reqOrganId", topOneOrgan.getOrganizationId());
            request.setAttribute("reqOrganName", topOneOrgan.getOrganizationName());
        }
        List<String> organIds = getOrganPermitId();
        if (!organIds.isEmpty()) {
            List<KVItemDto> kvItemDtos = talentMapsService.queryTalentMapsDateCycle(organIds, getCustomerId());
            request.setAttribute("kvItemDtos", kvItemDtos);
        }
        return "biz/competency/talentMapsShow";
    }

    /**
     * 获取地图简易模式数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMapsSimpleEmpCount", method = RequestMethod.POST)
    public List<TalentMapsSimpleCountDto> getMapsSimpleEmpCount(TalentMapsTeamQueryDto dto) {
        if (StringUtils.isEmpty(dto.getOrganId())) return null;
        dto.setCustomerId(getCustomerId());
        return talentMapsService.queryMapsSimpleEmpCount(dto);
    }

    /**
     * 获取人才地图点人员信息
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentMapsEmpPoint", method = RequestMethod.POST)
    public PaginationDto<TalentMapsPointDto> getTalentMapsEmpPoint(TalentMapsTeamQueryDto queryDto, Integer page, Integer rows) {
        queryDto.setCustomerId(getCustomerId());
        PaginationDto<TalentMapsPointDto> dto = new PaginationDto<>();
        if (page != null && rows != null) {
            dto = new PaginationDto<TalentMapsPointDto>(page, rows);
        }
        return talentMapsService.queryMapsEmpPoint(queryDto, dto);
    }

    /**
     * 获取人才地图点人员信息-趋势图用
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentMapsEmpPointByStr", method = RequestMethod.POST)
    public PaginationDto<TalentMapsPointDto> getTalentMapsEmpPointByStr(String queryDtoStr, Integer page, Integer rows) {
        if (StringUtils.isEmpty(queryDtoStr)) return null;

        Gson gson = new Gson();
        TalentMapsTeamQueryDto queryDto = gson.fromJson(queryDtoStr, new TypeToken<TalentMapsTeamQueryDto>() {
        }.getType());

        queryDto.setCustomerId(getCustomerId());
        PaginationDto<TalentMapsPointDto> dto = new PaginationDto<>();
        if (page != null && rows != null) {
            dto = new PaginationDto<TalentMapsPointDto>(page, rows);
        }
        return talentMapsService.queryMapsEmpPointStr(queryDto, dto);
    }

    /**
     * 跳转到全部历史审核页面
     *
     * @return
     */
    @RequestMapping("/toTalentMapsAuditingHistoryView")
    public String toTalentMapsAuditingHistory(HttpServletRequest request) {
        return "biz/competency/talentMapsAuditingHistory";
    }

    /**
     * 跳转到查看历史页面
     *
     * @return
     */
    @RequestMapping("/toTalentMapsHistoryView")
    public String toTalentMapsHistoricalReview(HttpServletRequest request, String id, String topId, String adjustmentId, String empId, Integer yearMonth) {
        request.setAttribute("id", id);
        request.setAttribute("topId", topId);
        request.setAttribute("adjustmentId", adjustmentId);
        request.setAttribute("empId", empId);
        request.setAttribute("yearMonth", yearMonth);
        return "biz/competency/talentMapsHistoricalReview";
    }

    /**
     * 跳转到团队能力/绩效趋势图展示页面
     *
     * @return
     */
    @RequestMapping("/toTalentMapsTeamCPView")
    public String toTalentMapsTeamCPView(HttpServletRequest request) {
        List<String> organIds = getOrganPermitId();
        if (organIds.isEmpty()) return null;
//        return talentMapsService.queryTalentMapsDateCycle(organIds, getCustomerId());
        Map<String, Object> map = talentMapsService.queryTimesCycle(organIds, getCustomerId());
        request.setAttribute("map", map);
        return "biz/competency/talentMapsTeamCP";
    }

    /**
     * 团队能力/绩效趋势图 地图显示
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryTeamCPMap", method = RequestMethod.POST)
    public Map<String, Object> queryTeamCPMap(TalentMapsTeamQueryDto dto) {
        if (StringUtils.isEmpty(dto.getOrganId())) {
            return null;
        }
        dto.setCustomerId(getCustomerId());
        return talentMapsService.queryTeamCPMap(dto);
    }

    /**
     * 团队能力/绩效趋势图 地图显示 人员明细
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryTeamCPMapPersonDetail", method = RequestMethod.POST)
    public PaginationDto<TalentMapsDto> queryTeamCPMapPersonDetail(TalentMapsTeamQueryDto dto, Integer page, Integer rows) {
        dto.setCustomerId(getCustomerId());
        PaginationDto<TalentMapsDto> pDto = new PaginationDto<>();
        if (page != null && rows != null) {
            pDto = new PaginationDto<TalentMapsDto>(page, rows);
        }
        return talentMapsService.queryTeamCPMapPersonDetail(dto, pDto);
    }

    /**
     * 团队能力/绩效趋势图 列表显示-标题列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryAbilityForList", method = RequestMethod.POST)
    public Map<String, Object> queryAbilityForList(TalentMapsTeamQueryDto dto) {
        if (StringUtils.isEmpty(dto.getOrganId())) {
            return null;
        }
        dto.setCustomerId(getCustomerId());
        return talentMapsService.queryAbilityForList(dto);
    }

    /**
     * 团队能力/绩效趋势图 列表显示
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryTeamCPGrid", method = RequestMethod.POST)
    public List<TalentMapsDto> queryTeamCPGrid(TalentMapsTeamQueryDto dto,
                                               String titleId, int pageNum, int pageSize) {
        if (StringUtils.isEmpty(dto.getOrganId())) {
            return null;
        }
        dto.setCustomerId(getCustomerId());
        return talentMapsService.queryTeamCPGrid(dto, titleId, pageNum, pageSize);
    }

    /**
     * 团队能力/绩效趋势图- 明细显示
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryTeamCPDetail", method = RequestMethod.POST)
    public Map<String, Object> queryTeamCPDetail(TalentMapsTeamQueryDto dto) {
        if (StringUtils.isEmpty(dto.getOrganId())) {
            return null;
        }
        dto.setCustomerId(getCustomerId());
        return talentMapsService.queryTeamCPDetail(dto);
    }

    /**
     * 团队能力/绩效趋势图- 全屏明细显示
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryTeamCPFullDetail", method = RequestMethod.POST)
    public PaginationDto<TalentMapsPointDto> queryTeamCPFullDetail(TalentMapsTeamQueryDto qDto, Integer page, Integer rows) {
        qDto.setCustomerId(getCustomerId());
        PaginationDto<TalentMapsPointDto> dto = new PaginationDto<TalentMapsPointDto>(page, rows);
        return talentMapsService.queryTeamCPFullDetail(qDto, dto);
    }

    /**
     * 盘点报告 -人员信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryInventoryReportPersonInfo", method = RequestMethod.POST)
    public List<TalentMapsDto> queryInventoryReportPersonInfo(String empId) {
        return talentMapsService.queryInventoryReportPersonInfo(getCustomerId(), empId);
    }

    /**
     * 根据页面显示要求，获取最大最小日期
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMinMaxDate", method = RequestMethod.POST)
    public String getMinMaxDate() {
        List<String> organIds = getOrganPermitId();
        if (organIds.isEmpty()) return null;
        String date = talentMapsService.getMinMaxDate(organIds, getCustomerId());
        JSONObject jo = new JSONObject();
        jo.put("date", date);
        return jo.toString();
    }

    /**
     * 盘点报告 -人才地图轨迹
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryTalentMapTrajectory", method = RequestMethod.POST)
    public Map<String, Object> queryTalentMapTrajectory(String empId, String date, Integer rows) {
        return talentMapsService.queryTalentMapTrajectory(getCustomerId(), empId, date, rows);
    }

    /**
     * 盘点报告 -绩效轨迹
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryPerformanceTrajectory", method = RequestMethod.POST)
    public Map<String, Object> queryPerformanceTrajectory(String empId, Integer rows) {
        return talentMapsService.queryPerformanceTrajectory(getCustomerId(), empId, rows);
    }

    /**
     * 盘点报告 -能力轨迹
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryAbilityTrajectory", method = RequestMethod.POST)
    public Map<String, Object> queryAbilityTrajectory(String empId, Integer rows) {
        return talentMapsService.queryAbilityTrajectory(getCustomerId(), empId, rows);
    }

    /**
     * 盘点报告 -异动轨迹
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryChangeTrajectory", method = RequestMethod.POST)
    public Map<String, Object> queryChangeTrajectory(String empId, Integer rows) {
        return talentMapsService.queryChangeTrajectory(getCustomerId(), empId, rows);
    }

    /**
     * 跳转到人才地图审核页面
     *
     * @return
     */
    @RequestMapping("/toTalentMapsAuditingView")
    public String toTalentMapsAuditing(HttpServletRequest request, String topId, Integer yearMonth, String organName) {
        request.setAttribute("topId", topId);
        String time = yearMonth / 100 + "年" + ((yearMonth % 100) < 7 ? "上半年" : "下半年");
        request.setAttribute("time", time);
        request.setAttribute("organName", Transcode.toStringHex(organName));
        List<TalentMapsDto> performanceList = talentMapsService.queryPerformanceInfo(getCustomerId());
        String performance = "";
        for (int i = 0; i < performanceList.size(); i++) {
            if (i < performanceList.size() - 1)
                performance += performanceList.get(i).getName() + ",";
            else
                performance += performanceList.get(i).getName();
        }
        List<TalentMapsDto> abilityList = talentMapsService.queryAbilityInfo(getCustomerId());
        String ability = "";
        for (int i = 0; i < abilityList.size(); i++) {
            if (i < abilityList.size() - 1)
                ability += abilityList.get(i).getName() + ",";
            else
                ability += abilityList.get(i).getName();
        }
        request.setAttribute("performance", performance);
        request.setAttribute("ability", ability);
        return "biz/competency/talentMapsAuditing";
    }

    /**
     * 跳转到人才地图发布页面
     *
     * @return
     */
    @RequestMapping("/toTalentMapsPublishView")
    public String toTalentMapsPublish(HttpServletRequest request, String topId, Integer yearMonth, String adjustment, String adjustmentId, String organName) {
        request.setAttribute("adjustment", Transcode.toStringHex(adjustment));
        request.setAttribute("topId", topId);
        request.setAttribute("adjustmentId", adjustmentId);
        String time = yearMonth / 100 + "年" + ((yearMonth % 100) < 7 ? "上半年" : "下半年");
        request.setAttribute("time", time);
        List<TalentMapsDto> performanceList = talentMapsService.queryPerformanceInfo(getCustomerId());
        String performance = "";
        for (int i = 0; i < performanceList.size(); i++) {
            if (i < performanceList.size() - 1)
                performance += performanceList.get(i).getName() + ",";
            else
                performance += performanceList.get(i).getName();
        }
        List<TalentMapsDto> abilityList = talentMapsService.queryAbilityInfo(getCustomerId());
        String ability = "";
        for (int i = 0; i < abilityList.size(); i++) {
            if (i < abilityList.size() - 1)
                ability += abilityList.get(i).getName() + ",";
            else
                ability += abilityList.get(i).getName();
        }
        request.setAttribute("performance", performance);
        request.setAttribute("ability", ability);
        request.setAttribute("organName", Transcode.toStringHex(organName));
        return "biz/competency/talentMapsPublish";
    }

    /**
     * 跳转到团队PK页面
     *
     * @return
     */
    @RequestMapping("/toTalentMapsForTeamPKView")
    public String toTalentMapsForTeamPK(HttpServletRequest request, String cycleDate) {
        cycleDate = StringUtils.isEmpty(cycleDate) ? "" : cycleDate;

        String customerId = getCustomerId();
        List<TalentMapsTeamInfoDto> list = talentMapsService.queryUserTeamInfo(getUserEmpId(), customerId);
        if (!list.isEmpty()) {
            request.getSession().setAttribute("userTeamInfo", list);
            return "redirect:/talentMaps/toTeamPKDetail?cycleDate=" + cycleDate;
        }

        List<OrganDto> topOrgans = getUserInfo().getOrganPermitTop();
        if (topOrgans.isEmpty()) return null;
        List<TalentMapsEmpCountDto> organs = getSecondOrgan();
        if (organs.isEmpty()) return null;

        List<TalentMapsEmpCountDto> rsDtos = talentMapsService.queryOrganMapsEmpCount(organs, cycleDate, customerId);
        request.setAttribute("topOrgan", topOrgans.get(0));
        request.setAttribute("organs", rsDtos);
        request.setAttribute("cycleDate", cycleDate);
        return "biz/competency/talentMapsForTeamPK";
    }

    /**
     * 跳转到团队PK详细页面
     *
     * @return
     */
    @RequestMapping("/toTeamPKDetail")
    public String toMaps4TeamPKDetails(HttpServletRequest request, String cycleDate) {
        List<OrganDto> topOrgans = getUserInfo().getOrganPermitTop();
        if (topOrgans.isEmpty()) return null;

        request.setAttribute("topOrgan", topOrgans.get(0));
        request.setAttribute("cycleDate", cycleDate);

        List<String> organIds = getOrganPermitId();
        if (!organIds.isEmpty()) {
            List<KVItemDto> kvItemDtos = talentMapsService.queryTalentMapsDateCycle(organIds, getCustomerId());
            request.setAttribute("kvItemDtos", kvItemDtos);
        }
        return "biz/competency/talentMapsTeamPKDetail";
    }

    /**
     * 执行团队PK的团队信息存储-团队选择界面（多个）
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doTeamInfoInsert", method = RequestMethod.POST)
    public ResultDto<String> doTeamInfoInsert(HttpServletRequest request, String teamInfoStr) {
        Gson gson = new Gson();
        List<TalentMapsTeamInfoDto> list = gson.fromJson(teamInfoStr, new TypeToken<List<TalentMapsTeamInfoDto>>() {
        }.getType());

        ResultDto<String> rsDto = new ResultDto<String>();
        if (!list.isEmpty()) {
            boolean pkView = false;
            String empId = getUserEmpId();
            String customerId = getCustomerId();
            for (TalentMapsTeamInfoDto dto : list) {
                pkView = dto.isPkView();
                dto.setEmpId(empId);
                dto.setCustomerId(customerId);
                if (pkView) dto.setTeamId(Identities.uuid2());
            }
            if (pkView) {
                talentMapsService.insertMapsTeams(list, empId, customerId);
            }
            request.getSession().setAttribute("userTeamInfo", list);
            rsDto.setType(true);
        }
        return rsDto;
    }

    /**
     * 执行团队PK的团队信息存储-地图页面（单个）
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doEditUserTeamInfo", method = RequestMethod.POST)
    public ResultDto<Boolean> doEditUserTeamInfo(HttpServletRequest request, String teamInfoStr) {
        ResultDto<Boolean> rsDto = new ResultDto<Boolean>();    //true为新增，false为修改

        Gson gson = new Gson();
        TalentMapsTeamInfoDto teamDto = gson.fromJson(teamInfoStr, new TypeToken<TalentMapsTeamInfoDto>() {
        }.getType());

        List<TalentMapsTeamInfoDto> list = (List<TalentMapsTeamInfoDto>) request.getSession().getAttribute("userTeamInfo");

        if (teamDto == null) return rsDto;   //转换不成功，格式有问题

        boolean bool = false;   //是否有相同原数据在历史里
        for (TalentMapsTeamInfoDto dto : list) {
            String teamId = dto.getTeamId();
            if (!StringUtils.isEmpty(teamId) && teamId.equals(teamDto.getTeamId())) {
                list.remove(dto);
                bool = true;
                break;
            }
        }
        list.add(teamDto);
        if (bool) { //true：修改，false：新增
            teamDto.setCustomerId(getCustomerId());
            talentMapsService.updateUserTeam(teamDto);
            rsDto.setT(Boolean.FALSE);
        } else {
            String teamId = Identities.uuid2();
            teamDto.setTeamId(teamId);
            teamDto.setCustomerId(getCustomerId());
            teamDto.setEmpId(getUserEmpId());
            talentMapsService.insertUserTeam(teamDto);
            rsDto.setT(Boolean.TRUE);
            rsDto.setMsg(teamId);
        }
        request.getSession().setAttribute("userTeamInfo", list);
        rsDto.setType(true);
        return rsDto;
    }

    /**
     * 检查用户团队数量
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserTeamNum", method = RequestMethod.GET)
    public ResultDto<Integer> getUserTeamNum(String teamId) {
        ResultDto<Integer> rsDto = new ResultDto<Integer>();
        int count = talentMapsService.findUserTeamNum(teamId,getUserEmpId(), getCustomerId());
        rsDto.setType(Boolean.TRUE);
        rsDto.setT(count);
        return rsDto;
    }

    /**
     * 执行移除用户团队PK的团队信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doDeleteUserTeam", method = RequestMethod.GET)
    public ResultDto<String> doDeleteUserTeam(HttpServletRequest request, String teamId) {
        ResultDto<String> rsDto = new ResultDto<String>();

        List<TalentMapsTeamInfoDto> list = (List<TalentMapsTeamInfoDto>) request.getSession().getAttribute("userTeamInfo");

        if (teamId == null) return rsDto;   //团队ID不存在，请求不正确

        boolean bool = false;   //是否有相同原数据在历史里
        for (TalentMapsTeamInfoDto dto : list) {
            if (teamId.equals(dto.getTeamId())) {
                list.remove(dto);
                bool = true;
                break;
            }
        }
        if (bool) request.getSession().setAttribute("userTeamInfo", list);   //有历史数据则重新保存到session中
        talentMapsService.deleteUserTeamInfo(getUserEmpId(), teamId, getCustomerId());
        rsDto.setType(true);
        return rsDto;
    }


    /**
     * 获取用户团队信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserTeamInfo", method = RequestMethod.POST)
    public List<TalentMapsTeamInfoDto> getUserTeamInfo(HttpServletRequest request) {
        List<TalentMapsTeamInfoDto> list = (List<TalentMapsTeamInfoDto>) request.getSession().getAttribute("userTeamInfo");
        if (list.isEmpty()) {
            list = talentMapsService.queryUserTeamInfo(getUserEmpId(), getCustomerId());
        }
        return list;
    }

    /**
     * 获取团队设置人员统计
     */
    @ResponseBody
    @RequestMapping(value = "/getTeamSettingEmpCount", method = RequestMethod.GET)
    public int getTeamPkTeamSettingEmpCount(TalentMapsTeamQueryDto dto) {
        dto.setCustomerId(getCustomerId());
        return talentMapsService.queryMapsSettingEmpCount(dto);
    }

    /**
     * 获取团队PK地图点人员信息
     */
    @ResponseBody
    @RequestMapping(value = "/getTeamEmpPoint", method = RequestMethod.POST)
    public List<TalentMapsPointDto> getTeamPkTeamSettingEmp(TalentMapsTeamQueryDto dto) {
        dto.setCustomerId(getCustomerId());
        return talentMapsService.queryTeamEmpPoint(dto);
    }

    /**
     * 获取团队地图时间周期
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentMapsDateCycle", method = RequestMethod.GET)
    public List<KVItemDto> getTalentMapsDateCycle() {
        List<String> organIds = getOrganPermitId();
        if (organIds.isEmpty()) return null;
        return talentMapsService.queryTalentMapsDateCycle(organIds, getCustomerId());
    }

    /**
     * 获取人才地图x轴和y轴基础信息
     */
    @ResponseBody
    @RequestMapping(value = "/getMapsBaseInfo", method = RequestMethod.GET)
    public Map<String, Object> getMapsBaseInfo() {
        return talentMapsService.queryMapsBaseInfo(getCustomerId());
    }

    /**
     * 获取人才地图x轴和y轴基础信息-能力/绩效/时间
     */
    @ResponseBody
    @RequestMapping(value = "/getMapsBaseInfoForTeamCP", method = RequestMethod.POST)
    public Map<String, Object> getMapsBaseInfoForTeamCP(String date, String viewType) {
        return talentMapsService.getMapsBaseInfoForTeamCP(getCustomerId(), date, viewType);
    }

    /**
     * 查询时间机构
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentMapsTime", method = RequestMethod.POST)
    public Map<String, Object> queryTalentMapsTime() {
        List<OrganDto> topOrgan = getUserInfo().getOrganPermit();
        return talentMapsService.queryTalentMapsTime(getCustomerId(), topOrgan);
    }

    /**
     * 查询待调整的人才地图
     */
    @ResponseBody
    @RequestMapping(value = "/queryMapsAuditing", method = RequestMethod.POST)
    public List<TalentMapsDto> queryMapsAdjustment() {
        List<OrganDto> topOrgan = getUserInfo().getOrganPermit();
        String empId = getUserInfo().getEmpId();
        String time = DateUtil.getDBNow();
        return talentMapsService.queryMapsAuditing(getCustomerId(), topOrgan, empId, time);
    }

    /**
     * 获取登录人第二级机构信息
     *
     * @return
     */
    private List<TalentMapsEmpCountDto> getSecondOrgan() {
        RbacUser user = getUserInfo();
        List<OrganDto> topOrgan = user.getOrganPermitTop();
        List<OrganDto> allOrgan = user.getOrganPermit();
        List<TalentMapsEmpCountDto> rsOrgan = CollectionKit.newList();
        for (OrganDto top : topOrgan) {
            for (OrganDto dto : allOrgan) {
                //假如机构的父级ID登录顶级机构ID,即为二级机构
                if (top.getOrganizationId().equals(dto.getOrganizationParentId())) {
                    rsOrgan.add(new TalentMapsEmpCountDto(dto.getOrganizationId(), dto.getOrganizationName()));
                }
            }
        }
        return rsOrgan;
    }

    /**
     * 获取待审核人才信息
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentPerformanceAbility", method = RequestMethod.POST)
    public PaginationDto<TalentMapsDto> getTalentPerformanceAbility(String keyName, String zName, String topId, Integer yearMonth, Integer page, Integer rows) {
        PaginationDto<TalentMapsDto> dto = new PaginationDto<TalentMapsDto>(page, rows);
        return talentMapsService.queryTalentPerformanceAbility(getCustomerId(), topId, yearMonth, keyName, zName, 0, dto);
    }

    /**
     * 获取待发布的人才信息
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentPublish", method = RequestMethod.POST)
    public PaginationDto<TalentMapsDto> getTalentPublish(String keyName, String topId, String adjustmentId, Integer yearMonth, Integer page, Integer rows) {
        PaginationDto<TalentMapsDto> dto = new PaginationDto<TalentMapsDto>(page, rows);
        return talentMapsService.queryTalentPublish(getCustomerId(), topId, adjustmentId, yearMonth, keyName, 1, dto);
    }

    /**
     * 查询已发布人才信息
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentPublishInfo", method = RequestMethod.POST)
    public PaginationDto<TalentMapsDto> getTalentPublishInfo(String topId, String releaseId, Integer yearMonth, Integer page, Integer rows) {
        PaginationDto<TalentMapsDto> dto = new PaginationDto<TalentMapsDto>(page, rows);
        return talentMapsService.queryTalentPublishInfo(getCustomerId(), topId, releaseId, yearMonth, 1, dto);
    }

    @ResponseBody
    @RequestMapping(value = "/getTalentAjustmentInfo", method = RequestMethod.POST)
    public List<TalentMapsDto> getTalentAjustmentInfo(String topId, String adjustmentId, Integer yearMonth) {
        List<TalentMapsDto> list = talentMapsService.queryTalentAdjustmentInfo(getCustomerId(), topId, adjustmentId, yearMonth, 1);
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/checkEmpInfo")
    public List<TalentMapsDto> checkEmpInfo(String keyName) {
        return talentMapsService.checkEmpInfo(getCustomerId(), keyName);
    }

    /**
     * 审核
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/updateAuditingInfo")
    public List<TalentMapsDto> updateAuditingInfo(String arr, String auditorId, String auditorName, String topId, String time, String organName) {
        ObjectMapper mapper = new ObjectMapper();
        List<TalentMapsDto> param = CollectionKit.newList();
        try {
            List<Map<String, String>> list = mapper.readValue(arr, List.class);
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = list.get(i);
                TalentMapsDto dto = new TalentMapsDto();
                dto.setEmpId(map.get("empId"));
                dto.setyDataAf(map.get("yData"));
                dto.setxDataAf(map.get("xData"));
                dto.setNote(map.get("note"));
                param.add(dto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String modifyTime = DateUtil.getDBNow();
        String fullPath = "ZRW";
        List<OrganDto> topOrgan = getUserInfo().getOrganPermit();
        for (OrganDto dto : topOrgan) {
            if (topId.equals(dto.getOrganizationId())) {
                fullPath = dto.getFullPath();
                break;
            }
        }
        String publishTime = "";
        if (time.indexOf("上半年") != -1)
            publishTime = time.substring(0, 4) + "06";
        else
            publishTime = time.substring(0, 4) + "12";
        String userId = getUserInfo().getEmpId();
        List<TalentMapsDto> result = talentMapsService.updateAuditingInfo(param, getCustomerId(), auditorId, userId, modifyTime, topId, fullPath, publishTime, topOrgan);
        //生成通知消息
        List<String> empList = CollectionKit.newList();
        empList.add(auditorId);
        String content = time + organName + "人才地图需要审核";
        String url = "talentMaps/toTalentMapsView";
        messageService.addPtpMessage(getCustomerId(), getUserInfo().getEmpId(), content, empList, url);

        return result;
    }

    /**
     * 发布
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/pulishMapsManagement")
    public List<TalentMapsDto> pulishMapsManagement(String arr, String topId, String publishTime) {
        ObjectMapper mapper = new ObjectMapper();
        List<TalentMapsDto> listDto = CollectionKit.newList();
        try {
            List<Map<String, String>> list = mapper.readValue(arr, List.class);
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = list.get(i);
                TalentMapsDto dto = new TalentMapsDto();
                dto.setEmpId(map.get("empId"));
                dto.setyDataAf(map.get("yDataAf"));
                dto.setxDataAf(map.get("xDataAf"));
                dto.setNote(map.get("note"));
                dto.setText(map.get("text"));
                listDto.add(dto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String releaseTime = DateUtil.getDBNow();
        String empId = getUserInfo().getEmpId();
        return talentMapsService.pulishMapsManagement(listDto, getCustomerId(), empId, releaseTime, topId, publishTime);
    }

    /**
     * 获取z轴纬度
     */
    @ResponseBody
    @RequestMapping(value = "/getZInfo")
    public List<TalentMapsDto> getZInfo() {
        return talentMapsService.queryZInfo(getCustomerId());
    }

    /**
     * 获取z轴信息
     */
    @ResponseBody
    @RequestMapping(value = "/getZInfoCount")
    public int getZInfoCount(String topId, Integer flag, String zName, Integer yearMonth) {
        return talentMapsService.queryTalentPerformanceAbilityCount(getCustomerId(), topId, flag, zName, yearMonth);
    }

    /**
     * 查询历史审核列表
     */
    @ResponseBody
    @RequestMapping(value = "/getHistoricalAuditing", method = RequestMethod.POST)
    public List<TalentMapsDto> getHistoricalAuditing() {
        List<OrganDto> topOrgan = getUserInfo().getOrganPermit();
        String empId = getUserInfo().getEmpId();
        return talentMapsService.queryHistoricalAuditing(getCustomerId(), topOrgan, empId);
    }

    /**
     * 查询历史
     */
    @ResponseBody
    @RequestMapping(value = "/queryMapManage", method = RequestMethod.POST)
    public TalentMapsDto queryMapManage(String id, String topId, Integer yearMonth) {
        return talentMapsService.queryMapManage(getCustomerId(), id, topId, yearMonth);
    }

    /**
     * 查询地图坐标
     */
    @ResponseBody
    @RequestMapping(value = "/queryMapsPreview", method = RequestMethod.POST)
    List<TalentMapsDto> queryMapsPreview(String flag, String topId, Integer yearMonth) {
        return talentMapsService.queryMapsPreview(getCustomerId(), topId, flag, yearMonth);
    }

    /**
     * 地图审核权限
     */
    @ResponseBody
    @RequestMapping(value = "/checkAuditingPermission", method = RequestMethod.POST)
    public int checkAuditingPermission(String topId) {
        List<OrganDto> topOrgan = getUserInfo().getOrganPermit();
        OrganDto dto = organService.findOrganById(topId, getCustomerId());
        for (OrganDto organ : topOrgan) {
            if (dto.getFullPath().indexOf(organ.getFullPath()) != -1) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * 地图审核权限
     */
    @ResponseBody
    @RequestMapping(value = "/checkPublishPermission", method = RequestMethod.POST)
    public int checkPublishPermission(String topId, String time) {
        String empId = getUserInfo().getEmpId();
        return talentMapsService.checkPublishPermission(getCustomerId(), topId, empId, time);
    }
}

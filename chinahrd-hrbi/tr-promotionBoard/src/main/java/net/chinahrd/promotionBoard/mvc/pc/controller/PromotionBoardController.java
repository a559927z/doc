package net.chinahrd.promotionBoard.mvc.pc.controller;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.api.TalentProfileApi;
import net.chinahrd.core.api.ApiManagerCenter;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionBoardDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionDateDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionForewarningDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionPayListDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionPaySelectDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionSpeedDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionStatusDto;
import net.chinahrd.entity.dto.pc.promotionBoard.PromotionTrackDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.common.CommonService;
import net.chinahrd.promotionBoard.mvc.pc.dao.PromotionBoardDao;
import net.chinahrd.promotionBoard.mvc.pc.service.PromotionBoardService;
import net.chinahrd.utils.CacheHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 晋级看板Controller
 */
@Controller
@RequestMapping("/promotionBoard")
public class PromotionBoardController extends BaseController {
	@Autowired
	private CommonService commonService;

    @Autowired
    private PromotionBoardService promotionBoardService;

    @RequestMapping(value = "/toPromotionBoardView")
    public String toPromotionBoardView(HttpServletRequest request) {
        // 改用缓存提取funId by jxzhang on 2016-11-10
        String functionId = CacheHelper.getFunctionId("promotionBoard/toPromotionBoardView");
        if(null !=functionId){request.setAttribute("quotaId", functionId); }
        
        PromotionDateDto dateDto = promotionBoardService.getDate(getCustomerId());
        if(dateDto !=null) {
            SimpleDateFormat f =  new SimpleDateFormat("yyyy-MM-dd");
            request.setAttribute("maxDate", f.format(dateDto.getMaxDate()));
            request.setAttribute("minDate", f.format(dateDto.getMinDate()));
            request.setAttribute("conditionProp", dateDto.getConditionProp());
        }
        return "biz/drivingforce/promotionBoard";
    }

    //符合条件(包括已申请与未申请的)，为了不改原来的方法，所以状态就用-1表示全部
    @ResponseBody
    @RequestMapping(value = "/getEligibilityApplication", method = RequestMethod.GET)
    public PromotionForewarningDto getEligibilityApplication(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return promotionBoardService.getEligibilityApplication(getCustomerId(), organId, -1);
    }

    //符合条件已申请 列表
    @ResponseBody
    @RequestMapping(value = "/getEligibilityApplicationList", method = RequestMethod.POST)
    public PaginationDto<PromotionBoardDto> getEligibilityApplicationList(String organId, Integer page, Integer rows) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return promotionBoardService.getEligibilityApplicationList(getCustomerId(), organId, page, rows, 1);
    }

    //符合条件未申请
    @ResponseBody
    @RequestMapping(value = "/getEligibilityNotApplication", method = RequestMethod.GET)
    public PromotionForewarningDto getEligibilityNotApplication(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return promotionBoardService.getEligibilityApplication(getCustomerId(), organId, 0);
    }
    //符合条件未申请 列表
    @ResponseBody
    @RequestMapping(value = "/getEligibilityNotApplicationList", method = RequestMethod.POST)
    public PaginationDto<PromotionBoardDto> getEligibilityNotApplicationList(String organId, Integer page, Integer rows) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return promotionBoardService.getEligibilityApplicationList(getCustomerId(), organId, page, rows, 0);
    }

    //部分符合条件
    @ResponseBody
    @RequestMapping(value = "/getSomeEligibility", method = RequestMethod.GET)
    public PromotionForewarningDto getSomeEligibility(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return promotionBoardService.getSomeEligibility(getCustomerId(), organId);
    }

    //全部符合条件 列表
    @ResponseBody
    @RequestMapping(value = "/getAllEligibilityList", method = RequestMethod.POST)
    public PaginationDto<PromotionBoardDto> getAllEligibilityList(String organId, Integer page, Integer rows) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return promotionBoardService.getEligibilityApplicationList(getCustomerId(), organId, page, rows, -1);
    }

    private List<String> strToList(String populationIds){
        List<String> pIds=new LinkedList<>();
        if(null!=populationIds && !"".equals(populationIds)){
            String[] arr = populationIds.split(",");
            for (String s:arr){
                pIds.add(s);
            }
        }
        return pIds;
    }

    //趋势分析
    @ResponseBody
    @RequestMapping(value = "/getTrendAnalysis", method = RequestMethod.GET)
    public List<PromotionSpeedDto> getTrendAnalysis(String organId, String beginDate, String endDate, String populationIds) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        return promotionBoardService.getTrendAnalysis(getCustomerId(), organId, beginDate, endDate, strToList(populationIds));
    }

    //下级组织分析
    @ResponseBody
    @RequestMapping(value = "/getOrgAnalysis", method = RequestMethod.GET)
    public Map<String, Object> getOrgAnalysis(String organId, String beginDate, String endDate, String populationIds) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        return promotionBoardService.getOrgAnalysis(getCustomerId(), organId, beginDate, endDate, strToList(populationIds));
    }

    //下级组织分析,个人晋级速度
    @ResponseBody
    @RequestMapping(value = "/getOrgAnalysisPerJinsuList", method = RequestMethod.POST)
    public PaginationDto<PromotionTrackDto> getOrgAnalysisPerJinsuList(String organId, Integer page, Integer rows) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return promotionBoardService.getOrgAnalysisPerJinsuList(getCustomerId(), organId, page, rows);
    }
    
    //序列统计
    @ResponseBody
    @RequestMapping(value = "/getSequenceAnalysis", method = RequestMethod.GET)
    public Map<String, Object> getSequenceAnalysis(String organId, String beginDate, String endDate, String populationIds) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        return promotionBoardService.getSequenceAnalysis(getCustomerId(), organId, beginDate, endDate, strToList(populationIds));
    }

    //关键人才
    @ResponseBody
    @RequestMapping(value = "/getKeyTalentAnalysis", method = RequestMethod.GET)
    public Map<String, Object> getKeyTalentAnalysis(String organId, String beginDate, String endDate, String populationIds) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        return promotionBoardService.getKeyTalentAnalysis(getCustomerId(), organId, beginDate, endDate, strToList(populationIds));
    }

    //绩效
    @ResponseBody
    @RequestMapping(value = "/getPerformanceAnalysis", method = RequestMethod.GET)
    public Map<String, Object> getPerformanceAnalysis(String organId, String beginDate, String endDate, String populationIds) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        return promotionBoardService.getPerformanceAnalysis(getCustomerId(), organId, beginDate, endDate, strToList(populationIds));
    }


    //晋级轨迹(图)
    @ResponseBody
    @RequestMapping(value = "/getTrackChart", method = RequestMethod.GET)
    public List<PromotionTrackDto> getTrackChart(String organId, String empIds) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        List<String> list = strToList(empIds);
        if(list.size()==0){
            return null;
        }

        return promotionBoardService.getTrackChart(getCustomerId(), organId, strToList(empIds));
    }

    //晋级轨迹(搜索下拉)
    @ResponseBody
    @RequestMapping(value = "/getTrackSelect", method = RequestMethod.POST)
    public Map<String, Object> getTrackSelect(String organId, String key, Integer page, Integer rows) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        if(StringUtils.isEmpty(key)){
            key = null;
        }

        return promotionBoardService.getTrackSelect(getCustomerId(), organId, key, page, rows);
    }

    //晋级轨迹(列表)
    @ResponseBody
    @RequestMapping(value = "/getTrackList", method = RequestMethod.POST)
    public PaginationDto<PromotionTrackDto> getTrackList(String organId, String empId, Integer page, Integer rows) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        if(StringUtils.isEmpty(empId)){
            empId = null;
        }

        return promotionBoardService.getTrackList(getCustomerId(), organId, empId, page, rows);
    }

    //人员晋级状态
    @ResponseBody
    @RequestMapping(value = "/getStatusList", method = RequestMethod.POST)
    public PaginationDto<PromotionStatusDto> getStatusList(String organId, String empId,
                                                           String rankName, String rankNameNext, Integer condition,
                                                           double conditionBegin, double conditionEnd,
                                                           Integer page, Integer rows,boolean isAll) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        if(StringUtils.isEmpty(empId)){
            empId = null;
        }
        if(StringUtils.isEmpty(rankName)){
            rankName = null;
        }
        if(StringUtils.isEmpty(rankNameNext)){
            rankNameNext = null;
        }
        if(condition <= 0){
            conditionBegin = conditionEnd = 0;
        }
        return promotionBoardService.getStatusList(getCustomerId(), organId, empId, rankName, rankNameNext, condition, conditionBegin, conditionEnd, page, rows, isAll);
    }

    //晋级薪酬模拟器（筛选显示的职级）
    @ResponseBody
    @RequestMapping(value = "/getPromotionSelectRank", method = RequestMethod.GET)
    public List<PromotionPaySelectDto> getPromotionSelectRank(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return promotionBoardService.getPromotionSelectRank(getCustomerId(), organId);
    }

    //晋级薪酬模拟器（列表）
    @ResponseBody
    @RequestMapping(value = "/getPromotionRankList", method = RequestMethod.GET)
    public List<PromotionPayListDto> getPromotionRankList(String organId, String ranks) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        if (StringUtils.isEmpty(ranks)){
            return null;
        }

        return promotionBoardService.getPromotionRankList(getCustomerId(), organId, strToList(ranks));
    }

    //晋级薪酬模拟器(添加下拉)
    @ResponseBody
    @RequestMapping(value = "/getPromotionAddPersonList", method = RequestMethod.POST)
    public Map<String, Object> getPromotionAddPersonList(String organId, String rank, Integer page, Integer rows,String key) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        if (StringUtils.isEmpty(rank)){
            return null;
        }

        return promotionBoardService.getPromotionAddPersonList(getCustomerId(), organId, rank, page, rows,key);
    }
    
    /**
     * 跳转到员工详情页面
     *
     * @return
     */
    @RequestMapping(value = "/toTalentDetailView")
    public String toTalentDetailView(HttpServletRequest request, @RequestParam("empId") String empId, String anchor) {
    	  String customerId = getCustomerId();
    	TalentProfileApi api=ApiManagerCenter.getApiDoc(TalentProfileApi.class);
    	 EmpDetailDto empDto=null;
    	if(null!=api){
    		empDto=api.findEmpDetail(empId, customerId);
         }
        String performanceStr = commonService.getPerformanceStr(customerId);
        request.setAttribute("empDto", empDto);
        request.setAttribute("performanceStr", performanceStr);
        if (null == anchor) {
            anchor = "";
        }
        request.setAttribute("anchor", anchor);
        return "biz/employee/talentDetail";
    }
 }

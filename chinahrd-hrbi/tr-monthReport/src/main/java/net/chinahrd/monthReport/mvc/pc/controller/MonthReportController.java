package net.chinahrd.monthReport.mvc.pc.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import net.chinahrd.api.AccordDismissApi;
import net.chinahrd.api.EmployeePerformanceApi;
import net.chinahrd.api.LaborEfficiencyApi;
import net.chinahrd.api.ManpowerCostApi;
import net.chinahrd.api.PerBenefitApi;
import net.chinahrd.api.RecruitBoardApi;
import net.chinahrd.api.SalaryBoardApi;
import net.chinahrd.api.TalentStructureApi;
import net.chinahrd.api.TrainBoardApi;
import net.chinahrd.core.api.ApiManagerCenter;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.performance.PerfChangeCountDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRecordDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRiskDto;
import net.chinahrd.entity.dto.pc.benefit.PerBenefitDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PreChangeStatusDto;
import net.chinahrd.entity.dto.pc.manage.HomeConfigDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerCompareDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerDto;
import net.chinahrd.entity.dto.pc.manpowerCost.ManpowerItemDto;
import net.chinahrd.entity.dto.pc.monthReport.MonthReportChangesDetailDto;
import net.chinahrd.entity.dto.pc.monthReport.MonthReportChangesDto;
import net.chinahrd.entity.dto.pc.monthReport.MonthReportConfigDto;
import net.chinahrd.entity.dto.pc.monthReport.MonthReportDimissionEmpDto;
import net.chinahrd.entity.dto.pc.monthReport.MonthReportEmpCountDto;
import net.chinahrd.entity.dto.pc.monthReport.MonthReportManpowerCostDto;
import net.chinahrd.entity.dto.pc.monthReport.MonthReportPromotionDto;
import net.chinahrd.entity.dto.pc.monthReport.MonthReportSalesCountDto;
import net.chinahrd.entity.dto.pc.monthReport.MonthReportSavaDto;
import net.chinahrd.entity.dto.pc.monthReport.MonthReportShareDto;
import net.chinahrd.entity.dto.pc.monthReport.MonthReportTrainGeneralDto;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitChannelResultDto;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitPositionMeetRateDto;
import net.chinahrd.entity.dto.pc.salaryBoard.SalaryBoardDto;
import net.chinahrd.entity.dto.pc.talentStructure.TalentStructureDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainTypeDto;
import net.chinahrd.monthReport.mvc.pc.service.MonthReportService;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;
import net.chinahrd.utils.WebUtils;
import net.chinahrd.utils.phantomjs.PhantomJSExecutor2;
import net.chinahrd.utils.phantomjs.PrintArguments2;

/**
 * 月报
 * Created by wqcai on 16/08/16 021.
 */
@Controller
@RequestMapping(value = "/monthReport")
public class MonthReportController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(MonthReportController.class);

    private final String dirName = "monthReport";
    @Autowired
    private MonthReportService monthReportService;
    @Autowired
    private PhantomJSExecutor2 exe;
    
    /***
     * 跳转到月报主页
     * @return
     */
    @RequestMapping(value = "/toMonthReportView")
    public String toMonthReportView(HttpServletRequest request) {
        // 改用缓存提取funId by jxzhang on 2016-11-10
        String functionId = CacheHelper.getFunctionId("monthReport/toMonthReportView");
        if (StringUtils.isNotBlank(functionId)) {
            request.setAttribute("quotaId", functionId);
        }

        //月报是月度总结，所以只能看上一个月的数据
        Integer yearMonth = DateUtil.getPreYearMonth(Integer.valueOf(DateUtil.getDBTime("yyyyMM")), 1);
        request.setAttribute("yearMonth", yearMonth.toString());

        StringBuilder urlStr = new StringBuilder();
        urlStr.append(exe.getPhantomJSReference().getRemotePath()).append(File.separator).append(dirName).append(File.separator);

        request.setAttribute("remotePath", urlStr.toString());
        return "biz/other/monthReport";
    }

    /**
     * 获取用户配置信息
     */
    @ResponseBody
    @RequestMapping(value = "/getUserConfig", method = RequestMethod.POST)
    public List<HomeConfigDto> getUserConfig(String functionId) {
        if (StringUtils.isEmpty(functionId)) return null;
        return monthReportService.queryUserConfig(functionId, getUserEmpId(), getCustomerId());
    }
    
    /**
     * 获取用户配置信息
     */
    @ResponseBody
    @RequestMapping(value = "/getMonthReportConfig", method = RequestMethod.POST)
    public List<MonthReportConfigDto> queryMonthReportConfig() {
    	return monthReportService.queryMonthReportConfig(getCustomerId(), getUserEmpId());
    }
    
    /**
     * 更新用户配置信息
     */
    @ResponseBody
    @RequestMapping(value = "/updateMonthReportConfig", method = RequestMethod.POST)
    public ResultDto<String> updateMonthReportConfig(String configData) {
    	if (StringUtils.isEmpty(configData)) return null;
    	ResultDto<String> rsDto = new ResultDto<String>();
    	List<MonthReportConfigDto> list = JSON.parseArray(configData, MonthReportConfigDto.class);  
    	try{
    		monthReportService.updateMonthReportConfig(list);
    		rsDto.setType(true);
    	} catch (Exception e) {
    		rsDto.setType(false);
    	}
    	return rsDto;
    }

    /**
     * 人员流失异动（按组织）
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getChangesToSubOrgan", method = RequestMethod.POST)
    public List<MonthReportChangesDto> getChangesToSubOrgan(String organId, String yearMonth) {
        if (StringUtils.isEmpty(organId) || StringUtils.isEmpty(yearMonth)) return null;
        return monthReportService.queryChangesToSubOrgan(organId, yearMonth, getCustomerId());
    }

    /**
     * 人员流失异动（按职级）
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getChangesToAbility", method = RequestMethod.POST)
    public List<MonthReportChangesDto> getChangesToAbility(String organId, String yearMonth) {
        if (StringUtils.isEmpty(organId) || StringUtils.isEmpty(yearMonth)) return null;
        return monthReportService.queryChangesToAbility(organId, yearMonth, getCustomerId());
    }

    /**
     * 人员流失异动详情
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getChangesDetails", method = RequestMethod.POST)
    public PaginationDto<MonthReportChangesDetailDto> getChangesDetails(String itemId, String organId, String pos, Integer yearMonth, Integer page, Integer rows) {
        PaginationDto<MonthReportChangesDetailDto> dto = new PaginationDto<MonthReportChangesDetailDto>(page, rows);
        if (StringUtils.isEmpty(itemId)) {
            itemId = null;
        }
        return monthReportService.queryChangesDetails(itemId, organId, pos, yearMonth, getCustomerId(), dto);
    }


    /**
     * 关键人才离职情况
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getKeyTalentdimissionEmps", method = RequestMethod.POST)
    public PaginationDto<DismissRiskDto> getKeyTalentdimissionEmps(String organId, Integer yearMonth, Integer page, Integer rows) {
        PaginationDto<DismissRiskDto> dto = new PaginationDto<DismissRiskDto>(page, rows);
        return monthReportService.queryDimissionEmpsList(organId, yearMonth, getCustomerId(), dto);
    }

    /**
     * 获取培训概况分析
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTrainGeneral", method = RequestMethod.POST)
    public List<MonthReportTrainGeneralDto> getTrainGeneral(String organId, Integer year) {
        if (StringUtils.isEmpty(organId) || year == null) return null;
        return monthReportService.queryTrainGeneral(organId, year, getCustomerId());
    }
    
    /**
     * 获取晋级分析概况
     * @param organId
     * @param yearMonth
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPromotionGeneral", method = RequestMethod.POST)
    public List<MonthReportPromotionDto> queryPromotionGeneral(String organId, Integer yearMonth) {
    	return monthReportService.queryPromotionGeneral(getCustomerId(), organId, yearMonth);
    }

    /**
     * 获取在职人员分布（职级+序列）统计
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getInJobEmpCount", method = RequestMethod.POST)
    public List<MonthReportEmpCountDto> getInJobEmpCount(String organId) {
        if (StringUtils.isEmpty(organId)) return null;
        return monthReportService.queryInJobsEmpCount(organId, getCustomerId());
    }

    /**
     * 获取销售情况
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalesCount", method = RequestMethod.POST)
    public List<MonthReportSalesCountDto> getSalesCount(Integer type, String organId, Integer yearMonth) {
        if (StringUtils.isEmpty(organId) || yearMonth == null) return null;
        return monthReportService.querySalesCount(type, organId, yearMonth, getCustomerId());
    }

    /**
     * 获取销售情况
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSalesCountByMonth", method = RequestMethod.POST)
    public List<MonthReportSalesCountDto> getSalesCountByMonth(Integer type, String itemId, String organId, Integer yearMonth) {
        if (StringUtils.isEmpty(organId) || yearMonth == null) return null;
        //TODO 页面展示12个月
        Integer beginTime = DateUtil.getPreYearMonth(yearMonth, 6);
        return monthReportService.querySalesCountByMonth(type, itemId, organId, beginTime, yearMonth, getCustomerId());
    }

    /**
     * 打印（导出）
     */
    @ResponseBody
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportTalentMap(HttpServletRequest request, HttpServletResponse response, String organId, String yearMonth, String quotaId) throws IOException {
    	String organName = new String(request.getParameter("organName").getBytes("iso-8859-1"), "utf-8");
    	if (StringUtils.isEmpty(organId) || StringUtils.isEmpty(organName) || null == yearMonth || StringUtils.isEmpty(quotaId))
            return;
        
        PrintArguments2 args = new PrintArguments2();

        StringBuilder urlStr = new StringBuilder();
        urlStr.append(getBasePath(request)).append("/monthReport/display?yearMonth=").append(yearMonth)
                .append("&organId=").append(organId).append("&organName=").append(URLEncoder.encode(URLEncoder.encode(organName, "UTF-8"), "UTF-8"))
                .append("&quotaId=").append(quotaId).append("&customerId=").append(getCustomerId()).append("&empId=").append(getUserEmpId());

        args.setUrl(urlStr.toString());
        args.setPdfSize(PrintArguments2.PdfSize.A4);
        args.setTimeout(1000);
        byte[] bytes = exe.rasterizeAsBytes(args);
        
//        String path = exe.getPhantomJSReference().getOutputDir() + "testArgs.pdf";
        String path = WebUtils.getAbsolutePath() + dirName + File.separator + "temp" + File.separator + "testArgs.pdf";
        FileUtils.writeByteArrayToFile(new File(path), bytes);
        
        
        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        String filename=new String((yearMonth+"月报").getBytes(),"iso8859-1");
//        System.out.println(filename);
        response.setHeader("Content-Disposition", "attachment;fileName=" + filename+".pdf");
        OutputStream os = response.getOutputStream();
        os.write(bytes, 0, bytes.length);
    }

    /**
     * 收藏
     */
    @ResponseBody
    @RequestMapping(value = "/favorites", method = RequestMethod.POST)
    public ResultDto<String> favoritesTalentMap(HttpServletRequest request, String organId, String organName, Integer yearMonth, String quotaId) throws UnsupportedEncodingException {
        ResultDto<String> rsDto = new ResultDto<>();
        if (StringUtils.isEmpty(organId) || StringUtils.isEmpty(organName) || null == yearMonth || StringUtils.isEmpty(quotaId))
            return rsDto;

        PrintArguments2 args = new PrintArguments2();

        StringBuilder urlStr = new StringBuilder();
        urlStr.append(getBasePath(request)).append("/monthReport/display?yearMonth=").append(yearMonth.toString())
                .append("&organId=").append(organId).append("&quotaId=").append(quotaId)
                .append("&organName=").append(URLEncoder.encode(URLEncoder.encode(organName, "UTF-8"), "UTF-8"))
                .append("&customerId=").append(getCustomerId()).append("&empId=").append(getUserEmpId());

        args.setUrl(urlStr.toString());
        args.setPdfSize(PrintArguments2.PdfSize.A4);
        args.setTimeout(1000);
        byte[] bytes = exe.rasterizeAsBytes(args);

        String savaId = Identities.uuid2();
        String path = savaId + ".pdf";

        StringBuilder filePath = new StringBuilder();
//        filePath.append(exe.getPhantomJSReference().getOutputDir()).append(File.separator).append(dirName).append(File.separator).append(path);
        filePath.append(WebUtils.getAbsolutePath()).append(dirName).append(File.separator).append(path);
        try {
            FileUtils.writeByteArrayToFile(new File(filePath.toString()), bytes);
        } catch (IOException e) {
            logger.debug(e.getMessage());
            return rsDto;
        }

        MonthReportSavaDto savaDto = new MonthReportSavaDto();
        savaDto.setSavaId(savaId);
        savaDto.setOrganId(organId);
        savaDto.setEmpId(getUserEmpId());
        savaDto.setCustomerId(getCustomerId());
        savaDto.setYearMonth(yearMonth);
        savaDto.setFileName(organName);
        savaDto.setPath(path.toString());
        savaDto.setCreateTime(DateUtil.getTimestamp());
        rsDto.setT(savaId);
        rsDto.setType(Boolean.TRUE);

        monthReportService.insertFavorites(savaDto);

        return rsDto;
    }


    /**
     * 获取用户指定年月及机构收藏信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkEmpFavoritesExist", method = RequestMethod.GET)
    public ResultDto<String> checkEmpFavoritesExist(String organId, Integer yearMonth) {
        ResultDto<String> rsDto = new ResultDto<String>();
        MonthReportSavaDto savaDto = monthReportService.findFavoritesInfo(getUserEmpId(), organId, yearMonth, getCustomerId());
        if (savaDto != null) {
            rsDto.setType(Boolean.TRUE);
            rsDto.setT(savaDto.getSavaId());
        }
        return rsDto;
    }

    /**
     * 获取用户指定年月及机构收藏信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doUnFavorites", method = RequestMethod.POST)
    public ResultDto<String> doUnFavorites(String favoritesId) {
        ResultDto<String> rsDto = new ResultDto<String>();
        StringBuilder filePath = new StringBuilder();
        filePath.append(WebUtils.getAbsolutePath()).append(dirName).append(File.separator).append(favoritesId).append(".pdf");
        monthReportService.deleteFavorites(favoritesId, getCustomerId(), filePath.toString());
        rsDto.setType(Boolean.TRUE);
        return rsDto;
    }

    /**
     * 获取用户收藏信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEmpFavorites", method = RequestMethod.GET)
    public MultiValueMap<Integer, MonthReportSavaDto> getEmpFavorites() {
        return monthReportService.queryFavorites(getUserEmpId(), getCustomerId());
    }
    
    @ResponseBody
    @RequestMapping(value = "/getEmpInfo", method = RequestMethod.POST)
    public List<MonthReportShareDto> checkEmpInfo(String keyName) {
        return monthReportService.checkEmpInfo(getCustomerId(), keyName);
    }
    
    /**
     * 保存分享信息
     * @param organId
     * @param yearMonth
     * @param check
     * @param toEmpId
     * @param shareContent
     * @param note
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addShareMonthReport", method = RequestMethod.POST)
    public ResultDto<String> addShareMonthReport(String organId, String organName, String quotaId, String yearMonth, Boolean check, String toEmpId, String shareContent, String note) throws UnsupportedEncodingException  {
    	if (StringUtils.isEmpty(organId) || StringUtils.isEmpty(organName) || null == yearMonth || StringUtils.isEmpty(quotaId) || StringUtils.isEmpty(toEmpId))
    		return null;
    	PrintArguments2 args = new PrintArguments2();

        StringBuilder urlStr = new StringBuilder();
        urlStr.append(getBasePath(request)).append("/monthReport/display?yearMonth=").append(yearMonth.toString())
                .append("&organId=").append(organId).append("&quotaId=").append(quotaId)
                .append("&organName=").append(URLEncoder.encode(URLEncoder.encode(organName, "UTF-8"), "UTF-8"))
                .append("&customerId=").append(getCustomerId()).append("&empId=").append(getUserEmpId());

        args.setUrl(urlStr.toString());
        args.setPdfSize(PrintArguments2.PdfSize.A4);
        args.setTimeout(1000);
        byte[] bytes = exe.rasterizeAsBytes(args);

        String savaId = Identities.uuid2();
        String path = savaId + ".pdf";

        StringBuilder filePath = new StringBuilder();
        filePath.append(WebUtils.getAbsolutePath()).append(dirName).append(File.separator).append(path);
        ResultDto<String> rsDto = new ResultDto<String>();
        try {
            FileUtils.writeByteArrayToFile(new File(filePath.toString()), bytes);
        } catch (IOException e) {
            logger.debug(e.getMessage());
            rsDto.setType(false);
            rsDto.setMsg("分享失败！");
            return rsDto;
        }
    	
    	Map<String, Object> map = CollectionKit.newMap();
    	map.put("shareId", Identities.uuid2());
    	map.put("customerId", getCustomerId());
    	map.put("shareEmpId", getUserEmpId());
    	map.put("toEmpId", toEmpId);
    	map.put("organId", organId);
    	map.put("createTime", DateUtil.getDBNow());
    	map.put("reportContent", shareContent);
    	map.put("sendMail", check == true ? 1 : 0);
    	map.put("path", path);
    	map.put("note", note);
    	map.put("yearMonth", Integer.parseInt(yearMonth));
    	try{
    		String rs = monthReportService.addMonthReportShare(map);
    		if("repeat".equals(rs)) {
    			rsDto.setType(false);
                rsDto.setMsg("已经分享过了，请不要重复操作！");
                return rsDto;
    		}
    	} catch(Exception e) {
    		rsDto.setType(false);
            rsDto.setMsg("分享失败！");
            return rsDto;
    	}
    	rsDto.setType(true);
    	return rsDto;
    }

    /**
     * 获取分享给我的
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEmpShare", method = RequestMethod.GET)
    public MultiValueMap<Integer, MonthReportShareDto> getEmpShare() {
        return monthReportService.queryShare(getUserEmpId(), getCustomerId());
    }

    /**
     * 删除分享
     * @param shareId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doCancelShare", method = RequestMethod.POST)
    public ResultDto<String> deleteMonthReportShare(String shareId) {
    	ResultDto<String> rsDto = new ResultDto<String>();
    	try{
    		monthReportService.deleteMonthReportShare(getCustomerId(), shareId);
    		rsDto.setType(true);
    	} catch(Exception e) {
    		rsDto.setType(false);
    	}
    	return rsDto;
    }

    @RequestMapping("/display")
    public String renderMonthRepost(String organId, String organName, String yearMonth, String quotaId, String customerId, String empId) throws UnsupportedEncodingException {
        request.setAttribute("organId", organId);
        request.setAttribute("organName", URLDecoder.decode(organName, "UTF-8"));
        request.setAttribute("quotaId", quotaId);
        request.setAttribute("yearMonth", yearMonth);
        
        //人员结构分析
        TalentStructureApi talentStructureApi = ApiManagerCenter.getApiDoc(TalentStructureApi.class);
        TalentStructureDto talentStructureDto = talentStructureApi.getBudgetAnalyse(organId, customerId);
        String talentStructureDtoToStr = JSON.toJSONString(talentStructureDto);
        request.setAttribute("talentStructureDto", talentStructureDtoToStr);
        Map<String, Object> getTalentStuctureByMonth = talentStructureApi.getTalentStuctureByMonth(organId, customerId);
        String getTalentStuctureByMonthStr = JSON.toJSONString(getTalentStuctureByMonth);
        request.setAttribute("getTalentStuctureByMonth", getTalentStuctureByMonthStr);
        
        //人员流动分析
        List<MonthReportChangesDto> changesByOrgan = monthReportService.queryChangesToSubOrgan(organId, yearMonth, customerId);
        String changesByOrganStr = JSON.toJSONString(changesByOrgan);
        request.setAttribute("changesByOrgan", changesByOrganStr);
        List<MonthReportChangesDto> changesByAbility = monthReportService.queryChangesToAbility(organId, yearMonth, customerId);
        String changesByAbilityStr = JSON.toJSONString(changesByAbility);
        request.setAttribute("changesByAbility", changesByAbilityStr);
        List<DismissRiskDto> getKeyTalentdimissionEmps = monthReportService.queryDimissionEmpsListNoPage(organId, Integer.parseInt(yearMonth), customerId);
        String getKeyTalentdimissionEmpsStr = JSON.toJSONString(getKeyTalentdimissionEmps);
        request.setAttribute("getKeyTalentdimissionEmps", getKeyTalentdimissionEmpsStr);
        AccordDismissApi accordDismissApi = ApiManagerCenter.getApiDoc(AccordDismissApi.class);
        List<DismissRecordDto> getDismissRecord = accordDismissApi.getDismissRecord(customerId, organId, null, yearMonth);
        String getDismissRecordStr = JSON.toJSONString(getDismissRecord);
        request.setAttribute("getDismissRecord", getDismissRecordStr);
        List<MonthReportDimissionEmpDto> getAccordDismissAnalysis = monthReportService.accordDismissAnalysis(customerId, organId, yearMonth);
        String getAccordDismissAnalysisStr = JSON.toJSONString(getAccordDismissAnalysis);
        request.setAttribute("getAccordDismissAnalysis", getAccordDismissAnalysisStr);
        Map<String, Object> accordDismissByYearMonth = monthReportService.accordDismissByYearMonth(customerId, organId, yearMonth);
        String accordDismissByYearMonthStr = JSON.toJSONString(accordDismissByYearMonth);
        request.setAttribute("accordDismissByYearMonth", accordDismissByYearMonthStr);
        Double accordDismissInYear = monthReportService.accordDismissInYear(customerId, organId, yearMonth);
        request.setAttribute("accordDismissInYear", accordDismissInYear);
        
        //人力成本分析
        ManpowerCostApi manpowerCostApi = ApiManagerCenter.getApiDoc(ManpowerCostApi.class);
        List<ManpowerItemDto> queryItemDetail = manpowerCostApi.queryItemDetail(customerId, organId, null);
        String queryItemDetailStr = JSON.toJSONString(queryItemDetail);
        request.setAttribute("queryItemDetail", queryItemDetailStr);
    	List<ManpowerDto> getAllDetailData = manpowerCostApi.getAllDetailData(customerId, organId, null);
    	String getAllDetailDataStr = JSON.toJSONString(getAllDetailData);
        request.setAttribute("getAllDetailData", getAllDetailDataStr);
    	List<ManpowerCompareDto> getProportionYear = manpowerCostApi.getProportionYear(customerId, organId, null);
    	String getProportionYearStr = JSON.toJSONString(getProportionYear);
        request.setAttribute("getProportionYear", getProportionYearStr);
        SalaryBoardApi salaryBoardApi = ApiManagerCenter.getApiDoc(SalaryBoardApi.class);
        Map<String,Object> getSalaryWageStructure = salaryBoardApi.getSalaryWageStructure(customerId, organId);
        String getSalaryWageStructureStr = JSON.toJSONString(getSalaryWageStructure);
        request.setAttribute("getSalaryWageStructure", getSalaryWageStructureStr);
        List<MonthReportManpowerCostDto> manpowerCostInfo = monthReportService.manpowerCostInfo(customerId, organId, yearMonth);
        String manpowerCostInfoStr = JSON.toJSONString(manpowerCostInfo);
        request.setAttribute("manpowerCostInfo", manpowerCostInfoStr);
        
        //投入产出分析
        PerBenefitApi perBenefitApi = ApiManagerCenter.getApiDoc(PerBenefitApi.class);
        List<PerBenefitDto> getOrgBenefitData = perBenefitApi.getOrgBenefitData(customerId, organId);
        String getOrgBenefitDataStr = JSON.toJSONString(getOrgBenefitData);
        request.setAttribute("getOrgBenefitData", getOrgBenefitDataStr);
    	List<PerBenefitDto> getTrendData = perBenefitApi.getTrendData(customerId, organId, "0");
    	String getTrendDataStr = JSON.toJSONString(getTrendData);
        request.setAttribute("getTrendData", getTrendDataStr);
        
        List<SalaryBoardDto> getSalaryOrganRateOfReturn = salaryBoardApi.getSalaryOrganRateOfReturn(customerId, organId, yearMonth);
        String getSalaryOrganRateOfReturnStr = JSON.toJSONString(getSalaryOrganRateOfReturn);
        request.setAttribute("getSalaryOrganRateOfReturn", getSalaryOrganRateOfReturnStr);
    	List<SalaryBoardDto> getSalaryMonthRateOfReturn = salaryBoardApi.getSalaryMonthRateOfReturn(customerId, organId);
    	String getSalaryMonthRateOfReturnStr = JSON.toJSONString(getSalaryMonthRateOfReturn);
        request.setAttribute("getSalaryMonthRateOfReturn", getSalaryMonthRateOfReturnStr);
        
        //劳动力效能分析
        LaborEfficiencyApi laborEfficiencyApi = ApiManagerCenter.getApiDoc(LaborEfficiencyApi.class);
        Map<String, Object> getLaborEfficiencyRatio = laborEfficiencyApi.getLaborEfficiencyRatio(customerId, organId, yearMonth, yearMonth, null);
        String getLaborEfficiencyRatioStr = JSON.toJSONString(getLaborEfficiencyRatio);
        request.setAttribute("getLaborEfficiencyRatio", getLaborEfficiencyRatioStr);
    	Map<String, Object> queryOvertimeByOrgan =laborEfficiencyApi.queryOvertimeByOrgan(customerId, organId, yearMonth, yearMonth, null);
    	String queryOvertimeByOrganStr = JSON.toJSONString(queryOvertimeByOrgan);
        request.setAttribute("queryOvertimeByOrgan", queryOvertimeByOrganStr);
        
        //招聘分析
        RecruitBoardApi recruitBoardApi = ApiManagerCenter.getApiDoc(RecruitBoardApi.class);
        List<RecruitPositionMeetRateDto> getPositionMeetRate = recruitBoardApi.getPositionMeetRate(customerId, empId, organId);
        String getPositionMeetRateStr = JSON.toJSONString(getPositionMeetRate);
        request.setAttribute("getPositionMeetRate", getPositionMeetRateStr);
        List<RecruitChannelResultDto> getRecruitChannel = recruitBoardApi.getRecruitChannel(customerId, null, organId);
        String getRecruitChannelStr = JSON.toJSONString(getRecruitChannel);
        request.setAttribute("getRecruitChannel", getRecruitChannelStr);
        
        //培训分析
        TrainBoardApi trainBoardApi = ApiManagerCenter.getApiDoc(TrainBoardApi.class);
        Map<String, Object> getSubOrganizationCover = trainBoardApi.getSubOrganizationCover(customerId, organId);
        String getSubOrganizationCoverStr = JSON.toJSONString(getSubOrganizationCover);
        request.setAttribute("getSubOrganizationCover", getSubOrganizationCoverStr);
    	Map<String, Object>  getTrainingType = trainBoardApi.getTrainingType(customerId, organId);
    	String getTrainingTypeStr = JSON.toJSONString(getTrainingType);
        request.setAttribute("getTrainingType", getTrainingTypeStr);
    	List<TrainTypeDto> findTrainingTypeRecord = trainBoardApi.findTrainingTypeRecord(customerId, "1", organId);
    	String findTrainingTypeRecordStr = JSON.toJSONString(findTrainingTypeRecord);
        request.setAttribute("findTrainingTypeRecord", findTrainingTypeRecordStr);
        List<MonthReportTrainGeneralDto> getTrainGeneral = monthReportService.queryTrainGeneral(organId, Integer.parseInt(yearMonth.substring(0, 4)), customerId);
        String getTrainGeneralStr = JSON.toJSONString(getTrainGeneral);
        request.setAttribute("getTrainGeneral", getTrainGeneralStr);
        
        //绩效分析
        EmployeePerformanceApi employeePerformanceApi = ApiManagerCenter.getApiDoc(EmployeePerformanceApi.class);
        List<PerfChangeCountDto> queryPerchangeByOrgan = employeePerformanceApi.queryPerchangeByOrgan(customerId, organId, yearMonth);
        String queryPerchangeByOrganStr = JSON.toJSONString(queryPerchangeByOrgan);
        request.setAttribute("queryPerchangeByOrgan", queryPerchangeByOrganStr);
    	List<Integer> getPerfChangeDate = employeePerformanceApi.getPerfChangeDate(customerId);
    	String getPerfChangeDateStr = JSON.toJSONString(getPerfChangeDate);
        request.setAttribute("getPerfChangeDate", getPerfChangeDateStr);
    	Map<String, PreChangeStatusDto> getPreChangeCountData = employeePerformanceApi.getPreChangeCountData(customerId, organId, getPerfChangeDate);
    	String getPreChangeCountDataStr = JSON.toJSONString(getPreChangeCountData);
        request.setAttribute("getPreChangeCountData", getPreChangeCountDataStr);
    	
        //晋级分析
    	List<MonthReportPromotionDto> queryPromotionGeneral = monthReportService.queryPromotionGeneral(customerId, organId, Integer.parseInt(yearMonth));
    	String queryPromotionGeneralStr = JSON.toJSONString(queryPromotionGeneral);
        request.setAttribute("queryPromotionGeneral", queryPromotionGeneralStr);
    	List<MonthReportEmpCountDto> getInJobEmpCount = monthReportService.queryInJobsEmpCount(organId, customerId);
    	String getInJobEmpCountStr = JSON.toJSONString(getInJobEmpCount);
        request.setAttribute("getInJobEmpCount", getInJobEmpCountStr);
    	
        //销售分析
        List<MonthReportSalesCountDto> getSalesCount0 = monthReportService.querySalesCount(0, organId, Integer.parseInt(yearMonth), customerId);
        String getSalesCountStr0 = JSON.toJSONString(getSalesCount0);
        request.setAttribute("getSalesCount0", getSalesCountStr0);
        Integer beginTime = DateUtil.getPreYearMonth(Integer.parseInt(yearMonth), 6);
        String id = null;
        if(getSalesCount0.size() > 0) {
        	id = getSalesCount0.get(0).getItemId();
        }
        List<MonthReportSalesCountDto> getSalesCountByMonth0 = monthReportService.querySalesCountByMonth(2, null, id, beginTime, Integer.parseInt(yearMonth), customerId);
        String getSalesCountByMonthStr0 = JSON.toJSONString(getSalesCountByMonth0);
        request.setAttribute("getSalesCountByMonth0", getSalesCountByMonthStr0);
        List<MonthReportSalesCountDto> getSalesCount1 = monthReportService.querySalesCount(1, organId, Integer.parseInt(yearMonth), customerId);
        String getSalesCountStr1 = JSON.toJSONString(getSalesCount1);
        request.setAttribute("getSalesCount1", getSalesCountStr1);
        String id1 = null;
        if(getSalesCount1.size() > 0) {
        	id1 = getSalesCount1.get(0).getItemId();
        }
        List<MonthReportSalesCountDto> getSalesCountByMonth1 = monthReportService.querySalesCountByMonth(1, id1, organId, beginTime, Integer.parseInt(yearMonth), customerId);
        String getSalesCountByMonthStr1 = JSON.toJSONString(getSalesCountByMonth1);
        request.setAttribute("getSalesCountByMonth1", getSalesCountByMonthStr1);
        
        return "biz/other/monthReportExport";
    }
    
    @ResponseBody
    @RequestMapping(value = "/getAccordDismissAnalysis", method = RequestMethod.POST)
    public List<MonthReportDimissionEmpDto> getAccordDismissAnalysis(String organId, String yearMonth) {
    	return monthReportService.accordDismissAnalysis(getCustomerId(), organId, yearMonth);
    }
    
    @ResponseBody
    @RequestMapping(value = "/getAccordDismissByYearMonth", method = RequestMethod.POST)
    public Map<String, Object> accordDismissByYearMonth(String organId, String yearMonth) {
    	return monthReportService.accordDismissByYearMonth(getCustomerId(), organId, yearMonth);
    }
    
    @ResponseBody
    @RequestMapping(value = "/getAccordDismissInYear", method = RequestMethod.POST)
    public Double accordDismissInYear(String organId, String yearMonth) {
    	return monthReportService.accordDismissInYear(getCustomerId(), organId, yearMonth);
    }
    
    @ResponseBody
    @RequestMapping(value = "/getManpowerCostInfo", method = RequestMethod.POST)
    public List<MonthReportManpowerCostDto> manpowerCostInfo(String organId, String yearMonth) {
    	return monthReportService.manpowerCostInfo(getCustomerId(), organId, yearMonth);
    }

    private String getServerPath(HttpServletRequest request) {
        StringBuilder path = new StringBuilder();
        path.append(request.getScheme()).append("://");
        path.append(request.getServerName()).append(":");
        path.append(request.getServerPort());
        return path.toString();
    }

    private String getBasePath(HttpServletRequest request) {
        StringBuilder path = new StringBuilder();
        path.append(request.getScheme()).append("://");
        path.append(request.getServerName()).append(":");
        path.append(request.getServerPort());
        path.append(request.getContextPath());
        return path.toString();
    }
    
}

package net.chinahrd.accordDismiss.mvc.pc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.accordDismiss.mvc.pc.service.AccordDismissService;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.accordDismiss.AccordDismissDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRecordDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissTrendDto;
import net.chinahrd.entity.dto.pc.accordDismiss.QuarterDismissCountDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.common.CompareDto;
import net.chinahrd.entity.dto.pc.common.ConfigDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.FunctionService;
import net.chinahrd.mvc.pc.service.common.ConfigService;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.PropertiesUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 主动流失率Controller
 */
@Controller
@RequestMapping("/accordDismiss")
public class AccordDismissController extends BaseController {

    @Autowired
    private FunctionService functionService;

    @Autowired
    private AccordDismissService accordDismissService;

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/toAccordDismissView")
    public String toAccordDismissView(HttpServletRequest request) {
    	String functionId = CacheHelper.getFunctionId("accordDismiss/toAccordDismissView");
      if(StringUtils.isNotEmpty(functionId)){request.setAttribute("quotaId", functionId); }
      String customerId = getCustomerId();
      List<OrganDto> organPermit = getOrganPermit();
      if (CollectionKit.isNotEmpty(organPermit)) {
          String quarterLastDay = accordDismissService.queryQuarterLastDay(customerId);
          request.setAttribute("quarterLastDay", quarterLastDay.substring(0, 10));
      }
      return "biz/drivingforce/accordDismiss";
    }

    /**
     * 更新主动流失率正常值和风险值
     *
     * @param normal
     * @param risk
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateDismissValue", method = RequestMethod.POST)
    public ResultDto<String> updateDismissValue(String normal, String risk) {
        ResultDto<String> rsDto = new ResultDto<String>();
        List<ConfigDto> list = CollectionKit.newList();
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.ZDLSL_NORMAL), normal));
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.ZDLSL_RISK), risk));
        configService.updateSysConfig(getCustomerId(), list);
        rsDto.setType(true);
        return rsDto;
    }

    /***
     * 更新主动流失率基础配置信息
     * @param terminals 通知终端数值
     * @param persons   通知对象数值
     * @param notify    通知信息数值
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateDismissBaseConfig", method = RequestMethod.POST)
    public ResultDto<String> updateDismissBaseConfig(String terminals, String persons, String notify) {
        ResultDto<String> rsDto = new ResultDto<String>();
        List<ConfigDto> list = CollectionKit.newList();
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.ZDLSL_FORTERMINAL), terminals));
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.ZDLSL_FORPERSON), persons));
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.ZDLSL_FORNOTIFY), notify));
        configService.updateSysConfig(getCustomerId(), list);
        rsDto.setType(true);
        return rsDto;
    }

    /**
     * 获取主动流失率基础配置信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDismissBaseConfig", method = RequestMethod.POST)
    public Map<String, Object> getDismissBaseConfig() {
        Map<String, Object> maps = CollectionKit.newMap();
        List<Integer> terminals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.ZDLSL_FORTERMINAL);
        List<Integer> persons = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.ZDLSL_FORPERSON);
        Integer notify = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.ZDLSL_FORNOTIFY);
        maps.put("terminals", terminals);
        maps.put("persons", persons);
        maps.put("notify", notify);
        return maps;
    }


    /**
     * 查询流失人员明细
     */
    @ResponseBody
    @RequestMapping(value = "/queryRunOffDetail", method = RequestMethod.POST)
    public PaginationDto<AccordDismissDto> queryRunOffDetail(
            String queryType,  //查询方式  按名字 还是高级
            String keyName,
            String roType, String sequenceId,
            String abilityId,
            String isKeyTalent, String keyTalentTypeId,
            String performanceKey,
            String organizationId, String beginDate, String endDate,
            Integer page, Integer rows
    ) {

        PaginationDto<AccordDismissDto> dto = new PaginationDto<AccordDismissDto>(page, rows);
//    	 
//    	QuarterRangeEnum range = QuarterRangeEnum.valueOf("Q" + quarter);
//    	String beginDate = year + "-" + range.getBeginDate();
//    	String endDate = year + "-" + range.getEndDate();
        return accordDismissService.queryRunOffDetail(queryType, keyName, getCustomerId(), organizationId, beginDate, endDate, dto,
                roType, sequenceId, abilityId, isKeyTalent, keyTalentTypeId, performanceKey);
    }

    /**
     * 本部与下属部门流失率对比数据的数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSubDismissData")
    public Map<String, Object> getSubDismissData(String organizationId, String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        return accordDismissService.getSubDismissData(getCustomerId(), organizationId, date);
    }

    /**
     * 查询流失记录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDismissRecord")
    public List<DismissRecordDto> getDismissRecord(String organizationId, String prevQuarter, String yearMonths) {
        return accordDismissService.queryDismissRecord(getCustomerId(), organizationId, yearMonths, prevQuarter);
    }


    /**
     * 查询主动流失率趋势相关信息
     *
     * @param organId
     * @param prevQuarter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryDismissTrend")
    public List<DismissTrendDto> queryDismissTrend(@RequestParam("organId") String organId, @RequestParam("prevQuarter") String prevQuarter) {
        return accordDismissService.queryDismissTrend(organId, prevQuarter, getCustomerId());
    }

    /**
     * 根据季度查询主动流失率
     *
     * @param organId
     * @param prevQuarter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryDisminss4Quarter")
    public Map<String, Object> queryDisminss4Quarter(@RequestParam("organId") String organId, @RequestParam("prevQuarter") String prevQuarter) {
        Map<String, Object> map = CollectionKit.newMap();
        //主动流失率-正常值和风险值
        double normal = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.ZDLSL_NORMAL);
        double risk = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.ZDLSL_RISK);
        double max = ArithUtil.sum(risk, ArithUtil.div(risk, 2d, 4));      //主动流失率的最大值取风险值+10%
        map.put("normal", normal);
        map.put("risk", risk);
        map.put("max", max);
        DismissTrendDto dto = accordDismissService.queryDisminss4Quarter(organId, prevQuarter, getCustomerId());
        map.put("dismissTrend", dto);
        return map;
    }


    /**
     * 获取“关键人才流失人数”的数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getKeyDismissData")
    public CompareDto getKeyDismissData(String organId, String date) {

        return accordDismissService.getKeyDismissData(organId, date, getCustomerId());
    }

    /**
     * 获取“高绩效流失人数”的数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPerfDismissData")
    public CompareDto getPerfDismissData(String organId, String date) {

        return accordDismissService.getPerformDismissData(organId, date, getCustomerId());
    }

    /**
     * 查询季度流失人员统计信息
     *
     * @param organId
     * @param prevQuarter
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/quarterDismissInfo")
    public Map<String, List<QuarterDismissCountDto>> quarterDismiss4Pref(@RequestParam("organId") String organId, @RequestParam("prevQuarter") String prevQuarter) {
    	return accordDismissService.queryQuarterDismissInfo(organId, prevQuarter, getCustomerId());
    }

}

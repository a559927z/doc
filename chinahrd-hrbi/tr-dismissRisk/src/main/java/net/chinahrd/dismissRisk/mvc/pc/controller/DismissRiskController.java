package net.chinahrd.dismissRisk.mvc.pc.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.chinahrd.dismissRisk.mvc.pc.service.DismissRiskService;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRiskDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRiskReviewDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.common.ConfigDto;
import net.chinahrd.entity.dto.pc.common.ItemDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.entity.dto.pc.common.RiskDto;
import net.chinahrd.entity.dto.pc.common.RiskTreeDto;
import net.chinahrd.entity.enums.TipsEnum;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.common.CommonService;
import net.chinahrd.mvc.pc.service.common.ConfigService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.PropertiesUtil;

/**
 * 人才流失风险Controller
 */
@Controller
@RequestMapping("/dismissRisk")
public class DismissRiskController extends BaseController {

    @Autowired
    private DismissRiskService dismissRiskService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/toDismissRiskView")
    public String toDismissRiskView(HttpServletRequest request) {
        List<OrganDto> organPermit = getOrganPermit();
        if (CollectionKit.isNotEmpty(organPermit)) {
            OrganDto topOneOrgan = organPermit.get(0);
            request.setAttribute("reqOrganId", topOneOrgan.getOrganizationId());
        }
        List<ItemDto> talentType = commonService.getTalentType(getCustomerId());
        request.setAttribute("talentType", talentType);
        return "biz/drivingforce/dismissRisk2";
    }


    /**
     * 获取直接下属的数据
     */
    @ResponseBody
    @RequestMapping(value = "/getDirectEmpData")
    public List<DismissRiskDto> getDirectEmpData() {
        String customerId = getCustomerId();
        String userEmpId = getUserEmpId();
        return dismissRiskService.getDirectRiskData(customerId, userEmpId);
    }


    /**
     * 查询下级组织员工信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/querySubOrganEmpInfo")
    public List<DismissRiskDto> querySubOrganEmpInfo() {
        return dismissRiskService.querySubOrganEmpInfo(getUserEmpId(), getCustomerId());
    }

    /**
     * 查询离职风险预测回归
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryDimissRissReviewInfo")
    public List<DismissRiskReviewDto> queryDimissRissReviewInfo() {
        return dismissRiskService.queryDimissRissReviewInfo(getCustomerId(), getUserEmpId());
    }

    /**
     * 查询离职风险预测回归
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEmpRiskDetail")
    public List<RiskTreeDto> getEmpRiskDetail(String empId) {

        return dismissRiskService.getEmpRiskDetail(getCustomerId(),empId);
    }
    /**
     * 查询离职风险历史数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEmpAllRiskDetail")
    public List<RiskDto> getEmpAllRiskDetail(String empId) {

        return dismissRiskService.getEmpAllRiskDetail(getCustomerId(),empId);
    }

    /**
	 *	默认评估的离职风险树
	 */
    @ResponseBody
    @RequestMapping(value = "/getEmpRiskDefaultDetail")
    public List<RiskTreeDto> getEmpRiskDefaultDetail() {
        return dismissRiskService.queryEmpRiskDefaultDetail(getCustomerId());
    }

    /***
     * 更新基础配置信息
     * @param terminals 通知终端数值
     * @param persons   通知对象数值
     * @param notify    通知信息数值
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateBaseConfig", method = RequestMethod.POST)
    public ResultDto<String> updateBaseConfig(String terminals, String persons,String notify,String hasmessage) {
        ResultDto<String> rsDto = new ResultDto<String>();
        List<ConfigDto> list = CollectionKit.newList();
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.RCLSFX_FORTERMINAL), terminals));
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.RCLSFX_FORPERSON), persons));
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.RCLSFX_FORNOTIFY), notify));
        list.add(new ConfigDto(PropertiesUtil.pps.getProperty(ConfigKeyUtil.RCLSFX_HASMESSAGE), hasmessage));
        configService.updateSysConfig(getCustomerId(), list);
        rsDto.setType(true);
        return rsDto;
    }

    /**
     * 获取基础配置信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBaseConfig", method = RequestMethod.POST)
    public Map<String, Object> getBaseConfig() {
        Map<String, Object> maps = CollectionKit.newMap();
        List<Integer> terminals = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCLSFX_FORTERMINAL);
        List<Integer> persons = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RCLSFX_FORPERSON);
        Integer notify = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.RCLSFX_FORNOTIFY);
        Integer hasmessage = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.RCLSFX_HASMESSAGE);
        maps.put("terminals", terminals);
        maps.put("persons", persons);
        maps.put("notify", notify);
        maps.put("hasmessage", hasmessage);
        maps.put("hasmessageTxt", TipsEnum.getDescByCode(hasmessage != null ? hasmessage.intValue() : 2, 5));
        return maps;
    }

    /**
     * 获取预警提示消息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRiskWarningPeriodDesc", method = RequestMethod.POST)
    public ResultDto<String> getRiskWarningPeriodDesc() {
        ResultDto<String> result = new ResultDto<String>();
//        String desc = dismissRiskService.getRiskWarningPeriodDesc(getCustomerId());
        result.setType(true);
//        result.setT(desc);
        return result;
    }
}

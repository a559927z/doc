package net.chinahrd.talentStructure.mvc.pc.controller;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.entity.dto.pc.common.TableGridDto;
import net.chinahrd.entity.dto.pc.talentStructure.TalentStructureDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.talentStructure.mvc.pc.service.TalentStructureService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 人力结构
 * Created by wqcai on 2016/2/19.
 */
@Controller
@RequestMapping("/talentStructure")
public class TalentStructureController extends BaseController {

    @Autowired
    TalentStructureService talentStructureService;

    @RequestMapping(value = "/toTalentStructureView")
    public String toTalentStructureView(HttpServletRequest request) {
        // 改用缓存提取funId by jxzhang on 2016-11-10
        String functionId = CacheHelper.getFunctionId("talentStructure/toTalentStructureView");
        if (StringUtils.isNotBlank(functionId)) request.setAttribute("quotaId", functionId);

        return "biz/competency/talentStructure";
    }

    /**
     * 编制分析 by jxzhag on 2016-02-22
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBudgetAnalyse", method = RequestMethod.POST)
    public TalentStructureDto getBudgetAnalyse(String organId) {
        return talentStructureService.findBudgetAnalyse(organId, getCustomerId());
    }

    /**
     * 查询预警值 by jxzhang on 2016-02-22
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getConfigWarnVal", method = RequestMethod.POST)
    public Map<String, Double> getConfigWarnVal() {
        Double normal = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.RLJG_NORMAL);
        Double risk = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.RLJG_RISK);
        Map<String, Double> map = CollectionKit.newMap();
        map.put("normal", normal);
        map.put("risk", risk);
        return map;
    }


    /**
     * 预警值设置 by jxzhang on 2016-02-22
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/setConfigWarnVal", method = RequestMethod.POST)
    public ResultDto<String> setConfigWarnVal(Double normal, Double risk) {
        ResultDto<String> result = new ResultDto<>();
        if (Double.isNaN(normal) && Double.isNaN(risk)) {
            result.setMsg("当前员工操作有误，请重新操作");
            return result;
        }
        boolean rs = talentStructureService.updateConfigWarnVal(getCustomerId(), normal, risk);
        result.setMsg(rs ? "预警值设置成功" : "预警值设置失败");
        result.setType(rs);
        return result;
    }

    /**
     * 人力结构数据 by jxzhang on 2016-02-23
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentStuctureData", method = RequestMethod.POST)
    public Map<String, Object> getTalentStuctureData(String organId) {
        return talentStructureService.getTalentStuctureData(organId, getCustomerId());
    }

    /**
     * 查询管理者员工分布
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getManageEmp4Organ", method = RequestMethod.GET)
    public List<KVItemDto> getManageEmp4Organ(String organId) {
        return talentStructureService.queryManageEmp4Organ(organId, getCustomerId());
    }

    /**
     * 查询管理者员工分布
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getManageToSubOrgan", method = RequestMethod.GET)
    public TableGridDto<Map<String, Object>> getManageToSubOrgan(String organId) {
        return talentStructureService.queryManagerOrEmpList(organId, getCustomerId());
    }

    /**
     * 查询当前机构职级分布
     *
     * @param organId
     * @param sequenceId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAbility4Organ", method = RequestMethod.GET)
    public List<KVItemDto> getAbility4Organ(String organId, String sequenceId) {
        return talentStructureService.queryAbility4Organ(organId, sequenceId, getCustomerId());
    }

    /**
     * 查询下级机构职级分布
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAbilityToSubOrgan", method = RequestMethod.GET)
    public TableGridDto<Map<String, Object>> getAbilityToSubOrgan(String organId) {
        return talentStructureService.queryAbilityToSubOrgan(organId, getCustomerId());
    }

    /**
     * 获取当前机构职位序列分布
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSequence4Organ", method = RequestMethod.GET)
    public List<SequenceItemsDto> getSequence4Organ(String organId) {
        return talentStructureService.querySequence4Organ(organId, getCustomerId());
    }


    /**
     * 获取职位序列职级统计信息
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSequenceAbilityCount", method = RequestMethod.GET)
    public TableGridDto<Map<String, Object>> getSequenceAbilityCount(String organId) {
        return talentStructureService.querySequenceAbilityCount(organId, getCustomerId());
    }

    /**
     * 查询组织分布
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSubOrganCount", method = RequestMethod.GET)
    public List<KVItemDto> getSubOrganCount(String organId) {
        return talentStructureService.querySubOrganCount(organId, getCustomerId());
    }

    /**
     * 查询工作地分布
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWorkplaceCount", method = RequestMethod.GET)
    public List<KVItemDto> getWorkplaceCount(String organId) {
        return talentStructureService.queryWorkplaceCount(organId, getCustomerId());
    }


    /**
     * 查询当前机构学历分布
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDegree4Organ", method = RequestMethod.GET)
    public List<KVItemDto> getDegree4Organ(String organId) {
        return talentStructureService.queryDegree4Organ(organId, getCustomerId());
    }

    /**
     * 查询下级机构学历分布
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDegreeToSubOrgan", method = RequestMethod.GET)
    public TableGridDto<Map<String, Object>> getDegreeToSubOrgan(String organId) {
        return talentStructureService.queryDegreeToSubOrgan(organId, getCustomerId());
    }

    /**
     * 查询当前机构司龄分布
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCompanyAge4Organ", method = RequestMethod.GET)
    public List<KVItemDto> getCompanyAge4Organ(String organId) {
        return talentStructureService.queryCompanyAge4Organ(organId, getCustomerId());
    }

    /**
     * 查询下级机构司龄分布
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCompanyAgeToSubOrgan", method = RequestMethod.GET)
    public TableGridDto<Map<String, Object>> getCompanyAgeToSubOrgan(String organId) {
        return talentStructureService.queryCompanyAgeToSubOrgan(organId, getCustomerId());
    }


    /**
     * 人力结构数据To月报
     *
     * @param organId
     * @return
     * @author wqcai
     * @time 2016-08-25
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentStuctureByMonth", method = RequestMethod.POST)
    public Map<String, Object> getTalentStuctureByMonth(String organId) {
        return talentStructureService.queryTalentStuctureByMonth(organId, getCustomerId());
    }

    /**
     * 根据主序列查询能力层级分布-柱状图 by jxzhang 2016-02-26
     *
     * @param organId
     * @param seqId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAbEmpCountBarBySeqId", method = RequestMethod.POST)
    public Map<String, Integer> getAbEmpCountBarBySeqId(String organId, String seqId) {
        return talentStructureService.queryAbEmpCountBarBySeqId(organId, getCustomerId(), seqId);
    }
}

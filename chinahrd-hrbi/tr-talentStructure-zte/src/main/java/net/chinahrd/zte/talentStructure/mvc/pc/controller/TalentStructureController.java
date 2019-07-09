package net.chinahrd.zte.talentStructure.mvc.pc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.admin.FunctionTreeDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.entity.dto.pc.competency.TalentStructureTeamImgDto;
import net.chinahrd.entity.dto.pc.competency.TalentStructureTeamImgListDto;
import net.chinahrd.entity.dtozte.pc.talentstructure.TalentstructureDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.admin.FunctionService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.zte.talentStructure.mvc.pc.service.TalentStructureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 人力结构 Created by wqcai on 2016/2/19.
 */
@Controller
@RequestMapping("/talentStructure")
public class TalentStructureController extends BaseController {

    @Autowired
    TalentStructureService talentStructureService;
    @Autowired
    private FunctionService functionService;

    @RequestMapping(value = "/toTalentStructureView")
    public String toTalentStructureView(HttpServletRequest request) {
        FunctionTreeDto dto = functionService.findFunctionObj(getCustomerId(), null, "talentStructure/toTalentStructureView");
        if (dto != null) {
            request.setAttribute("quotaId", dto.getId());
        }
        List<KVItemDto> days = CacheHelper.getEmpMinMaxDays();
        request.setAttribute("minDay", days.size()>0? days.get(0).getV():new Date());
        request.setAttribute("maxDay", days.size()>1? days.get(1).getV():new Date());

        return "biz/competency/talentStructure";
    }

    private List<String> getCrowds(String crowds){
        List<String> cs = new ArrayList<>();
        String[] arr = crowds.split(",");
        for(String a:arr){
            if(!"".equals(a)){
                cs.add(a);
            }
        }
        return cs;
    }

    /**
     * 编制分析 by jxzhag on 2016-02-22
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBudgetAnalyse", method = RequestMethod.POST)
    public TalentstructureDto getBudgetAnalyse(String organId) {
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
        Map<String, Double> map = new HashMap<String, Double>();
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
        boolean rs = false;
        result.setMsg("预警值设置失败");

        if (null == normal && null == risk) {
            result.setMsg("对当前员工操作有误，请重新操作");
        }
        rs = talentStructureService.updateConfigWarnVal(getCustomerId(), normal, risk);
        result.setMsg(rs == true ? "预警值设置成功" : "预警值设失败");
        result.setType(rs);
        return result;
    }



    /*
    * 管理者员工分布
    * */
    @ResponseBody
    @RequestMapping(value = "/getAbilityEmpCount", method = RequestMethod.POST)
    public Map<String, Object> getAbilityEmpCount(String organId, String day, String crowd){
        return talentStructureService.getAbilityEmpCount(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd));
    }

    @ResponseBody
    @RequestMapping(value = "/getAbilityEmpList", method = RequestMethod.POST)
    public PaginationDto<TalentstructureDto> getAbilityEmpList(String organId, String day, String crowd, String id, Integer page, Integer rows){
        return talentStructureService.getAbilityEmpList(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd), id, page, rows);
    }

    /*
    * 职级分布
    * */
    @ResponseBody
    @RequestMapping(value = "/getAbilityCurtEmpCount", method = RequestMethod.POST)
    public Map<String, Object> getAbilityCurtEmpCount(String organId, String day, String crowd){
        return talentStructureService.getAbilityCurtEmpCount(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd));
    }

    @ResponseBody
    @RequestMapping(value = "/getAbilityCurtEmpList", method = RequestMethod.POST)
    public PaginationDto<TalentstructureDto> getAbilityCurtEmpList(String organId, String day, String crowd, String id, Integer page, Integer rows){
        return talentStructureService.getAbilityCurtEmpList(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd), id, page, rows);
    }

    /*
    * 职位序列分布
    * */
    @ResponseBody
    @RequestMapping(value = "/getSeqEmpCount", method = RequestMethod.POST)
    public Map<String, Object> getSeqEmpCount(String organId, String day, String crowd){
        return talentStructureService.getSeqEmpCount(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd));
    }

    /*
    * 组织分布
    * */
    @ResponseBody
    @RequestMapping(value = "/getOrganEmpCount", method = RequestMethod.POST)
    public Map<String, Object> getOrganEmpCount(String organId, String day, String crowd){
        return talentStructureService.getOrganEmpCount(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd));
    }

    @ResponseBody
    @RequestMapping(value = "/getOrganEmpList", method = RequestMethod.POST)
    public PaginationDto<TalentstructureDto> getOrganEmpList(String organId, String day, String crowd, String id, Integer page, Integer rows){
        return talentStructureService.getOrganEmpList(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd), id, page, rows);
    }

    /*
    * 工作地点分布
    * */
    @ResponseBody
    @RequestMapping(value = "/getWorkplaceEmpCount", method = RequestMethod.POST)
    public Map<String, Object> getWorkplaceEmpCount(String organId, String day, String crowd){
        return talentStructureService.getWorkplaceEmpCount(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd));
    }

    @ResponseBody
    @RequestMapping(value = "/getWorkplaceEmpList", method = RequestMethod.POST)
    public PaginationDto<TalentstructureDto> getWorkplaceEmpList(String organId, String day, String crowd, String id, Integer page, Integer rows){
        return talentStructureService.getWorkplaceEmpList(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd), id, page, rows);
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
    public Map<String, Integer> getAbEmpCountBarBySeqId(String organId, String seqId, String day, String crowd) {
        return talentStructureService.queryAbEmpCountBarBySeqId(organId, getCustomerId(), seqId, DateUtil.strToDate(day), getCrowds(crowd));
    }

    @ResponseBody
    @RequestMapping(value = "/getAbEmpCountBarBySeqIdList", method = RequestMethod.POST)
    public PaginationDto<TalentstructureDto> getAbEmpCountBarBySeqIdList(String organId, String seqId, String day, String crowd, String id, Integer page, Integer rows){
        return talentStructureService.getAbEmpCountBarBySeqIdList(organId, seqId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd), id, page, rows);
    }


    //男女占比
    @ResponseBody
    @RequestMapping(value = "/getSexData", method = RequestMethod.POST)
    public List<TalentStructureTeamImgDto> getSexData(String organId, String day, String crowd) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        return talentStructureService.getSexData(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd));
    }
    @ResponseBody
    @RequestMapping(value = "/getSexDataList", method = RequestMethod.POST)
    public PaginationDto<TalentStructureTeamImgListDto> getSexDataList(String organId, String day, String crowd, String id, Integer page, Integer rows){
        return talentStructureService.getSexDataList(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd), id, page, rows);
    }

    //婚姻状况
    @ResponseBody
    @RequestMapping(value = "/getMarryStatusData", method = RequestMethod.POST)
    public List<TalentStructureTeamImgDto> getMarryStatusData(String organId, String day, String crowd) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        return talentStructureService.getMarryStatusData(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd));
    }
    @ResponseBody
    @RequestMapping(value = "/getMarryStatusDataList", method = RequestMethod.POST)
    public PaginationDto<TalentStructureTeamImgListDto> getMarryStatusDataList(String organId, String day, String crowd, String id, Integer page, Integer rows){
        return talentStructureService.getMarryStatusDataList(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd), id, page, rows);
    }

    //血型
    @ResponseBody
    @RequestMapping(value = "/getBloodData", method = RequestMethod.POST)
    public List<TalentStructureTeamImgDto> getBloodData(String organId, String day, String crowd) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        return talentStructureService.getBloodData(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd));
    }
    @ResponseBody
    @RequestMapping(value = "/getBloodDataList", method = RequestMethod.POST)
    public PaginationDto<TalentStructureTeamImgListDto> getBloodDataList(String organId, String day, String crowd, String id, Integer page, Integer rows){
        return talentStructureService.getBloodDataList(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd), id, page, rows);
    }

    //星座
    @ResponseBody
    @RequestMapping(value = "/getStarData", method = RequestMethod.POST)
    public  Map<String, Object> getStarData(String organId, String day, String crowd) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        return talentStructureService.getStarData(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd));
    }
    @ResponseBody
    @RequestMapping(value = "/getStarDataList", method = RequestMethod.POST)
    public PaginationDto<TalentStructureTeamImgListDto> getStarDataList(String organId, String day, String crowd, String id, Integer page, Integer rows){
        return talentStructureService.getStarDataList(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd), id, page, rows);
    }

    //性格
    @ResponseBody
    @RequestMapping(value = "/getPersonalityData", method = RequestMethod.POST)
    public List<TalentStructureTeamImgDto> getPersonalityData(String organId, String day, String crowd) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }

        return talentStructureService.getPersonalityData(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd));
    }
    @ResponseBody
    @RequestMapping(value = "/getPersonalityDataList", method = RequestMethod.POST)
    public PaginationDto<TalentStructureTeamImgListDto> getPersonalityDataList(String organId, String day, String crowd, String id, Integer page, Integer rows){
        return talentStructureService.getPersonalityDataList(organId, getCustomerId(), DateUtil.strToDate(day), getCrowds(crowd), id, page, rows);
    }
}

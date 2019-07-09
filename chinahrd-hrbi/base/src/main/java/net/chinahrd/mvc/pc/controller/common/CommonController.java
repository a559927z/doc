package net.chinahrd.mvc.pc.controller.common;

import java.util.List;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.common.EmpDto;
import net.chinahrd.entity.dto.pc.common.ItemDto;
import net.chinahrd.entity.dto.pc.common.OpearRiskDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.entity.dto.pc.common.RiskDto;
import net.chinahrd.entity.dto.pc.common.RiskItemDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.common.CommonService;
import net.chinahrd.utils.CollectionKit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 通用Controller
 *
 * @author wqcai
 */
@Controller
@RequestMapping(value = "/common")
public class CommonController extends BaseController {

    @Autowired
    private CommonService commonService;

    /***
     * 跳转到临时页面
     *
     * @return
     */
    @RequestMapping(value = "/noAuthor")
    public String getNoAuthor() {
        return "biz/noAuthor";
    }

    /**
     * 获取人群集合
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPopulations")
    public List<KVItemDto> getPopulations() {
        return commonService.queryPopulations(getCustomerId());
    }

    /**
     * 获取岗位信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPosition")
    public List<KVItemDto> getPosition(String positionName) {
        return commonService.queryPositions(positionName, getCustomerId());
    }

    /**
     * 获取职级
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAbility")
    public List<ItemDto> getAbility() {
        return commonService.queryAbilityType(getCustomerId());
    }

    /**
     * 获取人才类型
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentType")
    public List<ItemDto> getTalentType() {
        return commonService.getTalentType(getCustomerId());
    }

    /**
     * 筛选条件
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSearchBox")
    public List<ItemDto> getSearchBox() {
        return commonService.getSearchBox(getCustomerId());
    }
    
    /**
     * 筛选条件
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentMapsSearchBox")
    public List<ItemDto> getTalentMapsSearchBox() {
    	return commonService.getTalentMapsSearchBox(getCustomerId());
    }

    /**
     * 筛选条件
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTalentSearchBox")
    public List<ItemDto> getTalentSearchBox() {
        return commonService.getTalentSearchBox(getCustomerId());
    }

    /**
     * 绩效变化页面 筛选条件
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPerChangeSearchBox")
    public List<ItemDto> getSearchBoxPerChange() {
        return commonService.getSearchBoxPerChange(getCustomerId());
    }

    @ResponseBody
    @RequestMapping(value = "/getTimeScope")
    public List<String> getTimeScope() {
        return commonService.getTimeScope(getCustomerId());
    }

    @ResponseBody
    @RequestMapping(value = "/getEmpBaseInfo")
    public EmpDto getEmpBaseInfo(String empId) {

        return commonService.getEmpBaseInfo(getCustomerId(), empId);
    }

    /**
     * 对应 riskTree2 控件
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getEmpBaseInfo2")
    public EmpDto getEmpBaseInfo2(String empId) {

        return commonService.getEmpBaseInfo(getCustomerId(), empId);
    }

    @ResponseBody
    @RequestMapping(value = "/addRisk", method = RequestMethod.POST)
    public ResultDto<String> addRisk(
            @RequestBody(required = false) OpearRiskDto opearRiskDto) {
        ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        if (null == opearRiskDto) {
            result.setMsg("对当前员工操作有误，请重新选择员工进行操作");
        }
        List<RiskDto> list = opearRiskDto.getRisks();
        RiskDto riskDto = null;
        List<RiskItemDto> itemList = CollectionKit.newList();
        for (RiskDto r : list) {
            if (r.getRiskId().equals("-1")) {
                riskDto = r;
                r.setEmpId(opearRiskDto.getEmpId());
                r.setNote(opearRiskDto.getNote());
                list.remove(r);
                break;
            }
        }
        for (RiskDto r : list) {
            RiskItemDto rid = new RiskItemDto();
            rid.setRiskFlag(r.getRiskFlag());
            rid.setRiskId(r.getRiskId());
            itemList.add(rid);
        }
        rs = commonService.addRisk(getCustomerId(), riskDto, itemList);
        result.setType(rs);
        if (rs) {
            result.setMsg("评估成功");
        } else {
            result.setMsg("评估失败");
        }
        return result;
    }
};

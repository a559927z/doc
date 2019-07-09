package net.chinahrd.recruitBoard.mvc.pc.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.chinahrd.core.tools.collection.CollectionKit;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitChannelResultDto;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitDismissionWeekDto;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitJobChangeDto;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitJobChangeTotalDto;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitPositionMeetRateDto;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitPositionPayDto;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitPositionResultDto;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitRecommendEmpDto;
import net.chinahrd.entity.dto.pc.recruitBoard.RecruitTagDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.recruitBoard.mvc.pc.service.RecruitBoardService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.ConfigKeyUtil;

/**
 * 招聘看板
 * Created by dcli on 2016/5/4.
 */
@Controller
@RequestMapping("/recruitBoard")
public class RecruitBoardController extends BaseController {

    @Autowired
    private RecruitBoardService recruitBoardService;

    @RequestMapping(value = "/toRecruitBoardView")
    public String toRecruitBoardView(HttpServletRequest request) {
    	
        // 改用缓存提取funId by jxzhang on 2016-11-10
        String functionId = CacheHelper.getFunctionId("recruitBoard/toRecruitBoardView");
        if(null !=functionId){request.setAttribute("quotaId", functionId); }
        
        return "biz/productivity/recruitBoard";
    }


    /***
     * 获取待招聘岗位
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWaitRecruitPost", method = RequestMethod.GET)
    public Map<String, Object> getWaitRecruitPost(String organId) {
        if (StringUtils.isEmpty(organId)) return null;
        return recruitBoardService.queryWaitRecruitPost(organId, getCustomerId());
    }

    /**
     * 获取待招聘人数
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWaitRecruitNum", method = RequestMethod.GET)
    public Map<String, Object> getWaitRecruitNum(String organId) {
        if (StringUtils.isEmpty(organId)) return null;
        return recruitBoardService.queryWaitRecruitNum(organId, getCustomerId());
    }

    /**
     * 获取招聘费用及预算
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRecruitCostAndBudget", method = RequestMethod.GET)
    public Map<String, Object> getRecruitCostAndBudget(String organId) {
        if (StringUtils.isEmpty(organId)) return null;
        return recruitBoardService.queryRecruitCostAndBudget(organId, getCustomerId());
    }

    /**
     * 查询岗位满足率信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPostMeetRateNoSort", method = RequestMethod.GET)
    public List<RecruitPositionMeetRateDto> getPositionMeetRate(String organId) {
        if (StringUtils.isEmpty(organId)) return null;
        List<RecruitPositionMeetRateDto> dtos = recruitBoardService.queryPositionMeetRate(organId, getUserEmpId(), getCustomerId());
        return dtos;
    }

    /**
     * 查询岗位满足率信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPostMeetRate", method = RequestMethod.GET)
    public List<RecruitPositionMeetRateDto> getPositionMeetRate(String organId, String quotaId) {
        if (StringUtils.isEmpty(organId)) return null;
        List<RecruitPositionMeetRateDto> dtos = recruitBoardService.queryPositionMeetRate(organId, quotaId, getUserEmpId(), getCustomerId());
        return dtos;
    }

    /**
     * 招聘岗位进度详情
     *
     * @param id   满足率ID
     * @param type 0:简历 1:面试 2:offer 3:入职
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPositionResult", method = RequestMethod.GET)
    public PaginationDto<RecruitPositionResultDto> getPositionResult(String id, Integer type, Integer page, Integer rows) {
        if (StringUtils.isEmpty(id)) return null;

        if (type == null) type = 0;

        PaginationDto<RecruitPositionResultDto> dto = new PaginationDto<RecruitPositionResultDto>(page, rows);
        return recruitBoardService.queryPositionResult(id, type, dto, getCustomerId());
    }

    /**
     * 更新岗位满足率个人排序顺序信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatePostMeetRateSequence", method = RequestMethod.POST)
    public ResultDto<String> updatePositionMeetRateSequence(String sequenceStr, String quotaId) {
        ResultDto<String> rs = new ResultDto<String>();
        recruitBoardService.updatePositionMeetRateSequence(sequenceStr, quotaId, getUserEmpId(), getCustomerId());
        rs.setType(true);
        return rs;
    }

    /**
     * 招聘渠道统计
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRecruitChannel", method = RequestMethod.POST)
    public List<RecruitChannelResultDto> getRecruitChannel(String keyName, String organId) {
        if (StringUtils.isEmpty(organId)) return null;
        return recruitBoardService.queryRecruitChannel(keyName, organId, getCustomerId());
    }

    /**
     * 招聘渠道统计-离职率统计
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getProbationDismissionRate", method = RequestMethod.GET)
    public List<RecruitDismissionWeekDto> getProbationDismissionRate(Integer employNum, String channelId, String parent, String organId) {
        if (StringUtils.isEmpty(channelId)) return null;
        return recruitBoardService.queryDismissionRate(employNum, channelId, parent, organId, getCustomerId());
    }

    /**
     * 获取异动提醒统计
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRecruitChange", method = RequestMethod.GET)
    public List<RecruitJobChangeTotalDto> getRecruitChange(String organId) {
        if (StringUtils.isEmpty(organId)) return null;
        List<Integer> changeType = CollectionKit.newList();
        changeType.add(1);      //晋升/晋级
        changeType.add(4);      //调动/调出
        changeType.add(5);      //离职
        changeType.add(8);      //退休
        return recruitBoardService.queryRecruitChange(changeType, organId, getCustomerId());
    }

    /**
     * 获取异动提醒-员工列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRecruitChangeList", method = RequestMethod.GET)
    public PaginationDto<RecruitJobChangeDto> getRecruitChangeList(Integer changeType, String organId, Integer total, Integer page, Integer rows) {
        if (StringUtils.isEmpty(organId)) return null;
        PaginationDto<RecruitJobChangeDto> dto = new PaginationDto<RecruitJobChangeDto>(page, rows);
        dto.setRecords(total);  //总条数
        return recruitBoardService.queryRecruitChangeList(changeType, organId, getCustomerId(), dto);
    }

    /**
     * 获取岗位画像查询-绩效标签
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getImagesPerformatTags", method = RequestMethod.GET)
    public List<RecruitTagDto> getImagesPerformanceTags() {
        return recruitBoardService.queryImagesPerformanceTags(getCustomerId());
    }

    /**
     * 获取岗位画像查询标签
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getImagesQueryTags", method = RequestMethod.POST)
    public List<RecruitTagDto> getImagesQueryTags(String position) {
        if (StringUtils.isEmpty(position)) return null;
        return recruitBoardService.queryImagesQueryTags(position, getCustomerId());
    }

    /**
     * 获取某岗位绩效人群统计
     *
     * @param position    岗位
     * @param yearNum     年度
     * @param continueNum 次数
     * @param star        星级
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPositionPerfEmpCount", method = RequestMethod.POST)
    public int getPositionPerfEmpCount(String position, Integer yearNum, Integer continueNum, Integer star) {
        if (StringUtils.isEmpty(position)) return 0;
        return recruitBoardService.queryPositionPerfEmpCount(position, yearNum, continueNum, star, getCustomerId());
    }

    /**
     * 获取岗位画像标签
     *
     * @param position    岗位
     * @param yearNum     年度
     * @param continueNum 次数
     * @param star        星级
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPositionImages", method = RequestMethod.POST)
    public List<RecruitTagDto> getPositionImages(String position, Integer yearNum, Integer continueNum, Integer star) {
        if (StringUtils.isEmpty(position)) return null;
        return recruitBoardService.queryPositionImages(position, yearNum, continueNum, star, getCustomerId());
    }

    /**
     * 获取岗位推荐人群统计
     *
     * @param sex        性别 w or m
     * @param degreeId   学历ID
     * @param schoolType 学院类型
     * @param contentStr 优势标签 多个用逗号(,)分隔
     * @param qualityStr 能力素质集合 如:[{k:tagId v:tagVal},{k:tagId v:tagVal}]
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPositionRecommendCount", method = RequestMethod.POST)
    public int getPositionRecommendCount(String sex, String degreeId, String schoolType, String contentStr, String qualityStr) {
        List<String> contents = StringUtils.isEmpty(contentStr) ? null : Arrays.asList(contentStr.split(","));
        Gson gson = new Gson();
        List<KVItemDto> qualitys = gson.fromJson(qualityStr, new TypeToken<List<KVItemDto>>() {
        }.getType());

        int hasOrgan = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.ZPKB_recommend);
        List<String> organIds = null;
        if (hasOrgan == 1) organIds = getOrganPermitId();
        return recruitBoardService.queryPositionRecommendCount(sex, degreeId, schoolType, contents, qualitys, organIds, getCustomerId());
    }


    /**
     * 获取岗位推荐人群
     *
     * @param sex        性别 w or m
     * @param degreeId   学历ID
     * @param schoolType 学院类型
     * @param contentStr 优势标签 多个用逗号(,)分隔
     * @param qualityStr 能力素质集合 如:[{k:tagId v:tagVal},{k:tagId v:tagVal}]
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPositionRecommendEmp", method = RequestMethod.POST)
    public PaginationDto<RecruitRecommendEmpDto> getPositionRecommendEmp(String sex, String degreeId, String schoolType, String contentStr, String qualityStr, Integer page, Integer rows) {
        List<String> contents = StringUtils.isEmpty(contentStr) ? null : Arrays.asList(contentStr.split(","));
        Gson gson = new Gson();
        List<KVItemDto> qualitys = gson.fromJson(qualityStr, new TypeToken<List<KVItemDto>>() {
        }.getType());

        PaginationDto<RecruitRecommendEmpDto> dto = new PaginationDto<RecruitRecommendEmpDto>(page, rows);
        int hasOrgan = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.ZPKB_recommend);
        List<String> organIds = null;
        if (hasOrgan == 1) {
            organIds = getOrganPermitId();
        }
        return recruitBoardService.queryPositionRecommendEmp(sex, degreeId, schoolType, contents, qualitys, organIds, getCustomerId(), dto);
    }

    /**
     * 获取招聘岗位薪酬列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPositionPay", method = RequestMethod.POST)
    public List<RecruitPositionPayDto> getPositionPay(String keyName, String organId) {
        return recruitBoardService.queryPositionPay(keyName, organId, getCustomerId());
    }


}

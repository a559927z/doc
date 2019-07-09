package net.chinahrd.trainBoard.mvc.pc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainBoardDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainLecturerDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainRecordDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainTypeDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.trainBoard.mvc.pc.service.TrainBoardService;
import net.chinahrd.utils.CacheHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 培训看板
 * Created by wqcai on 16/3/29.
 */
@Controller
@RequestMapping("/trainBoard")
public class TrainBoardController extends BaseController {

    @Autowired
    private TrainBoardService trainBoardService;

    @RequestMapping(value = "/toTrainBoardView")
    public String toTrainBoardView(HttpServletRequest request) {
		// 改用缓存提取funId by jxzhang on 2016-11-10
        String functionId = CacheHelper.getFunctionId("trainBoard/toTrainBoardView");
        if(null !=functionId){request.setAttribute("quotaId", functionId); }
		
        return "biz/competency/trainBoard";
    }
     
    /**
     * 获取当前年度的培训费用以及预算率
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTrainCostYear", method = RequestMethod.GET)
    public TrainBoardDto getTrainCostYear(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getTrainCostYear(getCustomerId(), organId);
    }
    
    /**
     * 获取培训计划完成率
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTrainPlan", method = RequestMethod.GET)
    public TrainBoardDto getTrainPlan(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getTrainPlan(getCustomerId(), organId);
    }
    
    /**
     * 获取培训覆盖率
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTrainCover", method = RequestMethod.GET)
    public Map<String, Object> getTrainCover(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getTrainCover(getCustomerId(), organId);
    }
    
    /**
     * 培训费用统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTrainingCost", method = RequestMethod.GET)
    public Map<String, Object> getTrainingCost(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getTrainingCost(getCustomerId(), organId);
    }
    
    /**
     * 人均培训费用统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPerCapitaCost", method = RequestMethod.GET)
    public List<TrainBoardDto> getPerCapitaCost(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getPerCapitaCost(getCustomerId(), organId);
    }
    
    
    /**
     * 培训计划完成率
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPlanCompletion", method = RequestMethod.GET)
    public Map<String, Object>  getPlanCompletion(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getPlanCompletion(getCustomerId(), organId);
    }
    
    /**
     * 下级组织人均学时对比
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPerCapitaHours", method = RequestMethod.GET)
    public Map<String, Object>  getPerCapitaHours(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getPerCapitaHours(getCustomerId(), organId);
    }
    
    /**
     * 费用统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getExpenseStatistics", method = RequestMethod.GET)
    public Map<String, Object>  getExpenseStatistics(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getExpenseStatistics(getCustomerId(), organId);
    }
    
    /**
     * 费用年度趋势图
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getYearCost", method = RequestMethod.GET)
    public List<TrainBoardDto>  getYearCost(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getYearCost(getCustomerId(), organId);
    }
    
    /**
     * 下级组织培训费用对比
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSubOrganizationCost", method = RequestMethod.GET)
    public Map<String, Object>  getSubOrganizationCost(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getSubOrganizationCost(getCustomerId(), organId);
    }
    
    /**
     * 下级组织培训平均费用对比
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSubOrganizationAvgCost", method = RequestMethod.GET)
    public List<TrainBoardDto>  getSubOrganizationAvgCost(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getSubOrganizationAvgCost(getCustomerId(), organId);
    }
    
    /**
     * 实施统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getImplementation", method = RequestMethod.GET)
    public Map<String, Object>  getImplementation(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getImplementation(getCustomerId(), organId);
    }
    
    /**
     * 下级组织培训人次对比
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSubOrganizationPassengers", method = RequestMethod.GET)
    public Map<String, Object> getSubOrganizationPassengers(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getSubOrganizationPassengers(getCustomerId(), organId);
    }
    
    /**
     * 下级组织培训覆盖率对比
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSubOrganizationCover", method = RequestMethod.GET)
    public Map<String, Object> getSubOrganizationCover(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getSubOrganizationCover(getCustomerId(), organId);
    }
    
    /**
     * 培训类型次数统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTrainingType", method = RequestMethod.GET)
    public Map<String, Object>  getTrainingType(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getTrainingType(getCustomerId(), organId);
    }
    
    /**
     * 查询培训分类明细
     *
     * @param type
     * @param page
     * @param rows
     * @param sidx
     * @param sord
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findTrainingTypeRecord", method = RequestMethod.POST)
    public PaginationDto<TrainTypeDto> findTrainingTypeRecord(String type,String organId, Integer page, Integer rows, String sidx, String sord) {
        PaginationDto<TrainTypeDto> dto = new PaginationDto<TrainTypeDto>(page, rows);
        dto = trainBoardService.findTrainingTypeRecord(type,organId, dto, sidx, sord, getCustomerId());
        return dto;
    }
    
    /**
     * 培训满意度年度统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTrainingSatisfaction", method = RequestMethod.GET)
    public List<TrainBoardDto>  getTrainingSatisfaction(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getTrainingSatisfaction(getCustomerId(), organId);
    }
    
    /**
     * 下级组织内部讲师统计
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getInternalTrainer", method = RequestMethod.GET)
    public List<TrainBoardDto> getInternalTrainer(String organId) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        return trainBoardService.getInternalTrainer(getCustomerId(), organId);
    }
    
    /**
     * 内部讲师清单
     *
     * @param organId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getInternalTrainerList", method = RequestMethod.POST)
    public PaginationDto<TrainLecturerDto>  getInternalTrainerList(String organId,Integer page, Integer rows) {
        if (StringUtils.isEmpty(organId)) {
            return null;
        }
        PaginationDto<TrainLecturerDto> dto = new PaginationDto<TrainLecturerDto>(page, rows);
        return trainBoardService.getInternalTrainerList(getCustomerId(), organId, dto);
    }
    
    
    /**
     * 查询培训记录
     *
     * @param keyName
     * @param page
     * @param rows
     * @param sidx
     * @param sord
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findTrainingRecord", method = RequestMethod.POST)
    public PaginationDto<TrainRecordDto> findTrainingRecord(String keyName,String organId, Integer page, Integer rows, String sidx, String sord) {
        PaginationDto<TrainRecordDto> dto = new PaginationDto<TrainRecordDto>(page, rows);
        dto = trainBoardService.findTrainingRecord(keyName, organId, dto, sidx, sord, getCustomerId());
        return dto;
    }
}

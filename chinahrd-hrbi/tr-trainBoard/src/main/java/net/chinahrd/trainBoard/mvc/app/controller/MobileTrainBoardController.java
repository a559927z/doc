package net.chinahrd.trainBoard.mvc.app.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.trainBoard.TrainBoardDto;
import net.chinahrd.entity.dto.app.trainBoard.TrainRecordDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.trainBoard.mvc.app.service.MobileTrainBoardService;
import net.chinahrd.utils.CollectionKit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 培训看板 app
 * @author htpeng
 *2016年6月13日下午2:50:45
 */
@Controller
@RequestMapping("mobile/trainBoard")
public class MobileTrainBoardController extends BaseController {
	
	@Autowired
	private MobileTrainBoardService trainBoardService;
    
    @RequestMapping(value = "/totrainBoardView")
    public String toTalentStructureView(HttpServletRequest request) {
    	Object obj = request.getParameter("organId");
		List<OrganDto> organPermit = getOrganPermit();
		if (CollectionKit.isNotEmpty(organPermit)) {
			if (null == obj) {
				// 当前机构id
				OrganDto topOneOrgan = organPermit.get(0);
				request.setAttribute("reqOrganId",
						topOneOrgan.getOrganizationId());
				request.setAttribute("reqOrganName",
						topOneOrgan.getOrganizationName());
			} else {
				String orgId = obj.toString();
				request.setAttribute("reqOrganId", orgId);
				boolean bool = false;
				for (OrganDto topOneOrgan : organPermit) {
					if (topOneOrgan.getOrganizationId().equals(orgId)) {
						request.setAttribute("reqOrganName",
								topOneOrgan.getOrganizationName());
						bool = true;
						break;
					}
				}
				if (!bool) {
					request.setAttribute("reqOrganName", "没有找到");
				}
			}
		}
        return "mobile/trainBoard/trainBoard";
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
    public PaginationDto<TrainRecordDto> findTrainingRecord(String organId, Integer page, Integer rows, String sidx, String sord) {
        PaginationDto<TrainRecordDto> dto = new PaginationDto<TrainRecordDto>(page, rows);
        List<String> organPermitIds = getOrganPermitId();
        dto = trainBoardService.findTrainingRecord(organPermitIds, organId, dto, sidx, sord, getCustomerId());
        return dto;
    }


    
}

package net.chinahrd.talentProfile.mvc.app.controller;

import java.util.List;

import net.chinahrd.mvc.app.AppUserMapping;
import net.chinahrd.mvc.app.DataPacket;
import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.EvalReport4MobileDto;
import net.chinahrd.entity.dto.pc.JobChangeDto;
import net.chinahrd.entity.dto.pc.OtherReportDto;
import net.chinahrd.entity.dto.pc.emp.BonusPenaltyDto;
import net.chinahrd.entity.dto.pc.emp.EmpEduDto;
import net.chinahrd.entity.dto.pc.emp.EmpFamilyDto;
import net.chinahrd.entity.dto.pc.emp.EmpPastResumeDto;
import net.chinahrd.entity.dto.pc.emp.ProfTitleDto;
import net.chinahrd.entity.dto.pc.emp.TrainExperienceDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PerfChangeDto;
import net.chinahrd.mvc.app.controller.BaseController;
import net.chinahrd.mvc.pc.service.common.CommonService;
import net.chinahrd.talentProfile.mvc.pc.service.TalentProfileService;
import net.chinahrd.eis.permission.model.RbacUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mobile/empProfile")
public class MobileEmpProfileController extends BaseController {
	@Autowired
	private CommonService commonService;
	@Autowired
	private TalentProfileService talentProfileService;

	/**
	 * 查询员工信息
	 * 
	 * @param empId 
	 * @param token
	 * @return DataPacket
	 */
	@ResponseBody
	@RequestMapping(value = "/findEmpDetail", method = RequestMethod.GET)
	public DataPacket findEmpDetail(String empId, String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			EmpDetailDto empDto = talentProfileService.findEmpDetail(empId, getCustomerId());
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(empDto);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
	}
	 /**
     * 获取员工工作异动信息（多个员工id用“,”号隔开）
     *
     * @param empId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getJobChange")
    public DataPacket getJobChange(String empId, String token) {
        DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			 List<JobChangeDto> dtos = talentProfileService.queryJobChange(empId, getCustomerId(),null);
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(dtos);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
    }
	/**
	 * 获取员工绩效信息（多个员工id用“,”号隔开）
	 * 
	 * @param empId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getPerfChange")
	public DataPacket getPerfChange(String empId, String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<PerfChangeDto> dtos = talentProfileService.queryPerfChange(empId, getCustomerId());
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(dtos);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;

	}

	/**
	 * 获取员工360测评报告信息
	 * 
	 * @param empId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "get360Evaluation")
	public DataPacket get360Evaluation( String empId, String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<EvalReport4MobileDto> dtos = talentProfileService.query360Evaluation4Mobile(empId, getCustomerId());
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(dtos);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		
		return data;
	}

	/**
	 * 获取员工其它测评报告信息
	 * 
	 * @param empId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getOtherEvaluation")
	public DataPacket getOtherEvaluation( String empId, String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<OtherReportDto> dtos = talentProfileService.queryOtherEvaluation(empId, getCustomerId());
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(dtos);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
	}

	/**
	 * 获取员工奖惩信息
	 * 
	 * @param empId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getBonusData")
	public DataPacket getBonusData( String empId, String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<BonusPenaltyDto> dtos = talentProfileService.getBonusData(empId, getCustomerId());
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(dtos);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
	}

	/**
	 * 获取员工培训经历信息
	 * 
	 * @param empId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getTrainExp")
	public DataPacket getTrainExp(String empId, String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<TrainExperienceDto> dtos = talentProfileService.getTrainExp(empId, getCustomerId());
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(dtos);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		
		return data;
	}

	/**
	 * 获取员工过往履历信息 （多个员工id用“,”号隔开）
	 * 
	 * @param empId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getPastResume")
	public DataPacket getPastResume(String empId, String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<EmpPastResumeDto> dtos = talentProfileService.getPastResume(empId, getCustomerId());
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(dtos);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		
		return data;
	}

	/**
	 * 获取员工教育背景信息
	 * 
	 * @param empId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getEduBg")
	public DataPacket getEduBg(String empId, String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<EmpEduDto> dtos = talentProfileService.getEduBg(empId, getCustomerId());
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(dtos);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
	}

	/**
	 * 获取员工所获职称信息
	 * 
	 * @param empId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getProfTitle")
	public DataPacket getProfTitle(String empId, String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<ProfTitleDto> dtos = talentProfileService.getProfTitle(empId, getCustomerId());
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(dtos);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
	}

	/**
	 * 获取员工家庭关系信息
	 * 
	 * @param empId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getFamilyData")
	public DataPacket getFamilyData(String empId, String token) {
		DataPacket data = new DataPacket();
		RbacUser user = AppUserMapping.getUserByToken(token);
		if (user != null) {
			List<EmpFamilyDto> dtos = talentProfileService.getFamilyData(empId, getCustomerId());
			data.setMessage(DataPacket.MSG_SUCCESS);
			data.setCode(DataPacket.CODE_SUCCESS);
			data.setData(dtos);
		} else {
			data.setMessage(DataPacket.MSG_INVALID_TOKEN);
			data.setCode(DataPacket.CODE_INVALID_TOKEN);
			data.setData("");
		}
		return data;
	}

}

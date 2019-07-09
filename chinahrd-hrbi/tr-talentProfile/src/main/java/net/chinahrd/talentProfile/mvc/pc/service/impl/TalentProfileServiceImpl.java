package net.chinahrd.talentProfile.mvc.pc.service.impl;

import java.util.List;

import net.chinahrd.entity.dto.pc.EmpDetailDto;
import net.chinahrd.entity.dto.pc.EvalReport4MobileDto;
import net.chinahrd.entity.dto.pc.EvalReportDto;
import net.chinahrd.entity.dto.pc.JobChangeDto;
import net.chinahrd.entity.dto.pc.OtherReportDto;
import net.chinahrd.entity.dto.pc.emp.BonusPenaltyDto;
import net.chinahrd.entity.dto.pc.emp.EmpEduDto;
import net.chinahrd.entity.dto.pc.emp.EmpFamilyDto;
import net.chinahrd.entity.dto.pc.emp.EmpPastResumeDto;
import net.chinahrd.entity.dto.pc.emp.ProfTitleDto;
import net.chinahrd.entity.dto.pc.emp.TrainExperienceDto;
import net.chinahrd.entity.dto.pc.employeePerformance.PerfChangeDto;
import net.chinahrd.mvc.pc.dao.admin.OrganDao;
import net.chinahrd.talentProfile.mvc.pc.dao.TalentProfileDao;
import net.chinahrd.talentProfile.mvc.pc.service.TalentProfileService;
import net.chinahrd.utils.CollectionKit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 人才剖像Service实现类
 * Created by wqcai on 15/10/14 0014.
 */
@Service("talentProfileService")
public class TalentProfileServiceImpl implements TalentProfileService {

    @Autowired
    private TalentProfileDao talentProfileDao;
    @Autowired
    private OrganDao organDao;

    @Override
    public List<JobChangeDto> queryJobChange(String empId, String customerId,Integer changeType) {
        List<String> empIds = CollectionKit.strToList(empId);
        return talentProfileDao.queryJobChange(empIds, customerId,changeType);
    }

    @Override
    public List<PerfChangeDto> queryPerfChange(String empId, String customerId) {
        List<String> empIds = CollectionKit.strToList(empId);
        return talentProfileDao.queryPerfChange(empIds, customerId, empIds.size()*6);
    }

    @Override
    public List<EvalReportDto> query360Evaluation(String empId, String customerId) {
        return talentProfileDao.query360Evaluation(empId, customerId);
    }

    @Override
    public List<OtherReportDto> queryOtherEvaluation(String empId, String customerId) {
        return talentProfileDao.queryOtherEvaluation(empId, customerId);
    }

    @Override
    public EmpDetailDto findEmpDetail(String empId, String customerId) {
        return talentProfileDao.findEmpDetail(empId, customerId);
    }

    @Override
    public List<BonusPenaltyDto> getBonusData(String empId, String customerId) {
        return talentProfileDao.queryBonusData(empId, customerId);
    }

    @Override
    public List<TrainExperienceDto> getTrainExp(String empId, String customerId) {
        return talentProfileDao.queryTrainExp(empId, customerId);
    }

    @Override
    public List<EmpPastResumeDto> getPastResume(String empId, String customerId) {
        List<String> empIds = CollectionKit.strToList(empId);
        return talentProfileDao.queryPastResume(empIds, customerId);
    }

    @Override
    public List<EmpEduDto> getEduBg(String empId, String customerId) {
        return talentProfileDao.queryEduBg(empId, customerId);
    }

    @Override
    public List<ProfTitleDto> getProfTitle(String empId, String customerId) {
        return talentProfileDao.queryProfTitle(empId, customerId);
    }

    @Override
    public List<EmpFamilyDto> getFamilyData(String empId, String customerId) {
        return talentProfileDao.queryFamilyData(empId, customerId);
    }

	@Override
	public List<EvalReport4MobileDto> query360Evaluation4Mobile(String empId,
			String customerId) {
		return talentProfileDao.query360Evaluation4Mobile(empId, customerId);
	}

}

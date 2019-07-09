package net.chinahrd.trainBoard.mvc.pc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainBoardDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainLecturerDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainRecordDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainTypeDto;
import net.chinahrd.trainBoard.mvc.pc.dao.TrainBoardDao;
import net.chinahrd.trainBoard.mvc.pc.service.TrainBoardService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.DateUtil;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 培训看板 实现
 *
 * @author qpzhu by 2016-03-29
 */
@Service("trainBoardService")
public class TrainBoardServiceImpl implements TrainBoardService {

    private final String YEAR_BEGIN_SUFFIX = ".1.1";
    private final String YEAR_BEGIN_SUFFIXMD = "年1月1日";

    @Autowired
    TrainBoardDao trainBoardDao;

    /**
     * 培训费用以及预算率
     */
    @Override
    public TrainBoardDto getTrainCostYear(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        TrainBoardDto dto = trainBoardDao.queryTrainCostYear(customerId, organId, year);
        if (dto == null) {
            dto = new TrainBoardDto();
            dto.setYear(year);
        }
        return dto;
    }

    /**
     * 培训计划完成率
     */
    @Override
    public TrainBoardDto getTrainPlan(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        TrainBoardDto dto = trainBoardDao.queryTrainPlan(customerId, organId, year);
        if (dto == null) {
            dto = new TrainBoardDto();
            dto.setYear(year);
        }
        return dto;
    }

    /**
     * 培训覆盖率
     */
    @Override
    public Map<String, Object> getTrainCover(String customerId, String organId) {
        int empCount = trainBoardDao.queryEmpCount(customerId, organId);
        String year = DateUtil.getDBNow().substring(0, 4);
        TrainBoardDto dto = trainBoardDao.queryTrainCover(customerId, organId, empCount, year);
        Map<String, Object> map = CollectionKit.newMap();
        map.put("year", year);
        map.put("result", dto != null ? dto.getCoverageRate() : null);
        return map;
    }

    /**
     * 培训费用月度/季度
     */
    @Override
    public Map<String, Object> getTrainingCost(String customerId, String organId) {
        int type = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.PXKB_outlay);
        String year = DateUtil.getDBNow().substring(0, 4);
        TrainBoardDto dto = trainBoardDao.queryTrainCostYear(customerId, organId, year);
        List<TrainBoardDto> list = new ArrayList<TrainBoardDto>();
        if (type == 1) {
            list = trainBoardDao.queryTrainMonthCostList(customerId, organId, year);
        } else {
            list = trainBoardDao.queryTrainQuarterCostList(customerId, organId, year);
        }

        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("budgetCost", dto != null ? dto.getBudgetCost() : null);
        rs.put("cost", dto != null ? dto.getCost() : null);
        rs.put("list", list);
        return rs;
    }

    /**
     * 人均培训费用月度
     */
    @Override
    public List<TrainBoardDto> getPerCapitaCost(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<TrainBoardDto> list = trainBoardDao.queryTrainMonthAvgCostList(customerId, organId, year);
        return list;
    }

    /**
     * 培训计划完成率月度
     */
    @Override
    public Map<String, Object> getPlanCompletion(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        TrainBoardDto dto = trainBoardDao.queryTrainPlan(customerId, organId, year);
        List<TrainBoardDto> list = trainBoardDao.queryTrainMonthPlanList(customerId, organId, year);
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("cumulative", dto != null ? dto.getCompleteRate() : null);
        rs.put("list", list);
        return rs;
    }

    /**
     * 下级组织人均学时
     */
    @Override
    public Map<String, Object> getPerCapitaHours(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        Map<String, Object> rs = CollectionKit.newMap();
        List<TrainBoardDto> list = trainBoardDao.queryPerCapitaHours(customerId, organId, year);
        rs.put("beginDate", year +YEAR_BEGIN_SUFFIXMD);
        rs.put("endDate", DateUtil.getDBNowYMD());
        rs.put("list", list);
        return rs;
    }

    /**
     * 费用统计
     */
    @Override
    public Map<String, Object> getExpenseStatistics(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        //查询培训总费用
        Double trainTotal = trainBoardDao.queryTrainTotal(customerId, organId, year);
        //查询人力成本总费用
        Double costTotal = trainBoardDao.queryCostTotal(customerId, organId, year);
        //培训人数
        int trainNum = trainBoardDao.queryTrainNum(customerId, organId, year);

        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("year", year + "年");
        if (trainTotal == null || costTotal == null) {
            rs.put("proportion", null);
        } else {
            rs.put("proportion", trainTotal / costTotal);
        }
        rs.put("avgCost", trainTotal == null ? null : trainTotal / trainNum);
        return rs;
    }

    /**
     * 年度费用
     */
    @Override
    public List<TrainBoardDto> getYearCost(String customerId, String organId) {
        List<TrainBoardDto> list = trainBoardDao.queryYearCost(customerId, organId);
        return list;
    }

    /**
     * 下级组织培训费用
     */
    @Override
    public Map<String, Object> getSubOrganizationCost(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<TrainBoardDto> list = trainBoardDao.querySubOrganizationCost(customerId, organId, year);
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("beginDate", year + YEAR_BEGIN_SUFFIX);
        rs.put("endDate", DateUtil.getDBNow().substring(0, 10).replaceAll("-", "."));
        rs.put("list", list);
        return rs;
    }

    /**
     * 下级组织培训人均费用
     */
    @Override
    public List<TrainBoardDto> getSubOrganizationAvgCost(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<TrainBoardDto> list = trainBoardDao.querySubOrganizationAvgCost(customerId, organId, year);
        return list;
    }

    /**
     * 实施统计
     */
    @Override
    public Map<String, Object> getImplementation(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        //培训人次
        int trainPassengers = trainBoardDao.queryTrainPassengers(customerId, organId, year);
        //培训时间
        Double trainTime = trainBoardDao.queryTrainTime(customerId, organId, year);
        //培训人数
        int trainEmpCount = trainBoardDao.queryTrainNum(customerId, organId, year);
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("year", year + "年");
        rs.put("trainPassengers", trainPassengers);
        rs.put("manpower", null != trainTime ? trainTime / trainEmpCount : null);
        return rs;
    }

    /**
     * 下级组织培训人次
     */
    @Override
    public Map<String, Object> getSubOrganizationPassengers(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<TrainBoardDto> list = trainBoardDao.querySubOrganizationPassengers(customerId, organId, year);
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("beginDate", year + YEAR_BEGIN_SUFFIX);
        rs.put("endDate", DateUtil.getDBNow().substring(0, 10).replaceAll("-", "."));
        rs.put("list", list);
        return rs;
    }

    /**
     * 下级组织培训覆盖率
     */
    @Override
    public Map<String, Object> getSubOrganizationCover(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        //查询下级组织
        List<TrainBoardDto> orgList = trainBoardDao.querySubOrganization(customerId, organId);
        for (int i = 0; i < orgList.size(); i++) {
            TrainBoardDto dto = orgList.get(i);
            int empCount = trainBoardDao.queryEmpCount(customerId, dto.getOrganizationId());
            TrainBoardDto dto1 = trainBoardDao.queryTrainCover(customerId, dto.getOrganizationId(), empCount, year);
            dto.setCoverageRate(dto1.getCoverageRate());
        }
        //查询公司组织的覆盖率
        TrainBoardDto companyDto = trainBoardDao.queryCompanyOrganization(customerId);
        int companyCount = trainBoardDao.queryEmpCount(customerId, companyDto.getOrganizationId());
        TrainBoardDto dto2 = trainBoardDao.queryTrainCover(customerId, companyDto.getOrganizationId(), companyCount, year);

        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("beginDate", year + YEAR_BEGIN_SUFFIX);
        rs.put("endDate", DateUtil.getDBNow().substring(0, 10).replaceAll("-", "."));
        rs.put("list", orgList);
        //将公司级组织加入map
        rs.put("companyCover", dto2 != null ? dto2.getCoverageRate() : 0d);
        return rs;
    }

    /**
     * 培训类型次数
     */
    @Override
    public Map<String, Object> getTrainingType(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<TrainBoardDto> list = trainBoardDao.queryTrainingType(customerId, organId, year);
        Map<String, Object> rs = CollectionKit.newMap();
        rs.put("year", year + "年");
        rs.put("list", list);
        return rs;
    }

    /**
     * 培训满意度年度
     */
    @Override
    public List<TrainBoardDto> getTrainingSatisfaction(String customerId, String organId) {
        List<TrainBoardDto> list = trainBoardDao.queryTrainingSatisfaction(customerId, organId);
        return list;
    }

    /**
     * 下级组织内部讲师统计
     */
    @Override
    public List<TrainBoardDto> getInternalTrainer(String customerId, String organId) {
        List<TrainBoardDto> list = trainBoardDao.queryInternalTrainer(customerId, organId);
        return list;
    }

    /**
     * 内部讲师清单
     */
    @Override
    public PaginationDto<TrainLecturerDto> getInternalTrainerList(String customerId, String organId, PaginationDto<TrainLecturerDto> dto) {
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        int count = trainBoardDao.queryInternalTrainerCount(customerId, organId);
        List<TrainLecturerDto> list = trainBoardDao.queryInternalTrainerList(customerId, organId, rowBounds);
        dto.setRecords(count);
        dto.setRows(list);
        return dto;
    }

    /**
     * 培训记录分页查询
     */
    @Override
    public PaginationDto<TrainRecordDto> findTrainingRecord(String keyName, String organId, PaginationDto<TrainRecordDto> dto, String sidx, String sord, String customerId) {
        List<String> organPermitIds = getOrganPermitId();
        if (null == organPermitIds || organPermitIds.isEmpty()) {
            return null;
        }
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        int count = trainBoardDao.findTrainingRecordCount(organId, keyName, customerId, organPermitIds);
        List<TrainRecordDto> dtos = trainBoardDao.findTrainingRecord(organPermitIds, organId, keyName, sidx, sord, customerId, rowBounds);

        dto.setRecords(count);
        dto.setRows(dtos);
        return dto;
    }

    /**
     * 培训类型明细查询
     */
    @Override
    public PaginationDto<TrainTypeDto> findTrainingTypeRecord(String type, String organId, PaginationDto<TrainTypeDto> dto,
                                                              String sidx, String sord, String customerId) {
        int empCount = trainBoardDao.queryEmpCount(customerId, organId);
        String year = DateUtil.getDBNow().substring(0, 4);
        Map<String, Object> map = CollectionKit.newMap();
        map.put("empCount", empCount);
        map.put("year", year);
        map.put("type", type);
        map.put("organId", organId);
        map.put("customerId", customerId);
        int count = trainBoardDao.findTrainingTypeRecordCount(map);
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        map.put("sidx", sidx);
        map.put("sord", sord);
        List<TrainTypeDto> dtos = trainBoardDao.findTrainingTypeRecord(map, rowBounds);

        dto.setRecords(count);
        dto.setRows(dtos);
        return dto;
    }
    
    public List<TrainTypeDto> findTrainingTypeRecordNoPage(String type, String organId, String customerId) {
    	String year = DateUtil.getDBNow().substring(0, 4);
        Map<String, Object> map = CollectionKit.newMap();
        map.put("year", year);
        map.put("type", type);
        map.put("organId", organId);
        map.put("customerId", customerId);
    	List<TrainTypeDto> dtos = trainBoardDao.findTrainingTypeRecordNoPage(map);
    	return dtos;
    }

    /**
     * 登录人所有数据权限ID集	by jxzhang
     *
     * @return
     */
    private List<String> getOrganPermitId() {
        List<OrganDto> organPermit = EisWebContext.getCurrentUser().getOrganPermit();
        if (null == organPermit) {
            return null;
        }
        List<String> rs = CollectionKit.newList();
        for (OrganDto organDto : organPermit) {
            rs.add(organDto.getOrganizationId());
        }
        return rs;
    }

}

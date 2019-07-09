package net.chinahrd.trainBoard.mvc.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.app.trainBoard.TrainBoardDto;
import net.chinahrd.entity.dto.app.trainBoard.TrainLecturerDto;
import net.chinahrd.entity.dto.app.trainBoard.TrainRecordDto;
import net.chinahrd.entity.dto.app.trainBoard.TrainTypeDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.trainBoard.mvc.app.dao.MobileTrainBoardDao;
import net.chinahrd.trainBoard.mvc.app.service.MobileTrainBoardService;
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
@Service("mobileTrainBoardService")
public class MobileTrainBoardServiceImpl implements MobileTrainBoardService {

    private final String YEAR_BEGIN_SUFFIX = ".1.1";

    @Autowired
    MobileTrainBoardDao mobileTrainBoardDao;

    /**
     * 培训费用以及预算率
     */
    @Override
    public TrainBoardDto getTrainCostYear(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        TrainBoardDto dto = mobileTrainBoardDao.queryTrainCostYear(customerId, organId, year);
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
        TrainBoardDto dto = mobileTrainBoardDao.queryTrainPlan(customerId, organId, year);
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
        int empCount = mobileTrainBoardDao.queryEmpCount(customerId, organId);
        String year = DateUtil.getDBNow().substring(0, 4);
        TrainBoardDto dto = mobileTrainBoardDao.queryTrainCover(customerId, organId, empCount, year);
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
        TrainBoardDto dto = mobileTrainBoardDao.queryTrainCostYear(customerId, organId, year);
        List<TrainBoardDto> list = new ArrayList<TrainBoardDto>();
        if (type == 1) {
            list = mobileTrainBoardDao.queryTrainMonthCostList(customerId, organId, year);
        } else {
            list = mobileTrainBoardDao.queryTrainQuarterCostList(customerId, organId, year);
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
        List<TrainBoardDto> list = mobileTrainBoardDao.queryTrainMonthAvgCostList(customerId, organId, year);
        return list;
    }

    /**
     * 培训计划完成率月度
     */
    @Override
    public Map<String, Object> getPlanCompletion(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        TrainBoardDto dto = mobileTrainBoardDao.queryTrainPlan(customerId, organId, year);
        List<TrainBoardDto> list = mobileTrainBoardDao.queryTrainMonthPlanList(customerId, organId, year);
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
        List<TrainBoardDto> list = mobileTrainBoardDao.queryPerCapitaHours(customerId, organId, year);
        rs.put("beginDate", year + YEAR_BEGIN_SUFFIX);
        rs.put("endDate", DateUtil.getDBNow().substring(0, 10).replaceAll("-", "."));
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
        Double trainTotal = mobileTrainBoardDao.queryTrainTotal(customerId, organId, year);
        //查询人力成本总费用
        Double costTotal = mobileTrainBoardDao.queryCostTotal(customerId, organId, year);
        //培训人数
        int trainNum = mobileTrainBoardDao.queryTrainNum(customerId, organId, year);

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
        List<TrainBoardDto> list = mobileTrainBoardDao.queryYearCost(customerId, organId);
        return list;
    }

    /**
     * 下级组织培训费用
     */
    @Override
    public Map<String, Object> getSubOrganizationCost(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        List<TrainBoardDto> list = mobileTrainBoardDao.querySubOrganizationCost(customerId, organId, year);
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
        List<TrainBoardDto> list = mobileTrainBoardDao.querySubOrganizationAvgCost(customerId, organId, year);
        return list;
    }

    /**
     * 实施统计
     */
    @Override
    public Map<String, Object> getImplementation(String customerId, String organId) {
        String year = DateUtil.getDBNow().substring(0, 4);
        //培训人次
        int trainPassengers = mobileTrainBoardDao.queryTrainPassengers(customerId, organId, year);
        //培训时间
        Double trainTime = mobileTrainBoardDao.queryTrainTime(customerId, organId, year);
        //培训人数
        int trainEmpCount = mobileTrainBoardDao.queryTrainNum(customerId, organId, year);
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
        List<TrainBoardDto> list = mobileTrainBoardDao.querySubOrganizationPassengers(customerId, organId, year);
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
        List<TrainBoardDto> orgList = mobileTrainBoardDao.querySubOrganization(customerId, organId);
        for (int i = 0; i < orgList.size(); i++) {
            TrainBoardDto dto = orgList.get(i);
            int empCount = mobileTrainBoardDao.queryEmpCount(customerId, dto.getOrganizationId());
            TrainBoardDto dto1 = mobileTrainBoardDao.queryTrainCover(customerId, dto.getOrganizationId(), empCount, year);
            dto.setCoverageRate(dto1.getCoverageRate());
        }
        //查询公司组织的覆盖率
        TrainBoardDto companyDto = mobileTrainBoardDao.queryCompanyOrganization(customerId);
        int companyCount = mobileTrainBoardDao.queryEmpCount(customerId, companyDto.getOrganizationId());
        TrainBoardDto dto2 = mobileTrainBoardDao.queryTrainCover(customerId, companyDto.getOrganizationId(), companyCount, year);

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
        List<TrainBoardDto> list = mobileTrainBoardDao.queryTrainingType(customerId, organId, year);
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
        List<TrainBoardDto> list = mobileTrainBoardDao.queryTrainingSatisfaction(customerId, organId);
        return list;
    }

    /**
     * 下级组织内部讲师统计
     */
    @Override
    public List<TrainBoardDto> getInternalTrainer(String customerId, String organId) {
        List<TrainBoardDto> list = mobileTrainBoardDao.queryInternalTrainer(customerId, organId);
        return list;
    }

    /**
     * 内部讲师清单
     */
    @Override
    public PaginationDto<TrainLecturerDto> getInternalTrainerList(String customerId, String organId, PaginationDto<TrainLecturerDto> dto) {
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        int count = mobileTrainBoardDao.queryInternalTrainerCount(customerId, organId);
        List<TrainLecturerDto> list = mobileTrainBoardDao.queryInternalTrainerList(customerId, organId, rowBounds);
        dto.setRecords(count);
        dto.setRows(list);
        return dto;
    }

    /**
     * 培训记录分页查询
     */
    @Override
    public PaginationDto<TrainRecordDto> findTrainingRecord(  List<String> organPermitIds, String organId, PaginationDto<TrainRecordDto> dto, String sidx, String sord, String customerId) {

        if (null == organPermitIds || organPermitIds.isEmpty()) {
            return null;
        }
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        int count = mobileTrainBoardDao.findTrainingRecordCount(organId, null,customerId, organPermitIds);
        List<TrainRecordDto> dtos = mobileTrainBoardDao.findTrainingRecord(organPermitIds, organId, null, sidx, sord, customerId, rowBounds);

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
        int empCount = mobileTrainBoardDao.queryEmpCount(customerId, organId);
        String year = DateUtil.getDBNow().substring(0, 4);
        Map<String, Object> map = CollectionKit.newMap();
        map.put("empCount", empCount);
        map.put("year", year);
        map.put("type", type);
        map.put("organId", organId);
        map.put("customerId", customerId);
        int count = mobileTrainBoardDao.findTrainingTypeRecordCount(map);
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        map.put("sidx", sidx);
        map.put("sord", sord);
        List<TrainTypeDto> dtos = mobileTrainBoardDao.findTrainingTypeRecord(map, rowBounds);

        dto.setRecords(count);
        dto.setRows(dtos);
        return dto;
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

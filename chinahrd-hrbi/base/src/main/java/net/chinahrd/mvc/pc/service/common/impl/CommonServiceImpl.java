package net.chinahrd.mvc.pc.service.common.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.pc.common.EmpDto;
import net.chinahrd.entity.dto.pc.common.ItemDto;
import net.chinahrd.entity.dto.pc.common.RiskDto;
import net.chinahrd.entity.dto.pc.common.RiskItemDto;
import net.chinahrd.entity.dto.pc.laborEfficiency.TheoryAttendanceDto;
import net.chinahrd.entity.enums.AgeIntervalEnum;
import net.chinahrd.entity.enums.ProcedureInfoEnum;
import net.chinahrd.entity.enums.TipsEnum;
import net.chinahrd.mvc.pc.dao.common.CommonDao;
import net.chinahrd.mvc.pc.service.common.CommonService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;
import net.chinahrd.utils.holiday.DaysDto;
import net.chinahrd.utils.holiday.EasybotsGetDays;
import net.chinahrd.utils.holiday.GetDays;

/**
 * 公共Service实现类
 */
@Service("commonService")
@Transactional
public class CommonServiceImpl implements CommonService {

    private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private CommonDao commonDao;

    @Override
    public List<KVItemDto> queryPopulations(String customerId) {
        return commonDao.queryPopulations(customerId);
    }

    @Override
    public List<ItemDto> getTalentType(String customerId) {

        return commonDao.queryTalentType(customerId);
    }

    @Override
    public List<ItemDto> getSequence(String customerId) {
        return commonDao.querySequence(customerId);
    }

    @Override
    public List<ItemDto> queryAbilityType(String customerId) {
        return commonDao.queryAbilityType(customerId);
    }

    @Override
    public List<KVItemDto> queryPositions(String positionName, String customerId) {
        return commonDao.queryPositions(positionName, customerId);
    }

    @Override
    public List<ItemDto> getPerformance(String customerId) {
        return commonDao.queryPerformance(customerId);
    }

    @Override
    public String getPerformanceStr(String customerId) {
        List<ItemDto> performanceList = this.getPerformance(customerId);
        return CollectionKit.extractToString(performanceList, "name", ",");
    }

    @Override
    public String queryPerformanceMaxDate(String customerId) {
        List<ItemDto> itemDtos = commonDao.queryPerformanceDate(customerId);
        if (itemDtos != null && !itemDtos.isEmpty()) {
            return itemDtos.get(0).getName();
        }
        return DateUtil.convertToIntYearMonth(DateUtil.getDate()) + "";
    }

    @Override
    public List<ItemDto> getSearchBox(String customerId) {
        List<ItemDto> seqList = commonDao.querySequence(customerId);
        List<ItemDto> ablList = commonDao.queryAbility(customerId);
        List<ItemDto> talentTypeList = commonDao.queryTalentType(customerId);
        List<ItemDto> perforList = commonDao.queryPerformance(customerId);
        List<ItemDto> keyTypeList = getKeyTypeList();
        List<ItemDto> dismList = getDismissTypeList();
        // 这些id的命名需对应属性名，用于页面上条件筛选时做匹配。具体属性名可见AccordDismissDto
        ItemDto dismDto = new ItemDto("roType", "流失类型", dismList);
        ItemDto seqDto = new ItemDto("sequenceId", "职位序列", seqList);
        seqDto.setBindObj("abilityId");
        ItemDto ablDto = new ItemDto("abilityId", "职位层级", ablList);
        // ItemDto keyTypeDto = new ItemDto("isKeyTalent","人才分类",keyTypeList);
        ItemDto perforDto = new ItemDto("performanceKey", "绩效", perforList);
        talentTypeList.add(new ItemDto("-1", "非关键人才"));
        ItemDto ttDto = new ItemDto("keyTalentTypeId", "人员分类", talentTypeList);
        List<ItemDto> rs = CollectionKit.newList();
        rs.add(dismDto);
        rs.add(seqDto);
        rs.add(ablDto);
        // rs.add(keyTypeDto);
        rs.add(ttDto);
        rs.add(perforDto);
        return rs;
    }

    @Override
    public List<ItemDto> getTalentMapsSearchBox(String customerId) {
        // 年龄
        List<ItemDto> ageList = new ArrayList<ItemDto>();
        for (AgeIntervalEnum age : AgeIntervalEnum.values()) {
            ItemDto dto = new ItemDto(age.toString(), age.getDesc());
            ageList.add(dto);
        }
        // 性别
        List<ItemDto> sexList = new ArrayList<ItemDto>();
        sexList.add(new ItemDto("m", "男"));
        sexList.add(new ItemDto("w", "女"));
        // 学历
        List<ItemDto> eduList = commonDao.queryEdu(customerId);
        for (ItemDto dto : eduList) { // 人才地图直接使用ID
            dto.setId(dto.getBindObj());
        }
        // 主序列
        List<ItemDto> seqList = commonDao.querySequence(customerId);
        // 子序列
        List<ItemDto> seqSubList = commonDao.querySequenceSub(customerId);
        // 层级
        List<ItemDto> ablList = commonDao.queryAbility(customerId);
        // 绩效
        List<ItemDto> perforList = commonDao.queryPerformance(customerId);
        for (ItemDto dto : perforList) { // 人才地图直接使用ID
            dto.setId(dto.getBindObj());
        }
        ItemDto seqDto = new ItemDto("sequenceId", "职位序列", seqList);
        // seqDto.setBindObj("abilityId");
        ItemDto seqSubDto = new ItemDto("sequenceSubId", "子序列", seqSubList);
        ItemDto ablDto = new ItemDto("abilityId", "职位层级", ablList);
        ItemDto perforDto = new ItemDto("performanceKey", "最新绩效", perforList);
        ItemDto ageDto = new ItemDto("ageId", "年龄", ageList);
        ItemDto sexDto = new ItemDto("sexId", "性别", sexList);
        ItemDto degreeDto = new ItemDto("eduId", "学历", eduList);
        List<ItemDto> rs = CollectionKit.newList();
        rs.add(seqDto);
        rs.add(seqSubDto);
        rs.add(ablDto);
        rs.add(perforDto);
        rs.add(ageDto);
        rs.add(sexDto);
        rs.add(degreeDto);
        return rs;
    }

    @Override
    public List<ItemDto> getTalentSearchBox(String customerId) {
        // 员工类型
        List<ItemDto> empTypeList = new ArrayList<ItemDto>();
        empTypeList.add(new ItemDto("type1", "正式员工"));
        empTypeList.add(new ItemDto("type2", "兼职员工"));
        // 年龄
        List<ItemDto> ageList = new ArrayList<ItemDto>();
        ageList.add(new ItemDto("age1", "23岁以下"));
        ageList.add(new ItemDto("age2", "23-28岁"));
        ageList.add(new ItemDto("age3", "29-35岁"));
        ageList.add(new ItemDto("age4", "36-45岁"));
        ageList.add(new ItemDto("age5", "46-55岁"));
        ageList.add(new ItemDto("age6", "55岁以上"));
        // 性别
        List<ItemDto> sexList = new ArrayList<ItemDto>();
        sexList.add(new ItemDto("sex1", "男"));
        sexList.add(new ItemDto("sex2", "女"));
        // 学历
        List<ItemDto> eduList = commonDao.queryEdu(customerId);
        // 主序列
        List<ItemDto> seqList = commonDao.querySequence(customerId);
        // 子序列
        List<ItemDto> seqSubList = commonDao.querySequenceSub(customerId);
        // 层级
        List<ItemDto> ablList = commonDao.queryAbility(customerId);
        // 职衔
        List<ItemDto> JobTitleList = commonDao.queryJobTitle(customerId);
        // 绩效时间
        List<ItemDto> perforListDate = commonDao.queryPerformanceDate(customerId);
        List<ItemDto> perforListYear = getListWithDup(perforListDate);
        // 绩效
        List<ItemDto> perforList = commonDao.queryPerformance(customerId);

        // 这些id的命名需对应属性名，用于页面上条件筛选时做匹配。具体属性名可见AccordDismissDto
        ItemDto empTypeDto = new ItemDto("empTypeId", "员工性质", empTypeList);
        ItemDto ageDto = new ItemDto("ageId", "年龄", ageList);
        ItemDto sexDto = new ItemDto("sexId", "性别", sexList);
        ItemDto eduDto = new ItemDto("eduId", "学历", eduList);
        ItemDto seqDto = new ItemDto("sequenceId", "职位主序列", seqList);
        ItemDto seqSubDto = new ItemDto("sequenceSubId", "职位子序列", seqSubList);
        ItemDto ablDto = new ItemDto("abilityId", "职位层级", ablList);
        ItemDto JobTitleDto = new ItemDto("JobTitleId", "职衔", JobTitleList);
        ItemDto perforYearDto = new ItemDto("performanceYearKey", "绩效年份", perforListYear);
        ItemDto perforDateDto = new ItemDto("performanceDateKey", "绩效时间", perforListDate);
        ItemDto perforDto = new ItemDto("performanceKey", "绩效排名", perforList);

        List<ItemDto> rs = CollectionKit.newList();
        rs.add(empTypeDto);
        rs.add(ageDto);
        rs.add(sexDto);
        rs.add(eduDto);
        rs.add(seqDto);
        rs.add(seqSubDto);
        rs.add(ablDto);
        rs.add(JobTitleDto);
        rs.add(perforYearDto);
        rs.add(perforDateDto);
        rs.add(perforDto);
        return rs;
    }

    /**
     * 绩效变化页面 筛选条件
     *
     * @param customerId
     * @return
     */
    public List<ItemDto> getSearchBoxPerChange(String customerId) {
        List<ItemDto> seqList = commonDao.querySequence(customerId);
        List<ItemDto> ablList = commonDao.queryAbility(customerId);
        List<ItemDto> perforList = commonDao.queryPerformance(customerId);
        // 这些id的命名需对应属性名，用于页面上条件筛选时做匹配。具体属性名可见AccordDismissDto
        ItemDto seqDto = new ItemDto("sequenceId", "职位序列", seqList);
        ItemDto ablDto = new ItemDto("abilityId", "职位层级", ablList);
        seqDto.setBindObj("abilityId");
        ItemDto perforDto = new ItemDto("performanceKey", "当期绩效", perforList);
        List<ItemDto> rs = CollectionKit.newList();
        rs.add(seqDto);
        rs.add(ablDto);
        rs.add(perforDto);
        return rs;
    }

    /**
     * 获取关键人才类型
     */
    private List<ItemDto> getKeyTypeList() {
        return TipsEnum.getCollection(7);
    }

    /**
     * 获取流失类型
     */
    private List<ItemDto> getDismissTypeList() {
        return TipsEnum.getCollection(11);
    }

    @Override
    public List<String> getTimeScope(String customerId) {
        Date startTime = commonDao.findDataSartTime(customerId);

        DateTime dt = new DateTime(startTime);
        int year = dt.getYear();
        int nowYear = DateTime.now().getYear();

        int offset = nowYear - year;

        List<String> timeScopes = CollectionKit.newList();
        timeScopes.add(year + "");
        while (0 < offset) {
            year++;
            timeScopes.add(year + "");
            offset--;
        }
        Collections.reverse(timeScopes);
        return timeScopes;
    }

    @Override
    public EmpDto getEmpBaseInfo(String customerId, String empId) {
        return commonDao.findEmpBaseInfo(customerId, empId);
    }

    public Integer existEmp(String empId, String empKey, String userName) {
        return commonDao.existEmp(empId, empKey, userName);
    }

    @Override
    public PaginationDto<EmpDto> findEmpAll(String keyName, String reqOrgId, PaginationDto<EmpDto> pageDto, String sidx,
                                            String sord, String customerId) {

        // 通过keyName查询员工时，登录人必须要具备查看所在员工下的数据权限 by jxzhang
        List<String> organPermitIds = getOrganPermitId();
        if (null == organPermitIds || organPermitIds.isEmpty()) {
            return null;
        }
        int count = commonDao.findEmpAllCount(keyName, reqOrgId, customerId, organPermitIds);
        List<EmpDto> dtos = commonDao.findEmpAll(organPermitIds, keyName, reqOrgId, sidx, sord, customerId,
                pageDto.getOffset(), pageDto.getLimit());

        pageDto.setRecords(count);
        pageDto.setRows(dtos);
        return pageDto;
    }

    /**
     * 登录人所有数据权限ID集 by jxzhang
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

    @Override
    public boolean addRisk(String customerId, RiskDto riskDto, List<RiskItemDto> list) {
        try {
            commonDao.updateRiskState(customerId, riskDto.getEmpId());
            String date = DateUtil.getDBNow();
            String uuid = Identities.uuid2();
            riskDto.setCustomerId(customerId);
            riskDto.setId(uuid);
            riskDto.setRiskDate(date);
            riskDto.setLast(1);
            commonDao.addRisk(riskDto);
            for (RiskItemDto rid : list) {
                rid.setId(Identities.uuid2());
                rid.setDimissionId(uuid);
                rid.setCustomerId(customerId);
            }
            commonDao.addRiskItem(list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<HashMap<String, String>> queryAbilityHashMap(String customerId) {
        return commonDao.queryAbilityHashMap(customerId);
    }

    @Override
    public List<HashMap<String, String>> queryAbilityLvHashMap(String customerId) {
        return commonDao.queryAbilityLvHashMap(customerId);
    }

    /**
     * 绩效年份去重
     *
     * @return
     */
    private List<ItemDto> getListWithDup(List<ItemDto> list) {
        List<ItemDto> itemList = new ArrayList<ItemDto>();
        for (int i = 0; i < list.size(); i++) {
            ItemDto item = new ItemDto();
            String name = list.get(i).getName().substring(0, 4);
            item.setName(name);
            itemList.add(item);
        }
        for (int i = itemList.size() - 1; i > 0; i--) {
            String name = itemList.get(i).getName();
            for (int j = i - 1; j >= 0; j--) {
                if (itemList.get(j).getName().equals(name)) {
                    itemList.remove(i);
                    break;
                }
            }
        }
        return itemList;
    }

    @Transactional
    @Override
    public List<DaysDto> insertDays(String endDay, String startDay) {
        GetDays c = new EasybotsGetDays();
        List<DaysDto> days = c.calcDays(endDay, startDay);
        System.out.println(JSON.toJSONString(days));
        try {
            commonDao.insertDays(days);
            insertLog("sysJob", "days", "插入新的一年每天工作或假期情况", new Date(), TipsEnum.NOT_IS_ERROR.getCode(),
                    ProcedureInfoEnum.DAYS.getShowIndex());
            return days;
        } catch (Exception e) {
            insertLog("sysJob", "days", e.toString(), new Date(), TipsEnum.IS_ERROR.getCode(), null);
            log.debug(e.toString());
            return CollectionKit.newList();
        }
    }

    @Override
    public boolean insertTheoryAttendance(List<DaysDto> days, double hour, int ym, int y) {
        try {
            // Map<String, Object> paramMap = CollectionKit.newMap();
            // paramMap.put("customerId", EisWebContext.getCustomerId());
            // paramMap.put("year", days);
            // paramMap.put("hour", hour);
            List<TheoryAttendanceDto> taDto = CollectionKit.newList();
            for (DaysDto d : days) {
                taDto.add(new TheoryAttendanceDto(Identities.uuid2(), EisWebContext.getCustomerId(),
                        d.getIsWorkFlag() == 1 ? hour : 0, d.getDays(), y));
            }
            int optRs = commonDao.insertTheoryAttendance(taDto);
            System.out.println("====>" + optRs);
            insertLog("sysJob", "TheoryAttendance", "插入指定年每天应出勤小时数", new Date(), TipsEnum.NOT_IS_ERROR.getCode(),
                    ProcedureInfoEnum.THEORY_ATTENDANCE.getShowIndex());
            return true;
        } catch (Exception e) {
            insertLog("sysJob", "TheoryAttendance", e.toString(), new Date(), TipsEnum.IS_ERROR.getCode(), null);
            log.debug(e.toString());
            return false;
        }
    }

    @Override
    public void insertLog(String userId, String module, String text, Date startTime, int isError, Integer showIndex) {
        int useTime = DateUtil.getBetweenDiff(startTime, new Date(), 3);
        Map<String, Object> paramMap = CollectionKit.newMap();
        paramMap.put("logId", Identities.uuid2());
        paramMap.put("customerId", EisWebContext.getCustomerId());
        paramMap.put("userId", userId);
        paramMap.put("module", module);
        paramMap.put("text", text);
        paramMap.put("useTime", useTime);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", new Date());
        paramMap.put("isError", isError);
        paramMap.put("showIndex", showIndex);
        paramMap.put("yearMonth", DateUtil.convertToIntYearMonth(new Date()));
        commonDao.insertLog(paramMap);
    }

    /**
     * 检查Days表是否有本年度数据
     *
     * @param paramMap
     * @return
     */
    @Override
    public boolean checkDaysTable(String year) {
        Map<String, String> paramMap = CollectionKit.newMap();
        paramMap.put("beginTime", year + "-01-01");
        paramMap.put("endTime", year + "-12-31");
        return commonDao.checkDaysTable(paramMap) > 0 ? true : false;
    }

    @Override
    public void updateVacationDays(List<DaysDto> params) {
        commonDao.updateVacationDays(params);
    }

    @Override
    public int findVacationDays(String beginTime, String endTime) {
        return commonDao.findVacationDays(beginTime, endTime);
    }

    @Override
    public int checkHistoryOrganization(int ym) {
        return commonDao.checkHistoryOrganization(ym);
    }

    @Transactional
    @Override
    public int insertHistoryOrganization(int ym) {
        try {
            Integer rs = commonDao.insertHistoryOrganization(ym);
            System.out.println(rs);
            return 1;
        } catch (Exception e) {
            commonDao.delectHistoryOrganization(ym);
            return 0;
        }
    }
}

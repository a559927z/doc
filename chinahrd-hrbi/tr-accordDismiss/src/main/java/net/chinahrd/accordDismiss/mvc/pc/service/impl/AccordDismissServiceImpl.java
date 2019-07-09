package net.chinahrd.accordDismiss.mvc.pc.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.chinahrd.accordDismiss.mvc.pc.dao.AccordDismissDao;
import net.chinahrd.accordDismiss.mvc.pc.service.AccordDismissService;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.accordDismiss.AccordDismissDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissContrastDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRecordDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissTrendDto;
import net.chinahrd.entity.dto.pc.accordDismiss.QuarterDismissCountDto;
import net.chinahrd.entity.dto.pc.common.CompareDto;
import net.chinahrd.entity.enums.CompanyAgeEnum;
import net.chinahrd.utils.ArithUtil;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;

import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 主动流失率service
 */
@Service("accordDismissService")
public class AccordDismissServiceImpl implements AccordDismissService {

    @Autowired
    private AccordDismissDao accordDismissDao;

    @Override
    public PaginationDto<AccordDismissDto> queryRunOffDetail(String queryType, String keyName,
                                                             String customerId, String organizationId, String beginDate, String endDate,
                                                             PaginationDto<AccordDismissDto> dto, String roType, String sequenceId,
                                                             String abilityId, String isKeyTalent, String keyTalentTypeId, String performanceKey) {
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("customerId", customerId);
        mapParam.put("organizationId", organizationId);
        mapParam.put("rowBounds", rowBounds);
        if (queryType.equals("2")) {
            List<String> roTypelist = CollectionKit.strToList(roType, ",");
            List<String> sequenceIdlist = CollectionKit.strToList(sequenceId, ",");
            List<String> abilityIdlist = CollectionKit.strToList(abilityId, ",");
            List<String> kTTIdlist = CollectionKit.newList();
            kTTIdlist.addAll(CollectionKit.strToList(keyTalentTypeId, ","));
            List<String> performanceKeylist = CollectionKit.strToList(performanceKey, ",");
            mapParam.put("startYm", beginDate);
            mapParam.put("endYm", endDate);
            mapParam.put("roTypelist", roTypelist);
            mapParam.put("sequenceIdlist", sequenceIdlist);
            mapParam.put("abilityIdlist", abilityIdlist);
            boolean notKeyTalent = false;
            for (String str : kTTIdlist) {
                if (str.equals("-1")) {
                    notKeyTalent = true;
                    kTTIdlist.remove(str);
                    break;
                }
            }
            mapParam.put("notKeyTalent", notKeyTalent);
            mapParam.put("kTTIdlist", kTTIdlist);
            mapParam.put("performanceKeylist", performanceKeylist);
            int count = accordDismissDao.queryRunOffCount(mapParam);
            List<AccordDismissDto> dtos = accordDismissDao.queryRunOffDetail(mapParam);

            dto.setRecords(count);
            dto.setRows(dtos);

            // return dto;
        } else if (queryType.equals("1")) { // 按名字
            if (null == keyName || keyName.trim().length() == 0) {
                return dto;
            }
            mapParam.put("keyName", keyName);
            int count = accordDismissDao.queryRunOffByNameCount(mapParam);
            List<AccordDismissDto> dtos = accordDismissDao.queryRunOffDetailByName(mapParam);
            dto.setRecords(count);
            dto.setRows(dtos);
            // return dto;
        }
        return dto;
    }

    @Override
    public List<DismissRecordDto> queryDismissRecord(String customerId, String organizationId, String yearMonths, String quarterLastDay) {
        return accordDismissDao.queryDismissRecord(customerId, organizationId, yearMonths, quarterLastDay);
    }

    @Override
    public List<DismissTrendDto> queryDismissTrend(String organId, String prevQuarter, String customerId) {
        return accordDismissDao.queryDismissTrend(organId, prevQuarter, customerId);
    }

    @Override
    public CompareDto getKeyDismissData(String organId, String date, String customerId) {
        String beginDate = getPre5Month(date);
        List<CompareDto> quaKeyPerson = accordDismissDao.countQuaKeyPerson(organId, beginDate,
                date, customerId);
        return extractedCompare(date, quaKeyPerson);
    }

    @Override
    public CompareDto getPerformDismissData(String organId, String date, String customerId) {
        String beginDate = getPre5Month(date);
        List<CompareDto> quaKeyPerson = accordDismissDao.countQuaHighPerform(organId,
                beginDate, date, customerId);
        return extractedCompare(date, quaKeyPerson);
    }

    private CompareDto extractedCompare(List<CompareDto> quaKeyPerson) {
        if (CollectionKit.isEmpty(quaKeyPerson)) {
            return new CompareDto();
        }
        Integer cur = quaKeyPerson.get(0).getCur();
        Integer prev = (quaKeyPerson.size() > 1) ? (cur - quaKeyPerson.get(1).getCur()) : cur;
        CompareDto compareDto = new CompareDto(cur, prev);
        return compareDto;
    }

    private CompareDto extractedCompare(String date, List<CompareDto> quaKeyPerson) {
        if (CollectionKit.isEmpty(quaKeyPerson)) {
            return new CompareDto();
        }
        if (quaKeyPerson.size() == 2) {
            Integer cur = quaKeyPerson.get(0).getCur();
            Integer prev = cur - quaKeyPerson.get(1).getCur();
            CompareDto compareDto = new CompareDto(cur, prev);
            return compareDto;
        } else {
            if (isFirst(date, quaKeyPerson.get(0).getDate())) {
                Integer cur = quaKeyPerson.get(0).getCur();
                Integer prev = cur;
                CompareDto compareDto = new CompareDto(cur, prev);
                return compareDto;
            } else {
                Integer cur = 0;
                Integer prev = -quaKeyPerson.get(0).getCur();
                CompareDto compareDto = new CompareDto(cur, prev);
                return compareDto;
            }

        }

    }

    /**
     * 判断本季度还是 上季度数据
     *
     * @param date
     * @param dbDate
     * @return
     */
    private boolean isFirst(String date, Integer dbDate) {
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        Integer ym = Integer.parseInt(year + calQuarter(Integer.parseInt(month)));
        return ym.equals(dbDate);

    }

    /**
     * 计算季度
     *
     * @param month
     * @return
     */
    private int calQuarter(int month) {
        int quarter;
        switch (month) {
            case 1:
            case 2:
            case 3:
                quarter = 1;
                break;
            case 4:
            case 5:
            case 6:
                quarter = 2;
                break;
            case 7:
            case 8:
            case 9:
                quarter = 3;
                break;
            case 10:
            case 11:
            case 12:
                quarter = 4;
                break;
            default:
                quarter = 1;
                break;
        }
        return quarter;

    }

    /**
     * 获取5个月前的第一天
     *
     * @param endDateStr
     * @return
     */
    private String getPre5Month(String endDateStr) {
        DateTime dt = new DateTime(endDateStr);
        return dt.minusMonths(5).dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
    }

    @Override
    public Map<String, Object> getSubDismissData(String customerId, String organizationId,
                                                 String date) {
        DateTime dt = new DateTime(date);
        int endYearMonth = Integer.parseInt(dt.toString("yyyyMM"));
        int beginYearMonth = DateUtil.getPreYearMonth(endYearMonth, 2);
        List<DismissContrastDto> dismissList = accordDismissDao.querySubDismissData(customerId, organizationId, beginYearMonth, endYearMonth);
        if (CollectionKit.isEmpty(dismissList)) {
            return null;
        }
        Map<String, DismissContrastDto> allDataMap = packDisQuaData(organizationId, endYearMonth, beginYearMonth, dismissList);
        Map<String, Object> resultMap = packContrResultMap(organizationId, allDataMap);
        return resultMap;
    }

    /**
     * 计算各个机构的流失率
     *
     * @param organizationId
     * @param allDataMap
     * @return
     */
    private Map<String, Object> packContrResultMap(String organizationId, Map<String, DismissContrastDto> allDataMap) {
        Map<String, Object> resultMap = CollectionKit.newMap();
        List<DismissContrastDto> resultList = CollectionKit.newList();
        for (Entry<String, DismissContrastDto> entry : allDataMap.entrySet()) {
            DismissContrastDto value = entry.getValue();
            // （季度初人数+季度末人数）/2
            double averTotal = ArithUtil.div(value.getBeginSum() + value.getEndSum(), 2, 1);
            // 流失率= 季度内流失总人数 / ((季度初人数+季度末人数)/2)
            Double dismissRate = averTotal == 0 ? null : ArithUtil.div(value.getRunOffCount() * 100, averTotal, 2);
            value.setDismissRate(dismissRate);
            if (organizationId.equals(value.getOrganizationId())) {
                // 如果是当前节点，则把流失率放进map对应的key“curRate”
                // resultMap.put("curRate", dismissRate);
            } else {
                resultList.add(value);
                if (value.getOrganizationId().equals("sum")) {
                    resultMap.put("curRate", value.getDismissRate());
                }
            }
        }
        resultMap.put("sub", resultList);
        return resultMap;
    }

    /**
     * 组装流失率的季度数据 （并加入“合计”）
     *
     * @param endYearMonth
     * @param beginYearMonth
     * @param dismissList
     * @return
     */
    private Map<String, DismissContrastDto> packDisQuaData(String parentOrgId,
                                                           int endYearMonth, int beginYearMonth, List<DismissContrastDto> dismissList) {
        Map<String, DismissContrastDto> map = new LinkedHashMap<String, DismissContrastDto>();
        // 子机构的数值总和
        int subRunOffSum = 0, subBeginSum = 0, subEndSum = 0;
        for (DismissContrastDto dto : dismissList) {
            String organId = dto.getOrganizationId();
            DismissContrastDto existDto = map.get(organId);
            if (existDto == null) {
                existDto = new DismissContrastDto(organId, dto.getOrganizationName());
            }
            int runOffSum = existDto.getRunOffCount() + dto.getRunOffCount();
            existDto.setRunOffCount(runOffSum);
            // 分别给季初和季末赋值
            if (dto.getYearMonth() == beginYearMonth) {
                existDto.setBeginSum(dto.getBeginSum());
            } else if (dto.getYearMonth() == endYearMonth) {
                existDto.setEndSum(dto.getEndSum());
            }
            map.put(organId, existDto);
            if (parentOrgId.equals(organId)) {
                continue;
            }
            subRunOffSum += dto.getRunOffCount();
            if (dto.getYearMonth() == beginYearMonth) {
                subBeginSum += dto.getBeginSum();
            } else if (dto.getYearMonth() == endYearMonth) {
                subEndSum += dto.getEndSum();
            }
        }
        // 添加合计
        DismissContrastDto summaryDto = new DismissContrastDto("sum", "合计", subBeginSum,  subEndSum, subRunOffSum);
        map.put("sum", summaryDto);
        return map;
    }

    @Override
    public String queryQuarterLastDay(String customerId) {
        return accordDismissDao.queryQuarterLastDay(customerId);
    }

    @Override
    public DismissTrendDto queryDisminss4Quarter(String organId, String quarterLastDay,
                                                 String customerId) {
        List<DismissTrendDto> dtos = accordDismissDao.queryDisminss4Quarter(organId, quarterLastDay, customerId);
        double quarterBegin = 0, quarterEnd = 0, minMonth = 0, maxMonth = 0, accordCount = 0;
        for (DismissTrendDto dto : dtos) {
            // 累计流失人数
            accordCount = ArithUtil.sum(accordCount, dto.getAccordCount());
            // 计算季度总人数
            double yearMonth = Double.valueOf(dto.getYearMonth());
            if (quarterBegin == 0) {
                quarterBegin = dto.getMonthBegin();
                quarterEnd = dto.getMonthEnd();
                minMonth = yearMonth;
                maxMonth = yearMonth;
                continue;
            }
            if (minMonth > yearMonth) {
                minMonth = yearMonth;
                quarterBegin = dto.getMonthBegin();
                continue;
            }
            if (maxMonth < yearMonth) {
                maxMonth = yearMonth;
                quarterEnd = dto.getMonthEnd();
                continue;
            }
        }
        // 季度总人数
        double monthCount = ArithUtil.div(ArithUtil.sum(quarterBegin, quarterEnd), 2, 4);
        // 季度流失率
        double rate = accordCount == 0 ? 0d : ArithUtil.div(accordCount, monthCount, 4);
        DismissTrendDto dismiss = new DismissTrendDto();
        dismiss.setMonthBegin(quarterBegin);
        dismiss.setMonthEnd(quarterEnd);
        dismiss.setAccordCount(accordCount);
        dismiss.setMonthCount(monthCount);
        dismiss.setRate(rate);
        return dismiss;
    }

    @Override
    public Map<String, List<QuarterDismissCountDto>> queryQuarterDismissInfo(String organId,
                                                                             String quarterLastDay, String customerId) {
        Map<String, List<QuarterDismissCountDto>> map = CollectionKit.newMap();
        if (StringUtils.isEmpty(quarterLastDay)) {
            return map;
        }
        map.put("pref",
                accordDismissDao.queryQuarterDismiss4Pref(organId, quarterLastDay, customerId));
        map.put("ability", accordDismissDao.queryQuarterDismiss4Ability(organId,
                quarterLastDay, customerId));
        map.put("companyAge",
                queryQuarterDismiss4CompanyAge(organId, quarterLastDay, customerId));
        return map;
    }

    private List<QuarterDismissCountDto> queryQuarterDismiss4CompanyAge(String organId,
                                                                        String quarterLastDay, String customerId) {
        List<QuarterDismissCountDto> dtos = CollectionKit.newList();
        List<QuarterDismissCountDto> ageDtos = accordDismissDao
                .queryQuarterDismiss4CompanyAge(organId, quarterLastDay, customerId);
        if (ageDtos.size() > 0) {
            for (CompanyAgeEnum ageType : CompanyAgeEnum.values()) {
                QuarterDismissCountDto dismissAge = new QuarterDismissCountDto();
                dismissAge.setTypeId(String.valueOf(ageType.getTypeId()));
                dismissAge.setTypeName(ageType.getDesc());
                for (QuarterDismissCountDto dto : ageDtos) {
                    if (Integer.valueOf(dto.getTypeId()).intValue() == ageType.getTypeId()) {
                        dismissAge.setWorkingCount(dto.getWorkingCount());
                        dismissAge.setRunOffCount(dto.getRunOffCount());
                        break;
                    }
                }
                dtos.add(dismissAge);
            }
        }
        return dtos;
    }

}

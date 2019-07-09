package net.chinahrd.talentMaps.mvc.pc.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsConfigDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsEmpCountDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsPointDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsSimpleCountDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsTeamInfoDto;
import net.chinahrd.entity.dto.pc.talentMaps.TalentMapsTeamQueryDto;
import net.chinahrd.talentMaps.mvc.pc.dao.TalentMapsDao;
import net.chinahrd.talentMaps.mvc.pc.service.TalentMapsService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * 人才地图
 *
 * @author xwLi 2016-07-18
 */
@Transactional
@Service("talentMapsService")
public class TalentMapsServiceImpl implements TalentMapsService {
    private static Logger log = LoggerFactory.getLogger(TalentMapsServiceImpl.class);

    @Autowired
    private TalentMapsDao talentMapsDao;

    /**
     * 查询时间区间选择
     */
    @Override
    public Map<String, Object> queryTimesCycle(List<String> organIds, String customerId) {
        Map<String, Object> map = CollectionKit.newMap();
//        List<String> list = talentMapsDao.queryTimesCycle(customerId);
        List<KVItemDto> list = talentMapsDao.queryTalentMapsDateCycle(organIds, customerId);
        String minTime = "", maxTime = "", times = "";
        if (list != null && list.size() > 0) {
            String minYear = list.get(list.size() - 1).getK().substring(0, 4);
            String minMonth = list.get(list.size() - 1).getK().substring(4);
            String maxYear = list.get(0).getK().substring(0, 4);
            String maxMonth = list.get(0).getK().substring(4);
            minTime = minYear + "-" + minMonth + "-01";
            maxTime = maxYear + "-" + maxMonth + "-01";
            int minNum = changeMonthToNumber(minMonth);
            int maxNum = changeMonthToNumber(maxMonth);
            int num = Integer.parseInt(maxYear) - Integer.parseInt(minYear);
            if (num > 2) {
                minYear = (Integer.parseInt(maxYear) - 2) + "";
            }
            if (maxNum == 2) {
                minNum = 2;
            }
            times = minYear + "," + minNum + "," + maxYear + "," + maxNum;
        }
        map.put("minTime", minTime);
        map.put("maxTime", maxTime);
        map.put("times", times);
        return map;
    }

    @Override
    public boolean checkIsAuditor(String empId, String customerId) {
        return talentMapsDao.checkIsAuditor(empId, customerId);
    }

    /**
     * 根据月份，转换为数字1或2
     * 1页面表示上半年 2页面表示下半年
     */
    public int changeMonthToNumber(String month) {
        int num = 0;
        switch (Integer.parseInt(month)) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                num = 1;
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                num = 2;
                break;
        }
        return num;
    }

    /**
     * 查询时间机构
     */
    public Map<String, Object> queryTalentMapsTime(String customerId, List<OrganDto> topOrgan) {
        List<OrganDto> config = talentMapsDao.queryConfigTalentMaps(customerId);
        List<OrganDto> organPermit = CollectionKit.newList();
        for (OrganDto dto : config) {
            boolean has = false;
            for (OrganDto organ : topOrgan) {
                if (dto.getFullPath().indexOf(organ.getFullPath()) != -1) {
                    has = true;
                }
            }
            if (has)
                organPermit.add(dto);
        }
        Map<String, Object> map = CollectionKit.newMap();
        map.put("organPermit", organPermit);
        List<Integer> timeList = talentMapsDao.queryTalentMapsTime(customerId);
        map.put("timeList", timeList);
        return map;
    }

    /**
     * 生成原始人才地图
     */
    @Transactional
    public void addOriginalTalentMaps(String customerId, List<OrganDto> topOrgan, String startTime) {
        Map<String, Object> map = CollectionKit.newMap();
        map.put("customerId", customerId);
        map.put("flag", 0);
//        map.put("adjustmentId", adjustmentId);
        map.put("startTime", startTime);
        int month = Integer.parseInt(startTime.substring(5, 7));
        int yearMonth = 0;
        if (month < 7)
            yearMonth = Integer.parseInt(startTime.substring(0, 4) + "06");
        else
            yearMonth = Integer.parseInt(startTime.substring(0, 4) + "12");
        map.put("yearMonth", yearMonth);
        for (OrganDto dto : topOrgan) {
            map.put("id", Identities.uuid2());
            map.put("organId", dto.getOrganizationId());
            talentMapsDao.addOriginalTalentMaps(map);
        }
    }

    /**
     * 查询待审核的人才地图
     */
    public List<TalentMapsDto> queryMapsAuditing(String customerId, List<OrganDto> organDtos, String empId, String updateTime) {
        Integer yearMonth = getHalfYearMonth(updateTime);
        List<OrganDto> config = talentMapsDao.queryConfigTalentMaps(customerId);
        List<OrganDto> organPermit = CollectionKit.newList();
        for (OrganDto dto : config) {
            boolean has = false;
            for (OrganDto organ : organDtos) {
                if (dto.getFullPath().indexOf(organ.getFullPath()) != -1) {
                    has = true;
                }
            }
            if (has) organPermit.add(dto);
        }
        Map<String, Object> map = CollectionKit.newMap();
        map.put("customerId", customerId);
        map.put("yearMonth", yearMonth);
        map.put("topOrgan", organPermit);
        List<TalentMapsDto> list = CollectionKit.newList();
        List<TalentMapsDto> result = CollectionKit.newList();
        if (organPermit != null && organPermit.size() > 0) {
            list = talentMapsDao.queryAllTalentMaps(map);
            try {
                if (list == null || list.size() == 0) {
                    addOriginalTalentMaps(customerId, organPermit, updateTime);
                } else {
                    List<OrganDto> organ = CollectionKit.newLinkedList();
                    for (OrganDto dto : organPermit) {
                        boolean b = true;
                        for (TalentMapsDto talent : list) {
                            if (talent.getOrganId().equals(dto.getOrganizationId())) {
                                b = false;
                            }
                        }
                        if (b) {
                            organ.add(dto);
                        }
                    }
                    if (organ != null && organ.size() > 0) {
                        addOriginalTalentMaps(customerId, organ, updateTime);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Map<String, Object> auditingMap = CollectionKit.newMap();
            auditingMap.put("customerId", customerId);
            auditingMap.put("yearMonth", yearMonth);
            auditingMap.put("topOrgan", organPermit);
            auditingMap.put("status", 0);
            result = talentMapsDao.queryTalentMapsAuditing(auditingMap);
        }
        Map<String, Object> publishMap = CollectionKit.newMap();
        publishMap.put("customerId", customerId);
        publishMap.put("yearMonth", yearMonth);
        publishMap.put("topOrgan", organPermit);
        publishMap.put("status", 1);
        publishMap.put("empId", empId);
        result.addAll(talentMapsDao.queryTalentMapsPublish(publishMap));
        return result;
    }

    /**
     * 获取当前时间的所在半年的开始结束月
     */
    public Integer getHalfYearMonth(String time) {
        int month = Integer.parseInt(time.substring(5, 7));
        int year = Integer.parseInt(time.substring(0, 4));
        if (month < 7) {
            return year * 100 + 6;
        } else {
            return year * 100 + 12;
        }
    }

    /**
     * 查询待审核的人员信息
     */
    public PaginationDto<TalentMapsDto> queryTalentPerformanceAbility(String customerId, String topId, Integer yearMonth, String keyName, String zName, int flag, PaginationDto<TalentMapsDto> d) {
        Map<String, Object> param = CollectionKit.newMap();
        param.put("customerId", customerId);
        param.put("topId", topId);
        param.put("yearMonth", yearMonth);
        param.put("keyName", keyName);
        param.put("zName", zName);
//        param.put("flag", flag);
        param.put("offset", d.getOffset());
        param.put("limit", d.getLimit());
        List<TalentMapsDto> list = talentMapsDao.queryTalentPerformanceAbility(param);
        List<TalentMapsDto> ability = talentMapsDao.queryAbilityInfo(customerId);
        List<TalentMapsDto> performance = talentMapsDao.queryPerformanceInfo(customerId);
        List<TalentMapsDto> result = CollectionKit.newList();
        for (TalentMapsDto dto : list) {
            for (TalentMapsDto a : ability) {
                String name = a.getName();
                if (a.getId().equals(dto.getYaxis())) {
                    dto.setYaxisName(name);
                }
                if (a.getId().equals(dto.getYaxisAf())) {
                    dto.setYaxisAfName(name);
                }
            }
            for (TalentMapsDto p : performance) {
                String name = p.getName();
                if (p.getId().equals(dto.getXaxis())) {
                    dto.setXaxisName(name);
                }
                if (p.getId().equals(dto.getXaxisAf())) {
                    dto.setXaxisAfName(name);
                }
            }
            result.add(dto);
        }
        int count = talentMapsDao.queryTalentPerformanceAbilityCount(param);
        d.setRecords(count);
        d.setRows(list);
        return d;
    }

    /**
     * 查询待发布的人员信息
     */
    public PaginationDto<TalentMapsDto> queryTalentPublish(String customerId, String topId, String adjustmentId, Integer yearMonth, String keyName, int flag, PaginationDto<TalentMapsDto> d) {
        Map<String, Object> param = CollectionKit.newMap();
        param.put("customerId", customerId);
        param.put("topId", topId);
        param.put("adjustmentId", adjustmentId);
        param.put("keyName", keyName);
        param.put("flag", flag);
        param.put("yearMonth", yearMonth);
        param.put("offset", d.getOffset());
        param.put("limit", d.getLimit());
        List<TalentMapsDto> list = talentMapsDao.queryTalentPublish(param);
        List<TalentMapsDto> ability = talentMapsDao.queryAbilityInfo(customerId);
        List<TalentMapsDto> performance = talentMapsDao.queryPerformanceInfo(customerId);
        List<TalentMapsDto> result = CollectionKit.newList();
        for (TalentMapsDto dto : list) {
            for (TalentMapsDto a : ability) {
                String text = a.getText();
                String name = a.getName();
                if (a.getId().equals(dto.getYaxis())) {
                    dto.setYaxisName(name);
                    dto.setyData(text);
                }
                if (a.getId().equals(dto.getYaxisAf())) {
                    dto.setYaxisAfName(name);
                    dto.setyDataAf(text);
                }
            }
            for (TalentMapsDto p : performance) {
                String text = p.getText();
                String name = p.getName();
                if (p.getId().equals(dto.getXaxis())) {
                    dto.setXaxisName(name);
                    dto.setxData(text);
                }
                if (p.getId().equals(dto.getXaxisAf())) {
                    dto.setXaxisAfName(name);
                    dto.setxDataAf(text);
                }
            }
            result.add(dto);
        }
        int count = talentMapsDao.queryTalentPublishCount(param);
        d.setRecords(count);
        d.setRows(result);
        return d;
    }

    /**
     * 查询已调整的人员信息-全部
     */
    public List<TalentMapsDto> queryTalentAdjustmentInfo(String customerId, String topId, String adjustmentId, Integer yearMonth, int flag) {
        Map<String, Object> param = CollectionKit.newMap();
        param.put("yearMonth", yearMonth);
        param.put("customerId", customerId);
        param.put("topId", topId);
        param.put("adjustmentId", adjustmentId);
        param.put("flag", flag);
        List<TalentMapsDto> list = talentMapsDao.queryTalentAdjustmentInfo(param);
        List<TalentMapsDto> ability = talentMapsDao.queryAbilityInfo(customerId);
        List<TalentMapsDto> performance = talentMapsDao.queryPerformanceInfo(customerId);
        List<TalentMapsDto> result = CollectionKit.newList();
        for (TalentMapsDto dto : list) {
            for (TalentMapsDto a : ability) {
                String text = a.getText();
                String name = a.getName();
                if (a.getId().equals(dto.getYaxis())) {
                    dto.setYaxisName(name);
                    dto.setyData(text);
                }
                if (a.getId().equals(dto.getYaxisAf())) {
                    dto.setYaxisAfName(name);
                    dto.setyDataAf(text);
                }
            }
            for (TalentMapsDto p : performance) {
                String text = p.getText();
                String name = p.getName();
                if (p.getId().equals(dto.getXaxis())) {
                    dto.setXaxisName(name);
                    dto.setxData(text);
                }
                if (p.getId().equals(dto.getXaxisAf())) {
                    dto.setXaxisAfName(name);
                    dto.setxDataAf(text);
                }
            }
            result.add(dto);
        }
        return result;
    }

    /**
     * 查询已调整的人员信息-分页
     */
    public PaginationDto<TalentMapsDto> queryTalentPublishInfo(String customerId, String topId, String releaseId, Integer yearMonth, int flag, PaginationDto<TalentMapsDto> d) {
        Map<String, Object> param = CollectionKit.newMap();
        param.put("yearMonth", yearMonth);
        param.put("customerId", customerId);
        param.put("topId", topId);
        param.put("releaseId", releaseId);
        param.put("flag", flag);
        param.put("offset", d.getOffset());
        param.put("limit", d.getLimit());
        List<TalentMapsDto> list = talentMapsDao.queryTalentPublishInfo(param);
        List<TalentMapsDto> ability = talentMapsDao.queryAbilityInfo(customerId);
        List<TalentMapsDto> performance = talentMapsDao.queryPerformanceInfo(customerId);
        List<TalentMapsDto> result = CollectionKit.newList();
        for (TalentMapsDto dto : list) {
            for (TalentMapsDto a : ability) {
                String text = a.getText();
                String name = a.getName();
                if (a.getId().equals(dto.getYaxis())) {
                    dto.setYaxisName(name);
                    dto.setzData(text);
                }
                if (a.getId().equals(dto.getYaxisAf())) {
                    dto.setYaxisAfName(name);
                    dto.setyDataAf(text);
                }
            }
            for (TalentMapsDto p : performance) {
                String text = p.getText();
                String name = p.getName();
                if (p.getId().equals(dto.getXaxis())) {
                    dto.setXaxisName(name);
                    dto.setxData(text);
                }
                if (p.getId().equals(dto.getXaxisAf())) {
                    dto.setXaxisAfName(name);
                    dto.setxDataAf(text);
                }
            }
            result.add(dto);
        }
        int count = talentMapsDao.queryTalentPublishInfoCount(param);
        d.setRecords(count);
        d.setRows(result);
        return d;
    }

    @Override
    public List<TalentMapsEmpCountDto> queryOrganMapsEmpCount(List<TalentMapsEmpCountDto> organs, String yearMonths, String customerId) {
        return talentMapsDao.queryOrganMapsEmpCount(organs, yearMonths, customerId);
    }

    @Override
    public List<KVItemDto> queryTalentMapsDateCycle(List<String> organIds, String customerId) {
        return talentMapsDao.queryTalentMapsDateCycle(organIds, customerId);
    }

    @Override
    public List<TalentMapsSimpleCountDto> queryMapsSimpleEmpCount(TalentMapsTeamQueryDto dto) {
        return talentMapsDao.queryMapsSimpleEmpCount(dto);
    }

    @Override
    public PaginationDto<TalentMapsPointDto> queryMapsEmpPoint(TalentMapsTeamQueryDto queryDto, PaginationDto<TalentMapsPointDto> dto) {
        String minDate = null, maxDate = null;
        if (queryDto.getDate() != null && !"".equals(queryDto.getDate()) && queryDto.getDate().indexOf(",") != -1) {
            String[] dates = queryDto.getDate().split(",");
            minDate = dates[0] + changeNumToMonth(dates[1]);
            maxDate = dates[2] + changeNumToMonth(dates[3]);
        }
        queryDto.setMinYearMonth(minDate);
        queryDto.setMaxYearMonth(maxDate);
        int count = talentMapsDao.queryMapsSettingEmpCount(queryDto);
        dto.setRecords(count);
        RowBounds rowBounds = dto.getPage() > 0 ? new RowBounds(dto.getOffset(), dto.getLimit()) : new RowBounds();
        List<TalentMapsPointDto> list = talentMapsDao.queryMapsEmpPoint(queryDto, rowBounds);
        dto.setRows(list);
        return dto;
    }

    /**
     * 获取地图相关员工点信息-趋势图用
     *
     * @param dto
     * @return
     */
    @Override
    public PaginationDto<TalentMapsPointDto> queryMapsEmpPointStr(TalentMapsTeamQueryDto queryDto, PaginationDto<TalentMapsPointDto> dto) {
        String minDate = null, maxDate = null;
        String date = queryDto.getDate();
        if (!StringUtils.isEmpty(date) && date.indexOf(",") != -1) {
            String[] dates = date.split(",");
            minDate = dates[0] + changeNumToMonth(dates[1]);
            maxDate = dates[2] + changeNumToMonth(dates[3]);
        }
        queryDto.setMinYearMonth(minDate);
        queryDto.setMaxYearMonth(maxDate);
        int count = talentMapsDao.queryMapsSettingEmpCount(queryDto);
        dto.setRecords(count);
        RowBounds rowBounds = dto.getPage() > 0 ? new RowBounds(dto.getOffset(), dto.getLimit()) : new RowBounds();
        List<TalentMapsPointDto> list = talentMapsDao.queryMapsEmpPoint(queryDto, rowBounds);

        if (StringUtils.isEmpty(date) || date.indexOf(",") == -1) {
            dto.setRows(list);
            return dto;
        }
        //人员搜索时间分组
        if (list != null && list.size() > 0) {
            List<TalentMapsPointDto> finalList = CollectionKit.newLinkedList();
            MultiValueMap<String, TalentMapsPointDto> m = new LinkedMultiValueMap<String, TalentMapsPointDto>();
            for (TalentMapsPointDto l : list) {
                m.add(l.getYearMonthText(), l);
            }
            for (Map.Entry<String, List<TalentMapsPointDto>> empEntry : m.entrySet()) {
                TalentMapsPointDto talentMapsDto = new TalentMapsPointDto();
                talentMapsDto.setText(empEntry.getKey());
                talentMapsDto.setChildren(empEntry.getValue());
                finalList.add(talentMapsDto);
            }
            dto.setRows(finalList);
        }
        return dto;
    }

    @Override
    public int queryMapsSettingEmpCount(TalentMapsTeamQueryDto dto) {
        return talentMapsDao.queryMapsSettingEmpCount(dto);
    }

    @Override
    public List<TalentMapsPointDto> queryTeamEmpPoint(TalentMapsTeamQueryDto dto) {
        return talentMapsDao.queryTeamEmpPoint(dto);
    }

    @Transactional
    @Override
    public void insertMapsTeams(List<TalentMapsTeamInfoDto> list, String empId, String customerId) {
        try {
            talentMapsDao.deleteUserTeamInfo(empId, null, customerId);
            talentMapsDao.insertMapsTeams(list);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

    @Override
    public int findUserTeamNum(String teamId, String empId, String customerId) {
        return talentMapsDao.findUserTeamNum(teamId, empId, customerId);
    }

    @Override
    public void deleteUserTeamInfo(String empId, String teamId, String customerId) {
        talentMapsDao.deleteUserTeamInfo(empId, teamId, customerId);
    }

    @Override
    public void insertUserTeam(TalentMapsTeamInfoDto dto) {
        List<TalentMapsTeamInfoDto> list = CollectionKit.newList(dto);
        talentMapsDao.insertMapsTeams(list);
    }

    @Override
    public void updateUserTeam(TalentMapsTeamInfoDto dto) {
        talentMapsDao.updateUserTeam(dto);
    }

    @Override
    public List<TalentMapsTeamInfoDto> queryUserTeamInfo(String empId, String customerId) {
        return talentMapsDao.queryUserTeamInfo(empId, customerId);
    }

    @Override
    public Map<String, Object> queryMapsBaseInfo(String customerId) {
        Map<String, Object> map = CollectionKit.newMap();
        List<TalentMapsDto> abilitys = talentMapsDao.queryAbilityInfo(customerId);
        List<TalentMapsDto> perfs = talentMapsDao.queryPerformanceInfo(customerId);

        List<TalentMapsConfigDto> configDtos = talentMapsDao.queryMapsConfigInfo(customerId);
        map.put("xAxisTitle", "绩效排名");
        map.put("xAxisData", perfs);
        map.put("yAxisTitle", "能力等级");
        map.put("yAxisData", abilitys);
        map.put("zAxisData", configDtos);
        return map;
    }

    /**
     * 获取人才地图基础信息-能力/绩效/时间
     */
    @Override
    public Map<String, Object> getMapsBaseInfoForTeamCP(String customerId, String date, String viewType) {
        Map<String, Object> map = CollectionKit.newMap();
        List<String> xData = CollectionKit.newLinkedList();
        if (date != null && !"".equals(date) && date.indexOf(",") != -1) {
            String[] dates = date.split(",");
            int minYear = Integer.parseInt(dates[0]);
            int maxYear = Integer.parseInt(dates[2]);
            for (int i = minYear; i <= maxYear; i++) {
                xData.add(i + "年" + changeNumToCn(1));
                xData.add(i + "年" + changeNumToCn(2));
            }
            if (Integer.parseInt(dates[1]) == 2) {
                xData.remove(0);
            }
            if (Integer.parseInt(dates[3]) == 1) {
                xData.remove(xData.size() - 1);
            }
        }
        List<String> yData;
        //0为能力，1为绩效
        if ("0".equals(viewType)) {
            List<TalentMapsDto> yList = talentMapsDao.queryAbilityInfo(customerId);
            yData = CollectionKit.newLinkedList();
            if (yList != null && yList.size() > 0) {
//                for (int i = yList.size() - 1; i >= 0; i--) {
//                    yData.add(yList.get(i).getName());
//                }
                for (TalentMapsDto yl : yList) {
                    yData.add(yl.getName());
                }
            }
        } else {
            List<TalentMapsDto> xList = talentMapsDao.queryPerformanceInfo(customerId);
            yData = CollectionKit.newLinkedList();
            if (xList != null && xList.size() > 0) {
                for (TalentMapsDto xl : xList) {
                    yData.add(xl.getName());
                }
            }
        }

        List<TalentMapsConfigDto> configDtos = talentMapsDao.queryMapsConfigInfo(customerId);
        map.put("xAxisData", xData);
        map.put("yAxisData", yData);
        map.put("zAxisData", configDtos);
        return map;
    }

    /**
     * 团队能力/绩效趋势图 地图显示
     */
    @Override
    public Map<String, Object> queryTeamCPMap(TalentMapsTeamQueryDto dto) {
        Map<String, Object> map = CollectionKit.newMap();
        String minDate = null, maxDate = null;
        if (dto.getDate() != null && !"".equals(dto.getDate()) && dto.getDate().indexOf(",") != -1) {
            String[] dates = dto.getDate().split(",");
            minDate = dates[0] + changeNumToMonth(dates[1]);
            maxDate = dates[2] + changeNumToMonth(dates[3]);
        }
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(dto.getOrganId());
        Map<String, Object> parMap = CollectionKit.newMap();
        parMap.put("customerId", dto.getCustomerId());
        parMap.put("organId", dto.getOrganId());
        parMap.put("subOrganIdList", subOrganIdList);
        parMap.put("minDate", minDate);
        parMap.put("maxDate", maxDate);
        parMap.put("sequenceList", CollectionKit.strToList(dto.getSequenceStr(), ","));
        parMap.put("sequenceSubList", CollectionKit.strToList(dto.getSequenceSubStr(), ","));
        parMap.put("abilityList", CollectionKit.strToList(dto.getAbilityStr(), ","));
        parMap.put("performanceList", CollectionKit.strToList(dto.getPerformanceStr(), ","));
        parMap.put("ageList", dto.getAgeIntervals());
        parMap.put("sexList", CollectionKit.strToList(dto.getSexStr(), ","));
        parMap.put("eduList", CollectionKit.strToList(dto.getEduStr(), ","));
        List<TalentMapsDto> list = CollectionKit.newList();
        List<TalentMapsDto> yDataList = CollectionKit.newList();
        if ("0".equals(dto.getViewType())) {
            //查看能力
            yDataList = talentMapsDao.queryAbilityInfo(dto.getCustomerId());
            list = talentMapsDao.queryTeamCPMapForAbility(parMap);
        } else {
            //查看绩效
            yDataList = talentMapsDao.queryPerformanceInfo(dto.getCustomerId());
            list = talentMapsDao.queryTeamCPMapForPerformance(parMap);
        }
        List<String> xList = CollectionKit.newLinkedList();
        List<String> yList = CollectionKit.newLinkedList();
        List<String> xIdList = CollectionKit.newLinkedList();
        List<String> yIdList = CollectionKit.newLinkedList();
        List<Object> fList = CollectionKit.newLinkedList();
        List<Integer> sList = CollectionKit.newList();
        Set<String> xSet = new TreeSet<String>();
        Set<String> xIdSet = new TreeSet<String>();
        int min = 0, max = 0;
        if (list != null && list.size() > 0) {
            if (yDataList != null && yDataList.size() > 0) {
//                for (int i = yDataList.size() - 1; i >= 0; i--) {
//                    yIdList.add(yDataList.get(i).getId());
//                    yList.add(yDataList.get(i).getName());
//                }
                for (TalentMapsDto yl : yDataList) {
                    yIdList.add(yl.getId());
                    yList.add(yl.getName());
                }
            }

            for (TalentMapsDto l : list) {
                xSet.add(l.getCnDate());
                xIdSet.add(l.getDate());
                sList.add(l.getConNum());
            }
            //根据时间段补全时间
            List<String> yearMonths = getFullDates(dto.getDate());
            if (yearMonths != null && yearMonths.size() > 0) {
                for (String y : yearMonths) {
                    String[] arr = y.split("#");
                    xIdSet.add(arr[0]);
                    xSet.add(arr[1]);
                }
            }
            xList.addAll(xSet);
            xIdList.addAll(xIdSet);

            Map<String, Integer> xMap = CollectionKit.newMap();
            for (int i = 0; i < xList.size(); i++) {
                xMap.put(xList.get(i), i);
            }
            Map<String, Integer> yMap = CollectionKit.newMap();
            for (int i = 0; i < yList.size(); i++) {
                yMap.put(yList.get(i), i);
            }
            for (int i = 0; i < xList.size(); i++) {
                for (int j = 0; j < yList.size(); j++) {
                    Integer[] arr = new Integer[3];
                    arr[0] = j;
                    arr[1] = i;
                    arr[2] = 0;
                    fList.add(arr);
                }
            }
            for (TalentMapsDto l : list) {
                Integer[] arr = new Integer[3];
                arr[0] = yMap.get(l.getName());
                arr[1] = xMap.get(l.getCnDate());
                arr[2] = l.getConNum();
                fList.add(arr);
            }
            Integer[] sArr = sList.toArray(new Integer[sList.size()]);
            Arrays.sort(sArr);
            min = sArr[0];
            max = sArr[sArr.length - 1];
        }
        map.put("xData", xList.toArray(new String[xList.size()]));
        map.put("yData", yList.toArray(new String[yList.size()]));
        map.put("xIdData", xIdList.toArray(new String[xIdList.size()]));
        map.put("yIdData", yIdList.toArray(new String[yIdList.size()]));
        map.put("fData", fList.toArray(new Object[fList.size()]));
        map.put("min", min);
        map.put("max", max);
        return map;
    }

    /**
     * 团队能力/绩效趋势图 地图显示 人员明细
     */
    @Override
    public PaginationDto<TalentMapsDto> queryTeamCPMapPersonDetail(TalentMapsTeamQueryDto dto, PaginationDto<TalentMapsDto> pDto) {
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(dto.getOrganId());
        Map<String, Object> parMap = CollectionKit.newMap();
        parMap.put("customerId", dto.getCustomerId());
        parMap.put("organId", dto.getOrganId());
        parMap.put("subOrganIdList", subOrganIdList);
        parMap.put("date", dto.getYearMonths());
        parMap.put("yVal", dto.getyVal());
        parMap.put("sequenceList", CollectionKit.strToList(dto.getSequenceStr(), ","));
        parMap.put("sequenceSubList", CollectionKit.strToList(dto.getSequenceSubStr(), ","));
        parMap.put("abilityList", CollectionKit.strToList(dto.getAbilityStr(), ","));
        parMap.put("performanceList", CollectionKit.strToList(dto.getPerformanceStr(), ","));
        parMap.put("ageList", dto.getAgeIntervals());
        parMap.put("sexList", CollectionKit.strToList(dto.getSexStr(), ","));
        parMap.put("eduList", CollectionKit.strToList(dto.getEduStr(), ","));
        parMap.put("start", pDto.getOffset());
        parMap.put("rows", pDto.getLimit());
        List<TalentMapsDto> list = CollectionKit.newLinkedList();

        int count;
//        RowBounds rowBounds = new RowBounds(pDto.getOffset(), pDto.getLimit());
        //0为能力，1为绩效
        if ("0".equals(dto.getViewType())) {
            count = talentMapsDao.queryTeamCPMapPersonDetailForAbilityCount(parMap);
            list = talentMapsDao.queryTeamCPMapPersonDetailForAbility(parMap);
        } else {
            count = talentMapsDao.queryTeamCPMapPersonDetailForPerformanceCount(parMap);
            list = talentMapsDao.queryTeamCPMapPersonDetailForPerformance(parMap);
        }
        pDto.setRecords(count);
        pDto.setRows(list);

        return pDto;
    }

    /**
     * 团队能力/绩效趋势图 列表显示-标题列表
     */
    @Override
    public Map<String, Object> queryAbilityForList(TalentMapsTeamQueryDto dto) {
        Map<String, Object> map = CollectionKit.newMap();
        String minDate = null, maxDate = null;
        if (dto.getDate() != null && !"".equals(dto.getDate()) && dto.getDate().indexOf(",") != -1) {
            String[] dates = dto.getDate().split(",");
            minDate = dates[0] + changeNumToMonth(dates[1]);
            maxDate = dates[2] + changeNumToMonth(dates[3]);
        }
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(dto.getOrganId());
        Map<String, Object> parMap = CollectionKit.newMap();
        parMap.put("customerId", dto.getCustomerId());
        parMap.put("organId", dto.getOrganId());
        parMap.put("subOrganIdList", subOrganIdList);
        parMap.put("minDate", minDate);
        parMap.put("maxDate", maxDate);
        parMap.put("yearMonth", dto.getYearMonths());
        parMap.put("sequenceList", CollectionKit.strToList(dto.getSequenceStr(), ","));
        parMap.put("sequenceSubList", CollectionKit.strToList(dto.getSequenceSubStr(), ","));
        parMap.put("abilityList", CollectionKit.strToList(dto.getAbilityStr(), ","));
        parMap.put("performanceList", CollectionKit.strToList(dto.getPerformanceStr(), ","));
        parMap.put("ageList", dto.getAgeIntervals());
        parMap.put("sexList", CollectionKit.strToList(dto.getSexStr(), ","));
        parMap.put("eduList", CollectionKit.strToList(dto.getEduStr(), ","));

        int count = talentMapsDao.queryAbilityForListCount(parMap);
        List<TalentMapsDto> list = talentMapsDao.queryAbilityForList(parMap);
        map.put("count", count);
        map.put("list", list);
        return map;
    }

    /**
     * 团队能力/绩效趋势图 列表显示
     */
    @Override
    public List<TalentMapsDto> queryTeamCPGrid(TalentMapsTeamQueryDto dto, String titleId, int pageNum, int pageSize) {
        String minDate = null, maxDate = null;
        if (dto.getDate() != null && !"".equals(dto.getDate()) && dto.getDate().indexOf(",") != -1) {
            String[] dates = dto.getDate().split(",");
            minDate = dates[0] + changeNumToMonth(dates[1]);
            maxDate = dates[2] + changeNumToMonth(dates[3]);
        }
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(dto.getOrganId());
        Map<String, Object> parMap = CollectionKit.newMap();
        parMap.put("customerId", dto.getCustomerId());
        parMap.put("organId", dto.getOrganId());
        parMap.put("subOrganIdList", subOrganIdList);
        parMap.put("minDate", minDate);
        parMap.put("maxDate", maxDate);
        parMap.put("yearMonth", dto.getYearMonths());
        parMap.put("sequenceList", CollectionKit.strToList(dto.getSequenceStr(), ","));
        parMap.put("sequenceSubList", CollectionKit.strToList(dto.getSequenceSubStr(), ","));
        parMap.put("abilityList", CollectionKit.strToList(dto.getAbilityStr(), ","));
        parMap.put("performanceList", CollectionKit.strToList(dto.getPerformanceStr(), ","));
        parMap.put("ageList", dto.getAgeIntervals());
        parMap.put("sexList", CollectionKit.strToList(dto.getSexStr(), ","));
        parMap.put("eduList", CollectionKit.strToList(dto.getEduStr(), ","));
        parMap.put("titleId", titleId);
        parMap.put("pageNum", (pageNum - 1) * pageSize);
        parMap.put("pageSize", pageSize);
        List<TalentMapsDto> list = talentMapsDao.queryTeamCPGrid(parMap);
        return list;
    }

    /**
     * 团队能力/绩效趋势图- 明细显示
     */
    @Override
    public Map<String, Object> queryTeamCPDetail(TalentMapsTeamQueryDto dto) {
        Map<String, Object> map = CollectionKit.newMap();
        String minDate = null, maxDate = null;
        if (dto.getDate() != null && !"".equals(dto.getDate()) && dto.getDate().indexOf(",") != -1) {
            String[] dates = dto.getDate().split(",");
            minDate = dates[0] + changeNumToMonth(dates[1]);
            maxDate = dates[2] + changeNumToMonth(dates[3]);
        }
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(dto.getOrganId());
        Map<String, Object> parMap = CollectionKit.newMap();
        parMap.put("customerId", dto.getCustomerId());
        parMap.put("organId", dto.getOrganId());
        parMap.put("subOrganIdList", subOrganIdList);
        parMap.put("minDate", minDate);
        parMap.put("maxDate", maxDate);
        parMap.put("sequenceList", CollectionKit.strToList(dto.getSequenceStr(), ","));
        parMap.put("sequenceSubList", CollectionKit.strToList(dto.getSequenceSubStr(), ","));
        parMap.put("abilityList", CollectionKit.strToList(dto.getAbilityStr(), ","));
        parMap.put("performanceList", CollectionKit.strToList(dto.getPerformanceStr(), ","));
        parMap.put("ageList", dto.getAgeIntervals());
        parMap.put("sexList", CollectionKit.strToList(dto.getSexStr(), ","));
        parMap.put("eduList", CollectionKit.strToList(dto.getEduStr(), ","));
        List<TalentMapsSimpleCountDto> list = CollectionKit.newLinkedList();
        //0为能力，1为绩效
        if ("0".equals(dto.getViewType())) {
            if ("0".equals(dto.getIsFull())) {
                list = talentMapsDao.queryTeamCPDetailForAbility(parMap);
            } else {
                list = talentMapsDao.queryTeamCPDetailForFullAbility(parMap);
            }
        } else {
            if ("0".equals(dto.getIsFull())) {
                list = talentMapsDao.queryTeamCPDetailForPerformance(parMap);
            } else {
                list = talentMapsDao.queryTeamCPDetailForFullPerformance(parMap);
            }
        }
        map.put("list", list);
        return map;
    }

    /**
     * 团队能力/绩效趋势图- 全屏明细显示
     */
    @Override
    public PaginationDto<TalentMapsPointDto> queryTeamCPFullDetail(TalentMapsTeamQueryDto qDto, PaginationDto<TalentMapsPointDto> dto) {
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList(qDto.getOrganId());
        Map<String, Object> parMap = CollectionKit.newMap();
        parMap.put("customerId", qDto.getCustomerId());
        parMap.put("organId", qDto.getOrganId());
        parMap.put("subOrganIdList", subOrganIdList);
        parMap.put("yearMonth", qDto.getYearMonths());
        parMap.put("sequenceList", CollectionKit.strToList(qDto.getSequenceStr(), ","));
        parMap.put("sequenceSubList", CollectionKit.strToList(qDto.getSequenceSubStr(), ","));
        parMap.put("abilityList", CollectionKit.strToList(qDto.getAbilityStr(), ","));
        parMap.put("performanceList", CollectionKit.strToList(qDto.getPerformanceStr(), ","));
        parMap.put("ageList", qDto.getAgeIntervals());
        parMap.put("sexList", CollectionKit.strToList(qDto.getSexStr(), ","));
        parMap.put("eduList", CollectionKit.strToList(qDto.getEduStr(), ","));
        parMap.put("start", dto.getOffset());
        parMap.put("rows", dto.getLimit());
        int count;
        List<TalentMapsPointDto> list;
//        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        //0为能力，1为绩效
        if ("0".equals(qDto.getViewType())) {
            parMap.put("abilityStr", qDto.getyLaberId());
            count = talentMapsDao.queryTeamCPAbilityEmpCount(parMap);
            list = talentMapsDao.queryTeamCPAbilityEmpPoint(parMap);
        } else {
            parMap.put("performanceStr", qDto.getyLaberId());
            count = talentMapsDao.queryTeamCPPerformanceEmpCount(parMap);
            list = talentMapsDao.queryTeamCPPerformanceEmpPoint(parMap);
        }
        dto.setRecords(count);
        dto.setRows(list);
        return dto;
    }

    /**
     * 盘点报告-人员信息
     */
    @Override
    public List<TalentMapsDto> queryInventoryReportPersonInfo(String customerId, String empId) {
        return talentMapsDao.queryInventoryReportPersonInfo(customerId, empId);
    }

    /**
     * 盘点报告-人才地图轨迹
     */
    @Override
    public Map<String, Object> queryTalentMapTrajectory(String customerId, String empId, String date, Integer rows) {
        Map<String, Object> map = CollectionKit.newMap();
        String maxDate = null;
        if (date != null && !"".equals(date)) {
            String[] dates = date.split(",");
            if (dates != null && dates.length > 0) {
                maxDate = dates[dates.length - 1];
            }
        } else {
            maxDate = DateUtil.getDBNow().substring(0, 7).replaceAll("-", "");
        }
        List<TalentMapsDto> positionList = talentMapsDao.queryPositionInfo(customerId);
        List<TalentMapsDto> list = talentMapsDao.queryTalentMapTrajectory(customerId, empId, maxDate, rows);
        List<String> xData = CollectionKit.newLinkedList();
        List<String> yData = CollectionKit.newLinkedList();
        List<Integer> yVal = CollectionKit.newLinkedList();
        List<Integer> data = CollectionKit.newLinkedList();
        if (positionList != null && positionList.size() > 0) {
            for (TalentMapsDto l : positionList) {
                yData.add(l.getzName());
                yVal.add(Integer.parseInt(l.getCurtName()));
            }
        }
        if (list != null && list.size() > 0) {
            for (TalentMapsDto l : list) {
                xData.add(l.getDate());
                data.add(Integer.parseInt(l.getCurtName()));
            }
        }
        map.put("xData", xData.toArray(new String[xData.size()]));
        map.put("yData", yData.toArray(new String[yData.size()]));
        map.put("yVal", yVal.toArray(new Integer[yVal.size()]));
        map.put("data", data.toArray(new Integer[data.size()]));
        return map;
    }

    /**
     * 盘点报告-绩效轨迹
     */
    @Override
    public Map<String, Object> queryPerformanceTrajectory(String customerId, String empId, Integer rows) {
        Map<String, Object> map = CollectionKit.newMap();
        List<TalentMapsDto> perfList = talentMapsDao.queryPerformanceInfo(customerId);
        List<TalentMapsDto> list = talentMapsDao.queryPerformanceTrajectory(customerId, empId, rows);
        List<String> xData = CollectionKit.newLinkedList();
        List<String> yData = CollectionKit.newLinkedList();
        List<Integer> yVal = CollectionKit.newLinkedList();
        List<Integer> data = CollectionKit.newLinkedList();
        if (perfList != null && perfList.size() > 0) {
            for (TalentMapsDto l : perfList) {
                yData.add(l.getName());
                yVal.add(Integer.parseInt(l.getText()));
            }
        }
        if (list != null && list.size() > 0) {
            for (TalentMapsDto l : list) {
                xData.add(l.getDate());
                data.add(Integer.parseInt(l.getCurtName()));
            }
        }
        map.put("xData", xData.toArray(new String[xData.size()]));
        map.put("yData", yData.toArray(new String[yData.size()]));
        map.put("yVal", yVal.toArray(new Integer[yVal.size()]));
        map.put("data", data.toArray(new Integer[data.size()]));
        return map;
    }

    /**
     * 盘点报告-能力轨迹
     */
    @Override
    public Map<String, Object> queryAbilityTrajectory(String customerId, String empId, Integer rows) {
        Map<String, Object> map = CollectionKit.newMap();
        List<TalentMapsDto> abilityList = talentMapsDao.queryAbilityInfo(customerId);
        List<TalentMapsDto> list = talentMapsDao.queryAbilityTrajectory(customerId, empId, rows);
        List<String> xData = CollectionKit.newLinkedList();
        List<String> yData = CollectionKit.newLinkedList();
        List<Integer> yVal = CollectionKit.newLinkedList();
        List<Integer> data = CollectionKit.newLinkedList();
        if (abilityList != null && abilityList.size() > 0) {
            for (TalentMapsDto l : abilityList) {
                yData.add(l.getName());
                yVal.add(Integer.parseInt(l.getText()));
            }
//            int num = 1;
//            for (int i = abilityList.size() - 1; i >= 0; i--) {
//                yData.add(abilityList.get(i).getName());
//                yVal.add(num);
//                num++;
//            }
        }
        if (list != null && list.size() > 0) {
            for (TalentMapsDto l : list) {
                xData.add(l.getDate());
                data.add(Integer.parseInt(l.getCurtName()));
            }
        }
        map.put("xData", xData.toArray(new String[xData.size()]));
        map.put("yData", yData.toArray(new String[yData.size()]));
        map.put("yVal", yVal.toArray(new Integer[yVal.size()]));
        map.put("data", data.toArray(new Integer[data.size()]));
        return map;
    }

    /**
     * 盘点报告-异动轨迹
     */
    @Override
    public Map<String, Object> queryChangeTrajectory(String customerId, String empId, Integer rows) {
        Map<String, Object> map = CollectionKit.newMap();
        List<TalentMapsDto> rankList = talentMapsDao.queryRankInfo(customerId);
        List<TalentMapsDto> list = talentMapsDao.queryChangeTrajectory(customerId, empId, rows);
        List<String> xData = CollectionKit.newLinkedList();
        List<String> yData = CollectionKit.newLinkedList();
        List<Integer> yVal = CollectionKit.newLinkedList();
        List<Object> data = CollectionKit.newLinkedList();
        if (rankList != null && rankList.size() > 0) {
            for (TalentMapsDto l : rankList) {
                yData.add(l.getName());
                yVal.add(Integer.parseInt(l.getCurtName()));
            }
        }
        if (list != null && list.size() > 0) {
            for (TalentMapsDto l : list) {
                xData.add(l.getDate());
                Map<String, Object> mMap = CollectionKit.newMap();
                mMap.put("name", l.getRankName());
                mMap.put("value", Integer.parseInt(l.getCurtName()));
                mMap.put("abilityName", l.getName());
                data.add(mMap);
            }
        }
        map.put("xData", xData.toArray(new String[xData.size()]));
        map.put("yData", yData.toArray(new String[yData.size()]));
        map.put("yVal", yVal.toArray(new Integer[yVal.size()]));
        map.put("data", data.toArray(new Object[data.size()]));
        return map;
    }

    /**
     * 根据页面显示要求，获取最大最小日期
     */
    @Override
    public String getMinMaxDate(List<String> organIds, String customerId) {
//        List<String> list = talentMapsDao.queryTimesCycle(customerId);
        List<KVItemDto> list = talentMapsDao.queryTalentMapsDateCycle(organIds, customerId);
        String minYearMonth = "", maxYearMonth = "";
        if (list != null && list.size() > 0) {
            if (list.size() >= 3) {
                minYearMonth = list.get(2).getK();
            } else {
                minYearMonth = list.get(list.size() - 1).getK();
            }
            maxYearMonth = list.get(0).getK();
        }
        return minYearMonth + "," + maxYearMonth;
    }

    /**
     * 根据最大最小时间获取所有时间
     */
    public List<String> getFullDates(String date) {
        List<String> list = CollectionKit.newLinkedList();
        if (date != null && !"".equals(date) && date.indexOf(",") != -1) {
            String[] dates = date.split(",");
            for (int i = Integer.parseInt(dates[0]); i <= Integer.parseInt(dates[2]); i++) {
                list.add(i + "06#" + i + "年上半年");
                list.add(i + "12#" + i + "年下半年");
            }
            if (Integer.parseInt(dates[1]) == 2) {
                list.remove(0);
            }
            if (Integer.parseInt(dates[3]) == 1) {
                list.remove(list.size() - 1);
            }
        }
        return list;
    }

    /**
     * 转换“1”、“2”为相应的月份
     */
    public String changeNumToMonth(String date) {
        String month = "";
        if (date != null && !"".equals(date)) {
            if ("1".equals(date)) {
                month = "06";
            } else if ("2".equals(date)) {
                month = "12";
            }
        }
        return month;
    }

    /**
     * 转换“上半年”、“下半年”为相应的月份
     */
    public String changeCnDateToMonth(String date) {
        String newDate = "";
        if (date != null && !"".equals(date)) {
            String year = date.substring(0, 4);
            String cn = date.substring(5);
            String month = "";
            if ("上半年".equals(cn)) {
                month = "06";
            } else if ("下半年".equals(cn)) {
                month = "12";
            }
            newDate = year + month;
        }
        return newDate;
    }

    /**
     * 转换“1”、“2”为相应的“上半年”、“下半年”
     */
    public String changeNumToCn(int num) {
        String newDate = "";
        if (num == 1) {
            newDate = "上半年";
        } else if (num == 2) {
            newDate = "下半年";
        }
        return newDate;
    }

    public List<TalentMapsDto> checkEmpInfo(String customerId, String keyName) {
        return talentMapsDao.checkEmpInfo(customerId, keyName);
    }

    /**
     * 审核-调整人才地图
     */
    @Transactional
    public List<TalentMapsDto> updateAuditingInfo(List<TalentMapsDto> listDto, String customerId, String empId, String userId, String modifyTime,
                                                  String topId, String fullPath, String publishTime, List<OrganDto> topOrgan) {
        List<TalentMapsDto> list = CollectionKit.newList();
        List<TalentMapsDto> ability = talentMapsDao.queryAbilityInfo(customerId);
        List<TalentMapsDto> performance = talentMapsDao.queryPerformanceInfo(customerId);
        for (TalentMapsDto dto : listDto) {
            for (TalentMapsDto a : ability) {
                if (dto.getyDataAf().equals(a.getText())) {
                    dto.setYaxisAf(a.getId());
                }
            }
            for (TalentMapsDto p : performance) {
                if (dto.getxDataAf().equals(p.getText())) {
                    dto.setXaxisAf(p.getId());
                }
            }
            dto.setUpdateTime(modifyTime);
            dto.setFlag(1);
            dto.setCustomerId(customerId);
            dto.setAdjustmentId(userId);
            dto.setYearMonth(Integer.parseInt(publishTime));
            list.add(dto);
        }
        try {
            if (list.size() > 0)
                talentMapsDao.updateAuditingInfo(list);
            Map<String, Object> map = CollectionKit.newMap();
            map.put("customerId", customerId);
            map.put("adjustmentId", userId);
            map.put("empId", empId);
            map.put("modifyTime", modifyTime);
            map.put("topId", topId);
            map.put("time", publishTime);
            map.put("flag", 1);
            talentMapsDao.updateMapsManagement(map);
            int con = talentMapsDao.queryPublishMaps(customerId, topId, publishTime);  // 查询是否存在人才地图发布
            if (con <= 0) {
                Map<String, Object> param = CollectionKit.newMap();
                param.put("empId", empId);
                param.put("topId", topId);
                param.put("customerId", customerId);
                param.put("updateTime", modifyTime);
                param.put("fullPath", fullPath);
                param.put("time", publishTime);
                talentMapsDao.addPublishMaps(param);
            }
            List<OrganDto> config = talentMapsDao.queryConfigTalentMaps(customerId);
            List<OrganDto> organPermit = CollectionKit.newList();
            for (OrganDto dto : config) {
                boolean has = false;
                for (OrganDto organ : topOrgan) {
                    if (dto.getFullPath().indexOf(organ.getFullPath()) != -1) {
                        has = true;
                    }
                }
                if (has)
                    organPermit.add(dto);
            }
            Map<String, Object> auditingMap = CollectionKit.newMap();
            auditingMap.put("customerId", customerId);
            auditingMap.put("yearMonth", publishTime);
            auditingMap.put("topOrgan", organPermit);
            auditingMap.put("status", 0);
            List<TalentMapsDto> result = talentMapsDao.queryTalentMapsAuditing(auditingMap);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发布人才地图
     */
    @Transactional
    public List<TalentMapsDto> pulishMapsManagement(List<TalentMapsDto> listDto, String customerId, String empId, String releaseTime, String topId, String publishTime) {
        List<TalentMapsDto> list = CollectionKit.newList();
        List<TalentMapsDto> ability = talentMapsDao.queryAbilityInfo(customerId);
        List<TalentMapsDto> performance = talentMapsDao.queryPerformanceInfo(customerId);
        for (TalentMapsDto dto : listDto) {
            for (TalentMapsDto a : ability) {
                if (dto.getyDataAf().equals(a.getText())) {
                    dto.setYaxisAf(a.getId());
                }
            }
            for (TalentMapsDto p : performance) {
                if (dto.getxDataAf().equals(p.getText())) {
                    dto.setXaxisAf(p.getId());
                }
            }
            dto.setUpdateTime(releaseTime);
            dto.setFlag(1);
            dto.setCustomerId(customerId);
            if ("1".equals(dto.getText()))
                dto.setReleaseId(empId);
            else
                dto.setReleaseId(null);

            dto.setYearMonth(Integer.parseInt(publishTime));
            list.add(dto);
        }
        try {
            if (list.size() > 0) {
                talentMapsDao.updatePublishInfo(list);
            }
            Map<String, Object> map = CollectionKit.newMap();
            map.put("customerId", customerId);
            map.put("empId", empId);
            map.put("releaseTime", releaseTime);
            map.put("topId", topId);
            map.put("time", publishTime);
            map.put("flag", 2);
            talentMapsDao.pulishMapsManagement(map);

            // 调用过程插入map_talent_result表里 by jxzhang on 2016-10-12
           /* Map<String, Object> mapRs = CollectionKit.newMap();
            mapRs.put("p_customer_id", customerId);
            mapRs.put("p_top_id", topId);
            mapRs.put("p_ym", Integer.parseInt(publishTime));
            Integer error_msg = talentMapsDao.callMapTalentResult(mapRs);
            if (error_msg == 1) {
                //TODO 抛入ExceptionDao,让它去logger.error();
                log.error("调用 CALL pro_update_map_talent_result(), 过程里回滚  map_talent_result 数据更新失败");
            }*/
            List<TalentMapsDto> mapResult = talentMapsDao.queryTalentmapsResult(customerId, topId, Integer.parseInt(publishTime));
            if(mapResult != null && mapResult.size() > 0) {
            	try {
            		talentMapsDao.insertMapTalentResult(mapResult);
            	} catch (Exception e) {
            		log.error("map_talent_result 数据更新失败");
                }
            }

            Map<String, Object> publishMap = CollectionKit.newMap();
            publishMap.put("customerId", customerId);
            publishMap.put("yearMonth", publishTime);
            publishMap.put("status", 1);
            publishMap.put("empId", empId);
            return talentMapsDao.queryTalentMapsPublish(publishMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取z轴纬度
     *
     * @param customerId
     * @return
     */
    public List<TalentMapsDto> queryZInfo(String customerId) {
        return talentMapsDao.queryZInfo(customerId);
    }

    public int queryTalentPerformanceAbilityCount(String customerId, String topId, int flag, String zName, Integer yearMonth) {
        Map<String, Object> param = CollectionKit.newMap();
        param.put("customerId", customerId);
        param.put("topId", topId);
        param.put("flag", flag);
        param.put("yearMonth", yearMonth);
        param.put("zName", zName);
        return talentMapsDao.queryTalentPerformanceAbilityCount(param);
    }

    /**
     * 查询历史审核列表
     */
    public List<TalentMapsDto> queryHistoricalAuditing(String customerId, List<OrganDto> topOrgan, String empId) {
        /*List<OrganDto> config = talentMapsDao.queryConfigTalentMaps(customerId);
        List<OrganDto> organPermit = CollectionKit.newList();
        for (OrganDto dto : config) {
            boolean has = false;
            for (OrganDto organ : topOrgan) {
                if (dto.getFullPath().indexOf(organ.getFullPath()) != -1) {
                    has = true;
                }
            }
            if (has)
                organPermit.add(dto);
        }
        if (organPermit != null && organPermit.size() > 0)*/
        if (topOrgan != null && topOrgan.size() > 0)
            return talentMapsDao.queryHistoricalAuditing(customerId, topOrgan, empId);
        else
            return null;
    }

    /**
     * 查询历史
     */
    public TalentMapsDto queryMapManage(String customerId, String id, String topId, Integer yearMonth) {
        return talentMapsDao.queryMapManage(customerId, id, topId, yearMonth);
    }

    /**
     * 查询地图坐标
     */
    public List<TalentMapsDto> queryMapsPreview(String customerId, String topId, String flag, Integer yearMonth) {
        Map<String, Object> param = CollectionKit.newMap();
        param.put("customerId", customerId);
        param.put("topId", topId);
        param.put("yearMonth", yearMonth);
        param.put("flag", flag);
        List<TalentMapsDto> list = talentMapsDao.queryMapsPreview(param);
        return list;
    }

    /**
     * 查询能力信息
     * return
     * curtName	abilityNumberName
     */
    public List<TalentMapsDto> queryAbilityInfo(String customerId) {
        return talentMapsDao.queryAbilityInfo(customerId);
    }

    /**
     * 查询绩效信息
     * return
     * curtName	performanceName
     */
    public List<TalentMapsDto> queryPerformanceInfo(String customerId) {
        return talentMapsDao.queryPerformanceInfo(customerId);
    }

    /**
     * 检查是否有发布地图权限
     */
    public int checkPublishPermission(String customerId, String topId, String empId, String time) {
        return talentMapsDao.checkPublishPermission(customerId, topId, empId, time);
    }
}

package net.chinahrd.recruitBoard.mvc.pc.service.impl;

import net.chinahrd.core.tools.collection.CollectionKit;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.manage.HomeConfigDto;
import net.chinahrd.entity.dto.pc.recruitBoard.*;
import net.chinahrd.mvc.pc.service.admin.FunctionService;
import net.chinahrd.mvc.pc.service.common.CommonService;
import net.chinahrd.recruitBoard.mvc.pc.dao.RecruitBoardDao;
import net.chinahrd.recruitBoard.mvc.pc.service.RecruitBoardService;
import net.chinahrd.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Timestamp;
import java.util.*;

/**
 * 招聘看板Service实现类
 * Created by wqcai on 16/5/5.
 */
@Service("recruitBoardService")
public class RecruitBoardServiceImpl implements RecruitBoardService {
    private static Logger log = LoggerFactory.getLogger(RecruitBoardServiceImpl.class);

    @Autowired
    private RecruitBoardDao recruitBoardDao;

    @Autowired
    private FunctionService functionService;
    @Autowired
    private CommonService commonService;

    @Override
    public String findEmpPQMaxDate(String customerId) {
        return recruitBoardDao.findEmpPQMaxDate(customerId);
    }

    @Override
    public Map<String, Object> queryWaitRecruitPost(String organId, String customerId) {
        int year = new DateTime(DateUtil.getDate()).getYear();
        Map<String, Object> map = CollectionKit.newMap();
        List<RecruitPositionMeetRateDto> dtos = recruitBoardDao.queryWaitRecruitPost(organId, year, customerId);
        map.put("year", year);
        map.put("dtos", dtos);
        return map;
    }

    @Override
    public Map<String, Object> queryWaitRecruitNum(String organId, String customerId) {
        int year = new DateTime(DateUtil.getDate()).getYear();
        Map<String, Object> map = CollectionKit.newMap();
        Integer recruitNum = recruitBoardDao.queryWaitRecruitNum(organId, year, customerId);
        map.put("year", year);
        map.put("recruitNum", recruitNum != null ? recruitNum : 0);
        return map;
    }

    @Override
    public Map<String, Object> queryRecruitCostAndBudget(String organId, String customerId) {
        int year = new DateTime(DateUtil.getDate()).getYear();
        Map<String, Object> map = CollectionKit.newMap();
        Map<String, Double> recruit = recruitBoardDao.queryRecruitCostAndBudget(organId, year, customerId);
        double outlay = recruit != null && recruit.get("outlay") != null ? recruit.get("outlay") : 0d;
        double budgetValue = recruit != null && recruit.get("budgetValue") != null ? recruit.get("budgetValue") : 0d;

        map.put("year", year);
        map.put("outlay", outlay);
        map.put("outlayRate", budgetValue != 0d ? ArithUtil.div(outlay, budgetValue, 4) : 0d);
        return map;
    }

    @Override
    public List<RecruitPositionMeetRateDto> queryPositionMeetRate(String organId, String empId, String customerId) {
        Timestamp currDate = DateUtil.getTimestamp();
        int year = new DateTime(DateUtil.getDate()).getYear();
        return recruitBoardDao.queryPostMeetRate(organId, currDate, year, customerId);
    }

    @Override
    public List<RecruitPositionMeetRateDto> queryPositionMeetRate(String organId, String quotaId, String empId, String customerId) {
        Timestamp currDate = DateUtil.getTimestamp();
        int year = new DateTime(DateUtil.getDate()).getYear();
        List<RecruitPositionMeetRateDto> dtos = recruitBoardDao.queryPostMeetRate(organId, currDate, year, customerId);
        List<HomeConfigDto> configDtos = functionService.queryUserHomeConfig(quotaId, empId, customerId);
        if (configDtos == null || (configDtos != null && configDtos.size() == 0)) {
            return dtos;
        }
        //有排序的dto集合
        List<RecruitPositionMeetRateDto> sortDtos = CollectionKit.newLinkedList();
        //先把已排序的添加进去
        for (HomeConfigDto configDto : configDtos) {
            for (RecruitPositionMeetRateDto dto : dtos) {
                if (configDto.getCardCode().equals(dto.getId())) {
                    dto.setFunctionConfigId(configDto.getFunctionConfigId());
                    dto.setView(configDto.isView());
                    sortDtos.add(dto);
                    break;
                }
            }
        }
        //假如之前的全无匹配,则返回所有新的数据
        if (sortDtos.size() == 0) return dtos;

        for (RecruitPositionMeetRateDto dto : dtos) { //取出未排序的新数据
            boolean hasExist = false;
            for (RecruitPositionMeetRateDto hasSortDto : sortDtos) {
                if (dto.getId().equals(hasSortDto.getId())) {
                    hasExist = true;
                    break;
                }
            }
            if (hasExist) continue;

            if(dto.isWarn()){
                int length = sortDtos.size();
                for (int j = 0; j < length; j++) {
                    RecruitPositionMeetRateDto newDto = sortDtos.get(j);
                    RecruitPositionMeetRateDto nextDto = j + 1 >= length ? null : sortDtos.get(j + 1);
                    if(nextDto == null){
                        sortDtos.add(dto);
                    }else if (newDto.isWarn() && !nextDto.isWarn()){
                        sortDtos.add(j + 1, dto);
                    }
                }
            }else{
                sortDtos.add(dto);
            }
            //新数据按排序规则插入到相关位置中

        }
        return sortDtos;
    }

    @Override
    public PaginationDto<RecruitPositionResultDto> queryPositionResult(String publicId, Integer type, PaginationDto<RecruitPositionResultDto> dto, String customerId) {
        int year = new DateTime(DateUtil.getDate()).getYear();
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        int count = recruitBoardDao.findPositionResultCount(publicId, year, type, customerId);
        List<RecruitPositionResultDto> list = recruitBoardDao.queryPositionResult(publicId, year, type, customerId, rowBounds);
        dto.setRows(list);
        dto.setRecords(count);
        return dto;
    }

    @Override
    public void updatePositionMeetRateSequence(String sequenceStr, String quotaId, String empId, String customerId) {
        functionService.editUserHomeConfig(sequenceStr, quotaId, empId, customerId);
    }

    @Override
    public List<RecruitChannelResultDto> queryRecruitChannel(String keyName, String organId, String customerId) {
        int year = new DateTime(DateUtil.getDate()).getYear();
        int cycleDays = queryDismisstionWeekCycleDays(customerId);
        List<RecruitChannelQueryDto> dtos = recruitBoardDao.queryRecruitChannel(keyName, organId, cycleDays, year, customerId);
        List<RecruitChannelResultDto> resultDtos = CollectionKit.newList();
        for (RecruitChannelQueryDto dto : dtos) {
            boolean hasParent = false;
            for (RecruitChannelResultDto resultDto : resultDtos) {
                if (resultDto.getChannelId().equals(dto.getPositionId())) {
                    hasParent = true;
                    break;
                }
            }
            //确认父级(岗位)是否存在,存在则跳过,不存在则添加父级(岗位)
            if (!hasParent) {
                RecruitChannelResultDto parentDto = new RecruitChannelResultDto();
                parentDto.setChannelId(dto.getPositionId());
                parentDto.setChannelName(dto.getPositionName());
                parentDto.setDays(dto.getPositionDays());
                parentDto.setEmployNum(dto.getPostionEmployNum());
                int employNum = dto.getPostionEmployNum(), dimissionNum = 0;
                double outlay = 0d;
                for (RecruitChannelQueryDto subDto : dtos) {
                    if (dto.getPositionId().equals(subDto.getPositionId())) {
                        outlay = ArithUtil.sum(outlay, subDto.getOutlay());
                        dimissionNum += subDto.getDimissionNum();
                    }
                }
                parentDto.setDimissionRate(employNum != 0 ? ArithUtil.div(dimissionNum, employNum, 4) : 0);
                parentDto.setOutlay(employNum != 0 ? ArithUtil.div(outlay, employNum, 4) : 0);
                resultDtos.add(parentDto);
            }
            //添加渠道
            int employNum = dto.getEmployNum();
            RecruitChannelResultDto currDto = new RecruitChannelResultDto();
            currDto.setChannelId(dto.getChannelId());
            currDto.setChannelName(dto.getChannelName());
            currDto.setParent(dto.getPositionId());
            currDto.setDays(dto.getDays());
            currDto.setDimissionRate(employNum != 0 ? ArithUtil.div(dto.getDimissionNum(), employNum, 4) : 0);
            currDto.setOutlay(employNum != 0 ? ArithUtil.div(dto.getOutlay(), employNum, 4) : 0);
            currDto.setEmployNum(employNum);
            resultDtos.add(currDto);
        }
        return setTree(resultDtos);
    }

    private List<RecruitChannelResultDto> setTree(List<RecruitChannelResultDto> data) {
        List<RecruitChannelResultDto> tem = new LinkedList<>();
        for (RecruitChannelResultDto d : data) {
            if (null == d.getParent()) {
                d.setId(d.getChannelId());
                d.setLevel("0");
                d.setExpanded("true");
                d.setIsLeaf(hasLeaf(data, d.getChannelId().toString()));
                tem.add(d);
                setLevel(tem, data, d.getChannelId(), 0);
            } else {
                d.setId(UUID.randomUUID().toString());
            }
        }
        return tem;
    }

    private void setLevel(List<RecruitChannelResultDto> tem, List<RecruitChannelResultDto> data, String id, Integer level) {
        level++;
        for (RecruitChannelResultDto d : data) {
            if (id.equals(d.getParent())) {
                d.setExpanded("true");
                d.setLevel(level.toString());
                d.setIsLeaf(hasLeaf(data, d.getChannelId().toString()));
                tem.add(d);
                setLevel(tem, data, d.getChannelId(), level);
            }
        }
    }

    private String hasLeaf(List<RecruitChannelResultDto> data, String id) {
        boolean b = true;
        for (RecruitChannelResultDto d : data) {
            if (id.equals(d.getParent())) {
                b = false;
                break;
            }
        }
        return b ? "true" : "false";
    }

    @Override
    public List<RecruitPositionPayDto> queryPositionPay(String keyName, String organId, String customerId) {
        int year = new DateTime(DateUtil.getDate()).getYear();
        //获取解密钥匙
        String cryptKey = EisWebContext.getCryptKey();
        String maxYearMonth = recruitBoardDao.findPositionPayMaxYearMonth(customerId);
        return recruitBoardDao.queryPositionPay(keyName, organId, cryptKey, year, maxYearMonth, customerId);
    }

    @Override
    public int queryDismisstionWeekCycleDays(String customerId) {
        int type = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.ZPKB_rodWeek);
        Integer cycleDays = recruitBoardDao.queryDismisstionWeekCycleDays(type, customerId);
        return cycleDays == null ? 0 : cycleDays;
    }

    @Override
    public List<RecruitDismissionWeekDto> queryDismissionRate(Integer employNum, String channelId, String parent, String organId, String customerId) {
        if (employNum == null) employNum = 0;

        int year = new DateTime(DateUtil.getDate()).getYear();
        Timestamp currDate = DateUtil.getTimestamp();
        Map<String, Object> map = CollectionKit.newMap();
        map.put("employNum", employNum);
        map.put("year", year);
        map.put("channelId", channelId);
        map.put("currDate", currDate);
        map.put("parent", parent);
        map.put("organId", organId);
        map.put("customerId", customerId);
        return recruitBoardDao.queryDismissionRate(map);
    }

    @Override
    public List<RecruitJobChangeTotalDto> queryRecruitChange(List<Integer> changeType, String organId, String customerId) {
        int year = new DateTime(DateUtil.getDate()).getYear();

        List<RecruitJobChangeTotalDto> list = CollectionKit.newList();
        for (Integer type : changeType) {
            RecruitJobChangeTotalDto dto = recruitBoardDao.queryRecruitChange(type, year, organId, customerId);
            if (null != dto) {
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    public List<RecruitTagDto> queryImagesPerformanceTags(String customerId) {
        return recruitBoardDao.queryImagesPerformanceTags(customerId);
    }

    @Override
    public List<RecruitTagDto> queryImagesQueryTags(String position, String customerId) {
        return recruitBoardDao.queryImagesQueryTags(position, customerId);
    }


    @Override
    public int queryPositionPerfEmpCount(String position, Integer yearNum, Integer continueNum, Integer star, String customerId) {
        int year = Integer.valueOf(DateUtil.getDBTime("yyyy")).intValue();

        Map<String, Object> queryMap = CollectionKit.newMap();
        queryMap.put("position", position);
        queryMap.put("continueNum", continueNum);
        queryMap.put("star", star);
        queryMap.put("year", year - yearNum);
        queryMap.put("customerId", customerId);

        return recruitBoardDao.queryPositionPerfEmpCount(queryMap);
    }

    @Override
    public List<RecruitTagDto> queryPositionImages(String position, Integer yearNum, Integer continueNum, Integer star, String customerId) {
        int year = Integer.valueOf(DateUtil.getDBTime("yyyy")).intValue();
        Map<String, Object> queryMap = CollectionKit.newMap();
        queryMap.put("position", position);
        queryMap.put("continueNum", continueNum);
        queryMap.put("star", star);
        queryMap.put("year", year - yearNum);
        queryMap.put("customerId", customerId);
        List<RecruitHighPerfEmpDto> list = recruitBoardDao.queryHighPerfImagesEmps(queryMap);
        if (list.size() == 0) {
            return null;
        }
        Map<String, Object> baseMap = getBaseTag(list);
        List<String> emps = (List<String>) baseMap.get("emps");
        List<RecruitTagDto> tagList = (List<RecruitTagDto>) baseMap.get("tagList");
        //获取岗位能力素质维度标签
        List<RecruitTagDto> qualityTag = getQualityTag(position, emps, customerId);
        //查询优势标签
        List<RecruitTagDto> superiorityTags = recruitBoardDao.querySuperiorityTags(emps, customerId);

        tagList.addAll(qualityTag);
        tagList.addAll(superiorityTags);
        return tagList;
    }

    /**
     * 基础标签
     *
     * @param list
     * @return
     */
    private Map<String, Object> getBaseTag(List<RecruitHighPerfEmpDto> list) {
        List<String> emps = CollectionKit.newList();
        int len = list.size();
        double manTotal = 0.0, womanTotal = 0.0;

        MultiValueMap<String, String> schoolMap = new LinkedMultiValueMap<String, String>();
        MultiValueMap<String, String> degreeListMap = new LinkedMultiValueMap<String, String>();
        Map<String, String> degreeMap = CollectionKit.newMap();
        for (RecruitHighPerfEmpDto dto : list) {
            String empId = dto.getEmpId();
            emps.add(empId);
            if ("m".equals(dto.getSex())) {
                manTotal++;
            } else {
                womanTotal++;
            }
            schoolMap.add(dto.getSchoolType(), empId);
            degreeListMap.add(dto.getDegreeId(), empId);
            degreeMap.put(dto.getDegreeId(), dto.getDegree());
        }
        //性别
        RecruitTagDto sexDto = new RecruitTagDto();
        if (manTotal > womanTotal) {
            sexDto.setTagId("m");
            sexDto.setTagName("男生");
            sexDto.setTagVal(ArithUtil.div(manTotal, len, 4));
        } else {
            sexDto.setTagId("w");
            sexDto.setTagName("女生");
            sexDto.setTagVal(ArithUtil.div(womanTotal, len, 4));
        }
        //学校类型
        int schoolNum = 0;
        String type = null;
        for (Map.Entry<String, List<String>> empEntry : schoolMap.entrySet()) {
            int schoolSize = empEntry.getValue().size();
            if (schoolSize > schoolNum) {
                schoolNum = schoolSize;
                type = empEntry.getKey();
            }
        }
        RecruitTagDto schoolDto = new RecruitTagDto();
        schoolDto.setTagId(type);
        schoolDto.setTagName(type);
        schoolDto.setTagVal(ArithUtil.div(schoolNum, len, 4));
        //专业
        int degreeNum = 0;
        String degreeName = null;
        String degreeId = null;
        for (Map.Entry<String, List<String>> empEntry : degreeListMap.entrySet()) {
            int degreeSize = empEntry.getValue().size();
            if (degreeSize > degreeNum) {
                degreeNum = degreeSize;
                degreeId = empEntry.getKey();
                degreeName = degreeMap.get(degreeId);
            }
        }
        RecruitTagDto degreeDto = new RecruitTagDto();
        degreeDto.setTagId(degreeId);
        degreeDto.setTagName(degreeName);
        degreeDto.setTagVal(ArithUtil.div(degreeNum, len, 4));


        List<RecruitTagDto> tagList = CollectionKit.newList();
        sexDto.setTagScore(11d);
        schoolDto.setTagScore(12d);
        degreeDto.setTagScore(13d);
        tagList.add(sexDto);
        tagList.add(schoolDto);
        tagList.add(degreeDto);

        Map<String, Object> map = CollectionKit.newMap();
        map.put("emps", emps);
        map.put("tagList", tagList);
        return map;
    }

    /**
     * 岗位能力素质标签
     *
     * @param position
     * @param emps
     * @param customerId
     * @return
     */
    private List<RecruitTagDto> getQualityTag(String position, List<String> emps, String customerId) {
        String date = findEmpPQMaxDate(customerId);
        List<RecruitEmpQualityTagDto> empQualityTags = recruitBoardDao.queryEmpQualityTags(position, emps, date, customerId);
        //获得每个员工前5
        MultiValueMap<String, RecruitTagDto> empTags = new LinkedMultiValueMap<String, RecruitTagDto>();
        for (RecruitEmpQualityTagDto empTagDto : empQualityTags) {
            String empId = empTagDto.getEmpId();
            List<RecruitTagDto> tags = empTags.get(empId);
            if (tags != null && tags.size() >= 5) {
                continue;
            }
            RecruitTagDto tagDto = new RecruitTagDto();
            tagDto.setTagId(empTagDto.getQualityId());
            tagDto.setTagName(empTagDto.getVocabulary());
            tagDto.setTagScore(empTagDto.getShowIndex());
            empTags.add(empId, tagDto);
        }
        //获得每条标签的员工数
        Map<String, RecruitTagDto> map = CollectionKit.newMap();
        MultiValueMap<String, String> qualityTags = new LinkedMultiValueMap<String, String>();
        for (Map.Entry<String, List<RecruitTagDto>> empEntry : empTags.entrySet()) {
            List<RecruitTagDto> tags = empEntry.getValue();
            for (RecruitTagDto dto : tags) {
                String tagId = dto.getTagId();
                qualityTags.add(tagId, empEntry.getKey());
                if (!map.containsKey(tagId)) { //不存在则保存
                    map.put(tagId, dto);
                }
            }
        }
        //获取所有标签并排序
        List<RecruitTagDto> qualitylist = CollectionKit.newList();
        for (Map.Entry<String, List<String>> qualityEntry : qualityTags.entrySet()) {
            String tagId = qualityEntry.getKey();
            RecruitTagDto tagDto = map.get(tagId);
            tagDto.setTagVal(qualityEntry.getValue().size() + 0d);
            tagDto.setTagType(1);
            qualitylist.add(tagDto);
        }
        Collections.sort(qualitylist, CompareUtil.createComparator(1, "tagVal", "tagName"));
        //取前三
        List<RecruitTagDto> resultDtos = CollectionKit.newList();
        for (int i = 0; i < qualitylist.size() && i < 3; i++) {
            resultDtos.add(qualitylist.get(i));
        }
        return resultDtos;
    }


    @Override
    public PaginationDto<RecruitJobChangeDto> queryRecruitChangeList(Integer changeType, String organId, String customerId, PaginationDto<RecruitJobChangeDto> dto) {
        int year = new DateTime(DateUtil.getDate()).getYear();
        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        List<RecruitJobChangeDto> list = recruitBoardDao.queryRecruitChangeList(changeType, year, organId, customerId, rowBounds);
        dto.setRows(list);
        return dto;
    }

    @Override
    public int queryPositionRecommendCount(String sex, String degreeId, String schoolType, List<String> contents, List<KVItemDto> qualitys, List<String> organIds, String customerId) {
        Map<String, Object> queryMap = getPositionRecommendQueryMap(sex, degreeId, schoolType, contents, qualitys, organIds, customerId);

        int count = recruitBoardDao.queryPositionRecommendCount(queryMap);
        return count;
    }


    @Override
    public PaginationDto<RecruitRecommendEmpDto> queryPositionRecommendEmp(String sex, String degreeId, String schoolType, List<String> contents, List<KVItemDto> qualitys, List<String> organIds, String customerId, PaginationDto<RecruitRecommendEmpDto> dto) {

        RowBounds rowBounds = new RowBounds(dto.getOffset(), dto.getLimit());
        Map<String, Object> queryMap = getPositionRecommendQueryMap(sex, degreeId, schoolType, contents, qualitys, organIds, customerId);

        int count = recruitBoardDao.queryPositionRecommendCount(queryMap);
        List<RecruitRecommendEmpDto> list = recruitBoardDao.queryPositionRecommendEmp(queryMap, rowBounds);
        dto.setRows(list);
        dto.setRecords(count);

        return dto;
    }


    private Map<String, Object> getPositionRecommendQueryMap(String sex, String degreeId, String schoolType, List<String> contents, List<KVItemDto> qualitys, List<String> organIds, String customerId) {

        Map<String, Object> queryMap = CollectionKit.newMap();
        int total = 0;
        if (!StringUtils.isEmpty(sex)) {
            total++;
        } else {
            sex = null;
        }
        if (!StringUtils.isEmpty(degreeId)) {
            total++;
        } else {
            degreeId = null;
        }
        if (!StringUtils.isEmpty(schoolType)) {
            total++;
        } else {
            schoolType = null;
        }
        if (contents != null && contents.size() > 0) {
            total = total + contents.size();
        }
        if (qualitys != null && qualitys.size() > 0) {
            total = total + qualitys.size();
        }
        queryMap.put("sex", sex);
        queryMap.put("degreeId", degreeId);
        queryMap.put("schoolType", schoolType);
        queryMap.put("contents", contents);
        queryMap.put("qualitys", qualitys);
        queryMap.put("total", total);
        queryMap.put("organIds", organIds);
        queryMap.put("customerId", customerId);
        return queryMap;
    }
}

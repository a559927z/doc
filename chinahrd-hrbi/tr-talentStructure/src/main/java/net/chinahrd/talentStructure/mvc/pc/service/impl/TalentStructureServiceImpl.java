package net.chinahrd.talentStructure.mvc.pc.service.impl;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.DegreeDto;
import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import net.chinahrd.entity.dto.pc.common.TableGridDto;
import net.chinahrd.entity.dto.pc.talentStructure.TalentStructureDto;
import net.chinahrd.entity.dto.pc.talentStructure.TalentStructureResultDto;
import net.chinahrd.entity.dto.pc.talentStructure.TalentStructureResultSubDto;
import net.chinahrd.entity.enums.CompanyAgeEnum;
import net.chinahrd.module.SysCache;
import net.chinahrd.talentStructure.module.Cache;
import net.chinahrd.talentStructure.mvc.pc.dao.TalentStructureDao;
import net.chinahrd.talentStructure.mvc.pc.service.TalentStructureService;
import net.chinahrd.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.Map.Entry;

/**
 * 人力结构Service
 *
 * @author jxzhang by 2016-02-21
 */
@Service("talentStructureService")
public class TalentStructureServiceImpl implements TalentStructureService {

    @Autowired
    private TalentStructureDao talentStructureDao;

    @Override
    public TalentStructureDto findBudgetAnalyse(String organId, String customerId) {

        List<Integer> personTypeKey = CacheHelper.getConfigValsByCacheInt(ConfigKeyUtil.RLCB_personType);

        TalentStructureDto dto = talentStructureDao.findBudgetAnalyse(organId, customerId, DateUtil.getDBCurdate(), personTypeKey);
        if (null == dto) {
            return null;
        }
        Double empCount = dto.getEmpCount();
        Double number = dto.getNumber();
        if (null == number) {
            number = 0.0;
            dto.setHasBudgetPer(false);
        } else {
            dto.setHasBudgetPer(true);
        }
        dto.setUsableEmpCount(number - empCount);

        if (0.0 == empCount && 0.0 == number
                || 0.0 == empCount
                || 0.0 == number) {
            dto.setBudgetPer(0.0);
        } else {
            dto.setBudgetPer(empCount / number);
        }
        Double normal = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.RLJG_NORMAL);
        Double risk = CacheHelper.getConfigValByCacheDouble(ConfigKeyUtil.RLJG_RISK);
        dto.setNormal(normal);
        dto.setRisk(risk);
        return dto;
    }

    @Override
    public boolean updateConfigWarnVal(String customerId, Double normal, Double risk) {
        try {
            if (null != normal && null != risk) {
                talentStructureDao.updateConfigWarnValByNormal(customerId, normal);
                talentStructureDao.updateConfigWarnValByRisk(customerId, risk);
                //更新缓存数据
                SysCache.config.update();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public Map<String, Integer> queryAbEmpCountBarBySeqId(String organId, String customerId, String seqId) {
        Map<String, Integer> rsMap = new LinkedHashMap<>();
        List<TalentStructureDto> rsList = talentStructureDao.queryAbEmpCountBarBySeqId(organId, customerId, seqId);

        MultiValueMap<String, TalentStructureDto> m = new LinkedMultiValueMap<String, TalentStructureDto>();
        for (TalentStructureDto dto : rsList) {
            if (null != dto.getAbCurtName()) {
                m.add(dto.getAbCurtName(), dto);
            }
        }
        for (Entry<String, List<TalentStructureDto>> entry : m.entrySet()) {
            String key = entry.getKey();
            List<TalentStructureDto> groupDtos = entry.getValue();

            rsMap.put(key, groupDtos.size());
        }
        return rsMap;
    }


    @Override
    public Map<String, Object> getTalentStuctureData(String organId, String customerId) {
        Map<String, Object> rs = new LinkedHashMap<String, Object>();

        List<TalentStructureDto> rsDb = talentStructureDao.queryEmpInfo(organId, customerId);
        Map<Integer, List<TalentStructureDto>> empDistinguishByHasAb = getEmpDistinguishByHasAb(rsDb);

        if (null == rsDb) {
            return null;
        }

        // 补0操作
        Map<String, Integer> mackUpRs = new LinkedHashMap<String, Integer>();
        Integer maxAbLevel = rsDb.get(0).getMaxAbLevel();
        for (int i = maxAbLevel; i >= 1; i--) {
            mackUpRs.put(i + "级", 0);
        }

        Map<String, Integer> abilityEmpCountPie = queryAbilityEmpCountPie(rsDb);
        Map<String, Map<String, Integer>> abilityEmpCount = queryAbilityEmpCount(rsDb, organId);
        Map<String, Integer> abilityCurtEmpCountPie = queryAbilityCurtEmpCountPie(empDistinguishByHasAb.get(1), empDistinguishByHasAb.get(2));
        Map<String, Map<String, Integer>> abilityCurtEmpCount = queryAbilityCurtEmpCount(rsDb, organId);
        Map<String, Integer> seqEmpCountBar = querySeqEmpCountBar(rsDb);
        Map<String, Map<String, Integer>> seqEmpCount = querySeqEmpCount(rsDb);

        Map<String, Integer> organEmpCount = queryOrganEmpCount(rsDb, organId);
        Map<String, Integer> workplaceEmpCount = queryWorkplaceEmpCount(rsDb);
        Map<String, Integer> degreeEmpCountPie = queryDegreeEmpCountPie(rsDb);
        Map<String, Map<String, Integer>> degreeEmpCount = queryDegreeEmpCount(rsDb, organId);
        Map<String, Integer> companyAgeEmpCountPie = queryCompanyAgeEmpCountPie(rsDb);
        Map<String, Map<String, Integer>> companyAgeEmpCount = queryCompanyAgeEmpCount(rsDb, organId);

        rs.put("abilityEmpCountPie", abilityEmpCountPie);            // 管理者员工分布-饼图
        rs.put("abilityEmpCount", abilityEmpCount);                    // 管理者员工分布-表
        rs.put("abilityCurtEmpCountPie", abilityCurtEmpCountPie);    // 职级分布-饼图
        rs.put("abilityCurtEmpCount", abilityCurtEmpCount);            // 职级分布-表
        rs.put("seqEmpCountBar", seqEmpCountBar);                    // 职位序列分布-柱状图
        rs.put("seqEmpCount", seqEmpCount);                            // 职位序列分布-表

        rs.put("organEmpCount", organEmpCount);                        // 组织分布
        rs.put("workplaceEmpCount", workplaceEmpCount);                // 工作地点分布
        rs.put("degreeEmpCountPie", degreeEmpCountPie);                // 学历分布-饼图
        rs.put("degreeEmpCount", degreeEmpCount);                    // 学历分布-表
        rs.put("companyAgeEmpCountPie", companyAgeEmpCountPie);        // 司龄分布-饼图
        rs.put("companyAgeEmpCount", companyAgeEmpCount);            // 司龄分布-表
        return rs;
    }

    @Override
    public List<KVItemDto> queryManageEmp4Organ(String organId, String customerId) {
        String mgrSeq = TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ;
        if (StringUtils.isEmpty(mgrSeq)) return null;

        List<String> organList = CacheHelper.getSubOrganIdList(organId);
        Integer abilityMaxIdx = Cache.queryAbilityMaxIdx.get();
        abilityMaxIdx = null != abilityMaxIdx ? abilityMaxIdx + 1 : 1;

        List<TalentStructureResultSubDto> list = talentStructureDao.queryManageEmp4Organ(organList, abilityMaxIdx, mgrSeq, customerId);
        List<KVItemDto> items = CollectionKit.newList();
        int mTotal = 0, eTotal = 0;
        for (TalentStructureResultSubDto dto : list) {
            if (mgrSeq.equals(dto.getTypeId())) {
                mTotal += dto.getTotal();
            } else {
                eTotal += dto.getTotal();
            }

            String keyName = StringUtils.isBlank(dto.getTypeName()) ? "员工" : dto.getTypeName();
            items.add(new KVItemDto(keyName, dto.getTotal().toString()));
        }
        if(mTotal>0){
        	items.add(new KVItemDto("管理者员工比例", "1 : " + ArithUtil.div(eTotal, mTotal, 2)));
        }else{
        	// 获取当前组织没有管理者 by jxzhang on 2017-01-17
        	items.add(new KVItemDto("管理者员工比例", "0 : " + eTotal));
        }
        return items;
    }

    @Override
    public TableGridDto<Map<String, Object>> queryManagerOrEmpList(String organId, String customerId) {
        String mgrSeq = TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ;
        Integer abilityMaxIdx = Cache.queryAbilityMaxIdx.get();
        abilityMaxIdx = null != abilityMaxIdx ? abilityMaxIdx + 1 : 1;

        List<TalentStructureResultDto> resultDtos = talentStructureDao.queryManagerOrEmpList(organId, abilityMaxIdx, mgrSeq, customerId);

        List<KVItemDto> modals = CollectionKit.newList();
        modals.add(new KVItemDto("organName", "下级组织"));
        List<KVItemDto> manageAbility = CacheHelper.getManageAbility();
        modals.addAll(manageAbility);
        modals.add(new KVItemDto("-99", "管理者合计"));

        //总计map
        Map<String, Object> totalMap = CollectionKit.newMap();
        totalMap.put("organName", "总计");
        //返回的数据集合
        List<Map<String, Object>> rsMap = CollectionKit.newList();

        boolean hasEmp = false;
        int tManage = 0, tEmp = 0;
        for (TalentStructureResultDto rsDto : resultDtos) {
            Map<String, Object> map = CollectionKit.newMap();
            map.put("organName", rsDto.getOrganName());

            int mTotal = 0, eTotal = 0;
            for (TalentStructureResultSubDto subDto : rsDto.getSubDtos()) {
                //加载机构不同人数
            	String typeId = subDto.getTypeId();
				map.put(typeId, subDto.getTotal());

                //加载员工
                String typeKey = subDto.getTypeKey();
				if(null != typeKey){
                	if (!hasEmp && !mgrSeq.equals(typeKey)) {
                        modals.add(new KVItemDto(typeId, "员工"));
                        hasEmp = true;
                    }	
                }
                
                //各层级总计
                if (null == totalMap.get(typeId)) {
                    totalMap.put(typeId, subDto.getTotal());
                } else {
                    totalMap.put(typeId, (Integer) totalMap.get(typeId) + subDto.getTotal());
                }

                //统计管理者与非管理者人数
                if (mgrSeq.equals(typeKey)) {
                    mTotal += subDto.getTotal();
                    tManage += subDto.getTotal();
                } else {
                    eTotal += subDto.getTotal();
                    tEmp += subDto.getTotal();
                }
            }
            map.put("-99", mTotal);
            if(mTotal>0){
            	map.put("-999", "1 : " + ArithUtil.div(eTotal, mTotal, 2));
            }else{
            	// 获取当前组织没有管理者 by jxzhang on 2017-01-18
            	map.put("-999", "0 : " + eTotal);
            }
            rsMap.add(map);
        }
        //总数相关
        totalMap.put("-99", tManage);
        if(tManage>0){
        	totalMap.put("-999", "1 : " + ArithUtil.div(tEmp, tManage, 2));
        }else{
        	// 获取当前组织没有管理者 by jxzhang on 2017-01-18
        	totalMap.put("-999", "0 : " + tEmp);
        }
        rsMap.add(0, totalMap);

        modals.add(new KVItemDto("-999", "管理者员工比例"));
        TableGridDto<Map<String, Object>> rsDto = new TableGridDto<>();
        rsDto.setModals(modals);
        rsDto.setData(rsMap);
        return rsDto;
    }

    @Override
    public List<KVItemDto> queryAbility4Organ(String organId, String sequenceId, String customerId) {
        List<String> organList = CacheHelper.getSubOrganIdList(organId);
        Integer abilityMaxIdx = Cache.queryAbilityMaxIdx.get();
        abilityMaxIdx = null != abilityMaxIdx ? abilityMaxIdx + 1 : 1;

        List<KVItemDto> list = talentStructureDao.queryAbility4Organ(organList, sequenceId, abilityMaxIdx, customerId);
        for (KVItemDto dto : list) {
            if (StringUtils.isBlank(dto.getK())) {
                dto.setK("其它");
                break;
            }
        }
        return list;
    }

    @Override
    public TableGridDto<Map<String, Object>> queryAbilityToSubOrgan(String organId, String customerId) {
        Integer abilityMaxIdx = Cache.queryAbilityMaxIdx.get();
        abilityMaxIdx = null != abilityMaxIdx ? abilityMaxIdx + 1 : 1;

        List<TalentStructureResultDto> list = talentStructureDao.queryAbilityToSubOrgan(organId, abilityMaxIdx, customerId);

        List<KVItemDto> modals = CollectionKit.newList();
        modals.add(new KVItemDto("organName", "下级组织"));
        List<KVItemDto> ability = CacheHelper.getAbility();
        modals.addAll(ability);

        //总计map
        Map<String, Object> totalMap = CollectionKit.newMap();
        totalMap.put("organName", "总计");

        //返回的数据集合
        List<Map<String, Object>> rsMap = CollectionKit.newList();
        boolean hasOther = false;
        for (TalentStructureResultDto rsDto : list) {
            Map<String, Object> map = CollectionKit.newMap();
            map.put("organName", rsDto.getOrganName());

            for (TalentStructureResultSubDto subDto : rsDto.getSubDtos()) {
                //加载机构不同人数
                map.put(subDto.getTypeId(), subDto.getTotal());

                //加载其它
                if (!hasOther && null == CacheHelper.getAbilityName(subDto.getTypeId())) {
                    modals.add(new KVItemDto(subDto.getTypeId(), "其它"));
                    hasOther = true;
                }

                //各层级总计
                if (null == totalMap.get(subDto.getTypeId())) {
                    totalMap.put(subDto.getTypeId(), subDto.getTotal());
                } else {
                    totalMap.put(subDto.getTypeId(), (Integer) totalMap.get(subDto.getTypeId()) + subDto.getTotal());
                }
            }
            rsMap.add(map);
        }
        rsMap.add(0, totalMap);

        TableGridDto<Map<String, Object>> rsDto = new TableGridDto<>();
        rsDto.setModals(modals);
        rsDto.setData(rsMap);
        return rsDto;
    }

    @Override
    public List<SequenceItemsDto> querySequence4Organ(String organId, String customerId) {
        List<String> organList = CacheHelper.getSubOrganIdList(organId);
        List<SequenceItemsDto> list = talentStructureDao.querySequence4Organ(organList, customerId);
        for (SequenceItemsDto dto : list) {
            if (StringUtils.isBlank(dto.getItemId())) {
                dto.setItemId("-1");
                dto.setItemName("其它");
            }
        }
        return list;
    }

    @Override
    public TableGridDto<Map<String, Object>> querySequenceAbilityCount(String organId, String customerId) {
        List<String> organList = CacheHelper.getSubOrganIdList(organId);
        List<TalentStructureResultDto> list = talentStructureDao.querySequenceAbilityCount(organList, customerId);

        List<KVItemDto> modals = CollectionKit.newList();
        modals.add(new KVItemDto("organName", "职级"));
        List<SequenceItemsDto> sequences = CacheHelper.getSequence();
        for (SequenceItemsDto itemDto : sequences) {
            modals.add(new KVItemDto(itemDto.getItemId(), itemDto.getItemName()));
        }
        modals.add(new KVItemDto("-1", "总计"));

        //返回的数据集合
        List<Map<String, Object>> rsMap = CollectionKit.newList();
        for (TalentStructureResultDto rsDto : list) {
            Map<String, Object> map = CollectionKit.newMap();
            map.put("organName", rsDto.getOrganName());
            int total = 0;
            for (TalentStructureResultSubDto dto : rsDto.getSubDtos()) {
                map.put(dto.getTypeId(), dto.getTotal());
                total += dto.getTotal();
            }
            map.put("-1", total);
            rsMap.add(map);
        }

        TableGridDto<Map<String, Object>> rsDto = new TableGridDto<>();
        rsDto.setModals(modals);
        rsDto.setData(rsMap);
        return rsDto;
    }

    @Override
    public List<KVItemDto> querySubOrganCount(String organId, String customerId) {
        return talentStructureDao.querySubOrganCount(organId, customerId);
    }

    @Override
    public List<KVItemDto> queryWorkplaceCount(String organId, String customerId) {
        List<String> organList = CacheHelper.getSubOrganIdList(organId);
        List<KVItemDto> list = talentStructureDao.queryWorkplaceCount(organList, customerId);
        for (KVItemDto dto : list) {
            if (StringUtils.isBlank(dto.getK())) {
                dto.setK("其它");
            }
        }
        return list;
    }

    @Override
    public List<KVItemDto> queryDegree4Organ(String organId, String customerId) {
        List<String> organList = CacheHelper.getSubOrganIdList(organId);
        List<DegreeDto> degreeObj = CacheHelper.getDegree();
        Collections.sort(degreeObj, CompareUtil.createComparator(-1, "showIndex"));

        List<KVItemDto> list = talentStructureDao.queryDegree4Organ(organList, customerId);

        List<KVItemDto> rsDtos = CollectionKit.newList();
        int juniorCollege = 0, other = 0;
        for (DegreeDto degree : degreeObj) {
            for (KVItemDto kvItem : list) {
                if (degree.getType() == 0 && degree.getItemId().equals(kvItem.getK())) {
                    rsDtos.add(new KVItemDto(degree.getItemName(), kvItem.getV()));
                } else if (degree.getType() == 1 && degree.getItemId().equals(kvItem.getK())) {
                    juniorCollege += Integer.valueOf(kvItem.getV());
                }
            }
        }
        for (KVItemDto kvItem : list) {
            if (StringUtils.isBlank(kvItem.getK())) {
                other += Integer.valueOf(kvItem.getV());
            }
        }
        if (juniorCollege > 0) {
            rsDtos.add(new KVItemDto("大专以下", String.valueOf(juniorCollege)));
        }
        if (other > 0) {
            rsDtos.add(new KVItemDto("未知", String.valueOf(other)));
        }
        return rsDtos;
    }

    @Override
    public TableGridDto<Map<String, Object>> queryDegreeToSubOrgan(String organId, String customerId) {
        List<DegreeDto> degreeObj = CacheHelper.getDegree();
        Collections.sort(degreeObj, CompareUtil.createComparator(-1, "showIndex"));

        List<TalentStructureResultDto> list = talentStructureDao.queryDegreeToSubOrgan(organId, customerId);

        List<KVItemDto> modals = CollectionKit.newList();
        modals.add(new KVItemDto("organName", "下级组织"));

        Map<String, String> modalMap = new LinkedHashMap<String, String>();
        //总计map
        Map<String, Object> totalMap = CollectionKit.newMap();
        totalMap.put("organName", "总计");

        //返回的数据集合
        List<Map<String, Object>> rsMap = CollectionKit.newList();
        int tJuniorCollege = 0, tOther = 0;
        for (TalentStructureResultDto rsDto : list) {
            Map<String, Object> map = CollectionKit.newMap();
            map.put("organName", rsDto.getOrganName());

            int juniorCollege = 0, other = 0;
            for (TalentStructureResultSubDto subDto : rsDto.getSubDtos()) {
                if (StringUtils.isBlank(subDto.getTypeId())) { //获取未知类型
                    other += subDto.getTotal();
                    //各层级总计
                    tOther += subDto.getTotal();
                    continue;
                }
                DegreeDto degreeDto = null;
                for (DegreeDto degree : degreeObj) {
                    if (degree.getItemId().equals(subDto.getTypeId())) {
                        degreeDto = degree;
                        break;
                    }
                }
                if (null == degreeDto) {    //获取未知类型
                    other += subDto.getTotal();
                    //各层级总计
                    tOther += subDto.getTotal();
                    continue;
                }

                if (degreeDto.getType() == 0) {
                    //加载机构不同人数
                    map.put(subDto.getTypeId(), subDto.getTotal());

                    //各层级总计
                    if (null == totalMap.get(subDto.getTypeId())) {
                        totalMap.put(subDto.getTypeId(), subDto.getTotal());
                    } else {
                        totalMap.put(subDto.getTypeId(), (Integer) totalMap.get(subDto.getTypeId()) + subDto.getTotal());
                    }
                } else {
                    juniorCollege += subDto.getTotal();
                    //各层级总计
                    tJuniorCollege += subDto.getTotal();
                }
            }

            if (juniorCollege > 0) {
                map.put("-1", String.valueOf(juniorCollege));
            }
            if (other > 0) {
                map.put("-9", String.valueOf(other));
            }
            rsMap.add(map);
        }
        for (DegreeDto degree : degreeObj) {
            if (degree.getType() == 0 &&
                    (null != totalMap.get(degree.getItemId()) && Integer.parseInt(totalMap.get(degree.getItemId()).toString()) > 0)) {
                modals.add(new KVItemDto(degree.getItemId(), degree.getItemName()));
            }
        }
        if (tJuniorCollege > 0) {
            modals.add(new KVItemDto("-1", "大专以下"));
            totalMap.put("-1", String.valueOf(tJuniorCollege));
        }
        if (tOther > 0) {
            modals.add(new KVItemDto("-9", "未知"));
            totalMap.put("-9", String.valueOf(tOther));
        }
        rsMap.add(0, totalMap);

        TableGridDto<Map<String, Object>> rsDto = new TableGridDto<>();
        rsDto.setModals(modals);
        rsDto.setData(rsMap);

        return rsDto;
    }

    @Override
    public List<KVItemDto> queryCompanyAge4Organ(String organId, String customerId) {
        List<String> organList = CacheHelper.getSubOrganIdList(organId);

        List<CompanyAgeEnum> companyAges = Arrays.asList(CompanyAgeEnum.values());
        if (companyAges.isEmpty()) return null;

        Map<String, Object> map = talentStructureDao.queryCompanyAge4Organ(organList, companyAges, customerId);
        List<KVItemDto> rsDtos = CollectionKit.newList();
        for (CompanyAgeEnum age : companyAges) {    //按顺序取出有值的数据
            for (Entry<String, Object> entryMap : map.entrySet()) {
                Integer val = Integer.parseInt(entryMap.getValue().toString());
                if (age.getDesc().equals(entryMap.getKey()) && (null != val && val != 0)) { //描述相同并且值不等于0则添加到返回对象
                    rsDtos.add(new KVItemDto(entryMap.getKey(), String.valueOf(val)));
                }
            }
        }
        return rsDtos;
    }

    @Override
    public TableGridDto<Map<String, Object>> queryCompanyAgeToSubOrgan(String organId, String customerId) {
        List<CompanyAgeEnum> companyAges = Arrays.asList(CompanyAgeEnum.values());
        if (companyAges.isEmpty()) return null;

        List<Map<String, Object>> list = talentStructureDao.queryCompanyAgeToSubOrgan(organId, companyAges, customerId);

        List<KVItemDto> modals = CollectionKit.newList();
        modals.add(new KVItemDto("parentName", "下级组织"));

        Map<String, Object> totalMap = CollectionKit.newMap();
        totalMap.put("parentName", "合计");

        for (Map<String, Object> map : list) {
            for (Entry<String, Object> entry : map.entrySet()) {

                //计算合计
                if (null != totalMap.get(entry.getKey()) && StringUtils.isNumeric(entry.getValue().toString())) {
                    totalMap.put(entry.getKey(), (int) totalMap.get(entry.getKey()) + Integer.parseInt(entry.getValue().toString()));
                } else if (null == totalMap.get(entry.getKey()) && StringUtils.isNumeric(entry.getValue().toString())) {
                    totalMap.put(entry.getKey(), Integer.parseInt(entry.getValue().toString()));
                }
            }
        }
        list.add(0, totalMap);

        for (CompanyAgeEnum age : companyAges) {
            if (null != totalMap.get(age.getDesc()) && Integer.parseInt(totalMap.get(age.getDesc()).toString()) > 0) {
                modals.add(new KVItemDto(age.getDesc(), age.getDesc()));
            }
        }

        TableGridDto<Map<String, Object>> rsDto = new TableGridDto<>();
        rsDto.setModals(modals);
        rsDto.setData(list);

        return rsDto;
    }


    @Override
    public Map<String, Object> queryTalentStuctureByMonth(String organId, String customerId) {
        Map<String, Object> rs = new LinkedHashMap<String, Object>();

        List<TalentStructureDto> rsDb = talentStructureDao.queryEmpInfo(organId, customerId);
        Map<Integer, List<TalentStructureDto>> empDistinguishByHasAb = getEmpDistinguishByHasAb(rsDb);

        if (null == rsDb) {
            return null;
        }

        // 补0操作
        Map<String, Integer> mackUpRs = new LinkedHashMap<String, Integer>();
        Integer maxAbLevel = rsDb.get(0).getMaxAbLevel();
        for (int i = maxAbLevel; i >= 1; i--) {
            mackUpRs.put(i + "级", 0);
        }

        Map<String, Map<String, Integer>> abilityEmpCount = queryAbilityEmpCount(rsDb, organId);
        Map<String, Map<String, Integer>> abilityCurtEmpCount = queryAbilityCurtEmpCount(rsDb, organId);
        Map<String, Map<String, Integer>> seqEmpCount = querySeqEmpCount(rsDb);

        rs.put("abilityEmpCount", abilityEmpCount);                    // 管理者员工分布-表
        rs.put("abilityCurtEmpCount", abilityCurtEmpCount);            // 职级分布-表
        rs.put("seqEmpCount", seqEmpCount);                            // 职位序列分布-表

        return rs;
    }


    /**
     * 职位序列分布 by jxzhang 2016-02-26
     *
     * @param rsDb
     * @return
     */
    private Map<String, Map<String, Integer>> querySeqEmpCount(List<TalentStructureDto> rsDb) {
        // TODO 排序使用
        List<SequenceItemsDto> sequence = CacheHelper.getSequence();


        MultiValueMap<String, TalentStructureDto> m = new LinkedMultiValueMap<String, TalentStructureDto>();
        for (TalentStructureDto dto : rsDb) {
            if (null != dto.getAbCurtName()) {
                m.add(dto.getAbCurtName(), dto);
            }
        }
        Map<String, Map<String, Integer>> rsMap = new LinkedHashMap<>();

        for (Entry<String, List<TalentStructureDto>> abEntry : m.entrySet()) {
            List<TalentStructureDto> groupDtos = abEntry.getValue();

            MultiValueMap<String, TalentStructureDto> seqMap = new LinkedMultiValueMap<String, TalentStructureDto>();
            for (TalentStructureDto dto : groupDtos) {
                if (null != dto.getSequenceName()) {
                    seqMap.add(dto.getSequenceName(), dto);
                }
            }

            Map<String, Integer> rsSeqMap = new LinkedHashMap<>();
            for (Entry<String, List<TalentStructureDto>> seqEntry : seqMap.entrySet()) {
                rsSeqMap.put(seqEntry.getKey(), seqEntry.getValue().size());
            }
            Map<String, Integer> sortMap = CollectionKit.sortMapByValue(rsSeqMap);
            rsMap.put(abEntry.getKey(), sortMap);
        }
        return rsMap;
    }

    /**
     * 职位序列分布-柱状图 by jxzhang 2016-02-26
     *
     * @param rsDb
     * @return
     */
    private Map<String, Integer> querySeqEmpCountBar(List<TalentStructureDto> rsDb) {
        MultiValueMap<String, TalentStructureDto> m = new LinkedMultiValueMap<String, TalentStructureDto>();
        for (TalentStructureDto dto : rsDb) {
            if (null != dto.getSequenceId()) {
                m.add(dto.getSequenceId() + "," + dto.getSequenceName(), dto);
            } else {
                m.add("其他", dto);
            }
        }
        Map<String, Integer> rsMap = new LinkedHashMap<>();
        for (Entry<String, List<TalentStructureDto>> entry : m.entrySet()) {
            String key = entry.getKey();
            List<TalentStructureDto> groupDtos = entry.getValue();

            rsMap.put(key, groupDtos.size());
        }
        Map<String, Integer> sortMap = CollectionKit.sortMapByValue(rsMap);
        return sortMap;
    }


    /**
     * 司龄分布 by jxzhang on 2016-02-25
     *
     * @param rsDb
     * @param organId
     * @return
     */
    private Map<String, Map<String, Integer>> queryCompanyAgeEmpCount(List<TalentStructureDto> rsDb, String organId) {
        Map<String, List<TalentStructureDto>> m = new LinkedHashMap<String, List<TalentStructureDto>>();
        //BUG:497
        List<String> underSubOrganIdList = CacheHelper.getUnderSubOrganIdList(organId);
        if (null == underSubOrganIdList) {
            //BUG:502 管理者员工分布、职级分布、学历分布、司龄分布、组织分布、工作地点分布：数据都是显示架构‘北京中人网信息咨询有限公司’的数据；
            return myselfSubCount(rsDb, organId, 4);
        } else {
            for (String string : underSubOrganIdList) {
                String pKey = string.split(",")[1];
                String pName = string.split(",")[2];
                List<TalentStructureDto> dtos = CollectionKit.newList();

                for (TalentStructureDto item : rsDb) {
                    if (item.getFullPath().contains(pKey)) {
                        dtos.add(item);
                    }
                }
                m.put(pName, dtos);
            }
        }


        Map<String, Map<String, Integer>> rsMap = new LinkedHashMap<String, Map<String, Integer>>();

        //BUG:498
        Map<String, Map<String, Integer>> myselfMap = myselfSubCount(rsDb, organId, 4);
        if (null != myselfMap) {
            rsMap.putAll(myselfMap);
        }


        for (Entry<String, List<TalentStructureDto>> organNameEntry : m.entrySet()) {
            Map<String, Integer> mapDto = new LinkedHashMap<String, Integer>();
            int[] seriesData = {0, 0, 0, 0, 0};
            List<TalentStructureDto> value = organNameEntry.getValue();

            for (TalentStructureDto dto : value) {

                Integer compAge = dto.getCompanyAge();
                if (null != compAge) {
                    if (compAge >= 0 && compAge < 3) {
                        int temp = seriesData[0];
                        seriesData[0] = temp + 1;
                    } else if (compAge >= 3 && compAge < 12) {
                        int temp = seriesData[1];
                        seriesData[1] = temp + 1;
                    } else if (compAge >= 12 && compAge < 36) {
                        int temp = seriesData[2];
                        seriesData[2] = temp + 1;
                    } else if (compAge >= 36 && compAge < 60) {
                        int temp = seriesData[3];
                        seriesData[3] = +temp + 1;
                    } else {
                        int temp = seriesData[4];
                        seriesData[4] = temp + 1;
                    }
                }
            }
            mapDto.put("3月以下", seriesData[0]);
            mapDto.put("3月-1年", seriesData[1]);
            mapDto.put("1-3年", seriesData[2]);
            mapDto.put("3-5年", seriesData[3]);
            mapDto.put("5年以上", seriesData[4]);

            rsMap.put(organNameEntry.getKey(), mapDto);
        }
        return rsMap;
    }


    /**
     * 司龄分布-饼图 by jxzhang on 2016-02-25
     *
     * @param rs
     * @return
     */
    private Map<String, Integer> queryCompanyAgeEmpCountPie(List<TalentStructureDto> rs) {
        MultiValueMap<Integer, TalentStructureDto> m = new LinkedMultiValueMap<Integer, TalentStructureDto>();
        for (TalentStructureDto dto : rs) {
            m.add(dto.getCompanyAge(), dto);
        }
        Map<String, Integer> mapDto = new LinkedHashMap<String, Integer>();
        int[] seriesData = {0, 0, 0, 0, 0};
        for (Entry<Integer, List<TalentStructureDto>> empEntry : m.entrySet()) {
            if (empEntry.getKey() instanceof Integer) {
                Integer key = empEntry.getKey();
                if (key >= 0 && key < 3) {
                    int temp = seriesData[0];
                    seriesData[0] = temp + empEntry.getValue().size();
                } else if (key >= 3 && key < 12) {
                    int temp = seriesData[1];
                    seriesData[1] = temp + empEntry.getValue().size();
                } else if (key >= 12 && key < 36) {
                    int temp = seriesData[2];
                    seriesData[2] = temp + empEntry.getValue().size();
                } else if (key >= 36 && key < 60) {
                    int temp = seriesData[3];
                    seriesData[3] = temp + empEntry.getValue().size();
                } else {
                    int temp = seriesData[4];
                    seriesData[4] = temp + empEntry.getValue().size();
                }
            }
        }
        mapDto.put("3月以下", seriesData[0]);
        mapDto.put("3月-1年", seriesData[1]);
        mapDto.put("1-3年", seriesData[2]);
        mapDto.put("3-5年", seriesData[3]);
        mapDto.put("5年以上", seriesData[4]);
        return mapDto;
    }

    /**
     * 学历分布统计表 by jxzhang on 2016-02-23
     *
     * @param rsDb
     * @return
     */
    private Map<String, Map<String, Integer>> queryDegreeEmpCount(List<TalentStructureDto> rsDb, String organId) {

        Map<String, List<TalentStructureDto>> m = new LinkedHashMap<String, List<TalentStructureDto>>();
        //BUG:497
        List<String> underSubOrganIdList = CacheHelper.getUnderSubOrganIdList(organId);
        if (null == underSubOrganIdList) {
            return myselfSubCount(rsDb, organId, 3);
        } else {
            for (String string : underSubOrganIdList) {
                String pKey = string.split(",")[1];
                String pName = string.split(",")[2];
                List<TalentStructureDto> dtos = CollectionKit.newList();

                for (TalentStructureDto item : rsDb) {
                    if (item.getFullPath().contains(pKey)) {
                        dtos.add(item);
                    }
                }
                m.put(pName, dtos);
            }
        }

        Map<String, Map<String, Integer>> rsMap = new LinkedHashMap<String, Map<String, Integer>>();

        //BUG:498
        Map<String, Map<String, Integer>> myselfMap = myselfSubCount(rsDb, organId, 3);
        if (null != myselfMap) {
            rsMap.putAll(myselfMap);
        }

        List<DegreeDto> degreeObj = CacheHelper.getDegree();

        for (Entry<String, List<TalentStructureDto>> organNameEntry : m.entrySet()) {
            List<TalentStructureDto> groupDtos = organNameEntry.getValue();

            MultiValueMap<String, TalentStructureDto> degreeMap = new LinkedMultiValueMap<String, TalentStructureDto>();
            for (TalentStructureDto groupDto : groupDtos) {
                if (null != groupDto.getDegree()) {
                    degreeMap.add(groupDto.getDegree(), groupDto);
                }
            }
            Map<String, Integer> rsAbMap = CollectionKit.newMap();
            for (Entry<String, List<TalentStructureDto>> degreeEntry : degreeMap.entrySet()) {

                String mapkey = degreeEntry.getKey();
                if (null == mapkey) {
                    continue;
                }
                //TODO 实施注意地方： 这块要硬编码。因为不知大专以下是什么。
//				if ((!(mapkey.equals("大专")) && !(mapkey.equals("本科")) && !(mapkey.equals("硕士")) && !mapkey.equals("博士"))){
//					
//					rsAbMap.put("大专以下", degreeEntry.getValue().size());
//				} else{
//					rsAbMap.put(degreeEntry.getKey(), degreeEntry.getValue().size());
//				}

//				维度表			
                for (DegreeDto dimDegree : degreeObj) {

                    if (mapkey.equals(dimDegree.getItemName())
                            && dimDegree.getType() == 0) {
                        rsAbMap.put(mapkey, degreeEntry.getValue().size());
                        continue;
                    }
//					else{
//						rsAbMap.put("大专以下", degreeEntry.getValue().size());
//					}
                }
            }
            rsMap.put(organNameEntry.getKey(), rsAbMap);
        }
        return rsMap;
    }

    /**
     * 学历分布饼图 by jxzhang on 2016-02-24
     *
     * @param rsDb
     * @return
     */
    private Map<String, Integer> queryDegreeEmpCountPie(
            List<TalentStructureDto> rsDb) {

        // 能力层级分组
        MultiValueMap<String, TalentStructureDto> abMap = new LinkedMultiValueMap<String, TalentStructureDto>();
        for (TalentStructureDto dto : rsDb) {
            if (null != dto.getDegree()) {
                abMap.add(dto.getDegree(), dto);
            }
        }
        Map<String, Integer> rs = new LinkedHashMap<String, Integer>();

        for (Entry<String, List<TalentStructureDto>> entry : abMap.entrySet()) {
            rs.put(entry.getKey(), entry.getValue().size());
        }


        List<DegreeDto> degreeObj = CacheHelper.getDegree();
        // 补0操作
        Map<String, Integer> markUpRs = new LinkedHashMap<String, Integer>();
//		markUpRs.put("大专以下", 0);
//		markUpRs.put("大专", 0);
//		markUpRs.put("本科", 0);
//		markUpRs.put("硕士", 0);
//		markUpRs.put("博士", 0);
        for (DegreeDto dimDegree : degreeObj) {
            if (dimDegree.getType() == 0) {
                markUpRs.put(dimDegree.getItemName(), 0);
            } else {
                markUpRs.put("大专以下", 0);
            }
        }

        Set<Entry<String, Integer>> entrySet = rs.entrySet();
        for (Entry<String, Integer> entry : entrySet) {
            String key = entry.getKey();
//			if (null != key && 
//					(!(key.equals("大专")) && !(key.equals("本科"))
//					&& !(key.equals("硕士")) && !(key.equals("博士")))) {
//				
//				markUpRs.put("大专以下", entry.getValue());
//			}
//			markUpRs.put(key, entry.getValue());

//			维度表			
            for (DegreeDto dimDegree : degreeObj) {
                if (key.equals(dimDegree.getItemName())
                        && dimDegree.getType() == 0) {
                    markUpRs.put(key, entry.getValue());
                    continue;
                }
//				else{
//					markUpRs.put("大专以下", entry.getValue());
//				}
            }


        }
        return markUpRs;
    }

    /**
     * 工作地点分布 by jxzhang on 2016-02-24
     *
     * @param rsDb
     * @return
     */
    private Map<String, Integer> queryWorkplaceEmpCount(List<TalentStructureDto> rsDb) {
        MultiValueMap<String, TalentStructureDto> rsMap = new LinkedMultiValueMap<String, TalentStructureDto>();
        for (TalentStructureDto dto : rsDb) {
            if (null != dto.getWorkPlace()) {
                rsMap.add(dto.getWorkPlace(), dto);
            }
        }
        Map<String, Integer> rs = CollectionKit.newMap();
        for (Entry<String, List<TalentStructureDto>> entry : rsMap.entrySet()) {
            rs.put("".equals(entry.getKey()) ? "其他" : entry.getKey(), entry.getValue().size());
        }
        return rs;
    }

    /**
     * 组织分布 by jxzhang on 2016-02-23
     *
     * @param rsDb
     * @return
     */
    private Map<String, Integer> queryOrganEmpCount(List<TalentStructureDto> rsDb, String organId) {
        Map<String, Integer> rs = new LinkedHashMap<String, Integer>();

        List<String> underSubOrganIdList = CacheHelper.getUnderSubOrganIdList(organId);
        if (null == underSubOrganIdList) {
            List<TalentStructureDto> myList = CollectionKit.newList();
            for (TalentStructureDto dto : rsDb) {
                if (dto.getOrganizationId().equals(organId)) {
                    myList.add(dto);
                }
            }
            rs.put(myList.get(0).getOrganizationName(), myList.size());
            return rs;
        }
        MultiValueMap<String, TalentStructureDto> rsMap = new LinkedMultiValueMap<String, TalentStructureDto>();
        for (String string : underSubOrganIdList) {
            String pKey = string.split(",")[1];
            String pName = string.split(",")[2];
            List<TalentStructureDto> dtos = CollectionKit.newList();

            for (TalentStructureDto item : rsDb) {
                if (item.getFullPath().contains(pKey)) {
                    dtos.add(item);
                }
            }
            rsMap.put(pName, dtos);
        }

        // BUG:506 组织分布：只显示了一个下级架构；
        List<TalentStructureDto> myOrganList = CollectionKit.newList();
        for (TalentStructureDto dto : rsDb) {
            if (null == dto.getOrganizationId()) {
                continue;
            }
            // 选中的机构如果也有人，也显示出来
            if (null != dto.getOrganizationId() && dto.getOrganizationId().equals(organId)) {
                myOrganList.add(dto);
            }
        }
        if (myOrganList.size() > 0) {
            rs.put(myOrganList.get(0).getOrganizationName(), myOrganList.size());
        }

        for (Entry<String, List<TalentStructureDto>> entry : rsMap.entrySet()) {
            rs.put(entry.getKey(), entry.getValue().size());
        }
        return rs;
    }


    /**
     * 职级分布饼图 by jxzhang on 2016-02-24
     *
     * @param hasList * @return
     */
    private Map<String, Integer> queryAbilityCurtEmpCountPie(
            List<TalentStructureDto> hasList,
            List<TalentStructureDto> notHasList) {

        // 能力层级分组
        MultiValueMap<String, TalentStructureDto> abMap = new LinkedMultiValueMap<String, TalentStructureDto>();
        for (TalentStructureDto dto : hasList) {
            if (null != dto.getAbCurtName()) {
                abMap.add(dto.getAbCurtName(), dto);
            }
        }
        Map<String, Integer> rs = new LinkedHashMap<String, Integer>();

        for (Entry<String, List<TalentStructureDto>> entry : abMap.entrySet()) {
            rs.put(entry.getKey(), entry.getValue().size());
        }

        // 补0操作
        Map<String, Integer> orderRs = new LinkedHashMap<String, Integer>();
        Integer maxAbLevel = hasList.get(0).getMaxAbLevel();
        for (int i = maxAbLevel; i >= 1; i--) {
            orderRs.put(i + "级", 0);
        }

        Set<Entry<String, Integer>> entrySet = rs.entrySet();
        for (Entry<String, Integer> entry : entrySet) {
            String key = entry.getKey();
            orderRs.put(key, entry.getValue());
        }
        if (notHasList.size() > 0) {
            orderRs.put("其他", notHasList.size());
        }
        return orderRs;
    }

    /**
     * 职级分布表 by jxzhang on 2016-02-24
     *
     * @param rsDb
     * @param organId
     * @return
     */
    private Map<String, Map<String, Integer>> queryAbilityCurtEmpCount(List<TalentStructureDto> rsDb, String organId) {

        Map<String, List<TalentStructureDto>> m = new LinkedHashMap<String, List<TalentStructureDto>>();
        //BUG:497
        List<String> underSubOrganIdList = CacheHelper.getUnderSubOrganIdList(organId);
        if (null == underSubOrganIdList) {
            return myselfSubCount(rsDb, organId, 1);
        } else {
            // 按子机构分组
            for (String string : underSubOrganIdList) {
                String pKey = string.split(",")[1];
                String pName = string.split(",")[2];
                List<TalentStructureDto> dtos = CollectionKit.newList();

                for (TalentStructureDto item : rsDb) {
                    if (item.getFullPath().contains(pKey)) {
                        dtos.add(item);
                    }
                }
                m.put(pName, dtos);
            }
        }

        Map<String, Map<String, Integer>> rsMap = new LinkedHashMap<String, Map<String, Integer>>();
        //BUG:498
        // xxx
        Map<String, Map<String, Integer>> myselfMap = myselfSubCount(rsDb, organId, 2);
        if (null != myselfMap) {
            rsMap.putAll(myselfMap);
        }


        for (Entry<String, List<TalentStructureDto>> organNameEntry : m.entrySet()) {
            List<TalentStructureDto> groupDtos = organNameEntry.getValue();
            // 能力层级分组
            MultiValueMap<String, TalentStructureDto> abMap = new LinkedMultiValueMap<String, TalentStructureDto>();
            for (TalentStructureDto groupDto : groupDtos) {
                if (null != groupDto.getAbCurtName()) {
                    abMap.add(groupDto.getAbCurtName(), groupDto);
                }
            }
            Integer hasSize = 0;
            Integer notHasSize = 0;
            Map<String, Integer> rsAbMap = new LinkedHashMap<String, Integer>();

            for (Entry<String, List<TalentStructureDto>> abNameEntry : abMap.entrySet()) {
                for (TalentStructureDto dto : abNameEntry.getValue()) {
                    if (null != dto.getAbilityId()) {
                        hasSize++;
                    } else {
                        notHasSize++;
                    }
                }
                rsAbMap.put(abNameEntry.getKey(), hasSize);
                hasSize = 0;
            }
            rsAbMap.put("其它", notHasSize);
            notHasSize = 0;
            rsMap.put(organNameEntry.getKey(), rsAbMap);
        }
        return rsMap;
    }

    /**
     * 能力层级统计饼图 by jxzhang on 2016-02-23
     *
     * @param rsDb
     * @return
     */
    private Map<String, Integer> queryAbilityEmpCountPie(List<TalentStructureDto> rsDb) {
        Map<Integer, List<TalentStructureDto>> empDistinguish = getEmpDistinguishByMgr(rsDb);
        List<TalentStructureDto> mgrList = empDistinguish.get(1);
        List<TalentStructureDto> empList = empDistinguish.get(2);

        // 能力层级分组
        MultiValueMap<String, TalentStructureDto> abMap = new LinkedMultiValueMap<String, TalentStructureDto>();
        for (TalentStructureDto dto : mgrList) {
            if (null != dto.getAbilityId()) {
                abMap.add(dto.getAbilityId(), dto);
            }
        }
        Map<String, Integer> mackUpRs = new LinkedHashMap<String, Integer>();
//		Integer maxAbLevel = rsDb.get(0).getMaxAbLevel();
//		for (int i = maxAbLevel; i >=1; i--) {
//			mackUpRs.put(i+"级", 0);
//		}
        for (Entry<String, List<TalentStructureDto>> abNameEntry : abMap.entrySet()) {
            // BUG:517 管理者员工分布：环形图右边的标识信息显示错误；不是显示5/4/3/2/1，与列表一致；
            String abId = abNameEntry.getKey();
            mackUpRs.put(CacheHelper.getAbilityName(abId), abNameEntry.getValue().size());
        }
        mackUpRs.put("员工", empList.size());
        return mackUpRs;
    }

    /**
     * 能力层级统计表 by jxzhang on 2016-02-23
     *
     * @param rsDb
     * @return
     */
    private Map<String, Map<String, Integer>> queryAbilityEmpCount(List<TalentStructureDto> rsDb, String organId) {
        Map<String, List<TalentStructureDto>> m = new LinkedHashMap<>();

        //BUG:497
        List<String> underSubOrganIdList = CacheHelper.getUnderSubOrganIdList(organId);
        List<String> mgrAbNameList = CollectionKit.newList();
        if (null == underSubOrganIdList) {
            return myselfSubCount(rsDb, organId, 1);
        } else {
            for (String string : underSubOrganIdList) {
                String pKey = string.split(",")[1];
                String pName = string.split(",")[2];
                List<TalentStructureDto> dtos = CollectionKit.newList();
                for (TalentStructureDto item : rsDb) {
                    if (item.getFullPath().contains(pKey)) {
                        dtos.add(item);
                    }
                    // 管理序列的名称
                    if (null != item.getSeqKey() && item.getSeqKey().equals(TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ)) {
                        mgrAbNameList.add(item.getAbilityName());
                    }
                }
                m.put(pName, dtos);
            }
        }

        Map<String, Map<String, Integer>> rsMap = new LinkedHashMap<>();
        //BUG:498
        Map<String, Map<String, Integer>> myselfMap = myselfSubCount(rsDb, organId, 1);
        if (null != myselfMap) {
            rsMap.putAll(myselfMap);
        }


        for (Entry<String, List<TalentStructureDto>> organNameEntry : m.entrySet()) {
            List<TalentStructureDto> groupDtos = organNameEntry.getValue();
            // 能力层级分组
            MultiValueMap<String, TalentStructureDto> abMap = new LinkedMultiValueMap<String, TalentStructureDto>();
            for (TalentStructureDto groupDto : groupDtos) {
                if (null != groupDto.getAbilityName()) {
                    abMap.add(groupDto.getAbilityName(), groupDto);
                }
            }
            Integer mgrSize = 0;
            Integer empSize = 0;
//			Map<String, Integer> rsAbMap = CollectionKit.newMap();
            Map<String, Integer> rMap = new LinkedHashMap<String, Integer>();
            for (Entry<String, List<TalentStructureDto>> abNameEntry : abMap.entrySet()) {
                // 在能力层级里面，区分管理者和谱通员工
                for (TalentStructureDto dto : abNameEntry.getValue()) {
                    if (null != dto.getSeqKey() && dto.getSeqKey().equals(TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ)) {
                        mgrSize++;
                    } else {
                        empSize++;
                    }
                }
                // bug:504 管理者员工分布：列表不属于管理序列的职位层级不要显示；
                for (String mgrAbNameStr : mgrAbNameList) {
                    if (mgrAbNameStr.equals(abNameEntry.getKey())) {
//						rsAbMap.put(abNameEntry.getKey(), mgrSize);
                        rMap.put(abNameEntry.getKey(), mgrSize);
                        break;
                    }
                }
                mgrSize = 0;
            }
//			rsAbMap.put("员工", empSize);
            rMap.put("员工", empSize);
            empSize = 0;
            rsMap.put(organNameEntry.getKey(), rMap);
        }
        return rsMap;
    }

    /**
     * 计算选择机构
     *
     * @param rsDb
     * @param organId
     * @param groupByType
     * @return
     */
    private Map<String, Map<String, Integer>> myselfSubCount(List<TalentStructureDto> rsDb, String organId, int groupByType) {
        Map<String, Map<String, Integer>> rsMap = new LinkedHashMap<>();

        List<TalentStructureDto> myList = CollectionKit.newList();
        for (TalentStructureDto dto : rsDb) {
            if (dto.getOrganizationId().equals(organId)) {
                myList.add(dto);
            }
        }

        if (myList.size() > 0) {
            Map<String, Integer> myRsAbMap = new LinkedHashMap<String, Integer>();
            // 管理者员工分布-本机构
            if (1 == groupByType) {
                MultiValueMap<String, TalentStructureDto> myMap = new LinkedMultiValueMap<String, TalentStructureDto>();
                for (TalentStructureDto myOrgan : myList) {
                    if (null != myOrgan.getAbilityName()) {
                        myMap.add(myOrgan.getAbilityName(), myOrgan);
                    }
                }
                // 管理序列的名称
                List<String> mgrAbNameList = CollectionKit.newList();
                for (TalentStructureDto item : rsDb) {
                    if (null != item.getSeqKey() && item.getSeqKey().equals(TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ)) {
                        mgrAbNameList.add(item.getAbilityName());
                    }
                }
                Integer myMgrSize = 0;
                Integer myEmpSize = 0;
                for (Entry<String, List<TalentStructureDto>> abNameEntry : myMap.entrySet()) {
                    for (TalentStructureDto dto : abNameEntry.getValue()) {
                        if (null != dto.getSeqKey() && dto.getSeqKey().equals(TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ)) {
                            myMgrSize++;
                        } else {
                            myEmpSize++;
                        }
                    }
                    if (null != mgrAbNameList) {
                        // bug:504 管理者员工分布：列表不属于管理序列的职位层级不要显示；
                        for (String mgrAbNameStr : mgrAbNameList) {
                            if (mgrAbNameStr.equals(abNameEntry.getKey())) {
                                myRsAbMap.put(abNameEntry.getKey(), myMgrSize);
                                break;
                            }
                        }
                    }
                    myMgrSize = 0;
                }
                myRsAbMap.put("员工", myEmpSize);
            }
            // 能力层级分布-本机构
            if (2 == groupByType) {
                MultiValueMap<String, TalentStructureDto> myMap = new LinkedMultiValueMap<String, TalentStructureDto>();
                for (TalentStructureDto myOrgan : myList) {
                    // BUG:514
                    if (null != myOrgan.getAbCurtName()) {
                        myMap.add(myOrgan.getAbCurtName(), myOrgan);
                    }
                }
                Integer myMgrSize = 0;
                Integer myEmpSize = 0;
                for (Entry<String, List<TalentStructureDto>> abNameEntry : myMap.entrySet()) {
                    for (TalentStructureDto dto : abNameEntry.getValue()) {
                        if (null != dto.getSeqKey() && dto.getSeqKey().equals(TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ)) {
                            myMgrSize++;
                        } else {
                            myEmpSize++;
                        }
                    }
                    myRsAbMap.put(abNameEntry.getKey(), myMgrSize);
                    myMgrSize = 0;
                }
                myRsAbMap.put("员工", myEmpSize);
            }
            // 学历分布-本机构
            if (3 == groupByType) {
                MultiValueMap<String, TalentStructureDto> degreeMap = new LinkedMultiValueMap<String, TalentStructureDto>();
                for (TalentStructureDto groupDto : myList) {
                    if (null != groupDto.getDegree()) {
                        degreeMap.add(groupDto.getDegree(), groupDto);
                    }
                }
                for (Entry<String, List<TalentStructureDto>> degreeEntry : degreeMap.entrySet()) {

                    String mapkey = degreeEntry.getKey();
                    if (null != mapkey &&
                            (!(mapkey.equals("大专")) && !(mapkey.equals("本科"))
                                    && !(mapkey.equals("硕士")) && !mapkey.equals("博士"))) {
                        myRsAbMap.put("大专以下", degreeEntry.getValue().size());
                    }
                    myRsAbMap.put(degreeEntry.getKey(), degreeEntry.getValue().size());
                }
            }
            // 年龄分布-本机构
            if (4 == groupByType) {
                Map<String, Integer> mapDto = new LinkedHashMap<String, Integer>();
                int[] seriesData = {0, 0, 0, 0, 0};
                List<TalentStructureDto> value = myList;
                for (TalentStructureDto dto : value) {
                    Integer compAge = dto.getCompanyAge();
                    if (compAge >= 0 && compAge < 3) {
                        int temp = seriesData[0];
                        seriesData[0] = temp + 1;
                    } else if (compAge >= 3 && compAge < 12) {
                        int temp = seriesData[1];
                        seriesData[1] = temp + 1;
                    } else if (compAge >= 12 && compAge < 36) {
                        int temp = seriesData[2];
                        seriesData[2] = temp + 1;
                    } else if (compAge >= 36 && compAge < 60) {
                        int temp = seriesData[3];
                        seriesData[3] = +temp + 1;
                    } else {
                        int temp = seriesData[4];
                        seriesData[4] = temp + 1;
                    }
                }
                mapDto.put("3月以下", seriesData[0]);
                mapDto.put("3月-1年", seriesData[1]);
                mapDto.put("1-3年", seriesData[2]);
                mapDto.put("3-5年", seriesData[3]);
                mapDto.put("5年以上", seriesData[4]);

                myRsAbMap.putAll(mapDto);
            }
            rsMap.put(myList.get(0).getOrganizationName(), myRsAbMap);
            return rsMap;
        } else {
            return null;
        }
    }


    /**
     * 人员区分-管理者
     *
     * @param rsDb
     * @return
     */
    private Map<Integer, List<TalentStructureDto>> getEmpDistinguishByMgr(List<TalentStructureDto> rsDb) {
        List<TalentStructureDto> mgrList = CollectionKit.newList();
        List<TalentStructureDto> empList = CollectionKit.newList();
        for (TalentStructureDto dto : rsDb) {
            if (null != dto.getSeqKey() && dto.getSeqKey().equals(TableKeyUtil.DIM_SEQUENCE_KEY_MGRSEQ)) {
                mgrList.add(dto);
            } else {
                empList.add(dto);
            }
        }
        Map<Integer, List<TalentStructureDto>> rs = CollectionKit.newMap();
        rs.put(1, mgrList);
        rs.put(2, empList);
        return rs;
    }

    /**
     * 人员区分-有没有职级
     *
     * @param rsDb
     * @return
     */
    private Map<Integer, List<TalentStructureDto>> getEmpDistinguishByHasAb(List<TalentStructureDto> rsDb) {
        List<TalentStructureDto> hasList = CollectionKit.newList();
        List<TalentStructureDto> notHasList = CollectionKit.newList();
        for (TalentStructureDto dto : rsDb) {
            if (null != dto.getAbilityId()) {
                hasList.add(dto);
            } else {
                notHasList.add(dto);
            }
        }
        Map<Integer, List<TalentStructureDto>> rs = CollectionKit.newMap();
        rs.put(1, hasList);
        rs.put(2, notHasList);
        return rs;
    }


}

package net.chinahrd.module;

import java.util.List;
import java.util.Map;

import net.chinahrd.core.cache.CacheBlock;
import net.chinahrd.core.cache.CacheBlockConstructor;
import net.chinahrd.core.cache.CustomBlock;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.DegreeDto;
import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import net.chinahrd.entity.dto.pc.SubSequenceItemsDto;
import net.chinahrd.entity.dto.pc.admin.FunctionDto;
import net.chinahrd.entity.dto.pc.common.ConfigDto;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.TableKeyUtil;

/**
 * 系统缓存
 *
 * @author htpeng
 */
public class SysCache {

    private final static String customerId = EisWebContext.getCustomerId();

    /**
     * 获得所有config值
     */
    public static CacheBlock<Map<String, List<String>>> config = new CacheBlockConstructor<Map<String, List<String>>>(
            "queryConfig")
            .getCustomBlock(new CustomBlock<Map<String, List<String>>>() {
                @SuppressWarnings("unchecked")
                @Override
                public Map<String, List<String>> formatData(Object o) {
                    Map<String, List<String>> resultMap = CollectionKit
                            .newMap();
                    if (null == o) {
                        return null;
                    }
                    List<ConfigDto> reList = (List<ConfigDto>) o;
                    // 用父机构ID分组
                    for (ConfigDto dto : reList) {
                        List<String> list = resultMap.get(dto.getConfigKey());
                        if (null == list) {
                            list = CollectionKit.newList();
                            // cala(list,dto.getConfigVal());
                            resultMap.put(dto.getConfigKey(), list);
                        }
                        cala(list, dto.getConfigVal());
                    }
                    return resultMap;
                }

                public void cala(List<String> list, String val) {
                    if (val != null) {
                        String[] arr = val.split(",");
                        for (int i = 0; i < arr.length; i++) {
                            list.add(arr[i]);
                        }
                    }

                }
            }, customerId);


    /**
     * 获得所有子孙机构ID by 父机构ID
     */
    public static CacheBlock<Map<String, List<String>>> cacheAllSubOrganId = new CacheBlockConstructor<Map<String, List<String>>>(
            "queryAllOrganRelation")
            .getCustomBlock(new CustomBlock<Map<String, List<String>>>() {
                @SuppressWarnings("unchecked")
                @Override
                public Map<String, List<String>> formatData(Object o) {
                    Map<String, List<String>> resultMap = CollectionKit
                            .newMap();
                    if (null == o) {
                        return null;
                    }
                    List<KVItemDto> reList = (List<KVItemDto>) o;
                    // 用父机构ID分组
                    for (KVItemDto kVItemDto : reList) {
                        // if(kVItemDto.getK().equals(kVItemDto.getV())){
                        // continue;
                        // }
                        List<String> list = resultMap.get(kVItemDto.getK());
                        if (null == list) {
                            list = CollectionKit.newList();
                            // list.add(kVItemDto.getV());
                            resultMap.put(kVItemDto.getK(), list);
                        }
                        list.add(kVItemDto.getV());
                    }

                    return resultMap;
                }
            }, customerId);

    /**
     * 获得所有子机构ID by 父机构ID
     */
    public static CacheBlock<Map<String, List<String>>> cacheUnderAllSubOrganId = new CacheBlockConstructor<Map<String, List<String>>>(
            "queryUnderAllOrganRelation")
            .getCustomBlock(new CustomBlock<Map<String, List<String>>>() {
                @SuppressWarnings("unchecked")
                @Override
                public Map<String, List<String>> formatData(Object o) {
                    Map<String, List<String>> resultMap = CollectionKit
                            .newMap();
                    if (null == o) {
                        return null;
                    }
                    List<KVItemDto> reList = (List<KVItemDto>) o;
                    // 用父机构ID分组
                    for (KVItemDto kVItemDto : reList) {
                        List<String> list = resultMap.get(kVItemDto.getK());
                        if (null == list) {
                            list = CollectionKit.newList();
                            resultMap.put(kVItemDto.getK(), list);
                        }
                        list.add(kVItemDto.getV());
                    }

                    return resultMap;
                }
            }, customerId);

    /**
     * 员工日切片表最小最大日期
     */
    public static CacheBlock<List<KVItemDto>> queryEmpMinMaxDays =
            new CacheBlockConstructor<List<KVItemDto>>("queryEmpMinMaxDays").getDefaultBlock(customerId);

    /**
     * 获取管理序列层级
     */
    public static CacheBlock<List<KVItemDto>> queryPositionAbility =
            new CacheBlockConstructor<List<KVItemDto>>("queryPositionAbility").getDefaultBlock(customerId);

    /**
     * 获取管理序列层级
     */
    public static CacheBlock<List<KVItemDto>> queryManageAbility =
            new CacheBlockConstructor<List<KVItemDto>>("queryManageAbility").getDefaultBlock(customerId);

    /**
     * 获取大职级（能力层级）
     */
    public static CacheBlock<List<KVItemDto>> queryAbility =
            new CacheBlockConstructor<List<KVItemDto>>("queryAbility").getDefaultBlock(customerId);

    /**
     * 获取大职级（能力层级）
     */
    public static CacheBlock<List<String>> queryAbilityKey =
            new CacheBlockConstructor<List<String>>("queryAbilityKey").getDefaultBlock(customerId);

    /**
     * 性格
     */
    public static CacheBlock<List<KVItemDto>> queryPersonality =
            new CacheBlockConstructor<List<KVItemDto>>("querySoureCodeItem").getDefaultBlock(customerId, TableKeyUtil.CODE_GROUP_PERSONALITY);

    /**
     * 关键人才类型
     */
    public static CacheBlock<Map<String, String>> KeyTalentType =
            new CacheBlockConstructor<Map<String, String>>("queryKeyTalentType").getCustomBlock(new CustomBlock<Map<String, String>>() {
                @SuppressWarnings("unchecked")
                @Override
                public Map<String, String> formatData(Object obj) {
                    Map<String, String> resultMap = CollectionKit.newMap();
                    if (null == obj) {
                        return null;
                    }
                    List<KVItemDto> reList = (List<KVItemDto>) obj;
                    for (KVItemDto kVItemDto : reList) {
                        resultMap.put(kVItemDto.getK(), kVItemDto.getV());
                    }
                    return resultMap;
                }

            }, customerId);

    /**
     * 功能维
     */
    public static CacheBlock<List<FunctionDto>> queryFunction =
            new CacheBlockConstructor<List<FunctionDto>>("queryFunction").getDefaultBlock(customerId);

    /**
     * 主序列维
     */
    public static CacheBlock<List<SequenceItemsDto>> querySequence =
            new CacheBlockConstructor<List<SequenceItemsDto>>("querySeq").getDefaultBlock(customerId);
    /**
     * 子序列
     */
    public static CacheBlock<List<SubSequenceItemsDto>> querySubSequence =
            new CacheBlockConstructor<List<SubSequenceItemsDto>>("querySubSeq").getDefaultBlock(customerId);
    /**
     * 学历
     */
    public static CacheBlock<List<DegreeDto>> queryDegree =
            new CacheBlockConstructor<List<DegreeDto>>("queryDegree").getDefaultBlock(customerId, TableKeyUtil.CODE_GROUP_DEGREE);


    public static CacheBlock<List<KVItemDto>> queryPosition =
            new CacheBlockConstructor<List<KVItemDto>>("queryPosition").getDefaultBlock(customerId);
    public static CacheBlock<List<KVItemDto>> queryOrgan =
            new CacheBlockConstructor<List<KVItemDto>>("queryOrgan").getDefaultBlock(customerId);
    public static CacheBlock<List<KVItemDto>> queryPerformance =
            new CacheBlockConstructor<List<KVItemDto>>("queryPerformance").getDefaultBlock(customerId);
    public static CacheBlock<List<KVItemDto>> queryAbilityLv =
            new CacheBlockConstructor<List<KVItemDto>>("queryAbilityLv").getDefaultBlock(customerId);
    public static CacheBlock<List<KVItemDto>> queryJobTitle =
            new CacheBlockConstructor<List<KVItemDto>>("queryJobTitle").getDefaultBlock(customerId);
    public static CacheBlock<List<KVItemDto>> queryCity =
            new CacheBlockConstructor<List<KVItemDto>>("queryCity").getDefaultBlock(customerId);


    public static CacheBlock<List<KVItemDto>> queryChannel =
            new CacheBlockConstructor<List<KVItemDto>>("queryChannel").getDefaultBlock(customerId);

}

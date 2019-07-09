package net.chinahrd.utils;

import net.chinahrd.entity.dto.KVItemDto;
import net.chinahrd.entity.dto.pc.DegreeDto;
import net.chinahrd.entity.dto.pc.SequenceItemsDto;
import net.chinahrd.entity.dto.pc.SubSequenceItemsDto;
import net.chinahrd.entity.dto.pc.admin.FunctionDto;
import net.chinahrd.module.SysCache;

import java.util.List;
import java.util.Map;


/**
 * 缓存助手
 */
public class CacheHelper {

    private CacheHelper() {
    }

    /**
     * 获得所有子孙机构ID by 父机构ID
     *
     * @param organId
     * @return
     */
    public static List<String> getSubOrganIdList(String organId) {
        Map<String, List<String>> obj = SysCache.cacheAllSubOrganId.get();
        if (null != obj) {
            return obj.get(organId);
        }
        return null;
    }

    /**
     * 获得所有子机构ID by 父机构ID
     *
     * @param organId
     * @return
     */
    public static List<String> getUnderSubOrganIdList(String organId) {
        Map<String, List<String>> obj = SysCache.cacheUnderAllSubOrganId.get();
        if (null != obj) {
            return obj.get(organId);
        }
        return null;
    }

    public static Double getConfigValByCacheDouble(String key) {
        Map<String, List<String>> formatObj = SysCache.config.get();

        if (null != formatObj) {
            List<String> rs = formatObj.get(PropertiesUtil.getProperty(key).trim());
            if (rs.size() > 0) {
                try {
                    return Double.valueOf(rs.get(0));
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return 0.0;
    }

    public static List<Double> getConfigValsByCacheDouble(String key) {
        Map<String, List<String>> formatObj = SysCache.config.get();
        if (null != formatObj) {
            List<String> list = formatObj.get(PropertiesUtil.getProperty(key).trim());
            List<Double> dob = CollectionKit.newList();

            for (String string : list) {
                try {
                    dob.add(Double.valueOf(string));
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            return dob;
        }
        return null;
    }


    /**
     * 获取缓存单一值
     *
     * @param key ConfigKeyUtil值
     * @return
     */
    public static Integer getConfigValByCacheInt(String key) {
        Map<String, List<String>> formatObj = SysCache.config.get();
        if (null != formatObj) {
            List<String> rs = formatObj.get(PropertiesUtil.getProperty(key).trim());
            if (rs.size() > 0) {
                try {
                    return Integer.valueOf(rs.get(0));
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return 0;
    }

    /**
     * 获取缓存多值
     *
     * @param key ConfigKeyUtil值
     * @return
     */
    public static List<Integer> getConfigValsByCacheInt(String key) {
        Map<String, List<String>> formatObj = SysCache.config.get();
        if (null != formatObj) {
            List<String> rs = formatObj.get(PropertiesUtil.getProperty(key).trim());
            List<Integer> intRs = CollectionKit.newList();
            for (String i : rs) {
                try {
                    intRs.add(Integer.valueOf(i));
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return intRs;
        }
        return null;
    }

    /**
     * 获取缓存单值
     *
     * @param key
     * @return
     */
    public static String getConfigValByCacheStr(String key) {
        Map<String, List<String>> formatObj = SysCache.config.get();
        if (null != formatObj) {
            List<String> list = formatObj.get(PropertiesUtil.getProperty(key).trim());
            try {
                return list.get(0);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }


    /**
     * 能力层级表
     */
    public static String getAbilityName(String key) {
        if (null == key) {
            return null;
        }

        List<KVItemDto> reList = CacheHelper.getAbility();
        for (KVItemDto kVItemDto : reList) {
            if (kVItemDto.getK().equals(key)) {
                return kVItemDto.getV();
            }
        }
        return null;
    }
    
    public static List<KVItemDto> getPositionAbility(){
    	return SysCache.queryPositionAbility.get();
    }

    public static List<KVItemDto> getManageAbility() {
        return SysCache.queryManageAbility.get();
    }

    public static List<KVItemDto> getAbility() {
        return SysCache.queryAbility.get();
    }

    public static List<KVItemDto> getPersonality() {
        return SysCache.queryPersonality.get();
    }


    /**
     * 获取主序列维
     *
     * @return
     */
    public static List<SequenceItemsDto> getSequence() {
        return SysCache.querySequence.get();
    }
    
    public static List<SubSequenceItemsDto> getSubSequence(){
    	return SysCache.querySubSequence.get();
    }

    /**
     * 获取学历维
     *
     * @return
     */
    public static List<DegreeDto> getDegree() {
        return SysCache.queryDegree.get();
    }

    /**
     * 员工日切片表最小最大日期
     */
	public static List<KVItemDto> getEmpMinMaxDays(){
		return SysCache.queryEmpMinMaxDays.get();
	}

    /**
     * 员工性格维
     *
     * @return
     */
    public static List<KVItemDto> queryEmpPersonality() {
        return SysCache.queryPersonality.get();
    }

    /**
     * 岗位维
     *
     * @return
     */
    public static List<KVItemDto> getPosition() {
        List<KVItemDto> list = SysCache.queryPosition.get();
        return list;
    }

    /**
     * 机构维
     *
     * @return
     */
    public static List<KVItemDto> getOrgan() {
        List<KVItemDto> list = SysCache.queryOrgan.get();
        return list;
    }

    /**
     * 绩效维
     *
     * @return
     */
    public static List<KVItemDto> getPerformance() {
        List<KVItemDto> list = SysCache.queryPerformance.get();
        return list;
    }

    public static List<KVItemDto> getAbilityLv() {
        return SysCache.queryAbilityLv.get();
    }

    public static List<KVItemDto> getJobTitle() {
        return SysCache.queryJobTitle.get();
    }

    public static List<KVItemDto> getCity() {
        return SysCache.queryCity.get();
    }

    public static List<FunctionDto> getFunction() {
        return SysCache.queryFunction.get();
    }
    public static List<KVItemDto> getChannel(){
    	return SysCache.queryChannel.get();
    }

    /**
     * 传入url得到functionId，作用各个指标得到quotaId
     *
     * @param url
     * @return
     */
    public static String getFunctionId(String url) {
        List<FunctionDto> function = getFunction();
        for (FunctionDto dto : function) {
            if (dto.getUrl().equals(url)) {
                return dto.getFunctionId();
            }
        }
        return null;
    }
}

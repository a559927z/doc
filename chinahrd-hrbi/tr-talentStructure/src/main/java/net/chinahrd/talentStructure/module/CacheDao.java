package net.chinahrd.talentStructure.module;


import org.apache.ibatis.annotations.Param;


public interface CacheDao {


    /**
     * 查询能力层级（大职级）最大的索引
     *
     * @param customerId
     * @return
     */
    Integer queryAbilityMaxIdx(@Param("customerId") String customerId);
}

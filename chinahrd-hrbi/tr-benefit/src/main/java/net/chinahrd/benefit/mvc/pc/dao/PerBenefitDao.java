package net.chinahrd.benefit.mvc.pc.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.benefit.PerBenefitAmountDto;
import net.chinahrd.entity.dto.pc.benefit.PerBenefitDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 人均效益dao
 */
@Repository("perBenefitDao")
public interface PerBenefitDao {
    /**
     * 查询人均效益数据（按月统计）
     *
     * @param organizationId 架构id
     * @param limitNum       要查的月份个数
     * @return
     */
    List<PerBenefitDto> queryTrendByMonth(@Param("customerId") String customerId, @Param("organizationId") String organizationId, @Param("limitNum") int limitNum);

    /**
     * 查询人均效益趋势（按年统计）
     *
     * @param organizationId 架构id
     * @return
     */
    List<PerBenefitDto> queryTrendByYear(@Param("customerId") String customerId, @Param("organizationId") String organizationId);

    /**
     * 查询等效全职员工数的最大日期
     *
     * @param organizationId
     * @return
     */
    Integer findFteMaxDate(@Param("customerId") String customerId, @Param("organizationId") String organizationId);

    /**
     * 查询"当前组织人均效益"（包含子节点为独立核算的部门）,结果集已按照level排序
     *
     * @param organizationId 当前架构id
     * @return 当前架构的人均效益相关数据和其子架构（具有独立核算资格）的人均效益相关数据
     */
    List<PerBenefitDto> queryOrgBenefit(@Param("customerId") String customerId, @Param("organizationId") String organizationId);

    /**
     * 查询当前组织最近12个月人均效益、利润总额、销售总额数据(按时间倒序)
     *
     * @return
     */
    PerBenefitAmountDto queryOrgRecentData(Map<String,Object> map);

    /**
     * 行业均值
     *
     * @param organizationId 当前架构id
     * @return
     */
    Double queryAvgValue(@Param("customerId") String customerId, @Param("organizationId") String organizationId);

    /**
     * 人均效益变化幅度
     *
     * @param organizationId
     * @param upgrade        true: 升幅,false: 降幅
     * @param maxYm          TODO
     * @param perYm          TODO
     * @return
     */
    List<PerBenefitDto> queryVariationRange(@Param("customerId") String customerId,
                                            @Param("organizationId") String organizationId,
                                            @Param("upgrade") boolean upgrade,
                                            @Param("maxYm") int maxYm, @Param("perYm") int perYm
    );
}

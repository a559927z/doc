package net.chinahrd.talentContrast.mvc.pc.dao;

import net.chinahrd.entity.dto.pc.talentContrast.ContrastEmpDto;
import net.chinahrd.entity.dto.pc.EvalReportDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 人员PK Dao接口类
 * Created by wqcai on 15/10/23 0023.
 */
@Repository("talentContrastDao")
public interface TalentContrastDao {
    /**
     * 根据条件查询人才PK员工信息
     *
     * @param customerId
     * @param keyName
     * @param empIds
     * @param organPermitIds TODO
     * @param offset         TODO
     * @param limit          TODO
     * @return
     */
    List<ContrastEmpDto> queryContrastAll(@Param("customerId") String customerId, @Param("keyName") String keyName, @Param("empIds") List<String> empIds,
                                          @Param("organPermitIds") List<String> organPermitIds,
                                          @Param("offset") Integer offset, @Param("limit") Integer limit);

    int queryContrastCount(@Param("customerId") String customerId, @Param("keyName") String keyName, @Param("empIds") List<String> empIds,
                           @Param("organPermitIds") List<String> organPermitIds);

    /**
     * 查询最新一次的测评信息
     *
     * @param customerId
     * @param empIds
     * @return
     */
    List<EvalReportDto> queryNewEvalReport(@Param("customerId") String customerId, @Param("empIds") List<String> empIds);
}

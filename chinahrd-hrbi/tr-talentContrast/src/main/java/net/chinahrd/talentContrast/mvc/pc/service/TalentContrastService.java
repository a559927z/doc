package net.chinahrd.talentContrast.mvc.pc.service;


import net.chinahrd.entity.dto.pc.talentContrast.ContrastEmpDto;
import net.chinahrd.entity.dto.pc.EvalReportDto;
import net.chinahrd.entity.dto.PaginationDto;

import java.util.List;

/**
 * 人才PK service接口类
 * Created by wqcai on 15/10/23 0023.
 */
public interface TalentContrastService {
    /**
     * 根据条件查询人才PK员工信息
     *
     * @param customerId
     * @param keyName    查询条件 可以是编码或名称
     * @param pageDto
     * @return
     */
    PaginationDto<ContrastEmpDto> queryContrastAll(String customerId, String keyName, PaginationDto<ContrastEmpDto> pageDto);

    /**
     * 根据员工ID查询员工信息（多条）
     *
     * @param customerId
     * @param empIds
     * @return
     */
    List<ContrastEmpDto> queryContrastAll(String customerId, List<String> empIds);

    /**
     * 查询最新一次的测评信息
     *
     * @param customerId
     * @param empIds
     * @return
     */
    List<EvalReportDto> queryNewEvalReport(String customerId, String empIds);
}

package net.chinahrd.talentContrast.mvc.pc.service.impl;

import net.chinahrd.core.tools.collection.CollectionKit;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.entity.dto.pc.EvalReportDto;
import net.chinahrd.entity.dto.pc.admin.OrganDto;
import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.talentContrast.ContrastEmpDto;
import net.chinahrd.talentContrast.mvc.pc.dao.TalentContrastDao;
import net.chinahrd.talentContrast.mvc.pc.service.TalentContrastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人员PK Serivice实现类
 * Created by wqcai on 15/10/23 0023.
 */
@Service("talentContrastService")
public class TalentContrastServiceImpl implements TalentContrastService {

    @Autowired
    private TalentContrastDao talentContrastDao;

    @Override
    public PaginationDto<ContrastEmpDto> queryContrastAll(String customerId, String keyName, PaginationDto<ContrastEmpDto> pageDto) {

        // 通过keyName查询员工时，登录人必须要具备查看所在员工下的数据权限	by jxzhang
        List<String> organPermitIds = getOrganPermitId();
        if (organPermitIds.isEmpty()) {
            return null;
        }

        int count = talentContrastDao.queryContrastCount(customerId, keyName, null, organPermitIds);

        List<ContrastEmpDto> dtos = talentContrastDao.queryContrastAll(customerId, keyName, null, organPermitIds, pageDto.getOffset(), pageDto.getLimit());

        pageDto.setRecords(count);
        pageDto.setRows(dtos);
        return pageDto;
    }

    @Override
    public List<ContrastEmpDto> queryContrastAll(String customerId, List<String> empIds) {

        // 通过keyName查询员工时，登录人必须要具备查看所在员工下的数据权限	by jxzhang
        List<String> organPermitIds = getOrganPermitId();
        if (organPermitIds.isEmpty()) {
            return null;
        }

        List<ContrastEmpDto> resultDto = CollectionKit.newList();
        List<ContrastEmpDto> dtos = talentContrastDao.queryContrastAll(customerId, null, empIds, organPermitIds, null, null);

        for (int i = 0; i < empIds.size(); i++) {
            for (ContrastEmpDto dto : dtos) {
                if (empIds.get(i).equals(dto.getEmpId())) {
                    resultDto.add(dto);
                }
            }
        }
        return resultDto;
    }

    /**
     * 登录人所有数据权限ID集	by jxzhang
     *
     * @return
     */
    private List<String> getOrganPermitId() {
        List<OrganDto> organPermit = EisWebContext.getCurrentUser().getOrganPermit();
        List<String> rs = CollectionKit.newList();
        if (null == organPermit) {
            rs.add("");
            return rs;
        }
        for (OrganDto organDto : organPermit) {
            rs.add(organDto.getOrganizationId());
        }
        return rs;
    }


    @Override
    public List<EvalReportDto> queryNewEvalReport(String customerId, String empId) {
        List<String> empIds = CollectionKit.strToList(empId);
        return talentContrastDao.queryNewEvalReport(customerId, empIds);
    }

}

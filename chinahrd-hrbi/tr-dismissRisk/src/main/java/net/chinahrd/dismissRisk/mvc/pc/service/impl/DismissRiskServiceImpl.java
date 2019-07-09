package net.chinahrd.dismissRisk.mvc.pc.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.chinahrd.dismissRisk.mvc.pc.dao.DismissRiskDao;
import net.chinahrd.dismissRisk.mvc.pc.service.DismissRiskService;
import net.chinahrd.dismissRisk.mvc.pc.task.DismissRiskTask;
import net.chinahrd.eis.notice.NoticeConfig;
import net.chinahrd.eis.notice.service.NoticeService;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRiskDto;
import net.chinahrd.entity.dto.pc.accordDismiss.DismissRiskReviewDto;
import net.chinahrd.entity.dto.pc.common.RiskDto;
import net.chinahrd.entity.dto.pc.common.RiskTreeDto;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;

/**
 * 人才流失风险service
 */
@Service("dismissRiskService")
public class DismissRiskServiceImpl implements DismissRiskService {

    @Autowired
    private DismissRiskDao dismissRiskDao;

    @Override
    public List<DismissRiskDto> getDirectRiskData(String customerId, String empId) {
        return dismissRiskDao.queryDirectRisk(customerId, empId);
    }

    @Override
    public List<DismissRiskDto> querySubOrganEmpInfo(String empId, String customerId) {
        return dismissRiskDao.querySubOrganEmpInfo(empId, customerId);
    }

    @Override
    public List<DismissRiskReviewDto> queryDimissRissReviewInfo(String customerId, String empId) {
        Integer period = CacheHelper.getConfigValByCacheInt(ConfigKeyUtil.RCLSFX_HASMESSAGE);
        if (null == period){
            period = 2;
        }
        return dismissRiskDao.queryDimissRissReviewInfo(customerId, empId, period.intValue());
    }

    @Override
    public List<RiskTreeDto> getEmpRiskDetail(String customerId, String empId) {

        List<RiskTreeDto> empRiskDetail = dismissRiskDao.queryEmpRiskDetail(customerId, empId);
        if (CollectionKit.isEmpty(empRiskDetail)) {
            return empRiskDetail;
        }
        String topRiskId = "";
        int topRiskFlag = 0;
        String note = "";
        for (RiskTreeDto riskTreeDto : empRiskDetail) {
            String riskId = riskTreeDto.getTopRiskId();
            if (StringUtils.isEmpty(riskId)) {
                continue;
            }
            topRiskId = riskId;
            topRiskFlag = riskTreeDto.getTopRiskFlag();
            note = riskTreeDto.getNote();
            break;
        }
        //因为数据库上“行为表现”和“主观判断”的父id为-1，所以顶级节点的虚拟id需为-1，才能组成一棵树
        RiskTreeDto riskTreeDto = new RiskTreeDto("-1", topRiskFlag, topRiskId, note);
        empRiskDetail.add(0, riskTreeDto);
        return empRiskDetail;
    }

    @Override
    public List<RiskDto> getEmpAllRiskDetail(String customerId, String empId) {
        return dismissRiskDao.queryEmpAllRiskDetail(customerId, empId);
    }

    @Override
    public List<RiskTreeDto> queryEmpRiskDefaultDetail(String customerId) {
        return dismissRiskDao.queryEmpRiskDefaultDetail(customerId);
    }


    @Override
    public List<String> queryRiskEmpIds(String customerId, String condStr) {

        List<String> empIds = new ArrayList<>();

        if (condStr == null || condStr.trim().isEmpty()) {
            return empIds;
        }

        String regex = DismissRiskTask.initCfgValueRule().get(NoticeService.WARNING_NOTICE_COND);
        String[] conds = NoticeConfig.validate(condStr, regex);

        List<String> condList = Arrays.asList(conds);

        if (condList != null && condList.isEmpty()) {
            return empIds;
        }

        return dismissRiskDao.queryRiskEmpIds(customerId, condList);
    }
}

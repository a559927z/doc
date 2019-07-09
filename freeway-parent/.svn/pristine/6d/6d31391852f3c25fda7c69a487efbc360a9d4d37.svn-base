package net.chinahrd.modules.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.chinahrd.common.PageData;
import net.chinahrd.modules.demo.dao.EmpInfoDAO;
import net.chinahrd.modules.demo.entity.EmpInfo;
import net.chinahrd.modules.demo.request.EmpQueryReq;
import net.chinahrd.modules.demo.response.EmpInfoRes;
import net.chinahrd.modules.demo.service.EmpInfoService;
import net.chinahrd.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bright
 * @since 2019-03-20
 */
@Slf4j
@Component
@Service(interfaceClass = EmpInfoService.class)
public class EmpInfoServiceImpl extends ServiceImpl<EmpInfoDAO, EmpInfo> implements EmpInfoService {
    @Resource
    EmpInfoDAO empInfoDAO;

    @Override
    public PageData<EmpInfoRes> selectByPage(EmpQueryReq dataReq) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(dataReq.getEmpName())) {
            paramMap.put("empName", dataReq.getEmpName());
        }
        if (dataReq.getCurrentUser() != null && StringUtils.isNotBlank(dataReq.getCurrentUser().getTenantId())) {
            paramMap.put("tenantId", dataReq.getCurrentUser().getTenantId());
        }

        PageUtil.startPage(dataReq);
        List<EmpInfoRes> list = empInfoDAO.listEmpInfo(paramMap);
        return PageUtil.convert(list);
    }
}

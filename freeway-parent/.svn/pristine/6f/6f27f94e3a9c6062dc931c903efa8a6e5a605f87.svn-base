package net.chinahrd.platform.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import net.chinahrd.common.PageData;
import net.chinahrd.platform.demo.dao.DemoUserInfoDAO;
import net.chinahrd.platform.demo.entity.DemoUserInfo;
import net.chinahrd.platform.demo.request.DemoUserQueryReq;
import net.chinahrd.platform.demo.response.DemoUserInfoRes;
import net.chinahrd.platform.demo.service.DemoUserInfoService;
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
 * 服务实现类
 * </p>
 *
 * @author bright
 * @since 2019-03-05
 */
@Slf4j
@Component
@Service(interfaceClass = DemoUserInfoService.class)
public class DemoUserInfoServiceImpl extends ServiceImpl<DemoUserInfoDAO, DemoUserInfo> implements DemoUserInfoService {

    @Resource
    DemoUserInfoDAO demoUserInfoDAO;

    @Override
    public PageData<DemoUserInfoRes> selectByPage(DemoUserQueryReq dataReq) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(dataReq.getUserName())) {
            paramMap.put("userName", dataReq.getUserName());
        }
        if (dataReq.getCurrentUser() != null && StringUtils.isNotBlank(dataReq.getCurrentUser().getTenantId())) {
            paramMap.put("tenantId", dataReq.getCurrentUser().getTenantId());
        }

        PageUtil.startPage(dataReq);
        List<DemoUserInfoRes> userList = demoUserInfoDAO.listUserInfo(paramMap);
        return PageUtil.convert(userList);
    }


//    @Scheduled(cron = "0/5 0 0 * * ?")
    @Override
    public List<DemoUserInfoRes> selectListByTask() {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userName", "bright");
        return demoUserInfoDAO.listUserInfo(paramMap);
    }


}

package net.chinahrd.modules.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.chinahrd.common.PageData;
import net.chinahrd.modules.demo.dao.DemoUserInfoDAO;
import net.chinahrd.modules.demo.entity.UserInfo;
import net.chinahrd.modules.demo.request.UserQueryReq;
import net.chinahrd.modules.demo.response.UserInfoRes;
import net.chinahrd.modules.demo.service.UserInfoService;
import net.chinahrd.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
@Service
public class UserInfoServiceImpl extends ServiceImpl<DemoUserInfoDAO, UserInfo> implements UserInfoService {

    @Resource
    DemoUserInfoDAO demoUserInfoDAO;

    @Override
    public PageData<UserInfoRes> selectByPage(UserQueryReq dataReq) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(dataReq.getUserName())) {
            paramMap.put("userName", dataReq.getUserName());
        }
        if (dataReq.getCurrentUser() != null && StringUtils.isNotBlank(dataReq.getCurrentUser().getTenantId())) {
            paramMap.put("tenantId", dataReq.getCurrentUser().getTenantId());
        }

        PageUtil.startPage(dataReq);
        List<UserInfoRes> userList = demoUserInfoDAO.listUserInfo(paramMap);
        return PageUtil.convert(userList);
    }
}

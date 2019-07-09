package net.chinahrd.modules.rbac.service.impl;

import net.chinahrd.modules.rbac.dao.UserInfoDAO;
import net.chinahrd.service.rbac.SecurityService;
import net.chinahrd.response.RbacUserRes;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * rbac服务
 * @author wzmo
 */
@Service
public class AdminSecurityServiceImpl implements SecurityService {

    @Resource
    UserInfoDAO userInfoDAO;
    @Override
    public RbacUserRes selectByUserName(String userName) {
        List<RbacUserRes> rbacUserResList =  userInfoDAO.selectByUserName(userName);
        if(!CollectionUtils.isEmpty(rbacUserResList)) {
            for (RbacUserRes rbacUserRes : rbacUserResList) {
                return rbacUserRes;
            }
        }
        return null;
    }
}

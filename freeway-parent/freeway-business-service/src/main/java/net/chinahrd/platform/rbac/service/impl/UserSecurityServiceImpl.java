package net.chinahrd.platform.rbac.service.impl;

import net.chinahrd.platform.rbac.dao.UserInfoDAO;
import net.chinahrd.response.RbacUserRes;
import net.chinahrd.service.rbac.SecurityService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * rbac服务
 * @author wzmo
 */
@Service
public class UserSecurityServiceImpl implements SecurityService{

    @Resource
    private UserInfoDAO userInfoDAO;

    public RbacUserRes selectByUserName(String userName) {
        List<RbacUserRes> rbacUserResList = userInfoDAO.selectByUserName(userName);
        if(!CollectionUtils.isEmpty(rbacUserResList)) {
            for (RbacUserRes rbacUserRes : rbacUserResList) {
                return rbacUserRes;
            }
        }
        return null;
    }
}

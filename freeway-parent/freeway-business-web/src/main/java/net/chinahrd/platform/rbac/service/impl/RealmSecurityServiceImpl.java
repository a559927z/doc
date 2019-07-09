package net.chinahrd.platform.rbac.service.impl;

import net.chinahrd.response.RbacUserRes;
import net.chinahrd.service.rbac.SecurityService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
/**
 * 支持Realm调用dobbo服务
 * @author wzmo
 */
@Service
public class RealmSecurityServiceImpl implements SecurityService {

    @Reference
    private SecurityService securityService;

    @Override
    public RbacUserRes selectByUserName(String userName) {
        return securityService.selectByUserName(userName);
    }
}

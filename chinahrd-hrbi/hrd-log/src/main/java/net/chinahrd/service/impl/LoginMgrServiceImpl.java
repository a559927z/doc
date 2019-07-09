package net.chinahrd.service.impl;

import net.chinahrd.dao.LoginMgrDao;
import net.chinahrd.dto.EmpExtendDto;
import net.chinahrd.service.LoginMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jxzhang on 2018年05月01
 * @Verdion 1.0版本
 */
@Service("loginMgrService")
public class LoginMgrServiceImpl implements LoginMgrService {

    @Autowired
    private LoginMgrDao loginMgrDao;

    @Override
    public EmpExtendDto getUser(String userKey, String password) {
        EmpExtendDto user = loginMgrDao.getUser(userKey, password);
        if (user == null) return null;

        List<String> roleKeyList = loginMgrDao.queryRoleKeyByUserId(user.getUserId());
        user.setRoleKeyList(roleKeyList);
        return user;
    }

    @Override
    public EmpExtendDto getUser(String userId) {
        EmpExtendDto user = loginMgrDao.getUserByUserId(userId);
        if (user == null) return null;

        List<String> roleKeyList = loginMgrDao.queryRoleKeyByUserId(user.getUserId());
        user.setRoleKeyList(roleKeyList);
        return user;
    }


}

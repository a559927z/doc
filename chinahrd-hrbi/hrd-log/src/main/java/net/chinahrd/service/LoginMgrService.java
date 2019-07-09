package net.chinahrd.service;

import net.chinahrd.dto.EmpExtendDto;

/**
 * @author jxzhang on 2018年05月01
 * @Verdion 1.0版本
 */
public interface LoginMgrService {

    /**
     * 获取员工
     *
     * @param userKey
     * @param password
     * @return
     */
    EmpExtendDto getUser(String userKey, String password);

    /**
     * 获取员工
     *
     * @param userId
     * @return
     */
    EmpExtendDto getUser(String userId);
}

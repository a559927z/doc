package net.chinahrd.service;

import net.chinahrd.vo.UserLockVo;

import java.util.List;

public interface EmpMgrService {

    /**
     * 员工列表
     *
     * @param startIndex
     * @param pageSize
     * @param search
     * @param orderColumn
     * @param orderDir
     * @return
     */
    List<UserLockVo> queryEmpList(Integer startIndex, Integer pageSize, String search, String orderColumn, String orderDir);


    /**
     * 逻辑删除员工状态(加锁)
     *
     * @param userIds
     */
    void updateLockStatus(List<String> userIds);

    /**
     * 更新员工年假
     *
     * @param annualTotal
     * @param userId
     */
    void updateAnnualTotal(Double annualTotal, String userId);


    /**
     * 删除
     *
     * @param userIds
     * @return 删除总条数
     */
    int delete(List<String> userIds);
}
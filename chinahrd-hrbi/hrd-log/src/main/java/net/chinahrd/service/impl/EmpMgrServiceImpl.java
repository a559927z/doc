package net.chinahrd.service.impl;

import com.google.common.collect.Lists;
import net.chinahrd.constants.YesOrOnEnum;
import net.chinahrd.dao.DimUserMapper;
import net.chinahrd.dao.EmpMgrDao;
import net.chinahrd.dao.EmpVacationMapper;
import net.chinahrd.dao.VDimEmpMapper;
import net.chinahrd.dto.*;
import net.chinahrd.service.EmpMgrService;
import net.chinahrd.service.LoginMgrService;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.VerifyPermissionUtil;
import net.chinahrd.utils.WebUtils;
import net.chinahrd.vo.UserLockVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Title: ${type_name} <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年04月27日 12:12
 * @Verdion 1.0 版本
 * ${tags}
 */
@Service("empMgrService")
public class EmpMgrServiceImpl implements EmpMgrService {

    @Qualifier("empMgrDao")
    @Autowired
    private EmpMgrDao empMgrDao;

    @Autowired
    private EmpVacationMapper empVacationMapper;

    @Autowired
    private DimUserMapper dimUserMapper;

    @Autowired
    private VDimEmpMapper vDimEmpMapper;

    @Autowired
    private LoginMgrService loginMgrService;

    @Autowired
    private VerifyPermissionUtil verifyPermissionUtil;

    @Override
    public List<UserLockVo> queryEmpList(Integer startIndex, Integer pageSize, String search, String orderColumn, String orderDir) {
        Long total = empMgrDao.countEmpSQL(YesOrOnEnum.lock.disable(), search);
        List<EmpExtendDto> userList = empMgrDao.queryEmpList(null, startIndex, pageSize, search, orderColumn, orderDir);
//        List<EmpExtendDto> userList = empMgrDao.queryEmpList(YesOrOnEnum.lock.disable(), startIndex, pageSize, search, orderColumn, orderDir);

        HttpServletRequest request = WebUtils.getRequest();
        boolean adminOrSuperAdmin = verifyPermissionUtil.isAdminOrSuperAdmin(request);


        List<UserLockVo> rs = Lists.newArrayList();
        for (EmpExtendDto empDto : userList) {
            UserLockVo vo = new UserLockVo();
            BeanUtils.copyProperties(empDto, vo);
            vo.setTotal(null == total ? 0 : total);
            if (!adminOrSuperAdmin) {
                vo.setNationalId("******");
            }
            rs.add(vo);
        }
        return rs;
    }

    @Override
    public void updateLockStatus(List<String> userIds) {
        int total = 0;
        for (String userId : userIds) {
            //查出empId
            DimUserExample userIdParam = new DimUserExample();
            userIdParam.createCriteria().andUserIdEqualTo(userId);
            List<DimUser> dimUsers = dimUserMapper.selectByExample(userIdParam);
            if (dimUsers.size() == 1) {
                Boolean isLock = dimUsers.get(0).getIsLock();

                // 取反
                boolean relIsLock = isLock == true ? false : true;
                DimUser dto = new DimUser();
                dto.setIsLock(relIsLock);


                DimUserExample queryParam = new DimUserExample();
                queryParam.createCriteria().andUserIdEqualTo(userId);
                int delUserNum = dimUserMapper.updateByExampleSelective(dto, queryParam);
                total += delUserNum;
            }
        }
//        empMgrDao.updateLockStatus(isLock, userIds);
    }

    @Override
    public void updateAnnualTotal(Double annualTotal, String userId) {
        EmpExtendDto user = loginMgrService.getUser(userId);
        String empId = user.getEmpId();
        EmpVacation dto = new EmpVacation();
        dto.setEmpId(empId);
        dto.setCustomerId("1");
        dto.setEmpKey(user.getUserKey());
        dto.setUserNameCh(user.getUserNameCh());
        dto.setAnnualTotal(String.valueOf(annualTotal));

        EmpVacation vacation = empMgrDao.getVacation(empId);
        if (null == vacation) {
            // 剩余年假
            dto.setAnnual(String.valueOf(annualTotal));
            // 剩余内部调休假
            dto.setCanLeave("0");
            // 剩余加班时长
            dto.setHistoryHour("0");
            empMgrDao.saveAnnual(dto);
        } else {
            // 当补入新的年假天数时
            // 1. 减掉原本欠公司的年假 2. 如何有剩余年假就加起来
            double calcAnnual = annualTotal + Double.parseDouble(vacation.getAnnual());
            dto.setAnnual(String.valueOf(calcAnnual));
            dto.setHistoryHour(vacation.getHistoryHour());
            empMgrDao.updateAnnualTotal(String.valueOf(annualTotal), String.valueOf(calcAnnual), empId);
        }
    }

    @Override
    public int delete(List<String> userIds) {
        int total = 0;
        for (String userId : userIds) {
            //查出empId
            DimUserExample userIdParam = new DimUserExample();
            userIdParam.createCriteria().andUserIdEqualTo(userId);
            List<DimUser> dimUsers = dimUserMapper.selectByExample(userIdParam);
            if (dimUsers.size() == 1) {
                String empId = dimUsers.get(0).getEmpId();
                // 删除 dimUser
                DimUserExample queryParam = new DimUserExample();
                queryParam.createCriteria().andEmpIdEqualTo(empId);
                int delUserNum = dimUserMapper.deleteByExample(queryParam);
                total += delUserNum;
                // 删除 vDimEmp
                VDimEmpExample queryParam1 = new VDimEmpExample();
                queryParam1.createCriteria().andEmpIdEqualTo(empId);
                int delEmpNum = vDimEmpMapper.deleteByExample(queryParam1);
                total += delEmpNum;
            }
        }
        return total;
    }

    /**
     * 没测试
     *
     * @param annualTotal
     * @param userId
     */
    public void updateAnnualTotal2(Integer annualTotal, String userId) {
        EmpExtendDto user = loginMgrService.getUser(userId);
        String empId = user.getEmpId();
        EmpVacation dto = new EmpVacation();
        dto.setEmpId(empId);
        dto.setCustomerId("1");
        dto.setEmpKey(user.getEmpKey());
        dto.setUserNameCh(user.getUserNameCh());
        dto.setAnnualTotal(String.valueOf(annualTotal));

        EmpVacationExample queryParam = new EmpVacationExample();
        queryParam.createCriteria().andEmpIdEqualTo(empId);
        List<EmpVacation> empVacations = empVacationMapper.selectByExample(queryParam);
        if (CollectionKit.isEmpty(empVacations)) {
            dto.setAnnual("0");
            dto.setHistoryHour("0");
            empVacationMapper.insert(dto);
        } else {
            EmpVacation vacation = empVacations.get(0);
            // 当补入新的年假天数时
            double temp = Double.parseDouble(vacation.getAnnual());
            double calcAnnual = 0.0;
            if (temp < 0) {
                // 减掉原本欠公司的年假
                calcAnnual = annualTotal - temp;
            } else {
                // 如何有剩余年假就加起来
                calcAnnual = (annualTotal - Double.parseDouble(vacation.getAnnualTotal())) + temp;
            }
            dto.setAnnual(String.valueOf(calcAnnual));
            dto.setHistoryHour(vacation.getHistoryHour());
            empVacationMapper.updateByExampleSelective(dto, queryParam);
        }
    }
}

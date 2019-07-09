package net.chinahrd.utils;

import net.chinahrd.constants.BaseConstants;
import net.chinahrd.constants.RoleFunctionConstants;
import net.chinahrd.dto.EmpExtendDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Title: 验证权限 <br/>
 * <p>
 * Description: <br/>
 *
 * @author jxzhang
 * @DATE 2018年11月29日 14:03
 * @Verdion 1.0 版本
 * ${tags}
 */
@Component
public class VerifyPermissionUtil {

    /**
     * 是否超管或某分公司管理员
     *
     * @param request
     * @return
     */
    public boolean isAdminOrSuperAdmin(HttpServletRequest request) {
        EmpExtendDto empExtendDto = this.getEmpExtendDto(request);
        if (null == empExtendDto) {
            return false;
        }
        List<String> roleKeyList = empExtendDto.getRoleKeyList();

//        for (String i : roleKeyList) {
//            if (BaseConstants.SUPER_ADMIN.equals(i) || StringUtils.containsAny(BaseConstants.ADMIN, i)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return false;
        return roleKeyList.stream().anyMatch(i -> BaseConstants.SUPER_ADMIN.equals(i) || StringUtils.containsAny(BaseConstants.ADMIN, i));
    }


    /**
     * 是否有导出月度考勤表
     *
     * @param request
     * @return
     */
    public boolean isExportAttendanceMonthly(HttpServletRequest request) {
        if (StringUtils.equals(request.getRequestURI(), RoleFunctionConstants.IMPORT_EXCEL.uri())) {
            return this.isMatchRoleId(request, RoleFunctionConstants.IMPORT_EXCEL.roleId());
        }
        return false;
    }

    /**
     * 是否匹配指定角色Id
     *
     * @param request
     * @param roleId
     * @return
     */
    private boolean isMatchRoleId(HttpServletRequest request, String roleId) {
        EmpExtendDto empExtendDto = this.getEmpExtendDto(request);
        if (null == empExtendDto) {
            return false;
        }
        List<String> roleKeyList = empExtendDto.getRoleKeyList();
        return roleKeyList.stream().anyMatch(i -> i.equals(roleId));
    }


    /**
     * 获session里的用户对象
     *
     * @return
     */
    private EmpExtendDto getEmpExtendDto(HttpServletRequest request) {
        return (EmpExtendDto) request.getSession().getAttribute("empInfo");
    }


}

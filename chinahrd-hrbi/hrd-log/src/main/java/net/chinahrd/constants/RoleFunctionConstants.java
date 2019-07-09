package net.chinahrd.constants;

/**
 * Title: 角色、功能、uri <br/>
 * <p>
 * Description: <br/>
 * 因为日志系统统一用考勤系统数据库。
 * <p>
 * 针对日志系统不走考勤系统的dim_function表，暂时用常量
 *
 * @author jxzhang
 * @DATE 2019年02月14日 23:48
 * @Verdion 1.0 版本
 * ${tags}
 */
public enum RoleFunctionConstants {


    /**
     * 考勤导出功能
     */
    IMPORT_EXCEL() {
        @Override
        public String roleId() {
            return "abbf5e16692547829f1396b92318194b";
        }

        @Override
        public String uri() {
            return "/import/exportAttendanceMonthly.do";
        }
    };

    /**
     * dim_role.role_id
     *
     * @return
     */
    public abstract String roleId();

    /**
     * controller
     *
     * @return
     */
    public abstract String uri();

}

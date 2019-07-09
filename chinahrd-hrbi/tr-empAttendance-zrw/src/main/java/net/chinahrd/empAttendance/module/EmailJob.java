package net.chinahrd.empAttendance.module;

import java.util.List;

import net.chinahrd.core.annotation.Injection;
import net.chinahrd.core.job.JobRegisterAbstract;
import net.chinahrd.core.timer.model.JobContext;
import net.chinahrd.core.timer.model.TimerConfig;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.mvc.pc.service.common.ConfigService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.eMail.MailUtil;

/**
 * 员工考勤每月5号定时发送邮件提醒
 *
 * @author jxzhang on 2016年12月20日
 * @Verdion 1.0 版本
 */
public class EmailJob extends JobRegisterAbstract {


    @Injection
    private MailUtil mailUtil;


    @Injection
    private ConfigService configService;

    @Override
    public void execute(JobContext context) {
        mailMain();
    }

    /**
     * 定时器
     */
    @Override
    public void setTimerConfig(TimerConfig tc) {
        tc.setPriority(4);
        tc.setCron("0 0 9 5 * ?"); // 每年，每月5号，早上9点
    }

    /**
     * 入口
     */
    public void mailMain() {

        // TODO 只有中人网合同单位是广州的员工，情况特殊先硬编码。产品需求是登录人所有机构。
        List<String> subOrganIdList = CacheHelper.getSubOrganIdList("d7fd6b524e5111e6999590b11c32f63c");
        List<String> rs = configService.querySendToEmpList(EisWebContext.getCustomerId(), subOrganIdList);
        mailUtil.send(rs, "考勤记录填写", telmplet());
    }

    /**
     * 邮件模板
     *
     * @return
     */
    private String telmplet() {
        StringBuffer buf = new StringBuffer();
        buf.append("<HTML><head></head><body>");
        buf.append("<p>");
        buf.append("    &nbsp;大家好！<br/>");
        buf.append("</p>");
        buf.append("<div>");
        buf.append("    <br/>考勤调整期即将截止（终止期限为每月7日），为避免影响个人的薪酬核算，请各位速速上考勤系统核对上月的考勤数据。");
        buf.append("</div>");
        buf.append("<div>");
        buf.append("    <br/><a href='http://links.chinahrd.net'>http://links.chinahrd.net</a>");
        buf.append("</div>");
        buf.append("</body></html>");
        return buf.toString();
    }

}

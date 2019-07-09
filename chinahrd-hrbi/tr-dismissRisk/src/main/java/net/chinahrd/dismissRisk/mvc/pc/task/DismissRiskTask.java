package net.chinahrd.dismissRisk.mvc.pc.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.chinahrd.dismissRisk.mvc.pc.service.DismissRiskService;
import net.chinahrd.eis.notice.NoticeInfo;
import net.chinahrd.eis.notice.service.NoticeService;
import net.chinahrd.entity.dto.pc.common.ConfigDto;
import net.chinahrd.mvc.pc.service.common.ConfigService;

/**
 * 人才流失率风险定时任务
 * 
 * @author hhzhou
 * 
 */
public class DismissRiskTask {
	
	private static Logger log = LoggerFactory.getLogger(DismissRiskTask.class);
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private DismissRiskService riskService;
	
	@Autowired
	private NoticeService noticeService;

	public void doDismissRisk() {
		
		log.info("开始执行 人才流失风险 预警扫描/通知任务...");
		
		Map<String, List<ConfigDto>> riskCfgMap = configService.getConfigs(null, DismissRiskService.CFG_PREFIX);
		
		Iterator<String> keys = riskCfgMap.keySet().iterator();
		
		while (keys.hasNext()) {
			
			String customerId = (String) keys.next();
			List<ConfigDto> cfgs = riskCfgMap.get(customerId);
			if (cfgs == null || cfgs.isEmpty()) {
				continue;
			}
			
			log.info("初始化通知服务...");
			
			noticeService.init(cfgs, DismissRiskService.CFG_PREFIX, initCfgValueRule());
			
			// 查询符合提醒的人员列表
			List<String> empIdList = riskService.queryRiskEmpIds(customerId, noticeService.getNoticeCond());
			
			if(empIdList == null || empIdList.isEmpty()) {
				return;
			}
			
			for (String role : noticeService.getNoticeRole()) {
					
				NoticeInfo infoParam = new NoticeInfo();
				switch (role) {
				case "1":// 1-上级
					infoParam.setMailSubject("${headName},您的下属有离职风险了！");
					infoParam.setMailTemplate("DimissionRisk_HF_MailTemplate.ftl");
					
					infoParam.setSmsSignName("活动验证");
					infoParam.setSmsTemplate("SMS_5043965");
					break;
					
				case "2":// 2-架构负责人
					infoParam.setMailSubject("${headName},您所管理的组织有流失率预警了！");
					infoParam.setMailTemplate("DimissionRisk_Organ_MailTemplate.ftl");
					
					infoParam.setSmsSignName("活动验证");
					infoParam.setSmsTemplate("SMS_5079198");
					break;
					
				case "3":// 3-BP
					infoParam.setMailSubject("${headName},您所管理的组织有流失率预警了！");
					infoParam.setMailTemplate("DimissionRisk_BP_MailTemplate.ftl");

					infoParam.setSmsSignName("活动验证");
					infoParam.setSmsTemplate("SMS_5004057");
					break;
				default:
					break;
				}
				
				noticeService.resolve(customerId, empIdList, role, infoParam);
			}
			
			noticeService.sendNotices();
		}
		
		log.info("人才流失率风险 预警扫描/通知任务 结束.");
	}
	
	/**
	 * 初始化参数的有效值
	 * @return
	 */
	public static Map<String, String> initCfgValueRule() {
		Map<String, String> ruleMap = new HashMap<String, String>();
		ruleMap.put(NoticeService.WARNING_NOTICE_TYPE, "1,2");
		ruleMap.put(NoticeService.WARNING_NOTICE_ROLE, "1,2,3");
		ruleMap.put(NoticeService.WARNING_NOTICE_COND, "1,2");
		return ruleMap;
	}

}

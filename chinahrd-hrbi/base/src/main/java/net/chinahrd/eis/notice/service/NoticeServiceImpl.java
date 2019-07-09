package net.chinahrd.eis.notice.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.eis.notice.NoticeInfo;
import net.chinahrd.entity.dto.pc.common.ConfigDto;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("noticeService")
@Scope("prototype")
public class NoticeServiceImpl implements NoticeService {
	private static Logger log=Logger.getLogger(NoticeServiceImpl.class);
	@Override
	public void init(List<ConfigDto> cfgs, String prefix,
			Map<String, String> regexMap) {
		// TODO Auto-generated method stub
		log.info("暂不支持");
	}

	@Override
	public void resolve(String customerId, List<String> empIdList, String role,
			NoticeInfo infoParam) {
		// TODO Auto-generated method stub
		log.info("暂不支持");
	}


	@Override
	public void sendNotices() {
		// TODO Auto-generated method stub
		log.info("暂不支持");
	}


	@Override
	public String getNoticeCond() {
		// TODO Auto-generated method stub
		log.info("暂不支持");
		return null;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.eis.notice.service.NoticeService#getNoticeRole()
	 */
	@Override
	public String[] getNoticeRole() {
		// TODO Auto-generated method stub
		log.info("暂不支持");
		return new String[0];
	}
//	@Autowired
//	private SMSService smsService;
//	@SuppressWarnings("unused")
//	@Autowired
//	private MailService mailService;
//	
//	@Autowired
//    private NoticeDao noticeDao;
//    
//	private List<NoticeInfo> infos = new ArrayList<>();
//	private NoticeConfig config = new NoticeConfig();
//
//
//	/* (non-Javadoc)
//	 * @see net.chinahrd.eis.notice.service.NoticeService2#init(java.util.List, java.lang.String)
//	 */
//	@Override
//	public void init(List<ConfigDto> cfgs, final String prefix, Map<String, String> ruleMap) {
//		
//		for (ConfigDto cfg : cfgs) {
//			
//			if(cfg.getConfigKey().equalsIgnoreCase(prefix + WARNING_NOTICE_TYPE)){
//				config.setNoticeType(cfg.getConfigVal(), ruleMap.get(WARNING_NOTICE_TYPE));
//				continue;
//			}
//			
//			if(cfg.getConfigKey().equalsIgnoreCase(prefix + WARNING_NOTICE_ROLE)){
//				config.setNoticeRole(cfg.getConfigVal(), ruleMap.get(WARNING_NOTICE_ROLE));
//				continue;
//			}
//			
//			if(cfg.getConfigKey().equalsIgnoreCase(prefix + WARNING_NOTICE_COND)){				
//				config.setNoticeCond(cfg.getConfigVal(), ruleMap.get(WARNING_NOTICE_COND));
//				continue;
//			}
//			
//		}
//		
//		this.infos.clear();
//	}
//
//	/* (non-Javadoc)
//	 * @see net.chinahrd.eis.notice.service.NoticeService2#resolve(java.lang.String, java.util.List, java.lang.String, net.chinahrd.eis.notice.NoticeInfo)
//	 */
//	@Override
//	public void resolve(String customerId, List<String> empIdList,
//			String role, NoticeInfo infoParam) {
//		
//		if (empIdList == null || empIdList.isEmpty()) {
//			return;
//		}
//		
//		List<SimpleEmpDto> dtos = new ArrayList<>();
//
//		switch (role) {
//		case "1":
//			dtos = noticeDao.queryHFEmpInfos(customerId, empIdList);
//			break;
//		case "2":
//			dtos = noticeDao.queryOrgHeadOrBPEmpInfos(customerId, empIdList,"1");
//			break;
//		case "3":
//			dtos = noticeDao.queryOrgHeadOrBPEmpInfos(customerId, empIdList,"2");
//			break;
//		default:
//			break;
//		}
//
//		for (SimpleEmpDto dto : dtos) {
//			Map<String, String> maps = new HashMap<String, String>();
////			maps.put(SYS_NAME, EisWebContext.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_SYSNAME));
////			maps.put("product", EisWebContext.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_SYSNAME));
////			maps.put("adminMail", EisWebContext.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_ADMINMAIL));
////			
//			maps.put(TEMPLATEPARAM_HEAD_NAME, dto.getHeadName());
//			maps.put(TEMPLATEPARAM_ORGAN_NAMES, dto.getOrganName2());
//			maps.put(TEMPLATEPARAM_EMP_NAMES, dto.getEmpName2());
//			maps.put(TEMPLATEPARAM_DATESTR, dto.getDateStr());
//
//			NoticeInfo info = new NoticeInfo();
//			info.setReceiverName(dto.getHeadName());
//			info.setMailReceiver(dto.getEmail());
//			
//			info.setMailSubject(getValue(infoParam.getMailSubject(),maps));
//			info.setMailTemplate(infoParam.getMailTemplate());			
//			info.getMailTemplateParams().putAll(maps);
//			if(infoParam.getMailTemplateParams() != null && !infoParam.getMailTemplateParams().isEmpty()){
//				info.getMailTemplateParams().putAll(infoParam.getMailTemplateParams());
//			}
//			
//			info.setSmsReceiver(dto.getTelPhone());
//			info.setSmsSignName(infoParam.getSmsSignName());
//			info.setSmsTemplate(infoParam.getSmsTemplate());
//			info.getSmsTemplateParams().putAll(maps);
//			if(infoParam.getSmsTemplateParams() != null && !infoParam.getSmsTemplateParams().isEmpty()){
//				info.getSmsTemplateParams().putAll(infoParam.getSmsTemplateParams());
//			}
//			info.loseTemplateParams();
//			infos.add(info);
//		}
//		
//	}
//	
//	/* (non-Javadoc)
//	 * @see net.chinahrd.eis.notice.service.NoticeService2#sendNotices()
//	 */
//	@Override
//	public void sendNotices() {
//		String[] noticeType = config.getNoticeType();
//		for (NoticeInfo info : infos) {
//			for (int i = 0; i < noticeType.length; i++) {
//				if (noticeType[i].equals("1")) {
//					sendMail(info);
//				} else if (noticeType[i].equals("2")) {
//					sendSMS(info);
//				}
//			}
//		}
//	}
//
//	/* (non-Javadoc)
//	 * @see net.chinahrd.eis.notice.service.NoticeService2#getNoticeCond()
//	 */
//	@Override
//	public String getNoticeCond() {
//		return StringUtils.join(config.getNoticeCond(),",");
//	}
//	
//	/* (non-Javadoc)
//	 * @see net.chinahrd.eis.notice.service.NoticeService2#getNoticeRole()
//	 */
//	@Override
//	public String[] getNoticeRole() {
//		return config.getNoticeRole();
//	}
//
//	private void sendMail(NoticeInfo info) {
//		MailContent content = MailContent.of(info.getMailReceiver(), info.getMailSubject(), info.getMailTemplate());
//		Map<String, String> params = info.getMailTemplateParams();
//		Iterator<String> keys = params.keySet().iterator();		
//		while (keys.hasNext()) {
//			String key = keys.next();
//			content.addParam(key, params.get(key));
//		}
////		mailService.sendMail(content);
//	}
//
//	private void sendSMS(NoticeInfo info) {
//		smsService.sendSMS(info.getSmsReceiver().split(","), info.getSmsSignName(), info.getSmsTemplate(), info.getSmsTemplateParams());
//	}
//	
//	private static String getValue(String template, Map<String, String> paramMap) {
//		//用参数替换模板中的${}变量
//		Matcher m = Pattern.compile("\\$\\{\\w+\\}").matcher(template);
//
//		StringBuffer sb = new StringBuffer();
//
//		while (m.find()) {
//			String param = m.group(); // ${xx}
//			Object value = paramMap.get(param.substring(2, param.length() - 1));
//			m.appendReplacement(sb, value == null ? "" : value.toString());
//		}
//
//		m.appendTail(sb);
//		return sb.toString();
//	}
}

package net.chinahrd.eis.notice.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.eis.notice.NoticeInfo;
import net.chinahrd.entity.dto.pc.common.ConfigDto;

/**
 * 通用的通知服务<br>
 * 描述：根据config表配置的信息进行通知(email or sms or ...)
 * 
 * @author hhzhou
 * 
 */
public interface NoticeService {

	/**
	 * step1:根据配置参数初始化通知服务
	 * 
	 * @param cfgs
	 * @param prefix
	 * @param regex
	 */
	void init(List<ConfigDto> cfgs, final String prefix, Map<String, String> regexMap);

	/**
	 * step2:解析生成NoticeInfo
	 * 
	 * @param customerId
	 * @param empIdList
	 * @param role
	 * @param infoParam
	 */
	void resolve(String customerId, List<String> empIdList, String role,
			NoticeInfo infoParam);

	/**
	 * step3:发送通知
	 */
	void sendNotices();

	/**
	 * 获取配置的通知条件
	 * 
	 * @return
	 */
	String getNoticeCond();

	/**
	 * 获取配置的通知角色
	 * 
	 * @return 1:上级 2:机构负责人 3:BP
	 */
	String[] getNoticeRole();

	public final static String WARNING_NOTICE_COND = "forNotify";
	public final static String WARNING_NOTICE_TYPE = "forTerminal";
	public final static String WARNING_NOTICE_ROLE = "forPerson";
	public final static String SYS_NAME = "sysName";
	public final static String PRODUCT_NAME = "product";
	public final static String TEMPLATEPARAM_HEAD_NAME = "headName";
	public final static String TEMPLATEPARAM_ORGAN_NAMES = "organNames";
	public final static String TEMPLATEPARAM_EMP_NAMES = "empNames";
	public final static String TEMPLATEPARAM_DATESTR = "dateStr";

}

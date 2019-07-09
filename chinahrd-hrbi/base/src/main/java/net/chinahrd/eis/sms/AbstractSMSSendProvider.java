package net.chinahrd.eis.sms;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.chinahrd.eis.sms.dao.SMSSendStatusDao;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractSMSSendProvider implements ISMSSendProvider {

	// 短信网关提供商名称
	protected String providerName;
	// 发送短信URL
	protected String sendUrl;
	// 查询发送状态URL
	protected String dataUrl;
	// 接口帐号
	protected String uid;
	// 接口密钥
	protected String authKey;
	// 缺省编码
	protected String defaultEncoding;

	protected Timer timer;
	protected long pollingInterval = 600*1000;//600秒

	@Autowired
	private SMSSendStatusDao smsSendStatusDao;

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public String getSendUrl() {
		return sendUrl;
	}

	public String getDataUrl() {
		return dataUrl;
	}

	public String getUid() {
		return uid;
	}

	public String getAuthKey() {
		return authKey;
	}

	public String getDefaultEncoding() {
		return defaultEncoding;
	}

	public String getProviderName() {
		return providerName;
	}

	@Override
	final public List<SMSSendStatus> getSendStatus(String mobile) {
		return smsSendStatusDao.querySMSSendStatus(mobile);
	}

	abstract protected List<SMSSendStatus> getSendStatus();

	@Override
	final public void initMonitor() {
		timer = new Timer(true);
		timer.schedule(new MonitorNotifier(), 0, pollingInterval);
	}

	@Override
	final public void stopMonitor() {
		timer.cancel();
	}

	private class MonitorNotifier extends TimerTask {

		@Override
		public void run() {
			
			try {
				List<SMSSendStatus> status = getSendStatus();
				if (status == null || status.isEmpty()) {
					return;
				}
				smsSendStatusDao.addSMSSendStatus(status);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	}
}

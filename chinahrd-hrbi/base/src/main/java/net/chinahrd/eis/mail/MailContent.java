package net.chinahrd.eis.mail;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import net.chinahrd.utils.CollectionKit;

/**
 * 邮件相关内容
 * 
 * @author bright
 *
 */
public class MailContent implements Serializable {
	private static final long serialVersionUID = 2674936676121856858L;

	// 收件人
	private String to;
	// 抄送给
	private String cc;
	// 邮件主题
	private String subject;
	// 邮件模板
	private String templateName;
	// 模板参数值
	private Map<String, Object> params = CollectionKit.newMap();
	
	// 邮件附件
	private Map<String, File> attachments = CollectionKit.newMap();
	private boolean hasAttachment = false;

	// 邮件内嵌资源
	private Map<String, File> inlines = CollectionKit.newMap();
	private boolean hasInline = false;

	private MailContent(String to, String subject, String templateName) {
		this.to = to;
		this.subject = subject;
		this.templateName = templateName;
	}

	/**
	 * 
	 * @param to
	 *            收件人
	 * @param subject
	 *            主题
	 * @param templateName
	 *            模板名称
	 * @return
	 */
	public static MailContent of(String to, String subject, String templateName) {
		return new MailContent(to, subject, templateName);
	}

	public String getTo() {
		return this.to;
	}

	public String getCc() {
		return cc;
	}

	public MailContent cc(String cc) {
		this.cc = cc;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public String getTemplateName() {
		return templateName;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public MailContent addParam(String key, Object value) {
		this.params.put(key, value);
		return this;
	}

	public Map<String, File> getAttachments() {
		return attachments;
	}

	public MailContent addAttachment(String attachmentName, File attachmentFile) {
		this.attachments.put(attachmentName, attachmentFile);
		this.hasAttachment = true;
		return this;
	}

	public boolean hasAttachment() {
		return this.hasAttachment;
	}

	public Map<String, File> getInlines() {
		return inlines;
	}

	public MailContent addInline(String inlineName, File inlineFile) {
		this.inlines.put(inlineName, inlineFile);
		this.hasInline = true;
		return this;
	}

	public boolean hasInline() {
		return this.hasInline;
	}
}

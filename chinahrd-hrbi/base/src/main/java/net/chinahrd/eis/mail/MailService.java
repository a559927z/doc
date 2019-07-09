package net.chinahrd.eis.mail;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * MIME邮件服务类.
 * 
 * 由Freemarker引擎生成的的html格式邮件, 并带有附件.
 * 
 */
public class MailService {

	private static final String DEFAULT_ENCODING = "utf-8";

	private static Logger logger = LoggerFactory.getLogger(MailService.class);

	private JavaMailSenderImpl  mailSender;
	private Configuration freemarkerConfiguration;
	private String from;
	/**
	 * 发送MIME格式的邮件.
	 */
	public boolean sendMail(final MailContent mailContent) {
		try {
			
			MimeMessage msg = mailSender.createMimeMessage();
			boolean multipart = false;
			if (mailContent.hasAttachment() || mailContent.hasInline()) {
				multipart = true;
			}
			MimeMessageHelper helper = new MimeMessageHelper(msg, multipart, DEFAULT_ENCODING);

			helper.setTo(mailContent.getTo());
			helper.setFrom(from);
			helper.setSubject(mailContent.getSubject());
			if (mailContent.getCc() != null) {
				helper.setCc(mailContent.getCc());
			}
			
			helper.setText(generateContent(mailContent), true);
			//有附件
			if (mailContent.hasAttachment()) {
				for (Entry<String, File> entry : mailContent.getAttachments().entrySet()) {
					helper.addAttachment(entry.getKey(), entry.getValue());
				}
			}
			//有内嵌资源
			if (mailContent.hasInline()) {
				for (Entry<String, File> entry : mailContent.getInlines().entrySet()) {
					helper.addInline(entry.getKey(), entry.getValue());
				}
			}

			mailSender.send(msg);
			logger.info("邮件已发送至" + mailContent.getTo());
			return true;
		} catch (MessagingException e) {
			logger.error("构造邮件失败", e);
			return false;
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
			return false;
		}
	}
	
	public void init(){
		mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.163.com");
		mailSender.setPort(25);
		mailSender.setUsername("xxx@163.com");
		mailSender.setPassword("xxx");
		this.from="xxx@163.com";
	}
	/**
	 * 使用Freemarker生成html格式内容.
	 */
	private String generateContent(final MailContent mailContent)
			throws MessagingException {
		
		try {
			Template template = freemarkerConfiguration.getTemplate(
					mailContent.getTemplateName(), DEFAULT_ENCODING);
			return FreeMarkerTemplateUtils.processTemplateIntoString(template,
					mailContent.getParams());
		} catch (IOException e) {
			logger.error("生成邮件内容失败, FreeMarker模板不存在", e);
			throw new MessagingException("FreeMarker模板不存在", e);
		} catch (TemplateException e) {
			logger.error("生成邮件内容失败, FreeMarker处理失败", e);
			throw new MessagingException("FreeMarker处理失败", e);
		}
	}

	/**
	 * Spring的MailSender.
	 */
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 注入Freemarker引擎配置,构造Freemarker 邮件内容模板.
	 */
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
	}

	/**
	 * Spring的from
	 */
	public void setFrom(String from) {
		this.from = from;
	}
}

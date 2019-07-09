package net.chinahrd.eis.notice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class NoticeConfig {
	private String[] noticeType;
	private String[] noticeRole;
	private String[] noticeCond;

	public String[] getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType, String regex) {
		this.noticeType = validate(noticeType, regex);
	}

	public String[] getNoticeRole() {
		return noticeRole;
	}

	public void setNoticeRole(String noticeRole, String regex) {
		this.noticeRole = validate(noticeRole, regex);
	}

	public String[] getNoticeCond() {
		return noticeCond;
	}

	public void setNoticeCond(String noticeCond, String regex) {
		this.noticeCond = validate(noticeCond, regex);
	}

	/**
	 * 去掉指定范围之外的字符
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static String[] validate(String str, String regex) {

		if (StringUtils.isBlank(str)) {
			return new String[0];
		}

		str = str.replaceAll(" ", "");
		String[] strs = StringUtils.split(str, ",");

		if (StringUtils.isBlank(regex)) {
			return strs;
		}

		regex = regex.replaceAll(" ", "");
		String[] regexs = StringUtils.split(regex, ",");

		List<String> tmpList = new ArrayList<>();
		for (String c : strs) {
			for (String r : regexs) {
				if (c.equalsIgnoreCase(r)) {
					tmpList.add(c);
					break;
				}
			}
		}

		return tmpList.toArray(new String[0]);
	}

	public static void main(String[] args) {
		validate("0,1,2", "2,1");
	}
}

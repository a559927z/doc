package net.chinahrd.eis.search;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import net.chinahrd.eis.search.aggs.AggsType;

/**
 * @Description 工具类
 * @author bright
 *
 */
public class EsUtil {
	public static String formatDate(Object object) {
		if (object instanceof java.util.Date) {
			return formatDateFromDate((java.util.Date) object);
		}
		return formatDateFromString(object.toString());
	}

	public static boolean isDate(Object object) {
		return object instanceof java.util.Date
				|| Pattern.matches("[1-2][0-9][0-9][0-9]-[0-9][0-9].*", object.toString());
	}

	public static String formatDateFromDate(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			String result = dateFormat.format(date);
			return result;
		} catch (Exception e) {
		}
		return dateFormat.format(new Date());
	}

	public static String formatDateFromDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String result = dateFormat.format(date);
			return result;
		} catch (Exception e) {
		}
		return dateFormat.format(new Date());
	}

	public static String formatDateFromString(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date value = dateFormat.parse(date);
			return formatDateFromDate(value);
		} catch (Exception e) {
		}
		return dateFormat.format(new Date());
	}

	public static boolean isMetricsType(AggsType type) {
		if (type == AggsType.value_count || type == AggsType.max || type == AggsType.min || type == AggsType.sum
				|| type == AggsType.avg || type == AggsType.stats || type == AggsType.extended_stats) {
			return true;
		}
		return false;
	}
}

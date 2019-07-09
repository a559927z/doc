package net.chinahrd.entity.enums;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.chinahrd.entity.dto.pc.common.ItemDto;
import net.chinahrd.utils.CollectionKit;

/**
 * 所有状态提示</br>
 * 可支持跨指标、跨客户</br>
 * MVC层公共使用</br>
 * 
 * （注：禁用格式化代码）
 * @author jxzhang on 2017年2月14日
 * @Verdion 1.1 版本 on 2017年2月15日
 */
public enum TipsEnum {
	// 是否完成
	NOT_BEGIN	(1, "未开始", "未开始", 1),
	ON_THE_WAY	(2, "进行中", "进行中", 1),
	FINISH		(3, "已完成", "已完成", 1),
	// 数据抽取
	DATA_SUCCESS(1, "成功", "数据抽取成功", 2), 
	DATA_FAIL	(2, "失败", "数据抽取失败", 2), 
	DATA_ERROR	(3, "错误", "数据抽取错误", 2),
	DATA_EXIST	(4, "存在", "源数据已存在", 2),
	// 定时器
	JOB_BEGIN	(1, "开始", "执行定时任务开始", 3), 
	JOB_END		(2, "结束", "执行定时任务结束", 3),
	// 是否对错
	IS_ERROR	(1, "1", "是错误", 	4),
	NOT_IS_ERROR(0, "0", "不是错误", 4),
	// 周期
    ONE_YEAR	(1, "一年", "一年", 5),
    HALF_YEAR	(2, "半年", "半年", 5),
    ONE_QUARTER	(3, "季度", "季度", 5),
    // 考勤类型
    ATTENDAN_POST		 	(1, "在岗"			 ,"在岗"		         ,6),
    ATTENDAN_LEAVE_ANNUAL	(2, "请假（年假）"		 ,"请假（年假）"	     ,6),
    ATTENDAN_LEAVE_ASK	 	(3, "请假（调假）"		 ,"请假（调假）"	     ,6),
    ATTENDAN_LEAVE_MARRY 	(4, "请假（婚假）"		 ,"请假（婚假）"	     ,6),
    ATTENDAN_LEAVE_SICK	 	(5, "请假（病假）"		 ,"请假（病假）"	     ,6),
    ATTENDAN_LEAVE_ABSENCE	(6, "请假（事假）"		 ,"请假（事假）"	     ,6),
    ATTENDAN_AFFAIRS_DAYOFF	(7, "公假（法定休息日）"	 ,"公假（法定休息日）"	 ,6),
    ATTENDAN_AFFAIRS_HOLIDAY(8, "公假（法定假期日）"	 ,"公假（法定假期日）"	 ,6),
    ATTENDAN_AFFAIRS_WELFARE(9, "公假（公司提供假期）" ,"公假（公司提供假期）"  ,6),
    // 关键人才
    KEYTALENT_KEY	(1,	"关键人才"		,"关键人才",7), 
    KEYTALENT_OTHER (0,	"其它"		,"其它"	 ,7),
    // 预警终端
    WARNING_NOT_TERMIN(0, "无任何终端"	,"无任何终端"	,8), 
    WARNING_EMAILL	  (1, "邮件"		,"邮件"		,8),     
    WARNING_SMS		  (2, "短信"		,"短信"		,8),
    // 预警颜色
    WARNING_RED		  (1, "红色"		,"红色"	,9),
    WARNING_YELLOW	  (2, "黄色"		,"黄色"	,9),
    WARNING_RED_YELLOW(3, "红黄色"	,"红黄色"	,9),
	// 预警对象
	WARNING_NOT_OBJ	 (0, "无任何对象"	,"无任何对象" ,10),
	WARNING_SUPERIOR (1, "上级"		,"上级"		,10),
	WARNING_PRINCIPAL(2, "架构负责人"	,"架构负责人"	,10),
	WARNING_HR_BP	 (3, "BP"		,"BP"		,10),
	// 流失类型
	DISMISSED_ACTIVE  (1, "主动流失"	,"主动流失"	,11), 
	DISMISSED_UNACTIVE(2, "被动流失"	,"被动流失"	,11), 
	DISMISSED_OTHER	  (3, "其它"		,"其它"		,11),
	// 合同类型
	CONTRACT_PASSTIME(1, "p","兼职员工",12), // 劳动合同（全）
	CONTRACT_FULLTIME(2, "f","正式员工",12), // 劳动合同（非全）
	CONTRACT_SOURCED (3, "s","外包员工",12), // 劳务派遣
	// 客户画像维度
    CLIENTIMG_BASEINFO(0, "BASEINFO","基础信息"	,13),
    CLIENTIMG_CONTACTS(1, "CONTACTS","联系人信息"	,13),
    CLIENTIMG_SUMMARY (2, "SUMMARY"	,"纪要信息"	,13),
    CLIENTIMG_ORDER	  (3, "ORDER"	,"订单信息"	,13),
    CLIENTIMG_CREDIT  (4, "Credit"	,"信用信息"	,13);
	;
	
	private final Integer code; 	// 编码 -->对接数据库字段
	private final String info; 		// 信息
	private final String desc; 		// 描述
	private final Integer group; 	// 组

	private TipsEnum(Integer code,  String info, String desc, Integer group) {
		this.code = code;
		this.info = info;
		this.desc = desc;
		this.group = group;
	}
	
	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public String getInfo() {
		return info;
	}

	public Integer getGroup() {
		return group;
	}

	/**
	 * 获取描述
	 * 
	 * @param code
	 *            编码
	 * @param group
	 *            组
	 * @return desc
	 */
	public static String getDescByCode(Integer code, Integer group) {
		return searchTipsEnum(code, group, 1);
	}
	
	/**
	 * 获取信息
	 * 
	 * @param code
	 *            编码
	 * @param group
	 *            组
	 * @return info
	 */
	public static String getInfoByCode(Integer code, Integer group) {
		return searchTipsEnum(code, group, 2);
	}

	/**
	 * 遍历TipsEnum类，通过指定类型返回信息或描述
	 * 
	 * @param code
	 *            搜索条件
	 * @param group
	 *            组
	 * @return 信息或描述
	 */
	private static String searchTipsEnum(Integer code, Integer group, Integer type) {
		if (null == code) {
			return "-";
		}
		TipsEnum[] values = TipsEnum.values();
		for (TipsEnum tipsEnum : values) {
			if (tipsEnum.group == group) {
				if (code != tipsEnum.getCode()) {
					continue;
				}
				return type == 1 ? tipsEnum.getDesc() : tipsEnum.getInfo();
			}
		}
		return "-";
	}
	
	/**
	 * 获取描述
	 * 
	 * @param info
	 *            编码
	 * @param group
	 *            组
	 * @return desc
	 */
	public static String getDescByInfo(String info, Integer group) {
		if (null == info) {
			return "-";
		}
		TipsEnum[] values = TipsEnum.values();
		for (TipsEnum tipsEnum : values) {
			if (tipsEnum.group == group) {
				if (StringUtils.equals(info, tipsEnum.getInfo())) {
					return tipsEnum.getDesc();
				}
			}
		}
		return "-";
	}
	
	
	/**
	 * 获取一个以code和Info集合对像
	 * 
	 * @param group
	 *            组
	 * @return ItemDto集合对像</br>
	 *         当组参数为null时，返回一个空集合对像。防Null point exception
	 */
	public static List<ItemDto> getCollection(Integer group) {
		List<ItemDto> rs = CollectionKit.newList();
		if (null == group) {
			return rs;
		}
		TipsEnum[] values = TipsEnum.values();
		for (TipsEnum tipsEnum : values) {
			if (tipsEnum.group == group) {
				rs.add(new ItemDto(tipsEnum.getCode() + "", tipsEnum.getInfo()));
			}
		}
		return rs;
	}
	
	/**
	 * 获取指定一个枚举类别
	 * 
	 * @param info
	 *            信息
	 * @param group
	 *            组
	 * @return 指定组中的以信息一致枚举类别
	 */
	public static TipsEnum asEnumByInfo(String info, Integer group) {
		if (null == info) {
			return null;
		}
		TipsEnum[] values = TipsEnum.values();
		for (TipsEnum tipsEnum : values) {
			if (tipsEnum.group == group) {
				if (StringUtils.equalsIgnoreCase(info, tipsEnum.getInfo())) {
					return tipsEnum;
				}
			}
		}
		return null;
	}
}

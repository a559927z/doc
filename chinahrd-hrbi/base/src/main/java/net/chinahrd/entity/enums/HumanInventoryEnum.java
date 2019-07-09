package net.chinahrd.entity.enums;

/**
 * 定义项目人力盘点数据导入公共信息
 * */
public enum HumanInventoryEnum {

	SUCCESS("success"),
	ERROR("error"),
	FILEERROR("fileError"),
	FILETYPEERROR("fileTypeError"),
	COSTTYPEERROR("costTypeError"),
	PERSONTYPEERROR("personTypeError"),
	OUTOFMAXLENGTH("outOfMaxLength"),
	FILEISNULLERROR("fileIsNullError"),
	FILEISREPEAT("fileIsRepeat"),
	//定义excel导入文件内容开始行
	FILECONTENTSTARTNUM("3"),
	//错误标识
	INVENTORYTIMEERROR("A1"),
	INVENTORYTIMETYPEERROR("A2"),
	PRONAMEISNULLERROR("A3"),
	PRONAMEISSAMEERROR("A4"),
	PRINCIPALISNULLERROR("A5"),
	PRINCIPALISNOERROR("A6"),
	LEADORGANISNULLERROR("A7"),
	LEADORGANISNOERROR("A8"),
	PARTAKEORGANISNULLERROR("A9"),
	PARTAKEORGANISNOERROR("A10"),
	TYPEISNULLERROR("A11"),
	PROGRESSERROR("A12"),
	DATETYPEERROR("A13"),
	NUMTYPEERROR("A14"),
	//操作标识
	UPDATEOPERATE("A15"),
	DELOPERATE("A16"),
	STARTDATETYPEERROR("A17"),
	ENDDATETYPEERROR("A18"),
	PRONAMEISMOREERROR("A19"),
	PRINCIPALISMOREERROR("A20"),
	LEADORGANISMOREERROR("A21"),
	TYPEISMOREERROR("A22"),
	PROGRESSISMOREERROR("A23"),
	NUMISNULLERROR("A24"),
	//错误标识名称
	A1("盘点时间与界面所选时间不一致"),
	A2("盘点时间格式不正确"),
	A3("项目名称为空"),
	A4("项目名称不唯一"),
	A5("项目负责人为空"),
	A6("项目负责人帐号在系统中不存在"),
	A7("主导组织为空"),
	A8("主导组织在系统中不存在"),
	A9("参与组织为空"),
	A10("参与组织在系统中不存在"),
	A11("项目类型为空"),
	A12("项目进度填写不正确"),
	A13("项目开始时间为空"),
	A14("不是数字形式"),
	//操作标识名称
	A15("更新项目及费用信息（以导入文档信息为准）"),
	A16("删除该项目费用及人员信息（导入文档不存在该项目）"),
	A17("项目开始时间格式不正确"),
	A18("项目结束时间格式不正确"),
	A19("项目名称只能为一个"),
	A20("项目负责人账号只能为一个"),
	A21("主导组织只能为一个"),
	A22("项目类型只能为一个"),
	A23("项目进度只能为一个"),
	A24("没有填写"),
	B1("更新项目人员信息（以导入文档信息为准）"),
	B2("新增项目人员信息（以导入文档信息为准）"),
	B3("删除项目人员信息（以导入文档信息为准）"),
	B4("项目名称在系统中不存在"),
	B5("参与员工账户的格式不正确"),
	B6("参与员工账户在系统中不存在"),
	B7("所服务子项目名称的格式不正确"),
	B8("参与员工为空"),
	B9("人力投入为空"),
	B10("人力投入只能是数字"),
	B11("所服务子项目在系统中不存在"),
	B12("项目和所服务子项目名称不能相同"),
	//文件
	FILETYPEERRORINFO("文件类型错误，请重新选择文件."),
	OUTOFMAXLENGTHINFO("您上传的文件太大，请分开上传文件."),
	FILEISNULLERRORINFO("导入文件为空，请选择文件."),
	FILECONTENTISNULL("导入文件内容为空，请重新选择."),
	
	INVENTORYTIME("盘点时间"),
	//费用文件标题
	PROJECTTITLE("项目信息及费用数据"),
	PROJECTNAME("项目名称\r\n（项目名称必须唯一。若有子项目，则用“-”符号隔开）"),
	PROJECTPRINCIPAL("项目负责人\r\n（员工账号）"),
	PROJECTLEADORGAN("主导组织\r\n（写明组织全路径并用\"-\"符合隔开）"),
	PROJECTPARTAKEORGAN("参与组织"),
	PROJECTTYPE("项目类型"),
	PROJECTPROGRESS("项目进度"),
	PROJECTSTARTDATE("项目开始时间"),
	PROJECTENDDATE("项目结束时间"),
	PROJECTINPUT("当月投入(万元)"),
	PROJECTOUTPUT("当月产出(万元)"),
	//人力文件标题
	PARTAKEEMP("参与员工"),
	INPUTEMP("人力投入"),
	WORKCONTENT("工作内容"),
	CONTENTISREPEAT("这个文档已经导入，请检查不要重复导入");
	private final String value;

	HumanInventoryEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}

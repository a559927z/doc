package net.chinahrd.annotation.configure;



/**
 * 注解类对应的 class
 * @author htpeng
 *2017年4月14日下午4:08:19
 */
public enum AnnotationType {
	TABLE("net.chinahrd.annotation.Table"),
	COLUMN("net.chinahrd.annotation.Column"),
	ID("net.chinahrd.annotation.Id");
	
	private String value="";
	AnnotationType(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	public static AnnotationType getType(String value){
		int index=value.indexOf("(");
		if(index>0)
		value = value.substring(1, index);
		for(AnnotationType c:AnnotationType.values()){
			if(c.value.equals(value)){
				return c;
			}
		}
		return null;
	}

}

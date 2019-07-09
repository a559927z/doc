/**
*net.chinahrd.core.module.config
*/
package net.chinahrd.core.module.config;

/**
 * @author htpeng
 *2016年10月13日下午3:10:39
 */
public enum FileType {
	XML("xml"),
	PROPS("properties");
	private String type;

	private FileType(String type) {
		this.type = type;
	}
	public String getValue(){
		return this.type;
	}
	public static FileType getFileType(String type){
		if(null==type)return null;
		for(FileType ft:FileType.values()){
			if(type.endsWith(ft.getValue())){
				return ft;
			}
		}
		return null;
	}
}

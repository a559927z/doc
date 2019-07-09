package net.chinahrd.core.cache.model;

import java.util.ArrayList;
import java.util.List;

//import net.chinahrd.core.ConfigEnum;
import net.chinahrd.core.xml.XmlModel;

/**
 * 解析缓存Dao 各个方法的信息
 * @author htpeng
 *2016年2月2日下午2:31:06
 */
public class MethodConfig {
	private String name;
	private Class<?> resultType;
	private Class<?> xmlResultType;
	private List<String> parmList=new ArrayList<String>();
	
	public MethodConfig(){
			
	}
	public MethodConfig(
			XmlModel xmlModel){
//		try {
//			xmlResultType=Class.forName(xmlModel.getAttribute(ConfigEnum.CACHE_NODE_RESULT_NAME));
//		} catch (ClassNotFoundException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
	}
	public Class<?> getResultType() {
		return resultType;
	}
	public void setResultType(Class<?> resultType) {
		this.resultType = resultType;
	}
	public List<String> getParmList() {
		return parmList;
	}
	public void setParmList(List<String> parmList) {
		this.parmList = parmList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class<?> getXmlResultType() {
		return xmlResultType;
	}
	public void setXmlResultType(Class<?> xmlResultType) {
		this.xmlResultType = xmlResultType;
	}
	
	
	
}

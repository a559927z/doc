package net.chinahrd.core.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.chinahrd.core.ConfigEnum;
import net.chinahrd.core.xml.XmlModel;

import org.dom4j.Attribute;


/**
 * 加载 xml 简单实现模板
 * @author htpeng
 *2016年2月2日下午2:32:07
 */
public class SimpleXmlModel implements XmlModel {
	private String text;
	private String nodeName;
	private List<XmlModel> xmlModelList=new ArrayList<XmlModel>();
	private Map<String,String> attributeMap=new HashMap<String, String>();
	
	public List<XmlModel> getNodes() {
		return xmlModelList;
	}

	public XmlModel addNode(XmlModel node){
		xmlModelList.add(node);
		return node;
	}
	
	public void addAttribute(String key,String value){
		attributeMap.put(key,value);
	}
	public void addAttribute(Attribute att){
		attributeMap.put(att.getName(),att.getValue());
	}
	
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getAttribute(String key) {
		// TODO Auto-generated method stub
		return attributeMap.get(key);
	}
	/* (non-Javadoc)
	 * @see net.chinahrd.core.xml.XmlModel#getAttribute(net.chinahrd.core.xml.XmlConfig)
	 */
	@Override
	public String getAttribute(ConfigEnum key) {
		// TODO Auto-generated method stub
		return getAttribute(key.toString());
	}
	
	@Override
	public XmlModel getNodeById(String id) {
		for(XmlModel x:xmlModelList){
			if(x.getAttribute(ConfigEnum.ID).equals(id)){
				return x;
			}
		}
		return null;
	}


	
	
}


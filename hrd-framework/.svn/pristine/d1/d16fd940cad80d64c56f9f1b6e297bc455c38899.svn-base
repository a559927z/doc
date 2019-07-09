package net.chinahrd.core.xml;

import java.util.List;

import net.chinahrd.core.ReadModel;

import org.dom4j.Attribute;

/**
 * xml加载模板
 * @author htpeng
 *2016年2月2日下午2:32:23
 */
public interface XmlModel extends ReadModel{
	
	List<XmlModel> getNodes();

	XmlModel addNode(XmlModel node);

	void addAttribute(String key, String value);

	void addAttribute(Attribute att);

	String getNodeName();

	void setNodeName(String nodeName);

	public String getText();

	public void setText(String text);
	
	public XmlModel getNodeById(String id);
}
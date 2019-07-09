package net.chinahrd.core.xml;

import java.io.File;
import java.io.InputStream;



import net.chinahrd.core.Read;

import org.dom4j.Element;

/**
 * xml加载接口
 * @author htpeng
 *2016年2月2日下午2:30:26
 */
public interface XmlRead extends Read{
//	/*
//	 * 加载xml配置文件
//	 */
//	public void load(String filename);
//	public void load(InputStream ins);
//	public void load(File file);
	/*
	 * 解析xml配置文件
	 */
	public void parseXml(XmlModel parentNode,Element el);
	
	/*
	 * 获取解析完的节点
	 */
	public XmlModel getXmlModel();
	public boolean valid();
}

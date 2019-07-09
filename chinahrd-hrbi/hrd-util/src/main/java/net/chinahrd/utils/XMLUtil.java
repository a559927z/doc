package net.chinahrd.utils;

import java.io.File;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Title: XMLUtil <br/>
 * Description:
 * 
 * @author jxzhang
 * @DATE 2017年11月28日 下午8:44:21
 * @Verdion 1.0 版本
 */
public class XMLUtil {
	/**
	 * 
	 * 获取叶子节点值
	 * 
	 * @param fileFullPath
	 * @param tagName
	 * @return
	 */
	public static String getValueByTagName(String fileFullPath, String tagName) {
		try {
			// 创建文档对象
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dFactory.newDocumentBuilder();
			Document doc;
			doc = builder.parse(new File(fileFullPath));

			NodeList nl = doc.getElementsByTagName(tagName);
			Node classNode = nl.item(0).getFirstChild();
			String chartType = classNode.getNodeValue().trim();
			return chartType;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 该方法用于从XML配置文件中提取具体类类名，并返回一个实例对象
	public static Object getBeanByTagName(String fileFullPath, String tagName) {
		try {
			// 创建DOM文档对象
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dFactory.newDocumentBuilder();
			Document doc;
			doc = builder.parse(new File(fileFullPath));

			// 获取包含类名的文本节点
			NodeList nl = doc.getElementsByTagName(tagName);
			Node classNode = nl.item(0).getFirstChild();
			String cName = classNode.getNodeValue();

			// 通过类名生成实例对象并将其返回
			Class<?> c = Class.forName(cName);
			Object obj = c.newInstance();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

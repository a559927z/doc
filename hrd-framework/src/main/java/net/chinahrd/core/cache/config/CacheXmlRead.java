package net.chinahrd.core.cache.config;

import java.util.Iterator;

import net.chinahrd.core.xml.SimpleXmlModel;
import net.chinahrd.core.xml.SimpleXmlRead;
import net.chinahrd.core.ConfigEnum;
import net.chinahrd.core.xml.XmlModel;

import org.dom4j.Attribute;
import org.dom4j.Element;


/**
 * 缓存xml加载器
 * @author htpeng
 *2016年2月2日下午2:29:27
 */
public class CacheXmlRead extends SimpleXmlRead {
	@SuppressWarnings("unchecked")
	@Override
	public void parseXml(XmlModel parentNode, Element el) {
		for (Iterator<Element> i = el.elementIterator(); i.hasNext();) {
			XmlModel xmlEntity = parentNode.addNode(new SimpleXmlModel());
			Element newEl = i.next();
			String name=newEl.getName();
			if(ConfigEnum.CACHE_NODE_SELECT_NAME.toString().equals(name)){
				xmlEntity.setNodeName(name);
				xmlEntity.setText(newEl.getTextTrim());
//				String text = newEl.getTextTrim();
//				String elName = newEl.getName();
				for (Iterator<Attribute> n = newEl.attributes().iterator(); n.hasNext();) {
					xmlEntity.addAttribute( n.next());
				}
			}
			parseXml(xmlEntity, newEl);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean checkAndParseRoot(Element rootEl) {
		if (ConfigEnum.CACHE_ROOT_NODE_NAME.toString().equals(rootEl.getName())) {
			rootNodel=new SimpleXmlModel();
			rootNodel.setNodeName(rootEl.getName());
			rootNodel.setText(rootEl.getTextTrim());
			for (Iterator<Attribute> n = rootEl.attributes().iterator(); n.hasNext();) {
				rootNodel.addAttribute( n.next());
			}
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.xml.XmlRead#valid()
	 */
	@Override
	public boolean valid() {
		// TODO Auto-generated method stub
		return false;
	}

}

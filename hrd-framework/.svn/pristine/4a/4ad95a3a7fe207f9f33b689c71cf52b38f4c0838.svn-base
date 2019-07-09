package net.chinahrd.core.menu.config;

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
public class MenuXmlRead extends SimpleXmlRead {
	@SuppressWarnings("unchecked")
	@Override
	public void parseXml(XmlModel parentNode, Element el) {
		for (Iterator<Element> i = el.elementIterator(); i.hasNext();) {
			Element newEl =  i.next();
			String name=newEl.getName();
			
			if(name.equals(ConfigEnum.MENU.toString())||
					name.equals(ConfigEnum.FIELDS.toString())||
					name.equals(ConfigEnum.FIELD.toString())||
					name.equals(ConfigEnum.LAYOUTS.toString())||
					name.equals(ConfigEnum.LAYOUT.toString())||
					name.equals(ConfigEnum.PLUG_IN.toString())
					){
				XmlModel xmlEntity = parentNode.addNode(new SimpleXmlModel());
				xmlEntity.setNodeName(name);
				xmlEntity.setText(newEl.getTextTrim());
				for (Iterator<Attribute> n = newEl.attributes().iterator(); n.hasNext();) {
					xmlEntity.addAttribute(n.next());
				}
				parseXml(xmlEntity, newEl);
			}
			
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean checkAndParseRoot(Element rootEl) {
		if (rootEl.getName().equals(ConfigEnum.MENUS.getValue())) {
			rootNodel=new SimpleXmlModel();
			rootNodel.setNodeName(rootEl.getName());
			rootNodel.setText(rootEl.getTextTrim());
			for (Iterator<Attribute> n = rootEl.attributes().iterator(); n.hasNext();) {
				rootNodel.addAttribute(n.next());
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
		return false;
	}

}

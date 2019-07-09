/**
*net.chinahrd.core.file
*/
package net.chinahrd.core.props.model;



import java.util.Properties;

import net.chinahrd.core.ReadModel;
import net.chinahrd.core.ConfigEnum;

/**
 * @author htpeng
 *2016年10月13日下午2:07:53
 */
public class PropsModel implements ReadModel{
	private Properties properties;
	
	
	
	public PropsModel(Properties properties) {
		super();
		this.properties = properties;
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.ReadModel#getAttribute(java.lang.String)
	 */
	@Override
	public String getAttribute(String key) {
		// TODO Auto-generated method stub
		return properties.getProperty(key);
	}

	/* (non-Javadoc)
	 * @see net.chinahrd.core.ReadModel#getAttribute(net.chinahrd.core.xml.XmlConfig)
	 */
	@Override
	public String getAttribute(ConfigEnum key) {
		// TODO Auto-generated method stub
		return getAttribute(key.getValue());
	}

}

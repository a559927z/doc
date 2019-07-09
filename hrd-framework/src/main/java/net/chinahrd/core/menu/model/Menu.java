/**
*net.chinahrd.core.menu.model
*/
package net.chinahrd.core.menu.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.chinahrd.core.ConfigEnum;
import net.chinahrd.core.xml.XmlModel;

/**
 * @author htpeng
 *2016年10月13日上午10:06:44
 */
public class Menu{
	private static final Logger log = Logger.getLogger(Menu.class);
	private String code;
	private String name;
	private String url;
	private String icon;



	private boolean mobile=false;
	private List<String> relies=new ArrayList<String>();
	private List<Field> fields=new ArrayList<Field>();
	private List<Layout> layouts=new ArrayList<Layout>();
	/**
	 * @param x
	 */
	public Menu(XmlModel x) {
		this.code=x.getAttribute(ConfigEnum.CODE);
		this.name=x.getAttribute(ConfigEnum.NAME);
		this.url=x.getAttribute(ConfigEnum.URL);
		this.icon=x.getAttribute(ConfigEnum.ICON);

		String m=x.getAttribute(ConfigEnum.MOBILE);
		String relieStr=x.getAttribute(ConfigEnum.RELIES);
		if(null!=relieStr){
			for(String s:relieStr.split(ConfigEnum.RelieSplitRule.toString())){
				if(s.trim().length()>0){
					relies.add(s);
				}
			}
		}
		if(null!=m){
			try {
				mobile=Boolean.parseBoolean(m);
			} catch (Exception e) {
				log.error("手机端格式错误");
			}
		}
		for(XmlModel b:x.getNodes()){
			if(b.getNodeName().equals(ConfigEnum.FIELDS.toString())){
				for(XmlModel f:b.getNodes()){
					fields.add(new Field(f));
				}
				
			}else if(b.getNodeName().equals(ConfigEnum.LAYOUTS.toString())){
				for(XmlModel f:b.getNodes()){
					layouts.add(new Layout(f));
				}
			}
//			blocks.add(new Block(b));
		}
	}
	
	
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}



	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}



	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}



	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}



	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}



	/**
	 * @return the mobile
	 */
	public boolean isMobile() {
		return mobile;
	}



	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}




//
//
//	/**
//	 * @return the blocks
//	 */
//	public List<Block> getBlocks() {
//		return blocks;
//	}



//	/**
//	 * @param blocks the blocks to set
//	 */
//	public void setBlocks(List<Block> blocks) {
//		this.blocks = blocks;
//	}



	/**
	 * @return the relies
	 */
	public List<String> getRelies() {
		return relies;
	}



	/**
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}



	/**
	 * @return the layouts
	 */
	public List<Layout> getLayouts() {
		return layouts;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str="\n菜单节点:";
		 str+="code:"+this.code+";";
		 str+="name:"+this.name+";";
		 str+="url:"+this.url+";";
		 str+="icon:"+this.icon+";";
		 str+="mobile:"+this.mobile+";";
		 for(Field p:fields){
				str+=p.toString();
			 }
		 for(Layout m:layouts){
				str+=m.toString();
			}

		return str;
	}

	
}
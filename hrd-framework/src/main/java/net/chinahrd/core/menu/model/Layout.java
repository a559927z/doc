/**
*net.chinahrd.core.menu.model
*/
package net.chinahrd.core.menu.model;

import java.util.ArrayList;
import java.util.List;

import net.chinahrd.core.ConfigEnum;
import net.chinahrd.core.xml.XmlModel;

/**
 * @author htpeng
 *2016年10月13日上午10:07:55
 */
public class Layout{

	private String code;
	private String name;
	private String img;
	private List<String> relies=new ArrayList<String>();
	private List<Plug_in> plug_ins=new ArrayList<Plug_in>();
	
	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param x
	 */
	public Layout(XmlModel x) {
		this.code=x.getAttribute(ConfigEnum.CODE);
		this.name=x.getAttribute(ConfigEnum.NAME);
		this.img=x.getAttribute(ConfigEnum.IMG);
		String relieStr=x.getAttribute(ConfigEnum.RELIES);
		if(null!=relieStr){
			for(String s:relieStr.split(ConfigEnum.RelieSplitRule.toString())){
				if(s.trim().length()>0){
					relies.add(s);
				}
			}
		}
		
		for(XmlModel p:x.getNodes()){
			plug_ins.add(new Plug_in(p));
		}
	}
	
	
	
	/**
	 * @return the id
	 */
	public String getCode() {
		return code;
	}



	/**
	 * @param id the id to set
	 */
	public void setCode(String code) {
		this.code =code;
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



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str="\n布局:";
		 str+="code:"+this.code+";";
		 str+="name:"+this.name+";";
		 str+="img:"+this.img+";";
		 for(Plug_in p:plug_ins){
			str+=p.toString();
		 }
		return str;
	}
	
	class Plug_in{
		private String code;
		private String name;
		/**
		 * @param p
		 */
		public Plug_in(XmlModel x) {
			this.code=x.getAttribute(ConfigEnum.CODE);
			this.name=x.getAttribute(ConfigEnum.NAME);
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			String str="\n插件:";
			 str+="code:"+this.code+";";
			 str+="name:"+this.name+";";

			return str;
		}
	}

	/**
	 * @return the relies
	 */
	public List<String> getRelies() {
		return relies;
	}
	
}

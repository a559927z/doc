package net.chinahrd.core.xml;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;





import net.chinahrd.core.ReadModel;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * xml加载器模板类
 * @author htpeng
 *2016年2月2日下午2:29:55
 */
public abstract class SimpleXmlRead implements XmlRead {
	private static final Logger log = Logger.getLogger(SimpleXmlRead.class);

	protected XmlModel rootNodel;
	public ReadModel getModel(){
		return rootNodel;
	}
	public XmlModel getXmlModel(){
		return rootNodel;
	}
	/**
	 * 解析xml文档
	 */
	public  void load(String filename){
		load(new File(filename));
	}
	public  void load(InputStream ins){
		 Document document = null;  
	        try {
	        	 SAXReader saxReader = new SAXReader();
	        	 //不检查dtd
	        	 saxReader.setEntityResolver(new EntityResolver() {    
	        		    public InputSource resolveEntity(String publicId,String systemId) throws SAXException, IOException {    
	        		    	InputSource is = new InputSource(new ByteArrayInputStream("".getBytes()));    
	        		    	is.setPublicId(publicId);    
	        		    	is.setSystemId(systemId);    
	        		    	return is;    
	        		    }    
	              });
	        	 
	        	    
				document = saxReader.read(ins);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				log.error("文件不存在");
				e.printStackTrace();
			} // 读取XML文件,获得document对象   
		    
		if(null!=document){
			Element rootEl=document.getRootElement();
			
			if(checkAndParseRoot(rootEl)){
				this.parseXml(rootNodel,rootEl);
			}else{
				log.error("文件根目录格式错误");
			}
			
		}
	}
	public  void load( File file){
		 Document document = null;  
	        try {
	        	 SAXReader saxReader = new SAXReader();
	        	 //不检查dtd
	        	 saxReader.setEntityResolver(new EntityResolver() {    
	        		    public InputSource resolveEntity(String publicId,String systemId) throws SAXException, IOException {    
	        		    	InputSource is = new InputSource(new ByteArrayInputStream("".getBytes()));    
	        		    	is.setPublicId(publicId);    
	        		    	is.setSystemId(systemId);    
	        		    	return is;    
	        		    }    
	              });
	        	 
	        	    
				document = saxReader.read(file);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				log.error("文件不存在");
				e.printStackTrace();
			} // 读取XML文件,获得document对象   
		    
		if(null!=document){
			Element rootEl=document.getRootElement();
			
			if(checkAndParseRoot(rootEl)){
				this.parseXml(rootNodel,rootEl);
			}else{
				log.error("文件根目录格式错误");
			}
			
		}
	}
	protected abstract boolean checkAndParseRoot(Element rootEl);
}


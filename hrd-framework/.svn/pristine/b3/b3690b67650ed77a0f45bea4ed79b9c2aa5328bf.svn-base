package net.chinahrd.core.props;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.chinahrd.core.Read;
import net.chinahrd.core.ReadModel;
import net.chinahrd.core.props.model.PropsModel;

/**
 * props 加载器模板类
 * @author htpeng
 *2016年2月2日下午2:29:55
 */
public  class PropsRead implements Read {
	private PropsModel propsModel;
	/**
	 * 解析xml文档
	 */
	public  void load(String filename){
		load(new File(filename));
	}
	public  void load(InputStream ins){
		try {
			Properties properties = new Properties();
			properties.load(ins);
			propsModel= new PropsModel(properties);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void load( File file){
		try {
			load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see net.chinahrd.core.props.PropsRead#get(java.lang.String)
	 */
	
	/* (non-Javadoc)
	 * @see net.chinahrd.core.Read#getModel()
	 */
	@Override
	public ReadModel getModel() {
		// TODO Auto-generated method stub
		return propsModel;
	}

}


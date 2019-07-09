package net.chinahrd.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 版本控制工具类
 */
public class Version {
	private static Version v=null;
//	private  final String version="version";
    // 获取默认的语言环境
	private  Locale locale = Locale.getDefault();
	
	private ResourceBundle bundle = ResourceBundle.getBundle("conf/version", locale,new Control());
	private Version(){
		
	}
	private static synchronized Version getInstance(){
		if(v==null)
			v=new Version();
		return v;
	}

    public static  Map<String,String>  version(){
        return getInstance().getMap();   
    }
    private   Map<String,String>  getMap(){
    	Map<String,String> map=new HashMap<String, String>();
     	Set<String> keys=bundle.keySet();
    	for (String key : keys) {  
    		map.put(key, bundle.getString(key));
    	}  
    	return map;
    }
    
    
    class Control extends ResourceBundle.Control{
    	/* (non-Javadoc)
    	 * @see java.util.ResourceBundle.Control#getTimeToLive(java.lang.String, java.util.Locale)
    	 */
    	@Override
    	public long getTimeToLive(String baseName, Locale locale) {
    		// TODO Auto-generated method stub
//    		return super.getTimeToLive(baseName, locale);
    		return 0;
    	}
    	
    	/* (non-Javadoc)
    	 * @see java.util.ResourceBundle.Control#needsReload(java.lang.String, java.util.Locale, java.lang.String, java.lang.ClassLoader, java.util.ResourceBundle, long)
    	 */
    	@Override
    	public boolean needsReload(String baseName, Locale locale,
    			String format, ClassLoader loader, ResourceBundle bundle,
    			long loadTime) {
    		// TODO Auto-generated method stub
    		//return super.needsReload(baseName, locale, format, loader, bundle, loadTime);
    		return true;
    	}
    	@Override
    	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader,  
                boolean reload) throws IllegalAccessException, InstantiationException, IOException {  
            // 将reload标识位置为true  
            return super.newBundle(baseName, locale, format, loader, true);  
        }  
    	
    	
    }
}

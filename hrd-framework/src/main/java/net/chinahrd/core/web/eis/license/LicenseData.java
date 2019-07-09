package net.chinahrd.core.web.eis.license;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import net.chinahrd.core.web.eis.license.LicenseConfig.LicenseParm;

/**
 * 授权数据模板
 * @author htpeng
 *2016年2月16日下午5:35:05
 */
public class LicenseData {
	private  AtomicBoolean valid = new AtomicBoolean(false);  //数据是否有效
	private Map<LicenseParm,Object> map=new HashMap<LicenseParm,Object>();    //数据值
	
	
	public boolean getValid() {
		return valid.get();
	}

	void setValid(boolean valid) {
		this.valid .set(valid);;
	}

	public Object get(LicenseParm key){
		return map.get(key);
	}
	public int getInt(LicenseParm key){
		Object obj=get(key);
		if(null==obj){
			return 0;
		}else{
			try {
				return Integer.parseInt(obj.toString());
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		
	}
	void put(LicenseParm key,Object obj){
		 map.put(key,obj);
	}

	public void clear() {
		map.clear();
		setValid(false);
	}
}

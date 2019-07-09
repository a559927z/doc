/**
*net.chinahrd.biz.paper
*/
package net.chinahrd.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.chinahrd.utils.Version;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.google.gson.Gson;

/**
 * PC和移动公共Controller 目前用于写当前版本号
 * @author htpeng
 *2016年4月27日下午4:05:31
 */
public abstract class VersionController {
	// @Autowired
	protected HttpServletRequest request;

	// @Autowired
	protected HttpServletResponse response;
	
	
	/**
	 * 用于手动调用
	 */
	protected void setVersion(){
		Map<String,String> map=Version.version();
		String version=new Gson().toJson(map);
		request.setAttribute("versionMap",map);
		request.setAttribute("versionJson",version);
	}
	
	@ModelAttribute
	protected void setReqAndRes(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
		Map<String,String> map=Version.version();
		String version=new Gson().toJson(map);
		request.setAttribute("versionMap",map);
		request.setAttribute("versionJson",version);
		autowiredAfter();
	}
	public abstract  void autowiredAfter();
}

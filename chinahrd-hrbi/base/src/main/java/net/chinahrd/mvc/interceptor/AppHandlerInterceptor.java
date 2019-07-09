package net.chinahrd.mvc.interceptor;

import com.google.gson.Gson;
import net.chinahrd.eis.permission.EisWebContext;
import net.chinahrd.eis.permission.model.AuthenticationResult;
import net.chinahrd.eis.permission.service.RbacAuthorizerService;
import net.chinahrd.mvc.app.AppUserMapping;
import net.chinahrd.mvc.app.DataPacket;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * app请求拦截器
 * @author guanjian
 *
 */
public class AppHandlerInterceptor implements HandlerInterceptor {
	protected static final Logger log = Logger.getLogger(AppHandlerInterceptor.class);
	@Autowired
	public RbacAuthorizerService authorizerService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//TODO 这里需要做更人性化的提示消息，比如在请求的URL(方法)不存在时
		log.debug("拦截APP请求！"+request.getRequestURI());
		String token = request.getParameter("token");
		if(StringUtils.isBlank(token)){
			token = request.getHeader("token");
			log.debug("从头部获取token:"+token);
		}
		if(StringUtils.isBlank(token)){
			Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
			if(null!=cookies){
				for(Cookie cookie : cookies){
		    		if(cookie.getName().equals("token")){
		    			token =cookie.getValue();
		    			log.debug("从Cookie获取token:"+token);
		    		}
		    	}
			}
		}
		DataPacket data = new DataPacket();
		Gson gson  = new Gson();
		String test=request.getParameter("test");
		//FIXME guanjian 这里是为了测试接口方便 在APP上测试时需要去掉
		if(null!=test&&log.isDebugEnabled()&&(StringUtils.isBlank(token)||!AppUserMapping.tokenIsExist(token))){
			AuthenticationResult result= authorizerService.authenticate(EisWebContext.getCustomerId(),"gyhe", "gyhe123456");
			if(result!=null&&result.getUser()!=null){
				token = AppUserMapping.addUserInMap(result.getUser());
				 Cookie cookie = new Cookie("token",token);
		    	 //设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
		    	 cookie.setPath("/");
		    	 response.addCookie(cookie);
				response.setContentType("application/json"); 
				PrintWriter out = response.getWriter();
				data.setCode(DataPacket.CODE_999);
				data.setMessage("在没登陆情况下获取到的token，用于接口测试，将新token放到url后再次请求即可访问目标接口！");
				data.setData(token);
//				out.print("{\"meg\":\"没登陆获取到token，将新token放到url后再次请求即可\",\"url\":\"?token="+token+"\"}");
				out.print(gson.toJson(data));
				out.flush();
				out.close();
				return false;
			}
		}
		//校验token是否有效
		if (StringUtils.isBlank(token)||!AppUserMapping.tokenIsExist(token)) {
			//设置输出文本格式为json
			response.setContentType("application/json"); 
			PrintWriter out = response.getWriter();
			// 获得输出流对象PrintWriter 
			data.setCode(StringUtils.isBlank(token)?DataPacket.CODE_LOGIN_FAIL:DataPacket.CODE_INVALID_TOKEN);
			data.setMessage(StringUtils.isBlank(token)?"请登录访问！":DataPacket.MSG_INVALID_TOKEN);
			out.print(gson.toJson(data));
			out.flush();
			out.close();
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}

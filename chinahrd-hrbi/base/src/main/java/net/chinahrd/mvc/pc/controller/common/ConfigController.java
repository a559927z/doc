package net.chinahrd.mvc.pc.controller.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.chinahrd.entity.dto.pc.common.ConfigDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.module.SysCache;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.common.ConfigService;
import net.chinahrd.utils.CacheHelper;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ConfigKeyUtil;
import net.chinahrd.utils.PropertiesUtil;

/**
 * 系统配置
 * @author htpeng
 *
 */
@Controller
@RequestMapping(value = "/config")
public class ConfigController extends BaseController {

	@Autowired
	private ConfigService configService;

	/***
	 * 跳转到临时页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toConfigView")
	public String getNoAuthor(HttpServletRequest requst) {
		
		return "biz/config";
	}

	/**
	 * 获取所有配置类型
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSysConfig")
	public Map<String,String> getSysConfig() {
		Map<String,String> map=CollectionKit.newMap();
		map.put(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_SYSNAME).trim(), CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_SYSNAME));
		map.put(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_MSGHOST).trim(), CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_MSGHOST));
		map.put(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_MSGACCOUNT).trim(), CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_MSGACCOUNT));
		map.put(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_MSGPASSWORD).trim(), CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_MSGPASSWORD));
		map.put(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_ISOFFSERVICE).trim(), CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_ISOFFSERVICE));
		map.put(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_EMAILHOST).trim(), CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_EMAILHOST));
		map.put(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_EMAILPASSWORD).trim(), CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_EMAILPASSWORD));
		map.put(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_EMAILPORT).trim(), CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_EMAILPORT));
		map.put(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_EMAILACCOUNT).trim(), CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_EMAILACCOUNT));
		map.put(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_ADMINPASSWORD).trim(), CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_ADMINPASSWORD));
		map.put(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_ADMINMAIL).trim(), CacheHelper.getConfigValByCacheStr(ConfigKeyUtil.XTSZ_ADMINMAIL));
		return map;
	}



	/**
	 * 修改所有配置类型
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upadteSysConfig")
	public ResultDto<String> upadteSysConfig(@RequestBody(required = false)List<ConfigDto> list ) {
		ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        result.setMsg("操作错误");
        rs= configService.updateSysConfig(getCustomerId(), list);
        if(rs){
        	SysCache.config.update();
        	 result.setMsg("修改系统配置成功");
        }else{
        	 result.setMsg("修改系统配置失败");
        }
        result.setType(rs);
        return result;
	}
	
	/**
	 * 发送测试短信
	 * @param list
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendMessage")
	public ResultDto<String> sendMessageUrl(@RequestBody(required = false)List<ConfigDto> list ) {
		ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        result.setMsg("操作错误");
        //rs= configService.updateSysConfig(getCustomerId(), list);
        if(rs){
        	SysCache.config.update();
        	 result.setMsg("发送短信成功");
        }else{
        	 result.setMsg("发送短信失败");
        }
        result.setType(rs);
        return result;
	}
	/**
	 * 发送测试邮件
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/sendEmail")
	public ResultDto<String> sendEmailUrl(@RequestBody(required = false)Map<String,String> map ) {
		ResultDto<String> result = new ResultDto<>();
        boolean rs = false;
        result.setMsg("操作错误");
        if(null==map){
        	return result;
        }
        String host= map.get(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_EMAILHOST).trim());
        int port=Integer.parseInt(map.get(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_EMAILPORT).trim()));
        String sendAccount= map.get(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_EMAILACCOUNT).trim());
        String sendAccountPassword= map.get(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_EMAILPASSWORD).trim());
        String receiveAccount= map.get(PropertiesUtil.getProperty(ConfigKeyUtil.XTSZ_ADMINMAIL).trim());
       // rs= configService.updateSysConfig(getCustomerId(), list);
        if(rs){
        	SysCache.config.update();
        	 result.setMsg("邮件发送成功");
        }else{
        	 result.setMsg("邮件发送失败");
        }
        result.setType(rs);
        return result;
	}
}
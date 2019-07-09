/**
*net.chinahrd.core.cache.model
*/
package net.chinahrd.core.cache.model;

import net.chinahrd.core.timer.model.TimerConfig;

/**
 * 简单的缓存标识config
 * @author htpeng
 *2016年10月10日下午2:19:37
 */
public abstract class CacheConfigAbstract implements CacheConfig {
	private TimerConfig timerConfig; // 定时更新配置
	private String method; // 查询数据库调用的方法
	private Object[] objs = null; // 参数
	private String identify;


	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}


	public CacheConfigAbstract(String method){
		this(null,method,false);
	}
	
	/**
	 * 构造方法
	 * @param method   数据库DAO层方法名
	 * @param lazy    是否懒加载
	 */
	public CacheConfigAbstract(String method,boolean lazy){
		this(null,method,lazy);
	}
	
	/**
	 * 构造方法
	 * @param method   数据库DAO层方法名
	 * @param cron    调度时间 规则
	 */
	public CacheConfigAbstract(String cron, String method){
		this(cron,method,false);
	}
	
	/**
	 * 构造方法
	 * @param cron  调度时间 规则
	 * @param method   数据库DAO层方法名
	 * @param lazy   是否懒加载
	 */
	public CacheConfigAbstract(String cron, String method,boolean lazy){
		super();
		this.method = method;
		this.timerConfig=new TimerConfig(cron,lazy);
	}


	/**
	 * 获取该缓存唯一标识符
	 */
	@Override
	public String getIdentify() {
		return this.identify;
	}


	/**
	 * 获取缓存的所有参数
	 */
	@Override
	public Object[] getParameters() {
		return objs;
	}
	/**
	 * 设置缓存所需参数
	 */
	@Override
	public void setParameters(Object... objs) {
		this.objs=objs;
	}

	/**
	 * 获取缓存定时任务配置
	 */
	@Override
	public TimerConfig getTimerConfig(){
		return timerConfig;
	}
	/**
	 * 设置缓存定时任务配置
	 */
	public void setTimerConfig(TimerConfig timerConfig) {
		this.timerConfig = timerConfig;
	}
	

	/* (non-Javadoc)
	 * @see net.chinahrd.core.cache.model.CacheConfig#setIdentify(java.lang.String)
	 */
	@Override
	public void setIdentify(String identify) {
		// TODO Auto-generated method stub
		this.identify=identify;
	}
}

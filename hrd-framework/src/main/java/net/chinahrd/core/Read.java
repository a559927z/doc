package net.chinahrd.core;

import java.io.File;
import java.io.InputStream;





/**
 * xml加载接口
 * @author htpeng
 *2016年2月2日下午2:30:26
 */
public interface Read {
	/*
	 * 配置文件
	 */
	public void load(String filename);
	public void load(InputStream ins);
	public void load(File file);

	/*
	 * 获取解析完的节点
	 */
	public ReadModel getModel();
}

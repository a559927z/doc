package net.chinahrd.core.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.chinahrd.core.web.init.DataInitialization;
import net.chinahrd.core.web.init.DataInitializationConfig;

/**
 * 服务器启动后 自动加载 初始化数据的serlvet
 * 
 * @author htpeng 2016年2月2日下午2:36:13
 */
public class InitDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitDataServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void init(ServletConfig config) throws ServletException {
		for (Class<? extends DataInitialization> c : DataInitializationConfig.getList()) {
			try {
				DataInitialization d = (DataInitialization) c.newInstance();
				d.init(config);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

}

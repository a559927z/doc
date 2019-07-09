package net.chinahrd.eis.permission;

import net.chinahrd.utils.PropertiesUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 * Created by wqcai on 2016/2/1.
 */
public class WebConfigListener implements ServletContextListener {

    protected static final Logger logger = Logger.getLogger(WebConfigListener.class);

    @SuppressWarnings("unused")
	@Override
    public void contextInitialized(ServletContextEvent sce) {
    	System.setProperty("user.timezone","Etc/GMT-8");
//    	-Duser.timezone="Etc/GMT-8"
        try {
            PropertiesUtil.pps = new Properties();
            InputStream inputStream = WebConfigListener.class.getClassLoader()
                    .getResourceAsStream("conf/config.properties");
            PropertiesUtil.pps.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        ServletContext servletContext = sce.getServletContext();
//        servletContext.setAttribute("defaultPassword", StringUtils.trim(PropertiesUtil.getProperty("user.password")));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        System.out.println("contextDestroyed:" + sce.getServletContext().getContextPath());
    }

}

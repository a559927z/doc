package net.chinahrd.core.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * 读取配置文件
 * Created by wqcai on 2016/2/1.
 */
public class ConfigInitialization implements ServletContextListener {

    protected static final Logger logger = Logger.getLogger(ConfigInitialization.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        try {
//            PropertiesUtil.pps = new Properties();
//            InputStream inputStream = ConfigInitialization.class.getClassLoader()
//                    .getResourceAsStream("conf/config.properties");
//            PropertiesUtil.pps.load(inputStream);
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//        ServletContext servletContext = sce.getServletContext();
////        servletContext.setAttribute("defaultPassword", StringUtils.trim(PropertiesUtil.getProperty("user.password")));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        System.out.println("contextDestroyed:" + sce.getServletContext().getContextPath());
    }

}

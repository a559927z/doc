/**
 * net.chinahrd.core
 */
package net.chinahrd.core;

import java.io.InputStream;

import org.apache.log4j.Logger;

import net.chinahrd.core.exception.io.NotFoundFile;
import net.chinahrd.core.tools.file.FileTool;

/**
 * 提供注入的file 读取方法
 * @author htpeng
 *2016年10月12日下午12:43:06
 */
public abstract class RegisterAbstract {
    private static final Logger log = Logger.getLogger(RegisterAbstract.class);

    /**
     * 默认提供获取Xml方法
     * @return 文件在包项目中的路径    建议不以  "/" 绝对路径开头
     */
    protected abstract String getXmlPath();

    /**
     * 默认提供获取Xml方法
     * @param path  文件在包项目中的路径    建议不以  "/" 绝对路径开头
     * @return
     */
    protected InputStream getInputStream(String path) {
        if (null == path || path.trim().length() == 0) {
            return null;
        }
        try {
            return FileTool.getInputStreamByJarPath(this.getClass().getProtectionDomain().getCodeSource().getLocation(), path);
        } catch (NotFoundFile e) {
            log.error(e);
            return null;
        }
    }
}

package net.chinahrd.core.web.eis.license.util;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
	 
/**
 * java在linux环境下执行linux命令，然后返回命令返回值。
 * @author htpeng
 *2016年2月16日下午5:42:28
 */
public class ExecLinuxCMDUtil {

    public static String exec(String cmd) {
        try {
            String[] cmdA = { "/bin/sh", "-c", cmd };
            Process process = Runtime.getRuntime().exec(cmdA);
            LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
           
            StringBuffer sb = new StringBuffer();
            String line;
            
            while ((line = br.readLine()) != null) {
                
                sb.append(line).append("\n");
            }
            
            return sb.toString();
            
        } catch (Exception e) {
        //	s_logger.error("An error occurred while trying to execute the command ["+cmd+"]" );
        }
        return null;
    }
 
  
 
}

package net.chinahrd.core.web.eis.license;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;



/**
 * 在window平台上生成机器码
 * @author htpeng
 *2016年2月16日下午5:40:01
 */
public class WindowSequenceService  extends AbstractSequenceService{
	
	public static final Logger s_logger = Logger.getLogger(WindowSequenceService.class);
	
    @Override
    public String generateSequence() {
    	
        try {
            
            Set<String> result = new HashSet<String>();
            Process process = Runtime.getRuntime().exec(
    			    new String[] { "wmic", "cpu", "get", "ProcessorId" });
    	    process.getOutputStream().close();
            @SuppressWarnings("resource")
			String cpu_id =new Scanner(process.getInputStream()).next();

            String sys_uuid =getMotherboardSN();
            if(StringUtils.isNotBlank(cpu_id)){
            	result.add(cpu_id);
            };
            
            if(StringUtils.isNotBlank(sys_uuid)){
            	s_logger.debug("==system uuid="+sys_uuid);
            	result.add(sys_uuid);
            }

            if(result.size()<1){
                return null;
            }
            
            String machineCode = getMD5(result.toString());

            return machineCode;
        } catch (Throwable ex) {
           s_logger.error("生成Window平台下的机器码失败", ex);
        }
        
        return null;
    }

    public static String getMotherboardSN() {
    	  String result = "";
    	  try {
    	   File file = File.createTempFile("realhowto", ".vbs");
    	   file.deleteOnExit();
    	   FileWriter fw = new java.io.FileWriter(file);

    	   String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
    	     + "Set colItems = objWMIService.ExecQuery _ \n"
    	     + "   (\"Select * from Win32_BaseBoard\") \n"
    	     + "For Each objItem in colItems \n"
    	     + "    Wscript.Echo objItem.SerialNumber \n"
    	     + "    exit for  ' do the first cpu only! \n" + "Next \n";

    	   fw.write(vbs);
    	   fw.close();
    	   Process p = Runtime.getRuntime().exec(
    	     "cscript //NoLogo " + file.getPath());
    	   BufferedReader input = new BufferedReader(new InputStreamReader(p
    	     .getInputStream()));
    	   String line;
    	   while ((line = input.readLine()) != null) {
    	    result += line;
    	   }
    	   input.close();
    	  } catch (Exception e) {
    	   e.printStackTrace();
    	  }
    	  return result.trim();
    	 }
    public static void main(String[] args) {
	     System.out.println(new WindowSequenceService().getSequence());

//        SequenceService s = new WindowSequenceService();
//        String seq = s.getSequence();
//        System.out.println("==生成的机器码为="+seq);
    	//License.setPath("/root/桌面");
    //	License.check();
 //   	License.test();
    }

	@Override
	public void exit() {
		try {
			Process process = Runtime.getRuntime().exec(
				    new String[] { "wmic", "process", "where", "name='javaw.exe'","call","terminate" });
			 process.getOutputStream().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	   
	}
}


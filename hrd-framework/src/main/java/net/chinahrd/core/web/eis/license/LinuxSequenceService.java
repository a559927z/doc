package net.chinahrd.core.web.eis.license;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashSet;
import java.util.Set;

import net.chinahrd.core.web.eis.license.util.ExecLinuxCMDUtil;
import net.chinahrd.core.web.eis.license.AbstractSequenceService;
import net.chinahrd.core.web.eis.license.LinuxSequenceService;
import net.chinahrd.core.web.eis.license.SequenceService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;



/**
 * 在Linux平台上生成机器码
 * @author htpeng
 *2016年2月16日下午5:39:31
 */
public class LinuxSequenceService  extends AbstractSequenceService{
	
	public static final Logger s_logger = Logger.getLogger(LinuxSequenceService.class);
	
    @Override
    public String generateSequence() {
        try {
            
            Set<String> result = new HashSet<String>();
            
            String cpu_cmd = "dmidecode -t 4|grep \"ID\"";
            String sysuuid_cmd = "dmidecode -t 1|grep \"UUID\"";
            String syssn_cmd = "dmidecode -t 1|grep \"Serial Number\"";
            
            String cpu_id = ExecLinuxCMDUtil.exec(cpu_cmd);
            String sys_uuid = ExecLinuxCMDUtil.exec(sysuuid_cmd);
            String sys_sn = ExecLinuxCMDUtil.exec(syssn_cmd);
            
            
            
            if(StringUtils.isNotBlank(cpu_id)){
            	int index = StringUtils.indexOf(cpu_id, "ID",4);
            	s_logger.debug("==cpu id="+cpu_id);
            	System.out.println("==cpu id="+cpu_id);
            	if(index > 0){
            		String cpuid = cpu_id.substring(1,index).split(":")[1].trim().toUpperCase().replaceAll(" ", "-");
            		result.add(cpuid);
            		s_logger.debug("==single cpu id="+cpuid);
            		System.out.println("==single cpu id="+cpuid);
            	}else{
            		String cpuid = cpu_id.split(":")[1].trim().toUpperCase().replaceAll(" ", "-");
            		result.add(cpuid);
            		s_logger.debug("==single cpu id="+cpuid);
            		System.out.println("==single cpu id="+cpuid);
            	}
            	
            };
            
            
            
            if(StringUtils.isNotBlank(sys_uuid)){
            	s_logger.debug("==system uuid="+sys_uuid);
            	System.out.println("==system uuid="+sys_uuid);
            	result.add(sys_uuid.split(":")[1].trim().toUpperCase());
            }
            
            if(StringUtils.isNotBlank(sys_sn)){
            	s_logger.debug("==system serial number="+sys_sn);
            	System.out.println("==system serial number="+sys_sn);
            	result.add(sys_sn.split(":")[1].trim().toUpperCase());
            }
            
            
            System.out.println("result" + result.size());
            
           
            if(result.size()<1){
                return null;
            }
            
            
            String machineCode = getMD5(result.toString());
            
            if(s_logger.isDebugEnabled()){
            	s_logger.debug("sn!!!!!!!!!!!!!!"+machineCode);
            }
            System.out.println("sn!!!!!!!!!!!!!!"+machineCode);

            return machineCode;
        } catch (Throwable ex) {
           s_logger.error("生成Linux平台下的机器码失败", ex);
        }
        
        return null;
    }

    public static void main(String[] args) {
        SequenceService s = new LinuxSequenceService();
        String seq = s.getSequence();
        System.out.println("==生成的机器码为="+seq);
    	//License.setPath("/root/桌面");
    //	License.check();
 //   	License.test();
    }

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		String pid = getPID();
		String kill_cmd = "kill -9 "+pid;
		
		ExecLinuxCMDUtil.exec(kill_cmd);
	}
	 private String getPID(){
	    	RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
	    	String process_name = runtime.getName();
	    	s_logger.info("==process name=="+process_name);
	    	String pid = process_name.substring(0,process_name.indexOf("@"));
	    	s_logger.info("==process id=="+pid);
	    	return pid;
	    }
	    
}


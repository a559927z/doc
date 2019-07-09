package net.chinahrd.core.web.eis.license;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.chinahrd.core.web.eis.license.util.ConvertUtils;
import net.chinahrd.core.web.eis.license.SequenceService;

import org.apache.log4j.Logger;



/**
 * 机器码生成的通用服务
 * @author htpeng
 *2016年2月16日下午5:40:56
 */
public abstract class AbstractSequenceService   implements SequenceService{
	protected String sequence=null;
	public static final Logger s_logger = Logger.getLogger(AbstractSequenceService.class);
	  public String getSequence() {
		  if(sequence==null){
			  sequence=generateSequence();
		  }
		  return sequence;
	  }
	  abstract String generateSequence() ;
	
    /**
     * 对一段String生成MD5摘要信息
     * @param message 要摘要的String
     * @return 生成的MD5摘要信息
     */
    protected String getMD5(String message) {
        message += "{shikongyun}";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
           s_logger.debug("MD5摘要长度：" + md.getDigestLength());
            byte[] b = md.digest(message.getBytes("utf-8"));
            String md5 = ConvertUtils.byte2HexString(b)+message.length();
            return getSplitString(md5);
        } catch (NoSuchAlgorithmException nse) {
           s_logger.error("MD5摘要失败",nse);
        } catch (UnsupportedEncodingException ue) {
            s_logger.error("MD5摘要失败",ue);
        }
        return null;
    }
    /**
     * 将很长的字符串以固定的位数分割开，以便于人类阅读
     * @param str
     * @return 
     */
    protected String getSplitString(String str){ 
        return getSplitString(str, "-", 4);
    }
    /**
     * 将很长的字符串以固定的位数分割开，以便于人类阅读
     * 如将
     * 71F5DA7F495E7F706D47F3E63DC6349A
     * 以-，每4个一组，则分割为
     * 71F5-DA7F-495E-7F70-6D47-F3E6-3DC6-349A
     * @param str 字符串
     * @param split 分隔符
     * @param length 长度
     * @return 
     */
    protected String getSplitString(String str, String split, int length){        
        int len=str.length();
        StringBuilder temp=new StringBuilder();
        for(int i=0;i<len;i++){
            if(i%length==0 && i>0){
                temp.append(split);
            }
            temp.append(str.charAt(i));
        }
        String[] attrs=temp.toString().split(split);
        StringBuilder finalMachineCode=new StringBuilder();
        for(String attr : attrs){
            if(attr.length()==length){
                finalMachineCode.append(attr).append(split);
            }
        }
        String result=finalMachineCode.toString().substring(0, finalMachineCode.toString().length()-1);
        return result;
    }    
   
}

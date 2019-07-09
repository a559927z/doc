package net.chinahrd.core.web.eis.license;

/**
 * 生成机器码的接口，不同平台有不同实现
 * @author htpeng
 *2016年2月16日下午5:40:31
 */
public interface SequenceService {
    /**
     * 获取机器码
     * @return  机器码
     */
    public String getSequence();  
    
    /**
     * 验证失败强制关闭服务
     */
    public void exit();
}

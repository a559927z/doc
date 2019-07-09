package net.chinahrd.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.dto.AopInformation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import net.chinahrd.dto.LogDto;

@Repository("logMgrDao")
public interface LogMgrDao {

    List<LogDto> queryAllLog(@Param("paramMap") Map<String, Object> paramsMap);

    //	@SelectProvider(type = LogProvider.class, method = "countAllLogSQL")
    Long countAllLog(@Param("paramMap") Map<String, Object> paramsMap);


    /**
     * 添加用户登录记录
     *
     * @param loginLog 用户登录对象
     */
    void insertLoginLog(AopInformation loginLog);

    /**
     * 添加操作日志
     */
    void insertOperateLog(AopInformation loginLog);

}

package net.chinahrd.modules.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.chinahrd.modules.demo.entity.EmpInfo;
import net.chinahrd.modules.demo.response.EmpInfoRes;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bright
 * @since 2019-03-20
 */
public interface EmpInfoDAO extends BaseMapper<EmpInfo> {

    /**
     * 根据条件查询员工信息
     *
     * @param paramMap
     * @return
     */
    List<EmpInfoRes> listEmpInfo(Map<String,Object> paramMap);

}

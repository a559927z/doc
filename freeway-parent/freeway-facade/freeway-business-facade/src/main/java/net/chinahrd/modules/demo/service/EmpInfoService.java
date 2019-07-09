package net.chinahrd.modules.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.chinahrd.common.PageData;
import net.chinahrd.modules.demo.entity.EmpInfo;
import net.chinahrd.modules.demo.request.EmpQueryReq;
import net.chinahrd.modules.demo.response.EmpInfoRes;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bright
 * @since 2019-03-20
 */
public interface EmpInfoService extends IService<EmpInfo> {
    /**
     * 分页查询列表
     * @param dataReq
     * @return
     */
    PageData<EmpInfoRes> selectByPage(EmpQueryReq dataReq);
}

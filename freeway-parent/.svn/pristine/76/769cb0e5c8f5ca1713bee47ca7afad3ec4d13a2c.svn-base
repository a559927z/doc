package net.chinahrd.platform.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.chinahrd.common.PageData;
import net.chinahrd.platform.demo.entity.DemoUserInfo;
import net.chinahrd.platform.demo.request.DemoUserQueryReq;
import net.chinahrd.platform.demo.response.DemoUserInfoRes;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author bright
 * @since 2019-03-05
 */
public interface DemoUserInfoService extends IService<DemoUserInfo> {

    /**
     * 分页查询列表
     *
     * @param dataReq
     * @return
     */
    PageData<DemoUserInfoRes> selectByPage(DemoUserQueryReq dataReq);


    /**
     * 定时测试
     *
     * @return
     */
    List<DemoUserInfoRes> selectListByTask();

}

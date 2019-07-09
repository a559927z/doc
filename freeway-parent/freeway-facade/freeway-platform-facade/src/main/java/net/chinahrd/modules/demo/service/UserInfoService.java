package net.chinahrd.modules.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.chinahrd.common.PageData;
import net.chinahrd.modules.demo.entity.UserInfo;
import net.chinahrd.modules.demo.request.UserQueryReq;
import net.chinahrd.modules.demo.response.UserInfoRes;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bright
 * @since 2019-03-20
 */
public interface UserInfoService extends IService<UserInfo> {
    /**
     * 分页查询列表
     *
     * @param dataReq
     * @return
     */
    PageData<UserInfoRes> selectByPage(UserQueryReq dataReq);


}

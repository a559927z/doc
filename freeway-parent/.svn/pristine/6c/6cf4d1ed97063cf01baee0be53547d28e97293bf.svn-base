package net.chinahrd.modules.pub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.PageData;
import net.chinahrd.modules.pub.entity.TenantInfo;
import net.chinahrd.modules.pub.request.TenantInfoReq;
import net.chinahrd.modules.pub.request.TenantQueryReq;
import net.chinahrd.modules.pub.response.TenantInfoRes;

/**
 * <p>
 * 租户管理 服务类
 * </p>
 *
 * @author zoujiaxi
 * @since 2019-03-19
 */
public interface TenantInfoService extends IService<TenantInfo> {

    PageData<TenantInfoRes> selectByPage(TenantQueryReq dataReq);

    void deleteById(String tenantId);

    void add(TenantInfoReq entity, CurrentUser user);

    void updateById(TenantInfoReq entity, CurrentUser user);

    TenantInfoRes selectById(String tenantId);
}

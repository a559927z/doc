package net.chinahrd.modules.pub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.PageData;
import net.chinahrd.modules.pub.entity.DatasourceInfo;
import net.chinahrd.modules.pub.request.DatasourceQueryReq;
import net.chinahrd.modules.pub.response.DatasourceInfoRes;


/**
 * <p>
 * 数据源管理 服务类
 * </p>
 *
 * @author zoujiaxi
 * @since 2019-03-19
 */
public interface DatasourceInfoService extends IService<DatasourceInfo> {

    /**
     * 分页查询列表
     *
     * @param dataReq
     * @return
     */
    PageData<DatasourceInfoRes> selectByPage(DatasourceQueryReq dataReq);

    void deleteById(DatasourceQueryReq dataReq);

    void insert(DatasourceInfo dataReq, CurrentUser user);

    void updateById(DatasourceInfo entity, CurrentUser user);

    DatasourceInfo selectById(String datasourceId);
}

package net.chinahrd.modules.pub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.PageData;
import net.chinahrd.modules.pub.dao.DatasourceInfoDAO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.chinahrd.modules.pub.entity.DatasourceInfo;
import net.chinahrd.modules.pub.request.DatasourceQueryReq;
import net.chinahrd.modules.pub.response.DatasourceInfoRes;
import net.chinahrd.modules.pub.service.DatasourceInfoService;
import net.chinahrd.utils.PageUtil;
import net.chinahrd.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据源管理 服务实现类
 * </p>
 *
 * @author zoujiaxi
 * @since 2019-03-19
 */
@Slf4j
@Service
public class DatasourceInfoServiceImpl extends ServiceImpl<DatasourceInfoDAO, DatasourceInfo> implements DatasourceInfoService {

    @Resource
    DatasourceInfoDAO datasourceInfoDAO;

    @Override
    public PageData<DatasourceInfoRes> selectByPage(DatasourceQueryReq dataReq) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(dataReq.getDatasourceName())) {
            paramMap.put("datasourceName", dataReq.getDatasourceName());
        }
        if (StringUtils.isNotBlank(dataReq.getDbType())) {
            paramMap.put("dbType", dataReq.getDbType());
        }

        PageUtil.startPage(dataReq);
        List<DatasourceInfoRes> userList = datasourceInfoDAO.listDatasourceInfo(paramMap);
        return PageUtil.convert(userList);
    }

    @Override
    public void deleteById(DatasourceQueryReq dataReq) {
        if (StringUtils.isNotBlank(dataReq.getDatasourceId())) {
            UpdateWrapper<DatasourceInfo> wrapper = new UpdateWrapper<DatasourceInfo>();
            wrapper.lambda().eq(DatasourceInfo::getDatasourceId,dataReq.getDatasourceId());
            datasourceInfoDAO.delete(wrapper);
        }
        return;
    }

    @Override
    public void insert(DatasourceInfo entity, CurrentUser user) {
        String datasourceId = UuidUtil.uuid32();
        entity.setDatasourceId(datasourceId);
        entity.setCreateInfo(user.getUserName());
        entity.setUpdateInfo(user.getUserName());
        datasourceInfoDAO.insert(entity);
    }

    @Override
    public void updateById(DatasourceInfo entity, CurrentUser user) {
        entity.setUpdateInfo(user.getUserName());
        UpdateWrapper<DatasourceInfo> wrapper = new UpdateWrapper<DatasourceInfo>();
        wrapper.lambda().eq(DatasourceInfo::getDatasourceId,entity.getDatasourceId());
        datasourceInfoDAO.update(entity,wrapper);
    }

    @Override
    public DatasourceInfo selectById(String datasourceId) {
        QueryWrapper<DatasourceInfo> wrapper = new QueryWrapper<DatasourceInfo>();
        wrapper.lambda().eq(DatasourceInfo::getDatasourceId,datasourceId);
        return datasourceInfoDAO.selectOne(wrapper);
    }
}

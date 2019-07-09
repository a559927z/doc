package net.chinahrd.modules.pub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.chinahrd.common.CurrentUser;
import net.chinahrd.common.PageData;
import net.chinahrd.modules.pub.dao.TenantDatasourceRelDAO;
import net.chinahrd.modules.pub.dao.TenantInfoDAO;
import net.chinahrd.modules.pub.entity.DatasourceInfo;
import net.chinahrd.modules.pub.entity.TenantDatasourceRel;
import net.chinahrd.modules.pub.entity.TenantInfo;
import net.chinahrd.modules.pub.request.TenantInfoReq;
import net.chinahrd.modules.pub.request.TenantQueryReq;
import net.chinahrd.modules.pub.response.TenantInfoRes;
import net.chinahrd.modules.pub.service.TenantInfoService;
import net.chinahrd.utils.PageUtil;
import net.chinahrd.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 租户管理 服务实现类
 * </p>
 *
 * @author zoujiaxi
 * @since 2019-03-19
 */
@Slf4j
@Service
public class TenantInfoServiceImpl extends ServiceImpl<TenantInfoDAO, TenantInfo> implements TenantInfoService {
    @Resource
    TenantInfoDAO tenantInfoDAO;
    @Resource
    TenantDatasourceRelDAO tenantDatasourceRelDAO;

    @Override
    public PageData<TenantInfoRes> selectByPage(TenantQueryReq dataReq) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(dataReq.getTenantName())) {
            paramMap.put("tenantName", dataReq.getTenantName());
        }
        if (StringUtils.isNotBlank(dataReq.getTenantState())) {
            paramMap.put("tenantState", dataReq.getTenantState());
        }

        PageUtil.startPage(dataReq);
        List<TenantInfoRes> userList = tenantInfoDAO.listTenantInfo(paramMap);
        return PageUtil.convert(userList);
    }

    @Override
    public void deleteById(String tenantId) {
        UpdateWrapper<TenantInfo> wrapper = new UpdateWrapper<TenantInfo>();
        wrapper.lambda().eq(TenantInfo::getTenantId,tenantId);
        tenantInfoDAO.delete(wrapper);

        UpdateWrapper<TenantDatasourceRel> relUpdateWrapper = new UpdateWrapper<TenantDatasourceRel>();
        relUpdateWrapper.lambda().eq(TenantDatasourceRel::getTenantId,tenantId);
        tenantDatasourceRelDAO.delete(relUpdateWrapper);
    }

    @Override
    public void add(TenantInfoReq entity, CurrentUser user) {
        TenantInfo tenantInfo = new TenantInfo();
        TenantDatasourceRel tenantDatasourceRel = new TenantDatasourceRel();
        BeanUtils.copyProperties(entity,tenantInfo);
        BeanUtils.copyProperties(entity,tenantDatasourceRel);
        String tenantId = UuidUtil.uuid32();
        tenantInfo.setTenantId(tenantId);
        tenantInfo.setCreateInfo(user.getUserName());
        tenantInfo.setUpdateInfo(user.getUserName());

        String relId = UuidUtil.uuid32();
        tenantDatasourceRel.setTenantId(tenantId);
        tenantDatasourceRel.setRelId(relId);
        tenantDatasourceRel.setCreateInfo(user.getUserName());
        tenantDatasourceRel.setUpdateInfo(user.getUserName());
        tenantInfoDAO.insert(tenantInfo);
        tenantDatasourceRelDAO.insert(tenantDatasourceRel);
    }

    @Override
    public void updateById(TenantInfoReq entity,CurrentUser user) {
        TenantInfo tenantInfo = new TenantInfo();
        TenantDatasourceRel tenantDatasourceRel = new TenantDatasourceRel();
        BeanUtils.copyProperties(entity,tenantInfo);
        BeanUtils.copyProperties(entity,tenantDatasourceRel);
        tenantInfo.setUpdateInfo(user.getUserName());
        tenantDatasourceRel.setUpdateInfo(user.getUserName());

        UpdateWrapper<TenantInfo> infoUpdateWrapper = new UpdateWrapper<TenantInfo>();
        infoUpdateWrapper.lambda().eq(TenantInfo::getTenantId,tenantInfo.getTenantId());
        tenantInfoDAO.update(tenantInfo,infoUpdateWrapper);

        UpdateWrapper<TenantDatasourceRel> relUpdateWrapper = new UpdateWrapper<TenantDatasourceRel>();
        relUpdateWrapper.lambda().eq(TenantDatasourceRel::getTenantId,tenantDatasourceRel.getTenantId());
        tenantDatasourceRelDAO.update(tenantDatasourceRel,relUpdateWrapper);
    }

    @Override
    public TenantInfoRes selectById(String tenantId) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(tenantId)) {
            paramMap.put("tenantId", tenantId);
        }
        List<TenantInfoRes> tenantInfoResList = tenantInfoDAO.listTenantInfo(paramMap);
        return tenantInfoResList.get(0);
    }
}

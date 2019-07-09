package net.chinahrd.deployment;

import lombok.extern.slf4j.Slf4j;
import net.chinahrd.common.PlatformTenant;
import net.chinahrd.service.refresh.Refreshable;
import net.chinahrd.service.tenant.PlatformTenantService;
import net.chinahrd.utils.SyncLockUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  读取平台端的租户信息，并保存在内存中，实现刷新接口
 * </p>
 *
 * @author bright
 * @since 2019/3/19 14:51
 */
@Slf4j
@Component
public class CacheTenantManager implements Refreshable {
    private final Map<String, PlatformTenant> tenantMap = new HashMap<>();

    @Lazy
    //@Reference(lazy = true)
    public PlatformTenantService platformTenantService;

    public PlatformTenant getPlatformTenant(String tenantId) {
        // 防止多个同tenantId线程同时进入
        String lock = SyncLockUtil.buildStringLock("T_", "M_", tenantId);
        synchronized (lock) {
            PlatformTenant platformTenant = tenantMap.get(tenantId);
            if (platformTenant == null) {
                platformTenant = platformTenantService.queryPlatformTenant(tenantId);
                String errInfo = null;
                if (platformTenant == null) {
                    errInfo = String.format("数据配置错误，平台系统未找到[tenantId=%s]对应的租户数据", tenantId);
                } else if (StringUtils.isBlank(platformTenant.getDatasourceId())) {
                    errInfo = String.format("数据配置错误，平台系统未找到[tenantId=%s]对应的租户数据数据源配置", tenantId);
                }
                if (StringUtils.isNotBlank(errInfo)) {
                    log.error(errInfo);
                    throw new RuntimeException(errInfo);
                } else {
                    tenantMap.put(tenantId, platformTenant);
                }
            }
            return platformTenant;
        }
    }

    @Override
    public void refresh() {
        tenantMap.clear();
    }
}

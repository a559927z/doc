package net.chinahrd.deployment;

import lombok.extern.slf4j.Slf4j;
import net.chinahrd.common.PlatformTenant;
import net.chinahrd.db.DBTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author bright
 * @since 2019/3/15 17:07
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Autowired
    public CacheTenantManager cacheTenantManager;

    /** 数据源KEY-VALUE键值对 */
    private Map<Object, Object> targetDataSources = new HashMap<>();

    public DynamicDataSource() {
        super.setTargetDataSources(this.targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        log.debug("【设置系统当前使用的数据源】");
        String key = TenantInfoHolder.getDataSourceKey();
        log.debug("【当前数据源key为：{}】", key);
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("没有指定数据源");
        }

        // 判断数据源是否需要初始化
        this.verifyAndInitDataSource(key);
        log.debug("【切换至数据源：{}】", key);
        return key;
    }

    /**
     * 判断数据源是否需要初始化
     */
    private void verifyAndInitDataSource(String key) {
        Object obj = this.targetDataSources.get(key);
        if (obj != null) {
            return;
        }
        log.info("【初始化数据源】");
        String tenantId = TenantInfoHolder.getTenantId();
        DataSource datasource = this.createDataSource(tenantId);
        this.addTargetDataSource(key, datasource);
    }

    /**
     * 往数据源key-value键值对集合添加新的数据源
     * @param key 新的数据源键
     * @param dataSource 新的数据源
     */
    public void addTargetDataSource(String key, DataSource dataSource) {
        this.targetDataSources.put(key, dataSource);
        // super.setTargetDataSources(this.targetDataSources);
        super.afterPropertiesSet();
    }

    /**
     * 创建数据源
     * @param tenantId
     * @return 数据源{@link T}
     */
    private DataSource createDataSource(String tenantId) {
        PlatformTenant platformTenant = cacheTenantManager.getPlatformTenant(tenantId);
        if (platformTenant != null) {
            DBTypeEnum dbTypeEnum = DBTypeEnum.byCode(platformTenant.getDbType());
            if (dbTypeEnum != null) {
                DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
                dataSourceBuilder.driverClassName(dbTypeEnum.getDriverClass());
                dataSourceBuilder.url(platformTenant.getUrl());
                dataSourceBuilder.username(platformTenant.getUsername());
                dataSourceBuilder.password(platformTenant.getPassword());
                return dataSourceBuilder.build();
            }
        }
        return null;
    }
}

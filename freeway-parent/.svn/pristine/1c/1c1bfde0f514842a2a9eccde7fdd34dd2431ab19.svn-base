package net.chinahrd.deployment;

/**
 * <p>
 * 租户数据线程持有类，包括租户Id和对应的数据源Key
 * </p>
 *
 * @author bright
 * @since 2019/3/18 14:38
 */
public abstract class TenantInfoHolder {
    private static ThreadLocal<String> tenantId = new ThreadLocal<String>();
    private static ThreadLocal<String> dataSourceKey = new ThreadLocal<String>();

    /**
     * 获取当前线程的数据源路由的key
     */
    public static String getDataSourceKey() {
        return dataSourceKey.get();
    }

    /**
     * 绑定当前线程数据源路由的key
     */
    public static void setDataSourceKey(String key) {
        dataSourceKey.set(key);
    }

    /**
     * 删除与当前线程绑定的数据源路由的key
     */
    public static void removeDataSourceKey() {
        dataSourceKey.remove();
    }

    /**
     * 获取当前线程的租户Id
     */
    public static String getTenantId() {
        return tenantId.get();
    }

    /**
     * 绑定当前线程租户Id
     */
    public static void setTenantId(String key) {
        tenantId.set(key);
    }

    /**
     * 删除与当前线程绑定的租户Id
     */
    public static void removeTenantId() {
        tenantId.remove();
    }
}

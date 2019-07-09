package net.chinahrd.utils;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

/**
 * <p>
 *  轻便的同步锁工具
 * </p>
 *
 * @author bright
 * @since 2019/3/19 15:03
 */
public class SyncLockUtil {
    private static final Interner<String> pool = Interners.newWeakInterner();

    /**
     * 构建基于String的intern值同步锁
     * @param prefix
     * @param flag
     * @param key
     * @return
     */
    public static String buildStringLock(String prefix, String flag, Object key) {
        return pool.intern(prefix + flag + key);
    }
}

package net.chinahrd.common.auth;

import net.chinahrd.common.CurrentUser;

/**
 * <p>
 *  当前线程持有的通用的登录实体信息
 * </p>
 *
 * @author bright
 * @since 2019/3/12 18:25
 */
public abstract class CurrentUserHolder {
    private static ThreadLocal<CurrentUser>  local = new ThreadLocal<>();

    public static void set(CurrentUser currentUser) {
        local.set(currentUser);
    }

    public static CurrentUser get() {
        return local.get();
    }

    public static void remove() {
        local.remove();
    }
}

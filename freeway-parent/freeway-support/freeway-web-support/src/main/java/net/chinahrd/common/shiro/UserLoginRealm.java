package net.chinahrd.common.shiro;

import net.chinahrd.response.RbacUserRes;
import net.chinahrd.service.rbac.SecurityService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * @author wzmo
 * <p>
 * 业务模块管理验证 多个验证器：类名应User开头
 */
public class UserLoginRealm extends AuthorizingRealm {

    //@Autowired
    @Resource
    private SecurityService realmService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }
    /**
     * 授权认证
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 登录认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //获取用户的输入的账号.
        String userName = (String) upToken.getPrincipal();
        RbacUserRes rbacUser = realmService.selectByUserName(userName);
        if (rbacUser == null) {
            throw new UnknownAccountException("用户名或密码错误!");
        }
        if (rbacUser.getState() != null && rbacUser.getState() == 1) { //账户冻结
            throw new LockedAccountException();
        }

        ByteSource credentialsSalt = ByteSource.Util.bytes(rbacUser.getUserName());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                rbacUser, //用户名
                rbacUser.getPassword(), //密码
                credentialsSalt,//盐值
                getName()  //realm name
        );
        return authenticationInfo;
    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

}
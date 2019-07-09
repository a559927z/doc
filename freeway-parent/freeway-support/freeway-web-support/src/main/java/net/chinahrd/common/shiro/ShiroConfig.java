package net.chinahrd.common.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置类
 *
 * @author wzmo
 */
@Slf4j
@Configuration
public class ShiroConfig {
    // 获取application.properties参数
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    //@Autowired
    @Resource
    private AuthcFilter authcFilter;

    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * shiro的拦截器
     *
     * @return
     **/
    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authcUserFilter", authcUserFilter());
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 注意过滤器配置顺序 不能颠倒
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/favicon**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/loginCheck", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/test/**", "anon");
        filterChainDefinitionMap.put("/login**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/files/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        // filterChainDefinitionMap.put("/api/**", "statelessAuthc");
        // filterChainDefinitionMap.put("/**", "anon");
        filterChainDefinitionMap.put("/ws/**", "anon");
        filterChainDefinitionMap.put("/**", "authcUserFilter");
        // 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        // shiroFilterFactoryBean.setLoginUrl("/user/unlogin");
        // 登录成功后要跳转的链接 自行处理。不用shiro进行跳转
        // shiroFilterFactoryBean.setSuccessUrl("user/index");
        // 未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/user/unauth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器（由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(Sha512Hash.ALGORITHM_NAME);// 散列算法:这里使用sha-512算法;
        hashedCredentialsMatcher.setHashIterations(1024);// 散列的次数，比如散列两次，相当于
        // md5( md5(""));
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(false);// 默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        return hashedCredentialsMatcher;
    }

    /**
     * 用shiro-redis插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    private RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        // redisManager.setExpire(1800);// 配置缓存过期时间
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        return redisManager;
    }

    @Bean
    @DependsOn(value = {"lifecycleBeanPostProcessor", "redisCacheManager"})
    public UserLoginRealm userLoginRealm() {
        UserLoginRealm shiroRealm = new UserLoginRealm();
        shiroRealm.setCachingEnabled(true);
        shiroRealm.setCacheManager(redisCacheManager());
        //// 设置认证密码算法及迭代复杂度
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        // 关闭登陆用户信息缓存
        // shiroRealm.setAuthenticationCachingEnabled(false);
        // shiroRealm.setAuthorizationCachingEnabled(false);
        return shiroRealm;
    }



    /**
     * shiro 用户数据注入
     * DefaultWebSecurityManager
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 SecurityManager
        securityManager.setRealm(userLoginRealm());
        // 配置 缓存管理类 cacheManager，这个cacheManager必须要在前面执行
        securityManager.setCacheManager(redisCacheManager());
        // 配置 sessionManager
        securityManager.setSessionManager(sessionManager());

        return securityManager;
    }


    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置SessionDao
        sessionManager.setSessionDAO(redisSessionDAO());
        // 设置全局session超时时间
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
        // 删除过期的session
        sessionManager.setDeleteInvalidSessions(true);
        // 加入缓存管理器
        sessionManager.setCacheManager(redisCacheManager());
        // 定时检查session
        sessionManager.setSessionValidationSchedulerEnabled(true);// 是否定时检查session
        return sessionManager;
    }


    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * Shiro生命周期处理器
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    public UserFilter authcUserFilter() {
        UserFilter filter = authcFilter.getUserFilter();
        return filter;
    }

}

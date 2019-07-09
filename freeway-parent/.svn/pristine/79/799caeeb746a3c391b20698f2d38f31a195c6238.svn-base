package net.chinahrd.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * <p>
 *  配置平台数据源
 * </p>
 *
 * @author bright
 * @since 2019/3/15 11:10
 */
@Configuration
@MapperScan(basePackages = "net.chinahrd.platform.**.dao", sqlSessionTemplateRef = "platformSqlSessionTemplate") // 配置mybatis包扫描
public class PlatformDataSource {

    @Autowired
    private Environment env;

    @Bean(name="platformData")
    @Primary
    public DataSource platformDataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSourceBuilder.url(env.getProperty("spring.datasource.url"));
        dataSourceBuilder.username(env.getProperty("spring.datasource.username"));
        dataSourceBuilder.password(env.getProperty("spring.datasource.password"));
        return dataSourceBuilder.build();
    }

    /**
     * 创建会话工厂
     *
     * @param dataSource 数据源
     * @return 会话工厂
     */
    @Bean(name="platformSqlSessionFactory")
    @Primary
    public SqlSessionFactory platformSqlSessionFactory(@Qualifier("platformData") DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        bean.setConfiguration(configuration);
        bean.setPlugins(new Interceptor[]{
                getPageInterceptor(),
                new PerformanceInterceptor(),
                new OptimisticLockerInterceptor()
        });
        return bean.getObject();
    }

    private Interceptor getPageInterceptor() {
        //分页插件
        Interceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        //properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "false");
        properties.setProperty("pageSizeZero", "false");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("params", "count=countSql");
        interceptor.setProperties(properties);
        return interceptor;
    }

    @Bean(name = "platformTransactionManager")
    @Primary
    public DataSourceTransactionManager platformTransactionManager(@Qualifier("platformData") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "platformSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate platformSqlSessionTemplate(@Qualifier("platformSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

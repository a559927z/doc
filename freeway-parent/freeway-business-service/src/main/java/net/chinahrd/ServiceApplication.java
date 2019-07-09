package net.chinahrd;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, PageHelperAutoConfiguration.class})
@DubboComponentScan(basePackages = {"net.chinahrd.modules.**.service.impl","net.chinahrd.platform.**.service.impl"}) // 配置dubbo包扫描
@ComponentScan(basePackages = { "net.chinahrd.*" })
public class ServiceApplication extends SpringBootServletInitializer implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("------------------启动完成！");
    }

    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}

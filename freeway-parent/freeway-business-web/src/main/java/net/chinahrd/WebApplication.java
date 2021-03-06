package net.chinahrd;

import net.chinahrd.common.PageRequest;
import net.chinahrd.common.elasticjob.annotation.EnableElasticJob;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SerializationUtils;

@Configuration
@EnableAutoConfiguration
@EnableElasticJob
@ServletComponentScan
@ComponentScan(basePackages = { "net.chinahrd.*" })
public class WebApplication extends SpringBootServletInitializer implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        PageRequest demoBean = new PageRequest();
        byte[] serialize = SerializationUtils.serialize(demoBean);
        Object deserialize = SerializationUtils.deserialize(serialize);
        System.out.println(WebApplication.class.getClassLoader());
        //注意这里进行了一次类型强转
        System.out.println(deserialize.getClass().getClassLoader());
        System.out.println((PageRequest)deserialize);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("------------------启动完成！");
    }

    @Override //为了打包springboot项目
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}

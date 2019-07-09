package net.chinahrd.modules.demo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import net.chinahrd.common.elasticjob.annotation.ElasticJobConf;

@Slf4j
@ElasticJobConf(name = "demoJobTest1")
public class PlatformJobTest implements SimpleJob {

    @Override
    public void execute(ShardingContext arg0) {
        log.debug("demoJobTest1定时任务启动！");
    }

}

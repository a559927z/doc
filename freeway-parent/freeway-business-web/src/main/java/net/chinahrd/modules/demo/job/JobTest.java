package net.chinahrd.modules.demo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import net.chinahrd.common.elasticjob.annotation.ElasticJobConf;

@Slf4j
@ElasticJobConf(name = "JobTest")
public class JobTest implements SimpleJob {

    @Override
    public void execute(ShardingContext arg0) {
        log.debug("定时任务启动！");
    }

}

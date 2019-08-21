package com.zt.elasticstarter.handler;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zt.annotion.EnableElasticJob;
import com.zt.elasticstarter.listener.MyNormalListener;
import com.zt.elasticstarter.sharding.CustomShardingStrategy;
import lombok.extern.slf4j.Slf4j;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/8/21 10:52
 */
@EnableElasticJob(
        jobName = "myyShardingJob",
        cron = "0/15 * * * * ?",
        shardingTotalCount = 10,
        jobEvent = false,
        overwrite = true,
        jobStrategy = CustomShardingStrategy.class,
        jobListener = {MyNormalListener.class}
)
@Slf4j
public class MyShardingJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("分片项： "+shardingContext.getShardingItem()+"   总分片数： "+shardingContext.getShardingTotalCount());
    }
}

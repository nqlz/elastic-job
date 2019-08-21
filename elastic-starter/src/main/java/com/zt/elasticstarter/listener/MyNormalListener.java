package com.zt.elasticstarter.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/8/21 12:42
 */
@Slf4j
public class MyNormalListener implements ElasticJobListener {
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info("执行作业是："+shardingContexts.getJobName()+"方法前。。。");
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info("执行作业是："+shardingContexts.getJobName()+"方法后！！！");
    }
}

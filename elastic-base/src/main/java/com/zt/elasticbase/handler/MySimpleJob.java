package com.zt.elasticbase.handler;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/8/19 14:25
 */
@Slf4j
public class MySimpleJob implements SimpleJob {


    @Override
    public void execute(ShardingContext shardingContext) {

        log.info("当前分片项：{}",shardingContext.getShardingItem());
        log.info("总分片项：{}",shardingContext.getShardingTotalCount());
    }



}

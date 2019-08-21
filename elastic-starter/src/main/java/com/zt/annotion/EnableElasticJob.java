package com.zt.annotion;

import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.api.strategy.JobShardingStrategy;
import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/8/19 22:14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface EnableElasticJob {

    /**
     * 作业名称
     */
     String jobName() default "";
    /**
     * 表达式
     */
     String cron() default "";

    /**
     * 总分片数
     */
     int shardingTotalCount() default 1;

    /**
     * 每次重置配置覆盖zk中的配置
     * @return
     */
     boolean overwrite() default false;

    /**
     * 自定义分片策略
     * @return
     */
     Class<? extends JobShardingStrategy> jobStrategy() default AverageAllocationJobShardingStrategy.class;

    /**
     * 任务类型
     * @return
     */
     JobType jobType() default  JobType.simpleJob;

    /**
     * 工作流持续
     * @return
     */
    boolean streamingProcess() default false;

    /**
     * 是否实现追踪
     * @return
     */
    boolean jobEvent() default  false;

    /**
     * 作业监听器
     * @return
     */
    Class<? extends ElasticJobListener>[] jobListener() default {};
}

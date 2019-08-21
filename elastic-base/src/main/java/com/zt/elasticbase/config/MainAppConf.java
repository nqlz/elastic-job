package com.zt.elasticbase.config;

import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.zt.elasticbase.handler.MyDataflowJob;
import com.zt.elasticcommon.model.JobProp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.zt.elasticbase.config.JobConfiguration.createJobConfiguration;
import static com.zt.elasticbase.config.JobConfiguration.createRegistryCenter;


/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/8/19 14:29
 */
@Configuration
@ComponentScan("com.zt.elasticbase")
public class MainAppConf {


    /**
     * 启动时调用作业调度器.
     * @return
     */
//    @Bean
    public CommandLineRunner simpleJobSche() {
        System.out.println("执行任务开始=======");
        String jobName = "demoSimpleJob";
        String cron = "0/15 * * * * ?";
        int shardingTotalCount = 10;
        JobProp prop = getProp(jobName, cron, shardingTotalCount,1, MyDataflowJob.class);
        return (String... args) ->
                new JobScheduler(createRegistryCenter(),
                        createJobConfiguration(prop)).init();
    }

    @Bean
    public CommandLineRunner dataflowJobSche() {
        System.out.println("执行任务开始=======");
        String jobName = "demoDataflowJob";
        String cron = "0/10 * * * * ?";
        int shardingTotalCount = 2;
        JobProp prop = getProp(jobName, cron, shardingTotalCount,2,MyDataflowJob.class);
        return (String... args) ->
                new JobScheduler(createRegistryCenter(),
                        createJobConfiguration(prop)).init();
    }

    private JobProp getProp(String jobName, String cron, int shardingTotalCount,Integer type,Class objClass) {
        return JobProp.builder().jobName(jobName)
                .cron(cron).shardingTotalCount(shardingTotalCount)
                .canonicalName(objClass.getCanonicalName())
                .type(type).build();
    }


}

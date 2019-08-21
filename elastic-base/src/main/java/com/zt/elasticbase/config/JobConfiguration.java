package com.zt.elasticbase.config;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.zt.elasticcommon.model.JobProp;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述:作业配置
 *
 * @author: MR.zt
 * @date: 2019/8/12 14:06
 */
@Configuration
public class JobConfiguration {
    /**
     * 配置Zookeeper注册中心
     * @return
     */
    public static CoordinatorRegistryCenter createRegistryCenter() {
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration("192.168.31.156:2181", "elastic-job-demo");
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zkConfig);
        regCenter.init();
        return regCenter;
    }

    public static LiteJobConfiguration createJobConfiguration(JobProp jobProp) {

        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig =
                JobCoreConfiguration.newBuilder(jobProp.getJobName(), jobProp.getCron(), jobProp.getShardingTotalCount()).build();

        // 定义SIMPLE类型配置,getCanonicalName全包名

        JobTypeConfiguration conf = getJobTypeConf(jobProp,simpleCoreConfig);

        // 定义Lite作业根配置
        // overwrite每次启动都会覆盖zk中的配置
       LiteJobConfiguration simpleJobRootConfig =
                LiteJobConfiguration.newBuilder(conf)
                        .overwrite(true).build();

        return simpleJobRootConfig;
    }

    private static JobTypeConfiguration getJobTypeConf(JobProp prop,JobCoreConfiguration simpleCoreConfig) {
        String canonicalName = prop.getCanonicalName();
        switch (prop.getType()){
            case 1:{

                return new SimpleJobConfiguration(simpleCoreConfig, canonicalName);
            }
            case 2:{
                return new DataflowJobConfiguration(simpleCoreConfig,canonicalName,true);
            }
            default:return null;
        }
    }

}

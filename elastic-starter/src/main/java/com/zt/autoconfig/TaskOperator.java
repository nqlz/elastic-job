package com.zt.autoconfig;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.zt.annotion.EnableElasticJob;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 功能描述:任务操作公共类
 *
 * @author: MR.zt
 * @date: 2019/8/19 23:08
 */
public class TaskOperator {

    public static LiteJobConfiguration getLiteJobConfiguration(EnableElasticJob enableElasticJob, String canonicalName) {
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig =
                JobCoreConfiguration.newBuilder(enableElasticJob.jobName(), enableElasticJob.cron(), enableElasticJob.shardingTotalCount()).build();


        // 定义SIMPLE类型配置,getCanonicalName全包名

        JobTypeConfiguration conf = getJobTypeConf(simpleCoreConfig, enableElasticJob, canonicalName);

        // 定义Lite作业根配置
        // overwrite每次启动都会覆盖zk中的配置
        return LiteJobConfiguration.newBuilder(conf)
                .jobShardingStrategyClass(enableElasticJob.jobStrategy().getCanonicalName())
                .overwrite(enableElasticJob.overwrite()).build();
    }

    private static JobTypeConfiguration getJobTypeConf(JobCoreConfiguration simpleCoreConfig, EnableElasticJob enableElasticJob, String canonicalName) {
        switch (enableElasticJob.jobType().getValue()) {
            case 1: {
                return new SimpleJobConfiguration(simpleCoreConfig, canonicalName);
            }
            case 2: {
                return new DataflowJobConfiguration(simpleCoreConfig, canonicalName, enableElasticJob.streamingProcess());
            }
            default:
                return new ScriptJobConfiguration(simpleCoreConfig, canonicalName);
        }
    }

    public static void domainScheduler(ApplicationContext applicationContext,
                                       CoordinatorRegistryCenter registryCenter, DataSource dataSource) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(EnableElasticJob.class);

        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();
            Class<?>[] interfaces = instance.getClass().getInterfaces();
            String canonicalName = instance.getClass().getCanonicalName();

            for (Class superInterface : interfaces) {
                boolean assignableFrom = ElasticJob.class.isAssignableFrom(superInterface);
                if (assignableFrom) {
                    EnableElasticJob annotation = instance.getClass().getAnnotation(EnableElasticJob.class);
                    LiteJobConfiguration simpleJobRootConfig = getLiteJobConfiguration(annotation, canonicalName);


                    Class<? extends ElasticJobListener>[] classes = annotation.jobListener();
                    int length = classes.length;
                    boolean lisNotNull = length > 0;
                    ElasticJobListener[] jobListeners = lisNotNull ? new ElasticJobListener[length] : null;
                    if (lisNotNull) {
                        int i = 0;
                        for (Class<? extends ElasticJobListener> aClass : classes) {
                            ElasticJobListener liInstance = aClass.getDeclaredConstructor().newInstance();
                            jobListeners[i] = liInstance;
                        }
                    }
                    JobEventRdbConfiguration jec = annotation.jobEvent() ? new JobEventRdbConfiguration(dataSource) : null;

                    boolean jecNotNull = !Objects.isNull(jec);

                    if (jecNotNull && lisNotNull) {
                        new SpringJobScheduler((com.dangdang.ddframe.job.api.ElasticJob) instance, registryCenter, simpleJobRootConfig, jec, jobListeners).init();
                    } else if (jecNotNull) {
                        new SpringJobScheduler((com.dangdang.ddframe.job.api.ElasticJob) instance, registryCenter, simpleJobRootConfig, jec).init();
                    } else if (lisNotNull) {
                        new SpringJobScheduler((com.dangdang.ddframe.job.api.ElasticJob) instance, registryCenter, simpleJobRootConfig, jobListeners).init();
                    }else {
                        new SpringJobScheduler((com.dangdang.ddframe.job.api.ElasticJob) instance, registryCenter, simpleJobRootConfig).init();
                    }

                }
        }
        }
    }
}

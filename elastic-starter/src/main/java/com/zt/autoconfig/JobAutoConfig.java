package com.zt.autoconfig;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;

/**
 * 功能描述:Job任务自动配置
 *
 * @author: MR.zt
 * @date: 2019/8/19 22:24
 */
@Configuration
@ConditionalOnBean(CoordinatorRegistryCenter.class)
@AutoConfigureAfter(ZookeeperAutoConfig.class)
public class JobAutoConfig {

    /**
     * 注入spring上下文
     */
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CoordinatorRegistryCenter registryCenter;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initSimpleJob() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TaskOperator.domainScheduler(applicationContext,registryCenter,dataSource);
    }

}

package com.zt.autoconfig;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/8/19 21:44
 */
@Configuration
@ConditionalOnProperty("elasticjob.zookeeper.server-list")
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZookeeperAutoConfig {

    @Autowired
    private ZookeeperProperties zookeeperProperties;

    /**
     * 配置Zookeeper注册中心
     *
     * @return
     */
    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter createRegistryCenter() {
        String serverList = zookeeperProperties.getServerList();
        String nameSpace = zookeeperProperties.getNameSpace();
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(serverList, nameSpace);
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zkConfig);
        return regCenter;
    }


}

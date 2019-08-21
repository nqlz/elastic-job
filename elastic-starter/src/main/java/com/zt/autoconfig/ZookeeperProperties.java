package com.zt.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/8/19 16:48
 */
@ConfigurationProperties(prefix = "elasticjob.zookeeper")
@Data
public class ZookeeperProperties {
    /**
     * 地址列表
     */
    private String serverList;
    /**
     * 命名空间
     */
    private String nameSpace;

}

package com.zt.elasticcommon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/8/19 15:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobProp {
    /**
     * 作业名称
     */
    private String jobName;
    /**
     * 全包名
     */
    private String canonicalName;
    /**
     * 表达式
     */
    private String cron;
    /**
     * 总分片数
     */
    private Integer shardingTotalCount;
    /**
     * 作业类型
     */
    private Integer type;
    /**
     * 每次重置配置覆盖zk中的配置
     */
    private boolean overwrite;
    /**
     * 工作流循环
     */
    private boolean streamingProcess;
}

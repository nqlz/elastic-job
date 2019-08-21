package com.zt.annotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/7/31 12:44
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum JobType {
    /**
     * simpleJob任务
     */
    simpleJob("simpleJob",1),
    /**
     * DataflowJob任务
     */
    dataFlowJob("dataFlowJob",2),
    /**
     * script任务
     */
    scriptJob("scriptJob",3),
    ;
    private String message;
    private Integer value;

}
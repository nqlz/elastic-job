package com.zt.elasticcommon.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author MR.zt
 * @since 2019-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TJobStatusTraceLog extends Model<TJobStatusTraceLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    /**
     * 作业名称
     */
    @TableField("job_name")
    private String jobName;

    /**
     * 原任务名称
     */
    @TableField("original_task_id")
    private String originalTaskId;

    /**
     * 任务名称
     */
    @TableField("task_id")
    private String taskId;

    /**
     * 作业执行服务器IP
     */
    @TableField("slave_id")
    private String slaveId;

    /**
     * 执行源
     */
    @TableField("source")
    private String source;

    /**
     * 任务执行类型
     */
    @TableField("execution_type")
    private String executionType;

    /**
     * 分片项集合
     */
    @TableField("sharding_item")
    private String shardingItem;

    /**
     * 执行状态
     */
    @TableField("state")
    private String state;

    /**
     * 相关信息
     */
    @TableField("message")
    private String message;

    @TableField("creation_time")
    private LocalDateTime creationTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

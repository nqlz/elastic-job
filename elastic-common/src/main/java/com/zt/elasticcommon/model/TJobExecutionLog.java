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
public class TJobExecutionLog extends Model<TJobExecutionLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    /**
     * 作业名称
     */
    @TableField("job_name")
    private String jobName;

    /**
     * 任务名称
     */
    @TableField("task_id")
    private String taskId;

    /**
     * 主机名称
     */
    @TableField("hostname")
    private String hostname;

    /**
     * 主机IP
     */
    @TableField("ip")
    private String ip;

    /**
     * 分片项
     */
    @TableField("sharding_item")
    private Integer shardingItem;

    /**
     * 任务执行类型
     */
    @TableField("execution_source")
    private String executionSource;

    /**
     * 执行失败原因
     */
    @TableField("failure_cause")
    private String failureCause;

    /**
     * 是否执行成功
     */
    @TableField("is_success")
    private Integer isSuccess;

    /**
     * 开始执行时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 完成执行时间
     */
    @TableField("complete_time")
    private LocalDateTime completeTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

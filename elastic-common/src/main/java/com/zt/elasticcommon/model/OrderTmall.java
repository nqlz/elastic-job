package com.zt.elasticcommon.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 模拟天猫订单
 * </p>
 *
 * @author MR.zt
 * @since 2019-08-20
 */
@Data
@TableName("t_order_tmall")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTmall extends Model<OrderTmall> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 状态：0未抓取 ，1 已抓取
     */
    @TableField("tm_status")
    private Integer tmStatus;

    @TableField("money")
    private BigDecimal money;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value="updateTime",fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

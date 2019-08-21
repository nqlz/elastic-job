package com.zt.elasticcommon.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author MR.zt
 * @since 2019-08-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_order")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 流水号
     */
    @TableField("code")
    private String code;

    /**
     * 订单类型：1实体销售,2网络销售
     */
    @TableField("type")
    private Integer type;

    /**
     * 零售店ID
     */
    @TableField("shop_id")
    private long shopId;

    /**
     * 会员ID
     */
    @TableField("customer_id")
    private long customerId;

    /**
     * 总金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 支付方式：1借记卡,2信用卡,3微信,4支付宝,5现金
     */
    @TableField("payment_type")
    private Integer paymentType;

    /**
     * 状态：1未付款,2已付款,3已发货,4已签收
     */
    @TableField("status")
    private Integer status;

    /**
     * 邮费
     */
    @TableField("postage")
    private BigDecimal postage;

    /**
     * 重量（克）
     */
    @TableField("weight")
    private Integer weight;

    /**
     * 购物券ID
     */
    @TableField("voucher_id")
    private Integer voucherId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 逻辑删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

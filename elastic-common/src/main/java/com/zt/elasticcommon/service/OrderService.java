package com.zt.elasticcommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.elasticcommon.model.Order;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author MR.zt
 * @since 2019-08-20
 */
public interface OrderService extends IService<Order> {

    void processOrder(Order order);
}

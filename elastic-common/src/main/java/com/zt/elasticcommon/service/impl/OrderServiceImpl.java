package com.zt.elasticcommon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.elasticcommon.dao.OrderDao;
import com.zt.elasticcommon.dao.TOrderJdDao;
import com.zt.elasticcommon.dao.TOrderTmallDao;
import com.zt.elasticcommon.model.Order;
import com.zt.elasticcommon.model.OrderJd;
import com.zt.elasticcommon.model.OrderTmall;
import com.zt.elasticcommon.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author MR.zt
 * @since 2019-08-20
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {
    @Autowired
    private TOrderJdDao jdDao;
    @Autowired
    private TOrderTmallDao tmallDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processOrder(Order order) {
        save(order);
        switch (order.getType()){
            case 1:{
                OrderJd jd = OrderJd.builder().id(Long.valueOf(order.getCustomerId()))
                        .status(1)
                        .build();
                jdDao.updateById(jd);
                break;
            }
            case 2:{
                OrderTmall tmall = OrderTmall.builder().id(Long.valueOf(order.getCustomerId()))
                        .tmStatus(1)
                        .build();
                tmallDao.updateById(tmall);
                break;
            }
            default:break;
        }
    }
}

package com.zt.elasticstarter.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zt.elasticcommon.model.Order;
import com.zt.elasticcommon.service.OrderService;
import com.zt.elasticstarter.thread.OrderThreadPoolConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/8/20 15:16
 */
//@EnableElasticJob(
//        jobName = "OrderCancelJob",
//        cron = "0/15 * * * * ?",
//        overwrite = true,
//        shardingTotalCount = 2
//)
@Slf4j
public class OrderCancelJob implements SimpleJob {

    @Autowired
    private OrderService orderService;

    private static ExecutorService orderPool;

    @Autowired
    private OrderThreadPoolConf orderThreadPoolConf;

    @Override
    public void execute(ShardingContext shardingContext) {

        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, -30);

        LocalDateTime targetTime = LocalDateTime.now().minusSeconds(30);
        //订单号 % 分片总数 == 当前分片项

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        int shardingTotalCount = shardingContext.getShardingTotalCount();
        int shardingItem = shardingContext.getShardingItem();
        queryWrapper.lambda()
                .le(Order::getCreateTime, targetTime)
                .eq(Order::getStatus, 1)
                .inSql(Order::getId, "select id from t_order where (id % " + shardingTotalCount + ")=" + shardingItem)
        ;
        List<Order> orders = orderService.list(queryWrapper);
        if (!CollectionUtils.isEmpty(orders)) {
            for (Order item:orders) {
                orderPool.execute(()->{
                    Integer id = item.getId();
                    Integer status = item.getStatus();

                    //取消订单状态
                    int canStatus = 5;
                    QueryWrapper<Order> upQuery = new QueryWrapper<>();
                    upQuery.lambda()
                            .eq(Order::getStatus, status)
                            .eq(Order::getId, id);
                    item.setStatus(canStatus);
                    orderService.update(item, upQuery);
                });
            }


        }


    }

    @PostConstruct
    public void init() {
        orderPool = orderThreadPoolConf.getInstance();
        if(orderPool == null) {
            log.error("【order】向订单服务注入订单线程池失败");
            throw new RuntimeException("【order】向订单服务注入订单线程池失败");
        }
    }
}

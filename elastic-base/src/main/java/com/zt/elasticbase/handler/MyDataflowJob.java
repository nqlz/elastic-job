package com.zt.elasticbase.handler;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.zt.elasticcommon.model.Order;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/8/19 14:59
 */
@Slf4j
public class MyDataflowJob implements DataflowJob<Order> {

    @Getter
    private List<Order> orders;

    {
        orders = creatOrders();
    }

    private List<Order> creatOrders() {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Order order = Order.builder()
                    .id(i + 1)
                    .status(0).build();
            orders.add(order);
        }
        return orders;
    }

    /**
     * 抓取数据
     *
     * @param shardingContext
     * @return
     */
    @Override
    public List<Order> fetchData(ShardingContext shardingContext) {

        //订单号 % 分片总数 == 当前分片项
        int shardingItem = shardingContext.getShardingItem();
        List<Order> orderList = orders.stream().filter(item -> item.getStatus() == 0)
                .filter(o -> o.getId() % shardingContext.getShardingTotalCount() == shardingItem)
                .collect(Collectors.toList());
        List<Order> subOdList = null;
        if (!CollectionUtils.isEmpty(orderList)) {
            subOdList = orderList.subList(0, 10);
        }

        sleepSth(3000);
        log.info(LocalDateTime.now()+"我是分片项： "+ shardingItem+",抓取的数据为：  "+subOdList);

        return subOdList;
    }

    /**
     * 处理数据
     *
     * @param shardingContext
     * @param data
     */
    @Override
    public void processData(ShardingContext shardingContext, List<Order> data) {

        data.forEach(order -> order.setStatus(1));
        sleepSth(5000);
        log.info(LocalDateTime.now()+"我是分片项： "+ shardingContext.getShardingItem()+",正在处理了数据。 ");

    }

    private void sleepSth(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

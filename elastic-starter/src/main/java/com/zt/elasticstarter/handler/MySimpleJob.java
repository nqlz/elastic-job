package com.zt.elasticstarter.handler;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zt.elasticcommon.model.Order;
import com.zt.elasticcommon.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 功能描述:模拟产生订单
 *
 * @author: MR.zt
 * @date: 2019/8/19 14:25
 */
@Slf4j
//@EnableElasticJob(
//        jobName = "demoSimpleJob",
//        cron = "0/15 * * * * ?",
//        overwrite = true
//)
public class MySimpleJob implements SimpleJob {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(ShardingContext shardingContext) {

        for (int i = 0; i < 10; i++) {
            Order order = Order.builder().status(1)
                    .type(1)
                    .amount(BigDecimal.valueOf(200))
                    .code(createOrderCode("S","23"+i,"56", LocalDate.now().toString())).createTime(LocalDateTime.now())
                    .customerId(1).paymentType(1)
                    .isDeleted(false).postage(BigDecimal.valueOf(10))
                    .shopId(2).voucherId(5)
                    .weight(11).build();
            orderService.save(order);
        }

        log.info("当前分片项：{}",shardingContext.getShardingItem()+"     总分片项："+shardingContext.getShardingTotalCount());
    }

    public static String createOrderCode(String type, String organizationId, String spgId, String date){
        StringBuilder builder = new StringBuilder();

        builder.append(type)
                .append(organizationId)
                .append(spgId)
                .append(date);
        ThreadLocalRandom.current().ints(0,9).limit(10);
        new Random().ints(0, 9).limit(10)
                .forEach(item-> builder.append(item));
        return builder.toString();
    }

}

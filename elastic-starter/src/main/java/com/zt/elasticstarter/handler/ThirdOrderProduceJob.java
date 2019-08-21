package com.zt.elasticstarter.handler;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zt.elasticcommon.model.OrderJd;
import com.zt.elasticcommon.model.OrderTmall;
import com.zt.elasticcommon.service.OrderJdService;
import com.zt.elasticcommon.service.OrderTmallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 功能描述:模拟第三方订单生产
 *
 * @author: MR.zt
 * @date: 2019/8/20 20:52
 */
//@EnableElasticJob(
//        jobName = "thirdOrderProduceJob",
//        cron = "0/15 * * * * ?",
//        overwrite = true
//)
@Slf4j
public class ThirdOrderProduceJob implements SimpleJob {


    @Autowired
    private OrderJdService jdService;

    @Autowired
    private OrderTmallService tmallService;

    @Override
    public void execute(ShardingContext shardingContext) {
        createThirdOrders();
    }

    private void createThirdOrders() {
        for (int i = 0; i < 5; i++) {

            int random = ThreadLocalRandom.current().nextInt(2);

            //京东订单
            if (random == 0){
                log.info("插入京东订单");
                OrderJd orderJd = OrderJd.builder()
                        .status(0)
                        .amount(BigDecimal.TEN)
                        .build();
                jdService.save(orderJd);
            }
            //天猫订单
            else {
                log.info("插入天猫订单");
                OrderTmall tMall = OrderTmall.builder()
                        .money(BigDecimal.TEN)
                        .tmStatus(0)
                        .build();
                tmallService.save(tMall);

            }
        }
    }


}

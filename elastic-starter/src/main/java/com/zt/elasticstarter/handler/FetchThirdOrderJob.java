package com.zt.elasticstarter.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.zt.elasticcommon.model.Order;
import com.zt.elasticcommon.model.OrderJd;
import com.zt.elasticcommon.model.OrderTmall;
import com.zt.elasticcommon.service.OrderJdService;
import com.zt.elasticcommon.service.OrderService;
import com.zt.elasticcommon.service.OrderTmallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * 功能描述:模拟抓取第三方订单，京东，天猫订单并处理
 *
 * @author: MR.zt
 * @date: 2019/8/19 14:59
 */
@Slf4j
//@EnableElasticJob(
//        jobName = "fetchThirdOrderJob",
//        cron = "0/15 * * * * ?",
//        shardingTotalCount = 2,
//        overwrite = true,
//        streamingProcess = true,
//        jobType = JobType.dataFlowJob
//)
public class FetchThirdOrderJob implements DataflowJob {
    @Autowired
    private OrderJdService jdService;

    @Autowired
    private OrderTmallService tmallService;

    @Autowired
    private OrderService orderService;


    @Override
    public List fetchData(ShardingContext shardingContext) {
        //京东订单
        if (shardingContext.getShardingItem() == 0) {
            log.info("抓取京东订单");
            return getJDList();
        }
        //天猫订单
        else {
            log.info("抓取天猫订单");
            return getTMallList();
        }

    }

    private List getTMallList() {
        QueryWrapper<OrderTmall> tMall = new QueryWrapper<>();
        tMall.lambda().eq(OrderTmall::getTmStatus, 0).last("LIMIT 5");
        List<OrderTmall> orderTmalls = tmallService.list(tMall);
        return CollectionUtils.isEmpty(orderTmalls) ? null : orderTmalls;
    }

    private List getJDList() {
        QueryWrapper<OrderJd> jd = new QueryWrapper<>();
        jd.eq("status", 0).last("LIMIT 5");
        List<OrderJd> orderJds = jdService.list(jd);
        return !CollectionUtils.isEmpty(orderJds) ? orderJds : null;
    }


    @Override
    public void processData(ShardingContext shardingContext, List data) {
        //京东订单
        if (shardingContext.getShardingItem() == 0) {
            log.info("处理京东订单");
            resolveJDList(data);
        }
        //天猫订单
        else {
            log.info("处理天猫订单");
            resolveTMallList(data);
        }
    }

    private void resolveTMallList(List data) {
        List<OrderTmall> orderTmalls = (List<OrderTmall>) data;
        orderTmalls.parallelStream().forEach(item->{
            Order order = Order.builder()
                    .customerId(item.getId())
                    .type(2)
                    .paymentType(1)
                    .isDeleted(false)
                    .status(1)
                    .code(MySimpleJob.createOrderCode("s","zu","33", LocalDate.now().toString()))
                    .amount(item.getMoney()).build();
            orderService.processOrder(order);
        });

    }

    private void resolveJDList(List data) {
        List<OrderJd> orderJds = (List<OrderJd>) data;
        orderJds.parallelStream().forEach(item->{
            Order order = Order.builder()
                    .customerId(item.getId())
                    .type(1)
                    .paymentType(1)
                    .isDeleted(false)
                    .status(1)
                    .code(MySimpleJob.createOrderCode("s","zu","33", LocalDate.now().toString()))
                    .amount(item.getAmount()).build();
            orderService.processOrder(order);
        });
    }
}

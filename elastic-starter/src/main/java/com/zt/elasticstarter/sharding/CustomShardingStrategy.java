package com.zt.elasticstarter.sharding;

import com.dangdang.ddframe.job.lite.api.strategy.JobInstance;
import com.dangdang.ddframe.job.lite.api.strategy.JobShardingStrategy;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 功能描述:自定义分片策略
 * AverageAllocationJobShardingStrategy 基于平均分配算法的分片策略.
 *
 * @author: MR.zt
 * @date: 2019/8/21 9:57
 */
public class CustomShardingStrategy implements JobShardingStrategy {


    @Override
    public Map<JobInstance, List<Integer>> sharding(List<JobInstance> jobInstances,
                                                    String jobName, int shardingTotalCount) {
        Map<JobInstance, List<Integer>> rtnMap = new HashMap<>();

        ArrayDeque<Integer> arrayQueue = new ArrayDeque<>(shardingTotalCount);

        for (int i = 0; i < shardingTotalCount; i++) {
            arrayQueue.add(i);
        }

        while (arrayQueue.size() > 0) {
            for (JobInstance jobInstance : jobInstances) {

               if (arrayQueue.size()>0){
                   Integer shardingItem = arrayQueue.pop();
                   List<Integer> list = rtnMap.get(jobInstance);
                   if (!CollectionUtils.isEmpty(list)) {
                       list.add(shardingItem);
                   } else {
                       List<Integer> integerList = new ArrayList<>();
                       integerList.add(shardingItem);
                       rtnMap.put(jobInstance, integerList);
                   }
               }
            }
        }

        return rtnMap;

    }
}

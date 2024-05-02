package com.zhaoli.pay.job.handler;

import com.google.common.collect.Lists;
import com.zhaoli.pay.job.entity.AggregateAmountDO;
import com.zhaoli.pay.job.service.PaymentJobDailyReportService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 余胜军
 * @ClassName PaymentDailyReportHandler
 */
@Component
@Slf4j
public class PaymentDailyReportHandler {
    @Value("${server.port}")
    private String serverPort;
    @Autowired
    private PaymentJobDailyReportService paymentJobDailyReportService;
    private static final Integer PARTITION_SIZE = 1;

    @XxlJob("payDailyReportHandler")
    public void payDailyReport() {
        // 获取到 定时任务 对应在注册中心 index位置
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        log.info("shardIndex:{},shardTotal:{}",
                shardIndex, shardTotal);
        // 1.查询昨日的商户成交金额（分页）
        List<AggregateAmountDO> aggregateAmountList = paymentJobDailyReportService.
                merchantTransactionAmount(shardIndex);
        // 每个定时只会查询 2条，对2条数据分割 成两个不同的线程处理
        List<List<AggregateAmountDO>> aggregateAmountListPartitions
                = Lists.partition(aggregateAmountList, PARTITION_SIZE);
        // 2.多线程拆分数据 异步处理
        for (List<AggregateAmountDO> aggregateAmountListPartition :
                aggregateAmountListPartitions) {
            // 每个批次都是独立线程处理
            paymentJobDailyReportService.
                    asynSendMerchantTransactionAmount(aggregateAmountListPartition);

        }


    }

}

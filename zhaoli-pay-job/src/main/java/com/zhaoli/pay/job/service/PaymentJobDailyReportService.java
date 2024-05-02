package com.zhaoli.pay.job.service;

import com.zhaoli.pay.job.entity.AggregateAmountDO;

import java.util.List;

public interface PaymentJobDailyReportService {

    /**
     * 查询每个商户 昨日交易金额
     *
     * @return
     */
    List<AggregateAmountDO> merchantTransactionAmount(Integer shardIndex);

    /**
     * 多线程异步处理
     */
    void asynSendMerchantTransactionAmount(List<AggregateAmountDO>
                                                   aggregateAmountList
    );
}

package com.zhaoli.pay.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaoli.pay.job.entity.AggregateAmountDO;
import com.zhaoli.pay.job.entity.PaymentInfoDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PaymentInfoMapper extends BaseMapper<PaymentInfoDO> {
    // 根据
    @Select("select merchant_id as merchantId, sum(PAY_AMOUNT) as payAmount from\n" +
            " payment_info where date(CREATED_TIME)=#{yesterdayTime} " +
            "and PAYMENT_STATUS='1' group by merchant_id  limit #{startIndex},2;")
    List<AggregateAmountDO> merchantTransactionAmount(@Param("yesterdayTime") String yesterdayTime,
                                                      @Param("startIndex") Integer startIndex);

}
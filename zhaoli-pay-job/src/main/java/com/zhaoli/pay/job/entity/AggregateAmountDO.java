package com.zhaoli.pay.job.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AggregateAmountDO {
    private String merchantId;
    private BigDecimal payAmount;

}
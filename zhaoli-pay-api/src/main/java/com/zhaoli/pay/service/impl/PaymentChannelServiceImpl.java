package com.zhaoli.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhaoli.pay.entity.PaymentChannelDO;
import com.zhaoli.pay.mapper.PaymentChannelMapper;
import com.zhaoli.pay.service.open.api.IPaymentChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付渠道表 服务实现类
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-24
 */
@Service
public class PaymentChannelServiceImpl extends ServiceImpl<PaymentChannelMapper, PaymentChannelDO> implements IPaymentChannelService {
    @Autowired
    private PaymentChannelMapper paymentChannelMapper;

    @Override
    public PaymentChannelDO getByChannelIdInfo(String channelId) {
        LambdaQueryWrapper<PaymentChannelDO> paymentChannelDOQueryWrapper = new LambdaQueryWrapper<>();
        paymentChannelDOQueryWrapper.eq(PaymentChannelDO::getChannelId, channelId);
        return paymentChannelMapper.selectOne(paymentChannelDOQueryWrapper);
    }
}

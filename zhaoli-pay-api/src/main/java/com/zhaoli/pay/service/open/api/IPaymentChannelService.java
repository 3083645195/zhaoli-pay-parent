package com.zhaoli.pay.service.open.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoli.pay.entity.PaymentChannelDO;
import com.zhaoli.pay.entity.PaymentInfoDO;

/**
 * <p>
 * 支付渠道表 服务类
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-24
 */
public interface IPaymentChannelService extends IService<PaymentChannelDO> {
    /**
     * 根据渠道id 查询渠道信息
     */
    PaymentChannelDO getByChannelIdInfo(String channelId);
}

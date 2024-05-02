package com.zhaoli.pay.service.impl;

import com.zhaoli.pay.entity.PaymentInfoDO;
import com.zhaoli.pay.mapper.PaymentInfoMapper;
import com.zhaoli.pay.service.IPaymentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付交易表  服务实现类
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-24
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfoDO> implements IPaymentInfoService {

}

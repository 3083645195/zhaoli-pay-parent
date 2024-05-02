package com.zhaoli.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaoli.pay.constant.Constant;
import com.zhaoli.pay.controller.open.api.dto.req.PaymentInfoDTO;
import com.zhaoli.pay.entity.PaymentInfoDO;
import com.zhaoli.pay.mapper.PaymentInfoMapper;
import com.zhaoli.pay.service.open.api.OpenPaymentCoreService;
import com.zhaoli.pay.utils.RedisUtils;
import com.zhaoli.pay.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
@Service
public class OpenPaymentCoreServiceImpl implements OpenPaymentCoreService {
    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Override
    @Transactional
    public String createPayToken(PaymentInfoDTO paymentInfoDTO) {
        PaymentInfoDO paymentInfo = new PaymentInfoDO();
        //DTO转化DO
        BeanUtils.copyProperties(paymentInfoDTO, paymentInfo);
        //插入数据
        boolean insertResult = paymentInfoMapper.insert(paymentInfo) > Constant.DB_RESULT_BIGZERO ? Boolean.TRUE : Boolean.FALSE;
        if (!insertResult) {
            return Constant.RESULT_STRING_NULL;
        }
        //生成对应支付令牌
        Integer payId = paymentInfo.getId();
        String payToken = TokenUtils.getToken();
        RedisUtils.setString(Constant.REDIS_PREFIX_PAYTOKEN + payToken, payId);
        return payToken;

    }

    @Override
    public PaymentInfoDO getByPayTokenInfo(String payToken) {
        //在redis中查询 主键 id
        String payIdStr = RedisUtils.getString(Constant.REDIS_PREFIX_PAYTOKEN + payToken);
        if (StringUtils.isEmpty(payIdStr)) {
            return null;
        }
        Integer payId = Integer.valueOf(payIdStr);
        //根据 id 查询支付交易信息
        return paymentInfoMapper.selectById(payId);
    }

    @Override
    public PaymentInfoDO getOrderIdByPaymentInfo(String orderId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", orderId);
        PaymentInfoDO paymentInfoDO = paymentInfoMapper.selectOne(queryWrapper);
        return paymentInfoDO;
    }

    @Override
    @Transactional
    public int updatePaymentStatus(PaymentInfoDO paymentInfo, Integer paymentStatus) {
        paymentInfo.setPaymentStatus(paymentStatus);
        int result = paymentInfoMapper.updateById(paymentInfo);
        return result;
    }

    @Override
    @Transactional
    public int updatePaymentStatusOk(PaymentInfoDO paymentInfo) {
        paymentInfo.setPaymentStatus(Constant.PAY_PAYMENT_COMPLETED);
        int result = paymentInfoMapper.updateById(paymentInfo);
        return result;
    }
}


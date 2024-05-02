package com.zhaoli.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaoli.pay.constant.Constant;
import com.zhaoli.pay.entity.PayMerchantInfoDO;
import com.zhaoli.pay.mapper.PayMerchantInfoMapper;
import com.zhaoli.pay.service.IPayMerchantInfoService;
import com.zhaoli.pay.utils.SnowflakeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商户信息表 服务实现类
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-18
 */
@Service
public class PayMerchantInfoServiceImpl extends ServiceImpl<PayMerchantInfoMapper, PayMerchantInfoDO> implements IPayMerchantInfoService {
    @Autowired
    private PayMerchantInfoMapper payMerchantInfoMapper;

    @Override
    @Transactional
    public boolean addPayMerchantInfo(PayMerchantInfoDO payMerchantInfoDO) {
        payMerchantInfoDO.setAuditStatus(Constant.MERCHANT_UNAPPROVED_STATUS); // 默认状态为0 未审核
        payMerchantInfoDO.setMerchantId(SnowflakeId.getIdStr());//生成一个唯一全局的id
        return payMerchantInfoMapper.insert(payMerchantInfoDO) > Constant.DB_RESULT_BIGZERO ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    @Transactional
    public boolean updateOpenidIsNull(String openId) {
        return payMerchantInfoMapper.updateOpenidIsNull(openId) > Constant.DB_RESULT_BIGZERO ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public PayMerchantInfoDO getBySocialCreditCodePayMerchantInfo(String socialCreditCode) {
        LambdaQueryWrapper<PayMerchantInfoDO> payMerchantInfoDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        payMerchantInfoDOLambdaQueryWrapper.eq(PayMerchantInfoDO::getSocialCreditCode, socialCreditCode);
        return payMerchantInfoMapper.selectOne(payMerchantInfoDOLambdaQueryWrapper);
    }

    @Override
    public PayMerchantInfoDO getByMerchantIdInfo(String merchantId) {
        QueryWrapper<PayMerchantInfoDO> payMerchantInfoDOQueryWrapper = new QueryWrapper<>();
        payMerchantInfoDOQueryWrapper.eq("merchant_id", merchantId);
        PayMerchantInfoDO payMerchantInfo = payMerchantInfoMapper.selectOne(payMerchantInfoDOQueryWrapper);
        return payMerchantInfo;
    }

}

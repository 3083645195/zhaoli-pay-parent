package com.zhaoli.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhaoli.pay.entity.MerchantSecretKeyDO;
import com.zhaoli.pay.mapper.MerchantSecretKeyMapper;
import com.zhaoli.pay.service.IMerchantSecretKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * API接口验证签名表 服务实现类
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-21
 */
@Service
public class MerchantSecretKeyServiceImpl extends ServiceImpl<MerchantSecretKeyMapper, MerchantSecretKeyDO> implements IMerchantSecretKeyService {
    @Autowired
    private MerchantSecretKeyMapper merchantSecretKeyMapper;

    @Override
    public MerchantSecretKeyDO getByAppIdKeyMerchantSecret(String appId, String appKey) {
        LambdaQueryWrapper<MerchantSecretKeyDO> merchantSecretKeyDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        merchantSecretKeyDOLambdaQueryWrapper.eq(MerchantSecretKeyDO::getAppId, appId);
        merchantSecretKeyDOLambdaQueryWrapper.eq(MerchantSecretKeyDO::getAppKey, appKey);
        return merchantSecretKeyMapper.selectOne(merchantSecretKeyDOLambdaQueryWrapper);
    }
}

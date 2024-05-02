package com.zhaoli.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoli.pay.entity.MerchantSecretKeyDO;

/**
 * <p>
 * API接口验证签名表 服务类
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-21
 */
public interface IMerchantSecretKeyService extends IService<MerchantSecretKeyDO> {
    /**
     * 根据 appId 和 appKey 验证商户
     */
    MerchantSecretKeyDO getByAppIdKeyMerchantSecret(String appId,String appKey);
}

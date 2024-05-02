package com.zhaoli.pay.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaoli.pay.entity.PayMerchantInfoDO;

/**
 * <p>
 * 商户信息表 服务类
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-18
 */
public interface IPayMerchantInfoService extends IService<PayMerchantInfoDO> {
    /**
     * 新增企业入驻信息 --- 默认状态是未审核
     *
     * @param payMerchantInfoDO
     * @return
     */
    boolean addPayMerchantInfo(PayMerchantInfoDO payMerchantInfoDO);

    /**
     * 将该 openId 的值设置为空
     */
    boolean updateOpenidIsNull(String openId);
    /**
     * 根据企业社会信用代码号(socialCreditCode)查询企业信息
     */
    PayMerchantInfoDO getBySocialCreditCodePayMerchantInfo(String socialCreditCode);
    /**
     * 根据商户id 查询企业基本信息
     */
    PayMerchantInfoDO getByMerchantIdInfo(String merchantId);
}

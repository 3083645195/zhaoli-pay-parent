package com.zhaoli.pay.mapper;

import com.zhaoli.pay.entity.PayMerchantInfoDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 商户信息表 Mapper 接口
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-18
 */
public interface PayMerchantInfoMapper extends BaseMapper<PayMerchantInfoDO> {
    /**
     * 根据 openid 将数据库中绑定的 contact_wx_openid 设置为空
     */
    @Update("update pay_merchant_info set \n" + "contact_wx_openid=null where contact_wx_openid=#{openid};")
    int updateOpenidIsNull(String openid);

}

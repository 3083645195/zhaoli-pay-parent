package com.zhaoli.pay.dto.req;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商户信息表
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-18
 */
@Data
@TableName("pay_merchant_info")
public class PayMerchantInfoReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 企业名称
     */
    private String enterpriseName;
    /**
     * 社会信用代码
     */
    private String socialCreditCode;
    /**
     * 营业执照图片
     */
    private String businessLicenseUrl;
    /**
     * 法人
     */
    private String juridicalPerson;
    /**
     * 法人身份证信息
     */
    private String juridicalPersonUrl;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 联系人手机号码
     */
    private String contactNumber;
    /**
     * 二维码 token 参数
     */
    private String qrCodeToken;
}

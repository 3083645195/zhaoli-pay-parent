package com.zhaoli.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class PayMerchantInfoDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商户id
     */
    private String merchantId;
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
     * 联系人微信openid
     */
    private String contactWxOpenid;
    /**
     * 审核状态 0未审核1审核通过2审核拒绝
     */
    private Integer auditStatus;
    /**
     * 创建时间
     */
    private LocalDateTime createDate;
    /**
     *
     */
    private LocalDateTime updateDate;
}

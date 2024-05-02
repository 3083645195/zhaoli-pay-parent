package com.zhaoli.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付交易表
 * </p>
 *
 * @author mayikt
 * @since 2023-03-21
 */
@TableName("payment_info")
@Data
public class PaymentInfoDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 支付状态 0待支付1已经支付2支付超时3支付失败
     */
    private Integer paymentStatus;

    /**
     * 商户id
     */
    private String merchantId;

    /**
     * 订单号码 商家给的
     */
    private String orderId;

    /**
     * 乐观锁
     */
    private Integer revision;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 第三方支付id
     */
    private String partyPayId;
    /**
     * 极简支付生成的支付id
     */
    private String paymentId;
    /**
     * 支付渠道
     */
    private String paymentChannel;
    /**
     * 订单名称
     */
    private String orderName;
    /**
     * 订单内容
     */
    private String orderBody;
}

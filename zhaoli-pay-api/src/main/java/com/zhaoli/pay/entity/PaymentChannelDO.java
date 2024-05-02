package com.zhaoli.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 支付渠道表
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-24
 */
@Data
@TableName("payment_channel")
@ApiModel(value = "PaymentChannel对象", description = "支付渠道表")
public class PaymentChannelDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
      @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("渠道名称")
    private String channelName;

    @ApiModelProperty("渠道ID")
    private String channelId;

    @ApiModelProperty("同步回调URL")
    private String syncUrl;

    @ApiModelProperty("异步回调URL")
    private String asynUrl;

    @ApiModelProperty("公钥")
    private String publicKey;

    @ApiModelProperty("私钥")
    private String privateKey;

    @ApiModelProperty("乐观锁")
    private Integer revision;

    @ApiModelProperty("创建人")
    private String createdBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("更新人")
    private String updatedBy;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedTime;
    /**
     * 该支付渠道对应在ioc中bean 的id
     */
    private String payBeanId;

    private String callbackBeanId;

    private Integer isDelete;
}

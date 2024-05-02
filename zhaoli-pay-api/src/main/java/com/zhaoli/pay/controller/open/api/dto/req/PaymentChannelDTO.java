package com.zhaoli.pay.controller.open.api.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
@Data
public class PaymentChannelDTO {
 /**
  * 渠道ID
  */
 private String channelId;
 /**
  * 支付令牌
  */
 private String payToken;
}

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
 * API接口验证签名表
 * </p>
 *
 * @author zhaoli
 * @since 2024-01-21
 */
@Data
@TableName("merchant_secret_key")
@ApiModel(value = "MerchantSecretKey对象", description = "API接口验证签名表")
public class MerchantSecretKeyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("账号")
    private String appId;

    @ApiModelProperty("密钥")
    private String appKey;

    @ApiModelProperty("0正常 ，1停止")
    private Integer disabled;

    @ApiModelProperty("创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateDate;

    @ApiModelProperty("盐值")
    private String saltKey;

    @ApiModelProperty("允许访问路径")
    private String permissionList;


    public MerchantSecretKeyDO() {
    }
}

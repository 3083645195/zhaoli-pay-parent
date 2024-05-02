package com.zhaoli.pay.controller;

import com.zhaoli.common.core.api.base.BaseApiController;
import com.zhaoli.common.core.api.base.BaseResponse;
import com.zhaoli.pay.constant.Constant;
import com.zhaoli.pay.dto.req.PayMerchantInfoReqDTO;
import com.zhaoli.pay.entity.PayMerchantInfoDO;
import com.zhaoli.pay.service.IPayMerchantInfoService;
import com.zhaoli.pay.service.PayMessageTemplateService;
import com.zhaoli.pay.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchant")
@Api(tags = "企业相关接口")
public class PayMerchantInfoController extends BaseApiController {
    @Autowired
    private IPayMerchantInfoService payMerchantInfoService;
    @Autowired
   private PayMessageTemplateService payMessageTemplateService;

    /**
     * 商家入驻接口
     * @return
     */
    @PostMapping("/merchantSettlement")
    @ApiOperation(value = "商家入驻接口", notes = "商家入驻接口")
    public BaseResponse<String> merchantSettlement(@RequestBody PayMerchantInfoReqDTO payMerchantInfoReqDTO) {
        String socialCreditCode = payMerchantInfoReqDTO.getSocialCreditCode();
        if (StringUtils.isEmpty(socialCreditCode)) {
            return setResultError("socialCreditCode is null");
        }
        //根据 socialCreditCode 查询数据库中是否存在该企业的信息
        PayMerchantInfoDO dbPayMerchantInfoDO = payMerchantInfoService.getBySocialCreditCodePayMerchantInfo(socialCreditCode);
        if (dbPayMerchantInfoDO != null) {
            return setResultError("该企业已经提交过数据，请勿重复提交..");
        }
        PayMerchantInfoDO payMerchantInfoDO = new PayMerchantInfoDO();
        //根据token 获取到对应的openId
        String qrCodeToken = payMerchantInfoReqDTO.getQrCodeToken();
        if (!StringUtils.isEmpty(qrCodeToken)) {
            String openId = RedisUtils.getString(Constant.REDIS_PREFIX_QRCODETOKEN + qrCodeToken);//从redis中获取到该 token中对应的 openId
            payMerchantInfoDO.setContactWxOpenid(openId);
        }
        // dto拷贝到do
        BeanUtils.copyProperties(payMerchantInfoReqDTO, payMerchantInfoDO);
        // 插入数据
        boolean insertResult = payMerchantInfoService.addPayMerchantInfo(payMerchantInfoDO);
        if (insertResult) {
            //数据成功插入之后将redis中存入的token删除
            RedisUtils.delKey(Constant.REDIS_PREFIX_QRCODETOKEN + qrCodeToken);
            //发送微信消息推送
            payMessageTemplateService.notifyMerchantSettlementThread(payMerchantInfoDO);
        }
        return insertResult ? setResultSuccess() : setResultError();
    }
}

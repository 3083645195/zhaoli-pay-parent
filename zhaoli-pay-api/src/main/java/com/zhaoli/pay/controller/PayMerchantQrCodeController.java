package com.zhaoli.pay.controller;

import com.zhaoli.common.core.api.base.BaseApiController;
import com.zhaoli.common.core.api.base.BaseResponse;
import com.zhaoli.pay.dto.resp.PayMerchantQrCodeRespDTO;
import com.zhaoli.pay.service.PayMerchantQrCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@Slf4j
//@CrossOrigin
@RequestMapping("/merchant")
@Api(tags = "二维码接口")
public class PayMerchantQrCodeController extends BaseApiController {
    @Autowired
    private PayMerchantQrCodeService payMerchantQrCodeService;

    /**
     * 生成临时二维码
     *
     * @return
     */
    @GetMapping("/generateQrCode")
    @ApiOperation(value = "二维码接口", notes = "生成临时二维码")
    public BaseResponse<PayMerchantQrCodeRespDTO> generateQrCode() {
        PayMerchantQrCodeRespDTO payMerchantQrCodeRespDTO = payMerchantQrCodeService.generateQrCode();
        if (payMerchantQrCodeRespDTO == null) {
            log.error("临时二维码生成失败");
            return setResultError("生成二维码失败!");
        }
//        //解决跨域问题
//        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        resp.setHeader("Access-Control-Allow-Origin", "*");
        return setResultSuccessData(payMerchantQrCodeRespDTO);
    }

    /**
     * 前端根据token定时查询扫码结果
     */
    @GetMapping("/getQrCodeTokenResult")
    @ApiOperation(value = "前端根据token定时查询扫码结果", notes = "前端根据token定时查询扫码结果")
    public BaseResponse<String> getQrCodeTokenResult(String qrCodeToken) {
        if (StringUtils.isEmpty(qrCodeToken)) {
            return setResultError("qrCodenToken is null");
        }
        boolean qrCodeTokenResult = payMerchantQrCodeService.getQrCodeTokenResult(qrCodeToken);
        return qrCodeTokenResult ? setResultSuccess() : setResultError();
    }

}

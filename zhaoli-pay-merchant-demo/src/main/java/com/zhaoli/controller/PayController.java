package com.zhaoli.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhaoli.core.constant.PayConstant;
import com.zhaoli.core.http.RpcZhaoLiPayHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/demo")
@Slf4j
@RestController
public class PayController {
    @Autowired
    private RpcZhaoLiPayHttpClient rpcZhaoLiPayHttpClient;

    /**
     * 该代码为商户端测试代码
     *
     * @return
     */
    @GetMapping("payDemo")
    public Object payDemo() {
        JSONObject paramMap = new JSONObject();
        paramMap.put("orderName", "zhaoli66666");
        String json = paramMap.toJSONString();
        JSONObject result = rpcZhaoLiPayHttpClient.doPostJson(PayConstant.PAY_TO_PAY, json);
        return result;
    }
    /**
     * 该代码为商户端测试代码
     *
     * @return
     */
    @GetMapping("payDemo01")
    public Object payDemo01() {
        JSONObject json = new JSONObject();
        json.put("merchantId", "36136584736586");
        json.put("payAmount", "199.9");
        json.put("orderId", "461321464");
        json.put("orderName", "zhaoli");
        json.put("orderBody", "vip");
        JSONObject result = rpcZhaoLiPayHttpClient.doPostJson("/api/v1/createPayToken", json.toJSONString());
        return result;
    }
}

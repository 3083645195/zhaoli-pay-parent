package com.zhaoli.pay.wechat.handler;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.zhaoli.pay.constant.Constant;
import com.zhaoli.pay.wechat.builder.TextBuilder;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService, WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }
        //获取用户输入的内容
        String userContent = wxMessage.getContent();
        HashMap<String, Object> paramMap = new HashMap<>();
        //存入官方提供的测试key
        paramMap.put("key", "free");//类似于密码
        paramMap.put("appid", "0");//类似于账号
        paramMap.put("msg", userContent);//用户的问题
        try {
            //调用第三方智能接口 获取关键字匹配 返回样式 {"result":0,"content":"你好，我就开心了"}
            String result = HttpUtil.get("http://api.qingyunke.com/api.php", paramMap);
            logger.info("result:{}", result);
            if (StringUtils.isEmpty(result)) {
                return setResult("系统忙，请稍后重试！", wxMessage, weixinService);
            }
            JSONObject json = JSONObject.parseObject(result);
            Integer code = json.getInteger("result");
            if (!Constant.QINGYUNKE_RESULT_OK.equals(code)) {
                return setResult("系统忙，请稍后重试！", wxMessage, weixinService);
            }
            //调用接口成功 获取第三方接口中响应的内容
            String resultContent = json.getString("content");
            return setResult(resultContent, wxMessage, weixinService);
        } catch (Exception e) {
            logger.error("e:{}", e);
            return setResult("系统忙，请稍后重试！", wxMessage, weixinService);
        }
    }

    private WxMpXmlOutMessage setResult(String content, WxMpXmlMessage wxMessage, WxMpService weixinService) {
        return new TextBuilder().build(content, wxMessage, weixinService);
    }
}
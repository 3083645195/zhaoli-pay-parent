package com.zhaoli.pay.template.impl;

import com.zhaoli.pay.template.AbstractPayCallbackTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
@Controller
public class PingAnPayCallbackTemplate extends AbstractPayCallbackTemplate {
 @Override
 protected String asyncService() {
  return "平安支付";
 }

 @Override
 protected boolean verifySignature() {
  return Boolean.TRUE;
 }

 @Override
 protected String resultOk() {
  return "ok";
 }

 @Override
 protected String resultFail() {
  return "fail";
 }
}

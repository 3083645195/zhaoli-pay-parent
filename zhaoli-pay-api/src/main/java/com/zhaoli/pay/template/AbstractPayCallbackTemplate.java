package com.zhaoli.pay.template;

import com.zhaoli.pay.bo.NotifyMerchantBO;
import com.zhaoli.pay.constant.Constant;
import com.zhaoli.pay.entity.PayMerchantInfoDO;
import com.zhaoli.pay.entity.PaymentInfoDO;
import com.zhaoli.pay.enumclass.PaymentEnum;
import com.zhaoli.pay.holder.PaymentInfoHolder;
import com.zhaoli.pay.service.IPayMerchantInfoService;
import com.zhaoli.pay.service.PayMessageTemplateService;
import com.zhaoli.pay.service.open.api.OpenPaymentCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
@Slf4j
public abstract class AbstractPayCallbackTemplate {
    @Autowired
    private PayMessageTemplateService payMessageTemplateService;
    @Autowired
    private IPayMerchantInfoService payMerchantInfoService;
    @Autowired
    private OpenPaymentCoreService openPaymentCoreService;

    /**
     * 异步回调的处理
     */
    public String asynCallback() {
        //1.记录日志  相同 父类中
        addPayLog();
        //2.验证参数  不同  子类中
        boolean verifySignature = verifySignature();
        if (!verifySignature) {
            return resultFail();
        }
        //3.解析参数  不同  子类中
        String result = asyncService();
        //4.投递消息mq中将用户支付结果通知给我们商户端  相同 父类中
        //用户实际支付结果告诉商户端
        if (resultOk().equalsIgnoreCase(result)) {
            //如果处理的业务逻辑是ok的情况下，则开始通知给我们商户端
            notifyPaymentResult();
        }
        return result;
    }

    /**
     * 根据参数处理业务逻辑
     *
     * @return
     */
    protected abstract String asyncService();

    /**
     * 解析参数
     *
     * @return
     */
    protected abstract boolean verifySignature();

    /**
     * 返回ok
     *
     * @return
     */
    protected abstract String resultOk();

    /**
     * 返回失败
     *
     * @return
     */
    protected abstract String resultFail();

    /**
     * 记录支付日志
     */
    public void addPayLog() {

    }


    /**
     * 将用户实际支付结果 告诉商户端
     */
    private void notifyPaymentResult() {
        PaymentInfoDO paymentInfo = PaymentInfoHolder.get();
        if (paymentInfo == null) {
            return;
        }
        //根据商户id查询 企业信息 绑定 openid
        String merchantId = paymentInfo.getMerchantId();
        PayMerchantInfoDO payMerchantInfo = payMerchantInfoService.getByMerchantIdInfo(merchantId);
        String contactWxOpenid = payMerchantInfo.getContactWxOpenid();
        NotifyMerchantBO notifyMerchant = new NotifyMerchantBO(merchantId, contactWxOpenid, paymentInfo.getPayAmount(), new Date(), PaymentEnum.find(paymentInfo.getPaymentChannel()), paymentInfo.getPartyPayId());
        // 发送微信公众号消息模板推送支付结果
        payMessageTemplateService.notifyMerchantPaymentResultThread(notifyMerchant);
    }

    /**
     * 根据订单号码查询用户支付的结果
     *
     * @param orderId
     * @return
     */
    protected PaymentInfoDO getOrderIdByPaymentInfo(String orderId) {
        return openPaymentCoreService.getOrderIdByPaymentInfo(orderId);
    }

    /**
     * 更新db状态 已经支付
     *
     * @param paymentInfo
     * @return
     */
    protected String updatePaymentStatusOk(PaymentInfoDO paymentInfo) {
        int result = openPaymentCoreService.updatePaymentStatus(paymentInfo, Constant.PAY_PAYMENT_COMPLETED);
        return result > Constant.DEFAULT_VALUE_ZERO ? resultOk() : resultFail();
    }

    /**
     * @param payAmount   下单金额
     * @param totalAmount 实际用户支付金额
     * @return
     */
    protected boolean comparePaymentAmount(BigDecimal payAmount, BigDecimal totalAmount) {
        return payAmount.equals(totalAmount);
    }
}

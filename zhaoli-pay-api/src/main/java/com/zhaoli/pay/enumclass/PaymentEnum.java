package com.zhaoli.pay.enumclass;

public enum PaymentEnum {
    ALIPAY("支付宝"),
    UNIONPAY("银联");

    private final String value;

    PaymentEnum(String value) {
        this.value = value;
    }
    public static String find(String name){
        for (PaymentEnum paymentEnum : PaymentEnum.values()) {
            if(name.equals(paymentEnum.name())){
                return paymentEnum.value;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(PaymentEnum.ALIPAY.value);
        System.out.println(PaymentEnum.ALIPAY.name());
        String 支付宝 = find("ALIPAY");
        System.out.println(支付宝);
    }
}

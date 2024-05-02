package com.zhaoli.pay.config;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 沙箱测配置类
 */
/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "9021000134624536";

    // 商户私钥，您的PKCS8格式RSA2私钥 对应支付宝中：应用私钥:
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBnkAMOFwoHiFlU5hgMwmt+xl4mxBukcrI/P3GC7T1wQWvgaypduW/E+PZ/XhCGYI+9muYcgm6ERD6W/PGYgo02eBbzHvuuN5WrYskF2sXAto1CMKs6hq7R8rjSVJ2KL/peNnXRGTaaJHojEmJnSnIObTToH1+rCa2ByXLF0atz2XoHi5lljxrp0vyjaCk/nUDneD1o22B6rK6JQPZ94ANQz/7/lNK5GRVu2KdFzJtWsIT9rZNWekTAWD0ACRKm5MQqRKJywP0lI/yjrYrR1fn+me60AvDEu+tftN4ulBPUGvVWZ0SV0KLSS7flIpsdgv4a6i9slcfMGb3iLYC9ok/AgMBAAECggEAP99iwmLXjvFiFZN/KBCeeXyeTZ9kFoiDfE2nJHvKXbwTeCwXkJ7FkkZT5q0QIMp/M1WtKb8wad9VFXpQg3b2fprLA5GdlMLG1cHJKizcGcfPDYlWnWzzZUx4f043c4CbKYrjFkBezn3vQMA40LaAy2kcYZjZnp1oqhSVnCHJM4af4aOi66H9G6w3v/qufVqghHCIDUr5TPkEdIlYCGwsU26jVGIkC4ObPgcQ+bFjziI2SWp4wVtZxH/nekHtZzVc4HQ6hvZ6xDTY8SG4ZAC57W0xehYv7LBgNHZrdhWQKSwiOYM3E0nyLR/tRbYDd8+0a8Ic5BfUQg0StTpqv7qWAQKBgQD0a5hcv5OJ3jhZ12FJ1NRfXX2x3e4GxZshHymALlZBspuueKE+uCcqCen5yTp77jtqWl5y4CABOSUOyi6cJZPWoTw3I4B+tDO1hj+GZkSkZ0xLMRbtlZgTim1OdRpGFwYsFzswP9vCA6WPvxST6CRkcXP7BpS5dfqgp2YH/iijAQKBgQCHwkyhMm3Eu0m9x2n1lMpyfxfZTw/l9pAxjvN0wPm1xLeTI1M/YZ5o5sVkagjq2Ylq3Q/O1r2aQ3MpnajKRRzB8ie8AeeMDiYCryDHO0Ny56pvJGlpnoHuey3d6yWJCXKP6xnEbzqwF0AalW+BV6xUId8HU9latgyOezehfDJsPwKBgChNDWRNmlAjOftmTf5Uf2fyDGWliDfJoViGwNsHyOUwRAcykLO8vYhq9g9fpKsGjvlsSSJ73gsVlwynPaQ1dfjvwPP+gJNjtxr5NcQ9XJgXCDdlsrgd8GNYccl7+YcRCM2ATxwXi26kF0pqYUQ6BLYjFtKPNTqXP2n58BLlT2IBAoGAKKlEZF+DSxJBYusQTHLVM/fm/7pTa7Auvfkv5/9Ii22xAgvpiilF1euQoaO0qgqfwvcVHgFpZoPfQU6sWHDczSYnSoNTg7pVHiav7ZxNdmP2wzUnsW+9QN15/mbIpkPDIWHsJM6fyFAebhfl/tpwjf5bG4m8pxOxrRcwsVhOjecCgYEA8wKX/uigwZFzf+Y0NbxpfyhQ6Ce9gUV9VC+akjzDnPrfmnRc/Ja3RHk7gdVm2myKIjXac9tuiR3I9VpseBUtTIf2X7t7xbGFMniYjitSoI0ez+SX1fZKSJS13dlyRQAbcEi3YKAqLGoWtMBn0S04L8H7NYreHSfbtIvz/WGaHZI=";

    // 支付宝中的支付宝公钥:

    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqbsOz0Rn/68XSIP45/UjYPm4MYLu5Sfj7Inbp9cMt/ZQB7aY/X1pGnmdE0BV2GwyvOqMO9kNMXDWObTnzfasC6/Np8Exl7u5E3JiYo3euyxJLxefRJS+PRUsdWpW6j2n7q4jFAuNFI+mvkROC/qVMrTNFVxetOVjgyX8mGlOV8TNodVrw+W22unnC9YeCCwCxaNrya5zQcJHi3CslPF+6AruTPP3wp7yncdZJsi9oasLE89xw+bJ+62hDppYRNv/dArVdjK9FgldOyES74zVmtTvJbrHO+dAf306WYd2hd376rX4tERdx1QPBlOR5lq/9BGr/BifKwNcnZ/84RwZgQIDAQAB";
    // 异步通知 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://zhaoli.nat300.top/notify_url.jsp";

    // 同步通知 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://zhaoli.nat300.top/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


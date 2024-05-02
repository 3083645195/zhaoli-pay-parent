package com.zhaoli.pay.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zhaoli.pay.config.BodyReaderWrapper;
import com.zhaoli.pay.entity.MerchantSecretKeyDO;
import com.zhaoli.pay.service.IMerchantSecretKeyService;
import com.zhaoli.pay.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@Slf4j
public class InterceptionVerification implements HandlerInterceptor {
    @Autowired
    private IMerchantSecretKeyService iMerchantSecretKeyService;

    /**
     * 拦截请求
     * 在处理请求之前被调用的
     * 用于在请求到达Controller之前执行某些操作。在方法体内，可以编写一些逻辑来对请求进行处理，例如鉴权、日志记录等操作
     * 方法返回 trun 执行目标方法 否则不执行目标方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取json数据
        String parametersStr = getParameters(request);
        if (parametersStr == null) {
            setResponseFail(response, "parametersStr is null");
            return Boolean.FALSE;
        }
        JSONObject jsonObject = JSONObject.parseObject(parametersStr);
        if (jsonObject == null) {
            setResponseFail(response, "jsonObject is null");
            return Boolean.FALSE;
        }
        //2.验证appId和appKey是否正确 (从请求头中获取)
        String appId = request.getHeader("appId");
        if (StringUtils.isEmpty(appId)) {
            setResponseFail(response, "appId is null");
            return Boolean.FALSE;
        }
        String appKey = request.getHeader("appKey");
        if (StringUtils.isEmpty(appKey)) {
            setResponseFail(response, "appKey is null");
            return Boolean.FALSE;
        }
        MerchantSecretKeyDO merchantSecret = iMerchantSecretKeyService.getByAppIdKeyMerchantSecret(appId, appKey);
        if (merchantSecret == null) {
            setResponseFail(response, "appid或者appkey不正确!");
            return Boolean.FALSE;
        }
        //3.验证是否允许访问该权限
        String servletPath = request.getServletPath();//获取了当前请求的 Servlet 路径 例如 /api/v1/testToPay
        String permissionList = merchantSecret.getPermissionList();//获取权限列表
        boolean existsPermissionResult = existsPermission(servletPath, permissionList);//判断是否有权限
        if (!existsPermissionResult) {
            setResponseFail(response, "权限不足！");
            return Boolean.FALSE;
        }
        //4.有权限的情况下 验证下签名 防止被黑客篡改数据
        String sign = request.getParameter("sign");//取出签名值
        if (StringUtils.isEmpty(sign)) {
            setResponseFail(response, "sign is null");
            return Boolean.FALSE;
        }
        String timestamp = request.getParameter("timestamp");//取出时间戳
        if (StringUtils.isEmpty(timestamp)) {
            setResponseFail(response, "timestamp is null");
            return Boolean.FALSE;
        }
        String saltKey = merchantSecret.getSaltKey();//盐值
        //parametersStr 中可能会有空格 导致验签失败 所以应使用 jsonObject.toString()
        boolean verifyJsonResult = SignUtil.verifyJson(sign, timestamp, jsonObject.toString(), saltKey);
        if (!verifyJsonResult) {
            setResponseFail(response, "验证签名失败");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    //获取参数
    public static String getParameters(HttpServletRequest request) throws IOException {
        /**
         * 使用包装 HttpServletReqZuest，以便能够多次读取请求体中的内容
         */
        BodyReaderWrapper apiSignRequestWrapper = new BodyReaderWrapper(request);
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(apiSignRequestWrapper.getInputStream(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) responseStrBuilder.append(inputStr);
        return responseStrBuilder.toString();
    }

    /**
     * 判断是否存在该权限
     */
    private boolean existsPermission(String servletPath, String permissionList) {
        if (StringUtils.isEmpty(permissionList)){
            return Boolean.FALSE;
        }
        String[] permissionUrls = permissionList.split(",");
        for (String permissionUrl : permissionUrls) {
            if (permissionUrl.equals(servletPath)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 成功返回
     */
    public void setResponseOk(HttpServletResponse response) throws IOException {
        setResponse(response, 200, "OK");
    }

    /**
     * 失败返回
     */
    public void setResponseFail(HttpServletResponse response, String msg) throws IOException {
        setResponse(response, 500, msg);
    }

    /**
     * 通用返回
     */
    public void setResponse(HttpServletResponse response, Integer code, String msg) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("msg", msg);
        response.getWriter().println(result.toJSONString());
        response.getWriter().flush();
    }

}
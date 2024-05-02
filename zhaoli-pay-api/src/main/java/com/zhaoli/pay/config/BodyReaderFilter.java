package com.zhaoli.pay.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 解决在过滤器中将数据读取之后 controller 中读不到数据的问题
 * 这样做的作用是实现对 HTTP 请求体内容的多次读取。
 * 通常情况下，HttpServletRequest 中的输入流只能被读取一次，一旦读取完成，就无法再次读取。
 * 在某些情况下，我们需要多次读取请求体内容，比如对请求体进行校验、日志记录、数据加工等操作。
 * 而使用 BodyReaderWrapper 类和 BodyReaderFilter 过滤器的组合，可以实现对请求体内容的多次读取，避免在处理请求时因为只能读取一次输入流而导致的问题
 */
@Component
@WebFilter(filterName="bodyReaderFilter",urlPatterns="/*")
public class BodyReaderFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ServletRequest requestWrapper=null;
        if(request instanceof HttpServletRequest) {
            requestWrapper=new BodyReaderWrapper((HttpServletRequest)request);
        }
        if(requestWrapper==null) {
            chain.doFilter(request, response);
        }else {
            chain.doFilter(requestWrapper, response);
        }

    }

    @Override
    public void destroy() {
        // do nothing

    }
}
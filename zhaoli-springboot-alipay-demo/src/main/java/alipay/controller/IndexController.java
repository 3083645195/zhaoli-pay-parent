package alipay.controller;/*
* 作    者 ：蚂蚁课堂-余胜军
* 版 本 号 ：v1.0.0.0
*******************************************************************
* 版权由每特教育-蚂蚁课堂-余胜军所有 微信yushengjun644 QQ644064779
* 官方网址:www.mayikt.com 
*******************************************************************
//----------------------------------------------------------------*/

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
}

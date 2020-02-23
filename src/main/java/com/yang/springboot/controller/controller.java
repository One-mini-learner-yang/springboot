package com.yang.springboot.controller;

import com.yang.springboot.data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/controller")
//@PropertySource(value = "classpath:application.properties")
public class controller {
//    @Value("${data.url}")
//    private String url;
//    @Value("${data.driver}")
//    private String driver;
//    @Autowired
//    @Qualifier("data")
//    private data d;
    @ResponseBody
    @RequestMapping("hello")
    public String hello()
    {
//        System.out.println(url+driver);
//        System.out.println(d);
        return "hello world";
    }
}

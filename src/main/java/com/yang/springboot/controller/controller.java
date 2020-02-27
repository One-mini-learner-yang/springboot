package com.yang.springboot.controller;

import com.yang.springboot.data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/controller")
//@PropertySource(value = "classpath:application.properties")
public class controller {
    @Autowired
    @Qualifier("data")
    private data data;
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
//        data.setDriver("4256");
//        System.out.println(data);
//        System.out.println(d);
        return "hello world";
    }
}

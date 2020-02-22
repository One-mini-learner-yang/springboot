package com.yang.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/controller")
public class controller {
    @ResponseBody
    @RequestMapping("hello")
    public String hello()
    {
        return "hello world";
    }
}

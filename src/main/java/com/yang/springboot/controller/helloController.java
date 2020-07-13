package com.yang.springboot.controller;

import com.yang.yangspringbootstarterautoconfig.HelloServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {
    @Autowired
    HelloServer helloServer;
    @RequestMapping("/yang")
    public String sayHello(){
        return helloServer.sayHello("he");
    }
}

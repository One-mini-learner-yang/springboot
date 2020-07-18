package com.yang.springboot.controller;

import com.yang.yangspringbootstarterautoconfig.HelloServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class helloController {
    @Autowired
    HelloServer helloServer;
    @RequestMapping("/yang")
    public String sayHello(){
        return helloServer.sayHello("he");
    }
    @RequestMapping(value = "/jsp/file",method = RequestMethod.POST)
    public void file(MultipartFile multipartFile) throws IOException {
        StringBuffer name=new StringBuffer();
        name.append("D:\\谷歌下载\\");
        name.append(multipartFile.getName());
        byte[] bytes=multipartFile.getBytes();
        System.out.println(bytes);
        FileOutputStream fileOutputStream=new FileOutputStream(String.valueOf(name));
        fileOutputStream.write(bytes);
    }
}

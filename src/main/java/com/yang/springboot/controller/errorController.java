package com.yang.springboot.controller;

import com.yang.springboot.myException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class errorController {
    @RequestMapping("/errorWarning")
    public String errorWarning(String name){
//        if (!name.equals("aaa")){
//            throw new myException();
//        }
        return "add";
    }
}

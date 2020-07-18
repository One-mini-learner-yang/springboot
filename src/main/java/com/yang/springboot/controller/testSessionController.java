package com.yang.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jsp")
public class testSessionController {
    @RequestMapping("testSessionController")
    public void test(HttpServletResponse response, HttpServletRequest request,String username,String password){
        Map<String,Integer>map = (Map<String, Integer>) request.getSession().getAttribute("test");
        Logger logger= LoggerFactory.getLogger(getClass().getName());
        if(map==null){
            map=new HashMap<>();
            map.put("password",Integer.valueOf(password));
            request.getSession().setAttribute("test",map);
        }else {
            logger.info(map.toString());
        }
    }

}

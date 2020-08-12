package com.yang.springboot.controller;

import com.yang.springboot.myException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler(myException.class)
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("11111");
        Map map=new HashMap();
        map.put("code","warning~~~~~");
        map.put("message",e.getMessage());
        map.put("author","yang");
        request.setAttribute("ext",map);
        request.getRequestDispatcher("jsp/error.jsp").forward(request,response);
//        return "forward:/error";
    }

}

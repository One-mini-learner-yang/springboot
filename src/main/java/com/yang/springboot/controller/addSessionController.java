package com.yang.springboot.controller;

import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jsp")
public class addSessionController {
    @RequestMapping("addSessionController")
    public void  add(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,int id) throws IOException {
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        String[] names={"ad钙奶","泡面","火腿肠","雀巢"};
        String name=names[id];
        Map<String,Integer> map= (Map<String, Integer>) httpServletRequest.getSession().getAttribute("kind");
        if(map==null){
            map=new HashMap<String, Integer>();
            httpServletRequest.getSession().setAttribute("kind",map);
        }
        if (map.containsKey(name)){
            map.put(name,map.get(name)+1);
        }else {
            map.put(name,1);
        }
        httpServletResponse.getWriter().write("<a href='./add.jsp'><h1>继续购买</h1></a>");
        httpServletResponse.getWriter().write("<a href='./show.jsp'><h1>去购物车结算</h1></a>");
    }
}

package com.yang.springboot.config;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
@Component
public class errorAttribute extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String,Object> map=new HashMap<>();
        map.put("company","yang");
        map.put("ext",webRequest.getAttribute("ext",0));
        return map;
    }
}

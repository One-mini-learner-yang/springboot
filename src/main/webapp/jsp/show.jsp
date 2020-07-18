<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en" >
    <head>
    </head>
    <body>
        <%
            Map<String,Integer> map= (Map<String, Integer>) session.getAttribute("kind");
            if(map!=null){
                for (String key:map.keySet()){
                    int value=map.get(key);
                    System.out.println(key);
                    System.out.println(value);
                }
            }
        %>
    <a href="clearAjax">清空购物车</a>
    </body>
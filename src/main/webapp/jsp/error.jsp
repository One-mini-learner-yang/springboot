<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en" >
<head>
</head>
    <body>
<%--    <%--%>
<%--        Map map= (Map) session.getAttribute("ext");--%>
<%--        if (map!=null){--%>
<%--            for(Object key:map.keySet()){--%>
<%--                System.out.println(key);--%>
<%--                System.out.println(map.get(key));--%>
<%--            }--%>
<%--        }--%>
<%--    %>--%>
        <h1>status:[[${status}]]</h1>
        <h2>timestamp:[[${timestamp}]]</h2>
        <h2>exception:[[${exception}]]</h2>
        <h2>message:[[${message}]]</h2>
        <h2>company:[[${company}]]</h2>
        <h2>ext:[[${ext.code}]]</h2>
        <h2>ext:[[${ext.message}]]</h2>
    </body>

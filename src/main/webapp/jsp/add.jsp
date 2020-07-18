<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en" >
    <head>
    </head>
    <body>
        <a href="addSessionController?id=0">ad钙奶</a>
        <a href="addSessionController?id=1">泡面</a>
        <a href="addSessionController?id=2">火腿肠</a>
        <a href="addSessionController?id=3">雀巢</a>
    <form action="file"  enctype="multipart/form-data" method="post">
        <input type="file" name="multipartFile">
        <input type="submit" value="提交">
    </form>
    </body>
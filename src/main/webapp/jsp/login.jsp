<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en" >
	<head>
	</head>
	<body class="text-center">
		<form  action="testSessionController"method="post">
			<center>
				<h1></h1>
				<!--判断-->
				<label ></label>
				<input type="text"  name="username"/>
				<br>
				<label ></label>
				<input type="password" name="password"/>
				<br>
				<button type="submit" value=""></button>
				<p>© 2017-2018</p>
			</center>
		</form>
		<table>
			<thead><a href="/?l=zh_CN">中文</a></thead>
			<thead>   </thead>
			<thead><a href="/?l=en_US">English</a></thead>
		</table>
	</body>
</html>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的网络相册</title>
</head>
<body>
<%String path=(String)request.getAttribute("src"); %>
<p align="center"><img src="Photo?op=getPhoto&path=<%=URLEncoder.encode(path, "UTF-8") %>"></p>
</body>
</html>
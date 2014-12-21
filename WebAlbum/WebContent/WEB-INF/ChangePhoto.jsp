<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的相册空间</title>
<link rel="stylesheet" type="text/css" href="CSS/mycss2.css" />
</head>
<body >
<%String currPath=(String)request.getAttribute("path"); 
	String albumName=(String)request.getAttribute("albumName");%>
<h1 align="center" style="font-size:3.0em;color:#F87B7B">修改图片名称</h1>
<br />
<form action="Photo?op=doChangePhoto&path=<%=URLEncoder.encode(currPath, "UTF-8") %>&name=<%=URLEncoder.encode(albumName, "UTF-8") %>" method="post">
	<p align="center" style="font-size:1.6em;">
		新的图片名(请不要输入后缀名)：<input type="text" name="newPhotoName" style="font-size:1.2em;" />
	</p>
	<br />
	<p align="center">
		<input type="submit" value="确认" style="font-size:1.3em;"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="Photo?op=showPhotos&name=<%=URLEncoder.encode(albumName, "UTF-8")%>"><input type="button" value="返回" style="font-size:1.3em;"/></a>
	</p>
</form>
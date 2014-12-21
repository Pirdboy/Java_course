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
<%String currAlbumName=(String)request.getAttribute("albumName"); %>
<h1 align="center" style="font-size:3.0em;color:#F87B7B">修改相册名称</h1>
<br />
<p align="center" style="font-size:1.8em">当前相册名:<%=currAlbumName %></p>
<br />
<form action="Photo?op=doChangeAlbum&albumName=<%=URLEncoder.encode(currAlbumName, "UTF-8") %>" method="post">
	<p align="center" style="font-size:1.6em;">
		新的相册名：<input type="text" name="newAlbumName" style="font-size:1.2em;" />
	</p>
	<br />
	<p align="center">
		<input type="submit" value="确认" style="font-size:1.3em;"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="Photo?op=showAlbums"><input type="button" value="返回" style="font-size:1.3em;"/></a>
	</p>
</form>

</body>
</html>
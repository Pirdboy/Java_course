<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>我的网络相册</title>
<link rel="stylesheet" type="text/css" href="CSS/mycss.css" />
</head>

<body>

<div id="container"> <!-- 总容器 -->
	<div id="Header">  <!-- 头部 -->
		<h1>Pird的相册空间</h1>
		<ul>
			<li><a href="Photo?op=showAlbums" class="header">相册首页</a></li>
			<li class="list_separate"></li>
			<li><a href="Photo?op=addAlbum" class="header">添加相册</a></li>
		</ul>
	</div>
	
	<div id="PageBody">   <!-- 主体 添加相片-->
	<%String albumName=(String)request.getAttribute("albumName"); %>
	<h1 class="addPhoto_h1"><%=albumName%>添加图片</h1>
		<form action="Photo?op=doAddPhoto&albumName=<%=URLEncoder.encode(albumName, "UTF-8") %>" method="post" enctype="multipart/form-data">
		<p class="addPhoto_p1">请选择图片:</p> 
		<p class="addPhoto_p2"><input accept="image/*" type="file" name="file1" style="font-size:1.8em;width:260px;height:30px" />
		<p class="addPhoto_p3">
			<input type="submit" value="确定" style="font-size:1.3em;width:80px;height:30px"/>
		</p>
		<p class="addPhoto_p4">
			<a href="Photo?op=showPhotos&name=<%=URLEncoder.encode(albumName, "UTF-8") %>" ><input type="button" value="返回" style="font-size:1.3em;width:80px;height:30px"/></a>
		</p>
		</form>
	</div>
	
	<div id="Footer">	<!-- 底部 -->
		<p class="footer_p1">Copyright &copy 2014 by CQU石鸿宇(Pirdboy). All rights reserved.</p>
		<p class="footer_p2">Powered By <a class="footer_a" href="http://www.w3school.com.cn/">http://www.w3school.com.cn/</a></p>
	</div>
</div>
</body>
</html>
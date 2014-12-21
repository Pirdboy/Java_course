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
	
	<div id="PageBody">   <!-- 主体 添加相册-->
		<h1 class="addAlbum_h1">添加相册</h1>
		<form action="Photo?op=doAddAlbum" method="post">
		<p class="input_p1">相册名称:</p> 
		<p class="input_p11"><input type="text" maxlength="15" name="name" style="font-size:1.7em;width:271px;height:38px" /></p>
		<p class="input_p2">
			<input type="submit" value="确定" style="font-size:1.3em;width:80px;height:30px"/>
		</p>
		<p class="input_p3">
			<a href="Photo?op=showAlbums"><input type="button" value="返回" style="font-size:1.3em;width:80px;height:30px"/></a>
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
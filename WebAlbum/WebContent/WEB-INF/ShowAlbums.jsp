<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
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
	
	<div id="PageBody">   <!-- 主体 显示相册-->
	<%
		File[] albums=(File[])(request.getAttribute("albums"));
	%>
	<table class="DisplayAlbums" width="1020px" border="4px">
		<tr>
			<th colspan="4">相册列表</th>
		</tr>
		<%
		int rows=albums.length/4+1;
		for(int r=0;r<rows;r++){
			%>
			<tr>
			<% 
			for(int i=0;i<4 && r*4+i<albums.length;i++){
				%>
				<td>
					<p><a class="a1" href="Photo?op=showPhotos&name=<%=URLEncoder.encode(albums[r*4+i].getName(), "UTF-8") %>"><%=albums[r*4+i].getName() %></a></p>
					<p><a class="a2" href="Photo?op=deleteAlbum&name=<%=URLEncoder.encode(albums[r*4+i].getName(), "UTF-8") %>">删除</a></p>
					<p><a class="a2" href="Photo?op=changeAlbum&name=<%=URLEncoder.encode(albums[r*4+i].getName(), "UTF-8") %>">修改</a></p>
				</td>
				<%
				if(i<3 && r*4+i==albums.length-1){
					int t=r*4+3-(albums.length-1);
					for(int k=0;k<t;k++){
						%>
						<td></td>
						<%
					}
				}
			}
			%>
			</tr>
			<% 
		}
		%>
	</table>
	</div>
	
	<div id="Footer">	<!-- 底部 -->
		<p class="footer_p1">Copyright &copy 2014 by CQU石鸿宇(Pirdboy). All rights reserved.</p>
		<p class="footer_p2">Powered By <a class="footer_a" href="http://www.w3school.com.cn/">http://www.w3school.com.cn/</a></p>
	</div>
</div>
</body>
</html>
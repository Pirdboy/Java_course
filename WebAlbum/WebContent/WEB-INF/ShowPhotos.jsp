<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.File"%>
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
	
	<div id="PageBody">   <!-- 主体 显示相片-->
	<%
		File[] photos=(File[])(request.getAttribute("photos"));
		String albumName=(String)(request.getAttribute("albumName"));
	%>
	<table class="DisplayPhotos" width="1020px" border="4px">
		<tr>
			<th colspan="4">
				相册:<%=albumName %>&nbsp;&nbsp;
				<a href="Photo?op=addPhoto&albumName=<%=URLEncoder.encode(albumName, "UTF-8") %>"><button type="button" style="height:30px">添加图片</button></a>
			</th>
		</tr>
		<%
		int rows=photos.length/4+1;
		for(int r=0;r<rows;r++){
			%>
			<tr>
			<% 
			for(int i=0;i<4 && r*4+i<photos.length;i++){
				%>
				<td width="260px" height="200px">
					<p><a target="_blank" href="Photo?op=showBigImage&path=<%=URLEncoder.encode(photos[r*4+i].getAbsolutePath(), "UTF-8") %>">
						<img width="160px" src="Photo?op=getPhoto&path=<%=URLEncoder.encode(photos[r*4+i].getAbsolutePath(), "UTF-8") %>">
						</a>
					</p>
					<p class="PhotoName"><%=photos[r*4+i].getName() %></p>
					<p><a class="PhotoAction" onclick="alert('链接已复制到剪切板')" href="Photo?op=sharePhoto&name=<%=URLEncoder.encode(albumName, "UTF-8") %>&path=<%=URLEncoder.encode(photos[r*4+i].getAbsolutePath(), "UTF-8") %>">
						分享</a>
					&nbsp;&nbsp;
					<a class="PhotoAction" href="Photo?op=downloadPhoto&name=<%=URLEncoder.encode(albumName, "UTF-8") %>&path=<%=URLEncoder.encode(photos[r*4+i].getAbsolutePath(), "UTF-8") %>">
						下载</a></p>
					<p><a class="PhotoAction" href="Photo?op=changePhoto&name=<%=URLEncoder.encode(albumName, "UTF-8") %>&path=<%=URLEncoder.encode(photos[r*4+i].getAbsolutePath(), "UTF-8") %>">
						修改</a>
					&nbsp;&nbsp;
					<a class="PhotoAction" href="Photo?op=deletePhoto&name=<%=URLEncoder.encode(albumName, "UTF-8") %>&path=<%=URLEncoder.encode(photos[r*4+i].getAbsolutePath(), "UTF-8") %>">
						删除</a></p>
				</td>
				<%
				if(i<3 && r*4+i==photos.length-1){
					int t=r*4+3-(photos.length-1);
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
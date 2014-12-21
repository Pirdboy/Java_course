package cqu.shy.servlet;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import cqu.shy.fileope.FileOperation;


@WebServlet("/Photo")
@MultipartConfig(
		location = "/home/asus/WebAlbumServer/",  // The directory location where files will be stored
		maxFileSize = 10485760,  // The maximum size allowed for uploaded file.8388608=8MB,以B为单位
		fileSizeThreshold = 819200 //800*1024 	当数据量大于该值时，内容将被写入文件。
)

public class PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String rootDir="/home/asus/WebAlbumServer/";
    public PhotoServlet() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String op = request.getParameter("op");
		try{
			switch(op){
				case "addAlbum":
					showAddAlbumPage(request, response);
					break;
				case "addPhoto":
					showAddPhotoPage(request, response);
					break;
				case "getPhoto":
					getPhoto(request, response);
					break;
				case "showAlbums": 
					doShowAlbums(request,response);
					break;
				case "showPhotos":
					doShowPhotos(request,response);
					break;
				case "deleteAlbum":  
					doDeleteAlbum(request,response);
					break;
				case "deletePhoto":  
					doDeletePhoto(request,response);
					break;
				case "changeAlbum":
					showChangeAlbumPage(request, response);
					break;
				case "changePhoto":
					showChangePhotoPage(request, response);
					break;
				case "showBigImage": 
					doShowBigImage(request,response);
					break;
				case "sharePhoto":
					doSharePhoto(request, response);
					break;
				case "downloadPhoto":
					doDownloadPhoto(request,response);
					break;
			}
		}catch (Exception e){
			throw new ServletException(e);
		}
	}
	private void showAddAlbumPage(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
		File dir = new File(rootDir);
		String[] albums =  dir.list();
		request.setAttribute("albums", albums);
		RequestDispatcher rd = request
				.getRequestDispatcher("/WEB-INF/AddAlbum.jsp");
		rd.forward(request, response);
	}
	private void showAddPhotoPage(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
		String albumName = request.getParameter("albumName");
		request.setAttribute("albumName", albumName);
		RequestDispatcher rd = request
				.getRequestDispatcher("/WEB-INF/AddPhoto.jsp");
		rd.forward(request, response);
	}
	private void getPhoto(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
		String path = request.getParameter("path");
		InputStream in = new FileInputStream(new File(path));
		OutputStream out = response.getOutputStream();
		FileOperation.copy(in, out);
	}
	private void doShowAlbums(HttpServletRequest request, HttpServletResponse response) 
				throws Exception {
		File dir = new File(rootDir);
		List<File> sortedFiles = FileOperation.getFileSortedByTime(dir);
		File[] albums = sortedFiles.toArray(new File[sortedFiles.size()]);
		request.setAttribute("albums", albums);
		RequestDispatcher rd = request
				.getRequestDispatcher("/WEB-INF/ShowAlbums.jsp");
		rd.forward(request, response);
	}
	private void doShowPhotos(HttpServletRequest request, HttpServletResponse response) 
				throws Exception {
		String albumName = request.getParameter("name");
		File album = new File(rootDir+albumName);
		List<File> sortedPhotos = FileOperation.getFileSortedByTime(album);
		File[] photos = sortedPhotos.toArray(new File[sortedPhotos.size()]);
		request.setAttribute("photos", photos);
		request.setAttribute("albumName", albumName);
		RequestDispatcher rd = request
				.getRequestDispatcher("/WEB-INF/ShowPhotos.jsp");
		rd.forward(request, response);
	}
	private void doDeleteAlbum(HttpServletRequest request, HttpServletResponse response) 
				throws Exception {
		String albumName = request.getParameter("name");
		File dir=new File(rootDir+albumName);
		if(!dir.exists())
			return;
		else if(dir.isDirectory())
			FileOperation.deleteDictory(dir);
		response.sendRedirect("Photo?op=showAlbums");
	}
	private void doDeletePhoto(HttpServletRequest request, HttpServletResponse response) 
				throws Exception {
		String path=request.getParameter("path");
		System.out.println("删除图片:"+path);
		File file=new File(path);
		if(file.isFile())
			file.delete();
		String albumName=request.getParameter("name");
		response.sendRedirect("Photo?op=showPhotos&name="+URLEncoder.encode(albumName,"UTF-8"));
	}
	private void showChangeAlbumPage(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		String albumName=request.getParameter("name");
		request.setAttribute("albumName", albumName);
		RequestDispatcher rd = request
				.getRequestDispatcher("/WEB-INF/ChangeAlbum.jsp");
		rd.forward(request, response);
			
	}
	private void showChangePhotoPage(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		String path=request.getParameter("path");
		String albumName=request.getParameter("name");
		request.setAttribute("path", path);
		request.setAttribute("albumName", albumName);
		RequestDispatcher rd = request
				.getRequestDispatcher("/WEB-INF/ChangePhoto.jsp");
		rd.forward(request, response);
	}
	private void doShowBigImage(HttpServletRequest request, HttpServletResponse response) 
				throws Exception {
		String path = request.getParameter("path");
		request.setAttribute("src", path);
		RequestDispatcher rd = request
				.getRequestDispatcher("/WEB-INF/ShowBigImage.jsp");
		rd.forward(request, response);
	}
	private void doSharePhoto(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		String path = request.getParameter("path");
		String albumName = request.getParameter("name");
		String URL = request.getRequestURL() +"?op=showBigImage&path="+URLEncoder.encode(path, "UTF-8");
		//复制到剪切板
		StringSelection stringSelection = new StringSelection( URL );
		Clipboard  clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		response.sendRedirect("Photo?op=showPhotos&name="+URLEncoder.encode(albumName,"UTF-8"));
	}
	private void doDownloadPhoto(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		String path= request.getParameter("path");
		String albumName = request.getParameter("name");
		File file = new File(path);  //获得的文件名的编码仍然不是UTF-8，是ISO-8859-1,在JSP文件中修改的是文件的编码
		String fileName = new String(file.getName().getBytes("UTF-8"),"ISO-8859-1");
		//当 Internet Explorer 接收到头时，它会激活文件下载对话框，它的文件名框自动填充了头中指定的文件名。
		response.setHeader("Content-disposition", "attachment; filename="+fileName);
		InputStream in = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		FileOperation.copy(in,out);
		response.sendRedirect("Photo?op=showPhotos&name="+URLEncoder.encode(albumName,"UTF-8"));
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String op = request.getParameter("op");
		try {
			switch (op) {
			case "doAddAlbum": 
				doAddAlbum(request,response);
				break;
			case "doAddPhoto": 
				doAddPhoto(request,response);
				break;
			case "doChangeAlbum":
				doChangeAlbum(request, response);
				break;
			case "doChangePhoto":
				doChangePhoto(request, response);
				break;
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	private void doAddAlbum(HttpServletRequest request, HttpServletResponse response) 
				throws Exception {
		String albumName= request.getParameter("name");
		if(albumName!=null && !albumName.equals("")){
			File file = new File(rootDir + albumName);
			file.mkdir();
		}
		//这里需要判断是否相册已经存在
		response.sendRedirect("Photo?op=showAlbums");
	}
	private void doAddPhoto(HttpServletRequest request, HttpServletResponse response) 
				throws Exception {
		String albumName=request.getParameter("albumName");
		Collection<Part> parts = request.getParts();
		if (parts != null) {
			System.out.println(parts.size());
			for (Part part : parts) {
				String filename = getFileName(part);
				if (filename != null && !filename.equals("")) {
					part.write(rootDir+albumName+"/"+ filename);
				}
			}
			response.sendRedirect("Photo?op=showPhotos&name="+URLEncoder.encode(albumName, "UTF-8"));
		}
	}
	private void doChangeAlbum(HttpServletRequest request, HttpServletResponse response) 
				throws Exception {
		String oldAlbumName = request.getParameter("albumName");
		String newAlbumName = request.getParameter("newAlbumName");
		if(oldAlbumName.equals(newAlbumName)){
			//do nothing
		}
		else{
			System.out.println("旧的相册名:"+oldAlbumName);
			System.out.println("新的相册名:"+newAlbumName);
			File dir=new File(rootDir+oldAlbumName);
			File newDir = new File(rootDir+newAlbumName);
			if(dir.exists() && !newDir.exists()){
				dir.renameTo(newDir);
			}
		}
		response.sendRedirect("Photo?op=showAlbums");
	}
	private void doChangePhoto(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		String oldPath = request.getParameter("path");
		String suffix = oldPath.substring(oldPath.lastIndexOf("."));
		System.out.println("后缀名是:"+suffix);
		String albumName = request.getParameter("name");
		String newPhotoName =request.getParameter("newPhotoName");
		if(newPhotoName.contains(".")){
			newPhotoName=newPhotoName.substring(0, newPhotoName.lastIndexOf(".")-1);
		}
		System.out.println("新的图片名："+newPhotoName);
		String newPath=rootDir+albumName+"/"+newPhotoName+suffix;
		if(oldPath.equals(newPath)||newPhotoName.equals("")||newPhotoName==null){
			//do nothing
		}
		else{
			File file1 = new File(oldPath);
			File file2 = new File(newPath);
			if(file1.exists() && !file2.exists()){
				file1.renameTo(file2);
			}
		}
		response.sendRedirect("Photo?op=showPhotos&name="+URLEncoder.encode(albumName, "UTF-8"));
	}
	private String getFileName(Part part) {
		// get file name from content-disposition in HTTP header.
		String cotentDesc = part.getHeader("content-disposition");
		String fileName = null;
		// use regular expression to find out file name
		Pattern pattern = Pattern.compile("filename=\".+\"");
		Matcher matcher = pattern.matcher(cotentDesc);
		if (matcher.find()) {
			fileName = matcher.group();
			fileName = fileName.substring(10, fileName.length() - 1);
		}
		return fileName;
	}
}

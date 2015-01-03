package cqu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileOperation {
	public static void copyDir(String oldPath,String newPath){
		try  {    
	           (new  File(newPath)).mkdirs();  //如果文件夹不存在  则建立新文件夹    
	           File  a=new  File(oldPath);    
	           String[]  file=a.list();    
	           File  temp=null;    
	           for  (int  i  =  0;  i  <  file.length;  i++)  {    
	               if(oldPath.endsWith(File.separator)){    
	                   temp=new  File(oldPath+file[i]);    
	               }    
	               else{    
	                   temp=new  File(oldPath+File.separator+file[i]);    
	               }    
	   
	               if(temp.isFile()){    
	                   FileInputStream  input  =  new  FileInputStream(temp);    
	                   FileOutputStream  output  =  new  FileOutputStream(newPath  +  "/"  +   
	                           (temp.getName()).toString());    
	                   byte[]  b  =  new  byte[1024  *  5];    
	                   int  len;    
	                   while  (  (len  =  input.read(b))  !=  -1)  {    
	                       output.write(b,  0,  len);    
	                   }    
	                   output.flush();    
	                   output.close();    
	                   input.close();    
	               }    
	               if(temp.isDirectory()){//如果是子文件夹    
	                   copyDir(oldPath+"/"+file[i],newPath+"/"+file[i]);    
	               }    
	           }    
	       }    
	       catch  (Exception  e)  {      
	           e.printStackTrace();    
	   
	       }   
	}
	public static void copyFile(String src, String dest) {
		try {
			InputStream in=new FileInputStream(src);
			OutputStream out=new FileOutputStream(dest);
			byte b[]=new byte[1024*1024*5];
			int len=in.read(b);
			while(len!=-1)
			{
				out.write(b, 0, len);
				len=in.read(b);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void delete(File f) {
		if (f.isFile() && f.exists()) {
			f.delete();
			return;
		} else if (f.isDirectory() && f.exists()) {
			File[] children = f.listFiles();
			if (children != null) {
				for (File temp : children) {
					if (temp.isFile() && temp.exists())
						temp.delete();
					else if (temp.isDirectory() && temp.exists())
						delete(temp);
				}
			}
			f.delete();
			return;
		}
	}

	public static void changeName(File f, String newName) {
		if (f.isFile() && f.exists()) {
			String parent = f.getParent();
			String newPath = parent+"/"+newName;
			f.renameTo(new File(newPath));
			return;
		}
		else if(f.isDirectory() && f.exists()){
			String parent = f.getParent();
			String newPath = parent+"/"+newName;
			copyDir(f.getAbsolutePath(), newPath);
			delete(f);
		}
	}
}

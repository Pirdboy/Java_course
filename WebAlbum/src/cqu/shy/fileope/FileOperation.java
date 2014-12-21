package cqu.shy.fileope;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileOperation {
	public static void delete(File file) {
		if (file.isFile())
			file.delete();
		else {
			File[] list = null;
			list = file.listFiles();
			if (list != null)
				for (File dirItem : list)
					delete(dirItem);
		}
		file.delete();
	}
	public static void copy(InputStream in,OutputStream out) throws IOException
	{
		byte[] bytes = new byte[1024];
		int len=0;
		while((len=in.read(bytes))>0)
			out.write(bytes, 0, len);
		out.close();
		in.close();
	}
	public static void deleteFile(File file){
		file.delete();
	}
	public static void deleteDictory(File dir){
		if(dir.isDirectory()){
			File[] files = dir.listFiles();
			for(int i=0;i<files.length;i++){
				if(files[i].isFile())
					deleteFile(files[i]);
				else if(files[i].isDirectory())
					deleteDictory(files[i]);
			}
			dir.delete();
		}
	}
	public static List<File> getFileSortedByTime(File dir){
		List<File> list = new ArrayList<File>();
		if(dir.isDirectory())
		{
			File[] subfiles = dir.listFiles();
			for(File file:subfiles){
				list.add(file);
			}
		}
		Collections.sort(list, new Comparator<File>(){

			@Override
			public int compare(File o1, File o2) {
				
				if(o1.lastModified() < o2.lastModified())
					return 1;
				else if(o1.lastModified()==o2.lastModified())
					return 0;
				else
					return -1;
			}
		});
		return list;
	}
}

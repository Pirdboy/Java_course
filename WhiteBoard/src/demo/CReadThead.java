package demo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

//客户端接受图片线程类
public class CReadThead extends Thread {
    
    private Socket socket;
    private ObjectInputStream objectInputStream;

    public CReadThead(Socket socket, ObjectInputStream objectInputStream) {
        this.socket = socket;
        this.objectInputStream = objectInputStream;
    }

    @Override
    public void run() {
        while(true) {
            try {
                PacketBean data = (PacketBean) objectInputStream.readObject();
                if(data.getPacketType().equals("sendshots")) {
                    System.out.println("接受图片");
                    byte[] ob = (byte[])data.getData();
                    createBit(ob);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }            
        }
    }
    
    public void createBit(byte[] obj) throws IOException {    
        ByteArrayInputStream bin = new ByteArrayInputStream(obj);  
        BufferedImage image = ImageIO.read(bin); 
        ImageIO.write(image, "jpg", new File("C:/Users/admin/Desktop/2.jpg"));  
       
        bin.close();
        
    }    
    
}
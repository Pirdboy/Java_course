package demo;


import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

//图片数据包发送线程类
public class ScreenShotsThread extends Thread {

    private ObjectOutputStream objectOutputStream;

    public ScreenShotsThread(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public void run() {    
        ScreenShots screenShots = new ScreenShots();
        ByteArrayOutputStream ByteArray = screenShots.snapShot();
        byte[] datas = ByteArray.toByteArray();    

        try {             
            PacketBean data = new PacketBean();
            data.setPacketType("sendshots");
            data.setData(datas);
            
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }    
 
    
    }
    
}
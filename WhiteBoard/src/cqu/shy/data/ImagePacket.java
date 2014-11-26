package cqu.shy.data;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class ImagePacket implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BufferedImage img;

	public ImagePacket(BufferedImage img){
		this.img = img;
	}
	public ByteArrayOutputStream getByteOutputStream() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			// 将img对象写入输出流
			ImageIO.write(img, "png", out);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return out;
	}
}

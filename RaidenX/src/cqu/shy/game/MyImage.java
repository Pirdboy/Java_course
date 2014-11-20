package cqu.shy.game;

import java.awt.image.BufferedImage;

class MyImage {
	private BufferedImage image;
	private int x;
	private int y;
	private int width;
	private int height;
	public MyImage(BufferedImage img,int w,int h,int x,int y){
		this.image = img;
		this.width = w;
		this.height = h;
		this.x = x;
		this.y = y;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}
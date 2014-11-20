package cqu.shy.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import cqu.shy.resource.ImageResource;

class HelpScene extends FlyingObject{
	private MyImage HelpImg = new MyImage(ImageResource.HelpIMG, ImageResource.HelpIMG.getWidth(),
			ImageResource.HelpIMG.getHeight(), 20, 20);
	public HelpScene(){
		
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(ImageResource.BackgroundIMG, 0, -ImageResource.BackgroundIMG.getHeight()/2-50, null);
		g.drawImage(HelpImg.getImage(), HelpImg.getX(), HelpImg.getY(),HelpImg.getWidth(), HelpImg.getHeight(), null);
		//g.setColor(Color.RED);
		//g.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 50));
		//g.drawString("这是帮助界面", 100, 300);
	}

}

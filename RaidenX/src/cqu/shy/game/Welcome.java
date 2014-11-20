package cqu.shy.game;
import cqu.shy.resource.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


class Welcome extends FlyingObject{
	MyImage Welcomeplane1;
	MyImage Welcomeplane2;
	int title_x=170;
	int title_y=-50;
	public Welcome(){
		Welcomeplane1 = new MyImage(ImageResource.WelcomePlane1IMG, 
				ImageResource.WelcomePlane1IMG.getWidth(), ImageResource.WelcomePlane1IMG.getHeight()
				, -ImageResource.WelcomePlane1IMG.getWidth(), 700+2);
		Welcomeplane2 = new MyImage(ImageResource.WelcomePlane2IMG, 
				ImageResource.WelcomePlane2IMG.getWidth(), ImageResource.WelcomePlane2IMG.getHeight()
				,500 , 700+2);
		
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		Welcomeplane1.setX(-ImageResource.WelcomePlane1IMG.getWidth());
		Welcomeplane1.setY(700+2);
		Welcomeplane2.setX(500);
		Welcomeplane2.setY(700+2);
		title_x=170;
		title_y = -50;
	}
	@Override
	public void move() {
		// TODO Auto-generated method stub
		if(Welcomeplane1.getY()>=-200){
			Welcomeplane1.setX(Welcomeplane1.getX()+2);
			Welcomeplane1.setY(Welcomeplane1.getY()-3);
			Welcomeplane2.setX(Welcomeplane2.getX()-2);
			Welcomeplane2.setY(Welcomeplane2.getY()-3);
		}
		if(title_y<=80){
			title_y+=1;
		}
	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(ImageResource.BackgroundIMG, 0, -ImageResource.BackgroundIMG.getHeight()/2-50, null);
		g.drawImage(Welcomeplane1.getImage(), Welcomeplane1.getX(), 
				Welcomeplane1.getY(), Welcomeplane1.getWidth(), Welcomeplane1.getHeight(), null);
		g.drawImage(Welcomeplane2.getImage(), Welcomeplane2.getX(), 
				Welcomeplane2.getY(), Welcomeplane2.getWidth(), Welcomeplane2.getHeight(), null);
		g.setColor(new Color(255,102,0));
		g.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 50));
		g.drawString("雷电  X", title_x, title_y);
	}
	
}
